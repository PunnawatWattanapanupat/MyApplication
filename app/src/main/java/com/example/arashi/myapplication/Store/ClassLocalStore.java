package com.example.arashi.myapplication.Store;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.arashi.myapplication.Activity.TabFragment1;
import com.example.arashi.myapplication.Object.Class;
/**
 * Created by Arashi on 2/21/2016.
 */
public class ClassLocalStore {
    public static final String SP_NAME = "classDetails";
    SharedPreferences classLocalDatabase;

    public ClassLocalStore(Context context){
        classLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }


    public void storeClassData(Class classroom){
        SharedPreferences.Editor spEditor = classLocalDatabase.edit();
        spEditor.putInt("class_id",classroom.class_id);
        spEditor.putString("classname", classroom.classname);
        spEditor.putString("class_code", classroom.class_code);
        spEditor.putInt("user_id",classroom.user_id);
        spEditor.commit();
    }

    public Class getJoinedInClass(){
        int class_id = classLocalDatabase.getInt("class_id",-1);
        String classname = classLocalDatabase.getString("classname", "");
        String class_code = classLocalDatabase.getString("class_code","");
        int user_id = classLocalDatabase.getInt("user_id",-1);


        Class storedClass = new Class(class_id, classname, class_code,user_id);
        return storedClass;
    }
    public void setClassJoinedIn(boolean joinedIn){
        SharedPreferences.Editor spEditor = classLocalDatabase.edit();
        spEditor.putBoolean("JoinedIn", joinedIn);
        spEditor.commit();
    }

    public boolean getClassJoinedIn(){
        if(classLocalDatabase.getBoolean("JoinedIn", false)){
            return true;
        }
        else{
            return false;
        }
    }
    public void cleanClassData(){
        SharedPreferences.Editor spEditor = classLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }

}
