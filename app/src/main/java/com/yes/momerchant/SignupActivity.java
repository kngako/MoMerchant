package com.yes.momerchant;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.yes.momerchant.R;

public class SignupActivity extends Activity {
    private Button signup;

    private EditText phoneNumber;
    private EditText mobilePin;
    private EditText confirmMobilePin;
    private EditText cRegNumber;
    private EditText cName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupButton(v);
            }
        });

        phoneNumber = (EditText) findViewById(R.id.phone_number);
        mobilePin = (EditText) findViewById(R.id.mobile_pin);
        confirmMobilePin = (EditText) findViewById(R.id.confirm_mobile_pin);
        cRegNumber = (EditText) findViewById(R.id.c_reg_number);
        cName = (EditText) findViewById(R.id.company_name);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.signup, menu);
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

    public void signupButton(View v)
    {
        if(confirmMobilePin.getText().toString().equals(mobilePin.getText().toString()) && mobilePin.getText().length() >= 3
                && phoneNumber.getText().length() == 10) {
            signup.setFocusable(false);
            signup.setClickable(false);
            signup.setTextColor(Color.GRAY);

            ParseUser man = new ParseUser();

            man.setUsername(phoneNumber.getText().toString());
            man.setPassword(mobilePin.getText().toString());

            man.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Intent intent = new Intent(getApplication(), MainMenuActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Account Exist.", Toast.LENGTH_SHORT).show();
                    }
                    signup.setFocusable(true);
                    signup.setClickable(true);
                    signup.setTextColor(getResources().getColor(R.color.brandColor));
                }
            });
        }
        else
        {
            Toast.makeText(this, "Enter valid data...", Toast.LENGTH_SHORT).show();
        }
    }
}
