package com.salah.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.salah.R;
import com.salah.activity.RetailerDashboard;
import com.salah.model.User;
import com.salah.util.ConnectivityUtility;

public class ForgetPassword extends AppCompatActivity {

    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;
    String _phoneNo;
    RelativeLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);

        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNumber = findViewById(R.id.forget_password_phone_number);
        progressBar = findViewById(R.id.forget_password_progress_bar);

        //String _getUserPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        //_phoneNo = countryCodePicker.getSelectedCountryCodeWithPlus() + _getUserPhoneNumber;

    }

    public void verifyPhoneNumber(View view) {

        if(!ConnectivityUtility.isConnected(this)){
            ConnectivityUtility.showCustomDialog(this);
            return;
        }

        if (!validateFields()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        if(_phoneNumber.charAt(0)=='0'){
            _phoneNumber = _phoneNumber.substring(1);
        }
        String _phoneNo = countryCodePicker.getSelectedCountryCodeWithPlus() + _phoneNumber;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");
        Query query = reference.orderByChild("phoneNo").equalTo(_phoneNo);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.GONE);
                if(snapshot.exists()){
                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);
                    String _fullName = snapshot.child(_phoneNo).child("fullName").getValue(String.class);
                    String _username = snapshot.child(_phoneNo).child("username").getValue(String.class);
                    String _email = snapshot.child(_phoneNo).child("email").getValue(String.class);
                    String _password = snapshot.child(_phoneNo).child("password").getValue(String.class);
                    String _date = snapshot.child(_phoneNo).child("date").getValue(String.class);
                    String _gender = snapshot.child(_phoneNo).child("gender").getValue(String.class);

                    Toast.makeText(ForgetPassword.this, _phoneNo+" logged in as "+_fullName, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(), MakeSelection.class);

                    intent.putExtra("fullName", _fullName);
                    intent.putExtra("username", _username);
                    intent.putExtra("email", _email);
                    intent.putExtra("password", _password);
                    intent.putExtra("date", _date);
                    intent.putExtra("gender", _gender);
                    intent.putExtra("phoneNo", _phoneNo);
                    intent.putExtra("action", "update");
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(ForgetPassword.this, _phoneNo+" does not exists!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ForgetPassword.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validateFields() {
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