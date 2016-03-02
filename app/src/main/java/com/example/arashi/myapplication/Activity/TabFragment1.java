package com.example.arashi.myapplication.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.arashi.myapplication.Store.ClassLocalStore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class TabFragment1 extends Fragment {
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private EditText fromDateEtxt;
    private View mViewGroup;
    private CheckBox testcheck;
    TextView textClassName, textClassCode;
    ClassLocalStore classLocalStore;
    //    private EditText toDateEtxt;
    private SimpleDateFormat dateFormatter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_fragment_1, container, false);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        fromDateEtxt = (EditText) v.findViewById(R.id.etxt_fromdate);
        textClassName = (TextView) v.findViewById(R.id.textClassName);
        textClassCode = (TextView) v.findViewById(R.id.textClassCode);

        classLocalStore = new ClassLocalStore(getActivity());
        //show classname & class code
        textClassName.setText( classLocalStore.getJoinedInClass().classname);
        textClassCode.setText("Code : " + classLocalStore.getJoinedInClass().class_code);


        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();



        setDateTimeField();
        fromDateEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });


        mViewGroup = v.findViewById(R.id.viewsContainer);


        testcheck = (CheckBox) v.findViewById(R.id.checkBox22);

        return v;
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }
}