package com.example.luxuria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class AdminLogin extends AppCompatActivity {
ImageView logo;
EditText adminem,adminpass;
LottieAnimationView adminloginbtn;
TextView notadmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        init();
        adminloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em=adminem.getText().toString();
                String pass=adminpass.getText().toString();
                if(em.equals("arez") && pass.equals("12341234")){
                    Toast.makeText(getApplicationContext(), "Admin Authorized", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent (getApplicationContext(),AdminHome.class);
                    startActivity(i);

                }else{
                    Toast.makeText(getApplicationContext(), "Admin not Authorized", Toast.LENGTH_SHORT).show();
                }
            }
        });
        notadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i=new Intent (getApplicationContext(),LoginActivity.class);
               startActivity(i);
               finish();
            }
        });



    }
    private void init(){
        logo=findViewById(R.id.logo);
        notadmin=findViewById(R.id.notadmin);
        adminem=findViewById(R.id.adminem);
        adminpass=findViewById(R.id.adminpass);
        adminloginbtn=findViewById(R.id.adminloginbtn);
    }
}