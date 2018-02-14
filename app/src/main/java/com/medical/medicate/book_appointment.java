package com.medical.medicate;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
public class book_appointment extends Fragment {

    private FirebaseDatabase mdatabase;
    private DatabaseReference mReference;
    private String namesList[] = {"Ehsan", "Rafique", "Qasim", "Doctor Qaleem"};


    public book_appointment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_appointment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mdatabase=FirebaseDatabase.getInstance();
        mReference=mdatabase.getReference();



        Spinner spin = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,namesList);
        spin.setAdapter(adapter);

        mReference.child("Hospital_Data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot messageSnapshot:dataSnapshot.getChildren())
                {
                    Message msg = messageSnapshot.getValue(Message.class);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Error", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

    }
    public static class Message{
        public String Hospital_Name;

        Message(){}
        Message(String hospital_name){

            this.Hospital_Name=hospital_name;
        }
    }
}
