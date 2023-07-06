package com.example.luxuria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Random;

public class AddProducts extends AppCompatActivity {
    EditText adminproductname, adminproductprice, adminproductid;
    Button addproduct, uploadpic;
    Uri imageuri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);
        init();

        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adminproductname.getText().toString().trim().isEmpty() || adminproductprice.getText().toString().trim().isEmpty()) {
                    Toast.makeText(AddProducts.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    String prname = adminproductname.getText().toString().trim();
                    String prprice = adminproductprice.getText().toString().trim();

                    progressDialog = new ProgressDialog(AddProducts.this);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setTitle("Uploading Product Details...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    // Create a storage reference
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

                    // Create a child reference with a unique name (e.g., using timestamp)
                    String imageName = "image_" + System.currentTimeMillis() + ".jpg";
                    StorageReference imageRef = storageRef.child(imageName);

                    // Get the image file URI (replace with your own method to get the file URI)
                    Uri imgUri = imageuri;

                    // Upload the file to Firebase Storage
                    imageRef.putFile(imgUri)
                            .addOnProgressListener(taskSnapshot -> {
                                // Calculate the progress percentage
                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                // Update the progress dialog
                                progressDialog.setProgress((int) progress);
                            })
                            .addOnSuccessListener(taskSnapshot -> {
                                // Image uploaded successfully
                                // Get the image URL
                                imageRef.getDownloadUrl()
                                        .addOnSuccessListener(uri -> {
                                            // Image URL retrieved successfully
                                            String imageUrl = uri.toString();
                                            // Use the image URL as needed
                                            int pid = 1;
                                            productdata data = new productdata(prname, prprice, imageUrl, pid);
                                            Toast.makeText(AddProducts.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                                            saveProduct(data);

                                            // Dismiss the progress dialog
                                            progressDialog.dismiss();
                                        })
                                        .addOnFailureListener(e -> {
                                            // Failed to retrieve the image URL
                                            // Handle the error
                                            progressDialog.dismiss();
                                        });
                            })
                            .addOnFailureListener(e -> {
                                // Failed to upload the image
                                // Handle the error
                                progressDialog.dismiss();
                            });
                }
            }
        });

        uploadpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Get the selected image URI
            Uri imageUri = data.getData();

            // Set the selected image to the ImageView
            imageuri = data.getData();
        }
    }

    private void init() {
        adminproductname = findViewById(R.id.adminproductname);
        adminproductprice = findViewById(R.id.adminproductprice);
        adminproductid = findViewById(R.id.adminproductid);
        addproduct = findViewById(R.id.btnAddProduct);
        uploadpic = findViewById(R.id.uploadpic);
    }

    private void saveProduct(productdata data) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user document with a unique ID
        DocumentReference productref = db.collection("products").document();

        // Create a User object with the provided data
        // Set the user object in the document

        productref.set(data)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(
                            this,
                            "Product data saved",
                            Toast.LENGTH_SHORT
                    ).show();
                })
                .addOnFailureListener(e -> {
                    // Failed to save user data
                    Toast.makeText(
                            this,
                            "Failed to save user data",
                            Toast.LENGTH_SHORT
                    ).show();
                });
    }
}
