package com.example.arashi.myapplication.Activity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.arashi.myapplication.Object.Class;

import com.example.arashi.myapplication.Object.User;
import com.example.arashi.myapplication.Store.ClassLocalStore;
import com.example.arashi.myapplication.Store.UserLocalStore;

import java.util.ArrayList;


public class ClassActivity extends Activity {
  //  TextView txt_class_id;
    ListView listView;
    Button addClass;
    ArrayList<Class> listItem = new ArrayList<>();
    SeverRequests severRequests;
    ClassLocalStore classLocalStore;
    UserLocalStore userLocalStore;
    ClassAdapter adapter;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        severRequests = new SeverRequests(this);
        classLocalStore = new ClassLocalStore(this);
        userLocalStore = new UserLocalStore(this);
        user = userLocalStore.getLoggedInUser();
        //classroom = classLocalStore.getJoinedInClass();
        listView = (ListView) findViewById(R.id.listView1);

        adapter = new ClassAdapter(this, listItem);
        listView.setAdapter(adapter);
//        Bundle bundle = getIntent().getExtras();
//        userId = bundle.getInt("userId");


        severRequests.showClassListInBackground(user, new GetShowClassCallback() {
            @Override
            public void done(ArrayList<Class> returnedShowClass) {
                if (returnedShowClass.size() > 0) {
                    listItem = returnedShowClass;
                    adapter.setListData(listItem);
                } else {
                    Toast.makeText(ClassActivity.this,"No Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //OnClickListView
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Class classItem = (Class) arg0.getItemAtPosition(arg2);
           //     Toast.makeText(ClassActivity.this,Integer.toString(classItem.class_id), Toast.LENGTH_SHORT).show();
                classLocalStore.storeClassData(classItem);
                classLocalStore.setClassJoinedIn(true);
                startActivity(new Intent(ClassActivity.this,tabMainActivity.class));
            }
        });

        //Add Class Button Click
        addClass = (Button) findViewById(R.id.btn_add_class);
        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClassActivity.this,PopUpActivity.class));
            }
        });


    }

}
