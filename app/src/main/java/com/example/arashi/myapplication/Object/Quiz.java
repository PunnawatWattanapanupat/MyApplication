package com.example.arashi.myapplication.Object;

import java.util.ArrayList;

/**
 * Created by Ooppo on 14/3/2559.
 */
public class Quiz {
    public String quiz_name;
    public boolean is_active;
    public int quizID;
    public ArrayList<Question> questionArray;

    public Quiz (String quiz_name){
        questionArray = new ArrayList<>();
        this.quiz_name=quiz_name;
}
    public String QuizConcat() {
        String AllConCat="";
        for(int i =0;i<questionArray.size();i++){
            Question q = questionArray.get(i);
            String question = q.question;
            String a1 = q.ans1;
            String a2 = q.ans2;
            String a3 = q.ans3;
            String a4 = q.ans4;
            AllConCat += question+"--"+a1+"--"+a2+"--"+a3+"--"+a4;
            if(i!=questionArray.size()-1)
            {
                AllConCat +="//";
            }
        }
        return AllConCat;
    }
    public void addQuestionforQuiz(String q,String a1,String a2,String a3,String a4){
       questionArray.add(new Question(q,a1,a2,a3,a4));
    }
}
