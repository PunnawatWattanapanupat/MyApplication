package com.example.arashi.myapplication.Object;

/**
 * Created by Arashi on 5/16/2016.
 */
public class Understand {
    public String understand;
    public int user_id, class_id, is_first, count_understand;
    public Understand(String understand, int user_id, int class_id, int is_first){
        this.understand = understand;
        this.user_id = user_id;
        this.class_id = class_id;
        this.is_first = is_first;
    }
    public Understand( int class_id){
        this.understand = "";
        this.user_id = -1;
        this.class_id = class_id;
        this.is_first = -1;
    }
    public Understand(String understand , int class_id){
        this.understand = understand;
        this.user_id = -1;
        this.class_id = class_id;
        this.is_first = -1;
    }
    public Understand(String understand , int class_id, int count_understand){
        this.understand = understand;
        this.user_id = -1;
        this.class_id = class_id;
        this.is_first = -1;
        this.count_understand = count_understand;
    }
}
