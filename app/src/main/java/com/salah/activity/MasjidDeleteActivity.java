package com.salah.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.salah.R;
import com.salah.model.Masjid;

import java.util.HashMap;
import java.util.Map;

public class MasjidDeleteActivity extends AppCompatActivity {

    Masjid masjid;
    String action;
    TextView name, location, farj, zuhr, assr, isha, jumma;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_masjid_delete);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        name = findViewById(R.id.mjd_name);
        location = findViewById(R.id.mjd_location);
        farj = findViewById(R.id.mjd1_time);
        zuhr = findViewById(R.id.mjd2_time);
        assr = findViewById(R.id.mjd3_time);
        isha = findViewById(R.id.mjd4_time);
        jumma = findViewById(R.id.mjd5_time);

        action = getIntent().getStringExtra("action");
        masjid = (Masjid) getIntent().getSerializableExtra("masjid");
        name.setText(masjid.getName());
        location.setText(masjid.getLocation());
        farj.setText(masjid.getFajr());
        zuhr.setText(masjid.getZuhr());
        assr.setText(masjid.getAssr());
        isha.setText(masjid.getIsha());
        jumma.setText(masjid.getJumma());

    }

    public void delete(View view) {
        if (action.equals("DELETE")) {
            databaseReference.child("masjid").child(masjid.getId()).removeValue().addOnSuccessListener(unused -> {
                Toast.makeText(MasjidDeleteActivity.this, "Registo removido com sucesso!", Toast.LENGTH_LONG).show();
                init();
            }).addOnFailureListener(e -> Toast.makeText(MasjidDeleteActivity.this, "Erro na remocao do registo!", Toast.LENGTH_LONG).show());
        } else {
            Toast.makeText(MasjidDeleteActivity.this, "Erro na remocao do registo!", Toast.LENGTH_LONG).show();
            init();
        }
    }

    private void init() {
        Intent intent = new Intent(getApplicationContext(), MasgidAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void back(View view) {
        MasjidDeleteActivity.super.onBackPressed();
    }

    public void cancel(View view) {
        Intent intent = new Intent(getApplicationContext(), MasgidAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}