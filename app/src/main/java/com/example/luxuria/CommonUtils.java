package com.example.luxuria;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CommonUtils {
    String u;
            String p;
            CommonUtils(){
                this.u="arezimran@gmail.com";
                this.p="13022002arez";
            }
            String getu(){
                return u;
            }
            String getp(){
                return p;
            }

        public static void showToast(Context context, String message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }


        public static void logMessage(String tag, String message) {
            Log.d(tag, message);
        }

    public static void makeFullscreen(Activity a) {
        // Hide the status bar and make the app fullscreen
        a.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hide the navigation bar if present (requires API level 19 or above)
        View decorView = a.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public static void buttonsound(Activity a){
  //  MediaPlayer btnsound = MediaPlayer.create(a, R.raw.click);
  //  btnsound.start();
    }
    public static void appsound(Activity a){
        //MediaPlayer appsound = MediaPlayer.create(a, R.raw.appintro);
       // appsound.start();
    }



}
