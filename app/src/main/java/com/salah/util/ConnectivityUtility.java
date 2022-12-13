package com.salah.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;

import com.salah.activity.MasgidAdminActivity;

public class ConnectivityUtility {
    public static boolean isConnected(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifiInfo!=null && wifiInfo.isConnected())||(mobileInfo!= null && mobileInfo.isConnected())){
            return true;
        }
        return false;
    }

    public static void showCustomDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity.getApplicationContext());
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        activity.startActivity(new Intent(activity.getApplicationContext(), MasgidAdminActivity.class));
                        activity.finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
