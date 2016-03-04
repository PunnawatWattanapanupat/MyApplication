package com.example.arashi.myapplication.Object;

/**
 * Created by Ooppo on 11/2/2559.
 */
public class Announcement {
    public String topic, detail, photo ;
    public int  user_id, class_id;
    public Announcement(String topic, String detail, String photo , int user_id, int class_id){
        this.topic = topic;
        this.detail = detail;
        this.photo = photo;
        this.user_id = user_id;
        this.class_id = class_id;
    }
    public Announcement(String topic, String detail, String photo , int user_id){
        this.topic = topic;
        this.detail = detail;
        this.photo = photo;
        this.user_id = user_id;
        this.class_id = -1;
    }

}
