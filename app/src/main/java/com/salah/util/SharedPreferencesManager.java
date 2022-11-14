package com.salah.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.salah.model.User;

import java.util.HashMap;

public class SharedPreferencesManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGGED_IN = "_isLoggedIn";
    public static final String FULLNAME = "_fullName";
    public static final String USERNAME = "_username";
    public static final String EMAIL = "_email";
    public static final String PASS = "_password";
    public static final String DATE = "_date";
    public static final String GENDER = "_gender";
    public static final String PHONE = "_phoneNo";

    public SharedPreferencesManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String fullName, String username, String email, String phoneNo, String password, String date, String gender) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(FULLNAME, fullName);
        editor.putString(USERNAME, username);
        editor.putString(EMAIL, email);
        editor.putString(PASS, password);
        editor.putString(DATE, date);
        editor.putString(GENDER, gender);
        editor.putString(PHONE, phoneNo);

        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> userDetails = new HashMap<>();
        userDetails.put(FULLNAME, sharedPreferences.getString(FULLNAME, null));
        userDetails.put(USERNAME, sharedPreferences.getString(USERNAME, null));
        userDetails.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        userDetails.put(PASS, sharedPreferences.getString(PASS, null));
        userDetails.put(DATE, sharedPreferences.getString(DATE, null));
        userDetails.put(GENDER, sharedPreferences.getString(GENDER, null));
        userDetails.put(PHONE, sharedPreferences.getString(PHONE, null));

        return userDetails;
    }

    public User getUser() {
        String fullName = sharedPreferences.getString(FULLNAME, null);
        String username = sharedPreferences.getString(USERNAME, null);
        String email = sharedPreferences.getString(EMAIL, null);
        String password = sharedPreferences.getString(PASS, null);
        String date = sharedPreferences.getString(DATE, null);
        String gender = sharedPreferences.getString(GENDER, null);
        String phone = sharedPreferences.getString(PHONE, null);

        User user = new User(fullName,username,email,phone,password,date,gender);
        return user;
    }

    public boolean isLoggedIn() {
        if (sharedPreferences.getBoolean(IS_LOGGED_IN, false)) {
            return true;
        } else {
            return false;
        }
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }
}
