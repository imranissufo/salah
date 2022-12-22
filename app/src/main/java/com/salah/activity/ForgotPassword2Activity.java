package com.salah.activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.salah.R;
import com.salah.model.User;

public class ForgotPassword2Activity extends AppCompatActivity {

    User user;
    String _action;
    FirebaseAuth auth;
    TextView message;
    RelativeLayout progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_password2);

        auth = FirebaseAuth.getInstance();
        user = (User) getIntent().getSerializableExtra("user");
        _action = getIntent().getStringExtra("action");

        message= findViewById(R.id.forget_password_message);
        progressBar = findViewById(R.id.forget_password_progress_bar);
        /*
        FirebaseUser user = auth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });

         */

        sendEmail(null);
    }

    public void sendEmail(View view) {
        progressBar.setVisibility(View.VISIBLE);
        message.setText("Reenviando email de redifinição da senha.");
        auth.sendPasswordResetEmail(user.getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Log.d(TAG, "Email sent.");
                    Toast.makeText(ForgotPassword2Activity.this, "Password reset email sent successfully!", Toast.LENGTH_LONG).show();
                    message.setText("Email de redifinição da senha enviado com sucesso!");
                            /*
                            Intent intent = new Intent(getApplicationContext(), ForgotPassword2Activity.class);
                            intent.putExtra("user", user);
                            intent.putExtra("action", "UPDATE");
                            startActivity(intent);
                            finish();
                             */
                }
            }
        });
    }

    public void back(View view) {
        ForgotPassword2Activity.super.onBackPressed();
    }

    public void init() {
        Intent intent = new Intent(getApplicationContext(), StartUpScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void cancel(View view) {
        Intent intent = new Intent(getApplicationContext(), StartUpScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}