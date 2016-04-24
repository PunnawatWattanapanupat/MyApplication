package com.example.arashi.myapplication.Activity;

import com.example.arashi.myapplication.Object.Question;

import java.util.ArrayList;

/**
 * Created by Arashi on 4/23/2016.
 */
public interface GetShowQuestionCallback {
    void done(ArrayList<Question> returnShowQuestion);
}
