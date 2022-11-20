package com.example.warehousemanagementapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    // Creating a constant variables for the database.
    // Below variable is for the database name.
    private static final String DB_NAME = "warehousedb";

    // Below int is the database version
    private static final int DB_VERSION = 1;

    // Below variable is for the table name
    private static final String TABLE_NAME = "products";

    // Below variable is for the id column
    private static final String ID_COL = "PID";

    // Below variable is for the product serial column
    private static final String SERIALNO_COL = "PSerialNo";

    // Below variable id for the product name column
    private static final String PNAME_COL = "PName";

    // Below variable for the product price column
    private static final String PRICE_COL = "Price";

    // Below variable is for the product quantity column
    private static final String QUANTITY_COL = "PQnt";

    // Creating a constructor for the database handler
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQLite query creation and set column names along with their data types
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SERIALNO_COL + " TEXT,"
                + PNAME_COL + " TEXT,"
                + PRICE_COL + " TEXT,"
                + QUANTITY_COL + " TEXT)";

        // Execute above sql query
        db.execSQL(query);
    }

    // This method is used to add a new product to the sqlite database
    public void addNewProduct(String productSerial, String productName, String price, String productQnt) {
        // SQLite database variable creation and call writable method to add data to database
        SQLiteDatabase db = this.getWritableDatabase();

        // Calling CheckIfProductExists method to check if product exists in sqlite database
        boolean exists = CheckIfProductExists(TABLE_NAME,SERIALNO_COL,productSerial);

        if (exists == true){
            // If product exists update its quantity
            String Query = " Select " + QUANTITY_COL + " from " + TABLE_NAME + " where " + SERIALNO_COL + " = " + productSerial + " limit 1 ";
            Cursor cursorQnt = db.rawQuery(Query, null);
            int oldqnt;
            if (cursorQnt.moveToFirst()) {
                do {
                    oldqnt = Integer.parseInt(cursorQnt.getString(0));
                } while (cursorQnt.moveToNext());
                int scannedqnt = Integer.parseInt(productQnt);
                int newqnt = oldqnt + scannedqnt;
                ContentValues values = new ContentValues();
                values.put(QUANTITY_COL, newqnt);
                db.update(TABLE_NAME, values, SERIALNO_COL + " = ?" , new String[]{productSerial});
            }
            cursorQnt.close();
        } else {
            // Create a new variable for content values
            ContentValues values = new ContentValues();

            // Pass all values along with their key and value pair
            values.put(SERIALNO_COL, productSerial);
            values.put(PNAME_COL, productName);
            values.put(PRICE_COL, price);
            values.put(QUANTITY_COL, productQnt);

            // Pass content values to the table
            db.insert(TABLE_NAME, null, values);
        }

        // Close database connection
        db.close();
    }

    // This method is for reading all the products
    public ArrayList<ProductModal> viewProducts()
    {
        // SQLite database variable creation and call readable method to read data from database
        SQLiteDatabase db = this.getReadableDatabase();

        // Cursor creation with query to read data from database
        Cursor cursorProducts = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // New array list creation
        ArrayList<ProductModal> productModalArrayList = new ArrayList<>();

        // Move cursor to first position
        if (cursorProducts.moveToFirst()) {
            do {
                // Add the data from cursor to the array list
                productModalArrayList.add(new
                        ProductModal(cursorProducts.getString(1),
                        cursorProducts.getString(3),
                        cursorProducts.getString(4),
                        cursorProducts.getString(2)));
            } while (cursorProducts.moveToNext());
            // Move cursor to next
        }

        // Closing cursor and returning the arraylist
        cursorProducts.close();
        return productModalArrayList;
    }

    // Method for deleting a product
    public void deleteProduct(String serialNo) {

        // SQLite database variable creation and call writable method to add data to database
        SQLiteDatabase db = this.getWritableDatabase();

        // Method to delete the product by its serial number
        db.delete(TABLE_NAME, "PSerialNo=?", new String[]{serialNo});

        // Close database connection
        db.close();
    }

    public boolean CheckIfProductExists(String TableName, String dbfield, String fieldValue) {

        // SQLite database variable creation and call readable method to read data from database
        SQLiteDatabase db = this.getReadableDatabase();

        String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;

        // Cursor creation with the above query to read all the data from database with a specified value
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This method is called to check if the table exists already
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
