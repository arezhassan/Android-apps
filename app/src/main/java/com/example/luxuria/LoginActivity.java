package com.example.luxuria;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button tvreg, adminlogin;
    LottieAnimationView loginbtn;
    EditText etemail, etpass;
    ImageView logoimage;
    TextView tvForgotPassword;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    private FirebaseAuth mAuth;
    SignInButton signInButton;

    CommonUtils commonUtils;
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        init();
        gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);
        super.onCreate(savedInstanceState);

// ...
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        CommonUtils.makeFullscreen(this);



//Google Sign in Button
      signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();

            }
        });
//Login BUTTON
        loginbtn.setOnClickListener(view -> {

            CommonUtils.buttonsound(this);
            String email = etemail.getText().toString();
            String pass = etpass.getText().toString();
            if (!email.isEmpty()&&pass.length()>=8) {
                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent( LoginActivity.this,MainActivity.class);
                                    startActivity(i);
                                    i.putExtra("flag",0);
                                    finish();


                                    FirebaseUser user = mAuth.getCurrentUser();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    etemail.setError("Recheck your email");
                                    etpass.setError("Recheck your password");

                                }
                            }
                        });


            }
            else {
                etemail.setError("Recheck your email");
                etpass.setError("Recheck your password");
                Toast.makeText(this, "Invalid Credentials. Signup before Logging in!", Toast.LENGTH_SHORT).show();

            }
        });
        tvreg.setOnClickListener(view -> {
            Intent c=new Intent(LoginActivity.this,Register.class);
            startActivity(c);
            Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show();
            finish();
        });
        adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c=new Intent(LoginActivity.this,AdminLogin.class);
                startActivity(c);
                Toast.makeText(getApplicationContext(), "Admin Panel Loading", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etemail.getText().toString().trim();

                if (email.isEmpty()) {
                    etemail.setError("Email is required");
                    etemail.requestFocus();
                } else {
                    // Send password reset email
                    sendPasswordResetEmail(email);
                }
            }
        });
    }
        //Media Completion Listener




    //Google Sign in Function
    private void SignIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.putExtra("flag",1);
                startActivity(i);
                finish();

            } catch (ApiException e) {
                Toast.makeText(this, "Google Sign in Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Views Initializer
    private void init(){
        logoimage =findViewById(R.id.logonobg);
        logoimage.setImageResource(R.drawable.logonobg);
        logoimage.setVisibility(View.VISIBLE);
        loginbtn=findViewById(R.id.logintbn);
        etemail=findViewById(R.id.etemail);
        etpass=findViewById(R.id.etpass);
        tvreg=findViewById(R.id.tvreg);
        adminlogin=findViewById(R.id.adminlogin);
        signInButton=findViewById(R.id.googlesigninbtn);
        tvForgotPassword=findViewById(R.id.tvForgotPassword);
    }
    private void sendPasswordResetEmail(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Password reset email sent successfully
                            Toast.makeText(LoginActivity.this, "Password reset email sent. Check your inbox.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Failed to send password reset email
                            Toast.makeText(LoginActivity.this, "Failed to send password reset email.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}










