package com.example.empireclickers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.util.Log;

import android.os.Bundle;
import android.os.Handler;
import android.widget.*;
import android.view.*;
import android.content.*;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.*;


/*
 * Basic Click Mechanism: Implemented in MainActivity, usually tied to a Button's OnClickListener.
 * Save and Load Game State: Could also live in MainActivity, using SharedPreferences or a dedicated class for managing persistent storage.
 * Idle Mechanics: Implemented as a service or using a Handler for timed updates within MainActivity.
 * */

public class MainActivity extends AppCompatActivity {

    // initialise buttons
    private Button moneyClick;
    private Button foodFactoryClick;
    private Button clothesFactoryClick;
    private Button paperFactoryClick;
    private Button electronicsFactoryClick;
    private Button carFactoryClick;

    // initialise textviews
    private TextView textViewMoney;
    private TextView textViewFoodFactoryCost;
    private TextView textViewClothesFactoryCost;
    private TextView textViewPaperFactoryCost;
    private TextView textViewElectronicsFactoryCost;
    private TextView textViewCarFactoryCost;

    private LineChart lineChart;
    private final MoneyWrapper money = new MoneyWrapper(0);
    // initialise factories
    private final FoodFactory foodFactory = new FoodFactory();
    private final ClothesFactory clothesFactory = new ClothesFactory();
    private final PaperFactory paperFactory = new PaperFactory();
    private final ElectronicsFactory electronicsFactory = new ElectronicsFactory();
    private final CarFactory carFactory = new CarFactory();

    private List<FactoryInterface> factories = new ArrayList<>();

    private Handler handler = new Handler();

    private int updateInterval = 1000;

    private final UpdateMoneyExecutorPool updateMoneyExecutorPool = new UpdateMoneyExecutorPool();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        factories.add(foodFactory);
        factories.add(clothesFactory);
        factories.add(paperFactory);
        factories.add(electronicsFactory);
        factories.add(carFactory);

        moneyClick = findViewById(R.id.moneyClick);
        foodFactoryClick = findViewById(R.id.foodFactoryClick);
        clothesFactoryClick = findViewById(R.id.clothesFactoryClick);
        paperFactoryClick = findViewById(R.id.paperFactoryClick);
        electronicsFactoryClick = findViewById(R.id.electronicsFactoryClick);
        carFactoryClick = findViewById(R.id.carFactoryClick);

        // set text
        textViewMoney = findViewById(R.id.textViewMoney);
        textViewFoodFactoryCost = findViewById(R.id.textViewFoodFactoryCost);
        textViewClothesFactoryCost = findViewById(R.id.textViewClothesFactoryCost);
        textViewPaperFactoryCost = findViewById(R.id.textViewPaperFactoryCost);
        textViewElectronicsFactoryCost = findViewById(R.id.textViewElectronicsFactoryCost);
        textViewCarFactoryCost = findViewById(R.id.textViewCarFactoryCost);

        textViewFoodFactoryCost.setText("Food Factory Cost: " + foodFactory.getCostofFactory());
        textViewClothesFactoryCost.setText("Clothes Factory Cost: " + clothesFactory.getCostofFactory());
        textViewPaperFactoryCost.setText("Paper Factory Cost: " + paperFactory.getCostofFactory());
        textViewElectronicsFactoryCost.setText("Electronics Factory Cost: " + electronicsFactory.getCostofFactory());
        textViewCarFactoryCost.setText("Car Factory Cost: " + carFactory.getCostofFactory());


        // initialise line chart
        lineChart = findViewById(R.id.chart);

        lineChart.setDrawGridBackground(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);

        List<Entry> entries = new LinkedList<>();
        for(long i = 0; i < 20; i++){
            entries.add(new Entry(i,0));
        }

        LineDataSet dataset = new LineDataSet(entries, "Money");
        dataset.setColor(Color.BLUE);

        LineData lineData = new LineData(dataset);
        lineChart.setData(lineData);

        lineChart.invalidate();


        //loadGame(); // Load the saved game state

        moneyClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                money.addMoney(1);
                textViewMoney.setText("Money: " + money.getMoney().toString());
                //saveGame(); // Save the game state whenever the money is updated
            }
        });

        foodFactoryClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = 0;
                while (money.getMoney().intValue() >= foodFactory.getCostofFactory()) {
                    money.deductMoney(foodFactory.getCostofFactory());
                    count++;
                }
                foodFactory.purchase(count);
                foodFactoryClick.setText("Food Factory Count: " + foodFactory.getCount());
                textViewMoney.setText("Money: " + money.getMoney().toString());
                textViewFoodFactoryCost.setText("Food Factory Cost: " + foodFactory.getCostofFactory());
                //saveGame(); // Save the game state whenever the money is updated
            }
        });

        clothesFactoryClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = 0;
                while (money.getMoney().intValue() >= clothesFactory.getCostofFactory()) {
                    money.deductMoney(clothesFactory.getCostofFactory());
                    count++;
                }
                clothesFactory.purchase(count);
                clothesFactoryClick.setText("Clothes Factory Count: " + clothesFactory.getCount());
                textViewMoney.setText("Money: " + money.getMoney().toString());
                textViewClothesFactoryCost.setText("Clothes Factory Cost: " + clothesFactory.getCostofFactory());
            }
        });

        paperFactoryClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                while (money.getMoney().intValue() >= paperFactory.getCostofFactory()) {
                    money.deductMoney(paperFactory.getCostofFactory());
                    count++;
                }
                paperFactory.purchase(count);
                paperFactoryClick.setText("Paper Factory Count: " + paperFactory.getCount());
                textViewMoney.setText("Money: " + money.getMoney().toString());
                textViewPaperFactoryCost.setText("Paper Factory Cost: " + paperFactory.getCostofFactory());
            }
        });

        electronicsFactoryClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                while (money.getMoney().intValue() >= electronicsFactory.getCostofFactory()) {
                    money.deductMoney(electronicsFactory.getCostofFactory());
                    count++;
                }
                electronicsFactory.purchase(count);
                electronicsFactoryClick.setText("Electronics Factory Count: " + electronicsFactory.getCount());
                textViewMoney.setText("Money: " + money.getMoney().toString());
            }
        });

        carFactoryClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                while (money.getMoney().intValue() >= carFactory.getCostofFactory()) {
                    money.deductMoney(carFactory.getCostofFactory());
                    count++;
                }
                carFactory.purchase(count);
                carFactoryClick.setText("Car Factory Count: " + carFactory.getCount());
                textViewMoney.setText("Money: " + money.getMoney().toString());
                textViewCarFactoryCost.setText("Car Factory Cost: " + carFactory.getCostofFactory());
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
                        Intent intent = new Intent(MainActivity.this, EmpireActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });

        handler.post(new Runnable() {
            @Override
            public void run() {
                // update textView here

                for (FactoryInterface f : factories) {
                    Runnable updateMoney = () -> money.addMoney(f.netProfitPerSecond());
                    updateMoneyExecutorPool.submit(updateMoney);
                }
                textViewMoney.setText("Money: " + money.getMoney().toString());
                handler.postDelayed(this, updateInterval); // set time here to refresh textView
            }
        });

        handler.post(new Runnable() {
            @Override
            public void run() {
                // update textView here
                entries.remove(1);
                for(Entry e : entries){
                    if(e.getX() != 0) {
                        e.setX(e.getX() - 1);
                    }
                }
                entries.add(new Entry(entries.size(),money.getMoney().floatValue()));

                LineDataSet dataset = new LineDataSet(entries, "Money");
                dataset.setColor(Color.BLUE);

                LineData lineData = new LineData(dataset);
                lineChart.setData(lineData);

                lineChart.invalidate();

                handler.post(this); // set time here to refresh textView
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        //saveGame(); // Ensure the game state is saved when the app is paused
    }

//    private void saveGame() {
//        SharedPreferences prefs = getSharedPreferences("EmpireClickersPrefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putInt("Money", money.getMoney());
//        editor.apply();
//    }

//    private void loadGame() {
//        SharedPreferences prefs = getSharedPreferences("EmpireClickersPrefs", MODE_PRIVATE);
//        money.setMoney(prefs.getInt("Money", 0)); // Load the money, default to 0 if not found
//        textViewMoney.setText("Money: " + money.getMoney());
//    }

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
//            case R.id.navigation_empire_builder:
//                // Navigate to Empire Builder Activity with appropriate flags
//                Intent empireBuilderIntent = new Intent(this, EmpireBuilder.class);
//                // This flag combination clears the task's existing activities except for the instance of the activity being launched
//                empireBuilderIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(empireBuilderIntent);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
