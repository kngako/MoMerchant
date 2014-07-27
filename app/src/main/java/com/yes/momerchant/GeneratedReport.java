package com.yes.momerchant;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ShareActionProvider;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.yes.momerchant.R;
import com.yes.momerchant.providers.Transaction;
import com.yes.momerchant.providers.TransactionContract;
import com.yes.momerchant.util.CustomAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GeneratedReport extends ListActivity {
    private List<Transaction> transactions;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_report);

        ListView listView = getListView();

        int balance = 0;
        // TODO: Implement the limited element list...
        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Transaction");
            query.whereEqualTo("merchant", ParseUser.getCurrentUser().getObjectId());
            query.whereGreaterThanOrEqualTo("createdAt", getIntent().getStringExtra(ReportActivity.START_DATE));
            query.whereLessThanOrEqualTo("createdAt", getIntent().getStringExtra(ReportActivity.END_DATE));
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

        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.transaction_row, transactions);
        listView.setAdapter(customAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.generated_report, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();

        // Return true to display menu
        return true;
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId())
        {
            case R.id.menu_item_share:
                Log.i("kfnr", "pressed");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
