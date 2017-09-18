package com.medical.medicate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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
import java.util.HashMap;
import java.util.Map;

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
        reg_Register= (Button) findViewById(R.id.reg_Register);
        reg_FullName= (EditText) findViewById(R.id.reg_FullName);
        reg_Username=(EditText)findViewById(R.id.reg_Username);
        reg_Email = (EditText) findViewById(R.id.reg_Email );
        reg_Password= (EditText) findViewById(R.id.login_Password);
        reg_ConfirmPassword= (EditText) findViewById(R.id.reg_ConfirmPassword);

        reg_Register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                reg_FullName_value = reg_FullName.getText().toString();
                reg_Username_value = reg_Username.getText().toString();
                reg_Email_value = reg_Email.getText().toString();
                reg_Password_value = reg_Password.getText().toString();
                reg_ConfirmPassword_value = reg_ConfirmPassword.toString();
                boolean isValidRegistration = validateUser();
                if (isValidRegistration)
                {
                    registerUser(reg_FullName_value, reg_Username_value, reg_Email_value, reg_Password_value);
                }
            }
        });
    }

    public boolean validateUser()
    {
        boolean isValid = true;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String Userpattern ="[a-zA-Z0-9]+";
        if ("".equals(reg_FullName_value)) {
            reg_FullName.setError("Please enter Name");
            isValid = false;
        }
        if ("".equals( reg_Username_value)) {
            reg_Username.setError("Please enter User Name");
            isValid = false;
        }
        if ("".equals(reg_Email_value)) {
            reg_Email.setError("Please enter Email Id");
            isValid = false;
        }

        if (!reg_FullName_value.matches("[a-zA-Z ]+")) {
            reg_FullName.setError("Name should be in character form");
            reg_FullName.setText("");
            isValid = false;
        }
        if ("".equals( reg_Password_value)) {
            reg_Password.setError("Please Enter valid Password");
            isValid = false;
        }
        if((reg_Password_value.length()<10) || (reg_Password_value.length()>20))
        {
            reg_Password.setError("Password length 10-20");
            reg_Password.setText("");
            isValid = false;
        }
        if ("".equals(reg_ConfirmPassword_value)) {
            reg_ConfirmPassword.setError("Please Enter Password again");
            isValid = false;
        }
        if (!reg_Email_value.matches(emailPattern))
        {reg_Email.setError("Please enter valid Email Id");
            reg_Email.setText("");
            isValid = false;
        }
        if (!reg_Username_value.matches(Userpattern))
        {   reg_Username.setError("Please enter valid username");
            reg_Username.setText("");
            isValid = false;
        }

        return isValid;
    }


    private void registerUser(final String reg_FullName_value ,final String  reg_Username_value, final String reg_Email_value, final String  reg_Password_value)
    {
        // Tag used to cancel the request
        String tag_string_req = "register";
        pDialog.setMessage("Registering ...");
        showDialog();
        StringRequest sr = new StringRequest(Request.Method.POST, AppURLs.URL , new Response.Listener<String>(){
            @Override
            public void onResponse(String response)
            {
                if(TextUtils.isEmpty(response)){
                    Toast.makeText(getApplicationContext(), "Response Empty", Toast.LENGTH_LONG).show();
                    return;
                }
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
                        Toast.makeText(getApplicationContext(), "Not Regestered.", Toast.LENGTH_LONG).show();
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
                       "Some problem occur: check NetworK" , Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "register");
                params.put("FullName", reg_FullName_value );
                params.put("Username", reg_Username_value);
                params.put("Email", reg_Email_value);
                params.put("Password", reg_Password_value);
                return params;
            }

        };
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
