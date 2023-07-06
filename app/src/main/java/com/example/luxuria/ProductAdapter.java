package com.example.luxuria;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;





import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.Cache;
import okhttp3.OkHttpClient;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Holder> {

        static ArrayList<productdata> productss;
        static  String URL;
        static int index=0;
    public static int itemCount = 0;
    File cacheDir ;

Context context;
Picasso picasso;


        public ProductAdapter(ArrayList<productdata> data) {

            productss = data;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            context=parent.getContext();
            Picasso picasso = new Picasso.Builder(context)
                    .build();
         cacheDir = new File(context.getCacheDir(), "picasso-cache");
            LayoutInflater li = LayoutInflater.from(parent.getContext());
            View v = li.inflate(R.layout.single_product, parent, false);

            return new Holder(v);
        }


        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            index=holder.getAdapterPosition();
            holder.productname.setText(productss.get(position).getProductname());

            holder.productval.setText(productss.get(position).getProductprice());

            // Glide image loading code
            int cacheSize = 20 * 1024 * 1024; // 10 MiB
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cache(new Cache(cacheDir, cacheSize))
                    .build();
            picasso = new Picasso.Builder(context)
                    .downloader(new OkHttp3Downloader(okHttpClient))
                    .build();



            Picasso
           .get()
                    .load(productss.get(position).getImg())
                    .fit()
                    .placeholder(R.drawable.purpleloading) // Optional: Set a placeholder image
                    .error(R.drawable.error) // Optional: Set an error image
                    .into(holder.productphoto);

            URL=productss.get(position).getProductphoto();
            holder.cartphoto.setImageResource(R.drawable.carticonblack);



        }

        @Override
        public int getItemCount() {
            return productss.size();
        }



        public static class Holder extends RecyclerView.ViewHolder {
            ImageView cartphoto, productphoto;

            TextView productname, productval;
            TextView viewdetail;

            FirebaseAuth mAuth;

            public Holder(@NonNull View itemView) {
                super(itemView);
                mAuth = FirebaseAuth.getInstance();
                productname = itemView.findViewById(R.id.productname);
                viewdetail = itemView.findViewById(R.id.viewdetail);
                productval = itemView.findViewById(R.id.productval);
                cartphoto = itemView.findViewById(R.id.cartphoto);
                productphoto = itemView.findViewById(R.id.productphoto);
                cartphoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemCount++;
                        MainActivity.updateFabCounter(itemCount);

                        String productName = productname.getText().toString();
                        String productId = UUID.randomUUID().toString();
                        String productPrice = productval.getText().toString();
                        String productPhoto =URL;

// Create a new cart item


// Add the cart item to Firebase Firestore
                        CartData cartItems = new CartData(productName, productId, productPrice, productPhoto);

// Add the cart item to Firebase Firestore
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        String cartItemId = UUID.randomUUID().toString();

                        Map<String, Object> cartItem = new HashMap<>();
                        cartItem.put("productName", productName);
                        cartItem.put("productId", productId);
                        cartItem.put("productPrice", productPrice);
                        cartItem.put("productPhoto", productPhoto);

                        db.collection("cart")
                                .document(userId)
                                .collection("items")
                                .document(userId+ itemCount)
                                .set(cartItem)
                                .addOnSuccessListener(aVoid -> {
                                    // Cart item added successfully
                                    Toast.makeText(view.getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
                                    // Handle the success case (e.g., show a success message)
                                })
                                .addOnFailureListener(e -> {
                                    // Error occurred while adding cart item
                                    // Handle the failure (e.g., show an error message)
                                });





                    }
                });
               viewdetail.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       FragmentManager fragmentManager = ((AppCompatActivity) itemView.getContext()).getSupportFragmentManager();
                       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                       fragmentTransaction.replace(R.id.fragmentContainer, new productDetails());
                       fragmentTransaction.addToBackStack(null);
                       fragmentTransaction.commit();
                   }
               });



            }

        }

    }

