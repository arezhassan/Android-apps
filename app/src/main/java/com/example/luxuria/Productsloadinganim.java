package com.example.luxuria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
// Declaring the animation view





public class Productsloadinganim extends AppCompatActivity {
    LottieAnimationView animation_view;
    protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_productsloadinganim);
        init();
        CommonUtils.makeFullscreen(Productsloadinganim.this);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 3s = 3000ms
                Intent i= new Intent (Productsloadinganim.this,MainActivity.class);
                startActivity(i);
                finish();
                // Declaring the animation view

                animation_view
                        .playAnimation();
            }


        }, 3000);

}
    private void init(){
        animation_view  = findViewById(R.id.confirmorderanim);
    }


}
