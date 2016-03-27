package com.example.arashi.myapplication.Activity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;


import com.example.arashi.myapplication.Object.Answerstack;

import java.util.ArrayList;

/**
 * Created by Ooppo on 26/3/2559.
 */
public class CustomAdapterAnswer extends BaseAdapter {

    Context mContext;
    ArrayList<Answerstack> answerstackData;

    public CustomAdapterAnswer(Context context, ArrayList<Answerstack> answerstackData) {
        this.mContext = context;
        this.answerstackData = answerstackData;

    }

    public int getCount() {
        return answerstackData.size();
    }

    public Object getItem(int position) {
        return answerstackData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = mInflater.inflate(R.layout.listview_answer, parent, false);
        }

        Answerstack answerstack = answerstackData.get(position);
        int answer_id = answerstack.answer_id;
        String answer = answerstack.answer;
        String dateanswer = answerstack.dateanswer;
     //   Log.i("listitem1", answer);
        String useranswer = answerstack.useranswer;

        //Log.i("asd",questionstack.toString());
        TextView textAnswerData = (TextView) view.findViewById(R.id.textAnswerData);
        TextView textDate = (TextView) view.findViewById(R.id.textDate);
        TextView username_thatAnswer = (TextView) view.findViewById(R.id.username_thatAnswer);



        textAnswerData.setText(answer);
        textDate.setText(dateanswer);
        username_thatAnswer.setText(useranswer);


        return view;
    }

    public void setListData(ArrayList<Answerstack> answerstackData) {
        this.answerstackData = answerstackData;
        this.notifyDataSetChanged();
    }
}
