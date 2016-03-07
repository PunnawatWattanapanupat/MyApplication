package com.example.arashi.myapplication.Activity;

/**
 * Created by Arashi on 2/21/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.arashi.myapplication.Object.Class;

import java.util.ArrayList;

public class ClassAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Class> strData = new ArrayList<>();
//    int[] resId;

    public ClassAdapter(Context context, ArrayList<Class> strData) {
        this.mContext= context;
        this.strData = strData;
//        this.resId = resId;
    }

    public int getCount() {
        return strData.size();
    }

    public Object getItem(int position) {
        return strData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.class_list_view, parent, false);

        TextView textView = (TextView)view.findViewById(R.id.textView1);

        Class showClass = strData.get(position);

        String ClassData = showClass.classname;
        textView.setText(ClassData);


//        ImageView imageView = (ImageView)view.findViewById(R.id.imageView1);
//        imageView.setBackgroundResource(resId[position]);

        return view;
    }

    public void setListData(ArrayList<Class> strData){
        this.strData = strData;
        this.notifyDataSetChanged();
    }
}