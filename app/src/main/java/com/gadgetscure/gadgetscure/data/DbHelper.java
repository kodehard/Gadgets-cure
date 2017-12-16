package com.gadgetscure.gadgetscure.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.gadgetscure.gadgetscure.data.DbContract.DbEntry;
/**
 * Created by awasthi's on 12/16/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = DbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "orders.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link DbHelper}.
     *
     * @param context of the app
     */
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_RECEIPT_TABLE =  "CREATE TABLE " + DbContract.DbEntry.TABLE_NAME + " ("
                + DbEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DbEntry.COLUMN_REF + " INTEGER NOT NULL, "
                + DbEntry.COLUMN_DEVICE + " TEXT NOT NULL, "
                + DbEntry.COLUMN_ISSUE + " TEXT NOT NULL, "
                + DbEntry.COLUMN_DATE + " TEXT);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_RECEIPT_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
