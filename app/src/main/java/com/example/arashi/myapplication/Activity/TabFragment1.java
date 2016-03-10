package com.example.arashi.myapplication.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arashi.myapplication.Object.User;
import com.example.arashi.myapplication.Object.Class;
import com.example.arashi.myapplication.Store.ClassLocalStore;
import com.example.arashi.myapplication.Store.UserLocalStore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class TabFragment1 extends Fragment {
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private EditText fromDateEtxt;
    private View mViewGroup;
    private CheckBox testcheck;
    //    private EditText toDateEtxt;
    private SimpleDateFormat dateFormatter;

    GridView gridView_roster;
    ArrayList<User> listItem_roster;
    TextView textClassName, textClassCode;
    SeverRequests severRequests;
    Class classroom;
    ClassLocalStore classLocalStore;
    UserLocalStore userLocalStore;
    RosterAdapter rosterAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_fragment_1, container, false);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        fromDateEtxt = (EditText) v.findViewById(R.id.etxt_fromdate);
        textClassName = (TextView) v.findViewById(R.id.textClassName);
        textClassCode = (TextView) v.findViewById(R.id.textClassCode);
        gridView_roster = (GridView) v.findViewById(R.id.grid_roster);

        listItem_roster = new ArrayList<>();
        severRequests = new SeverRequests(getActivity());
        classLocalStore = new ClassLocalStore(getActivity());
        userLocalStore = new UserLocalStore(getActivity());


        //show classname & class code
        textClassName.setText( classLocalStore.getJoinedInClass().classname);
        textClassCode.setText("Code : " + classLocalStore.getJoinedInClass().class_code);

        rosterAdapter = new RosterAdapter(getActivity(),listItem_roster);
        gridView_roster.setAdapter(rosterAdapter);
        classroom = classLocalStore.getJoinedInClass();

        //Tin's Part
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

        //
        severRequests.showRosterStudentListInBackground(classroom, new GetShowRosterStudentCallback() {
            @Override
            public void done(ArrayList<User> returnedRosterStudent) {
                if(returnedRosterStudent.size() > 0){
                    listItem_roster = returnedRosterStudent;
                    rosterAdapter.setListData(listItem_roster);
                }
            }
        });

        //Click Grid View
        gridView_roster.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User roster_student = (User) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(),roster_student.name, Toast.LENGTH_SHORT).show();
                userLocalStore.storeUserData(roster_student);
                userLocalStore.setUserLoggedIn(true);
                startActivity(new Intent(getActivity(),PopRosterStudent.class));
            }
        });
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