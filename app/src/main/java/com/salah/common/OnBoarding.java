package com.salah.common;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.salah.R;
import com.salah.user.UserDashboard;
import com.salah.util.SliderAdapter;

public class OnBoarding extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dotsLayout;

    SliderAdapter sliderAdapter;
    TextView[] dots;

    Button startBtn, skipBtn, nextBtn;
    Animation animation;
    int currentPosition;

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPosition = position;

            switch (position) {
                case 3: {
                    animation = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.bottom_anim);
                    startBtn.setAnimation(animation);
                    startBtn.setVisibility(View.VISIBLE);
                    break;
                }

                default:
                    startBtn.setVisibility(View.INVISIBLE);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding);

        viewPager = findViewById(R.id.os_vp);
        dotsLayout = findViewById(R.id.os_dots);
        startBtn = findViewById(R.id.os_start_btn);
        skipBtn = findViewById(R.id.os_skip_btn);
        nextBtn = findViewById(R.id.os_next_btn);

        startBtn.setOnClickListener(goToUserDashboard());
        skipBtn.setOnClickListener(goToUserDashboard());

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentPosition) {
                    case 3: {
                        startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                        finish();
                        break;
                    }

                    default:
                        viewPager.setCurrentItem(currentPosition+1);
                        break;
                }
            }
        });

        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        addDots(0);

        viewPager.addOnPageChangeListener(changeListener);
    }

    @NonNull
    private View.OnClickListener goToUserDashboard() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                finish();
            }
        };
    }

    private void addDots(int position) {
        dots = new TextView[4];
        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(40);

            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }
}