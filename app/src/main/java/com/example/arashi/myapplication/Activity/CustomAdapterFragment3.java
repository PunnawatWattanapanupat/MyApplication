package com.example.arashi.myapplication.Activity;

/**
 * Created by Ooppo on 21/2/2559.
 */
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arashi.myapplication.Object.Questionstack;

import java.util.ArrayList;


public class CustomAdapterFragment3 extends BaseAdapter    {

    Context mContext;
    ArrayList<Questionstack> questionstackData;

    public CustomAdapterFragment3(Context context,ArrayList<Questionstack> questionstackData) {
        this.mContext= context;
        this.questionstackData = questionstackData;

    }

    public int getCount() {
        return questionstackData.size();
    }

    public Object getItem(int position) {
        return questionstackData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null) {
            view = mInflater.inflate(R.layout.listview_qa, parent, false);
        }

        Questionstack questionstack = questionstackData.get(position);
        String question = questionstack.question;
        String date = questionstack.date;
        int qid = questionstack.question_id;
        Log.i("listitem1",question);
        int countanswer = questionstack.noNumber;

        //Log.i("asd",questionstack.toString());
        TextView numberAnswer = (TextView)view.findViewById(R.id.numberAnswer);
        TextView Text_Answer = (TextView)view.findViewById(R.id.Text_Answer);
        TextView textQuestion = (TextView)view.findViewById(R.id.textQuestion);
        TextView textDate = (TextView)view.findViewById(R.id.textDate);
        TextView textqid = (TextView)view.findViewById(R.id.qid);

        textqid.setText(Integer.toString(qid));
        numberAnswer.setText(Integer.toString(countanswer));
        textDate.setText(date);
        textQuestion.setText(question);


        return view;
    }
    public void setListData(ArrayList<Questionstack> questionstackData){
        this.questionstackData = questionstackData;
        this.notifyDataSetChanged();
    }
//    public int getbackquestionid(){
//        return qid;
//    }

}
