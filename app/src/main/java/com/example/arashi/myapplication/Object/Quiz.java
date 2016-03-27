package com.example.arashi.myapplication.Object;

import java.util.ArrayList;

/**
 * Created by Ooppo on 14/3/2559.
 */
public class Quiz {
    public String quiz_name;
    public int quizID,is_active,class_id;
    public ArrayList<Question> questionArray;

    public Quiz (String quiz_name,int quizID,int is_active,int class_id){
        questionArray = new ArrayList<>();
        this.quiz_name=quiz_name;
        this.quizID=quizID;
        this.is_active=is_active;
        this.class_id=class_id;
}

    public void addQuestionforQuiz(String q,String a1,String a2,String a3,String a4,String correctAnswer){
       questionArray.add(new Question(q,a1,a2,a3,a4,correctAnswer));
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
}
