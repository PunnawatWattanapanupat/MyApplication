package com.example.arashi.myapplication.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.arashi.myapplication.Object.Roster;


import java.util.ArrayList;

/**
 * Created by Arashi on 4/11/2016.
 */
public class CheckStudentAdapter extends BaseAdapter{
    Context mContext;
    ArrayList<Roster> studentData;


    public CheckStudentAdapter(Context context, ArrayList<Roster> studentData ) {
        this.mContext= context;
        this.studentData = studentData;
    }

    public int getCount() {
        return studentData.size();
    }

    public Object getItem(int position) {
        return studentData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.listview_show_check_student, parent, false);

        Roster student = studentData.get(position);
        String studentName = student.name;
        String studentDateTime = student.date;

        //for show
        TextView numberCount = (TextView)view.findViewById(R.id.number);
        numberCount.setText(Integer.toString(studentData.size()-position));


        TextView textView = (TextView)view.findViewById(R.id.show_student);
        textView.setText(studentName);

        TextView txt_date_time = (TextView)view.findViewById(R.id.txt_date_time);
        txt_date_time.setText(studentDateTime);


        return view;
    }

    public void setListData(ArrayList<Roster> studentData){
        this.studentData = studentData;
        this.notifyDataSetChanged();
    }
}
