package com.medical.medicate;

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

public class Register extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedinstancestate) {
        super.onCreate(savedinstancestate);
        setContentView(R.layout.registeration_form);
    }

    public void submit_register(View view) {
        EditText reg_FullName = (EditText) findViewById(R.id.reg_FullName);
        EditText reg_Username = (EditText) findViewById(R.id.reg_Username);
        EditText reg_Email = (EditText) findViewById(R.id.reg_Email);
        EditText reg_Password = (EditText) findViewById(R.id.login_Password);
        EditText reg_ConfirmPassword = (EditText) findViewById(R.id.reg_ConfirmPassword);
        final String reg_FullName_value = reg_FullName.getText().toString();
        final String reg_Username_value = reg_Username.getText().toString();
        final String reg_Email_value = reg_Email.getText().toString();
        final String reg_Password_value = reg_Password.getText().toString();
        String reg_ConfirmPassword_value = reg_ConfirmPassword.getText().toString();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.1.5/create_user.php";

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
                params.put("reg_FullName", reg_FullName_value);
                params.put("reg_Username", reg_Username_value);
                params.put("reg_Email", reg_Email_value);
                params.put("reg_Password", reg_Password_value);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
