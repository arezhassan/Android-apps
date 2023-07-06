package com.example.luxuria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firestore.v1.StructuredQuery;

import java.util.ArrayList;

public class orders extends AppCompatActivity {
    RecyclerView rvorders;
    ArrayList<OrderData> orders;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        mAuth=FirebaseAuth.getInstance();
        String userId=mAuth.getCurrentUser().getUid();
        db=FirebaseFirestore.getInstance();
    rvorders=findViewById(R.id.rvorders);
        rvorders.setLayoutManager(new LinearLayoutManager(this));


        db.collection("orders")
                .document(userId)
                .collection("items")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    orders = new ArrayList<>();

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String productName = documentSnapshot.getString("orderProducts");
                        String bill = documentSnapshot.getString("bill");
                        String orderId = documentSnapshot.getString("orderId");
                        String orderStatus = documentSnapshot.getString("orderStatus");
                        // Create an OrderData object and add it to the list
                        OrderData orderData = new OrderData(productName, bill, orderId, orderStatus);
                        orders.add(orderData);
                    }
ordersAdapter adapter = new ordersAdapter(orders);
                    rvorders.setAdapter(adapter);

                    // Initialize and set up your RecyclerView and adapter here
                    rvorders = findViewById(R.id.rvorders);
                    // Set up your RecyclerView adapter and layout manager
                    // ...

                });
    }
}










