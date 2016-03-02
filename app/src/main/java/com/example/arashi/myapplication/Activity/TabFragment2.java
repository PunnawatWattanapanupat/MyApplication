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

import java.text.SimpleDateFormat;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_fragment_2, container, false);




        ImageView createAnnounce = (ImageView) v.findViewById(R.id.createAnnounce);
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
        //String[] list = temp.split(",");
        String[] list = { "Cancel Class Database1234 See you next week.", "Assignment3 : multithred-OS Report", "Mcfree Laravella,please contact professer"
                , "Don't forgot to check your email to see your score", "Welcome to Database class"
                , "Tifa Lockhart", "Cancel Class Valentines's day", "27/2/2016 AI subject cancel class"
                , "Tomorrow morning,We start to study at 9.15 A.M.","Please prepare your equipment in the class.","On this Friday, Teacher don't come to the class."
                ,"Tomorrow TA will teach you instead of teacher"};

        //String[] cNumber = {"1","2","3","4","5"};

        CustomAdapter adapter = new CustomAdapter(getActivity().getApplicationContext(), list, resId);

        ListView listView = (ListView)v.findViewById(R.id.listView1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //startActivity(new Intent(getActivity(),PopAnswer.class));
            }
        });
//        Toast toast = Toast.makeText(getActivity(),""+list.length , Toast.LENGTH_SHORT);
//        toast.show();

        return v;
    }


//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && data != null){
//            Uri selectedImage = data.getData();
//
//            imageToUpload.setImageURI(selectedImage);
//        }
//    }
    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

//        toDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Calendar newDate = Calendar.getInstance();
//                newDate.set(year, monthOfYear, dayOfMonth);
//                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
//            }
//
//        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }






}