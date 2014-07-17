package com.yes.momerchant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.yes.momerchant.providers.Transaction;
import com.yes.momerchant.providers.TransactionContract;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TransactActivity extends Activity {
    EditText rNumber ;
    EditText amount;
    EditText ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transact);

        Button vBtn = (Button) findViewById(R.id.execute);
        vBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactButton(v);
            }
        });

        rNumber = (EditText) findViewById(R.id.receiver_number);
        amount = (EditText) findViewById(R.id.amount);
        ref = (EditText) findViewById(R.id.reference);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer_interaction, menu);

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

    public void transactButton(View v)
    {

        Context c = getApplicationContext();
        String time = new SimpleDateFormat("dd/MM/yyyy").format(new Timestamp(new Date().getTime()));
        String num = rNumber.getText().toString();
        String am = "R " + amount.getText().toString();

        if(num.length() == 10)
        {
            TransactionContract.insertTransaction(c, new Transaction(time, num, am, null));

            finish();
        }
        else
        {
            Toast.makeText(this, "Enter proper values", Toast.LENGTH_SHORT).show();
        }
    }

}
