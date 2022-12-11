package com.salah.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.salah.R;
import com.salah.model.Masjid;
import com.salah.util.TimeUtils;

import java.util.Calendar;

public class MasjidAnnc1Activity extends AppCompatActivity{

    Masjid masjid;
    String action;
    TextView name, fajrDate,fajrTime, assrDate,assrTime, ishaDate,ishaTime;
    ImageView fajrDateIcon,fajrTimeIcon, assrDateIcon, assrTimeIcon, ishaDateIcon, ishaTimeIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_masjid_annc1);

        action = getIntent().getStringExtra("action");
        masjid = (Masjid) getIntent().getSerializableExtra("masjid");

        name = findViewById(R.id.mja1_name);
        fajrDate = findViewById(R.id.mja1_fajr_date);
        fajrTime = findViewById(R.id.mja1_fajr_time);
        fajrDateIcon = findViewById(R.id.mja1_fajr_date_ic);
        fajrTimeIcon = findViewById(R.id.mja1_fajr_time_ic);

        assrDate = findViewById(R.id.mja1_assr_date);
        assrTime = findViewById(R.id.mja1_assr_time);
        assrDateIcon = findViewById(R.id.mja1_assr_date_ic);
        assrTimeIcon = findViewById(R.id.mja1_assr_time_ic);

        ishaDate = findViewById(R.id.mja1_isha_date);
        ishaTime = findViewById(R.id.mja1_isha_time);
        ishaDateIcon = findViewById(R.id.mja1_isha_date_ic);
        ishaTimeIcon = findViewById(R.id.mja1_isha_time_ic);

        name.setText(masjid.getName());
        fajrDate.setText(masjid.getAnncFajrDate());
        fajrTime.setText(masjid.getAnncFajrTime());
        assrDate.setText(masjid.getAnncAssrDate());
        assrTime.setText(masjid.getAnncAssrTime());
        ishaDate.setText(masjid.getAnncIshaDate());
        ishaTime.setText(masjid.getAnncIshaTime());

        fajrDateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(fajrDate);
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        fajrTimeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment(fajrTime);
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });


        assrDateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(assrDate);
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        assrTimeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment(assrTime);
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        ishaDateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(ishaDate);
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        ishaTimeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment(ishaTime);
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        TextView date;

        public DatePickerFragment (TextView date){
            this.date = date;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(requireContext(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            date.setText(TimeUtils.getFormatedDate(year, month,day));
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        private final TextView time;

        public TimePickerFragment(TextView time) {
            this.time = time;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            time.setText(TimeUtils.getFormatedTime(hourOfDay, minute));
        }
    }

    public void next(View view) {

        if (!validate()) {
            return;
        }

        Intent intent = new Intent(getApplicationContext(), MasjidAnnc2Activity.class);

        masjid.setAnncFajrDate(fajrDate.getText().toString());
        masjid.setAnncFajrTime(fajrTime.getText().toString());
        masjid.setAnncAssrDate(assrDate.getText().toString());
        masjid.setAnncAssrTime(assrTime.getText().toString());
        masjid.setAnncIshaDate(ishaDate.getText().toString());
        masjid.setAnncIshaTime(ishaTime.getText().toString());
        //Pass all fields to the next activity
        intent.putExtra("masjid", masjid);
        intent.putExtra("action", action);

        startActivity(intent);
    }

    private boolean validate() {
        return true;
    }

    public void back(View view) {
        MasjidAnnc1Activity.super.onBackPressed();
    }

    public void cancel(View view) {
        Intent intent = new Intent(getApplicationContext(), MasgidAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}