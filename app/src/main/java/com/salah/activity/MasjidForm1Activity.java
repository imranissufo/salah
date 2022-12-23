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
import com.salah.model.Masjid;
import com.salah.service.MasjidService;
import com.salah.util.ValidationUtils;

import java.util.List;

public class MasjidForm1Activity extends AppCompatActivity {

    //Variables
    ImageView backBtn;
    Button next;
    TextView titleText, slideText;
    TextInputLayout name, location;
    Masjid masjid;
    String action;
    MasjidService masjidService;
    List<Masjid> masjids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_masjid_form1);

        masjidService = MasjidService.getInstance();
        masjids = masjidService.findAll("");

        action = getIntent().getStringExtra("action");
        masjid = (Masjid) getIntent().getSerializableExtra("masjid");

        //Hooks for animation
        backBtn = findViewById(R.id.mjf1_back);
        next = findViewById(R.id.mjc_next_button);
        titleText = findViewById(R.id.mjc_title_text);
        slideText = findViewById(R.id.mjc_slide_text);

        name = findViewById(R.id.mjc_name);
        location = findViewById(R.id.mjc_location);

        if (action.equals("EDIT") || (masjid.getName() != null && !masjid.getName().isEmpty())) {
            name.getEditText().setText(masjid.getName());
            location.getEditText().setText(masjid.getLocation());
        }
    }

    public void next(View view) {

        if (!ValidationUtils.validateField(name) | !ValidationUtils.validateField(location)) {
            return;
        }

        if (!action.equals("EDIT")) {
            if(!ValidationUtils.validateFieldContains(name, masjids)){
                return;
            }
        }

        Intent intent = new Intent(getApplicationContext(), MasjidForm2Activity.class);

        String _name = name.getEditText().getText().toString().trim();
        String _location = location.getEditText().getText().toString().trim();

        masjid.setName(_name);
        masjid.setLocation(_location);
        //Pass all fields to the next activity
        intent.putExtra("masjid", masjid);
        intent.putExtra("action", action);

        startActivity(intent);

    }

    public void back(View view) {
        MasjidForm1Activity.super.onBackPressed();
    }

    public void cancel(View view) {
        Intent intent = new Intent(getApplicationContext(), MasgidAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}