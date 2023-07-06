package com.example.luxuria;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Register extends AppCompatActivity {
    ImageView imglogo1;
    TextView tvtop;
    Uri imageuri;
    EditText etname,etemail1,etpass1,etpass2;
    Button registerbtn;
    CardView cardv;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    ProgressBar progressbar;
    LottieAnimationView animation_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CommonUtils.makeFullscreen(this);
        mAuth=FirebaseAuth.getInstance();
        initViews();
        Intent a=getIntent();


        animation_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar.setVisibility(View.VISIBLE);
                CommonUtils.buttonsound(Register.this);
                String name=etname.getText().toString().trim();
                String email=etemail1.getText().toString().trim();
                String pass=etpass1.getText().toString().trim();
                String pass2=etpass2.getText().toString().trim();
                if(pass.equals(pass2) && pass.length()>=8){

                    Intent i=new Intent(Register.this,signinanimation.class);
                    mAuth.createUserWithEmailAndPassword(email, pass)

                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        saveUserDataWithImage(name,email, String.valueOf(imageuri));



                                        progressbar.setVisibility(View.GONE);
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(etname.getText().toString()) // Set the display name
                                                .setPhotoUri(imageuri)
                                                .build();
                                        assert user != null;
                                        user.updateProfile(profileUpdates);

                                        Toast.makeText(Register.this, "Registered", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(Register.this, "Registration failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                    i.putExtra("email",email);
                    i.putExtra("pass",pass);
                    i.putExtra("name",name);
                    startActivity(i);
                    finish();

                }


                else if(pass.length()<8) {
                    Toast.makeText(Register.this, "Password should be atleast 8 digits long.", Toast.LENGTH_SHORT).show();
                    etpass1.setText("");
                    etpass2.setText("");
                }else if(!pass.equals(pass2)) {
                    Toast.makeText(Register.this, "ERROR! Passwords do not match!", Toast.LENGTH_SHORT).show();
                    etpass1.setText("");
                    etpass2.setText("");
                }
            }
        });



    }

    private void initViews() {
        imglogo1=findViewById(R.id.imglogo1);
        tvtop=findViewById(R.id.tvtop);
        etname=findViewById(R.id.etname);
        etemail1=findViewById(R.id.etemail1);
        etpass1=findViewById(R.id.etpass1);
        etpass2=findViewById(R.id.etpass2);
        animation_view=findViewById(R.id.confirmorderanim);
        cardv=findViewById(R.id.cardv);
        progressbar=findViewById(R.id.progressbar);
    }
    private void saveUserDataWithImage(String name, String email, String imageUrl) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String user=mAuth.getCurrentUser().getUid();

        // Create a new user document with a unique ID
        DocumentReference userRef = db.collection("users").document(user);




        // Create a User object with the provided data
        // Set the user object in the document
        dataholder userd = new dataholder(name,email,mAuth.getUid(),imageuri);



        userRef.set(userd)
                .addOnSuccessListener(aVoid -> {
                    // Document successfully created
                })
                .addOnFailureListener(e -> {
                    // Handle any errors that occurred during document creation
                });

        userRef.set(userd)
                .addOnSuccessListener(aVoid -> {
                   Toast.makeText(
                           Register.this,
                           "User data saved",
                           Toast.LENGTH_SHORT
                   ).show();
                })
                .addOnFailureListener(e -> {
                    // Failed to save user data
                    Toast.makeText(
                            Register.this,
                            "Failed to save user data",
                            Toast.LENGTH_SHORT
                    ).show();
                });
    }
}