package com.example.luxuria;

import static com.example.luxuria.ProductAdapter.itemCount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.andremion.counterfab.CounterFab;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private long lastClickTime = 0;
    private static final long DOUBLE_CLICK_TIME_THRESHOLD = 500; // milliseconds

    CartFragment cartFragment = new CartFragment();
    LogoutFragment logoutFragment = new LogoutFragment();
    HomeFragment homeFragment = new HomeFragment();
    private BottomNavigationView bottomNavigationView;
    FirebaseAuth mAuth;
    GoogleSignInClient gso;
    public static CounterFab cartaction;
  TextView fabCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        onStart();


        Fragment defaultFragment = homeFragment;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, defaultFragment)
                .commit();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        MainActivity.cartaction = findViewById(R.id.fabOne);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        ViewCompat.setOnApplyWindowInsetsListener(bottomNavigationView, new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                v.setPadding(v.getPaddingLeft(), v.getPaddingTop(), v.getPaddingRight(), v.getPaddingBottom() + insets.getSystemWindowInsetBottom());
                return insets;
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        Fragment selectedFragment = null;

                        switch (item.getItemId()) {
                            case R.id.navigation_item1:
                                // Handle item 1 selection
                                selectedFragment = homeFragment;
                                cartaction.setVisibility(View.VISIBLE);
                                break;
                            case R.id.navigation_item3:
                                // Handle item 3 selection
                                cartaction.setVisibility(View.VISIBLE);
                                selectedFragment = logoutFragment;
                                break;
                        }

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainer, selectedFragment)
                                .commit();

                        return true;
                    }
                });

        cartaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastClickTime < DOUBLE_CLICK_TIME_THRESHOLD) {
                    // Double click detected
                    navigateToHomeFragment();
                }
                lastClickTime = currentTime;


                Fragment frag = cartFragment;
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, frag)
                        .commit();
            }
        });


    }
    private void navigateToHomeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Fragment frag = homeFragment;
        // Replace "HomeFragment" with the actual class name of your home fragment
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, frag)
                .commit();
    }
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

    }

    public static void updateFabCounter(int itemCount) {
      cartaction.setCount(itemCount);
    }

}
