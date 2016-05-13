package com.example.arashi.myapplication.Object;

/**
 * Created by Arashi on 1/29/2016.
 */
public class User {
    public String username, name, password, email;
    public int user_id,is_teacher;


    public User (String username, String name, String password, String email, int is_teacher){
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.is_teacher = is_teacher;
    }

    public User (String username, String password){
        this.user_id = -1;
        this.username = username;
        this.name = "";
        this.password = password;
        this.email = "";
        this.is_teacher = -1;
    }
    public User (int user_id,String username, String name, String password, String email, int is_teacher){
        this.user_id = user_id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.is_teacher = is_teacher;
    } public User (int user_id,String username, String name, String email){
        this.user_id = user_id;
        this.username = username;
        this.name = name;
        this.password = "";
        this.email = email;
        this.is_teacher = -1;
    }
    public User (String name){
        this.user_id = -1;
        this.username = "";
        this.name = name;
        this.password = "";
        this.email = "";
        this.is_teacher = -1;
    }
}
