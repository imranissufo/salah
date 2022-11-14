package com.salah.common;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.salah.R;
import com.salah.util.ConnectivityUtility;

public class SetNewPassword extends AppCompatActivity {

    RelativeLayout progressBar;
    String phoneNo;
    TextInputLayout password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_set_new_password);

        progressBar = findViewById(R.id.set_new_password_progress_bar);
        password = findViewById(R.id.new_password);
        confirmPassword = findViewById(R.id.confirm_password);
        phoneNo = getIntent().getStringExtra("phoneNo");
    }

    public void setNewPasswordBtn(View view) {
        if(!ConnectivityUtility.isConnected(this)){
            ConnectivityUtility.showCustomDialog(this);
            return;
        }

        if (!validateFields()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
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
            if(val.equals(conf)) {
                password.setError(null);
                password.setErrorEnabled(false);
                return true;
            }else{
                confirmPassword.setError("Confirmation password dont match");
                return false;
            }
        }
    }
}