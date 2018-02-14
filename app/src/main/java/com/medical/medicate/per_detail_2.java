package com.medical.medicate;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class    per_detail_2 extends Fragment {


    private FirebaseDatabase mdatabase;
    private DatabaseReference mReference;

    private Button save;
    private ImageButton img;
    private RadioButton male,female;
    private EditText fname,lname,dob,mobile;
    private RadioGroup radioGroup;
    private String first_name,last_name,date_of_birth,mobile_no,gen=null;
    private ProgressDialog pDialog;

    public per_detail_2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_per_detail_2, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mdatabase=FirebaseDatabase.getInstance();
        mReference=mdatabase.getReference();

        pDialog =new ProgressDialog(getContext());
        pDialog.setCancelable(false);

        save=(Button)view.findViewById(R.id.save);
        fname=(EditText)view.findViewById(R.id.editText);
        lname=(EditText)view.findViewById(R.id.editText2);
        dob=(EditText)view.findViewById(R.id.dob);

        img=(ImageButton)view.findViewById(R.id.imageButton);
        mobile=(EditText)view.findViewById(R.id.phone);
        male=(RadioButton)view.findViewById(R.id.male);
        female=(RadioButton)view.findViewById(R.id.female);
        radioGroup=(RadioGroup)view.findViewById(R.id.gender);

        pDialog.setMessage("Fetching Form...Please Wait");
        showDialog();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i==R.id.male){
                    gen="male";
                }else if(i==R.id.female){
                    gen="female";
                }
            }
        });


        mdatabase.getReference().child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Message obj = dataSnapshot.child("Personal details").getValue(Message.class);
                if (obj != null){
                    fname.setText(obj.First_name);
                    lname.setText(obj.Last_name);
                    dob.setText(obj.Date_of_Birth);
                    mobile.setText(obj.Mobile);
                    if(obj.Gender.equals("male"))
                    {
                       male.setChecked(true);
                    }else
                    {
                        female.setChecked(true);
                    }
                }
                hideDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


            



        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {


                first_name=fname.getText().toString();
                last_name=lname.getText().toString();
                date_of_birth=dob.getText().toString();
                mobile_no=mobile.getText().toString();
                boolean isValidRegistration = validateUser();
                if (isValidRegistration)
                {
                    Message msg=new Message(first_name,last_name,date_of_birth,gen,mobile_no);
                    mReference.child("users").child(userId).child("Personal details").setValue(msg);

                    Toast.makeText(getActivity(),"Date saved",Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    public static class Message{
        public String First_name,Last_name,Date_of_Birth,Mobile,Gender;

        Message(){}
        Message(String first_name,String last_name ,String date_of_birth,String gen,String mobile_no){
            this.First_name=first_name;
            this.Last_name=last_name;
            this.Date_of_Birth=date_of_birth;
            this.Gender=gen;
            this.Mobile=mobile_no;

        }
    }

    public boolean validateUser()
    {

        String fullnamePattern = "[a-zA-Z]+";
        if ("".equals(first_name))
        {
            fname.setError("Empty Field");
            fname.requestFocus();
            return false;
        }
        if (!first_name.matches(fullnamePattern))
        {
            fname.setError("character form");
            fname.setText("");
            fname.requestFocus();
            return false;
        }
        if ("".equals(last_name))
        {
            lname.setError("Empty Field");
            lname.requestFocus();
            return false;
        }
        if (!last_name.matches(fullnamePattern))
        {
            lname.setError("character form");
            lname.setText("");
            lname.requestFocus();
            return false;
        }
        if ("".equals(date_of_birth)) {

            dob.setError("Empty Field");
            dob.requestFocus();
            return false;
        }
        if ((male.isChecked()==false && female.isChecked()==false) || (male.isChecked()==true && female.isChecked()==true))
        {
            Toast.makeText(getActivity(), "select any one gender", Toast.LENGTH_LONG).show();
            return false;
        }
        if ("".equals(mobile_no)) {
            mobile.setError("Empty Field");
            mobile.requestFocus();
            return false;
        }
        if(mobile_no.length()>10 || mobile_no.length()<10)
        {
            mobile.setError("10 digit Mobile number");
            mobile.requestFocus();
            return false;
        }
        return true;
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
