package com.example.arashi.myapplication.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.example.arashi.myapplication.Object.Class;
import com.example.arashi.myapplication.Object.Roster;
import com.example.arashi.myapplication.Store.ClassLocalStore;

import java.util.ArrayList;
import java.util.Calendar;

public class ShowCheckStudent extends AppCompatActivity {

    private int mYear;
    private int mMonth;
    private int mDay;

    private TextView mDateDisplay;
    private Button mPickDate;

    static final int DATE_DIALOG_ID = 0;

    ListView listView;
    ArrayList<Roster> listItem;
    CheckStudentAdapter adapter;
    ClassLocalStore classLocalStore;
    SeverRequests severRequests;
    Class classroom;
    Roster roster;
    TableRow tableTextHead;
    LinearLayout down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_check_student);

        tableTextHead = (TableRow) findViewById(R.id.tableTextHead);
        down = (LinearLayout) findViewById(R.id.down);

        listView = (ListView) findViewById(R.id.listView1);
        listItem = new ArrayList<>();
        classLocalStore = new ClassLocalStore(this);
        severRequests = new SeverRequests(this);

        adapter = new CheckStudentAdapter(this,listItem);
        listView.setAdapter(adapter);
        classroom = classLocalStore.getJoinedInClass();



        //Choose date
        mDateDisplay = (TextView) findViewById(R.id.showMyDate);
        mPickDate = (Button) findViewById(R.id.myDatePickerButton);

        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // display the current date
        updateDisplay();


//        roster = new Roster(0, classLocalStore.getJoinedInClass().class_id, 1,mDateDisplay.getText().toString() );
//       // roster = new Roster(0, 4, 1,"13-05-2016" );
//        severRequests.showCheckedStudentListInBackground(roster, new GetShowCheckedStudent() {
//            @Override
//            public void done(ArrayList<Roster> returnedListRoster) {
//                if(returnedListRoster.size() > 0){
//                    listItem = returnedListRoster;
//                    adapter.setListData(listItem);
//
//                }
//            }
//        });




    }

    private void updateDisplay() {
        if(mMonth < 9 && mDay < 10) {
            this.mDateDisplay.setText(
                    new StringBuilder()
                            // Month is 0 based so add 1
                            .append(0)
                            .append(mDay).append("-")
                            .append(0)
                            .append(mMonth + 1).append("-")
                            .append(mYear).append(" "));
        }
        else if(mDay < 10){
            this.mDateDisplay.setText(
                    new StringBuilder()
                            // Month is 0 based so add 1
                            .append(0)
                            .append(mDay).append("-")
                            .append(mMonth + 1).append("-")
                            .append(mYear).append(" "));
        }
        else if(mMonth < 9){
            this.mDateDisplay.setText(
                    new StringBuilder()
                            // Month is 0 based so add 1
                            .append(mDay).append("-")
                            .append(0)
                            .append(mMonth + 1).append("-")
                            .append(mYear).append(" "));
        }
        else {
            this.mDateDisplay.setText(
                    new StringBuilder()
                            // Month is 0 based so add 1
                            .append(mDay).append("-")
                            .append(mMonth + 1).append("-")
                            .append(mYear).append(" "));
        }
        roster = new Roster(0, classLocalStore.getJoinedInClass().class_id, 1,mDateDisplay.getText().toString() );
        // roster = new Roster(0, 4, 1,"13-05-2016" );
        severRequests.showCheckedStudentListInBackground(roster, new GetShowCheckedStudent() {
            @Override
            public void done(ArrayList<Roster> returnedListRoster) {
                if(returnedListRoster.size() > 0){
                    listItem = returnedListRoster;
                    adapter.setListData(listItem);

                }
            }
        });
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();


                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }
}
