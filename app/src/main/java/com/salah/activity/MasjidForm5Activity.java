package com.salah.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.salah.R;
import com.salah.model.Masjid;
import com.salah.util.TimeUtils;
import com.salah.util.ValidationUtils;

public class MasjidForm5Activity extends AppCompatActivity {

    static final int MIN = 18;
    static final int MAX = 20;

    TimePicker timePicker;
    Masjid masjid;
    String action;
    TextView name, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_masjid_form5);

        name = findViewById(R.id.mjf5_name);
        time = findViewById(R.id.mjf5_time);
        timePicker = findViewById(R.id.mjf5_time_picker);
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
        if(action.equals("EDIT") || (masjid.getIsha()!=null && !masjid.getIsha().trim().isEmpty())){
            timePicker.setHour(TimeUtils.getHour(masjid.getIsha()));
            timePicker.setMinute(TimeUtils.getMinute(masjid.getIsha()));
        }else{
            timePicker.setHour(MIN);
            timePicker.setMinute(0);
        }

    }

    public void next(View view) {

        if (!ValidationUtils.validateHour(MasjidForm5Activity.this, MIN, MAX,timePicker)) {
            return;
        }

        Intent intent = new Intent(getApplicationContext(), MasjidForm6Activity.class);

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        masjid.setIsha(TimeUtils.getFormatedTime(hour, minute));
        //Pass all fields to the next activity
        intent.putExtra("masjid", masjid);
        intent.putExtra("action", action);


        startActivity(intent);
    }

    public void back(View view) {
        MasjidForm5Activity.super.onBackPressed();
    }

    public void cancel(View view) {
        Intent intent = new Intent(getApplicationContext(), MasgidAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}