package com.salah.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.salah.R;
import com.salah.adapter.MasgidAdapter;
import com.salah.adapter.MasjidAdapter;
import com.salah.adapter.TimingsAdapter;
import com.salah.common.Login;
import com.salah.common.RetailerStartUpScreen;
import com.salah.model.Masjid;
import com.salah.model.Timings;

import java.util.ArrayList;
import java.util.Calendar;

public class Dashboard2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView timingsRecycler, masjidRecycler;
    TimingsAdapter timingsAdapter;
    MasjidAdapter masjidAdapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon, addIcon;
    LinearLayout contentView;
    int height, width;
    LocationManager locationManager;
    LocationListener locationListener;

    private ArrayList<Masjid> entries = new ArrayList<>();
    private DatabaseReference databaseReference;
    private DatabaseReference masjidReference;


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

        timingsRecycler();
        masjidRecycler();

        geolocation();
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

    private void timingsRecycler() {

        //timingsRecycler.setHasFixedSize(true);
        timingsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        timingsRecycler.setItemAnimator(null);

//        Query query = FirebaseDatabase.getInstance()
//                .getReference()
//                .child("timings")
//                .limitToLast(60);

        Calendar calendar;
        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        String d = String.valueOf(day);
        String m = String.valueOf(month);
        if (day < 10) {
            d = "0" + d;
        }
        if (month < 10) {
            m = "0" + m;
        }
        String code = m + d;
        int limit = day;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
                case12:
                {
                    limit = 31 - day + 1;
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11: {
                limit = 30 - day + 1;
            }
            break;
            case 2: {
                limit = 29 - day + 1;
            }
            break;
            default:
                break;
        }

        //code LIKE month
//        Query query = FirebaseDatabase.getInstance()
//                .getReference()
//                .child("timings").orderByChild("code")
//                .startAt(m).endAt(m+"\uf8ff");


        //fetch data from current day until the last day of the month
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("timings").orderByChild("month")
                .equalTo(m).limitToLast(limit);

        FirebaseRecyclerOptions<Timings> options =
                new FirebaseRecyclerOptions.Builder<Timings>()
                        .setQuery(query, Timings.class)
                        .build();

        timingsAdapter = new TimingsAdapter(options);
        timingsRecycler.setAdapter(timingsAdapter);
    }

    private void masjidRecycler() {
        masjidRecycler.setHasFixedSize(true);
        masjidRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        masjidRecycler.setItemAnimator(null);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("masjid");

        FirebaseRecyclerOptions<Masjid> options =
                new FirebaseRecyclerOptions.Builder<Masjid>()
                        .setQuery(query, Masjid.class)
                        .build();

        masjidAdapter = new MasjidAdapter(options);
        masjidRecycler.setAdapter(masjidAdapter);
        /*
        ArrayList<Location> mostViewedLocations = new ArrayList<>();
        mostViewedLocations.add(new Location(R.drawable.sujud, "Sujud", "Sajdah"));
        mostViewedLocations.add(new Location(R.drawable.dua, "Salah", "Salah"));

        masjidAdapter = new MasjidAdapter(mostViewedLocations);
        masjidRecycler.setAdapter(masjidAdapter);
                 */
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home: {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            break;

            case R.id.nav_all_categories: {
                Intent intent = new Intent(getApplicationContext(), MasgidActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.nav_login: {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
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
        masjidAdapter.startListening();

        //Remove crash on press back
        timingsRecycler.getRecycledViewPool().clear();
        masjidRecycler.getRecycledViewPool().clear();

        timingsAdapter.notifyDataSetChanged();
        masjidAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onStop() {
        super.onStop();
        timingsAdapter.stopListening();
        masjidAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override

            public boolean onQueryTextChange(String newText) {
                txtSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    private void txtSearch(String str) {
        //fetch data from current day until the last day of the month
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("timings").orderByChild("code")
                .startAt(str).endAt(str + "~");

        FirebaseRecyclerOptions<Timings> options =
                new FirebaseRecyclerOptions.Builder<Timings>()
                        .setQuery(query, Timings.class)
                        .build();

        timingsAdapter = new TimingsAdapter(options);
        timingsAdapter.startListening();
        timingsRecycler.setAdapter(timingsAdapter);
    }

    private void geolocation() {

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);//get the location locater service running
        locationListener = new LocationListener() {//listening in on your current location
            @Override
            public void onLocationChanged(Location location) {
                //when location is changed
                Log.i("location", location.toString());//printing location to logs
                Toast.makeText(Dashboard2Activity.this, location.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                //when location is enabled on mobile
            }

            @Override
            public void onProviderDisabled(String provider) {
                //when location is disabled on mobile
            }
        };

        //checking if the version of android is before marshmellow (SDK version 23)
        if (Build.VERSION.SDK_INT < 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            //checking whether we have permission to access the user location
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED) {
                //checking if permission is not granted. if not granted then ask for it.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                //this asks for permission which is stored in an array String
            } else {
                //if we already have permission
                //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                //ou

//                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                double longitude = location.getLongitude();
//                double latitude = location.getLatitude();
//                Toast.makeText(DashboardActivity.this, " longitude: "+longitude+" latitude: "+latitude, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);//after getting permission get locationManager
                if (locationManager != null)// prevent NullPointerException
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

}