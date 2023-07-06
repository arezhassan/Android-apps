package com.example.luxuria;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;


public class productDetails extends Fragment {

    View view;
    ImageView arrowimg, img1,img2,img3,mainimg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_product_details, container, false);
        arrowimg=view.findViewById(R.id.arrowimg);
        img1=view.findViewById(R.id.img1);
        img2=view.findViewById(R.id.img2);
        img3=view.findViewById(R.id.img3);
        mainimg=view.findViewById(R.id.mainimg);
        arrowimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment())
                        .commit();
            }
        });
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainimg.setImageResource(R.drawable.s22);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainimg.setImageResource(R.drawable.ip11);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainimg.setImageResource(R.drawable.note20);
            }
        });



        return view;

    }
}