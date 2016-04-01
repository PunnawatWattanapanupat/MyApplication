package com.example.arashi.myapplication.Store;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.arashi.myapplication.Object.Announcement;
import com.example.arashi.myapplication.Object.Session;


/**
 * Created by Arashi on 3/2/2016.
 */
public class SessionLocalStore {
    public static final String SP_NAME = "sessionDetails";
    SharedPreferences sessionLocalDatabase;

    public SessionLocalStore(Context context){
        sessionLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }


    public void storeSessionData(Session session){
        SharedPreferences.Editor spEditor = sessionLocalDatabase.edit();
        spEditor.putBoolean("is_check",session.is_check);

//        spEditor.putString("topic", announcement.topic);
//        spEditor.putString("detail", announcement.detail);
//        spEditor.putString("photo", announcement.photo);
//        spEditor.putString("date_post", announcement.detail);
//        spEditor.putInt("user_id",announcement.user_id);
        spEditor.commit();
    }

    public Session getShowSession(){

        boolean is_check = sessionLocalDatabase.getBoolean("is_check",false);
//        String topic = announceLocalDatabase.getString("topic", "");
//        String detail = announceLocalDatabase.getString("detail","");
//        String photo = announceLocalDatabase.getString("photo","");
//        String date_post = announceLocalDatabase.getString("date_post","");
//        int user_id = announceLocalDatabase.getInt("user_id",-1);

        Session storedSession = new Session(is_check);
//        Announcement storedAnnounce = new Announcement(topic, detail, photo,date_post, user_id);
        return storedSession;
    }
    public void setSessionForShow(boolean joinedIn){
        SharedPreferences.Editor spEditor = sessionLocalDatabase.edit();
        spEditor.putBoolean("JoinedIn", joinedIn);
        spEditor.commit();
    }

    public boolean getSession(){
        if(sessionLocalDatabase.getBoolean("Show", false)){
            return true;
        }
        else{
            return false;
        }
    }
    public void cleanSessionData(){
        SharedPreferences.Editor spEditor = sessionLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
