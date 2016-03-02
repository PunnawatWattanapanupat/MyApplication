package com.example.arashi.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.arashi.myapplication.Object.User;

/**
 * Created by Arashi on 1/29/2016.
 */
public class UserLocalStore {
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putInt("user_id",user.user_id);
        spEditor.putString("username", user.username);
        spEditor.putString("name", user.name);
        spEditor.putString("password", user.password);
        spEditor.putString("email", user.email);
        spEditor.putInt("is_teacher", user.is_teacher);
        spEditor.commit();
    }

    public User getLoggedInUser(){
        int user_id = userLocalDatabase.getInt("user_id",-1);
        String username = userLocalDatabase.getString("username", "");
        String name = userLocalDatabase.getString("name","");
        String password = userLocalDatabase.getString("password","");
        String email = userLocalDatabase.getString("email","");
        int is_teacher = userLocalDatabase.getInt("is_teacher", -1);

        User storedUser = new User(user_id,username, name, password, email, is_teacher);
        return storedUser;
    }
    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("LoggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        if(userLocalDatabase.getBoolean("LoggedIn", false)){
            return true;
        }
        else{
            return false;
        }
    }
    public void cleanUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }

}
