package com.example.arashi.myapplication.Activity;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by Arashi on 3/27/2016.
 */
public class myApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}

