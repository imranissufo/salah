package com.salah.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.salah.R;
import com.salah.common.SignUpStep2;
import com.salah.model.Masjid;
import com.salah.util.TimeUtils;

import java.util.HashMap;
import java.util.Map;

public class MasjidForm6Activity extends AppCompatActivity {
    static final int MIN = 12;
    static final int MAX = 14;

    TimePicker timePicker;
    Masjid masjid;
    String action;
    TextView name, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_masjid_form6);
        name = findViewById(R.id.mjf6_name);
        time = findViewById(R.id.mjf6_time);
        timePicker = findViewById(R.id.mjf6_time_picker);
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
        if(action.equals("EDIT") || (masjid.getJumma()!=null && !masjid.getJumma().trim().isEmpty())){
            timePicker.setHour(TimeUtils.getHour(masjid.getJumma()));
            timePicker.setMinute(TimeUtils.getMinute(masjid.getJumma()));
        }else{
            timePicker.setHour(MIN);
            timePicker.setMinute(0);
        }
    }

    public void next(View view) {

        if (!validateHour()) {
            return;
        }

        Intent intent = new Intent(getApplicationContext(), MasjidForm7Activity.class);

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        masjid.setJumma(TimeUtils.getFormatedTime(hour, minute));
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
        Toast.makeText(MasjidForm6Activity.this, "Salah hour "+hour+" is not between "+MIN+"and "+MAX+"!", Toast.LENGTH_LONG).show();
        return false;
    }

    public void back(View view) {
        MasjidForm6Activity.super.onBackPressed();
    }

    public void cancel(View view) {
        Intent intent = new Intent(getApplicationContext(), MasgidAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}