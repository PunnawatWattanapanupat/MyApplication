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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arashi.myapplication.MySQLLiteDatabase.MyDbHelper;
import com.example.arashi.myapplication.Object.Question;
import com.example.arashi.myapplication.Object.Quiz;

import java.util.ArrayList;

/**
 * Created by Ooppo on 8/3/2559.
 */
public class QuizQuestionActivity extends Activity{
    TextView QuestionNumber;
    EditText question_text;
    EditText choice_a_text;
    EditText choice_b_text;
    Quiz quiz;
    Question question_obj;
    EditText choice_c_text;
    EditText choice_d_text;
    TextView question_name_text;
    Integer count=1;
    ListView listViewTest;
   // ListView listView1;
    CheckBox CheckboxRelease;
    Button finishButton;
    int arrayIndex=0;
    SQLiteDatabase mDb;
    ArrayList<Question> listItem;
    MyDbHelper mHelper;
    Cursor mCursor;
    CustomAdapterQuizQuestion adapter;

    String passedVar=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question);
        finishButton = (Button) findViewById(R.id.finishButton);
        question_name_text= (TextView) findViewById(R.id.question_name_text);
        question_text = (EditText) findViewById(R.id.question_text);
        choice_a_text = (EditText) findViewById(R.id.choice_a_text);
        choice_b_text = (EditText) findViewById(R.id.choice_b_text);
        choice_c_text = (EditText) findViewById(R.id.choice_c_text);
        choice_d_text = (EditText) findViewById(R.id.choice_d_text);
        QuestionNumber = (TextView) findViewById(R.id.QuestionNumber);
        CheckboxRelease = (CheckBox) findViewById(R.id.CheckboxRelease);


        Bundle bundle = getIntent().getExtras();
        String text = bundle.getString("MyValue");
        Toast.makeText(QuizQuestionActivity.this, text, Toast.LENGTH_LONG).show();



        //question_name_text.setText(""+text);
        if(text.contains(".\t\t")){

            text=text.substring(4,text.length()); //substring Number
            while(text.contains("\t")){
                text=text.substring(1,text.length()); //substring Number
            }
            text=text.substring(0,text.length()-26); //substring Release to Student
            Toast.makeText(QuizQuestionActivity.this,text , Toast.LENGTH_LONG).show();
        }
        question_name_text.setText(""+text);
//        if (text.contains("Release to Student?:Yes")&& text.contains("Release to Student?:No")){
//            CheckboxRelease.setChecked(true);
//        }


        //ListViewShow Question_alreadyCreate
//        listView1 = (ListView)findViewById(R.id.Question_alreadyCreate);
        listViewTest = (ListView) findViewById(R.id.Question_TEST);
        listItem = new ArrayList<>();
        adapter = new CustomAdapterQuizQuestion(this,listItem);
        listViewTest.setAdapter(adapter);



        QuestionNumber.setText(""+count);
        Button add_question = (Button) findViewById(R.id.add_question);
        quiz = new Quiz("");






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

                quiz.addQuestionforQuiz(question_text_value,choice_a_text_value,choice_b_text_value,choice_c_text_value,choice_d_text_value);
                Log.e("deeeeeeeeeee",quiz.questionArray.toString());
//                    Toast.makeText(getActivity(), choice_a_text_value+choice_b_text_value+choice_c_text_value+choice_d_text_value, Toast.LENGTH_LONG).show();
                Log.d("question_name_value",question_name_text_value);
                Log.d("question_text_value",question_text_value);
                Log.d("choice_a_text_value",choice_a_text_value);
                Log.d("choice_b_text_value",choice_b_text_value);
                Log.d("choice_c_text_value",choice_c_text_value);
                Log.d("choice_d_text_value",choice_d_text_value);

                for(int i =0;i<quiz.questionArray.size();i++){
                    listItem = quiz.questionArray;
                    adapter.setListData(listItem);
                    }


                ArrayList<Question> dirArray = new ArrayList<Question>();



                mHelper = new MyDbHelper(QuizQuestionActivity.this);
                mDb = mHelper.getWritableDatabase();


                if (!question_text_value.isEmpty()) {

                    Toast.makeText(QuizQuestionActivity.this, "This question's already saved.", Toast.LENGTH_LONG).show();
                } else
                {
                    Toast.makeText(QuizQuestionActivity.this, "Please fill Question !", Toast.LENGTH_LONG).show();
                }

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


        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strSend;
                if(CheckboxRelease.isChecked()==true){
                     strSend = "Yes";
                }else strSend="No";
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",strSend);
                setResult(Activity.RESULT_OK,returnIntent);

                Log.e("dddddddddddddddddd",quiz.QuizConcat());
                finish();
            }
        });
    }
    public void onPause() {
        super.onPause();
//        mHelper.close();
//        mDb.close();
    }
}
