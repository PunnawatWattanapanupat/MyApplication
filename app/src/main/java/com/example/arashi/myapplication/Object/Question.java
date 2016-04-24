package com.example.arashi.myapplication.Object;

/**
 * Created by Ooppo on 14/3/2559.
 */
public class Question {
    public String question,ans1,ans2,ans3,ans4,correctaAnswer;
    public int quizquestionpack_id, quiz_id;
    public Question (int quizquestionpack_id, String question,String ans1,String ans2,String ans3,String ans4,String correctaAnswer,int quiz_id){
        this.quizquestionpack_id = quizquestionpack_id;
        this.question=question;
        this.ans1=ans1;
        this.ans2=ans2;
        this.ans3=ans3;
        this.ans4=ans4;
        this.correctaAnswer=correctaAnswer;
        this.quiz_id = quiz_id;
    }
    public Question (String question,String ans1,String ans2,String ans3,String ans4,String correctaAnswer,int quiz_id){
        this.question=question;
        this.ans1=ans1;
        this.ans2=ans2;
        this.ans3=ans3;
        this.ans4=ans4;
        this.correctaAnswer=correctaAnswer;
        this.quiz_id = quiz_id;
    }
    public Question (String question,String ans1,String ans2,String ans3,String ans4){
        this.question=question;
        this.ans1=ans1;
        this.ans2=ans2;
        this.ans3=ans3;
        this.ans4=ans4;
    }
    public Question (int quizquestionpack_id,String question,String ans1,String ans2,String ans3,String ans4){
        this.quizquestionpack_id=quizquestionpack_id;
        this.question=question;
        this.ans1=ans1;
        this.ans2=ans2;
        this.ans3=ans3;
        this.ans4=ans4;
    }
    public Question (int quiz_id){
        this.question="";
        this.ans1="";
        this.ans2="";
        this.ans3="";
        this.ans4="";
        this.correctaAnswer="";
        this.quiz_id = quiz_id;
    }
}
