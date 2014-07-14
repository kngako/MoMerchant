package com.yes.momerchant.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by kgothatso on 2014/07/13.
 */
public class CustomerContentProvider extends ContentProvider {

    private DatabaseHelper database;
    private static final int ITEMS = 1;
    private static final int ITEM_ID = 2;
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(CustomerContract.AUTHORITY, CustomerContract.LIST_NAME, ITEMS);
        sURIMatcher.addURI(CustomerContract.AUTHORITY, CustomerContract.LIST_NAME + "/#", ITEM_ID);
    }

    @Override
    public boolean onCreate() {
        database = new DatabaseHelper(getContext());
        return false;
    }

    @Override
    public String getType(Uri uri) {
        switch (sURIMatcher.match(uri)) {
            case ITEMS:
                return CustomerContract.CONTENT_TYPE;
            case ITEM_ID:
                return CustomerContract.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] columns, String selection,
                        String[] selectionArgs, String sortOrder) {

        checkColumns(columns);
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(CustomerContract.LIST_NAME);
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case ITEMS:
                break;
            case ITEM_ID: // Append ID
                queryBuilder.appendWhere(CustomerContract.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, columns, selection, selectionArgs, null, null, sortOrder);
        // Make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = database.getWritableDatabase();
        long id = 0;
        // Status set ready for upload, unless specified something else
        switch (uriType) {
            case ITEMS:
                id = db.insert(CustomerContract.LIST_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(CustomerContract.LIST_NAME + "/" + id);
    }

    /**
     * Don't really delete the rows. Update the syncstatus to deleted
     * The synchor process will remove the records from the database
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case ITEMS:
                rowsDeleted = db.delete(CustomerContract.LIST_NAME, selection, selectionArgs);
                break;
            case ITEM_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(CustomerContract.LIST_NAME, CustomerContract.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = db.delete(CustomerContract.LIST_NAME,
                            CustomerContract.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case ITEMS:
                rowsUpdated = db.update(CustomerContract.LIST_NAME, values, selection, selectionArgs);
                break;
            case ITEM_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(CustomerContract.LIST_NAME, values, CustomerContract.COLUMN_ID + "=" + id, null);
                } else {
                    rowsUpdated = db.update(CustomerContract.LIST_NAME, values,
                            CustomerContract.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;

    }

    private void checkColumns(String[] projection) {

        String[] available = CustomerContract.PROJECTION;
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // Check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }
}
