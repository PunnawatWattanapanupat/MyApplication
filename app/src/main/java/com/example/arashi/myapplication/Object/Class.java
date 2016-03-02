package com.example.arashi.myapplication.Object;

/**
 * Created by Arashi on 2/12/2016.
 */

public class Class {
    public String classname,class_code;
    public int user_id,class_id;

    public Class (int class_id,String classname,String class_code,int user_id){
        this.class_id = class_id;
        this.classname = classname;
        this.class_code = class_code;
        this.user_id = user_id;
    }

    public Class (String classname,String class_code,int user_id){
        this.classname = classname;
        this.class_code = class_code;
        this.user_id = user_id;
    }
    public Class (String classname,String class_code){
        this.class_id = -1;
        this.classname = classname;
        this.class_code = class_code;
        this.user_id = -1;
    }
}
