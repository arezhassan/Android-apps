package com.example.luxuria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ordersAdapter extends RecyclerView.Adapter<ordersAdapter.Holder> {
    ArrayList<OrderData> orders;
    public ordersAdapter(ArrayList<OrderData> data) {
        this.orders=data;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View v = li.inflate(R.layout.single_order_item, parent, false);

        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ordersAdapter.Holder holder, int position) {
        holder.order_bill_text.setText("Bill "+orders.get(position).getPrice());
        holder.order_id_text.setText("Order ID: " +orders.get(position).getOrderId());
        holder.order_status_text.setText("Order Status: " +orders.get(position).getOrderStatus());
        holder.product_names_text.setText("Products: " +orders.get(position).getProductName());



    }



    @Override
    public int getItemCount() {
       return orders.size();
    }
    public static class Holder extends RecyclerView.ViewHolder {
        TextView order_bill_text;
        TextView order_id_text;
        TextView order_status_text;
        TextView product_names_text;




        public Holder(@NonNull View itemView) {
            super(itemView);
            order_bill_text = itemView.findViewById(R.id.order_bill_text);
            order_id_text = itemView.findViewById(R.id.order_id_text);
            order_status_text = itemView.findViewById(R.id.order_status_text);
            product_names_text = itemView.findViewById(R.id.product_names_text);





        }
    }
}
