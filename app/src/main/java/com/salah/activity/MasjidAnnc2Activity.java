package com.salah.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

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
import com.salah.model.Masjid;

import java.util.HashMap;
import java.util.Map;

public class MasjidAnnc2Activity extends AppCompatActivity {

    Masjid masjid;
    String action;
    TextView name, fajrDate, fajrTime, assrDate, assrTime, ishaDate, ishaTime;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_masjid_annc2);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        action = getIntent().getStringExtra("action");
        masjid = (Masjid) getIntent().getSerializableExtra("masjid");

        name = findViewById(R.id.mja1_name);

        fajrDate = findViewById(R.id.mja1_fajr_date);
        fajrTime = findViewById(R.id.mja1_fajr_time);

        assrDate = findViewById(R.id.mja1_assr_date);
        assrTime = findViewById(R.id.mja1_assr_time);

        ishaDate = findViewById(R.id.mja1_isha_date);
        ishaTime = findViewById(R.id.mja1_isha_time);

        name.setText(masjid.getName());
        fajrDate.setText(masjid.getAnncFajrDate());
        fajrTime.setText(masjid.getAnncFajrTime());
        assrDate.setText(masjid.getAnncAssrDate());
        assrTime.setText(masjid.getAnncAssrTime());
        ishaDate.setText(masjid.getAnncIshaDate());
        ishaTime.setText(masjid.getAnncIshaTime());


    }

    public void next(View view) {

//        String key = mDatabase.child("posts").push().getKey();
//        Post post = new Post(userId, username, title, body);
//        Map<String, Object> postValues = post.toMap();
//
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/posts/" + key, postValues);
//        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
//
//        mDatabase.updateChildren(childUpdates);


        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/masjid/" + masjid.getId(), masjid.toMap());
        //masjidReference = databaseReference.child("masjid");
        //masjidReference.child(masjid.getId()).updateChildren(masjid.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
        databaseReference.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(MasjidAnnc2Activity.this, "Registo actualizado com sucesso!", Toast.LENGTH_LONG).show();
                init();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MasjidAnnc2Activity.this, "Erro na actualizacao do registo!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void init() {
        Intent intent = new Intent(getApplicationContext(), MasgidAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void back(View view) {
        MasjidAnnc2Activity.super.onBackPressed();
    }

    public void cancel(View view) {
        Intent intent = new Intent(getApplicationContext(), MasgidAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}