package com.medical.medicate;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Navigation_SOS_Service extends Service {
    private FirebaseDatabase mDatabase;
    final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String num1,num2,num3;

    public Navigation_SOS_Service()
    {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mDatabase = FirebaseDatabase.getInstance();
        mDatabase.getReference().child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Navigation_SOS.SOS sos = dataSnapshot.child("SOS_Service").getValue(Navigation_SOS.SOS.class);
                if (sos != null)
                {
                    num1=sos.number1;
                    num2=sos.number2;
                    num3=sos.number3;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Read", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        LinearLayout mLinear = new LinearLayout(getApplicationContext())
        {
            public void onCloseSystemDialogs(String reason) {
                if ("globalactions".equals(reason))
                {
                    Log.d("Key", "Long press on power button");
                    final String sms_message="I am in medical emergency, reach me as soon as possible. PLEASE NOTE - This message was generarted as a part of the APP Medicate";
                    SmsManager smsManager = SmsManager.getDefault();
                    Log.d("Number",num1);
                    Log.d("Number",num2);
                    Log.d("Number",num3);
                    /*smsManager.sendTextMessage(num1, null, sms_message+"", null, null);
                    smsManager.sendTextMessage(num2, null, sms_message+"", null, null);
                    smsManager.sendTextMessage(num3, null, sms_message+"", null, null);*/
                }
            }
        };

        mLinear.setFocusable(true);

        View mView = LayoutInflater.from(this).inflate(R.layout.navigation_sos_service, mLinear);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        //params
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                100,
                100,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
        wm.addView(mView, params);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}