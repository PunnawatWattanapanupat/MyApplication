package com.example.arashi.myapplication.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.arashi.myapplication.Object.Announcement;
import com.example.arashi.myapplication.Object.User;
import com.example.arashi.myapplication.Store.AnnounceLocalStore;
import com.example.arashi.myapplication.UserLocalStore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TabFragment2 extends Fragment {


    private static final int RESULT_LOAD_IMAGE=1;
    ImageView imageToUpload,createAnnounce;
    Button bUploadImage;
    EditText uploadImageName;
    private View testViewGroup;
  //  EditText mEdit;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private EditText fromDateEtxt;
    private SimpleDateFormat dateFormatter;

    ListView listView;
    ArrayList<Announcement> listItem = new ArrayList<>();
    SeverRequests severRequests;
    UserLocalStore userLocalStore;
    AnnounceLocalStore announceLocalStore;
    CustomAdapter adapter;
    User user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_fragment_2, container, false);
        severRequests = new SeverRequests(getActivity());
        userLocalStore = new UserLocalStore(getActivity());
        announceLocalStore = new AnnounceLocalStore(getActivity());
        listView = (ListView) v.findViewById(R.id.listView1);


        ImageView createAnnounce = (ImageView) v.findViewById(R.id.createAnnounce);

        //Add announcement button
        createAnnounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Pop.class));
            }
        });


        int[] resId = { R.drawable.announcement_icon, R.drawable.announcement_icon
                , R.drawable.announcement_icon, R.drawable.announcement_icon
                , R.drawable.announcement_icon, R.drawable.announcement_icon
                , R.drawable.announcement_icon, R.drawable.announcement_icon
                , R.drawable.announcement_icon, R.drawable.announcement_icon
                , R.drawable.announcement_icon, R.drawable.announcement_icon
                , R.drawable.announcement_icon, R.drawable.announcement_icon
                , R.drawable.announcement_icon, R.drawable.announcement_icon};
        SeverRequests fetch = new SeverRequests(getActivity());
        String temp = fetch.GetTopic();
        temp = temp.substring(0, temp.length()-1);




        adapter = new CustomAdapter(getActivity(),listItem ,resId);
        listView.setAdapter(adapter);

        user = userLocalStore.getLoggedInUser();

        severRequests.showAnnounceListInBackground(user, new GetShowAnnounceCallback() {
            @Override
            public void done(ArrayList<Announcement> returnedShowAnnounce) {
                if(returnedShowAnnounce.size() > 0){
                    listItem = returnedShowAnnounce;
                    adapter.setListData(listItem);
                }
                else {
                    Toast.makeText(getActivity(),Integer.toString(user.user_id)+" No Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Announcement announcement = (Announcement) arg0.getItemAtPosition(arg2);
                //     Toast.makeText(ClassActivity.this,Integer.toString(classItem.class_id), Toast.LENGTH_SHORT).show();
                announceLocalStore.storeAnnounceData(announcement);
                announceLocalStore.setAnnounceForShow(true);
                //startActivity(new Intent(getActivity(),PopAnswer.class));
            }
        });
//        Toast toast = Toast.makeText(getActivity(),""+list.length , Toast.LENGTH_SHORT);
//        toast.show();

        return v;
    }



    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }






}