package com.example.arashi.myapplication.Object;

/**
 * Created by Arashi on 2/20/2016.
 */
public class Roster {
    public int user_id, class_id, check_student;
    public String date,session,name;
    public Roster(int user_id,int class_id,int check_student, String date){
        this.user_id = user_id;
        this.class_id = class_id;
        this.check_student = check_student;
        this.date = date;

    } public Roster(int user_id,int class_id,int check_student, String date, String name){
        this.user_id = user_id;
        this.class_id = class_id;
        this.check_student = check_student;
        this.date = date;
        this.name = name;
    }

    public Roster(int user_id,int class_id,int check_student){
        this.user_id = user_id;
        this.class_id = class_id;
        this.check_student = check_student;
    }

    public Roster(int class_id, String session){
        this.class_id = class_id;
        this.session = session;
    }

    public Roster(int user_id,int class_id){
        this.user_id = user_id;
        this.class_id = class_id;
    }
    public Roster(int class_id){
        this.class_id = class_id;
        this.session = "";
    }
}
