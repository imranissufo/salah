package com.salah.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.salah.R;
import com.salah.adapter.MasjidAdapter;
import com.salah.adapter.TimingsAdapter;
import com.salah.common.Login;
import com.salah.common.RetailerStartUpScreen;
import com.salah.model.Location;
import com.salah.model.Timings;
import com.salah.user.Categories;
import com.salah.util.CategoryAdapter;
import com.salah.util.FeaturedAdapter;
import com.salah.util.MostViewedAdapter;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    RecyclerView timingsRecycler, masjidRecycler;
    TimingsAdapter timingsAdapter;
    MasjidAdapter masjidAdapter;
    RecyclerView.Adapter adapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon, addIcon;
    LinearLayout contentView;
    int height, width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        timingsRecycler = findViewById(R.id.db_timings_recycler);
        masjidRecycler = findViewById(R.id.db_masjid_recycler);

        ViewGroup.LayoutParams params = masjidRecycler.getLayoutParams();
        params.height = height;
        masjidRecycler.setLayoutParams(params);

        //Menu Hooks
        drawerLayout = findViewById(R.id.db_drawer_layout);
        navigationView = findViewById(R.id.db_navigation_view);
        menuIcon = findViewById(R.id.db_menu);
        contentView = findViewById(R.id.db_content);
        addIcon = findViewById(R.id.db_add);

        navigationDrawer();
        retailerScreens();

        featuredRecycler();
        mostViewedRecycler();

    }

    private void navigationDrawer() {
        //Naviagtion Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void featuredRecycler() {
        /*
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Location(R.drawable.dua, "dua", "dua desc"));
        locations.add(new Location(R.drawable.hands, "hands", "hands desc"));
        locations.add(new Location(R.drawable.sujud, "sujud", "sujud desc"));
         */

        //timingsRecycler.setHasFixedSize(true);
        timingsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

//        Query query = FirebaseDatabase.getInstance()
//                .getReference()
//                .child("timings")
//                .limitToLast(60);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("timings");

        FirebaseRecyclerOptions<Timings> options =
                new FirebaseRecyclerOptions.Builder<Timings>()
                        .setQuery(query, Timings.class)
                        .build();

        timingsAdapter = new TimingsAdapter(options);
        timingsRecycler.setAdapter(timingsAdapter);
    }

    private void mostViewedRecycler() {
        masjidRecycler.setHasFixedSize(true);
        masjidRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ArrayList<Location> mostViewedLocations = new ArrayList<>();
        mostViewedLocations.add(new Location(R.drawable.sujud, "Sujud", "Sajdah"));
        mostViewedLocations.add(new Location(R.drawable.dua, "Salah", "Salah"));

        adapter = new MasjidAdapter(mostViewedLocations);
        masjidRecycler.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_all_categories:{
                Intent intent = new Intent(getApplicationContext(), Categories.class);
                startActivity(intent);
            }break;
            case R.id.nav_login:{
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }break;
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

    //Call the retailer Startup screen on it's OnClick
    public void retailerScreens() {
        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        timingsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timingsAdapter.stopListening();
    }
}