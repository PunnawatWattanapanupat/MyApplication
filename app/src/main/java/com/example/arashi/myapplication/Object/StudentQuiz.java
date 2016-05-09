package com.example.arashi.myapplication.Object;

/**
 * Created by Arashi on 5/7/2016.
 */
public class StudentQuiz {
    public int student_quiz_id, quiz_id, quizquestionpack_id, user_id;
    public String student_answer;

    public StudentQuiz(String student_answer, int quiz_id, int quizquestionpack_id, int user_id){
        this.student_answer = student_answer;
        this.quiz_id = quiz_id;
        this.quizquestionpack_id = quizquestionpack_id;
        this.user_id = user_id;
    }
    public StudentQuiz(int student_quiz_id, String student_answer, int quizquestionpack_id, int user_id){
        this.student_quiz_id = student_quiz_id;
        this.student_answer = student_answer;
        this.quizquestionpack_id = quizquestionpack_id;
        this.user_id = user_id;
    }
}
