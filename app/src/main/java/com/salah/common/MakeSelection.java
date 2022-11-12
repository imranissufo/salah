package com.salah.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.salah.R;

public class MakeSelection extends AppCompatActivity {

    String phoneNo, whatToDO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_selection);

        phoneNo = getIntent().getStringExtra("phoneNo");
        whatToDO = getIntent().getStringExtra("whatToDO");
    }

    public void callOTPScreenFromMakeSelection(View view) {
        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
        intent.putExtra("phoneNo", phoneNo);
        intent.putExtra("whatToDO", "updateData");
        startActivity(intent);
    }

    public void callBackScreenFromMakeSelection(View view) {
        Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
        startActivity(intent);
        finish();
    }

}