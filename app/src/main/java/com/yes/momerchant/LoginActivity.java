package com.yes.momerchant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {
    private final String TAG = "LoginActivity";

    private EditText phoneNumber;
    private EditText mobilePin;

    private boolean debug = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn = (Button) findViewById(R.id.login_or_signup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton(v);
            }
        });

        phoneNumber = (EditText) findViewById(R.id.phone_number);
        mobilePin = (EditText) findViewById(R.id.mobile_pin);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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

    public void loginButton(View v) {
        // TODO: Valdate the input in a more systematic way...
        String phone = phoneNumber.getText().toString();
        String pin = mobilePin.getText().toString();
        Log.i(TAG, "Phone:" + phone + ".\nPin:" + pin + ".");
        if(phone.equals("0739383807") && pin.equals("12345") || debug) {

            Intent intent = new Intent(this, MainMenuActivity.class);

            /*EditText editText = (EditText) findViewById(R.id.edit_message);
            String message = editText.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);*/
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Bad Credentials", Toast.LENGTH_SHORT).show();
        }
    }
}
