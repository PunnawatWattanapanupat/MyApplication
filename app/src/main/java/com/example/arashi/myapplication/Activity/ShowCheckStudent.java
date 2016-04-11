package com.example.arashi.myapplication.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_check_student);

        listView = (ListView) findViewById(R.id.listView1);
        listItem = new ArrayList<>();
        classLocalStore = new ClassLocalStore(this);
        severRequests = new SeverRequests(this);

        adapter = new CheckStudentAdapter(this,listItem);
        listView.setAdapter(adapter);
        classroom = classLocalStore.getJoinedInClass();

        severRequests.showCheckedStudentListInBackground(classroom, new GetShowCheckedStudent() {
            @Override
            public void done(ArrayList<Roster> returnedListRoster) {
                if(returnedListRoster.size() > 0){
                    listItem = returnedListRoster;
                    adapter.setListData(listItem);
                }
            }
        });
    }
}
