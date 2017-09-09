package com.medical.medicate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Register extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle savedinstancestate)
    {
        super.onCreate(savedinstancestate);
        setContentView(R.layout.registeration_form);
    }
    public void submit_register(View view)
    {
        EditText reg_FullName = (EditText) findViewById(R.id.reg_FullName);
        EditText reg_Username = (EditText) findViewById(R.id.reg_Username);
        EditText reg_Email = (EditText) findViewById(R.id.reg_Email);
        EditText reg_Password = (EditText) findViewById(R.id.reg_Password);
        EditText reg_ConfirmPassword = (EditText) findViewById(R.id.reg_ConfirmPassword);
        String reg_FullName_value = reg_FullName.getText().toString();
        String reg_Username_value = reg_Username.getText().toString();
        String reg_Email_value = reg_Email.getText().toString();
        String reg_Password_value = reg_Password.getText().toString();
        String reg_ConfirmPassword_value = reg_ConfirmPassword.getText().toString();
        Log.d("Full Name",reg_FullName_value);
        Log.d("Username",reg_Username_value);
        Log.d("Email",reg_Email_value);
        Log.d("Password",reg_Password_value);
        Log.d("Confirm Passowrd",reg_ConfirmPassword_value);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="localhost/Server/create_user.php";

        // Request a JSON response from the provided URL.
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        // Display the response in Android Manager
                        Log.d("Response is: ",
                                response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d("Error : ","Some error occured!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);
    }
}
