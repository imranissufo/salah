package com.salah.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.salah.R;
import com.salah.common.MakeSelection;
import com.salah.model.User;
import com.salah.util.ConnectivityUtility;
import com.salah.util.ValidationUtils;

public class ForgotPassword1Activity extends AppCompatActivity {

    TextInputLayout email;
    RelativeLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_password1);

        email = findViewById(R.id.fp1_email);
        progressBar = findViewById(R.id.forget_password_progress_bar);
    }

    public void next(View view) {

        if (!ConnectivityUtility.isConnected(this)) {
            ConnectivityUtility.showCustomDialog(this);
            return;
        }

        if (!validateFields()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        String _email = email.getEditText().getText().toString().trim();

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

                        if (user.getEmail().equals(_email)) {

                            Toast.makeText(ForgotPassword1Activity.this, _email + " found, " + user.getFullName(), Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(getApplicationContext(), ForgotPassword2Activity.class);
                            intent.putExtra("user", user);
                            intent.putExtra("action", "UPDATE");
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(ForgotPassword1Activity.this, _email + " does not exists!", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ForgotPassword1Activity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validateFields() {
        return ValidationUtils.validateEmail(email);
    }

    public void back(View view) {
        ForgotPassword1Activity.super.onBackPressed();
    }
}