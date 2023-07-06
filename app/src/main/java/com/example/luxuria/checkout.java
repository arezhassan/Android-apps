package com.example.luxuria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class checkout extends AppCompatActivity {
    LottieAnimationView orderconfirmbtn;
    RadioGroup radioPayment;
    RadioButton radiocreditcard, radiocash;
    EditText etaddress;

    String paymentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        init();

        orderconfirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address=etaddress.getText().toString();
if(address.isEmpty()){
    Toast.makeText(checkout.this, "Please enter your address", Toast.LENGTH_SHORT).show();
    etaddress.setText("");
    etaddress.setError("Address cannot be empty!");
}

                if(radiocash.isChecked()){

                    paymentType="CASH";
                }

                else if(radiocreditcard.isChecked()){
                    paymentType="CREDIT/DEBIT";
                }
                // Add the cart item to Firebase Firestore


// Add the cart item to Firebase Firestore
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Map<String, Object> DataClass = new HashMap<>();
                DataClass.put("address",address);
                DataClass.put("paymentType",paymentType);


                db.collection("shippingAddress") //document
                        .document(userId) //User reference
                        .set(DataClass) //add new data object
                        .addOnSuccessListener(aVoid -> {
                            // Cart item added successfully
                            Toast.makeText(checkout.this, "Shipping Address saved", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(checkout.this,Billing.class));
                            finish();
                            // Handle the success case (e.g., show a success message)
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(checkout.this, "Shipping Address Failed", Toast.LENGTH_SHORT).show();

                            // Error occurred while adding cart item
                            // Handle the failure (e.g., show an error message)
                        });

            }}
        );


    }
    private void init(){
        orderconfirmbtn=findViewById(R.id.btnconfirmorder);
        radioPayment=findViewById(R.id.radioPayment);
        radiocreditcard=findViewById(R.id.radiocreditcard);
        radiocash=findViewById(R.id.radiocash);
        etaddress=findViewById(R.id.etphone);

    }
}