package com.salah.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.salah.R;
import com.salah.common.SignUpStep2;
import com.salah.model.Masjid;
import com.salah.util.TimeUtils;

public class MasjidCreate2Activity extends AppCompatActivity {

    TimePicker fajr;
    Masjid masjid;
    String action;
    TextInputLayout name, location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_masjid_create2);

        name = findViewById(R.id.mjc2_name);
        fajr = findViewById(R.id.mjc_fajr_picker);
        fajr.setIs24HourView(true);

        action = getIntent().getStringExtra("action");
        masjid = (Masjid) getIntent().getSerializableExtra("masjid");
        name.getEditText().setText(masjid.getName());
        if(action.equals("EDIT")){
            fajr.setHour(TimeUtils.getHour(masjid.getFajr()));
            fajr.setMinute(TimeUtils.getMinute(masjid.getFajr()));
        }

    }

    public void next(View view) {

        if (!validateHour()) {
            return;
        }

        Intent intent = new Intent(getApplicationContext(), SignUpStep2.class);

        int hour = fajr.getHour();
        int minute = fajr.getMinute();

        masjid.setFajr(hour+":"+minute);
        //Pass all fields to the next activity
        intent.putExtra("masjid", masjid);
        intent.putExtra("action", action);


        startActivity(intent);
    }

    private boolean validateHour() {
        int hour = fajr.getHour();

        if(hour> 3 && hour<7){
            return true;
        }
        return false;
    }

    public void onBackBtnClick(View view) {
        MasjidCreate2Activity.super.onBackPressed();
    }

}