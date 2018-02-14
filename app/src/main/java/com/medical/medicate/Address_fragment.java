package com.medical.medicate;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class Address_fragment extends Fragment {

    private FirebaseDatabase mdatabase;
    private DatabaseReference mReference;

    private EditText l1,l2,s1,s2,c1,c2,ct1,ct2,p1,p2;
    private CheckBox checkBox;
    private Button save;
    private String line11,line22,city11,city22,state11,state22,country11,country22,p11,p22;
    private ProgressDialog pDialog;


    public Address_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_address_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mdatabase=FirebaseDatabase.getInstance();
        mReference=mdatabase.getReference();

        pDialog =new ProgressDialog(getContext());
        pDialog.setCancelable(false);

        l1=(EditText) view.findViewById(R.id.line1);
        l2=(EditText) view.findViewById(R.id.line2);
        c1=(EditText) view.findViewById(R.id.city1);
        c2=(EditText) view.findViewById(R.id.city2);
        s1=(EditText) view.findViewById(R.id.state1);
        s2=(EditText) view.findViewById(R.id.state2);
        ct1=(EditText) view.findViewById(R.id.country1);
        ct2=(EditText) view.findViewById(R.id.country2);
        p1=(EditText)view.findViewById(R.id.pin1);
        p2=(EditText)view.findViewById(R.id.pin2);
        checkBox=(CheckBox)view.findViewById(R.id.check);
        save=(Button)view.findViewById(R.id.button2);

        pDialog.setMessage("Fetching Form...Please Wait");
        showDialog();

        mdatabase.getReference().child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Message obj1 = dataSnapshot.child("Present Address").getValue(Message.class);
                Message obj2 = dataSnapshot.child("Permanent Address").getValue(Message.class);

                if(obj1!=null){
                    l1.setText(obj1.Address);
                    c1.setText(obj1.City);
                    s1.setText(obj1.State);
                    ct1.setText(obj1.Country);
                    p1.setText(obj1.pincode);
                }
                if(obj2!=null){
                    l2.setText(obj1.Address);
                    c2.setText(obj1.City);
                    s2.setText(obj1.State);
                    ct2.setText(obj1.Country);
                    p2.setText(obj1.pincode);
                }
                hideDialog();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                line11=l1.getText().toString();
                city11=c1.getText().toString();
                state11=s1.getText().toString();
                country11=ct1.getText().toString();
                p11=p1.getText().toString();

                if(checkBox.isChecked()==true)
                {
                    l2.setText(line11);
                    c2.setText(city11);
                    s2.setText(state11);
                    ct2.setText(country11);
                    p2.setText(p11);
                    l2.setEnabled(false);
                    c2.setEnabled(false);
                    s2.setEnabled(false);
                    ct2.setEnabled(false);
                    p2.setEnabled(false);
                }
                else
                {
                    l2.setText("");
                    c2.setText("");
                    s2.setText("");
                    ct2.setText("");
                    p2.setText("");
                    l2.setEnabled(true);
                    c2.setEnabled(true);
                    s2.setEnabled(true);
                    ct2.setEnabled(true);
                    p2.setEnabled(true);
                }

            }
        });



        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                line11=l1.getText().toString();
                line22=l2.getText().toString();
                city11=c1.getText().toString();
                city22=c2.getText().toString();
                state11=s1.getText().toString();
                state22=s2.getText().toString();
                country11=ct1.getText().toString();
                country22=ct2.getText().toString();
                p11=p1.getText().toString();
                p22=p2.getText().toString();

                Message ob1=new Message(line11,city11,state11,country11,p11);
                mReference.child("users").child(userId).child("Present Address").setValue(ob1);

                Message ob2=new Message(line22,city22,state22,country22,p22);
                mReference.child("users").child(userId).child("Permanent Address").setValue(ob2);

                Toast.makeText(getActivity(), "Data Saved", Toast.LENGTH_LONG).show();

            }
        });
    }




    public static class Message{
        String Address,City,State,Country,pincode;
        Message(){}
        Message(String address,String city,String state,String country,String pincode){
            this.Address=address;
            this.City=city;
            this.State=state;
            this.Country=country;
            this.pincode=pincode;
        }
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
