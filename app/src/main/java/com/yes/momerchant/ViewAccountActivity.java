package com.yes.momerchant;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.yes.momerchant.providers.Transaction;
import com.yes.momerchant.providers.TransactionContract;
import com.yes.momerchant.util.CustomAdapter;

import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ViewAccountActivity extends ListActivity implements AdapterView.OnItemClickListener/*LoaderManager.LoaderCallbacks<Cursor>*/ {
    private static final String TAG = "ViewAccountActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account);

        ListView listView = getListView();

        List<Transaction> transactions = TransactionContract.getTransactions(this);
        int balance = 0;
        for(Transaction tr: transactions)
        {
            balance += Integer.parseInt(tr.getAmount().substring(2));
        }

        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.transaction_row, transactions);
        listView.setAdapter(customAdapter);

        TextView bView = (TextView) findViewById(R.id.amount);
        bView.setText(balance + "");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Opens the detail(pager) activity if an entry is clicked
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        /*Intent intent = new Intent(this, DetailPagerActivity.class);
        intent.putExtra(KEY_LIST_INDEX, position);
        startActivity(intent);*/
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
