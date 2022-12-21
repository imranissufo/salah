package com.salah.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.salah.R;
import com.salah.common.ForgetPassword;
import com.salah.common.VerifyOTP;
import com.salah.model.User;

public class ForgotPassword2Activity extends AppCompatActivity {

    User user;
    TextView mobileDesc, emailDesc;
    String _action;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_password2);

        user = (User) getIntent().getSerializableExtra("user");
        _action = getIntent().getStringExtra("action");

        mobileDesc = findViewById(R.id.mobile_des);
        emailDesc = findViewById(R.id.mail_des);


        mobileDesc.setText(user.getPhoneNo());
        emailDesc.setText(user.getEmail());
    }

    public void onPhoneClick(View view) {
        Intent intent = new Intent(getApplicationContext(), ForgotPassword3Activity.class);

        intent.putExtra("user", user);
        intent.putExtra("action", _action);
        intent.putExtra("selection", "phone");
        startActivity(intent);
        finish();
    }

    public void onEmailClick(View view) {
        Intent intent = new Intent(getApplicationContext(), ForgotPassword3Activity.class);
        intent.putExtra("user", user);
        intent.putExtra("action", _action);
        intent.putExtra("selection", "email");
        startActivity(intent);
        finish();
    }

    public void back(View view) {
        ForgotPassword2Activity.super.onBackPressed();
    }

}