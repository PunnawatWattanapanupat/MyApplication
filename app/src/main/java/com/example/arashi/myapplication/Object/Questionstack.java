package com.example.arashi.myapplication.Object;


/**
 * Created by Ooppo on 24/3/2559.
 */
public class Questionstack {
    public int question_id;
    public String question;
    public int user_id;
    public String date;
    public int noNumber;
    public Questionstack(int question_id,String question,int user_id,String date,int noNumber){
        this.question_id = question_id;
        this.question = question;
        this.user_id = user_id;
        this.date = date;
        this.noNumber = noNumber;
    }
}
