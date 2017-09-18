package com.medical.medicate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity
{
    private Button LoginButton ;
    private EditText login_Username, login_Password ;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        LoginButton = (Button) findViewById(R.id.LoginButton );
        login_Username= (EditText) findViewById(R.id.login_Username);
        login_Password = (EditText) findViewById(R.id.login_Password );

        LoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String  login_Username_value = login_Username.getText().toString();
                String login_Password_value = login_Password.getText().toString();
                checkLogin( login_Username_value , login_Password_value );
            }
        });
    }
    public void register(View view)
    {
        Intent register = new Intent(Login.this,Register.class);
        Login.this.startActivity(register);
    }

    private void checkLogin(final String login_Username_value , final String login_Password_value ) {
        String tag_string_req = "login";
        progressDialog.setMessage("Logging in ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppURLs.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                hideDialog();
                try
                {
                    JSONObject jObj = new JSONObject(response.toString());
                    boolean error = jObj.getBoolean("error");
                    if(!error)
                    {
                        Toast.makeText(getApplication(), "Successfully Login", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplication(), "Username/Password Incorrect", Toast.LENGTH_LONG).show();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "login");
                params.put("Username", login_Username_value );
                params.put("Password", login_Password_value );
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}

