package com.example.empireclickers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import android.os.Bundle;
import android.os.Handler;
import android.widget.*;
import android.view.*;
import android.content.*;
import android.provider.Settings;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.*;


/*
 * Basic Click Mechanism: Implemented in MainActivity, usually tied to a Button's OnClickListener.
 * Save and Load Game State: Could also live in MainActivity, using SharedPreferences or a dedicated class for managing persistent storage.
 * Idle Mechanics: Implemented as a service or using a Handler for timed updates within MainActivity.
 * */

public class MainActivity extends AppCompatActivity {
    //initialise DB
    DatabaseConfig db;
    // initialise buttons

    private Button moneyClick;
    private Button foodFactoryClick;
    private Button clothesFactoryClick;
    private Button paperFactoryClick;
    private Button electronicsFactoryClick;
    private Button carFactoryClick;

    // initialise text views
    private TextView textViewMoney;
    private TextView textViewFoodFactoryCost;
    private TextView textViewClothesFactoryCost;
    private TextView textViewPaperFactoryCost;
    private TextView textViewElectronicsFactoryCost;
    private TextView textViewCarFactoryCost;

    private LineChart lineChart;
    private final MoneyWrapper money = MoneyWrapper.getInstance();
    // initialise factories
    private final FoodFactory foodFactory = FoodFactory.getInstance();
    private final ClothesFactory clothesFactory = ClothesFactory.getInstance();
    private final PaperFactory paperFactory = PaperFactory.getInstance();
    private final ElectronicsFactory electronicsFactory = ElectronicsFactory.getInstance();
    private final CarFactory carFactory = CarFactory.getInstance();

    private List<FactoryInterface> factories = new ArrayList<>();

    private Handler handler = new Handler();

    private int updateInterval = 1000;

