package com.yes.momerchant.providers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kgothatso on 2014/07/12.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cmoresync.db"; // TODO: Change NAME?
    private static final int DATABASE_VERSION = 1;
    private static Context c;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        c = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        TransactionContract.onCreate(db,c);
        CustomerContract.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        TransactionContract.onUpgrade(db, oldVersion, newVersion,c);
        CustomerContract.onUpgrade(db, oldVersion, newVersion);
    }

}
