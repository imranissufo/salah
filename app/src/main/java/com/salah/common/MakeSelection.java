package com.salah.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.salah.R;

public class MakeSelection extends AppCompatActivity {

    String _fullName, _username, _email, _password, _gender, _date, _phoneNo, _action;
    TextView mobileDesc, emailDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_make_selection);

        mobileDesc = findViewById(R.id.mobile_des);
        emailDesc = findViewById(R.id.mail_des);

        _fullName = getIntent().getStringExtra("fullName");
        _username = getIntent().getStringExtra("username");
        _email = getIntent().getStringExtra("email");
        _password = getIntent().getStringExtra("password");
        _gender = getIntent().getStringExtra("gender");
        _date = getIntent().getStringExtra("date");
        _phoneNo = getIntent().getStringExtra("phoneNo");
        _action = getIntent().getStringExtra("action");

        mobileDesc.setText(_phoneNo);
        emailDesc.setText(_email);
    }

    public void callOTPScreenFromMakeSelectionPhone(View view) {
        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);

        intent.putExtra("fullName", _fullName);
        intent.putExtra("username", _username);
        intent.putExtra("email", _email);
        intent.putExtra("password", _password);
        intent.putExtra("date", _date);
        intent.putExtra("gender", _gender);
        intent.putExtra("phoneNo", _phoneNo);
        intent.putExtra("action", _action);
        intent.putExtra("selection", "phone");
        startActivity(intent);
        finish();
    }

    public void callOTPScreenFromMakeSelectionEmail(View view) {
        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
        intent.putExtra("fullName", _fullName);
        intent.putExtra("username", _username);
        intent.putExtra("email", _email);
        intent.putExtra("password", _password);
        intent.putExtra("date", _date);
        intent.putExtra("gender", _gender);
        intent.putExtra("phoneNo", _phoneNo);
        intent.putExtra("action", _action);
        intent.putExtra("selection", "email");
        startActivity(intent);
        finish();
    }

    public void callBackScreenFromMakeSelection(View view) {
        Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
        startActivity(intent);
        finish();
    }

}