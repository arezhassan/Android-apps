package com.example.luxuria;

import static com.example.luxuria.ProductAdapter.itemCount;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    CardView checkoutbtn,clearcart;
    public static RecyclerView cart_recycler_view;
    public static List<CartData> cartItemList;
    public  String productName;
    public  String productPrice;
    public  String productPhoto;
    public  String productId;
    int cartcount;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // Find and initialize views
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        clearcart=view.findViewById(R.id.clearcart);
        cart_recycler_view=view.findViewById(R.id.cart_recycler_view);
        checkoutbtn=view.findViewById(R.id.checkoutbtn);
        cart_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_animation);
        cart_recycler_view.setAnimation(animation);







        //Retreive Cart Info

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("cart")
                .document(userId)
                .collection("items")
                .get()
                .addOnCompleteListener(task -> {
                    cartItemList = new ArrayList<>();
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            cartcount = querySnapshot.size();
                            Log.d("CartFragment", "Number of items retrieved: " + cartcount);


                            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                // Retrieve the data from each DocumentSnapshot
                                 productName = document.getString("productName");
                                 productId = document.getString("productId");
                                 productPrice = document.getString("productPrice");
                                 productPhoto = document.getString("productPhoto");

                                // Create a CartItem object with the retrieved data
                                CartData cartItem = new CartData(productName, productId, productPrice, productPhoto);

                                // Add the CartItem to the list
                                cartItemList.add(cartItem);

                            }

                            CartAdapter c = new CartAdapter(cartItemList);
                            cart_recycler_view.setAdapter(c);
                            cart_recycler_view.startAnimation(animation);
                            c.notifyDataSetChanged();
                            clearcart.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(cartcount>0){
                                        clearCart();
                                        Toast.makeText(getActivity(), "Cart Cleared", Toast.LENGTH_SHORT).show();
                                        cart_recycler_view.setAdapter(null);
                                        c.notifyItemRemoved(1);
                                    }else{
                                        Toast.makeText(getActivity(), "Cart is Already Empty!", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });

                            // Notify the adapter of the data change


                        } else {
                            Toast.makeText(view.getContext(), "No Data", Toast.LENGTH_SHORT).show();

                            // Cart is empty
                            // Handle the case accordingly
                        }
                    } else {
                        Toast.makeText(view.getContext(), "Error", Toast.LENGTH_SHORT).show();

                        // Error occurred while fetching data
                        // Handle the error
                    }
                });
checkoutbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Vibrate with vibration effect (Android Oreo and above)
                VibrationEffect effect = VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE);
                vibrator.vibrate(effect);
            } else {
                // Vibrate for a fixed duration (prior to Android Oreo)
                vibrator.vibrate(100);
            }
        }








        if(itemCount>0){
        Intent i=new Intent (getActivity(),orderconfirmanim.class);
        startActivity(i);
    }else{
        Toast.makeText(getActivity(), "Cart is Empty", Toast.LENGTH_SHORT).show();

        }

    }
});


                // Set product information


        return view;
    }
    private void clearCart() {
        Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Vibrate with vibration effect (Android Oreo and above)
                VibrationEffect effect = VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE);
                vibrator.vibrate(effect);
            } else {
                // Vibrate for a fixed duration (prior to Android Oreo)
                vibrator.vibrate(100);
            }
        }
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
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



}
