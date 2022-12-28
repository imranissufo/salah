package com.salah.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.salah.R;
import com.salah.model.User;
import com.salah.util.ValidationUtils;

public class UserFormActivity extends AppCompatActivity {

    TextInputLayout fullName, username, email, phoneNumber,date, gender;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_form);

        user = (User) getIntent().getSerializableExtra("user");

        fullName = findViewById(R.id.user_fullname);
        username = findViewById(R.id.user_username);
        email = findViewById(R.id.user_email);
        phoneNumber = findViewById(R.id.user_phone);
        date = findViewById(R.id.user_date);
        gender = findViewById(R.id.user_gender);

        fullName.getEditText().setText(user.getFullName());
        username.getEditText().setText(user.getUsername());
        email.getEditText().setText(user.getEmail());
        phoneNumber.getEditText().setText(user.getPhoneNo());
        date.getEditText().setText(user.getDate());
        gender.getEditText().setText(user.getGender());

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
        UserFormActivity.super.onBackPressed();
    }

    public void cancel(View view) {
        Intent intent = new Intent(getApplicationContext(), UserAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
