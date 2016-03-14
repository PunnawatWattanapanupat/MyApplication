package com.example.arashi.myapplication.Object;

import java.util.ArrayList;

/**
 * Created by Ooppo on 14/3/2559.
 */
public class Quiz {
    String name;
    boolean is_active;
    int quizID;
    ArrayList<Question> questionArray;

    public Quiz (String name, int quizID){
        questionArray = new ArrayList<>();
        this.name=name;
        this.quizID=quizID;
}
}
