package com.salah.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.salah.R;

public class MakeSelection extends AppCompatActivity {

    String phoneNo, whatToDO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_make_selection);

        phoneNo = getIntent().getStringExtra("phoneNo");
        whatToDO = getIntent().getStringExtra("whatToDo");
    }

    public void callOTPScreenFromMakeSelectionPhone(View view) {
        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
        intent.putExtra("phoneNo", phoneNo);
        intent.putExtra("selection", "phone");
        intent.putExtra("whatToDo", "updateData");
        startActivity(intent);
    }

    public void callOTPScreenFromMakeSelectionEmail(View view) {
        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
        intent.putExtra("phoneNo", phoneNo);
        intent.putExtra("selection", "email");
        intent.putExtra("whatToDo", "updateData");
        startActivity(intent);
    }

    public void callBackScreenFromMakeSelection(View view) {
        Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
        startActivity(intent);
        finish();
    }

}