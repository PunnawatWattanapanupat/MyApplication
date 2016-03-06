package com.example.arashi.myapplication.Store;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.arashi.myapplication.Object.Announcement;

/**
 * Created by Arashi on 3/2/2016.
 */
public class AnnounceLocalStore {
    public static final String SP_NAME = "announceDetails";
    SharedPreferences announceLocalDatabase;

    public AnnounceLocalStore(Context context){
        announceLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }


    public void storeAnnounceData(Announcement announcement){
        SharedPreferences.Editor spEditor = announceLocalDatabase.edit();
        spEditor.putString("topic", announcement.topic);
        spEditor.putString("detail", announcement.detail);
        spEditor.putString("photo", announcement.photo);
        spEditor.putInt("user_id",announcement.user_id);
        spEditor.commit();
    }

    public Announcement getShowAnnounce(){

        String topic = announceLocalDatabase.getString("topic", "");
        String detail = announceLocalDatabase.getString("detail","");
        String photo = announceLocalDatabase.getString("photo","");
        int user_id = announceLocalDatabase.getInt("user_id",-1);


        Announcement storedAnnounce = new Announcement(topic, detail, photo, user_id);
        return storedAnnounce;
    }
    public void setAnnounceForShow(boolean joinedIn){
        SharedPreferences.Editor spEditor = announceLocalDatabase.edit();
        spEditor.putBoolean("JoinedIn", joinedIn);
        spEditor.commit();
    }

    public boolean getAnnounce(){
        if(announceLocalDatabase.getBoolean("Show", false)){
            return true;
        }
        else{
            return false;
        }
    }
    public void cleanAnnounceData(){
        SharedPreferences.Editor spEditor = announceLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
