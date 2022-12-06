package com.salah.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.salah.R;
import com.salah.common.SignUpStep2;
import com.salah.model.Masjid;
import com.salah.util.TimeUtils;

public class MasjidForm6Activity extends AppCompatActivity {
    Masjid masjid;
    String action;
    TextView name, location, farj, zuhr, assr, isha;
    private DatabaseReference databaseReference;
    private DatabaseReference masjidReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_masjid_form6);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        name = findViewById(R.id.mjf6_name);
        location = findViewById(R.id.mjf6_location);
        farj = findViewById(R.id.mjf2_time);
        zuhr = findViewById(R.id.mjf3_time);
        assr = findViewById(R.id.mjf4_time);
        isha = findViewById(R.id.mjf5_time);

        action = getIntent().getStringExtra("action");
        masjid = (Masjid) getIntent().getSerializableExtra("masjid");
        name.setText(masjid.getName());
        location.setText(masjid.getLocation());
        farj.setText(masjid.getFajr());
        zuhr.setText(masjid.getZuhr());
        assr.setText(masjid.getAssr());
        isha.setText(masjid.getIsha());
    }

    public void next(View view) {

        masjidReference = databaseReference.child("masjid");
        masjidReference.child(masjid.getId()).setValue(masjid).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(MasjidForm6Activity.this, "Registo actualizado com sucesso!", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MasjidForm6Activity.this, "Erro na actualizacao do registo!", Toast.LENGTH_LONG).show();
            }
        });

        Intent intent = new Intent(getApplicationContext(), MasgidAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void back(View view) {
        MasjidForm6Activity.super.onBackPressed();
    }

    public void cancel(View view) {
        Intent intent = new Intent(getApplicationContext(), MasgidAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}