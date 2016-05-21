package com.example.arashi.myapplication.Activity;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arashi.myapplication.Object.Quiz;
import com.example.arashi.myapplication.Object.Roster;
import com.example.arashi.myapplication.Object.Understand;
import com.example.arashi.myapplication.Store.ClassLocalStore;
import com.example.arashi.myapplication.Store.UserLocalStore;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import java.util.ArrayList;

/**
 * Created by Ooppo on 16/5/2559.
 */
public class TabFragment5 extends Fragment {


    Button yesButton,noButton;
    TextView  show_understand;
    Understand understand;
    Roster roster;
    UserLocalStore userLocalStore;
    ClassLocalStore classLocalStore;
    SeverRequests severRequests;
    String concate = "";
    int digit = 0;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
              View v = inflater.inflate(R.layout.tab_fragment_5, container, false);
            yesButton = (Button) v.findViewById(R.id.yesButton);
            noButton = (Button) v.findViewById(R.id.noButton);
            show_understand = (TextView) v.findViewById(R.id.show_understand);

            userLocalStore = new UserLocalStore(getActivity());
            classLocalStore = new ClassLocalStore(getActivity());
            severRequests = new SeverRequests(getActivity());

            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
            installation.put("classUnderstand",true);
            installation.saveInBackground();

            //authority for show
            if(userLocalStore.getLoggedInUser().is_teacher == 1){

                yesButton.setVisibility(View.GONE);
                noButton.setVisibility(View.GONE);

            }else{
                yesButton.setVisibility(View.VISIBLE);
                noButton.setVisibility(View.VISIBLE);
            }

            understand = new Understand(classLocalStore.getJoinedInClass().class_id);
            severRequests.fetchUnderstandDataInBackground(understand, new GetUnderstandCallback() {
                @Override
                public void done(Understand returnUnderstand) {
                    if(returnUnderstand.is_first == 0 ){
                        //OnClick yes button
                        yesButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addUnderstand("YES",1);
                                show_understand.setText("I understand");
                                countTime();
                            }
                        });
                        //OnClick no button
                        noButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addUnderstand("NO",1);
                                show_understand.setText("I don't understand");
                                countTime();
                            }
                        });

                    }
                    else {
                        //OnClick yes button
                        yesButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addUnderstand("YES",1);
                                show_understand.setText("I understand");
                            }
                        });
                        //OnClick no button
                        noButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addUnderstand("NO",1);
                                show_understand.setText("I don't understand");
                            }
                        });
                    }

                }
            });


            return v;
        }

    private void addUnderstand(String status, int is_first){
        understand = new Understand(status, userLocalStore.getLoggedInUser().user_id, classLocalStore.getJoinedInClass().class_id,is_first);
        severRequests.storeUnderstandDataInBackground(understand, new GetUnderstandCallback() {
            @Override
            public void done(Understand understand) {
            }
        });
        yesButton.setVisibility(View.GONE);
        noButton.setVisibility(View.GONE);
    }

    private String getText(String text){
        concate = concate + text  ;
        return concate;
    }
    private int addNumber(int num){
        return digit = digit + num;
    }

    private void countTime(){
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
              //  count_time.setText("Time remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
               // count_time.setText("done!");


                // Create our Installation query
                ParseQuery pushQuery = ParseInstallation.getQuery();
                pushQuery.whereEqualTo("classUnderstand", true);

                // Send push notification to query
                final ParsePush push = new ParsePush();
                push.setQuery(pushQuery); // Set our Installation query
                //  push.se
               // push.setMessage(announcement.topic);




                //notice & update

                severRequests.updateUnderstandDataInBackground(understand, new GetUnderstandCallback() {
                    @Override
                    public void done(Understand returnUnderstand) {
                        understand = new Understand("YES",classLocalStore.getJoinedInClass().class_id);
                        severRequests.fetchCountUnderstandDataInBackground(understand, new GetUnderstandCallback() {
                            @Override
                            public void done(Understand returnUnderstand) {
                                getText("Understand : " + addNumber(returnUnderstand.count_understand) + "\n");
                            }
                        });
                        understand = new Understand("NO",classLocalStore.getJoinedInClass().class_id);
                        severRequests.fetchCountUnderstandDataInBackground(understand, new GetUnderstandCallback() {
                            @Override
                            public void done(Understand returnUnderstand) {
                                getText("NOT Understand : " + returnUnderstand.count_understand + "\n");
                                addNumber(returnUnderstand.count_understand);
                            }
                        });
                        roster = new Roster(classLocalStore.getJoinedInClass().class_id);
                        severRequests.fetchCountRosterDataInBackground(roster, new GetRosterCallback() {
                            @Override
                            public void done(Roster returnedRoster) {

                                //No Vote = All students in class - number of students understand + number of students not understand
                                push.setMessage(getText("No Answer : "+ (returnedRoster.class_id - addNumber(0))));
                                push.sendInBackground();
                            }
                        });
                        severRequests.deleteUnderstandDataInBackground(understand, new GetUnderstandCallback() {
                            @Override
                            public void done(Understand returnUnderstand) {

                            }
                        });
                    }
                });

            }

        }.start();
    }


}

