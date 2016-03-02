package com.example.arashi.myapplication.Activity;

/**
 * Created by Ooppo on 11/2/2559.
 */
public class Announcement {
    public String topic, detail, photo ;
    public int  user_id;
    public Announcement(String topic, String detail, String photo , int user_id){
        this.topic = topic;
        this.detail = detail;
        this.photo = photo;
        this.user_id = user_id;
    }

}
