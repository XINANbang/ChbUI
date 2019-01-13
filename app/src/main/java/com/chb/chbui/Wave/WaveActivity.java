package com.chb.chbui.Wave;

import android.app.Activity;
import android.os.Bundle;

import com.chb.chbui.R;

public class WaveActivity extends Activity {

    WaveViewBezier mBezierWave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wave_main);
        mBezierWave = findViewById(R.id.bezier_wave);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBezierWave.startAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBezierWave.resumeAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBezierWave.pauseAnimation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBezierWave.stopAnimation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
