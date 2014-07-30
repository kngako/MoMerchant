package com.yes.momerchant.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by kgothatso on 2014/07/30.
 */
public class IncomingSms extends BroadcastReceiver {
    // As Seen on http://androidexample.com/Incomming_SMS_Broadcast_Receiver_-_Android_Example/index.php?view=article_discription&aid=62&aaid=87
    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("KGOTHATSO!!!!!", "senderNum: "+ senderNum + "; message: " + message);

                    if(senderNum.equals("TYME"))
                    {
                        /*
                        TODO: Parse SMS...
                        ParseObject transaction = new ParseObject("Transaction");

                        transaction.put("amount", am);
                        transaction.put("CustomerPhoneNumber", num);
                        transaction.put("merchant", ParseUser.getCurrentUser().getObjectId());
                        transaction.saveInBackground();
                         */
                    }

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }
    }
}
