package com.example.empireclickers;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.content.*;
import androidx.appcompat.app.AppCompatActivity;

public class EmpireBuilder extends AppCompatActivity {

    private TextView tvMoney;
    private Button btnBuild;
    private int money = 1000; // Example starting money, you would actually pass this via intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empire_builder);

        tvMoney = findViewById(R.id.tvMoney);
        btnBuild = findViewById(R.id.btnBuild);

        // Update money display
        tvMoney.setText("Money: " + money);

        btnBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (money >= 100) { // Assuming building something costs 100
                    money -= 100;
                    tvMoney.setText("Money: " + money);
                    // Implement what happens when you build something (e.g., increase empire stats)
                }
            }
        });
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
                // Navigate back to MainActivity
                Intent mainIntent = new Intent(this, MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(mainIntent);
                return true;
            case R.id.navigation_empire_builder:
                // In this case, we're already in EmpireBuilder, so no action is needed
                // But you might want to refresh or reset the current activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
