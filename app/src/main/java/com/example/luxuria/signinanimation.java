package com.example.luxuria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;

import com.airbnb.lottie.LottieAnimationView;
import com.example.luxuria.CommonUtils;

public class signinanimation extends AppCompatActivity {
    LottieAnimationView animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signinanimation);
        CommonUtils.makeFullscreen(this);
        animation=findViewById(R.id.animation);
        animation
                .playAnimation();
        final Handler handler = new Handler();
        Intent a=getIntent();
        String email=a.getStringExtra("email");
        String password=a.getStringExtra("pass");
        String name=a.getStringExtra("name");

        Intent i=new Intent(signinanimation.this,MainActivity.class);
        i.putExtra("email",email);
        i.putExtra("pass",password);
        i.putExtra("name",name);



        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms

                startActivity(i);
                finish();
            }
        }, 3000);
    }
}