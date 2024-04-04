package com.example.empireclickers;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EmpireActivity extends AppCompatActivity {
    private final MoneyWrapper money = MoneyWrapper.getInstance();
    // initialise factories
    private final FoodFactory foodFactory = FoodFactory.getInstance();
    private final ClothesFactory clothesFactory = ClothesFactory.getInstance();
    private final PaperFactory paperFactory = PaperFactory.getInstance();
    private final ElectronicsFactory electronicsFactory = ElectronicsFactory.getInstance();
    private final CarFactory carFactory = CarFactory.getInstance();

    private List<FactoryInterface> factories = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empire);

        factories.add(foodFactory);
        factories.add(clothesFactory);
        factories.add(paperFactory);
        factories.add(electronicsFactory);
        factories.add(carFactory);

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
//                        intent.putExtra("MoneyWrapper", money);
//                        intent.putExtra("factories", (Serializable) factories);
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
