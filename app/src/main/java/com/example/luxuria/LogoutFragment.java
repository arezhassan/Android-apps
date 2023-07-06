package com.example.luxuria;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.luxuria.LoginActivity;
import com.example.luxuria.R;
import com.example.luxuria.orders;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import java.util.Random;

public class LogoutFragment extends Fragment {

    private ImageView logoutImageView, userimage;

    private static final int PICK_IMAGE_REQUEST = 1;
    CardView changepicture;
    CardView orders;
    Uri imageuri;
    TextView username;
    FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logout, container, false);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getActivity());

        logoutImageView = view.findViewById(R.id.logoutImageView);
        username = view.findViewById(R.id.username);
        userimage = view.findViewById(R.id.userimage);
        changepicture = view.findViewById(R.id.changepicture);
        orders = view.findViewById(R.id.orders);

        String hello = "Hello";

        if (mAuth.getCurrentUser() != null) {
            String userId = mAuth.getCurrentUser().getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            username.setText(hello + " " + mAuth.getCurrentUser().getDisplayName() + "!");
            db.collection("users").document(userId)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                String profilepic = documentSnapshot.getString("profilepic");
                                Picasso.get()
                                        .load(profilepic)
                                        .centerInside()
                                        .fit()
                                        .placeholder(R.drawable.purpleloading)
                                        .error(R.drawable.error)
                                        .into(userimage);
                            }
                        }
                    });
        } else {
            userimage.setImageResource(R.drawable.avatar);
            username.setText("HELLO");
        }

        changepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.example.luxuria.orders.class);
                startActivity(intent);

            }
        });

        logoutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(view.getContext(), "Logged out", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                requireActivity().finish();
            }
        });

        return view;
    }

    private void uploadImg(Uri img) {

        progressDialog.setTitle("Uploading Image");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        String imageName = "image_" + System.currentTimeMillis() + ".jpg";
        StorageReference imageRef = storageRef.child(imageName);
        Uri imgUri = img;

        imageRef.putFile(imgUri)
                .addOnSuccessListener(taskSnapshot -> {
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        String userId = mAuth.getCurrentUser().getUid();
                        DocumentReference userref = db.collection("users").document(userId);
                        userref.update("profilepic", imageUrl);
                        Picasso.get().load(imageUrl).into(userimage);
                        Toast.makeText(getActivity(), "Profile Picture Added successfully", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }).addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Failed to retrieve the image URL", Toast.LENGTH_SHORT).show();
                    });
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Failed to upload the image", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageuri = data.getData();
            progressDialog.show();
            uploadImg(imageuri);
        }
    }
}
