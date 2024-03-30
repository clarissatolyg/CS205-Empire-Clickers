package com.example.empireclickers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.content.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;


/*
* Basic Click Mechanism: Implemented in MainActivity, usually tied to a Button's OnClickListener.
* Save and Load Game State: Could also live in MainActivity, using SharedPreferences or a dedicated class for managing persistent storage.
* Idle Mechanics: Implemented as a service or using a Handler for timed updates within MainActivity.
* */

public class MainActivity extends AppCompatActivity {

    private Button buttonClick;
    private TextView textViewMoney;
    private int money = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonClick = findViewById(R.id.buttonClick);
        textViewMoney = findViewById(R.id.textViewMoney);

        loadGame(); // Load the saved game state

        buttonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                money++;
                textViewMoney.setText("Money: " + money);
                saveGame(); // Save the game state whenever the money is updated
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_main:
                        // Switch to the main fragment/activity
                        return true;
                    case R.id.navigation_empire_builder:
                        // Intent to switch to EmpireBuilder Activity
                        startActivity(new Intent(MainActivity.this, EmpireBuilder.class));
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveGame(); // Ensure the game state is saved when the app is paused
    }

    private void saveGame() {
        SharedPreferences prefs = getSharedPreferences("EmpireClickersPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Money", money);
        editor.apply();
    }

    private void loadGame() {
        SharedPreferences prefs = getSharedPreferences("EmpireClickersPrefs", MODE_PRIVATE);
        money = prefs.getInt("Money", 0); // Load the money, default to 0 if not found
        textViewMoney.setText("Money: " + money);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_main:
                // Check if we're already in MainActivity to avoid reloading it
                if (!(this instanceof MainActivity)) {
                    Intent mainActivityIntent = new Intent(this, MainActivity.class);
                    mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(mainActivityIntent);
                }
                return true;
            case R.id.navigation_empire_builder:
                // Navigate to Empire Builder Activity with appropriate flags
                Intent empireBuilderIntent = new Intent(this, EmpireBuilder.class);
                // This flag combination clears the task's existing activities except for the instance of the activity being launched
                empireBuilderIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(empireBuilderIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
