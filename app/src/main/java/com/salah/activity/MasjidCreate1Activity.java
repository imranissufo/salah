package com.salah.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.salah.R;
import com.salah.common.SignUpStep2;
import com.salah.model.Masjid;

public class MasjidCreate1Activity extends AppCompatActivity {

    //Variables
    ImageView backBtn;
    Button next;
    TextView titleText, slideText;
    TextInputLayout name, location;
    Masjid masjid;
    String action;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_masjid_create1);

        action = getIntent().getStringExtra("action");
        masjid = (Masjid) getIntent().getSerializableExtra("masjid");

        //Hooks for animation
        backBtn = findViewById(R.id.mjc_back_button);
        next = findViewById(R.id.mjc_next_button);
        titleText = findViewById(R.id.mjc_title_text);
        slideText = findViewById(R.id.mjc_slide_text);

        name = findViewById(R.id.mjc_name);
        location = findViewById(R.id.mjc_location);

        if(action.equals("EDIT")){
            name.getEditText().setText(masjid.getName());
            location.getEditText().setText(masjid.getLocation());
        }
    }

    private boolean validateName() {
        String val = name.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            name.setError("Field can not be empty");
            return false;
        } else {
            name.setError(null);
            name.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateLocation() {
        String val = location.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            location.setError("Field can not be empty");
            return false;
        } else {
            location.setError(null);
            location.setErrorEnabled(false);
            return true;
        }
    }

    public void next(View view) {

        if (!validateName() | !validateLocation()) {
            return;
        }

        Intent intent = new Intent(getApplicationContext(), MasjidCreate2Activity.class);

        String _name = name.getEditText().getText().toString().trim();
        String _location = location.getEditText().getText().toString().trim();

        masjid.setName(_name);
        masjid.setLocation(_location);
        //Pass all fields to the next activity
        intent.putExtra("masjid", masjid);
        intent.putExtra("action", action);

        startActivity(intent);

    }

    public void onBackBtnClick(View view) {
        MasjidCreate1Activity.super.onBackPressed();
    }
}