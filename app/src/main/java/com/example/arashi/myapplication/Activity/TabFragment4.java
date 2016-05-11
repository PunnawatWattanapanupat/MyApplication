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

import com.example.arashi.myapplication.Object.Announcement;
import com.example.arashi.myapplication.Object.Quiz;
import com.example.arashi.myapplication.Store.ClassLocalStore;
import com.example.arashi.myapplication.Store.UserLocalStore;

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
    TextView question_name, score_text, score_num;
    ListView listView1;
    Button createQuizButton;
    SQLiteDatabase mDb;
    Boolean Tvalue = true;
    Cursor mCursor;
    Quiz quiz;
    ClassLocalStore classLocalStore;
    UserLocalStore userLocalStore;
    ServerRequestQuizQuestion serverRequestQuizQuestion;
    ArrayList<Quiz> listItem;
    CustomAdapterQuiz adapter;
    String php_file;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_fragment_4, container, false);
       // Button testbtn = (Button) v.findViewById(R.id.testbtn);

        question_name_text  = (EditText) v.findViewById(R.id.question_name_text );
        question_name = (TextView) v.findViewById(R.id.question_name);
        score_text = (TextView) v.findViewById(R.id.score_text);
        score_num = (TextView) v.findViewById(R.id.score_num);
        //ListViewShow Question_alreadyCreate
        listView1 = (ListView)v.findViewById(R.id.Quiz_alreadyCreate);
        serverRequestQuizQuestion = new ServerRequestQuizQuestion(getActivity());
        classLocalStore = new ClassLocalStore(getActivity());
        userLocalStore = new UserLocalStore(getActivity());
        listItem = new ArrayList<>();
        adapter = new CustomAdapterQuiz(getActivity(),listItem);
        listView1.setAdapter(adapter);

        createQuizButton = (Button) v.findViewById(R.id.createQuizButton);

        if(userLocalStore.getLoggedInUser().is_teacher == 0){
            createQuizButton.setVisibility(View.GONE);
            question_name_text.setVisibility(View.GONE);
            question_name.setVisibility(View.GONE);
            php_file = "ShowQuizStudent.php";
        }else{
            php_file = "ShowQuiz.php";
        }



        quiz = new Quiz(question_name_text.getText().toString(),classLocalStore.getJoinedInClass().class_id);

        createQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question_name_text_value = question_name_text.getText().toString();
                if(!question_name_text_value.isEmpty()) {
                    quiz.quiz_name = question_name_text_value;
                    serverRequestQuizQuestion.storeQuizDataInBackground(quiz, "Post_Quiz.php", new GetQuizCallback() {
                        @Override
                         public void done(Quiz returnQuiz) {
                            serverRequestQuizQuestion.showQuizDataInBackground(quiz, php_file, new GetShowQuizCallback() {
                                @Override
                                public void done(ArrayList<Quiz> returnedShowQuiz) {
                                    listItem = returnedShowQuiz;
                                    adapter.setListData(listItem);
                                }
                            });
                        }
                     });
                }

            }
        });


        serverRequestQuizQuestion.showQuizDataInBackground(quiz, php_file, new GetShowQuizCallback() {
            @Override
            public void done(ArrayList<Quiz> returnedShowQuiz) {
                listItem = returnedShowQuiz;
                adapter.setListData(listItem);
            }
        });

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //     Toast.makeText(ClassActivity.this,Integer.toString(classItem.class_id), Toast.LENGTH_SHORT).show();
               // startActivity(new Intent(getActivity(),QuizQuestionActivity.class));
                Quiz text =(Quiz) arg0.getItemAtPosition(arg2);
                //substring
//                if(text.contains(".\t\t")){
//
//                    text=text.substring(4,text.length()); //substring Number
//                    while(text.contains("\t")){
//                        text=text.substring(1,text.length()); //substring Number
//                    }
//                    text=text.substring(0,text.length()-26); //substring Release to Student
//                }

                quiz = new Quiz(text.quiz_name, text.class_id);
                Toast.makeText(getActivity(),quiz.quiz_name+" "+quiz.class_id, Toast.LENGTH_SHORT).show();
                serverRequestQuizQuestion.fetchQuizDataInBackground(quiz, new GetQuizCallback() {
                    @Override
                    public void done(Quiz returnQuiz) {
                        Intent intent = new Intent(getActivity(), QuizQuestionActivity.class);
                      //  Toast.makeText(getActivity(),Integer.toString(returnQuiz.quizID), Toast.LENGTH_SHORT).show();
                        intent.putExtra("MyValue", returnQuiz.quiz_name);
                        intent.putExtra("quizID", returnQuiz.quizID);
                        startActivityForResult(intent,1);
                    }
                });


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

