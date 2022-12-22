package com.salah.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;
import com.salah.R;
import com.salah.model.User;

public class SignUp3Activity extends AppCompatActivity {

    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;
    ScrollView scrollView;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up3);

        user = (User) getIntent().getSerializableExtra("user");

        //Hooks
        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNumber = findViewById(R.id.signup3_phone_number);
        scrollView = findViewById(R.id.signup3_scroll_view);
    }

    public void next(View view) {

        if (!validatePhoneNumber()) {
            return;
        }

        Intent intent = new Intent(getApplicationContext(), SignUp4Activity.class);

        String _getUserPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        //String _phoneNo = "+" + countryCodePicker.getFullNumber() + _getUserPhoneNumber;
        String _phoneNo = countryCodePicker.getSelectedCountryCodeWithPlus() + _getUserPhoneNumber;

        user.setPhoneNo(_phoneNo);
        intent.putExtra("user", user);
        intent.putExtra("action", "CREATE"); // This is to identify that which action should OTP perform after verification.

        //Add Transition
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(scrollView, "SignUpActivity");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp3Activity.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();
        String checkNo = "[7-9][0-9]{9}";

        if (val.isEmpty()) {
            phoneNumber.setError("Insira um número de telefone válido!");
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

    public void init() {
        Intent intent = new Intent(getApplicationContext(), StartUpScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void back(View view) {
        SignUp3Activity.super.onBackPressed();
    }

    public void cancel(View view) {
        Intent intent = new Intent(getApplicationContext(), StartUpScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}