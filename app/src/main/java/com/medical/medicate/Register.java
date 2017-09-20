package com.medical.medicate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Register extends AppCompatActivity
{
    private EditText reg_FullName, reg_Username, reg_Email, reg_Password, reg_ConfirmPassword;
    private String reg_FullName_value, reg_Username_value, reg_Email_value, reg_Password_value, reg_ConfirmPassword_value;
    private Button reg_Register;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeration_form);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        reg_Register= (Button)findViewById(R.id.reg_Register);
        reg_FullName= (EditText)findViewById(R.id.reg_FullName);
        reg_Username=(EditText)findViewById(R.id.reg_Username);
        reg_Email = (EditText)findViewById(R.id.reg_Email );
        reg_Password= (EditText)findViewById(R.id.reg_Password);
        reg_ConfirmPassword= (EditText)findViewById(R.id.reg_ConfrimPassword);

        reg_Register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                reg_FullName_value = reg_FullName.getText().toString();
                reg_Username_value = reg_Username.getText().toString();
                reg_Email_value = reg_Email.getText().toString();
                reg_Password_value = reg_Password.getText().toString();
                reg_ConfirmPassword_value = reg_ConfirmPassword.getText().toString();
                boolean isValidRegistration = validateUser();
                if (isValidRegistration)
                {
                        registerUser(reg_FullName_value, reg_Username_value, reg_Email_value, reg_Password_value);
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
        String fullnamePattern = "[a-zA-Z ]+";
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String userPattern ="[a-zA-Z0-9]+";
        if ("".equals(reg_FullName_value))
        {
            reg_FullName.setError("Empty Field");
            reg_FullName.requestFocus();
            return false;
        }
        if (!reg_FullName_value.matches(fullnamePattern))
        {
            reg_FullName.setError("Name should be in character form");
            reg_FullName.setText("");
            reg_FullName.requestFocus();
            return false;
        }
        if ("".equals( reg_Username_value))
        {
            reg_Username.setError("Empty Field");
            reg_Username.requestFocus();
            return false;
        }
        if (!reg_Username_value.matches(userPattern))
        {
            reg_Username.setError("Please enter valid username");
            reg_Username.setText("");
            reg_Username.requestFocus();
            return false;
        }
        if ("".equals(reg_Email_value))
        {
            reg_Email.setError("Empty Field");
            reg_Email.requestFocus();
            return false;
        }
        if (!reg_Email_value.matches(emailPattern))
        {
            reg_Email.setError("Please enter valid Email Id");
            reg_Email.setText("");
            reg_Email.requestFocus();
            return false;
        }
        if ("".equals( reg_Password_value))
        {
            reg_Password.setError("Empty Field");
            reg_Password.requestFocus();
            return false;
        }
        if((reg_Password_value.length()<8) || (reg_Password_value.length()>20))
        {
            reg_Password.setError("Password length 8-20");
            reg_Password.setText("");
            reg_Password.requestFocus();
            return false;
        }
        if ("".matches(reg_ConfirmPassword_value))
        {
            reg_ConfirmPassword.setError("Empty Field");
            reg_ConfirmPassword.requestFocus();
            return false;
        }
        if (!reg_Password_value.matches(reg_ConfirmPassword_value))
        {
            reg_ConfirmPassword.setError("Password Mismatch");
            reg_ConfirmPassword.setText("");
            reg_ConfirmPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void registerUser(final String reg_FullName_value ,final String  reg_Username_value, final String reg_Email_value, final String  reg_Password_value)
    {
        // Tag used to cancel the request
        String tag_string_req = "register";
        pDialog.setMessage("Registering ...");
        showDialog();
        StringRequest sr = new StringRequest(Request.Method.GET, AppURLs.URL+"?tag=register&FullName="+reg_FullName_value+"&Username="+reg_Username_value+"&Email="+reg_Email_value+"&Password="+reg_Password_value , new Response.Listener<String>(){
            @Override
            public void onResponse(String response)
            {
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error)
                    {
                        Toast.makeText(getApplicationContext(), "Regestered successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);
                        finish();
                        setContentView(R.layout.login);
                    }
                    else
                    {
                        reg_Username.setError("Username Exists");
                        reg_Username.setText("");
                        reg_Username.requestFocus();
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
                hideDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(sr, tag_string_req);
    }

    private void showDialog()
    {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog()
    {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
