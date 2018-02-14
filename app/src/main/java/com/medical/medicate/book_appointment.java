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

    public int n=10,i=1;
    private FirebaseDatabase mdatabase;
    private DatabaseReference mReference;
    private String namesList[] =new String[3];
    private String problems[]={"Choose Option","Heart Problem","Kidney Problems","Sex Problem"};
    private String timing[]={"Choose Option","Morning Shift(9:30AM - 12:30PM)","Evening Shift(4:30PM - 8:00PM)"};
    private ProgressDialog pDialog;

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

        namesList[0]="Choose Option";
        pDialog =new ProgressDialog(getContext());
        pDialog.setCancelable(false);

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mdatabase=FirebaseDatabase.getInstance("https://medicate-c8086-ee60d.firebaseio.com");
        mReference=mdatabase.getReference();

        final Spinner spin = (Spinner) view.findViewById(R.id.spinner);
        final Spinner spin2=(Spinner)view.findViewById(R.id.spin2);
        final Spinner spin3=(Spinner)view.findViewById(R.id.spin3);
        pDialog.setMessage("Fetching Form...Please Wait");
        showDialog();

        ArrayAdapter adapter2 = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,problems);
        spin2.setAdapter(adapter2);

        ArrayAdapter adapter3 = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,timing);
        spin3.setAdapter(adapter3);





        mReference.child("Hospital_Data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot messageSnapshot:dataSnapshot.getChildren())
                {
                    Message msg = messageSnapshot.getValue(Message.class);
                    Log.d("Name",msg.Hospital_Name);
                    namesList[i]=msg.Hospital_Name;
                    i++;

                }
                ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,namesList);
                spin.setAdapter(adapter);
                hideDialog();
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
        Message(String Hospital_Name){

            this.Hospital_Name=Hospital_Name;
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
