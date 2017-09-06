package com.medical.medicate;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class Register extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle savedinstancestate)
    {
        super.onCreate(savedinstancestate);
        Intent regsister=getIntent();
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
        /*Log.d("Full Name",reg_FullName_value);
        Log.d("Username",reg_Username_value);
        Log.d("Email",reg_Email_value);
        Log.d("Password",reg_Password_value);
        Log.d("Confirm Passowrd",reg_ConfirmPassword_value);*/
        new SendPostRequest().execute(reg_FullName_value,reg_Username_value,reg_Email_value,reg_Password_value,reg_ConfirmPassword_value);
    }
}
class SendPostRequest extends AsyncTask<String, Void, String>
{
    protected void onPreExecute(){}

    protected String doInBackground(String... arg) {
        try {
            URL url = new URL("https://139.59.78.53/Medicate/test.php"); // here is your URL path
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("Full Name", arg[0]);
            postDataParams.put("Username", arg[1]);
            postDataParams.put("Email", arg[2]);
            postDataParams.put("Password", arg[3]);
            postDataParams.put("Confirm Passowrd", arg[4]);
            Log.e("params", postDataParams.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            } else {
                return new String("false : " + responseCode);
            }
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        Log.e("Execution","Successful Data send to the server");
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
}