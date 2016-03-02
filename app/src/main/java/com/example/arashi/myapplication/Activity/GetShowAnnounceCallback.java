package com.example.arashi.myapplication.Activity;

import com.example.arashi.myapplication.Object.Announcement;

import java.util.ArrayList;

/**
 * Created by Arashi on 3/2/2016.
 */
public interface GetShowAnnounceCallback {
    void done(ArrayList<Announcement> returnedShowAnnounce);
}
