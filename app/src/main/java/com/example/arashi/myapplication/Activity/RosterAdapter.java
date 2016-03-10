package com.example.arashi.myapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arashi.myapplication.Object.User;
import com.example.arashi.myapplication.Store.UserLocalStore;

import java.util.ArrayList;

/**
 * Created by Arashi on 3/8/2016.
 */
public class RosterAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<User> rosterData;
    UserLocalStore userLocalStore;


    public RosterAdapter(Context context, ArrayList<User> rosterData ) {
        this.mContext= context;
        this.rosterData = rosterData;
    }

    public int getCount() {
        return rosterData.size();
    }

    public Object getItem(int position) {
        return rosterData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.gridview_roster, parent, false);

        final User roster = rosterData.get(position);
        String RosterUsername = roster.name;
       // String AnnounceDateTime = announcement.date_post;

        //for show
        //CountGrid
//        TextView numberCount = (TextView)view.findViewById(R.id.NumberCount);
//        numberCount.setText(Integer.toString(position+1));


        TextView textView = (TextView)view.findViewById(R.id.student_name);
        textView.setText(RosterUsername);

        ImageButton imageButton = (ImageButton)view.findViewById(R.id.imagePhotoStudent);

        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userLocalStore = new UserLocalStore(mContext);
                userLocalStore.storeUserData(roster);
                userLocalStore.setUserLoggedIn(true);
                mContext.startActivity(new Intent(mContext,PopRosterStudent.class));
            }
        });
//        TextView txt_date_time = (TextView)view.findViewById(R.id.txt_date_time);
//        txt_date_time.setText(AnnounceDateTime);

//        int[] resId= new int[getCount()];
//        for(int number = 0; number < getCount();number++ ){
//            resId[number] = R.drawable.personicon;
//        }

//        ImageView imageView = (ImageView)view.findViewById(R.id.imagePhotoStudent);
//        imageView.setBackgroundResource(resId[position]);

        return view;
    }

    public void setListData(ArrayList<User> rosterData){
        this.rosterData = rosterData;
        this.notifyDataSetChanged();
    }
}
