package com.salah.common;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.salah.R;
import com.salah.activity.DashboardActivity;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIMER = 1000;
    private TextView textView;

    private Animation sideAnimation, bottomAnimation;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        textView = findViewById(R.id.sc_tv);

        sideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_anim);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        textView.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                preferences = getSharedPreferences("preferences",MODE_PRIVATE);
                boolean isFirstTime = preferences.getBoolean("firstTime", true);
                if (false){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("firstTime", false);
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), OnBoarding.class);
                    startActivity(intent);
                    finish();
                }else{
                    //Intent intent = new Intent(getApplicationContext(), UserDashboard.class);
                    Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_TIMER);
    }
}