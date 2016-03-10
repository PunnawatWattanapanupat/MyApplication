package com.example.arashi.myapplication.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.arashi.myapplication.Store.UserLocalStore;

public class PopRosterStudent extends AppCompatActivity {

    TextView fullName_text, username_text, email_text;
    Button ok_roster;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_roster_student);

        DisplayMetrics dm =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int width = dm.widthPixels;
        int heighht = dm.heightPixels;

        getWindow().setLayout((int)(width*.6),(int)(heighht*.6));

        fullName_text = (TextView) findViewById(R.id.fullName_text);
        username_text = (TextView) findViewById(R.id.username_text);
        email_text = (TextView) findViewById(R.id.email_text);
        ok_roster = (Button) findViewById(R.id.ok_roster);

        userLocalStore = new UserLocalStore(this);

        fullName_text.setText(userLocalStore.getLoggedInUser().name);
        username_text.setText(userLocalStore.getLoggedInUser().username);
        email_text.setText(userLocalStore.getLoggedInUser().email);

        ok_roster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
