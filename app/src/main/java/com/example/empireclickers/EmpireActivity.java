package com.example.empireclickers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EmpireActivity extends Activity {

    private Handler handler = new Handler();

    private int updateInterval = 1000;

    private final UpdateMoneyExecutorPool updateMoneyExecutorPool = new UpdateMoneyExecutorPool();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empire);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.navigation_empire);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_main:
                        // Switch to the main fragment/activity
                        Intent intent = new Intent(EmpireActivity.this, MainActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_empire:
                        // Navigate to the Empire Activity
                        return true;
                }
                return false;
            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.navigation_empire);
    }

}
