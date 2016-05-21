package com.example.arashi.myapplication.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arashi.myapplication.Object.User;
import com.example.arashi.myapplication.Object.Class;
import com.example.arashi.myapplication.Store.ClassLocalStore;
import com.example.arashi.myapplication.Store.UserLocalStore;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;


import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Ooppo on 21/2/2559.
 */


public class PopFragment3 extends Activity implements View.OnClickListener /*implements View.OnClickListener */{
    EditText Questiontext;
    String question;
    int user_id;
    Button Done,Back;
    UserLocalStore userLocalStore;
    User user;
    Class classroom;
    ClassLocalStore classLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_qa);
        DisplayMetrics dm =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int width = dm.widthPixels;
        int heighht = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(heighht*.4));
        Questiontext = (EditText) findViewById(R.id.Questiontext);

        Done = (Button)findViewById(R.id.Done);
        Back = (Button)findViewById(R.id.Back);

        Done.setOnClickListener(this);
        Back.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this);
        user = userLocalStore.getLoggedInUser();


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Done:
                question = Questiontext.getText().toString();
                user_id = user.user_id;
                classLocalStore = new ClassLocalStore(this);
                classroom = classLocalStore.getJoinedInClass();
                addquestion(question,user_id, classroom);
                break;
            case R.id.Back:
                onBackPressed();
                break;
        }
    }

    public void addquestion(String q,int uid, Class classroom){

        ServerRequestsQA serverRequestsQA = new ServerRequestsQA(this);

        // Create our Installation query
        ParseQuery pushQuery = ParseInstallation.getQuery();
        pushQuery.whereEqualTo("classQuestion", true);

        // Send push notification to query
        ParsePush push = new ParsePush();
        push.setQuery(pushQuery); // Set our Installation query
        //  push.se
        push.setMessage(q);
        push.sendInBackground();

        //

        // Add custom intent
        Intent cIntent = new Intent(this, TabFragment3.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                cIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create custom notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentText("You have new Question")
                .setContentTitle(q)
                .setContentIntent(pendingIntent);

        Notification notification = builder.build();
        NotificationManager nm = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        nm.notify(1410, notification);



        if(!q.isEmpty()) {
            serverRequestsQA.AddQuestion(q, uid, classroom, new GetAddQuestion() {
                @Override
                public void done(String booboo) {
                    if (booboo == null) {
                        showErrorMessage("Error");
                    } else {
                        finish();
                    }
                }
            });
        }
        else{
            Toast.makeText(PopFragment3.this,"Please fill the Question", Toast.LENGTH_SHORT).show();
        }
    }
    private void showErrorMessage(String Errmes){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage(Errmes);
        dialogBuilder.setPositiveButton("OK",null);
        dialogBuilder.show();
    }
}
