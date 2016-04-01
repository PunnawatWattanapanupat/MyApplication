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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arashi.myapplication.Object.Roster;
import com.example.arashi.myapplication.Object.Session;
import com.example.arashi.myapplication.Object.User;
import com.example.arashi.myapplication.Object.Class;
import com.example.arashi.myapplication.Store.ClassLocalStore;
import com.example.arashi.myapplication.Store.SessionLocalStore;
import com.example.arashi.myapplication.Store.UserLocalStore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class TabFragment1 extends Fragment {
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private EditText fromDateEtxt;
    private View mViewGroup;
   // private CheckBox testcheck;
    //    private EditText toDateEtxt;
    private SimpleDateFormat dateFormatter;

    GridView gridView_roster;
    ArrayList<User> listItem_roster;
    TextView textClassName, textClassCode, textNumberStudent;
    Button btn_checked_student;
    SeverRequests severRequests;
    Class classroom;
    ClassLocalStore classLocalStore;
    UserLocalStore userLocalStore;
    Session session;
    Roster roster;
    SessionLocalStore sessionLocalStore;
    RosterAdapter rosterAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_fragment_1, container, false);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        fromDateEtxt = (EditText) v.findViewById(R.id.etxt_fromdate);
        textClassName = (TextView) v.findViewById(R.id.textClassName);
        textClassCode = (TextView) v.findViewById(R.id.textClassCode);
        textNumberStudent = (TextView) v.findViewById(R.id.textNumberStudent);
        gridView_roster = (GridView) v.findViewById(R.id.grid_roster);
        btn_checked_student = (Button) v.findViewById(R.id.btn_checked_student);

        listItem_roster = new ArrayList<>();
        severRequests = new SeverRequests(getActivity());
        classLocalStore = new ClassLocalStore(getActivity());
        userLocalStore = new UserLocalStore(getActivity());
        sessionLocalStore = new SessionLocalStore(getActivity());


        rosterAdapter = new RosterAdapter(getActivity(),listItem_roster);
        gridView_roster.setAdapter(rosterAdapter);
        classroom = classLocalStore.getJoinedInClass();

        //show classname & class code & student amount
        textClassName.setText( classLocalStore.getJoinedInClass().classname);
        textClassCode.setText("Code : " + classLocalStore.getJoinedInClass().class_code);


        CheckBox checkBox = (CheckBox) v.findViewById(R.id.check_activate);
        final CheckBox checkRoster = (CheckBox) v.findViewById(R.id.check_roster);

        //for check active //teacher
        checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()){

                    session = new Session(true);
                    sessionLocalStore.storeSessionData(session);
                    sessionLocalStore.setSessionForShow(true);
                    Toast.makeText(getActivity(), "students are checked !!! =)",Toast.LENGTH_LONG).show();
                }

                else {
                    session = new Session(false);
                    sessionLocalStore.storeSessionData(session);
                    sessionLocalStore.setSessionForShow(true);
                    Toast.makeText(getActivity(), "uncheck !!! =)",Toast.LENGTH_LONG).show();

                }
            }
        });
        //Update data for check student
        String date_post = DateFormat.getDateTimeInstance().format(new Date());
        roster = new Roster(userLocalStore.getLoggedInUser().user_id, classLocalStore.getJoinedInClass().class_id, 1, date_post);
        //for check roster //student
        checkRoster.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()){
                    Toast.makeText(getActivity(), " Hello ",Toast.LENGTH_LONG).show();
                    checkRoster.setVisibility(v.GONE);
                    severRequests.updateRosterDataInBackground(roster, new GetRosterCallback() {
                        @Override
                        public void done(Roster returnedRoster) {
                            Toast.makeText(getActivity(), "You are checked",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });


        //Hide Checkbox If user is student.
        if(userLocalStore.getLoggedInUser().is_teacher != 1)
        {
            checkBox.setVisibility(v.GONE);
            btn_checked_student.setVisibility(v.GONE);
            checkRoster.setVisibility(v.GONE);
            if(sessionLocalStore.getShowSession().is_check == true)
            {
                checkRoster.setVisibility(v.VISIBLE);
                Toast.makeText(getActivity(), " Hello ",Toast.LENGTH_LONG).show();
            }
        }
        //If user is teacher.
        else
        {
            checkRoster.setVisibility(v.GONE);
        }


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
       // testcheck = (CheckBox) v.findViewById(R.id.checkBox22);

        //show roster
        severRequests.showRosterStudentListInBackground(classroom, new GetShowRosterStudentCallback() {
            @Override
            public void done(ArrayList<User> returnedRosterStudent) {
                if(returnedRosterStudent.size() > 0){
                    listItem_roster = returnedRosterStudent;
                    rosterAdapter.setListData(listItem_roster);
                    //show  student amount
                    textNumberStudent.setText(Integer.toString(rosterAdapter.getCount()));
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
        sessionLocalStore.cleanSessionData();//clear session

        //click button checked student
        btn_checked_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ClassActivity.this,PopUpActivity.class));
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