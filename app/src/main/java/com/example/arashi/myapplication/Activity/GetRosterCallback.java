package com.example.arashi.myapplication.Activity;

import com.example.arashi.myapplication.Object.Roster;

import java.util.ArrayList;

/**
 * Created by Arashi on 2/20/2016.
 */
public interface GetRosterCallback {
    void done(Roster returnedRoster);
}