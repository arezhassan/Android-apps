package com.example.luxuria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class viewusers extends AppCompatActivity {
    WebView viewusers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewusers);
        viewusers = findViewById(R.id.viewuser);
        viewusers.loadUrl("https://console.firebase.google.com/");


    }
}