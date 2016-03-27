package com.example.arashi.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.arashi.myapplication.Object.Questionstack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TabFragment3 extends Fragment {
    ServerRequestsQA serverRequestsQA;
    CustomAdapterFragment3 adapter;
    ListView listview;
    ArrayList<Questionstack> listitem;
    ListView listView;
    Questionstack questionstackdata;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.tab_fragment_3, container, false);
        listitem = new ArrayList<>();

        adapter = new CustomAdapterFragment3(getActivity().getApplicationContext(), listitem);
        listView = (ListView)v.findViewById(R.id.listView1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(getActivity(),PopAnswer.class);
                String qid = ((TextView) arg1.findViewById(R.id.qid)).getText().toString();
                intent.putExtra("MyValue", qid);
//                Log.i("Myvalueanswer",str);
                startActivityForResult(intent,1);
            }
        });

        serverRequestsQA = new ServerRequestsQA(getActivity());
        serverRequestsQA.SelectQuestion(new GetSelection() {
            @Override
            public void done(ArrayList<Questionstack> returnedQuestionstack) {
                if(returnedQuestionstack.size() > 0){
                    listitem = returnedQuestionstack;
                    adapter.setListData(listitem);
                }
            }
        });

        Button AddQAbutton = (Button) v.findViewById(R.id.AddQAbutton);
        AddQAbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),PopFragment3.class));
            }
        });

//        int[] resId = { R.drawable.zeronumber
//                , R.drawable.zeronumber, R.drawable.onenumber
//                , R.drawable.onenumber, R.drawable.onenumber
//                , R.drawable.onenumber, R.drawable.onenumber
//                , R.drawable.zeronumber, R.drawable.zeronumber
//                , R.drawable.zeronumber, R.drawable.zeronumber };
//
//        String[] list = { "What do you know about DBMS?", "What is Relation?", "What is Database"
//                , "What is the mose popular DMBS?", "How to transform this table from 1st form to 5th form?", "What is the adventage of 1st form?"
//                , "What is the important of Database in the present?", "How do you describe about 3rd normal form?"
//                , "What is the benefit that you learn Database subject?" };



        return v;
    }

}