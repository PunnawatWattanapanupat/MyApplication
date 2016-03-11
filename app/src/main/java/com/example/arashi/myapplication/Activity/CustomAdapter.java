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
    ArrayList<Announcement> announceData;


    public CustomAdapter(Context context, ArrayList<Announcement> announceData ) {
        this.mContext= context;
        this.announceData = announceData;
    }

    public int getCount() {
        return announceData.size();
    }

    public Object getItem(int position) {
        return announceData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.listview_row, parent, false);

        Announcement announcement = announceData.get(position);
        String AnnounceTopic = announcement.topic;
        String AnnounceDateTime = announcement.date_post;

        //for show
        TextView numberCount = (TextView)view.findViewById(R.id.NumberCount);
        numberCount.setText(Integer.toString(announceData.size()-position));


        TextView textView = (TextView)view.findViewById(R.id.textView1);
        textView.setText(AnnounceTopic);

        TextView txt_date_time = (TextView)view.findViewById(R.id.txt_date_time);
        txt_date_time.setText(AnnounceDateTime);

        int[] resId= new int[getCount()];
        for(int number = 0; number < getCount();number++ ){
            resId[number] = R.drawable.announcement_icon;
        }

        ImageView imageView = (ImageView)view.findViewById(R.id.imageView1);
        imageView.setBackgroundResource(resId[position]);

        return view;
    }

    public void setListData(ArrayList<Announcement> announceData){
        this.announceData = announceData;
        this.notifyDataSetChanged();
    }
}
