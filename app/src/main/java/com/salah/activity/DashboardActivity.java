package com.salah.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.salah.R;
import com.salah.adapter.MasjidAdapter;
import com.salah.adapter.TimingAdapter;
import com.salah.model.Masjid;
import com.salah.model.Timings;
import com.salah.util.TimeUtils;

import java.util.ArrayList;
import java.util.Calendar;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RelativeLayout masjidProgressBar, timingsProgressBar;

    RecyclerView timingsRecycler, masjidRecycler;
    LinearLayout timmingLayout;
    TimingAdapter timingAdapter;
    MasjidAdapter masgidAdapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon, addIcon;
    LinearLayout contentView;
    int height, width;
    LocationManager locationManager;
    LocationListener locationListener;

    TextInputEditText searchInput;
    String searchTxt;

    private ArrayList<Timings> timings = new ArrayList<>();
    private ArrayList<Masjid> entries = new ArrayList<>();
    private DatabaseReference databaseReference;
    private DatabaseReference masjidReference;
    private DatabaseReference timingsReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        timmingLayout = findViewById(R.id.db_timming_layout);
        masjidProgressBar = findViewById(R.id.masjid_progress_bar);
        masjidProgressBar.setVisibility(View.VISIBLE);
        timingsProgressBar = findViewById(R.id.timings_progress_bar);
        timingsProgressBar.setVisibility(View.VISIBLE);

        loadEntries("");
        loadTimings();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        timingsRecycler = findViewById(R.id.db_timings_recycler);
        masjidRecycler = findViewById(R.id.db_masjid_recycler);

        timingsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        timingsRecycler.setItemAnimator(null);

        //masjidRecycler.setHasFixedSize(true);
        masjidRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        masjidRecycler.setItemAnimator(null);

        ViewGroup.LayoutParams params = masjidRecycler.getLayoutParams();
        params.height = height+100;
        masjidRecycler.setLayoutParams(params);

        //Menu Hooks
        drawerLayout = findViewById(R.id.db_drawer_layout);
        navigationView = findViewById(R.id.db_navigation_view);
        menuIcon = findViewById(R.id.db_menu);
        contentView = findViewById(R.id.db_content);
        addIcon = findViewById(R.id.db_add);

        navigationDrawer();
        startUpScreen();

        searchInput = findViewById(R.id.db_search_editText);
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

        masgidAdapter = new MasjidAdapter(entries);
        masjidRecycler.setAdapter(masgidAdapter);

        timingAdapter = new TimingAdapter(this, timings);
        timingsRecycler.setAdapter(timingAdapter);

    }

    private void loadEntries(String search) {
        masjidProgressBar.setVisibility(View.VISIBLE);
        if (search.isEmpty()) {
            timmingLayout.setVisibility(View.VISIBLE);
        } else {
            timmingLayout.setVisibility(View.GONE);
        }

        entries = new ArrayList<Masjid>();
        masjidReference = databaseReference.child("masjid");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                masjidProgressBar.setVisibility(View.GONE);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Masjid masjid = ds.getValue(Masjid.class);
//                    try {
//                        masjid.setEmpty();
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
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

    private void loadTimings() {
        timingsProgressBar.setVisibility(View.VISIBLE);

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        month++;
        String d = TimeUtils.addZero(day);
        String m = TimeUtils.addZero(month);
        String code = m + d;
        int limit = day;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12: {
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
        Query query = databaseReference
                .child("timings").orderByChild("month")
                .equalTo(m).limitToLast(limit);

        timings = new ArrayList<Timings>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                timingsProgressBar.setVisibility(View.GONE);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Timings t = ds.getValue(Timings.class);
                    if(t.getMonth().equals(m)){
                        timings.add(t);
                    }
                }
                timingAdapter.setTimings(timings);
                timingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("LogFragment", "loadLog:onCancelled", databaseError.toException());
                timingsProgressBar.setVisibility(View.GONE);
            }
        };
        query.addValueEventListener(valueEventListener);
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
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            break;

            case R.id.nav_login: {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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
    public void startUpScreen() {
        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StartUpScreenActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Remove crash on press back
        timingsRecycler.getRecycledViewPool().clear();
        masjidRecycler.getRecycledViewPool().clear();

        timingAdapter.notifyDataSetChanged();
        masgidAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void geolocation() {

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);//get the location locater service running
        locationListener = new LocationListener() {//listening in on your current location
            @Override
            public void onLocationChanged(Location location) {
                //when location is changed
                Log.i("location", location.toString());//printing location to logs
                Toast.makeText(DashboardActivity.this, location.toString(), Toast.LENGTH_LONG).show();
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