package com.medical.medicate;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class per_detail extends AppCompatActivity {

    private Button save;
    private EditText fname,lname,dob,mobile;
    private RadioGroup radioGroup;
    private String first_name,last_name,date_of_birth,mobile_no,gen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_detail);

        save=(Button)findViewById(R.id.save);
        fname=(EditText)findViewById(R.id.editText);
        lname=(EditText)findViewById(R.id.editText2);
        dob=(EditText)findViewById(R.id.dob);
        mobile=(EditText)findViewById(R.id.phone);

        /*radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i==R.id.male){
                    gen="male";
                }else if(i==R.id.female){
                    gen="female";
                }
            }
        });*/

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
            dob.setError("Empty Field");
            dob.requestFocus();
            return false;
        }
        /*if ("".matches(gen))
        {
            Toast.makeText(getApplicationContext(), "Select the gender", Toast.LENGTH_LONG).show();
            return false;
        }*/
        if ("".equals(mobile_no)) {
            mobile.setError("Empty Field");
            mobile.requestFocus();
            return false;
        }
        if(mobile_no.length()>10)
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
        StringRequest src = new StringRequest(Request.Method.GET, AppURLs.URL+"?tag=form_personal&Firstname="+first_name+"&Lastname="+last_name+"&Dateofbirth="+date_of_birth+"&Gender="+gen+"&Mobile="+mobile_no , new Response.Listener<String>(){
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
