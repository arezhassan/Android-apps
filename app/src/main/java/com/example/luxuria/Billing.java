package com.example.luxuria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Billing extends AppCompatActivity {
    LottieAnimationView btncompleteorder;
    EditText etphone;
    TextView totalbill, prname, orderId, shippingdetails, paymentmode;
    ArrayList<Bills> data;
    FirebaseAuth mAuth;
    String result;
    String order_Id;
    StringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        init();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
       order_Id = generateOrderNumber(userId);
        db.collection("cart")
                .document(userId)
                .collection("items")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    data = new ArrayList<>();

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String productprice = documentSnapshot.getString("productPrice");
                        String productname = documentSnapshot.getString("productName");

                        data.add(new Bills(productname, productprice));
                    }
                    ArrayList<String> productNames = new ArrayList<>();
                    for (Bills bill : data) {
                        String productName = bill.getProductName();
                        productNames.add(productName);
                    }

// Concatenate the product names into a single string
                 sb = new StringBuilder();
                    for (String productName : productNames) {
                        sb.append(productName).append("\n");
                    }

                    String allProductNames = sb.toString();
                    prname.setText(allProductNames);


                    BigDecimal totalPrice = BigDecimal.ZERO;
                    BigDecimal sum = BigDecimal.ZERO;
                    for (Bills bill : data) {
                        totalPrice = new BigDecimal(String.valueOf(bill.getProductPrice()));
                        sum = sum.add(totalPrice);
                    }


                    String totalPriceString = sum.toString();
                   result=totalPriceString.substring(2);
                    totalbill.setText("Rs " + result);





                    orderId.setText(order_Id);
                });

        db.collection("shippingAddress").document(userId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Extract paymentType and address fields
                                String paymentType = document.getString("paymentType");
                                String address = document.getString("address");
                                // Use paymentType and address variables as needed
                                shippingdetails.setText(address);
                                paymentmode.setText(paymentType);


                            } else {
                                Toast.makeText(getApplicationContext(), "Shipping address not found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        btncompleteorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Map<String, Object> DataClass = new HashMap<>();
                DataClass.put("orderId",order_Id);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                long currenttime = System.currentTimeMillis();
                String currentTime = String.valueOf(currenttime);
                String productName=sb.toString();

                DataClass.put("orderDate",currentTime);
                DataClass.put("bill",result);
                DataClass.put("orderStatus","Pending");
                DataClass.put("orderId",order_Id);
                DataClass.put("orderProducts",productName);
                DataClass.put("contact",etphone.getText().toString());






                db.collection("orders") //document
                        .document(userId) //User reference
                        .collection("items")
                        .document(order_Id)
                        .set(DataClass) //add new data object

                        .addOnSuccessListener(aVoid -> {
                            // Cart item added successfully
                            Toast.makeText(Billing.this, "Order Placed  ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Billing.this, orderSummary.class);
                        intent.putExtra("order_Id",order_Id);
                          startActivity(intent);
                           finish();
                            // Handle the success case (e.g., show a success message)
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(Billing.this, "Order Place Failed", Toast.LENGTH_SHORT).show();

                            // Error occurred while adding cart item
                            // Handle the failure (e.g., show an error message)
                        });

                DocumentReference userRef = db.collection("users").document(userId);

                userRef.update("orderNumber", order_Id)
                        .addOnSuccessListener(aVoid -> {
                            // Order number successfully assigned to the user
                        })
                        .addOnFailureListener(e -> {
                            // Failed to assign order number to the user
                        });




            }
        });



    }
    private String generateOrderNumber(String userId) {
        // Get the current timestamp
        String timestamp = String.valueOf(System.currentTimeMillis());

        // Extract the first 5 characters from the userId
        String userIdPrefix = userId.substring(0, 5);

        // Combine the userId prefix and timestamp to create the order number
        String orderNumber = userIdPrefix + timestamp;

        return orderNumber;
    }

    private void init() {
        btncompleteorder = findViewById(R.id.btncompleteorder);
        etphone = findViewById(R.id.etphone);
        totalbill = findViewById(R.id.totalbill);
        prname = findViewById(R.id.prname);
        orderId = findViewById(R.id.orderId);
        shippingdetails = findViewById(R.id.shipdetails);
        paymentmode = findViewById(R.id.paymentmode);
    }
}
