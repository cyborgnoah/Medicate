package com.medical.medicate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Login extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle savedinstantstate)
    {
        super.onCreate(savedinstantstate);
        setContentView(R.layout.login);
        Intent login=getIntent();
    }

    public void register(View view)
    {
        Intent register=new Intent(Login.this,Register.class);
        Login.this.startActivity(register);
    }
}
