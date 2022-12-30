package com.salah.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.salah.R;
import com.salah.model.User;
import com.salah.util.ValidationUtils;

public class UserBlockActivity extends AppCompatActivity {

    TextInputLayout fullName, username, email, phoneNumber,date, gender;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_block);

        user = (User) getIntent().getSerializableExtra("user");

        fullName = findViewById(R.id.user_fullname);
        username = findViewById(R.id.user_username);
        email = findViewById(R.id.user_email);
        phoneNumber = findViewById(R.id.user_phone);
        date = findViewById(R.id.user_date);
        gender = findViewById(R.id.user_gender);

        fullName.getEditText().setText(user.getFullName());
        username.getEditText().setText(user.getUsername());
        email.getEditText().setText(user.getEmail());
        phoneNumber.getEditText().setText(user.getPhoneNo());
        date.getEditText().setText(user.getDate());
        ((AutoCompleteTextView) gender.getEditText()).setText(user.getGender());

    }

    public void next(View view) {

        user.setStatus("BLOCKED");
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("user");
        //reference.child(user.getPhoneNo()).setValue(user);

        reference.child(user.getPhoneNo()).updateChildren(user.toMap()).addOnSuccessListener(unused -> {
            Toast.makeText(UserBlockActivity.this, "Conta bloqueada com sucesso!", Toast.LENGTH_LONG).show();
            init();
        }).addOnFailureListener(e -> Toast.makeText(UserBlockActivity.this, "Erro ao bloaquear a conta!", Toast.LENGTH_LONG).show());
    }

    public void init() {
        Intent intent = new Intent(getApplicationContext(), UserAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void back(View view) {
        UserBlockActivity.super.onBackPressed();
    }

    public void cancel(View view) {
        Intent intent = new Intent(getApplicationContext(), UserAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}