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

public class MasjidForm7Activity extends AppCompatActivity {

    Masjid masjid;
    String action;
    TextView name, location, farj, zuhr, assr, isha, jumma;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_masjid_form7);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        name = findViewById(R.id.mjf7_name);
        location = findViewById(R.id.mjf7_location);
        farj = findViewById(R.id.mjf2_time);
        zuhr = findViewById(R.id.mjf3_time);
        assr = findViewById(R.id.mjf4_time);
        isha = findViewById(R.id.mjf5_time);
        jumma = findViewById(R.id.mjf6_time);

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


        if(action.equals("EDIT")){
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/masjid/" + masjid.getId(), masjid.toMap());
            //masjidReference = databaseReference.child("masjid");
            //masjidReference.child(masjid.getId()).updateChildren(masjid.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            databaseReference.updateChildren(childUpdates).addOnSuccessListener(unused -> {
                Toast.makeText(MasjidForm7Activity.this, "Registo actualizado com sucesso!", Toast.LENGTH_LONG).show();
                init();
            }).addOnFailureListener(e -> Toast.makeText(MasjidForm7Activity.this, "Erro na actualizacao do registo!", Toast.LENGTH_LONG).show());
        }else{
            DatabaseReference masjidReference = databaseReference.child("masjid");
            String key = masjidReference.push().getKey();
            masjid.setId(key);
            masjidReference.child(key).updateChildren(masjid.toMap()).addOnSuccessListener(unused -> {
                Toast.makeText(MasjidForm7Activity.this, "Registo salvo com sucesso!", Toast.LENGTH_LONG).show();
                init();
            }).addOnFailureListener(e -> Toast.makeText(MasjidForm7Activity.this, "Erro ao salvar o registo!", Toast.LENGTH_LONG).show());
        }
    }

    private void init() {
        Intent intent = new Intent(getApplicationContext(), MasgidAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void back(View view) {
        MasjidForm7Activity.super.onBackPressed();
    }

    public void cancel(View view) {
        Intent intent = new Intent(getApplicationContext(), MasgidAdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}