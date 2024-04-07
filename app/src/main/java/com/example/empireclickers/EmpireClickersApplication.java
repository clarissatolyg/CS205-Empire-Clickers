package com.example.empireclickers;

import android.app.Application;

public class EmpireClickersApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new AppLifecycleObserver();
    }
}
