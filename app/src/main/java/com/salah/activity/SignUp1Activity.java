package com.salah.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.salah.R;
import com.salah.model.User;

public class SignUp1Activity extends AppCompatActivity {

    ImageView backBtn;
    Button next, login;
    TextView titleText, slideText;
    TextInputLayout fullName, username, email, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up1);

        mAuth = FirebaseAuth.getInstance();

        //Hooks for animation
        backBtn = findViewById(R.id.signup1_back_button);
        next = findViewById(R.id.signup1_next_button);
        login = findViewById(R.id.signup1_login_button);
        titleText = findViewById(R.id.signup1_title_text);
        slideText = findViewById(R.id.signup1_slide_text);

        fullName = findViewById(R.id.signup1_fullname);
        username = findViewById(R.id.signup1_username);
        email = findViewById(R.id.signup1_email);
        password = findViewById(R.id.signup1_password);

    }

    private boolean validateFullName() {
        String val = fullName.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            fullName.setError("O campo não pode estar vazio");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";
        if (val.isEmpty()) {
            username.setError("O campo não pode estar vazio");
            return false;
        } else if (val.length() > 20) {
            username.setError("O nome de usuário é muito grande!");
            return false;
//        } else if (!val.matches(checkspaces)) {
//            username.setError("No White spaces are allowed!");
//            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        if (val.isEmpty()) {
            email.setError("O campo não pode estar vazio");
            return false;
//        } else if (!val.matches(checkEmail)) {
//            email.setError("Invalid Email!");
//            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
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
            password.setError("O campo não pode estar vazio");
            return false;
//        } else if (!val.matches(checkPassword)) {
//            password.setError("Password should contain 4 characters!");
//            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void next(View view) {

        if (!validateFullName() | !validateUsername() | !validateEmail() | !validatePassword()) {
            return;
        }

        Intent intent = new Intent(getApplicationContext(), SignUp2Activity.class);

        String _fullName = fullName.getEditText().getText().toString().trim();
        String _userName = username.getEditText().getText().toString().trim();
        String _email = email.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        User user = new User();
        user.setFullName(_fullName);
        user.setUsername(_userName);
        user.setEmail(_email);
        user.setPassword(_password);
        //Pass all fields to the next activity
        intent.putExtra("user", user);

        //Add Shared Animation
        Pair[] pairs = new Pair[5];
        pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair<View, String>(next, "transition_next_btn");
        pairs[2] = new Pair<View, String>(login, "transition_login_btn");
        pairs[3] = new Pair<View, String>(titleText, "transition_title_text");
        pairs[4] = new Pair<View, String>(slideText, "transition_slide_text");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp1Activity.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }

    }

    public void login(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    public void init() {
        Intent intent = new Intent(getApplicationContext(), StartUpScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void back(View view) {
        SignUp1Activity.super.onBackPressed();
    }

    public void cancel(View view) {
        Intent intent = new Intent(getApplicationContext(), StartUpScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /*
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            admin();
        }
    }

    public void admin() {
        startActivity(new Intent(getApplicationContext(), MasgidAdminActivity.class));
        finish();
    }
     */

}