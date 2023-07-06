package com.example.luxuria;

import static com.example.luxuria.ProductAdapter.itemCount;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartData> cartItemList;

    public CartAdapter(List<CartData> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartData cartItem = cartItemList.get(position);
        // Bind the data to the views in the CartViewHolder
        holder.productNameTextView.setText("Product Name: " + cartItem.getProductName());
        holder.productIdTextView.setText("Product SKU " + cartItem.getProductId());
        holder.productPriceTextView.setText("Price: " + String.valueOf(cartItem.getProductPrice()));
        // Set the product image using Picasso
        Picasso.get()
                .load(cartItem.getProductPhoto()) // Use the product photo URL from the cart item
                .resize(150, 135)
                .placeholder(R.drawable.purpleloading) // Optional: Set a placeholder image
                .error(R.drawable.error) // Optional: Set an error image
                .into(holder.productImageView);
        // ...
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView productNameTextView;
        private TextView productIdTextView;
        private TextView productPriceTextView;
        private ImageView productImageView;
        private ImageView deleteicon;

        public CartViewHolder(View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.product_name_textview);
            productIdTextView = itemView.findViewById(R.id.product_id_textview);
            productPriceTextView = itemView.findViewById(R.id.product_price_textview);
            productImageView = itemView.findViewById(R.id.product_imageview);
            deleteicon = itemView.findViewById(R.id.deleteicon);
            deleteicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userId = FirebaseAuth.getInstance().getUid();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    int position = getAdapterPosition();
                    String itemId = userId + String.valueOf(position); // Replace with the actual item ID

                    // Build the reference to the item document
                    DocumentReference itemRef = db.collection("cart")
                            .document(userId)
                            .collection("items")
                            .document(itemId);

                    // Delete the item document
                    itemRef.delete()
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(view.getContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();

                                CartFragment.cartItemList.remove(position);
                                itemCount--;
                                MainActivity.updateFabCounter(itemCount);

                                notifyDataSetChanged();

                                // Document successfully deleted
                                // Perform any additional actions if needed
                            })
                            .addOnFailureListener(e -> {
                                // An error occurred while deleting the document
                                // Handle the error appropriately
                            });
                } // <-- Closing bracket for onClick method
            }); // <-- Closing bracket for setOnClickListener method
        } // <-- Closing bracket for CartViewHolder constructor

    } // <-- Closing bracket for CartViewHolder class

} // <-- Closing bracket for CartAdapter class
