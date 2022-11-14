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
import com.salah.R;
import com.salah.activity.RetailerDashboard;
import com.salah.util.ConnectivityUtility;

public class SetNewPassword extends AppCompatActivity {

    RelativeLayout progressBar;
    String phoneNo;
    TextInputLayout password, confirmPassword;
    String _fullName, _phoneNo, _email, _username, _password, _date, _gender, _action, selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_set_new_password);

        progressBar = findViewById(R.id.set_new_password_progress_bar);
        password = findViewById(R.id.new_password);
        confirmPassword = findViewById(R.id.confirm_password);

        //Get all the data from Intent
//        _fullName = getIntent().getStringExtra("fullName");
//        _email = getIntent().getStringExtra("email");
//        _username = getIntent().getStringExtra("username");
//        _password = getIntent().getStringExtra("password");
//        _date = getIntent().getStringExtra("date");
//        _gender = getIntent().getStringExtra("gender");
        _phoneNo = getIntent().getStringExtra("phoneNo");

    }

    public void setNewPasswordBtn(View view) {
        if (!ConnectivityUtility.isConnected(this)) {
            ConnectivityUtility.showCustomDialog(this);
            return;
        }

        if (!validateFields()) {
            return;
        }

        password.setError(null);
        password.setErrorEnabled(false);
        confirmPassword.setError(null);
        confirmPassword.setErrorEnabled(false);

        progressBar.setVisibility(View.VISIBLE);

        _password = password.getEditText().getText().toString().trim();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");
        reference.child(_phoneNo).child("password").setValue(_password);
        Toast.makeText(SetNewPassword.this, _phoneNo + " password changed!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getApplicationContext(), ForgetPasswordSuccessMessage.class);
        startActivity(intent);
        finish();
    }

    private boolean validateFields() {
        return validatePassword();
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        String conf = confirmPassword.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
//        } else if (!val.matches(checkPassword)) {
//            password.setError("Password should contain 4 characters!");
//            return false;
        } else {
            if (val.equals(conf)) {
                password.setError(null);
                password.setErrorEnabled(false);
                return true;
            } else {
                confirmPassword.setError("Confirmation password dont match");
                return false;
            }
        }
    }
}