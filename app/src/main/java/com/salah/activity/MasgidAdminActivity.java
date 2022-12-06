package com.salah.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.salah.R;
import com.salah.adapter.MasgidAdapter;
import com.salah.model.Masjid;

import java.util.ArrayList;

public class MasgidAdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView masjidRecycler;
    MasgidAdapter masgidAdapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon, addIcon;
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
        setContentView(R.layout.activity_masgid_admin);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        loadEntries("");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        masjidRecycler = findViewById(R.id.mjadm_masjid_recycler);

        masjidRecycler.setHasFixedSize(true);
        masjidRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        masjidRecycler.setItemAnimator(null);

        ViewGroup.LayoutParams params = masjidRecycler.getLayoutParams();
        params.height = height;
        masjidRecycler.setLayoutParams(params);

        //Menu Hooks
        drawerLayout = findViewById(R.id.mjadm_drawer_layout);
        navigationView = findViewById(R.id.mjadm_navigation_view);
        menuIcon = findViewById(R.id.mjadm_menu);
        addIcon = findViewById(R.id.mjadm_add);

        navigationDrawer();

        searchInput = findViewById(R.id.mjadm_search_editText);
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

        masgidAdapter = new MasgidAdapter(this, entries);
        masjidRecycler.setAdapter(masgidAdapter);

    }

    private void loadEntries(String search) {
        entries = new ArrayList<Masjid>();
        masjidReference = databaseReference.child("masjid");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Masjid masjid = ds.getValue(Masjid.class);
                    masjid.setId(dataSnapshot.getKey());
                    if (search.isEmpty() || masjid.getName().toUpperCase().contains(search.toUpperCase())) {
                        entries.add(masjid);
                    }
                }
                masgidAdapter.setMasjids(entries);
                masgidAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("LogFragment", "loadLog:onCancelled", databaseError.toException());
            }
        };
        masjidReference.addValueEventListener(valueEventListener);
    }

    private void navigationDrawer() {
        //Naviagtion Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home: {
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
                finish();
            }
            break;

            case R.id.nav_masjid: {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            break;
            case R.id.nav_logout: {
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
                finish();
            }
            break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void addMasjid(View view) {
        Masjid masjid = new Masjid();
        Intent intent = new Intent(getApplicationContext(), MasjidForm1Activity.class);
        intent.putExtra("masjid", masjid);
        intent.putExtra("action", "ADD");
        startActivity(intent);
    }

    public void editMasjid(View view) {
        Masjid masjid = new Masjid();
        Intent intent = new Intent(getApplicationContext(), MasjidForm1Activity.class);
        intent.putExtra("masjid", masjid);
        intent.putExtra("action", "EDIT");
        startActivity(intent);
    }

}