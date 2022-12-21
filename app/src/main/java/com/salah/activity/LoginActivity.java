package com.salah.activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.salah.R;
import com.salah.common.ForgetPassword;
import com.salah.model.User;
import com.salah.util.SharedPreferencesManager;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout password, email;
    RelativeLayout progressBar;
    CheckBox rememberMe;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        progressBar = findViewById(R.id.login_progress_bar);
        rememberMe = findViewById(R.id.remember_me);


        if (!isConnected(this)) {
            showCustomDialog();
        }

        SharedPreferencesManager manager = new SharedPreferencesManager(LoginActivity.this);
        if (manager.isRememberMe()) {
            HashMap<String, String> rememberMe = manager.getRememberMe();
            email.getEditText().setText(rememberMe.get(SharedPreferencesManager.RM_EMAIL));
            password.getEditText().setText(rememberMe.get(SharedPreferencesManager.RM_PASS));
        }
    }

    public void back(View view) {
        LoginActivity.super.onBackPressed();
    }

    public void forgotPassword(View view) {
        Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
        startActivity(intent);
    }

    public void login(View view) {

        if (!isConnected(this)) {
            showCustomDialog();
            return;
        }

        if (!validateFields()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        String _email = email.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        if (rememberMe.isChecked()) {
            SharedPreferencesManager manager = new SharedPreferencesManager(LoginActivity.this);
            manager.setRememberMe(_email, _password);
        }

        signIn(_email, _password);
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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

    private boolean isConnected(LoginActivity login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected())) {
            return true;
        }
        return false;
    }

    private boolean validateFields() {
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

    public void callSignUpFromLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp1Activity.class);
        startActivity(intent);
        finish();
    }

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(email, password);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null, null);
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            //reload();
        }
    }

    private void reload() {
        startActivity(new Intent(getApplicationContext(), MasgidAdminActivity.class));
        finish();
    }

    public void updateUI(String _email, String _password) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("user");
        Query query = reference.orderByChild("email").equalTo(_email);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.GONE);

                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        User user = ds.getValue(User.class);

                        if(user.getEmail().equals(_email)){
                            if (user.getPassword().equals(_password)) {
                                Toast.makeText(LoginActivity.this, _email + " logged in as " + user.getFullName(), Toast.LENGTH_LONG).show();

                                SharedPreferencesManager manager = new SharedPreferencesManager(LoginActivity.this);
                                manager.createSession(user);

                                reload();
                                break;
                            } else {
                                Toast.makeText(LoginActivity.this, _password + " does not match", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, _email + " does not exists!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}