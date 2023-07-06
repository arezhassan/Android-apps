package com.example.luxuria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminHome extends AppCompatActivity {

    private Button btnAddDeleteProducts;
    private Button btnViewUsers;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        btnAddDeleteProducts = findViewById(R.id.btnAddDeleteProducts);
        btnViewUsers = findViewById(R.id.btnViewUsers);
        btnLogout = findViewById(R.id.btnLogout);

        btnAddDeleteProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),AddProducts.class);
                startActivity(i);
                // Handle add/delete products button click
                // Add your code here
            }
        });

        btnViewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

Intent i=new Intent(getApplicationContext(),viewusers.class);
                startActivity(i);

                // Handle view users button click
                // Add your code here
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i=new Intent(getApplicationContext(),LoginActivity.class);
              startActivity(i);
              finish();
            }
        });
    }
}
