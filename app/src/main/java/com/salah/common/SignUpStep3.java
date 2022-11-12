package com.salah.common;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;
import com.salah.R;

public class SignUpStep3 extends AppCompatActivity {

    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_step3);

        //Hooks
        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNumber = findViewById(R.id.signup_phone_number);
        scrollView = findViewById(R.id.signup_3rd_screen_scroll_view);
    }

    public void callVerifyOTPScreen(View view) {

        if (!validatePhoneNumber()) {
            return;
        }

        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);

        String _fullName = getIntent().getStringExtra("fullName");
        String _username = getIntent().getStringExtra("username");
        String _email = getIntent().getStringExtra("email");
        String _password = getIntent().getStringExtra("password");
        String _gender = getIntent().getStringExtra("gender");
        String _date = getIntent().getStringExtra("date");

        String _getUserPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        //String _phoneNo = "+" + countryCodePicker.getFullNumber() + _getUserPhoneNumber;
        String _phoneNo = countryCodePicker.getSelectedCountryCodeWithPlus() + _getUserPhoneNumber;

        intent.putExtra("fullName", _fullName);
        intent.putExtra("username", _username);
        intent.putExtra("email", _email);
        intent.putExtra("password", _password);
        intent.putExtra("date", _date);
        intent.putExtra("gender", _gender);
        intent.putExtra("phoneNo", _phoneNo);
        intent.putExtra("whatToDO", "createNewUser"); // This is to identify that which action should OTP perform after verification.

        //Add Transition
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(scrollView, "transition_OTP_screen");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUpStep3.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();
        String checkNo = "[7-9][0-9]{9}";

        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid Phone Number!");
            return false;
//        } else if (!val.matches(checkNo)) {
//            phoneNumber.setError("Enter valid Phone Number!");
//            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    public void onBackBtnClick(View view){
        SignUpStep3.super.onBackPressed();
    }

}