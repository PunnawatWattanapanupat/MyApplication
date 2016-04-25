package com.example.arashi.myapplication.Activity;

import com.example.arashi.myapplication.Object.Quiz;

import java.util.ArrayList;

/**
 * Created by Arashi on 4/25/2016.
 */
public interface GetShowQuizCallback {
    void done(ArrayList<Quiz> returnedShowQuiz);
}
