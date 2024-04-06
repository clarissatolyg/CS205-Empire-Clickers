package com.example.empireclickers;

import android.graphics.Bitmap;

public class FactoryVisual {
    private Bitmap bitmap;
    private int x, y; // position on screen
    private int height = 300;
    private int width = 300;

    public FactoryVisual(Bitmap bitmap, int x, int y) {
        this.bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        this.x = x;
        this.y = y;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
