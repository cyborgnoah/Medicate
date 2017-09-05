package com.medical.medicate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Register extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle savedinstancestate)
    {
        super.onCreate(savedinstancestate);
        Intent regsister=getIntent();
        setContentView(R.layout.registeration_form);
    }
}
