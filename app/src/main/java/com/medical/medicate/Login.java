package com.medical.medicate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle savedinstantstate)
    {
        super.onCreate(savedinstantstate);
        setContentView(R.layout.login);
    }
    public void register(View view)
    {
        Intent register=new Intent(Login.this,Register.class);
        Login.this.startActivity(register);
    }
    public void login(View view)
    {
        EditText login_Username = (EditText) findViewById(R.id.login_Username);
        EditText login_Password = (EditText) findViewById(R.id.login_Password);
        final String login_Username_value = login_Username.getText().toString();
        final String login_Password_value = login_Password.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.1.5/user_login.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response is: ",response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error : ",error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("login_Username", login_Username_value);
                params.put("login_Password", login_Password_value);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}

