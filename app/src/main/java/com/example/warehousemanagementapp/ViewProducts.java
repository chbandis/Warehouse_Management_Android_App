package com.example.warehousemanagementapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ViewProducts extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    // Bottom Menu
    BottomNavigationView bottomNavigationView;

    // Creating variables for our array list, dbHandler, adapter and recycler view.
    private ArrayList<com.example.warehousemanagementapp.ProductModal> productModalArrayList;
    private com.example.warehousemanagementapp.DBHandler dbHandler;
    private com.example.warehousemanagementapp.ProductRVAdapter productRVAdapter;
    private RecyclerView productsRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set a Connectivity Manager
        final ConnectivityManager connMgr = (ConnectivityManager) ViewProducts.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr != null) {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) {
                // There is a connection

                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // Is connected to wifi
                }
            }else {
                // Is cnnected to the mobile provider's data plan
                // Setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Wifi Connection");
                builder.setMessage("Viewing products requires a Wifi connection. Please Connect to a Wifi network and try again.");

                // Add a button
                builder.setPositiveButton("OK", null);

                // Create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();

                Intent switchActivityIntentQR = new Intent(ViewProducts.this, MainActivity.class);
                startActivity(switchActivityIntentQR);
            }
        }

        setContentView(R.layout.activity_view_products);

        // Hide app title
        getSupportActionBar().hide();

        // Initializing our all variables.
        productModalArrayList = new ArrayList<>();
        dbHandler = new com.example.warehousemanagementapp.DBHandler(ViewProducts.this);

        // Getting our product arraylist from db handler class.
        productModalArrayList = dbHandler.viewProducts();

        // On below line passing our array lost to our adapter class.
        productRVAdapter = new com.example.warehousemanagementapp.ProductRVAdapter(productModalArrayList, ViewProducts.this);
        productsRV = findViewById(R.id.idRVProducts);

        // Setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new
                LinearLayoutManager(ViewProducts.this, RecyclerView.VERTICAL, false);

        productsRV.setLayoutManager(linearLayoutManager);

        // Setting our adapter to recycler view.
        productsRV.setAdapter(productRVAdapter);

        // Bottom Navigation Initialization
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.page_3);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Bottom Menu Actions
        switch (item.getItemId()) {

            case R.id.page_1:
                Intent switchActivityIntentQR = new Intent(ViewProducts.this, MainActivity.class);
                startActivity(switchActivityIntentQR);
                return true;

            case R.id.page_2:
                Intent switchActivityIntentList = new Intent(ViewProducts.this, QRScanner.class);
                startActivity(switchActivityIntentList);
                return true;
        }
        return false;
    }
}
