package com.example.arashi.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.arashi.myapplication.Object.Question;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by Ooppo on 24/4/2559.
 */
public class Popup_Result__Quiz_First_Page extends Activity{

    Question question, find_question_id;
    ServerRequestQuizQuestion serverRequestQuizQuestion;
    ArrayList<BarEntry> entries = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<String>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_result_quiz_first_page);


        Bundle bundle = getIntent().getExtras();
        final int count_choice = bundle.getInt("count_choice");

        final int quiz_id = bundle.getInt("quizID");
        serverRequestQuizQuestion = new ServerRequestQuizQuestion(this);

        DisplayMetrics dm =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int width = dm.widthPixels;
        int heighht = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(heighht*.4));



                for(int i = 1; i <= count_choice; i++){
                    //find quizquestionpack_id for checking correct choice number
                    question = new Question(quiz_id,i);
                    serverRequestQuizQuestion.fetchQuizQuestionDataInBackground(question, new GetQuestionCallback() {
                        @Override
                        public void done(Question returnQuestion) {
                            //check correct choice number in quiz by using quizquestionpack_id
                            find_question_id = new Question(returnQuestion.quizquestionpack_id, quiz_id, returnQuestion.numberQuestion);
                            serverRequestQuizQuestion.checkCorrectChoiceInBackground(find_question_id, new GetQuestionCallback() {
                                @Override
                                public void done(Question returnQuestion) {

                                    BarChart barChart = (BarChart) findViewById(R.id.bar1chart);
                                    // creating labels

                                    entries.add(new BarEntry(returnQuestion.count_question, returnQuestion.numberQuestion-1));
                                    labels.add(Integer.toString(returnQuestion.numberQuestion));


                                    BarDataSet dataset = new BarDataSet(entries, "# of Answers");


                                    barChart.setDescription("");  // set the description

                                    barChart.animateY(1000);
                                    BarData data = new BarData(labels, dataset);
                                    barChart.setData(data); // set the data and list of lables into chart

                                }
                            });
                        }
                   // Toast.makeText(Popup_Result__Quiz_First_Page.this, "Hello "+ quizquestionpack_id, Toast.LENGTH_LONG).show();
                    });

                }








    }
}
