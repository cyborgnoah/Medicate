package com.medical.medicate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication_Register extends AppCompatActivity
{
    private EditText reg_Email, reg_Password, reg_ConfirmPassword;
    private String reg_Email_value, reg_Password_value, reg_ConfirmPassword_value;
    private Button reg_Register;
    private ProgressDialog pDialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_register);

        mAuth = FirebaseAuth.getInstance();

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        reg_Register= (Button)findViewById(R.id.reg_Register);
        reg_Email = (EditText)findViewById(R.id.reg_Email );
        reg_Password= (EditText)findViewById(R.id.reg_Password);
        reg_ConfirmPassword= (EditText)findViewById(R.id.reg_ConfrimPassword);

        reg_Register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                reg_Email_value = reg_Email.getText().toString();
                reg_Password_value = reg_Password.getText().toString();
                reg_ConfirmPassword_value = reg_ConfirmPassword.getText().toString();
                if(isNetworkAvailable()!=true)
                {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }
                else
                {
                    boolean isValidRegistration = validateUser();
                    if (isValidRegistration)
                    {
                        pDialog.setMessage("Registering...Please Wait");
                        showDialog();
                        registerUser(reg_Email_value, reg_Password_value);
                    }
                }
            }
        });
    }

    public boolean validateUser()
    {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

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

    private void registerUser(final String reg_Email_value, final String  reg_Password_value)
    {
        mAuth.createUserWithEmailAndPassword(reg_Email_value, reg_Password_value)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideDialog();
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Registration", "createUserWithEmail:success");
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // email sent
                                                // after email is sent just logout the user and finish this activity
                                                FirebaseAuth.getInstance().signOut();
                                                Intent intent = new Intent(Authentication_Register.this, Authentication_Login.class);
                                                startActivity(intent);
                                                finish();
                                                setContentView(R.layout.authentication_login);
                                            }
                                            else
                                            {
                                                // email not sent, so display message and restart the activity or do whatever you wish to do
                                                //restart this activity
                                                Toast.makeText(getApplicationContext(), "E-Mail not Sent",
                                                        Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Registration", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "User Already Exists",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });


        }

    public boolean isNetworkAvailable()
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
