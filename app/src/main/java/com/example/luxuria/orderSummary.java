package com.example.luxuria;

import static com.example.luxuria.ProductAdapter.itemCount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class orderSummary extends AppCompatActivity {
    TextView orderId, totalbills, orderStatus, orderDate;
    CardView shopmorebtn;
    String orderid1, totalbills1, orderStatus1, orderDate1;
    FirebaseAuth mAuth;
    TextView tvThanks;

    String ordernum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        mAuth = FirebaseAuth.getInstance();
        init();
        String userId = mAuth.getUid();
        Intent a=getIntent();
        ordernum=a.getStringExtra("order_Id");
        tvThanks.setText("Thankyou, " + mAuth.getCurrentUser().getDisplayName() +"!");





        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("orders")
                .document(userId)
                .collection("items")
                .document(ordernum)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        orderid1 = documentSnapshot.getString("orderId");
                        totalbills1 = documentSnapshot.getString("bill");
                        orderDate1 = documentSnapshot.getString("orderDate");
                        long currentTime = Long.parseLong(orderDate1);
                        Date date = new Date(currentTime);

                        SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
                        String formattedDate = sdf.format(date);
                        orderStatus1 = documentSnapshot.getString("orderStatus");
                        // Create a Product object and add it to the list
                        orderDate.setText("Order Date: " +formattedDate);
                        totalbills.setText("Total Bill: " +totalbills1);
                        orderStatus.setText("Order Status: "+ orderStatus1);
                    } else {
                        // Handle document not found
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                });

        orderId.setText("Order Id: "+ ordernum);
shopmorebtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i = new Intent(orderSummary.this, Productsloadinganim.class);
        startActivity(i);
        finish();




        String userId = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        // Build the reference to the item document
        itemCount=0;
        MainActivity.updateFabCounter(itemCount);

        db.collection("cart")
                .document(userId)
                .collection("items")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    // Delete all cart items
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        document.getReference().delete();
                    }
                    // Handle the success case (e.g., show a success message)
                })
                .addOnFailureListener(e -> {
                    // Error occurred while clearing cart
                    // Handle the failure (e.g., show an error message)
                });
    }
});
    }
    private String generateOrderNumber(long currentTime, String userId) {
        String timestamp = String.valueOf(currentTime);
        String userIdPrefix = userId.substring(0, 5);
        String orderNumber = userIdPrefix + timestamp;
        return orderNumber;
    }


    private void init() {
        orderId = findViewById(R.id.OrderId);
        totalbills = findViewById(R.id.totalbills);
        orderDate = findViewById(R.id.orderDate);
        orderStatus = findViewById(R.id.orderStatus);
        shopmorebtn = findViewById(R.id.shopmorebtn);
        tvThanks = findViewById(R.id.tvThanks);
    }
}
