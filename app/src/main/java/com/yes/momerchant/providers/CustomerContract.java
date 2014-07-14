package com.yes.momerchant.providers;

import android.content.ContentResolver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by kgothatso on 2014/07/12.
 */
public class CustomerContract {
    private static final String TAG = "CustomerContract";

    public static final String ACCOUNT_TYPE = "com.yes.momerchant";

    public static final String LIST_NAME = "customers";
    public static final String ITEM_NAME = "customer";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "_name";
    public static final String COLUMN_NUMBER = "_number";

    public static final String AUTHORITY = "com.yes.momerchant.providers.CustomerContentProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + LIST_NAME);
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + LIST_NAME;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + ITEM_NAME;

    public static final int NUM_ID = 0;
    public static final int NUM_NAME = 1;
    public static final int NUM_NUMBER = 2;


    public static final String[] PROJECTION = new String[] {
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_NUMBER
    };

    /**
     * Synchronisation states
     */
    public static final String SYNC_INSERTED = "inserted";
    public static final String SYNC_UPLOAD_READY = "upload";
    public static final String SYNC_UPLOAD_STARTED = "uploadPicture";
    public static final String SYNC_UPLOADED = "uploaded";
    public static final String SYNC_SYNCHRONISED = "synchronised";
    public static final String SYNC_DELETE = "delete";

    /**
     * DDL queries
     */
    private static final String DATABASE_CREATE = "create table "
            + LIST_NAME + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text, "
            + COLUMN_NUMBER + " text"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(CustomerContract.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + LIST_NAME);
        onCreate(database);
    }
}
