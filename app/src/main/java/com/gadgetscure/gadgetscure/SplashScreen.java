package com.gadgetscure.gadgetscure;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1500;
    private ProgressBar progressbar;
    //private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        progressbar.setVisibility(ProgressBar.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(mainIntent);
                finish();

            }

        }, SPLASH_TIME_OUT);
    }
}
