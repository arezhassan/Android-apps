package com.example.luxuria;




import static com.example.luxuria.ProductAdapter.itemCount;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ProductAdapterforTwo extends RecyclerView.Adapter<ProductAdapterforTwo.HolderforTwo> {

    ArrayList<productdata> productss;
    private FirebaseAuth mAuth;
    static String url;



    public ProductAdapterforTwo(ArrayList<productdata> data)
    {
        productss = data;
    }

    @NonNull
    @Override
    public HolderforTwo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View v = li.inflate(R.layout.twoproductcard, parent, false);

        return new HolderforTwo(v);
    }


    @Override
    public void onBindViewHolder(@NonNull HolderforTwo holder, int position) {
        holder.productname.setText(productss.get(position).getProductname());
        holder.productval.setText(productss.get(position).getProductprice());
        holder.cartphoto.setImageResource(R.drawable.carticonblack);
        Picasso.get()
                .load(productss.get(position).getImg())
                .fit()
                .placeholder(R.drawable.purpleloading) // Optional: Set a placeholder image
                .error(R.drawable.error) // Optional: Set an error image
                .into(holder.productphoto);

        url=productss.get(position).getProductphoto();






    }

    @Override
    public int getItemCount() {
        return productss.size();
    }


    public class HolderforTwo extends RecyclerView.ViewHolder
    {
        ImageView cartphoto,productphoto;

        TextView productname, productval;


        public HolderforTwo(@NonNull View itemView) {
            super(itemView);
            productname = itemView.findViewById(R.id.productname);
            productval = itemView.findViewById(R.id.productval);
            cartphoto = itemView.findViewById(R.id.cartphoto);
            productphoto = itemView.findViewById(R.id.productphoto);
            mAuth=FirebaseAuth.getInstance();
            cartphoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemCount++;
                    MainActivity.updateFabCounter(itemCount);

                   /* count++;
                    Intent i=new Intent (view.getContext(), CartFragment.class);
                    i.putExtra("count",count);
                    view.getContext().startActivity(i);*/
                    String productName = productname.getText().toString();
                    String productId = "1";
                    String productPrice = productval.getText().toString();
                    String productPhoto = url;
                    String userid= mAuth.getCurrentUser().getUid();

// Create a new cart item
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
                            .document(userId+itemCount)
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


        }





        }

    }



