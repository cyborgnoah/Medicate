package com.medical.medicate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.IgnoreExtraProperties;

public class Navigation_SOS extends AppCompatActivity
{
    private Button save_edit;
    private DatabaseReference mDatabase;
    private EditText enumber1,enumber2,enumber3;
    private String enumber1_value,enumber2_value,enumber3_value;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_sos);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        save_edit = (Button) findViewById(R.id.save_edit);
        enumber1 = (EditText) findViewById(R.id.navigation_sos_number1);
        enumber2 = (EditText) findViewById(R.id.navigation_sos_number2);
        enumber3 = (EditText) findViewById(R.id.navigation_sos_number3);

        enumber1_value=enumber1.getText().toString();
        enumber2_value=enumber2.getText().toString();
        enumber3_value=enumber3.getText().toString();

        save_edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(validateNumber())
                {
                SOS sos = new SOS(enumber1_value, enumber2_value, enumber3_value);
                mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(sos);
                }
            }
        });
    }

    public boolean validateNumber()
    {

        if (enumber1_value.length()!=10)
        {
            enumber1.setError("Invalid Phone Number");
            enumber1.requestFocus();
            return false;
        }
        if (enumber2_value.length()!=10)
        {
            enumber2.setError("Invalid Phone Number");
            enumber2.requestFocus();
            return false;
        }
        if (enumber3_value.length()!=10)
        {
            enumber3.setError("Invalid Phone Number");
            enumber3.requestFocus();
            return false;
        }
        return true;
    }

    @IgnoreExtraProperties
    public static class SOS {

        public String number1;
        public String number2;
        public String number3;

        public SOS() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public SOS(String number1, String number2,String number3)
        {
            this.number1 = number1;
            this.number2 = number2;
            this.number3 = number3;
        }

    }
}
