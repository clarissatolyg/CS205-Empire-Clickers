package com.example.empireclickers;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

public class AppLifecycleObserver implements LifecycleObserver {
    public AppLifecycleObserver() {
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onAppBackgrounded() {
        BackgroundMusic.getInstance().stopPlaying();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onAppForegrounded() {
        BackgroundMusic.getInstance().startPlaying();
    }
}
