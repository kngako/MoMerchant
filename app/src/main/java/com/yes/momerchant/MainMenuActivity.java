package com.yes.momerchant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button vBtn = (Button) findViewById(R.id.view_account);
        vBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAccountButton(v);
            }
        });

        Button cBtn = (Button) findViewById(R.id.transact);
        cBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactButton(v);
            }
        });

        Button rBtn = (Button) findViewById(R.id.reports);
        rBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportButton(v);
            }
        });

        Button bBtn = (Button) findViewById(R.id.billing);
        bBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billingButton(v);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
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

    public void viewAccountButton(View v) {

        Intent intent = new Intent(this, ViewAccountActivity.class);

        /*EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);*/
        startActivity(intent);
    }

    public void transactButton(View v) {

        Intent intent = new Intent(this, TransactActivity.class);

        /*EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);*/
        startActivity(intent);
    }

    public void reportButton(View v) {

        Intent intent = new Intent(this, ReportActivity.class);

        /*EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);*/
        startActivity(intent);
    }

    public void billingButton(View v) {

        Intent intent = new Intent(this, BillingActivity.class);

        /*EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);*/
        startActivity(intent);
    }
}
