package com.example.warehousemanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.VideoView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    // Bottom Menu
    BottomNavigationView bottomNavigationView;
    // Video Background
    VideoView vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            // Set a Connectivity Manager
            final ConnectivityManager connMgr = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connMgr != null) {

                NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

                if (activeNetworkInfo != null) {
                    // There is a connection

                    if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        // Is connected to wifi
                        System.out.println("Wifi connected");
                    }
                }else {
                    // Is cnnected to the mobile provider's data plan
                    // Setup the alert builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Wifi Connection");
                    builder.setMessage("This app requires a Wifi connection. Please Connect to a Wifi network to use this app.");

                    // Add a button
                    builder.setPositiveButton("OK", null);

                    // Create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }

        setContentView(R.layout.activity_main);

        // Hide app title
        getSupportActionBar().hide();

        // Initialize Video Background View
        vv = (VideoView)findViewById(R.id.videoView);

        Uri uri = Uri.parse("android.resource://com.example.warehousemanagementapp/res/"
                + R.raw.test);

        vv.setVideoURI(uri);
        vv.requestFocus();
        vv.setOnPreparedListener(mediaPlayer -> vv.start());

        // Video Loop
        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                vv.start(); // Needed to make transition seamless.
            }
        });

        // Bottom Navigation Initialization
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.page_1);
    }

    public void switchToQR() {
        // Switch to QR Scanner activity
        Intent switchActivityIntent = new Intent(MainActivity.this, QRScanner.class);
        startActivity(switchActivityIntent);
    }

    public void switchToList() {
        // Switch to View Products activity
        Intent switchActivityIntent = new Intent(MainActivity.this, ViewProducts.class);
        startActivity(switchActivityIntent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Bottom Menu Actions
        switch (item.getItemId()) {

            case R.id.page_2:
                Intent switchActivityIntentQR = new Intent(MainActivity.this, QRScanner.class);
                startActivity(switchActivityIntentQR);
                return true;

            case R.id.page_3:
                Intent switchActivityIntentList = new Intent(MainActivity.this, ViewProducts.class);
                startActivity(switchActivityIntentList);
                return true;
        }
        return false;
    }
}
