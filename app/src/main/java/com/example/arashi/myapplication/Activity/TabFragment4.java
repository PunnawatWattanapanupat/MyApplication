/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.arashi.myapplication.Activity;

import com.example.arashi.myapplication.MySQLLiteDatabase.MyDbHelper;
import com.example.arashi.myapplication.Object.Announcement;
import com.example.arashi.myapplication.Object.Quiz;
import com.example.arashi.myapplication.Store.ClassLocalStore;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;



/**
 * Allows the user to create questions, which will be put as notifications on the watch's stream.
 * The status of questions will be updated on the phone when the user answers them.
 */
public class TabFragment4 extends Fragment{
    //    TextView QuestionNumber;
//    EditText question_text;
//    EditText choice_a_text;
//    EditText choice_b_text;
//    EditText choice_c_text;
//    EditText choice_d_text;
    //public final static String ID_Extra="com.example.arashi.myapplication.Activity.TabFragment4._ID";
    EditText question_name_text;
    // Integer count=1;
    ListView listView1;
    Button createQuizButton;
    SQLiteDatabase mDb;
    Boolean Tvalue = true;
    MyDbHelper mHelper;
    Cursor mCursor;
    Quiz quiz;
    ClassLocalStore classLocalStore;
    int questnum=1;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_fragment_4, container, false);
       // Button testbtn = (Button) v.findViewById(R.id.testbtn);
        quiz = new Quiz("",1,1,4);

        question_name_text  = (EditText) v.findViewById(R.id.question_name_text );

        //ListViewShow Question_alreadyCreate
        listView1 = (ListView)v.findViewById(R.id.Quiz_alreadyCreate);

//        testbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in = new Intent(getActivity(),QuizActivity.class);
//                startActivity(in);
//            }
//
//
//        });

//        QuestionNumber.setText(""+count);q
        createQuizButton = (Button) v.findViewById(R.id.createQuizButton);




        mHelper = new MyDbHelper(getActivity());
        mDb = mHelper.getWritableDatabase();
//        mCursor = mDb.rawQuery("SELECT " + MyDbHelper.COL_NAME + ", "  + MyDbHelper.COL_PIECE_PRICE
//                + ", " + MyDbHelper.COL_CAKE_PRICE + " FROM " + MyDbHelper.TABLE_NAME_QUIZ, null);



        mCursor = mDb.rawQuery("SELECT " + MyDbHelper.QUIZ_ID + ", "  + MyDbHelper.QUIZ_NAME
                + ", " + MyDbHelper.IS_ACTIVE + " FROM " + MyDbHelper.TABLE_NAME_QUIZ + " ORDER BY " + MyDbHelper.QUIZ_ID + " DESC;", null);

        ArrayList<String> dirArray = new ArrayList<String>();
        mCursor.moveToFirst();


//        while ( !mCursor.isAfterLast() ){
//            dirArray.add(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_NAME)) + "\n"
//                    + "Piece : " + mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_PIECE_PRICE)) + "\t\t"
//                    + "Cake : " + mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_CAKE_PRICE)));
//            mCursor.moveToNext();
//        }


        while ( !mCursor.isAfterLast() ){
            dirArray.add(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.QUIZ_ID)) + ".\t\t" +
                    mCursor.getString(mCursor.getColumnIndex(MyDbHelper.QUIZ_NAME)) + "\n"
                    + "Release to Students? : " +
                    mCursor.getString(mCursor.getColumnIndex(MyDbHelper.IS_ACTIVE)));
            mCursor.moveToNext();
        }

        ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, dirArray);
        listView1.setAdapter(adapterDir);


        createQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Toast.makeText(getActivity(), choice_a_text.getText().toString(), Toast.LENGTH_LONG).show();
                String question_name_text_value = question_name_text.getText().toString();
                quiz.quiz_name = question_name_text_value;
//                String question_text_value = question_text.getText().toString();
//                String choice_a_text_value = choice_a_text.getText().toString();
//                String choice_b_text_value = choice_b_text.getText().toString();
//                String choice_c_text_value = choice_c_text.getText().toString();
//                String choice_d_text_value = choice_d_text.getText().toString();



//                    Toast.makeText(getActivity(), choice_a_text_value+choice_b_text_value+choice_c_text_value+choice_d_text_value, Toast.LENGTH_LONG).show();
                Log.d("question_name_value",question_name_text_value);
