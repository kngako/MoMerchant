package com.yes.momerchant.providers;

import android.util.Log;

/**
 * Created by kgothatso on 2014/07/12.
 */
public class Transaction {
    private static final String TAG = "Customer";

    private final String mCustomerPhoneNumber;
    private final String mAmount;
    private final String mDate;
    private final Long mId;

    public Long getId()
    {
        return mId;
    }

    public String getDate()
    {
        return mDate;
    }

    public String getCustomerPhoneNumber()
    {
        return mCustomerPhoneNumber;
    }

    public String getAmount()
    {
        return mAmount;
    }

    public Transaction(String date, String customerPhoneNumber, String amount, Long id)
    {
        mDate = date;
        mCustomerPhoneNumber = customerPhoneNumber;
        mAmount = amount;
        mId = id;
    }

    @Override
    public String toString() {
        return mAmount;
    }
}
