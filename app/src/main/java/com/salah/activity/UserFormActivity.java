package com.salah.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.salah.R;
import com.salah.fragment.DatePickerFragment;
import com.salah.model.User;
import com.salah.util.ValidationUtils;

import java.util.Arrays;
import java.util.List;

public class UserFormActivity extends AppCompatActivity {

    TextInputLayout fullName, username, email, phoneNumber,date, gender;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_form);

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

        List items = Arrays.asList("Masculino", "Femenino");
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.dropdown_item, items);
        ((AutoCompleteTextView) gender.getEditText()).setAdapter(adapter);
    }

    public void onDateClick(View view) {
        DialogFragment newFragment = new DatePickerFragment(date);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void next(View view) {

        if (!ValidationUtils.validateField(fullName) | !ValidationUtils.validateField(username) | !ValidationUtils.validateEmail(email)) {
            return;
        }

        String _fullName = fullName.getEditText().getText().toString().trim();
        String _userName = username.getEditText().getText().toString().trim();
        String _email = email.getEditText().getText().toString().trim();
        String _phoneNo = phoneNumber.getEditText().getText().toString().trim();
        String _date = date.getEditText().getText().toString();
        String _gender = gender.getEditText().getText().toString();

        user.setFullName(_fullName);
        user.setUsername(_userName);
        user.setEmail(_email);
        user.setGender(_gender);
        user.setDate(_date);
        user.setPhoneNo(_phoneNo);

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("user");
        //reference.child(user.getPhoneNo()).setValue(user);

        reference.child(user.getPhoneNo()).updateChildren(user.toMap()).addOnSuccessListener(unused -> {
            Toast.makeText(UserFormActivity.this, "Registo salvo com sucesso!", Toast.LENGTH_LONG).show();
            init();
        }).addOnFailureListener(e -> Toast.makeText(UserFormActivity.this, "Erro ao salvar o registo!", Toast.LENGTH_LONG).show());
    }

    public void init() {
        Intent intent = new Intent(getApplicationContext(), UserAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void back(View view) {
        UserFormActivity.super.onBackPressed();
    }

    public void cancel(View view) {
        Intent intent = new Intent(getApplicationContext(), UserAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
