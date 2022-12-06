package com.salah.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.salah.R;
import com.salah.model.Masjid;
import com.salah.util.TimeUtils;

public class MasjidForm2Activity extends AppCompatActivity {

    static final int MIN = 3;
    static final int MAX = 7;

    TimePicker timePicker;
    Masjid masjid;
    String action;
    TextView name, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_masjid_form2);

        name = findViewById(R.id.mjf2_name);
        time = findViewById(R.id.mjf2_time);
        timePicker = findViewById(R.id.mjf2_fajr_picker);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time.setText(TimeUtils.getFormatedTime(hourOfDay,minute));
            }
        });

        action = getIntent().getStringExtra("action");
        masjid = (Masjid) getIntent().getSerializableExtra("masjid");
        name.setText(masjid.getName());
        if(action.equals("EDIT") || (masjid.getFajr()!=null && !masjid.getFajr().isEmpty())){
            timePicker.setHour(TimeUtils.getHour(masjid.getFajr()));
            timePicker.setMinute(TimeUtils.getMinute(masjid.getFajr()));
        }else{
            timePicker.setHour(3);
            timePicker.setMinute(0);
        }

    }

    public void next(View view) {

        if (!validateHour()) {
            return;
        }

        Intent intent = new Intent(getApplicationContext(), MasjidForm3Activity.class);

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        masjid.setFajr(TimeUtils.getFormatedTime(hour,minute));
        //Pass all fields to the next activity
        intent.putExtra("masjid", masjid);
        intent.putExtra("action", action);


        startActivity(intent);
    }

    private boolean validateHour() {
        int hour = timePicker.getHour();

        if(hour>= MIN && hour<=MAX){
            return true;
        }
        Toast.makeText(MasjidForm2Activity.this, "Salah hour "+hour+" is not between "+MIN+"and "+MAX+"!", Toast.LENGTH_LONG).show();
        return false;
    }

    public void back(View view) {
        MasjidForm2Activity.super.onBackPressed();
    }

    public void cancel(View view) {
        Intent intent = new Intent(getApplicationContext(), MasgidAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}