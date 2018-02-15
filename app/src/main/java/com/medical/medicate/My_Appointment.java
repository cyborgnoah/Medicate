package com.medical.medicate;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class My_Appointment extends Fragment {


    private FirebaseDatabase mdatabase;
    private DatabaseReference mReference;
    private ProgressDialog pDialog;
    private ListView listView;



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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mdatabase = FirebaseDatabase.getInstance();
        mReference = mdatabase.getReference();

        listView = (ListView) view.findViewById(R.id.list);

        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);

        pDialog.setMessage("Fetching Form...Please Wait");
        showDialog();

        mReference.child("users").child(userId).child("Book Appointment").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> namesList = new ArrayList<String>();

                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                     Message msg = messageSnapshot.getValue(Message.class);
                   if(msg!=null){
                     namesList.add(msg.Fullname);
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
    }

    public static class  Message{
        String Fullname;
        Message(){}
        Message(String Fullname){
            this.Fullname=Fullname;
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
