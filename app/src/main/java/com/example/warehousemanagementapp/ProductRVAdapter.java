package com.example.warehousemanagementapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductRVAdapter extends RecyclerView.Adapter<ProductRVAdapter.ViewHolder> {

    // Create variables for the arraylist, context, dbhandler and globaldb
    private ArrayList<ProductModal> productModalArrayList;
    private Context context;
    private com.example.warehousemanagementapp.DBHandler dbHandler;
    private com.example.warehousemanagementapp.GlobalDBHandler globalDBHandler;


    // Constructor
    public ProductRVAdapter(ArrayList<ProductModal> productModalArrayList, Context context) {
        this.productModalArrayList = productModalArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Layout file inflation with the recycler view items
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_rv_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Set data to the views of recycler view item
        ProductModal modal = productModalArrayList.get(position);
        holder.productSerialTV.setText(modal.getProductSerial());
        holder.productNameTV.setText(modal.getProductQnt());
        holder.priceTV.setText(modal.getProductName());
        holder.productQntTV.setText(modal.getPrice());
    }

    @Override
    public int getItemCount() {
        // Returning the size of the array list
        return productModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // Creating variables for the text views and button
        private TextView productSerialTV, productNameTV, priceTV, productQntTV;
        private Button deletebtn;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            // Initializing text views and button
            productSerialTV = itemView.findViewById(R.id.idTVProductSerial);
            productNameTV = itemView.findViewById(R.id.idTVProductName);
            priceTV = itemView.findViewById(R.id.idTVPrice);
            productQntTV = itemView.findViewById(R.id.idTVProductQuantity);
            deletebtn = itemView.findViewById(R.id.idTVDeleteButton);

            // Listener for the delete button
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHandler = new com.example.warehousemanagementapp.DBHandler(context);
                    globalDBHandler = new com.example.warehousemanagementapp.GlobalDBHandler();

                    // Calling deleteProduct method from dbhandler and globaldb classes
                    // to delete the product and reload the activity
                    dbHandler.deleteProduct(productSerialTV.getText().toString());
                    globalDBHandler.deleteProduct(productSerialTV.getText().toString());

                    Toast.makeText(context, "Product Deleted", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, ViewProducts.class);
                    context.startActivity(i);
                }
            });
        }
    }
}

