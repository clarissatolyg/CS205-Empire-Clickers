package com.example.empireclickers;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends View {
    private List<FactoryVisual> factoryVisuals = new ArrayList<>();
    private final FoodFactory foodFactory = FoodFactory.getInstance();
    private final ClothesFactory clothesFactory = ClothesFactory.getInstance();
    private final PaperFactory paperFactory = PaperFactory.getInstance();
    private final ElectronicsFactory electronicsFactory = ElectronicsFactory.getInstance();
    private final CarFactory carFactory = CarFactory.getInstance();
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(Context context) {
        initialiseFactories(context, "foodFactory.png", foodFactory);
        initialiseFactories(context, "clothesFactory.png", clothesFactory);
        initialiseFactories(context, "paperFactory.png", paperFactory);
        initialiseFactories(context, "electronicsFactory.png", electronicsFactory);
        initialiseFactories(context, "carFactory.png", carFactory);
    }

    private void initialiseFactories(Context context, String path, FactoryInterface factory) {
        try {
            int gameViewWidth = this.getWidth();
            int gameViewHeight = this.getHeight();
            Random random = new Random();
            for (int i = 0; i < factory.getCount(); i++) {
                Bitmap factoryBitmap = loadBitmapFromAssets(context, path);
                int maxRandX = gameViewWidth - factoryBitmap.getWidth();
                int maxRandY = gameViewHeight - factoryBitmap.getHeight();
                // prevent negative random range in case game view is smaller than bitmap
                maxRandX = Math.max(maxRandX, 0);
                maxRandY = Math.max(maxRandY, 0);

                float randX = random.nextFloat() * maxRandX;
                float randY = random.nextFloat() * maxRandY;
                factoryVisuals.add(new FactoryVisual(factoryBitmap, (int) randX, (int) randY));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap loadBitmapFromAssets(Context context, String path) throws IOException {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open(path);
        return BitmapFactory.decodeStream(inputStream);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // set background colour
        canvas.drawColor(Color.parseColor("#41980a"));
        canvas.save();

        for (FactoryVisual visual : factoryVisuals) {
            Bitmap bitmap = visual.getBitmap();
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, visual.getX(), visual.getY(), null);
            }
        }
        canvas.restore();
    }
}
