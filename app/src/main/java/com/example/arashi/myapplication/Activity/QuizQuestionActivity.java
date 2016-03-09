package com.example.arashi.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arashi.myapplication.MySQLLiteDatabase.MyDbHelper;

import java.util.ArrayList;

/**
 * Created by Ooppo on 8/3/2559.
 */
public class QuizQuestionActivity extends Activity{
    TextView QuestionNumber;
    EditText question_text;
    EditText choice_a_text;
    EditText choice_b_text;
    EditText choice_c_text;
    EditText choice_d_text;
    EditText question_name_text;
    Integer count=1;
    ListView listView1;

    SQLiteDatabase mDb;

    MyDbHelper mHelper;
    Cursor mCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question);

        question_text = (EditText) findViewById(R.id.question_text);
        choice_a_text = (EditText) findViewById(R.id.choice_a_text);
        choice_b_text = (EditText) findViewById(R.id.choice_b_text);
        choice_c_text = (EditText) findViewById(R.id.choice_c_text);
        choice_d_text = (EditText) findViewById(R.id.choice_d_text);
        QuestionNumber = (TextView) findViewById(R.id.QuestionNumber);


        //ListViewShow Question_alreadyCreate
        listView1 = (ListView)findViewById(R.id.Question_alreadyCreate);



        QuestionNumber.setText(""+count);
        Button add_question = (Button) findViewById(R.id.add_question);

        mHelper = new MyDbHelper(QuizQuestionActivity.this);
        mDb = mHelper.getWritableDatabase();
//        mCursor = mDb.rawQuery("SELECT " + MyDbHelper.COL_NAME + ", "  + MyDbHelper.COL_PIECE_PRICE
//                + ", " + MyDbHelper.COL_CAKE_PRICE + " FROM " + MyDbHelper.TABLE_NAME_QUIZ, null);

        // question_text.setText(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.QUIZ_NAME)));

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
                    + "Bool : " + mCursor.getString(mCursor.getColumnIndex(MyDbHelper.IS_ACTIVE)));
            mCursor.moveToNext();
        }

        ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(QuizQuestionActivity.this, android.R.layout.simple_list_item_1, dirArray);
        listView1.setAdapter(adapterDir);


        add_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Toast.makeText(getActivity(), choice_a_text.getText().toString(), Toast.LENGTH_LONG).show();

                String question_name_text_value = question_name_text.getText().toString();
                String question_text_value = question_text.getText().toString();
                String choice_a_text_value = choice_a_text.getText().toString();
                String choice_b_text_value = choice_b_text.getText().toString();
                String choice_c_text_value = choice_c_text.getText().toString();
                String choice_d_text_value = choice_d_text.getText().toString();



//                    Toast.makeText(getActivity(), choice_a_text_value+choice_b_text_value+choice_c_text_value+choice_d_text_value, Toast.LENGTH_LONG).show();
                Log.d("question_name_value",question_name_text_value);
                Log.d("question_text_value",question_text_value);
                Log.d("choice_a_text_value",choice_a_text_value);
                Log.d("choice_b_text_value",choice_b_text_value);
                Log.d("choice_c_text_value",choice_c_text_value);
                Log.d("choice_d_text_value",choice_d_text_value);





                // String count_value = count.toString();


                /// try to insert to database


                mHelper = new MyDbHelper(QuizQuestionActivity.this);
                mDb = mHelper.getWritableDatabase();

                if (!question_name_text_value.isEmpty()) {
                    mCursor = mDb.rawQuery("INSERT INTO " + MyDbHelper.TABLE_NAME_QUIZ + " (" + MyDbHelper.QUIZ_NAME + ", " + MyDbHelper.IS_ACTIVE
                            + ") VALUES (" + "'" + question_name_text_value + "'" + "," + 0 + ");", null);

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
                                + "Bool : " + mCursor.getString(mCursor.getColumnIndex(MyDbHelper.IS_ACTIVE)));
                        mCursor.moveToNext();
                    }


                    ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(QuizQuestionActivity.this, android.R.layout.simple_list_item_1, dirArray);
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
                                + "Bool : " + mCursor.getString(mCursor.getColumnIndex(MyDbHelper.IS_ACTIVE)));
                        mCursor.moveToNext();
                    }

                    adapterDir = new ArrayAdapter<String>(QuizQuestionActivity.this, android.R.layout.simple_list_item_1, dirArray);
                    listView1.setAdapter(adapterDir);
                    Toast.makeText(QuizQuestionActivity.this, "This question's already saved.", Toast.LENGTH_LONG).show();
                } else
                {
                    Toast.makeText(QuizQuestionActivity.this, "Please fill Question !", Toast.LENGTH_LONG).show();
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

                question_text.setText("");
                choice_a_text.setText("");
                choice_b_text.setText("");
                choice_c_text.setText("");
                choice_d_text.setText("");
                count++;
                Log.d("count_value",""+count);

                QuestionNumber.setText(""+count);
            }
        });


        Button finishButton = (Button) findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void onPause() {
        super.onPause();
//        mHelper.close();
//        mDb.close();
    }
}
