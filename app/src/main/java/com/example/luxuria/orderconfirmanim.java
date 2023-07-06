package com.example.luxuria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class orderconfirmanim extends AppCompatActivity {
    LottieAnimationView confirmanim;
    Animation anim;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderconfirmanim);

        init();

        CommonUtils.makeFullscreen(com.example.luxuria.orderconfirmanim.this);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 3s = 3000ms
                Intent i= new Intent (com.example.luxuria.orderconfirmanim.this,checkout.class);
                startActivity(i);
                finish();
                // Declaring the animation view


            }


        }, 3000);

    }
    private void init(){
        confirmanim  = findViewById(R.id.confirmorderanim);
        logo = findViewById(R.id.logo);


    }





}