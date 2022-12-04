package com.salah.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.salah.R;
import com.salah.common.Login;
import com.salah.common.SignUpStep2;
import com.salah.model.Masjid;
import com.salah.util.TimeUtils;

public class MasjidForm3Activity extends AppCompatActivity {

    TimePicker zuhr;
    Masjid masjid;
    String action;
    TextView name, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_masjid_form3);

        name = findViewById(R.id.mjf3_name);
        time = findViewById(R.id.mjf3_time);
        zuhr = findViewById(R.id.mjf3_time_picker);
        zuhr.setIs24HourView(true);
        zuhr.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time.setText(TimeUtils.addZero(hourOfDay)+":"+TimeUtils.addZero(minute));
            }
        });

        action = getIntent().getStringExtra("action");
        masjid = (Masjid) getIntent().getSerializableExtra("masjid");
        name.setText(masjid.getName());
        if(action.equals("EDIT")){
            zuhr.setHour(TimeUtils.getHour(masjid.getZuhr()));
            zuhr.setMinute(TimeUtils.getMinute(masjid.getZuhr()));
        }

    }

    public void next(View view) {

        if (!validateHour()) {
            return;
        }

        Intent intent = new Intent(getApplicationContext(), SignUpStep2.class);

        int hour = zuhr.getHour();
        int minute = zuhr.getMinute();

        masjid.setZuhr(hour+":"+minute);
        //Pass all fields to the next activity
        intent.putExtra("masjid", masjid);
        intent.putExtra("action", action);


        startActivity(intent);
    }

    private boolean validateHour() {
        int hour = zuhr.getHour();

        if(hour> 12 && hour<14){
            return true;
        }
        Toast.makeText(MasjidForm3Activity.this, "Salah hour "+hour+" is not between 12 and 14!", Toast.LENGTH_LONG).show();
        return false;
    }

    public void onBackBtnClick(View view) {
        MasjidForm3Activity.super.onBackPressed();
    }

}