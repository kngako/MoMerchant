package com.yes.momerchant.providers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kgothatso on 2014/07/12.
 */
public class TransactionContract {

    private static final String TAG = "TransactionContract";

    public static final String ACCOUNT_TYPE = "com.yes.momerchant";

    public static final String LIST_NAME = "transactions";
    public static final String ITEM_NAME = "transaction";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "_date";
    public static final String COLUMN_CUSTOMER_PHONE_NUMBER = "_customer_phone_number";
    public static final String COLUMN_AMOUNT = "_amount_paid";

    public static final String AUTHORITY = "com.yes.momerchant.providers.TransactionContentProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + LIST_NAME);
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + LIST_NAME;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + ITEM_NAME;

    public static final int NUM_ID = 0;
    public static final int NUM_DATE = 1;
    public static final int NUM_CUSTOMER_PHONE_NUMBER = 2;
    public static final int NUM_AMOUNT = 3;


    public static final String[] PROJECTION = new String[] {
            COLUMN_ID,
            COLUMN_DATE,
            COLUMN_CUSTOMER_PHONE_NUMBER,
            COLUMN_AMOUNT
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
            + COLUMN_DATE + " text, "
            + COLUMN_CUSTOMER_PHONE_NUMBER + " text, "
            + COLUMN_AMOUNT + " text "
            + ");";

    private static final String DEMO_DATA = " INSERT INTO " + LIST_NAME + " (_date, _customer_phone_number, _amount_paid) VALUES (17-01-2014, 0834562849,R4050);";

    public static void onCreate(SQLiteDatabase database, Context c) {
        database.execSQL(DATABASE_CREATE + DEMO_DATA);

        /*for(Transaction tr: getDemoTransactions(c))
        {
            insertTransaction(c, tr);
        }*/
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion,Context c) {
        Log.w(CustomerContract.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + LIST_NAME);
        onCreate(database, c);
    }

    public static List<Transaction> getTransactions(Context c)
    {
        List<Transaction> transactionList = new ArrayList<Transaction>();
        String selectQuery = "SELECT * FROM " + TransactionContract.LIST_NAME;//query to search appointment by title
        SQLiteDatabase db = new DatabaseHelper(c).getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do
            {
                Log.i("Kgothatso", cursor.getString(1));
                Transaction transaction = new Transaction(cursor.getString(1), cursor.getString(2), cursor.getString(3), Long.parseLong(cursor.getString(0)));
                transactionList.add(transaction);
            } while(cursor.moveToNext());
        }

        return transactionList;
    }

    public static List<Transaction> getDemoTransactions(Context c)
    {
        List<Transaction> transactionList = new ArrayList<Transaction>();

        transactionList.add(new Transaction("17-09-2014", "0739383807" , "R 1450", new Long(3)));
        transactionList.add(new Transaction("18-09-2014", "0839384607" , "R 257", new Long(57)));
        transactionList.add(new Transaction("15-09-2014", "0736384677" , "R 7573", new Long(73)));
        transactionList.add(new Transaction("12-09-2014", "0733683807" , "R 255", new Long(246)));
        transactionList.add(new Transaction("19-09-2014", "0257283807" , "R 31", new Long(642)));
        transactionList.add(new Transaction("11-09-2014", "0739325725" , "R 646", new Long(427)));

        return transactionList;
    }

    public static void insertTransaction(Context c, Transaction tData)
    {
        ContentResolver cr = c.getContentResolver();

        ContentValues values = new ContentValues();

        values.put(TransactionContract.COLUMN_AMOUNT, tData.getAmount());
        values.put(TransactionContract.COLUMN_CUSTOMER_PHONE_NUMBER, tData.getCustomerPhoneNumber());
        values.put(TransactionContract.COLUMN_DATE, tData.getDate());

        cr.insert(CONTENT_URI, values);


    }
}
