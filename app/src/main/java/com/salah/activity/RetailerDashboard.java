package com.salah.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.textfield.TextInputLayout;
import com.salah.R;
import com.salah.common.SignUp;
import com.salah.model.User;
import com.salah.util.SharedPreferencesManager;

public class RetailerDashboard extends AppCompatActivity {

//    ChipNavigationBar chipNavigationBar;
    TextInputLayout fullname, username, email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_dashboard);

        fullname = findViewById(R.id.rd_fullname);
        username = findViewById(R.id.rd_username);
        email = findViewById(R.id.rd_email);
        phone = findViewById(R.id.rd_phone);

        SharedPreferencesManager manager = new SharedPreferencesManager(this);
        User user = manager.getUser();

        fullname.getEditText().setText(user.getFullName());
        username.getEditText().setText(user.getUsername());
        email.getEditText().setText(user.getEmail());
        phone.getEditText().setText(user.getPhoneNo());

/*
        chipNavigationBar = findViewById(R.id.bottom_nav_menu);
        chipNavigationBar.setItemSelected(R.id.bottom_nav_dashboard,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new RetailerDashboardFragment()).commit();
        bottomMenu();

 */
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