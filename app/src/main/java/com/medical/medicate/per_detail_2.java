package com.medical.medicate;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class per_detail_2 extends Fragment {


    private Button save;
    private ImageButton img;
    private RadioButton male,female;
    private EditText fname,lname,dob,mobile;
    private RadioGroup radioGroup;
    private String first_name,last_name,date_of_birth,mobile_no,gen=null;

    private int pYear;
    private int pMonth;
    private int pDay;
    static final int DATE_DIALOG_ID = 0;

    /*private DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    pYear = year;
                    pMonth = monthOfYear;
                    pDay = dayOfMonth;
                    updateDisplay();
                }
            };
    private void updateDisplay() {
        dob.setText(
                new StringBuilder()
                        .append(pDay).append("/")
                        .append(pMonth + 1).append("/")
                        .append(pYear).append(" "));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,pDateSetListener,pYear, pMonth, pDay);
        }
        return null;
    }*/


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

        save=(Button)view.findViewById(R.id.save);
        fname=(EditText)view.findViewById(R.id.editText);
        lname=(EditText)view.findViewById(R.id.editText2);
        dob=(EditText)view.findViewById(R.id.dob);

        img=(ImageButton)view.findViewById(R.id.imageButton);
        mobile=(EditText)view.findViewById(R.id.phone);
        male=(RadioButton)view.findViewById(R.id.male);
        female=(RadioButton)view.findViewById(R.id.female);
        radioGroup=(RadioGroup)view.findViewById(R.id.gender);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* final Calendar c = Calendar.getInstance();
                pYear = c.get(Calendar.YEAR);
                pMonth = c.get(Calendar.MONTH);
                pDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dob.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, pYear, pMonth, pDay);
                dpd.show();

            */}
        });


        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"hello",Toast.LENGTH_LONG).show();
            }
        });

    }
}
