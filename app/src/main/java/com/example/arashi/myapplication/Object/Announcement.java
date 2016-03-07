package com.example.arashi.myapplication.Object;

/**
 * Created by Ooppo on 11/2/2559.
 */
public class Announcement {
    public String topic, detail, photo, date_post ;
    public int  user_id, class_id;
    public Announcement(String topic, String detail, String photo ,String date_post, int user_id, int class_id){
        this.topic = topic;
        this.detail = detail;
        this.photo = photo;
        this.date_post = date_post;
        this.user_id = user_id;
        this.class_id = class_id;
    }
    public Announcement(String topic, String detail, String photo ,String date_post, int user_id){
        this.topic = topic;
        this.detail = detail;
        this.photo = photo;
        this.date_post = date_post;
        this.user_id = user_id;
        this.class_id = -1;
    }

}
