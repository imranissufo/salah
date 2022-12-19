package com.salah.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
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
import com.salah.activity.MasgidAdminActivity;
import com.salah.util.SharedPreferencesManager;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    ImageView backBtn;
    TextInputLayout phoneNumber, password;
    CountryCodePicker countryCodePicker;
    RelativeLayout progressBar;
    CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        backBtn = findViewById(R.id.login_back_button);
        countryCodePicker = findViewById(R.id.login_country_code_picker);
        phoneNumber = findViewById(R.id.login_phone_number);
        password = findViewById(R.id.login_password);
        progressBar = findViewById(R.id.login_progress_bar);
        rememberMe = findViewById(R.id.remember_me);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login.super.onBackPressed();
            }
        });

        if(!isConnected(this)){
            showCustomDialog();
        }

        SharedPreferencesManager manager = new SharedPreferencesManager(Login.this);
        if(manager.isRememberMe()){
            HashMap<String, String> rememberMe = manager.getRememberMe();
            phoneNumber.getEditText().setText(rememberMe.get(SharedPreferencesManager.RM_PHONE));
            password.getEditText().setText(rememberMe.get(SharedPreferencesManager.RM_PASS));
            try {
                int countryCode = Integer.parseInt(rememberMe.get(SharedPreferencesManager.RM_COUNTRY));
                countryCodePicker.setCountryForPhoneCode(countryCode);
            }catch (Exception e){

            }
        }

    }

    public void callForgetPassword(View view){
        Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
        startActivity(intent);
    }

    public void letTheUserLoggedIn(View view){

        if(!isConnected(this)){
            showCustomDialog();
            return;
        }

        if(!validateFields()){
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        String _countryCode = countryCodePicker.getSelectedCountryCode();
        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        if(_phoneNumber.charAt(0)=='0'){
            _phoneNumber = _phoneNumber.substring(1);
        }

        String _phoneNo = countryCodePicker.getSelectedCountryCodeWithPlus() + _phoneNumber;

        if(rememberMe.isChecked()){
            SharedPreferencesManager manager = new SharedPreferencesManager(Login.this);
            manager.setRememberMe(_countryCode, _phoneNumber,_password);
        }

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

                    String systemPassword = snapshot.child(_phoneNo).child("password").getValue(String.class);
                    if(systemPassword.equals(_password)){
                        String fullName = snapshot.child(_phoneNo).child("fullName").getValue(String.class);
                        Toast.makeText(Login.this, _phoneNo+" logged in as "+fullName, Toast.LENGTH_LONG).show();

                        String _fullName = snapshot.child(_phoneNo).child("fullName").getValue(String.class);
                        String _username = snapshot.child(_phoneNo).child("username").getValue(String.class);
                        String _email = snapshot.child(_phoneNo).child("email").getValue(String.class);
                        String _password = snapshot.child(_phoneNo).child("password").getValue(String.class);
                        String _date = snapshot.child(_phoneNo).child("date").getValue(String.class);
                        String _gender = snapshot.child(_phoneNo).child("gender").getValue(String.class);

                        SharedPreferencesManager manager = new SharedPreferencesManager(Login.this);
                        manager.createSession(_fullName,_username,_email,_phoneNo,_password,_date,_gender);

                        //We will also create a Session here in next videos to keep the user logged In
                        startActivity(new Intent(getApplicationContext(), MasgidAdminActivity.class));
                        finish();
                    }else{
                        Toast.makeText(Login.this, _password+" does not match "+systemPassword, Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Login.this, _phoneNo+" does not exists!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), MasgidAdminActivity.class));
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean isConnected(Login login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifiInfo!=null && wifiInfo.isConnected())||(mobileInfo!= null && mobileInfo.isConnected())){
            return true;
        }
        return false;
    }

    private boolean validateFields(){
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

    public void callSignUpFromLogin(View view){
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
        finish();
    }


}