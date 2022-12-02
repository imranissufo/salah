package com.salah.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.salah.R;
import com.salah.adapter.MasgidAdapter;
import com.salah.adapter.MasjidAdapter;
import com.salah.model.Masjid;

import java.util.ArrayList;

public class MasgidActivity extends AppCompatActivity {

    RecyclerView masjidRecycler;
    MasgidAdapter masjidAdapter;
    int height, width;
    TextInputEditText searchInput;
    String searchTxt;

    private ArrayList<Masjid> entries = new ArrayList<>();
    private DatabaseReference databaseReference;
    private DatabaseReference masjidReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_masjid);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        loadEntries("");

        searchInput = findViewById(R.id.mj_search_editText);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchTxt = charSequence.toString();
                loadEntries(searchTxt);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        masjidRecycler = findViewById(R.id.mj_rv);
        masjidRecycler.setHasFixedSize(true);
        masjidRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        masjidRecycler.setItemAnimator(null);

        ViewGroup.LayoutParams params = masjidRecycler.getLayoutParams();
        params.height = height;
        masjidRecycler.setLayoutParams(params);

        masjidAdapter = new MasgidAdapter(entries);
        masjidRecycler.setAdapter(masjidAdapter);

    }

    private void loadEntries(String search) {
        entries = new ArrayList<Masjid>();
        masjidReference = databaseReference.child("masjid");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Masjid masjid = ds.getValue(Masjid.class);
                    if (search.isEmpty() || masjid.getName().toUpperCase().contains(search.toUpperCase())) {
                        entries.add(masjid);
                        masjidAdapter.setMasjids(entries);
                        masjidAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("LogFragment", "loadLog:onCancelled", databaseError.toException());
            }
        };
        masjidReference.addValueEventListener(valueEventListener);
    }

    public void onBackBtnClick(View view){
        MasgidActivity.super.onBackPressed();
    }
}