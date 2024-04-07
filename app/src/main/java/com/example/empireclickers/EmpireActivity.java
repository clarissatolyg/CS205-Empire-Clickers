package com.example.empireclickers;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
    private GameView gameView;
    private BackgroundMusic backgroundMusic = BackgroundMusic.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empire);

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


        gameView = findViewById((R.id.gameView));
        gameView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // at this point, layout has been completed and the dimensions of gameView are available
                int width = gameView.getWidth();
                int height = gameView.getHeight();

                // now can use these dimensions to initialise factories
                gameView.init(EmpireActivity.this);

                // remove global layout listener when done
                gameView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }


        });


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
        bottomNav.setSelectedItemId(R.id.navigation_empire_builder);
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (!isChangingConfigurations() && !isFinishing()) {
//            backgroundMusic.startPlaying();
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();
        backgroundMusic.initialiseMediaPlayer(this);
        backgroundMusic.startPlaying();
    }

}
