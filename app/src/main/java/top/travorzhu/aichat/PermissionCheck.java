package top.travorzhu.aichat;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.BLUETOOTH;
import static android.Manifest.permission.BLUETOOTH_ADMIN;
import static android.Manifest.permission.CHANGE_WIFI_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PermissionCheck {

    private final String[] permissions = {
            INTERNET,
            ACCESS_NETWORK_STATE,
            READ_PHONE_STATE,
            RECORD_AUDIO,
            WRITE_EXTERNAL_STORAGE,
            ACCESS_WIFI_STATE,
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION,
            CHANGE_WIFI_STATE,
            ACCESS_LOCATION_EXTRA_COMMANDS,
            BLUETOOTH,
            BLUETOOTH_ADMIN,
    };

    static void checkAndGet(Activity context, final String permission) {
        if (ContextCompat.checkSelfPermission(context, permission)
                != PackageManager.PERMISSION_GRANTED) {
            System.out.println(permission);
            ActivityCompat.requestPermissions(context, new String[]{permission},
                    0);
        }
    }

    void getAll(Activity context) {
        ActivityCompat.requestPermissions(context, permissions,
                0);
    }
}
