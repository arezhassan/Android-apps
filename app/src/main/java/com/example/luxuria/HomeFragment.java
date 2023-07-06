package com.example.luxuria;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luxuria.ProductAdapter;
import com.example.luxuria.productdata;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private EditText searchEditText;
    RecyclerView rv;

    LinearLayout layouttype;
    ImageView cartphoto;

    LinearLayoutManager llm;
    GridLayoutManager glm;
    ArrayList<productdata> data;

    TextView tophead;
    ImageView listview, gridview;
    ArrayList<productdata> searchdataa;
    Animation textAnimation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        rv = view.findViewById(R.id.rv);
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_animation);
        rv.setAnimation(animation);
         textAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_from_top);
        ImageView searchIcon = view.findViewById(R.id.searchicon);
        SearchFragment searchFragment = new SearchFragment();



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("products")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    data = new ArrayList<>();

                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String productName = documentSnapshot.getString("productname");
                        String productprice = documentSnapshot.getString("productprice");
                        String img = documentSnapshot.getString("img");

                        productdata product = new productdata(productName, productprice, img, 1);
                        data.add(product);
                    }

                    ProductAdapter ca = new ProductAdapter(data);
                    ProductAdapterforTwo ca2 = new ProductAdapterforTwo(data);
                    rv.setAdapter(ca);
                    rv.startAnimation(animation);
                    gridview.setOnClickListener(view1 -> {
                        rv.setLayoutManager(glm);
                        rv.setAdapter(ca2);
                    });

                    listview.setOnClickListener(view12 -> {
                        rv.setLayoutManager(llm);
                        rv.setAdapter(ca);
                       

                    });
                })
                .addOnFailureListener(e -> {
                    // Handle any errors that occurred while retrieving the data
                });

        layouttype = view.findViewById(R.id.layouttype);
        //tophead = view.findViewById(R.id.tophead);
        listview = view.findViewById(R.id.listview);
        gridview = view.findViewById(R.id.gridview);
      //  tophead.setAnimation(textAnimation);
      //  tophead.startAnimation(textAnimation);

        llm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        glm = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        rv.setLayoutManager(llm);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = searchFragment;
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, frag)
                        .commit();
            }
        });






        return view;
    }





}
