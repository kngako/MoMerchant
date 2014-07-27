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
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.yes.momerchant.providers.Transaction;
import com.yes.momerchant.providers.TransactionContract;
import com.yes.momerchant.util.CustomAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ViewAccountActivity extends ListActivity implements AdapterView.OnItemClickListener/*LoaderManager.LoaderCallbacks<Cursor>*/ {
    private static final String TAG = "ViewAccountActivity";
    private List<Transaction> transactions;
    private ListView listView;

    private int count = 1;
    private final int maxItems = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account);

        listView = getListView();
        int balance = 0;

        // TODO: Implement the limited element list...
        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Transaction");
            query.whereEqualTo("merchant", ParseUser.getCurrentUser().getObjectId());
            query.orderByDescending("createdAt");

            transactions = new ArrayList<Transaction>();
            for(ParseObject p: query.find())
            {
                Transaction t = new Transaction(p.getCreatedAt().toString(), p.getString("CustomerPhoneNumber"), p.getString("amount"), Long.parseLong("9999"));
                balance += Integer.parseInt(t.getAmount().substring(2));
                transactions.add(t);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.transaction_row, transactions/*.subList(count * maxItems, (count + 1) * maxItems)*/);
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

    public void updateList()
    {
        count++;
        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.transaction_row, transactions.subList(count * maxItems, (count + 1) * maxItems));
        listView.setAdapter(customAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        /*Intent intent = new Intent(this, DetailPagerActivity.class);
        intent.putExtra(KEY_LIST_INDEX, position);
        startActivity(intent);*/
        TextView cNumber = (TextView) v.findViewById(R.id.cNumber);
        // TODO: Get Customer History
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            TextView cNumber = (TextView) view.findViewById(R.id.cNumber);
            // TODO: Send SMS to user...
            Toast.makeText(this, "Send SMS to " + cNumber.getText(), Toast.LENGTH_SHORT).show();
        }
        catch(Exception ex)
        {
            Log.i(TAG, ex.toString());
        }
    }
}
