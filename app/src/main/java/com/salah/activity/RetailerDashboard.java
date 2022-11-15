package com.salah.activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;
import com.salah.R;
import com.salah.common.SignUp;
import com.salah.model.User;
import com.salah.util.SharedPreferencesManager;

public class RetailerDashboard extends AppCompatActivity {

    //ChipNavigationBar chipNavigationBar;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_dashboard);

        bottomNavigationView = findViewById(R.id.bottom_nav_menu);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        openFragment(RetailerHomeFragment.newInstance("",""));
                        return true;
                    case R.id.navigation_featured:
                        openFragment(RetailerFeaturedFragment.newInstance("",""));
                        return true;
                    case R.id.navigation_location:
                        openFragment(RetailerLocationFragment.newInstance("",""));
                        return true;
                    case R.id.navigation_settings:
                        openFragment(RetailerSettingsFragment.newInstance("",""));
                        return true;
                }
                return false;
            }
        });

/*
        chipNavigationBar = findViewById(R.id.bottom_nav_menu);
        chipNavigationBar.setItemSelected(R.id.bottom_nav_dashboard,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new RetailerDashboardFragment()).commit();
        bottomMenu();

 */
        //set initial fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RetailerHomeFragment()).commit();

    }

    private void openFragment(Fragment fragment) {
        Log.d(TAG, "openFragment: ");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //this is a helper class that replaces the container with the fragment. You can replace or add fragments.
        transaction.replace(R.id.fragment_container, fragment);
        //transaction.addToBackStack(null); //if you add fragments it will be added to the backStack. If you replace the fragment it will add only the last fragment
        transaction.commit(); // commit() performs the action
    }

    public void onBackBtnClick(View view){
        RetailerDashboard.super.onBackPressed();
    }


/*
    private void bottomMenu() {


        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.bottom_nav_dashboard:
                        fragment = new RetailerDashboardFragment();
                        break;
                    case R.id.bottom_nav_manage:
                        fragment = new RetailerManageFragment();
                        break;
                    case R.id.bottom_nav_profile:
                        fragment = new RetailerProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });


    }

 */

}