package com.salah.activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.salah.R;
import com.salah.model.User;

public class SignUp4Activity extends AppCompatActivity {

    ScrollView scrollView;
    User user;
    private FirebaseAuth mAuth;
    TextInputLayout fullName, username, email, password,phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up4);

        user = (User) getIntent().getSerializableExtra("user");

        mAuth = FirebaseAuth.getInstance();

        scrollView = findViewById(R.id.signup4_scroll_view);

        fullName = findViewById(R.id.signup4_fullname);
        username = findViewById(R.id.signup4_username);
        email = findViewById(R.id.signup4_email);
        password = findViewById(R.id.signup4_password);
        phoneNumber = findViewById(R.id.signup4_phone_number);

        fullName.getEditText().setText(user.getFullName());
        username.getEditText().setText(user.getUsername());
        email.getEditText().setText(user.getEmail());
        password.getEditText().setText(user.getPassword());
        phoneNumber.getEditText().setText(user.getPhoneNo());

    }

    public void next(View view) {
        createAccount(user.getEmail(), user.getPassword());
    }

    public void init() {
        Intent intent = new Intent(getApplicationContext(), StartUpScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void back(View view) {
        SignUp4Activity.super.onBackPressed();
    }

    public void cancel(View view) {
        Intent intent = new Intent(getApplicationContext(), StartUpScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp4Activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("user");
        //reference.child(user.getPhoneNo()).setValue(user);

        reference.child(user.getPhoneNo()).updateChildren(user.toMap()).addOnSuccessListener(unused -> {
            Toast.makeText(SignUp4Activity.this, "Registo salvo com sucesso!", Toast.LENGTH_LONG).show();
            init();
        }).addOnFailureListener(e -> Toast.makeText(SignUp4Activity.this, "Erro ao salvar o registo!", Toast.LENGTH_LONG).show());

    }

    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Email sent
                    }
                });
        // [END send_email_verification]
    }

    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent(getApplicationContext(), MasgidAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}