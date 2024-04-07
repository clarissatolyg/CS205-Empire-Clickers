package com.example.empireclickers;

import android.content.Context;
import android.media.MediaPlayer;

public class BackgroundMusic {
    private static BackgroundMusic reference = null;
    private MediaPlayer mediaPlayer;

    public static BackgroundMusic getInstance() {
        if (reference == null) {
            reference = new BackgroundMusic();
        }
        return reference;
    }

    public void initialiseMediaPlayer(Context context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.background_music);
            mediaPlayer.setLooping(true);
        }
    }

    public void startPlaying() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void stopPlaying() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release(); // release resources
            mediaPlayer = null; // ensure mediaPlayer is recreated nexxt time
        }
    }

}
