package com.example.arashi.myapplication.Activity;

/**
 * Created by Ooppo on 16/3/2559.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arashi.myapplication.Object.Question;

import java.util.ArrayList;


public class CustomAdapterQuizQuestion extends BaseAdapter {
    Context mContext;
    ArrayList<Question> questionData;

    public CustomAdapterQuizQuestion(Context context, ArrayList<Question> questionData) {
        this.mContext= context;
        this.questionData = questionData;
    }

    public int getCount() {return questionData.size();}

    public Object getItem(int position) {return questionData.get(position);}

    public long getItemId(int position) {return position;}

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.listview_quiz_question, parent, false);


        Question question = questionData.get(position);
        String questionText = question.question;
        String ans1 = question.ans1;
        String ans2 = question.ans2;
        String ans3 = question.ans3;
        String ans4 = question.ans4;

        TextView numberCount = (TextView)view.findViewById(R.id.NumberCount);
        TextView text_name_question = (TextView)view.findViewById(R.id.text_name_question);
        TextView textChoiceA = (TextView)view.findViewById(R.id.textChoiceA);
        TextView textChoiceB = (TextView)view.findViewById(R.id.textChoiceB);
        TextView textChoiceC = (TextView)view.findViewById(R.id.textChoiceC);
        TextView textChoiceD = (TextView)view.findViewById(R.id.textChoiceD);

        numberCount.setText(Integer.toString(position+1));
        text_name_question.setText(questionText);
        textChoiceA.setText(ans1);
        textChoiceB.setText(ans2);
        textChoiceC.setText(ans3);
        textChoiceD.setText(ans4);
        return view;
    }
    public void setListData(ArrayList<Question> questionData){
        this.questionData = questionData;
        this.notifyDataSetChanged();
    }
}