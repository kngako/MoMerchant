package com.yes.momerchant;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ShareActionProvider;

import com.yes.momerchant.R;
import com.yes.momerchant.providers.Transaction;
import com.yes.momerchant.providers.TransactionContract;
import com.yes.momerchant.util.CustomAdapter;

import java.util.List;

public class GeneratedReport extends ListActivity {
    private List<Transaction> transactions;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_report);

        ListView listView = getListView();

        transactions = TransactionContract.getTransactions(this);
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

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