    private final UpdateMoneyExecutorPool updateMoneyExecutorPool = new UpdateMoneyExecutorPool();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final WindowInsetsController controller = getWindow().getInsetsController();
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars());
            }
        } else {
            // Use deprecated methods for older APIs.
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }

        db = new DatabaseConfig(MainActivity.this);
        String uId = Settings.Secure.getString(MainActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);

        Cursor cursor = db.getCredit(uId);
        Log.i("log", cursor.toString());
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                long amount = cursor.getLong(0);
                Log.i("Print amount", String.valueOf(amount));
                money.addMoney(amount);
            }

        }
        cursor.close();

        Cursor cursorFood = db.getFactory(uId, "food");
        if (cursorFood.getCount() != 0) {
            while (cursorFood.moveToNext()) {
                long cost = cursorFood.getLong(0);
                long count = cursorFood.getLong(1);
                Log.i("Print food cost", String.valueOf(cost));
                Log.i("Print food count", String.valueOf(count));
                foodFactory.setCount(count);
                foodFactory.setCost(cost);
            }
        }

        Cursor cursorClothes = db.getFactory(uId, "clothes");
        if (cursorClothes.getCount() != 0) {
            while (cursorClothes.moveToNext()) {
                long cost = cursorClothes.getLong(0);
                long count = cursorClothes.getLong(1);
                Log.i("Print clothes cost", String.valueOf(cost));
                Log.i("Print clothes count", String.valueOf(count));
                clothesFactory.setCount(count);
                clothesFactory.setCost(cost);
            }
        }
        cursorClothes.close();

        Cursor cursorPaper = db.getFactory(uId, "paper");
        if (cursorPaper.getCount() != 0) {
            while (cursorPaper.moveToNext()) {
                long cost = cursorPaper.getLong(0);
                long count = cursorPaper.getLong(1);
                Log.i("Print paper cost", String.valueOf(cost));
                Log.i("Print paper count", String.valueOf(count));
                paperFactory.setCount(count);
                paperFactory.setCost(cost);
            }
        }
        cursorPaper.close();

        Cursor cursorElectronics = db.getFactory(uId, "electronics");
        if (cursorElectronics.getCount() != 0) {
            while (cursorElectronics.moveToNext()) {
                long cost = cursorElectronics.getLong(0);
                long count = cursorElectronics.getLong(1);
                Log.i("Print electronics cost", String.valueOf(cost));
                Log.i("Print electronics count", String.valueOf(count));
                electronicsFactory.setCount(count);
                electronicsFactory.setCost(cost);
            }
        }
        cursorElectronics.close();

        Cursor cursorCar = db.getFactory(uId, "car");
        if (cursorCar.getCount() != 0) {
            while (cursorCar.moveToNext()) {
                long cost = cursorCar.getLong(0);
                long count = cursorCar.getLong(1);
                Log.i("Print car cost", String.valueOf(cost));
                Log.i("Print car count", String.valueOf(count));
                carFactory.setCount(count);
                carFactory.setCost(cost);
            }
        }
        cursorCar.close();


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

        textViewMoney = findViewById(R.id.textViewMoney);
        textViewFoodFactoryCost = findViewById(R.id.textViewFoodFactoryCost);
        textViewClothesFactoryCost = findViewById(R.id.textViewClothesFactoryCost);
        textViewPaperFactoryCost = findViewById(R.id.textViewPaperFactoryCost);
        textViewElectronicsFactoryCost = findViewById(R.id.textViewElectronicsFactoryCost);
        textViewCarFactoryCost = findViewById(R.id.textViewCarFactoryCost);
        lineChart = findViewById(R.id.chart);

        lineChart.setDrawGridBackground(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);

        List<Entry> entries = new LinkedList<>();
        for (long i = 0; i < 20; i++) {
            entries.add(new Entry(i, 0));
        }

        LineDataSet dataset = new LineDataSet(entries, "Money");
        dataset.setColor(Color.BLUE);

        LineData lineData = new LineData(dataset);
        lineChart.setData(lineData);

        lineChart.invalidate();

        // initialise text view texts
        textViewFoodFactoryCost.setText("Food Factory Cost: " + foodFactory.getCostofFactory());
        textViewClothesFactoryCost.setText("Clothes Factory Cost: " + clothesFactory.getCostofFactory());
        textViewPaperFactoryCost.setText("Paper Factory Cost: " + paperFactory.getCostofFactory());
        textViewElectronicsFactoryCost.setText("Electronics Factory Cost: " + electronicsFactory.getCostofFactory());
        textViewCarFactoryCost.setText("Car Factory Cost: " + carFactory.getCostofFactory());

        foodFactoryClick.setText("Food Factory Count: " + foodFactory.getCount());
        clothesFactoryClick.setText("Clothes Factory Count: " + clothesFactory.getCount());
        paperFactoryClick.setText("Paper Factory Count: " + paperFactory.getCount());
        electronicsFactoryClick.setText("Electronics Factory Count: " + electronicsFactory.getCount());
        carFactoryClick.setText("Car Factory Count: " + carFactory.getCount());


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
                foodFactory.purchase(money);
                foodFactoryClick.setText("Food Factory Count: " + foodFactory.getCount());
                textViewMoney.setText("Money: " + money.getMoney().toString());
                textViewFoodFactoryCost.setText("Food Factory Cost: " + foodFactory.getCostofFactory());
                //saveGame(); // Save the game state whenever the money is updated
            }
        });

        clothesFactoryClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clothesFactory.purchase(money);
                clothesFactoryClick.setText("Clothes Factory Count: " + clothesFactory.getCount());
                textViewMoney.setText("Money: " + money.getMoney().toString());
                textViewClothesFactoryCost.setText("Clothes Factory Cost: " + clothesFactory.getCostofFactory());
            }
        });

        paperFactoryClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paperFactory.purchase(money);
                paperFactoryClick.setText("Paper Factory Count: " + paperFactory.getCount());
                textViewMoney.setText("Money: " + money.getMoney().toString());
                textViewPaperFactoryCost.setText("Paper Factory Cost: " + paperFactory.getCostofFactory());
            }
        });

        electronicsFactoryClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                electronicsFactory.purchase(money);
                electronicsFactoryClick.setText("Electronics Factory Count: " + electronicsFactory.getCount());
                textViewMoney.setText("Money: " + money.getMoney().toString());
                textViewElectronicsFactoryCost.setText("Electronics Factory Cost: " + electronicsFactory.getCostofFactory());
            }
        });

        carFactoryClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carFactory.purchase(money);
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
//                        intent.putExtra("MoneyWrapper", money);
//                        intent.putExtra("factories", (Serializable) factories);
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
                for (Entry e : entries) {
                    if (e.getX() != 0) {
                        e.setX(e.getX() - 1);
                    }
                }
                entries.add(new Entry(entries.size(), money.getMoney().floatValue()));

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
        //String uId = Settings.Secure.getString(MainActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
//
        //db.updateCredit(uId, money.getMoney());
        //Log.i("Print amount", String.valueOf(money.getMoney()));
        //saveGame(); // Ensure the game state is saved when the app is paused
        stopService(new Intent(this, BackgroundSoundService.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        String uId = Settings.Secure.getString(MainActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);

        db.updateCredit(uId, money.getMoney());
        db.updateFactory(uId, foodFactory.getCostofFactory(), foodFactory.getCount(), foodFactory.getProfitPerSecond(), "food");
        db.updateFactory(uId, clothesFactory.getCostofFactory(), clothesFactory.getCount(), clothesFactory.getProfitPerSecond(), "clothes");
        db.updateFactory(uId, paperFactory.getCostofFactory(), paperFactory.getCount(), paperFactory.getProfitPerSecond(), "paper");
        db.updateFactory(uId, electronicsFactory.getCostofFactory(), electronicsFactory.getCount(), electronicsFactory.getProfitPerSecond(), "electronics");
        db.updateFactory(uId, carFactory.getCostofFactory(), carFactory.getCount(), carFactory.getProfitPerSecond(), "car");
        Log.i("Print money amount", String.valueOf(money.getMoney()));
        Log.i("Print food count", String.valueOf(foodFactory.getCount()));
        Log.i("Print clothes count", String.valueOf(clothesFactory.getCount()));
        Log.i("Print paper count", String.valueOf(paperFactory.getCount()));
        Log.i("Print electronics count", String.valueOf(electronicsFactory.getCount()));
        Log.i("Print car count", String.valueOf(carFactory.getCount()));

        //saveGame(); // Ensure the game state is saved when the app is paused
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, BackgroundSoundService.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        startService(new Intent(this, BackgroundSoundService.class));
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
