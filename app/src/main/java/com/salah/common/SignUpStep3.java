package com.salah.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;
import com.salah.R;

public class SignUpStep3 extends AppCompatActivity {

    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_step3);

        //Hooks
        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNumber = findViewById(R.id.signup_phone_number);
    }

    public void callVerifyOTPScreen(View view) {

        if (!validatePhoneNumber()) {
            return;
        }

        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);

        String _fullName = getIntent().getStringExtra("fullname");
        String _username = getIntent().getStringExtra("username");
        String _email = getIntent().getStringExtra("email");
        String _password = getIntent().getStringExtra("password");
        String _gender = getIntent().getStringExtra("gender");
        String _date = getIntent().getStringExtra("date");

        String _getUserPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _phoneNo = "+" + countryCodePicker.getFullNumber() + _getUserPhoneNumber;

        intent.putExtra("fullName", _fullName);
        intent.putExtra("username", _username);
        intent.putExtra("email", _email);
        intent.putExtra("password", _password);
        intent.putExtra("date", _date);
        intent.putExtra("gender", _gender);
        intent.putExtra("phoneNo", _phoneNo);

        startActivity(intent);
    }

    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();
        String checkNo = "[7-9][0-9]{9}";

        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid Phone Number!");
            return false;
        } else if (!val.matches(checkNo)) {
            phoneNumber.setError("Enter valid Phone Number!");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }
}