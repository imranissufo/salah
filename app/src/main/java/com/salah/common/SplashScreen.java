package com.salah.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.salah.R;
import com.salah.user.UserDashboard;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIMER = 4000;
    private ImageView imageView;
    private TextView textView;

    private Animation sideAnimation, bottomAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        imageView = findViewById(R.id.sc_iv);
        textView = findViewById(R.id.sc_tv);

        sideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_anim);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        imageView.setAnimation(sideAnimation);
        textView.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), UserDashboard.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMER);
    }
}