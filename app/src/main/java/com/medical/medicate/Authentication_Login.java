package com.medical.medicate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication_Login extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private Button LoginButton,RegistrationButton ;
    private EditText login_Username, login_Password ;
    private String login_Username_value,login_Password_value;
    private TextView ForgetPassword;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_login);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        LoginButton = (Button) findViewById(R.id.LoginButton );
        RegistrationButton = (Button)findViewById(R.id.RegisterButton);
        login_Username= (EditText) findViewById(R.id.login_Username);
        login_Password = (EditText) findViewById(R.id.login_Password);
        ForgetPassword = (TextView) findViewById(R.id.forget_password);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null)
        {
            Intent intent = new Intent(getApplicationContext(), module_navigation.class);
            startActivity(intent);
            finish();
            setContentView(R.layout.activity_module_navigation);
        }

        LoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                login_Username_value = login_Username.getText().toString();
                login_Password_value = login_Password.getText().toString();
                if(isNetworkConnected()!=true)
                {
                    Toast.makeText(getApplication(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }
                else
                {
                    boolean isValidRegistration = validateUser();
                    if (isValidRegistration)
                    {
                        pDialog.setMessage("Loggin In...Please Wait");
                        showDialog();
                        checkLogin(login_Username_value, login_Password_value);
                    }
                }

            }
        });
        RegistrationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent register = new Intent(Authentication_Login.this,Authentication_Register.class);
                Authentication_Login.this.startActivity(register);
            }
        });
        ForgetPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(Authentication_Login.this);
                final EditText edittext = new EditText(getApplicationContext());
                alert.setMessage("Enter E-mail");
                alert.setTitle("Forget Password");
                alert.setView(edittext);
                alert.setPositiveButton("Ok",new DialogInterface.OnClickListener()
                {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                    //What ever you want to do with the value
                    String Email = edittext.getText().toString();
                    FirebaseAuth.getInstance().sendPasswordResetEmail(Email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("Password Reset", "Email sent.");
                                        Toast.makeText(getApplication(), "Reset Password Mail Sent", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                }
            });
                alert.show();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
    }

    public boolean validateUser()
    {
        boolean isValid = true;
        String userPattern ="[a-zA-Z0-9]+";

        if ("".equals( login_Username_value))
        {
            login_Username.setError("Empty Field");
            login_Username.requestFocus();
            return false;
        }
        if ("".equals( login_Password_value))
        {
            login_Password.setError("Empty Field");
            login_Password.requestFocus();
            return false;
        }
        return true;
    }

    private void checkLogin(final String login_Username_value , final String login_Password_value )
    {
        mAuth.signInWithEmailAndPassword(login_Username_value, login_Password_value)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideDialog();
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login", "signInWithEmail:success");
                            //if(mAuth.getCurrentUser().isEmailVerified())
                            //{
                            Intent intent = new Intent(getApplicationContext(), module_navigation.class);
                            startActivity(intent);
                            finish();
                            setContentView(R.layout.activity_module_navigation);
                            //updateUI(user);
                            //}
                            //else
                            //{//   Toast.makeText(getApplicationContext(), "Verify Email",
                            //            Toast.LENGTH_SHORT).show();
                            //}
                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Log.w("Login", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }


                    }
                });
    }

    private boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
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

