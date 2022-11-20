package com.example.warehousemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GlobalDBHandler extends AppCompatActivity {

    public void addNewProduct(String productSerial, String productName, String price, String productQnt) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            // Crete a new OKHttpClient
            OkHttpClient client = new OkHttpClient();

            // Parse php file from CRUDAPI path
            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://192.168.1.4/CRUDAPI/addProduct.php").newBuilder();

            // Set Query Parameters
            urlBuilder.addQueryParameter("PSerialNo", productSerial);
            urlBuilder.addQueryParameter("PName", productName);
            urlBuilder.addQueryParameter("Price", price);
            urlBuilder.addQueryParameter("PQnt", productQnt);

            String url = urlBuilder.build().toString();

            // Create the request
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            //Create a callback function for the request and execute it
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("onFailure", "Failed " + e.getMessage());
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try
                            {
                                System.out.println((response.body().string()));
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(String serialNo) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            // Crete a new OKHttpClient
            OkHttpClient client = new OkHttpClient();

            // Parse php file from CRUDAPI path
            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://192.168.1.4/CRUDAPI/deleteProduct.php").newBuilder();

            // Set Query Parameters
            urlBuilder.addQueryParameter("PSerialNo", serialNo);

            String url = urlBuilder.build().toString();

            // Create the request
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            //Create a callback function for the request and execute it
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    // Write some code here...
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                System.out.println((response.body().string()));
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}