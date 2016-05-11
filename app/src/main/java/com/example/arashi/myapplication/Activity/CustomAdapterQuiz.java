package com.example.arashi.myapplication.Activity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.arashi.myapplication.Object.Question;
import com.example.arashi.myapplication.Object.Quiz;

import java.util.ArrayList;

/**
 * Created by Arashi on 4/25/2016.
 */
public class CustomAdapterQuiz extends BaseAdapter {
    Context mContext;
    ArrayList<Quiz> quizData;

    public CustomAdapterQuiz(Context context, ArrayList<Quiz> quizData) {
        this.mContext= context;
        this.quizData = quizData;
    }

    public int getCount() {return quizData.size();}

    public Object getItem(int position) {return quizData.get(position);}

    public long getItemId(int position) {return position;}

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.listview_quiz, parent, false);

        Quiz quiz = quizData.get(position);
        String quiz_text = quiz.quiz_name;
        int quiz_active = quiz.is_active;
        String quiz_status ;
        if(quiz_active == 1){
            quiz_status = "Yes";
        }
        else {
            quiz_status = "No";

        }



        TextView numberCount = (TextView)view.findViewById(R.id.NumberCount);
        TextView quiz_name = (TextView)view.findViewById(R.id.quiz_name);
        TextView status = (TextView)view.findViewById(R.id.status);
        TextView score_text = (TextView) view.findViewById(R.id.score_text);
        TextView score_num = (TextView) view.findViewById(R.id.score_num);

        score_text.setVisibility(View.GONE);
        score_num.setVisibility(View.GONE);

        numberCount.setText(Integer.toString(quizData.size()-position));
        quiz_name.setText(quiz_text);
        status.setText(quiz_status);


        return view;
    }
    public void setListData(ArrayList<Quiz> quizData){
        this.quizData = quizData;
        this.notifyDataSetChanged();
    }
}
