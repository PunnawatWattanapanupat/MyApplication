package com.example.arashi.myapplication.Activity;

/**
 * Created by Ooppo on 12/2/2559.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arashi.myapplication.Object.Announcement;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Announcement> topic;
    int[] resId;


    public CustomAdapter(Context context, ArrayList<Announcement> topic, int[] resId ) {
        this.mContext= context;
        this.topic = topic;
        this.resId = resId;

    }

    public int getCount() {
        return topic.size();
    }

    public Object getItem(int position) {
        return topic.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.listview_row, parent, false);

        Announcement announcement = topic.get(position);
        String AnnounceData = announcement.topic;




        TextView textView = (TextView)view.findViewById(R.id.textView1);
        textView.setText(AnnounceData);

        ImageView imageView = (ImageView)view.findViewById(R.id.imageView1);
        imageView.setBackgroundResource(resId[position]);

        return view;
    }

    public void setListData(ArrayList<Announcement> topic){
        this.topic = topic;
        notifyDataSetChanged();
    }
}