//                Log.d("question_text_value",question_text_value);
//                Log.d("choice_a_text_value",choice_a_text_value);
//                Log.d("choice_b_text_value",choice_b_text_value);
//                Log.d("choice_c_text_value",choice_c_text_value);
//                Log.d("choice_d_text_value",choice_d_text_value);





                // String count_value = count.toString();


                /// try to insert to database


                mHelper = new MyDbHelper(getActivity());
                mDb = mHelper.getWritableDatabase();

                if (!question_name_text_value.isEmpty()) {
                    mCursor = mDb.rawQuery("INSERT INTO " + MyDbHelper.TABLE_NAME_QUIZ + " (" + MyDbHelper.QUIZ_NAME + ", " + MyDbHelper.IS_ACTIVE
                            + ") VALUES (" + "'" + question_name_text_value + "'" + "," + "'"+"No" +"'"+ ");", null);

//                mCursor = mDb.rawQuery("INSERT INTO " + MyDbHelper.TABLE_NAME_QUIZ + " (" + MyDbHelper.COL_NAME + ", " + MyDbHelper.COL_PIECE_PRICE
//                        + ", " + MyDbHelper.COL_CAKE_PRICE + ") VALUES ('Testtt', 445, 750);", null);


                    ArrayList<String> dirArray = new ArrayList<String>();
                    mCursor.moveToFirst();

//                while ( !mCursor.isAfterLast() ){
//                    dirArray.add(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_NAME)) + "\n"
//                            + "Piece : " + mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_PIECE_PRICE)) + "\t\t"
//                            + "Cake : " + mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_CAKE_PRICE)));
//                    mCursor.moveToNext();
//                }

                    while ( !mCursor.isAfterLast() ){
                        dirArray.add(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.QUIZ_ID)) + ".\t\t" +
                                mCursor.getString(mCursor.getColumnIndex(MyDbHelper.QUIZ_NAME)) + "\n"
                                + "Release to Students?: " + mCursor.getString(mCursor.getColumnIndex(MyDbHelper.IS_ACTIVE)));
                        mCursor.moveToNext();
                    }


                    ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, dirArray);
                    listView1.setAdapter(adapterDir);


                    mCursor = mDb.rawQuery("SELECT " + MyDbHelper.QUIZ_ID + ", "  + MyDbHelper.QUIZ_NAME
                            + ", " + MyDbHelper.IS_ACTIVE + " FROM " + MyDbHelper.TABLE_NAME_QUIZ + " ORDER BY " + MyDbHelper.QUIZ_ID + " DESC;", null);
//                //try to select data again
//                mCursor = mDb.rawQuery("SELECT " + MyDbHelper.COL_NAME + ", "  + MyDbHelper.COL_PIECE_PRICE
//                        + ", " + MyDbHelper.COL_CAKE_PRICE + " FROM " + MyDbHelper.TABLE_NAME_QUIZ + " ORDER BY " + MyDbHelper.cakeID+" DESC;", null);


                    mCursor.moveToFirst();

//                while ( !mCursor.isAfterLast() ){
//                    dirArray.add(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_NAME)) + "\n"
//                            + "Piece : " + mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_PIECE_PRICE)) + "\t\t"
//                            + "Cake : " + mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_CAKE_PRICE)));
//                    mCursor.moveToNext();
//                }

                    while ( !mCursor.isAfterLast() ){
                        dirArray.add(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.QUIZ_ID)) + ".\t\t" +
                                mCursor.getString(mCursor.getColumnIndex(MyDbHelper.QUIZ_NAME)) + "\n"
                                + "Release to Students? : " +  mCursor.getString(mCursor.getColumnIndex(MyDbHelper.IS_ACTIVE)));
                        mCursor.moveToNext();
                    }





                    //

                    adapterDir = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, dirArray);
                    listView1.setAdapter(adapterDir);
                    Toast.makeText(getActivity(), "This Quiz's already created.", Toast.LENGTH_LONG).show();
                } else
                {
                    Toast.makeText(getActivity(), "Please fill Quiz Name !", Toast.LENGTH_LONG).show();
                }

//                    getActivity().finish();
//                    startActivity(getActivity().getIntent());

//                    TextView QuestionNumber = (TextView) v.findViewById(R.id.QuestionNumber);
//                    QuestionNumber.setText("2");


//                    RadioButton uans =(RadioButton)findViewById(rg.getCheckedRadioButtonId());
//                    String ansText=uans.getText().toString();
//
//                    if (ansText.equals(ans[flag])){
//
//                        correct++;
//                    }
//                    else {
//                        wrong++;
//                    }
//                    flag++;
//                    if(flag<questions.length){
//                        tv.setText(questions[flag]);
//                        rb1.setText(opt[flag*4]);
//                        rb2.setText(opt[flag*4+1]);
//                        rb3.setText(opt[flag*4+2]);
//                        rb4.setText(opt[flag*4+3]);
//                    }
//                    else    {
//                        marks=correct;
//                        Intent in = new Intent(getApplicationContext(),ResultActivity.class);
//                        startActivity(in);
//                    }

//                question_text.setText("");
//                choice_a_text.setText("");
//                choice_b_text.setText("");
//                choice_c_text.setText("");
//                choice_d_text.setText("");
//                count++;
                // Log.d("count_value",""+count);

                // QuestionNumber.setText(""+count);
            }
        });


//        Button finishButton = (Button) v.findViewById(R.id.finishButton);
//        finishButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //     Toast.makeText(ClassActivity.this,Integer.toString(classItem.class_id), Toast.LENGTH_SHORT).show();
               // startActivity(new Intent(getActivity(),QuizQuestionActivity.class));
                Intent intent = new Intent(getActivity(), QuizQuestionActivity.class);
                String str=listView1.getItemAtPosition(arg2).toString();
                intent.putExtra("MyValue", str);
                Log.i("Myvalueanswer",str);
                startActivityForResult(intent,1);
                //startActivity(intent);
            }
        });




        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public void onPause() {
        super.onPause();
//        mHelper.close();
//        mDb.close();
    }
}

