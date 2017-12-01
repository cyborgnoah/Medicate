package com.medical.medicate;


import android.app.DatePickerDialog;
import android.app.Dialog;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.EditText;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class per_detail extends AppCompatActivity {

    private Button save;
    private ImageButton img;
    private RadioButton male,female;
    private EditText fname,lname,dob,mobile;
    private RadioGroup radioGroup;
    private String first_name,last_name,date_of_birth,mobile_no,gen=null;

    private int pYear;
    private int pMonth;
    private int pDay;
    static final int DATE_DIALOG_ID = 0;

    private DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    pYear = year;
                    pMonth = monthOfYear;
                    pDay = dayOfMonth;
                    updateDisplay();
                }
            };
    private void updateDisplay() {
        dob.setText(
                new StringBuilder()
                        .append(pDay).append("/")
                        .append(pMonth + 1).append("/")
                        .append(pYear).append(" "));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        pDateSetListener,
                        pYear, pMonth, pDay);
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_detail);

        save=(Button)findViewById(R.id.save);
        fname=(EditText)findViewById(R.id.editText);
        lname=(EditText)findViewById(R.id.editText2);
        dob=(EditText)findViewById(R.id.dob);

        img=(ImageButton)findViewById(R.id.imageButton);
        mobile=(EditText)findViewById(R.id.phone);
        male=(RadioButton)findViewById(R.id.male);
        female=(RadioButton)findViewById(R.id.female);
        radioGroup=(RadioGroup)findViewById(R.id.gender);


        /*radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i==R.id.male){
                    gen="male";
                }else if(i==R.id.female){
                    gen="female";

                    }
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        /* set the current date */
        final Calendar cal = Calendar.getInstance();
        pYear = cal.get(Calendar.YEAR);
        pMonth = cal.get(Calendar.MONTH);
        pDay = cal.get(Calendar.DAY_OF_MONTH);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_name=fname.getText().toString();
                last_name=lname.getText().toString();
                date_of_birth=dob.getText().toString();
                mobile_no=mobile.getText().toString();
                if(isNetworkAvailable()!=true)
                {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }
                else
                {
                    boolean isValidRegistration = validateUser();
                    if (isValidRegistration)
                    {
                        Log.d("Test0:","Not Reached");

                        Toast.makeText(getApplicationContext(), mobile.getText(), Toast.LENGTH_LONG).show();
                        registerUser(first_name, last_name,date_of_birth,gen,mobile_no);
                    }
                }
            }
        });
    }
    public boolean isNetworkAvailable()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    public boolean validateUser()
    {
        boolean isValid = true;
        String fullnamePattern = "[a-zA-Z]+";
        String userPattern ="[a-zA-Z0-9]+";
        if ("".equals(first_name))
        {
            fname.setError("Empty Field");
            fname.requestFocus();
            return false;
        }
        if (!first_name.matches(fullnamePattern))
        {
            fname.setError("Name should be in character form");
            fname.setText("");
            fname.requestFocus();
            return false;
        }
        if ("".equals(last_name))
        {
            lname.setError("Empty Field");
            lname.requestFocus();
            return false;
        }
        if (!last_name.matches(userPattern))
        {
            lname.setError("Please enter valid username");
            lname.setText("");
            lname.requestFocus();
            return false;
        }
        if ("".equals(date_of_birth)) {

            dob.setError("enter date of birth");
            dob.requestFocus();
            return false;
        }
        if ((male.isChecked()==false && female.isChecked()==false) || (male.isChecked()==true && female.isChecked()==true))
        {
            Toast.makeText(getApplicationContext(), "select any one gender", Toast.LENGTH_LONG).show();
            return false;
        }
        if ("".equals(mobile_no)) {
            mobile.setError("enter date of birth");
            mobile.requestFocus();
            return false;
        }
        if(mobile_no.length()>10 || mobile_no.length()<10)
        {
            mobile.setError("10 digit Mobile number");
            mobile.requestFocus();
            return false;
        }
        return true;
    }

    private void registerUser(final String first_name ,final String  last_name, final String date_of_birth, final String  gen,final String mobile_no)
    {
        // Tag used to cancel the request

        //String tag_string_req = "register";
        Log.d("Test1:","Not Reached");
        StringRequest src = new StringRequest(Request.Method.GET, "www.google.com?tag=form_personal&Firstname="+first_name+"&Lastname="+last_name+"&Dateofbirth="+date_of_birth+"&Gender="+gen+"&Mobile="+mobile_no , new Response.Listener<String>(){
            @Override
            public void onResponse(String response)
            {
                try {
                    Log.d("Test2:","Not Reached");
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    String error_msg=jObj.getString("error_msg");
                    if (!error)
                    {
                        Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_LONG).show();
                        /*Intent intent = new Intent(per_detail.this, module_navigation.class);
                        startActivity(intent);
                        finish();
                        setContentView(R.layout.content_module_navigation);*/
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Network Error" , Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(src, "form_personal");
    }
}
