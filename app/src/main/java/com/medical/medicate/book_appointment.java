package com.medical.medicate;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class book_appointment extends Fragment {

    public int n=10,i=1;
    private FirebaseDatabase mdatabase2,mdatabase1;
    private DatabaseReference mReference2,mReference1;
    private String timing[]={"Choose Option","Morning Shift(9:30AM - 12:30PM)","Evening Shift(4:30PM - 8:00PM)"};
    private ProgressDialog pDialog;
    private ImageButton imageButton;
    private EditText date,name,gen,age;
    private Button book;

    private int pYear;
    private int pMonth;
    private int pDay;

    private String fullname,gender,Age,hospital,time,apdate,approved,hospital_email,hospital_uid;

    public book_appointment() {
        // Required empty public constructor
    }
    final List<String> namesList_email = new ArrayList<String>();
    final List<String> namesList_uid = new ArrayList<String>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_appointment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        imageButton = (ImageButton) view.findViewById(R.id.imageButton);
        date = (EditText) view.findViewById(R.id.date);
        name = (EditText) view.findViewById(R.id.name);
        gen = (EditText) view.findViewById(R.id.gen);
        age = (EditText) view.findViewById(R.id.age);
        book = (Button) view.findViewById(R.id.book);
        final Spinner spin = (Spinner) view.findViewById(R.id.spin);

        final Spinner spin2 = (Spinner) view.findViewById(R.id.spin2);

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mdatabase1 = FirebaseDatabase.getInstance();
        mReference1 = mdatabase1.getReference();

        mdatabase2 = FirebaseDatabase.getInstance();
        mReference2 = mdatabase2.getReference();

        final Calendar cal = Calendar.getInstance();
        pYear = cal.get(Calendar.YEAR);
        pMonth = cal.get(Calendar.MONTH);
        pDay = cal.get(Calendar.DAY_OF_MONTH);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yr, int monthofyear, int dayofmonth) {
                        monthofyear = monthofyear + 1;
                        date.setText(dayofmonth + "/" + monthofyear + "/" + yr);
                    }
                }, pYear, pMonth, pDay);
                datePickerDialog.show();
            }
        });

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hospital = adapterView.getItemAtPosition(i).toString();
                hospital_email=namesList_email.get(i).toString();
                hospital_uid=namesList_uid.get(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                time = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        mdatabase1.getReference().child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Message1 obj = dataSnapshot.child("Personal details").getValue(Message1.class);
                if (obj != null) {
                    name.setText(obj.First_name + " " + obj.Last_name);
                    gen.setText(obj.Gender);
                    name.setEnabled(false);
                    gen.setEnabled(false);

                }else{
                    book.setEnabled(false);
                    Toast.makeText(getActivity(),"Fill 'Personal Detail' form first",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        pDialog.setMessage("Fetching Form...Please Wait");
        showDialog();

        mReference2.child("Hospital_Data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    final List<String> namesList = new ArrayList<String>();

                    for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                        Message msg = messageSnapshot.getValue(Message.class);
                        namesList.add(msg.Hospital_Name);
                        namesList_email.add(msg.Hospital_Email);
                        namesList_uid.add(msg.Hospital_Uid);
                    }

                    ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, namesList);
                    areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin.setAdapter(areasAdapter);

                hideDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Error", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
        ArrayAdapter adapter2 = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, timing);
        spin2.setAdapter(adapter2);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullname = name.getText().toString();
                gender = gen.getText().toString();
                Age = age.getText().toString();
                apdate = date.getText().toString();
                approved="No";

                boolean isValidRegistration = validateUser();
                if (isValidRegistration) {
                    Message2 appoint1 = new Message2(fullname, gender, Age, hospital, time, apdate, approved );
                    DatabaseReference newDatabaseReference = mReference1.child("users").child(userId).child("Book Appointment").push();
                    String TID=newDatabaseReference.getKey();
                    newDatabaseReference.setValue(appoint1);
                    /*mReference1.child("users").child(userId).child("Book Appointment").push().setValue(appoint1,new DatabaseReference.CompletionListener()
                    {@Override
                    public void onComplete(DatabaseError databaseError,
                        DatabaseReference databaseReference) {
                        Log.d("Value : ",""+databaseReference.getKey());
                    }
                    });*/

                    Message2 appoint2 = new Message2(fullname, gender, Age, hospital, time, apdate, userId, approved, TID);
                    mReference2.child("Appointment Requests").child(hospital_uid).push().setValue(appoint2);

                    Toast.makeText(getActivity(), "Appointment request generated ", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "Check 'My appointment' section for appointment status ", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public static class Message{
        public String Hospital_Name;
        public String Hospital_Email;
        public String Hospital_Uid;

        Message(){}
        Message(String Hospital_Name,String Hospital_Email,String Hospital_Uid){

            this.Hospital_Name=Hospital_Name;
            this.Hospital_Email=Hospital_Email;
            this.Hospital_Uid=Hospital_Uid;
        }
    }

    public static class Message1{
        public String First_name,Last_name,Gender;

        Message1(){}
        Message1(String First_name,String Last_name,String Gender){

            this.First_name=First_name;
            this.Last_name=Last_name;
            this.Gender=Gender;
        }
    }

    public static class  Message2{
        String Fullname,Gender,Age,Hospital,Time,Date,UserId,Approved,Tid;
        Message2(){}
        Message2(String Fullname,String Gender,String Age,String Hospital,String Time,String Date,String Approved){

            this.Fullname=Fullname;
            this.Gender=Gender;
            this.Age=Age;
            this.Hospital=Hospital;
            this.Time=Time;
            this.Date=Date;
            this.Approved=Approved;
        }
        Message2(String Fullname,String Gender,String Age,String Hospital,String Time,String Date,String userid,String Approve,String Tid){

            this.Fullname=Fullname;
            this.Gender=Gender;
            this.Age=Age;
            this.Hospital=Hospital;
            this.Time=Time;
            this.Date=Date;
            this.UserId=userid;
            this.Approved=Approve;
            this.Tid=Tid;
        }
    }

    public boolean validateUser()
    {
        String fullnamePattern = "[a-zA-Z ]+";
        if ("".equals(fullname))
        {
            name.setError("Empty Field");
            name.requestFocus();
            return false;
        }
        if (!fullname.matches(fullnamePattern))
        {
            name.setError("Character form");
            name.setText("");
            name.requestFocus();
            return false;
        }
        if ("".equals(gender))
        {
            gen.setError("Empty Field");
            gen.requestFocus();
            return false;
        }
        if (!"Male".equals(gender) && !"Female".equals(gender))
        {
            gen.setError("Enter correct gender");
            gen.setText("");
            gen.requestFocus();
            return false;
        }
        if ("".equals(Age)) {
            age.setError("Empty Field");
            age.requestFocus();
            return false;
        }
        if(!(Age.length()>0 && Age.length()<4))
        {
            age.setError("Enter Valid Age");
            age.requestFocus();
            return false;
        }
        if ("Choose Option".equals(hospital))
        {
            Toast.makeText(getActivity(), "Select a hospital", Toast.LENGTH_LONG).show();
            return false;
        }
        if ("Choose Option".equals(time))
        {
            Toast.makeText(getActivity(), "Select a timing", Toast.LENGTH_LONG).show();
            return false;
        }
        if ("".equals(apdate)) {

            date.setError("Empty Field");
            date.requestFocus();
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
