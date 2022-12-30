package com.salah.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.salah.R;
import com.salah.adapter.MasjidAdminAdapter;
import com.salah.model.Masjid;

import java.util.ArrayList;

public class MasgidAdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RelativeLayout masjidProgressBar;
    RecyclerView masjidRecycler;
    MasjidAdminAdapter masgidAdapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon, syncIcon;
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

        //hooks
        masjidProgressBar = findViewById(R.id.masjid_admin_progress_bar);
        masjidProgressBar.setVisibility(View.VISIBLE);

        masjidRecycler = findViewById(R.id.mjadm_masjid_recycler);
        drawerLayout = findViewById(R.id.mjadm_drawer_layout);
        navigationView = findViewById(R.id.mjadm_navigation_view);
        menuIcon = findViewById(R.id.mjadm_menu);
        syncIcon = findViewById(R.id.mjadm_sync);
        searchInput = findViewById(R.id.mjadm_search_editText);

        recycler();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        masjidRecycler.setHasFixedSize(true);
        masjidRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        masjidRecycler.setItemAnimator(null);

        //ViewGroup.LayoutParams params = masjidRecycler.getLayoutParams();
        //params.height = height+100;
        //masjidRecycler.setLayoutParams(params);

        masgidAdapter = new MasjidAdminAdapter(this, entries);
        masjidRecycler.setAdapter(masgidAdapter);

        loadEntries("");
        navigationDrawer();
        sync();
        search();


    }

    private void recycler() {
    }

    private void search() {
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
    }

    private void sync() {
        syncIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadEntries("");
            }
        });
    }

    private void loadEntries(String search) {
        masjidProgressBar.setVisibility(View.VISIBLE);
        entries = new ArrayList<Masjid>();
        masjidReference = databaseReference.child("masjid");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                masjidProgressBar.setVisibility(View.GONE);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Masjid masjid = ds.getValue(Masjid.class);
                    masjid.setId(ds.getKey());
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
                masjidProgressBar.setVisibility(View.GONE);
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

            case R.id.nav_user: {
                Intent intent = new Intent(getApplicationContext(), UserAdminActivity.class);
                startActivity(intent);
                finish();
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

}