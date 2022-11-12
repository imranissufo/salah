package com.salah.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;
import com.salah.R;
import com.salah.activity.RetailerDashboard;
import com.salah.model.User;

public class ForgetPassword extends AppCompatActivity {

    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;
    String _phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);

        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNumber = findViewById(R.id.forget_password_phone_number);

        String _getUserPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        _phoneNo = countryCodePicker.getSelectedCountryCodeWithPlus() + _getUserPhoneNumber;

    }

    public void verifyPhoneNumber(View view) {

        if (!validatePhoneNumber()) {
            return;
        }

        Intent intent = new Intent(getApplicationContext(), MakeSelection.class);
        intent.putExtra("phoneNo", _phoneNo);
        intent.putExtra("whatToDO", "updateData");
        startActivity(intent);
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
            FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
            DatabaseReference reference = rootNode.getReference("Users");
            reference.child(val);

            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    public void callBackScreenFromForgetPassword(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }

}