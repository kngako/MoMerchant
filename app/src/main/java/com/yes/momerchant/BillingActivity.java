package com.yes.momerchant;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class BillingActivity extends Activity {
    private static final String TAG = "BillingActivity";
    private static final int REQUEST_EXIT = 99;
    private static int count = 1;
    private static int serviceOrProductRenderedLimit = 5;

    private EditText serviceOrProduct;
    private EditText amount;
    private EditText customerNumber;
    private EditText customerName;
    private EditText emailAddress;
    private Button del;
    private ToggleButton emailSwitch;

    public final static String CUSTOMER_NAME = "customer";
    public final static String CUSTOMER_NUMBER = "customerNo";
    public final static String EMAIL_ADDRESS = "email_address";
    public final static String LIST_OF_SERVICES_OR_PRODUCTS = "list_of_services_or_products";
    public final static String LIST_OF_AMOUNTS = "list_of_amounts";

    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        Button iBtn = (Button) findViewById(R.id.issue_invoice);
        iBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                issueInvoiceButton(v);
            }
        });

        Button aBtn = (Button) findViewById(R.id.add_more);
        aBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMoreButton(v);
            }
        });

        del = (Button) findViewById(R.id.delete);
        //del.setId(count++);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButton(v);
            }
        });

        serviceOrProduct = (EditText) findViewById(R.id.serviceRendered);
        amount  = (EditText) findViewById(R.id.billing_amount);

        customerNumber = (EditText) findViewById(R.id.enter_customer_number_input);
        emailAddress = (EditText) findViewById(R.id.enter_transaction_amount_input);
        customerName = (EditText) findViewById(R.id.invoicee_name);
        //addMore  = (Button) findViewById(R.id.add_more);

        ll = (LinearLayout) findViewById(R.id.list_of_services);
        emailSwitch = (ToggleButton) findViewById(R.id.emailSwitch);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.billing, menu);
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

    public void issueInvoiceButton(View v)
    {
        Intent intent = new Intent(this, IssueInvoiceActivity.class);

        ArrayList<String> services = new ArrayList<String>();
        ArrayList<String> amounts = new ArrayList<String>();

        //intent.setClass(SourceActivity.this, TargetActivity.this);
        for(int i = 0; i < ll.getChildCount(); i++)
        {
            LinearLayout item = (LinearLayout) ll.getChildAt(i);

            EditText sp = (EditText) item.getChildAt(0);
            EditText am = (EditText) item.getChildAt(1);

            String cService = sp.getText().toString();
            String cAmount = am.getText().toString();

            services.add(cService);
            amounts.add(cAmount);
        }

        intent.putExtra(CUSTOMER_NAME, customerName.getText().toString());
        intent.putExtra(CUSTOMER_NUMBER, customerNumber.getText().toString());
        intent.putExtra(EMAIL_ADDRESS, emailAddress.getText().toString());
        intent.putStringArrayListExtra(LIST_OF_SERVICES_OR_PRODUCTS, services);
        intent.putStringArrayListExtra(LIST_OF_AMOUNTS, amounts);

        if(emailSwitch.isChecked()) sendEmail(services, amounts);

        startActivityForResult(intent, REQUEST_EXIT);
    }

    public void sendEmail(List<String> services, List<String> amount)
    {
        String text = "I'm too lazy to write it all out...\n\n";

        for(int i = 0; i < services.size(); i++) {
            text += services.get(i) + ": " + amount.get(i) + "\n";
        }

        text += "\nHave a good day.";

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress.getText().toString()});
        email.putExtra(Intent.EXTRA_SUBJECT, "Business Name: Invoice For You.");
        email.putExtra(Intent.EXTRA_TEXT, text);
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_EXIT) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    public void addMoreButton(View v)
    {
        if(count < serviceOrProductRenderedLimit) {
            LinearLayout item = new LinearLayout(this);

            item.setOrientation(LinearLayout.HORIZONTAL);
            item.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            item.setGravity(Gravity.CENTER);

            EditText newServiceOrProduct = new EditText(this);

            newServiceOrProduct.setHint("Service/Product");
            newServiceOrProduct.setMaxWidth(dp(160));
            newServiceOrProduct.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            newServiceOrProduct.setSingleLine();

            EditText newAmount = new EditText(this);

            newAmount.setHint("Amount");
            newAmount.setMaxWidth(dp(90));
            newAmount.setInputType(InputType.TYPE_CLASS_NUMBER);
            newAmount.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            newAmount.setSingleLine();

            Button delete = new Button(this);
            Drawable[] drawablesLeft = del.getCompoundDrawables();
            delete.setId(count++);
            delete.setCompoundDrawables(drawablesLeft[0], null, null, null);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteButton(v);
                }
            });

            item.addView(newServiceOrProduct);
            item.addView(newAmount);
            item.addView(delete);

            ll.addView(item);
        }
    }

    private int dp(float dp)
    {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float fpixels = metrics.density * dp;
        return (int) (fpixels + 0.5f);
    }

    public void deleteButton(View v)
    {
        for(int i = 0; i < ll.getChildCount(); i++)
        {
            LinearLayout item = (LinearLayout) ll.getChildAt(i);

            Button rm = (Button) item.getChildAt(2);

            if(rm.getId() == v.getId())
            {
                ll.removeView(item);
            }
        }
    }
}
