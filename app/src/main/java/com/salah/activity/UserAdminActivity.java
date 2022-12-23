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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.salah.R;
import com.salah.adapter.MasgidAdapter;
import com.salah.adapter.UserAdapter;
import com.salah.model.User;

import java.util.ArrayList;

public class UserAdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RelativeLayout userProgressBar;

    RecyclerView userRecycler;
    UserAdapter userAdapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon, syncIcon;
    int height, width;

    TextInputEditText searchInput;
    String searchTxt;

    private ArrayList<User> entries = new ArrayList<>();
    private DatabaseReference databaseReference;
    private DatabaseReference userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_admin);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        //hooks
        userProgressBar = findViewById(R.id.usadm_progress_bar);
        userProgressBar.setVisibility(View.VISIBLE);

        userRecycler = findViewById(R.id.usadm_recycler);
        drawerLayout = findViewById(R.id.usadm_drawer_layout);
        navigationView = findViewById(R.id.usadm_navigation_view);
        menuIcon = findViewById(R.id.usadm_menu);
        syncIcon = findViewById(R.id.usadm_sync);
        searchInput = findViewById(R.id.usadm_search_editText);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        userRecycler.setHasFixedSize(true);
        userRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        userRecycler.setItemAnimator(null);

        //ViewGroup.LayoutParams params = userRecycler.getLayoutParams();
        //params.height = height+100;
        //userRecycler.setLayoutParams(params);

        userAdapter = new UserAdapter(this, entries);
        userRecycler.setAdapter(userAdapter);

        loadEntries("");
        navigationDrawer();
        sync();
        search();

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
        userProgressBar.setVisibility(View.VISIBLE);
        entries = new ArrayList<User>();
        userReference = databaseReference.child("user");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userProgressBar.setVisibility(View.GONE);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    user.setId(ds.getKey());
                    if (search.isEmpty() || user.getFullName().toUpperCase().contains(search.toUpperCase())) {
                        entries.add(user);
                    }
                }
                userAdapter.setUsers(entries);
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("LogFragment", "loadLog:onCancelled", databaseError.toException());
                userProgressBar.setVisibility(View.GONE);
            }
        };
        userReference.addValueEventListener(valueEventListener);
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
                Intent intent = new Intent(getApplicationContext(), MasgidAdminActivity.class);
                startActivity(intent);
                finish();
            }
            break;

            case R.id.nav_user: {
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

    public void addUser(View view) {
        User user = new User();
        Intent intent = new Intent(getApplicationContext(), MasgidAdminActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("action", "ADD");
        startActivity(intent);
    }

}