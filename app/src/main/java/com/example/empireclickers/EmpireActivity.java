package com.example.empireclickers;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.List;

public class EmpireActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empire);

        // Retrieve the MoneyWrapper object
        MoneyWrapper money = (MoneyWrapper) getIntent().getSerializableExtra("MoneyWrapper");
        // Retrieve the list of factories
        List<FactoryInterface> factories = (List<FactoryInterface>) getIntent().getSerializableExtra("factories");

        TextView textViewMoney = findViewById(R.id.textViewMoney);
        textViewMoney.setText(String.format("Money: %s", money.getMoney().toString()));

        TextView textViewFactories = findViewById(R.id.textViewFactories);

        if (factories != null) {
            int count = 0;
            for (FactoryInterface factory : factories) {
                count += factory.getCount();
            }
            textViewFactories.setText(String.format("Factories: %d", count));
        } else {
            textViewFactories.setText("Factories: 0");
        }
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_main:
                        // Switch to the main fragment/activity
                        Intent intent = new Intent(EmpireActivity.this, MainActivity.class);
                        intent.putExtra("MoneyWrapper", money);
                        intent.putExtra("factories", (Serializable) factories);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_empire_builder:
                        return true;
                }
                return false;
            }
        });
    }
}
