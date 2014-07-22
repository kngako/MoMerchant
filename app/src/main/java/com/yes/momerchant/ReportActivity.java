package com.yes.momerchant;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.yes.momerchant.R;

import java.util.Calendar;

public class ReportActivity extends Activity {

    private DatePicker startDate;
    private DatePicker endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Button btn = (Button) findViewById(R.id.generate);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateButton(v);
            }
        });

        startDate = (DatePicker) findViewById(R.id.start_date_input);
        endDate = (DatePicker) findViewById(R.id.end_date_input);

        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        startDate.init(year, month, day, null);
        endDate.init(year, month, day, null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.report, menu);
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

    public void generateButton(View v) {
        // TODO: Valdate the input...
        int sDay = startDate.getDayOfMonth();
        int sMonth = startDate.getMonth() - 1;
        int sYear =  startDate.getYear();

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(sYear, sMonth, sDay);

        int eDay = endDate.getDayOfMonth();
        int eMonth = endDate.getMonth() - 1;
        int eYear =  endDate.getYear();

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(eYear, eMonth, eDay);

        Intent intent = new Intent(this, GeneratedReport.class);
        startActivity(intent);
        finish();
    }
}
