package com.example.arashi.myapplication.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;


import com.example.arashi.myapplication.Object.Class;
import com.example.arashi.myapplication.Object.Roster;
import com.example.arashi.myapplication.Store.ClassLocalStore;

import java.util.ArrayList;

public class ShowCheckStudent extends AppCompatActivity {

    ListView listView;
    ArrayList<Roster> listItem;
    CheckStudentAdapter adapter;
    ClassLocalStore classLocalStore;
    SeverRequests severRequests;
    Class classroom;
    Button presentButton,absentButton;
    TableRow tableTextHead;
    LinearLayout down;
    TextView presentText,absentText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_check_student);

        absentButton = (Button) findViewById(R.id.absentButton);
        presentButton = (Button) findViewById(R.id.presentButton);
        tableTextHead = (TableRow) findViewById(R.id.tableTextHead);
        down = (LinearLayout) findViewById(R.id.down);
        presentText = (TextView) findViewById(R.id.presentText);
        absentText = (TextView) findViewById(R.id.absentText);

        listView = (ListView) findViewById(R.id.listView1);
        listItem = new ArrayList<>();
        classLocalStore = new ClassLocalStore(this);
        severRequests = new SeverRequests(this);

        adapter = new CheckStudentAdapter(this,listItem);
        listView.setAdapter(adapter);
        classroom = classLocalStore.getJoinedInClass();

        tableTextHead.setVisibility(View.INVISIBLE);
        down.setVisibility(View.INVISIBLE);
        presentText.setVisibility(View.INVISIBLE);
        absentText.setVisibility(View.INVISIBLE);


        severRequests.showCheckedStudentListInBackground(classroom, new GetShowCheckedStudent() {
            @Override
            public void done(ArrayList<Roster> returnedListRoster) {
                if(returnedListRoster.size() > 0){
                    listItem = returnedListRoster;
                    adapter.setListData(listItem);
                }
            }
        });
        presentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableTextHead.setVisibility(View.VISIBLE);
                down.setVisibility(View.VISIBLE);
                presentText.setVisibility(View.VISIBLE);
                absentText.setVisibility(View.GONE);
            }
        });
        absentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableTextHead.setVisibility(View.VISIBLE);
                down.setVisibility(View.VISIBLE);
                presentText.setVisibility(View.GONE);
                absentText.setVisibility(View.VISIBLE);
            }
        });

    }
}
