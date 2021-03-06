package com.example.arashi.myapplication.Activity;

/**
 * Created by Ooppo on 16/3/2559.
 */

import android.content.Context;
import android.graphics.Color;
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
        String correct_ans = question.correctaAnswer;

        TextView numberCount = (TextView)view.findViewById(R.id.NumberCount);
        TextView text_name_question = (TextView)view.findViewById(R.id.text_name_question);
        TextView textChoiceA = (TextView)view.findViewById(R.id.textChoiceA);
        TextView textChoiceB = (TextView)view.findViewById(R.id.textChoiceB);
        TextView textChoiceC = (TextView)view.findViewById(R.id.textChoiceC);
        TextView textChoiceD = (TextView)view.findViewById(R.id.textChoiceD);
        TextView choiceA = (TextView)view.findViewById(R.id.choiceA);
        TextView choiceB = (TextView)view.findViewById(R.id.choiceB);
        TextView choiceC = (TextView)view.findViewById(R.id.choiceC);
        TextView choiceD = (TextView)view.findViewById(R.id.choiceD);

        numberCount.setText(Integer.toString(position+1));
        text_name_question.setText(questionText);
        textChoiceA.setText(ans1);
        textChoiceB.setText(ans2);
        textChoiceC.setText(ans3);
        textChoiceD.setText(ans4);

        if(correct_ans.equals(ans1)){
            textChoiceA.setTextColor(Color.RED);
            textChoiceB.setTextColor(Color.BLACK);
            textChoiceC.setTextColor(Color.BLACK);
            textChoiceD.setTextColor(Color.BLACK);
            choiceA.setTextColor(Color.RED);
            choiceB.setTextColor(Color.BLACK);
            choiceC.setTextColor(Color.BLACK);
            choiceD.setTextColor(Color.BLACK);
        }
        else if(correct_ans.equals(ans2)){
            textChoiceA.setTextColor(Color.BLACK);
            textChoiceB.setTextColor(Color.RED);
            textChoiceC.setTextColor(Color.BLACK);
            textChoiceD.setTextColor(Color.BLACK);
            choiceA.setTextColor(Color.BLACK);
            choiceB.setTextColor(Color.RED);
            choiceC.setTextColor(Color.BLACK);
            choiceD.setTextColor(Color.BLACK);
        }
        else if(correct_ans.equals(ans3)){
            textChoiceA.setTextColor(Color.BLACK);
            textChoiceB.setTextColor(Color.BLACK);
            textChoiceC.setTextColor(Color.RED);
            textChoiceD.setTextColor(Color.BLACK);
            choiceA.setTextColor(Color.BLACK);
            choiceB.setTextColor(Color.BLACK);
            choiceC.setTextColor(Color.RED);
            choiceD.setTextColor(Color.BLACK);
        }
        else if(correct_ans.equals(ans4)){
            textChoiceA.setTextColor(Color.BLACK);
            textChoiceB.setTextColor(Color.BLACK);
            textChoiceC.setTextColor(Color.BLACK);
            textChoiceD.setTextColor(Color.RED);
            choiceA.setTextColor(Color.BLACK);
            choiceB.setTextColor(Color.BLACK);
            choiceC.setTextColor(Color.BLACK);
            choiceD.setTextColor(Color.RED);
        }

        return view;
    }
    public void setListData(ArrayList<Question> questionData){
        this.questionData = questionData;
        this.notifyDataSetChanged();
    }
}