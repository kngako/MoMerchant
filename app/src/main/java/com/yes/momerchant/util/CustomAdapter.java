package com.yes.momerchant.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yes.momerchant.R;
import com.yes.momerchant.providers.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kgothatso on 2014/07/13.
 */
public class CustomAdapter extends ArrayAdapter<Transaction> {
    private final Activity activity;
    private final List<Transaction> list;

    public CustomAdapter(Activity activity, int resource, List<Transaction> list) {
        super(activity.getApplicationContext(), resource, list);
        this.activity = activity;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder view;

        if(rowView == null)
        {
            // Get a new instance of the row layout view
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.transaction_row, null);

            // Hold the view objects in an object, that way the don't need to be "re-  finded"
            view = new ViewHolder();
            view.number = (TextView) rowView.findViewById(R.id.cNumber);
            view.amount = (TextView) rowView.findViewById(R.id.cAmount);
            view.date = (TextView) rowView.findViewById(R.id.cDate);
            view.id = (TextView) rowView.findViewById(R.id.cId);

            rowView.setTag(view);
        } else {
            view = (ViewHolder) rowView.getTag();
        }

        /** Set data to your Views. */
        Transaction item = list.get(position);
        view.amount.setText(item.getAmount());
        view.number.setText(item.getCustomerPhoneNumber());
        view.date.setText(item.getDate());
        view.id.setText(item.getId().toString());

        return rowView;
    }

    protected static class ViewHolder {
        protected TextView amount;
        protected TextView number;
        protected TextView date;
        protected TextView id;
    }
}
