package com.yes.momerchant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.yes.momerchant.providers.Transaction;
import com.yes.momerchant.providers.TransactionContract;

import java.util.Date;
import java.util.List;


public class LoginActivity extends Activity {
    private final String TAG = "LoginActivity";

    private EditText phoneNumber;
    private EditText mobilePin;
    private Button login;
    private Button signup;
    public static Date createdAt = new Date();
    private static Context c;

    private boolean debug = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login_or_signup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton(v);
            }
        });

        signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupButton(v);
            }
        });

        phoneNumber = (EditText) findViewById(R.id.phone_number);
        mobilePin = (EditText) findViewById(R.id.mobile_pin);


        Parse.initialize(this, "2pJCMZpWtCL9ItTWfBkS8bKZ0l8ibdGCIzWLyKdf", "55R0K2H8qmpH9oBcKhdO2HAy76ZCFM4RcPUcn7Mz");

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();

        c = getApplicationContext();
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
        // TODO: Use Parse for server implementations...
        login.setFocusable(false);
        login.setClickable(false);
        login.setTextColor(Color.GRAY);

        String phone = phoneNumber.getText().toString();
        String pin = mobilePin.getText().toString();
        Log.i(TAG, "Phone:" + phone + ".\nPin:" + pin + ".");

        ParseUser.logInInBackground(phone, pin, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    //TODO: Clear input
                    Toast.makeText(getApplication(), "Logs on", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplication(), MainMenuActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Failed to log on", Toast.LENGTH_SHORT).show();
                }
                login.setFocusable(true);
                login.setClickable(true);
                login.setTextColor(getResources().getColor(R.color.brandColor));
            }
        });
        /*
        if(phone.equals("0739383807") && pin.equals("12345") || debug) {


            Intent intent = new Intent(this, MainMenuActivity.class);

            /*EditText editText = (EditText) findViewById(R.id.edit_message);
            String message = editText.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);*
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Bad Credentials", Toast.LENGTH_SHORT).show();
        }*/
    }

    public void signupButton(View v)
    {
        Intent intent = new Intent(getApplication(), SignupActivity.class);
        startActivity(intent);
    }
}
