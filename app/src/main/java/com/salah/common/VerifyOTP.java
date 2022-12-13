package com.salah.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.salah.R;
import com.salah.activity.MasgidAdminActivity;
import com.salah.model.User;

import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {

    PinView pinFromUser;
    String codeBySystem;
    TextView otpDescriptionText;
    String _fullName, _phoneNo, _email, _username, _password, _date, _gender, _action,selection;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_otp);
        //hooks
        pinFromUser = findViewById(R.id.pin_view);
        otpDescriptionText = findViewById(R.id.otp_description_text);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //Get all the data from Intent
        _fullName = getIntent().getStringExtra("fullName");
        _email = getIntent().getStringExtra("email");
        _username = getIntent().getStringExtra("username");
        _password = getIntent().getStringExtra("password");
        _date = getIntent().getStringExtra("date");
        _gender = getIntent().getStringExtra("gender");
        _phoneNo = getIntent().getStringExtra("phoneNo");
        _action = getIntent().getStringExtra("action");
        selection = getIntent().getStringExtra("selection");
        otpDescriptionText.setText("Enter One Time Password Sent On\n" + _phoneNo);
        sendVerificationCodeToUser(_phoneNo);
    }

    private void sendVerificationCodeToUser(String phoneNo) {
        // [START start_phone_auth]
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth) //mAuth is defined on top
                .setPhoneNumber(phoneNo)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END start_phone_auth]

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                    Toast.makeText(VerifyOTP.this, "onCodeSent:"+s, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    Toast.makeText(VerifyOTP.this, "onVerificationCompleted", Toast.LENGTH_LONG).show();
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        pinFromUser.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(VerifyOTP.this, "Verification Completed!", Toast.LENGTH_LONG).show();
                            //Verification completed successfully here Either
                            // store the data or do whatever desire
                            if (_action.equals("update")) {
                                updateUser();
                            } else if (_action.equals("create")) {
                                createUser();
                            }
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(VerifyOTP.this, "Verification Not Completed! Try again.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void createUser() {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Users");
        //Create helperclass reference and store data using firebase
        User user = new User(_fullName, _username, _email, _phoneNo, _password, _date, _gender);
        reference.child(_phoneNo).setValue(user);
        //We will also create a Session here in next videos to keep the user logged In
        startActivity(new Intent(getApplicationContext(), MasgidAdminActivity.class));
        finish();
    }

    private void updateUser() {
        Intent intent = new Intent(getApplicationContext(), SetNewPassword.class);
        intent.putExtra("phoneNo", _phoneNo);
        startActivity(intent);
        finish();
    }

    public void callNextScreenFromOTP(View view) {
        String code = pinFromUser.getText().toString();
        if(!code.isEmpty()){
            verifyCode(code);
        }
    }

    public void goToHomeFromOTP(View view) {
        Intent intent = new Intent(getApplicationContext(), RetailerStartUpScreen.class);
        startActivity(intent);
        finish();
    }

}