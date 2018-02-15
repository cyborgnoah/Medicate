package com.medical.medicate;


import android.app.AlertDialog;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

public class My_Appointment extends Fragment  {


    private FirebaseDatabase mdatabase;
    private DatabaseReference mReference;
    private ProgressDialog pDialog;
    private ListView listView;

    private String status;



    public My_Appointment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my__appointment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mdatabase = FirebaseDatabase.getInstance();
        mReference = mdatabase.getReference();

        final List<String> namesList = new ArrayList<String>();
        final List<String> approved = new ArrayList<String>();

        listView = (ListView) view.findViewById(R.id.list);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        pDialog.setMessage("Fetching Form...Please Wait");
        showDialog();

        mReference.child("users").child(userId).child("Book Appointment").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {




                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                     Message msg = messageSnapshot.getValue(Message.class);
                   if(msg!=null){
                     namesList.add("'"+msg.Hospital+"'"+" hospital on "+msg.Date+" in "+msg.Time);
                     approved.add(msg.Approved);
                   }
                }

                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getActivity(), R.layout.activity_listview, R.id.textView,namesList);
                listView.setAdapter(areasAdapter);

                hideDialog();
               // Toast.makeText(getActivity(),"i am out",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Error", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Appointment Status");

                status=approved.get(i).toString();

                if(status.equals("No")) {
                    builder.setMessage("Appointment not confirmed yet.");
                    builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int which) {
                                }
                            });
                    builder.show();

                }else if(status.equals("Yes")){
                    builder.setMessage("Appointment confirmed.");
                    builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                        }
                    });
                    builder.show();
                }
            }
        });

    }

    public static class  Message{
        String Fullname,Date,Time,Approved,Hospital;
        Message(){}
        Message(String Fullname,String Date,String Time,String Approved ,String Hospital){
            this.Fullname=Fullname;
            this.Date=Date;
            this.Time=Time;
            this.Approved=Approved;
            this.Hospital=Hospital;
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
