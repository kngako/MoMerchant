package com.yes.momerchant.providers;

/**
 * Created by kgothatso on 2014/07/12.
 */
public class Customer {
    private static final String TAG = "Customer";

    private final String mNumber;
    private final String mName;
    private final Long mId;

    public Long getId()
    {
        return mId;
    }

    public String getName()
    {
        return mName;
    }

    public String getNumber()
    {
        return mNumber;
    }

    public Customer(String name, String number, Long id)
    {
        mName = name;
        mNumber = number;
        mId = id;
    }
}
