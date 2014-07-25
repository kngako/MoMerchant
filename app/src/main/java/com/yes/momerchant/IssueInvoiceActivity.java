package com.yes.momerchant;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Header;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.yes.momerchant.R;
import com.yes.momerchant.providers.Transaction;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IssueInvoiceActivity extends Activity {
    private final String TAG = "IssueInvoiceActivity";

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Might wanna implement a spinner here...
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_invoice);

        generatePDF();
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.issue_invoice, menu);
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

    public void generateInvoiceButton(View v)
    {
        generatePDF();
        finish();
    }

    public void generatePDF()
    {
        Toast.makeText(this, "Generating PDF", Toast.LENGTH_SHORT).show();
        //AuthenticUser(session, list of privliges)
        Document doc = new Document();

        ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();

        try
        {
            Toast.makeText(this, "Stored in: /MoMerchant/", Toast.LENGTH_LONG).show();
            String root = Environment.getExternalStorageDirectory().toString();
            File storeDir = new File(root + "/MoMerchant");
            storeDir.mkdirs();

            File file = new File(storeDir, "Invoice.pdf");
            if(file.exists()) file.delete();

            FileOutputStream out = new FileOutputStream(file);

            PdfWriter docWriter = PdfWriter.getInstance(doc, out);
            doc.open();
            Timestamp time = new Timestamp(new Date().getTime());
            doc.add(new Paragraph("Issue invoiced by: " + "Company Name"));
            doc.add(new Paragraph("Created on: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(time)));
            doc.add(new Paragraph("Company Registration Number: " + "094-246-462-642"));
            doc.add(new Paragraph("Recipients Name:" + getIntent().getStringExtra(BillingActivity.CUSTOMER_NAME)));
            doc.add(new Paragraph("Cell Number: " + getIntent().getStringExtra(BillingActivity.CUSTOMER_NUMBER)));
            doc.add(new Paragraph("Auto generated Invoice Number: " + "02959"));

            DottedLineSeparator separator = new DottedLineSeparator();
            separator.setPercentage(59500f/523f);
            Chunk linebreak = new Chunk(separator);
            doc.add(linebreak);

            ArrayList<String> services = getIntent().getStringArrayListExtra(BillingActivity.LIST_OF_SERVICES_OR_PRODUCTS);
            ArrayList<String> amounts = getIntent().getStringArrayListExtra(BillingActivity.LIST_OF_AMOUNTS);

            // TODO: Might wanna make this a table
            for(int i = 0; i < services.size(); i++)
            {
                Log.i(TAG, services.get(i) + ": " + "R " + amounts.get(i));
                doc.add(new Paragraph(services.get(i) + ": " + "R " + amounts.get(i)));
            }

            doc.add(linebreak);

            doc.add(new Paragraph("Payment must be sent to this number: 0739383807"));
            doc.add(new Paragraph("Ad: Need a job? Get a Job! Dail 0840294529"));

            /*PdfPTable applicationTable = setupTable(Transaction.class);

            for (Transaction entity : entities) {
                applicationTable = addEntityToTable(applicationTable, entity);
            }
            doc.add(applicationTable);*/

            doc.close();
            docWriter.close();
            out.flush();
            out.close();
        }
        catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //return baosPDF;
        setResult(RESULT_OK, null);
        finish();
    }

}
