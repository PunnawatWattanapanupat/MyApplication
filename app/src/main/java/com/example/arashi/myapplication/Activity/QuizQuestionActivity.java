package com.example.arashi.myapplication.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arashi.myapplication.MySQLLiteDatabase.MyDbHelper;
import com.example.arashi.myapplication.Object.Question;
import com.example.arashi.myapplication.Object.Quiz;
import com.example.arashi.myapplication.Store.ClassLocalStore;
import com.example.arashi.myapplication.Store.UserLocalStore;

import java.util.ArrayList;

/**
 * Created by Ooppo on 8/3/2559.
 */
public class QuizQuestionActivity extends Activity{
    TextView QuestionNumber;
    EditText question_text;
    int questionPosition=0;
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
    Button add_question;
   Button Cancel_Edit_Button;
    TextView NumberCount;
    String passedVar=null;
String correctAnswer;
Button Submit_Edit_Button;

    ListView listView;
    TextView textClassName;
    SeverRequests severRequests;
    UserLocalStore userLocalStore;
    ClassLocalStore classLocalStore;
    //AnnounceLocalStore announceLocalStore;
    Class classroom;

    LinearLayout teacherQuiz;
    LinearLayout studentQuiz;

    RadioButton choice_a_radio,choice_b_radio,choice_c_radio,choice_d_radio;



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
        NumberCount = (TextView) findViewById(R.id.NumberCount);
        choice_d_text = (EditText) findViewById(R.id.choice_d_text);
        QuestionNumber = (TextView) findViewById(R.id.QuestionNumber);
        CheckboxRelease = (CheckBox) findViewById(R.id.CheckboxRelease);

        choice_a_radio = (RadioButton)   findViewById(R.id.choice_a_radio);
         choice_b_radio = (RadioButton)   findViewById(R.id.choice_b_radio);
         choice_c_radio = (RadioButton)   findViewById(R.id.choice_c_radio);
         choice_d_radio = (RadioButton)   findViewById(R.id.choice_d_radio);

        teacherQuiz = (LinearLayout) findViewById(R.id.teacherQuiz) ;
        studentQuiz = (LinearLayout) findViewById(R.id.studentQuiz) ;
        userLocalStore = new UserLocalStore(this);
        final AlertDialog.Builder alertYesNo_Edit = new AlertDialog.Builder(this);
        Bundle bundle = getIntent().getExtras();
        String text = bundle.getString("MyValue");
        Toast.makeText(QuizQuestionActivity.this, text, Toast.LENGTH_LONG).show();

        if(text.contains(".\t\t")){

            text=text.substring(4,text.length()); //substring Number
            while(text.contains("\t")){
                text=text.substring(1,text.length()); //substring Number
            }
            text=text.substring(0,text.length()-26); //substring Release to Student
            Toast.makeText(QuizQuestionActivity.this,text , Toast.LENGTH_LONG).show();
        }
        question_name_text.setText(""+text);

        if(userLocalStore.getLoggedInUser().is_teacher == 1){ //Teacher
            teacherQuiz.setVisibility(View.VISIBLE);
            studentQuiz.setVisibility(View.GONE);
            listViewTest = (ListView) findViewById(R.id.Question_TEST);
            listItem = new ArrayList<>();
            adapter = new CustomAdapterQuizQuestion(this,listItem);
            listViewTest.setAdapter(adapter);

            Cancel_Edit_Button = (Button) findViewById(R.id.Cancel_Edit_Button);
            Submit_Edit_Button = (Button) findViewById(R.id.Submit_Edit_Button);
            QuestionNumber.setText(""+count);
            add_question = (Button) findViewById(R.id.add_question);
            quiz = new Quiz("",-1,0,4);


            Submit_Edit_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    alertYesNo_Edit.setTitle("Confirm?");

                    alertYesNo_Edit.setMessage("You Really want to edit ?");

                    alertYesNo_Edit.setNegativeButton("Cancel", null);

                    alertYesNo_Edit.setPositiveButton("Ok", new AlertDialog.OnClickListener() {

                        public void onClick(DialogInterface dialog, int arg1) {







                            String choice_a_radio_value =   String.valueOf(choice_a_radio.isChecked());
                            String choice_b_radio_value =   String.valueOf(choice_b_radio.isChecked());
                            String choice_c_radio_value =   String.valueOf(choice_c_radio.isChecked());
                            String choice_d_radio_value =   String.valueOf(choice_d_radio.isChecked());
                            Log.d("choice_a_radio_value",choice_a_radio_value);
                            Log.d("choice_b_radio_value",choice_b_radio_value);
                            Log.d("choice_c_radio_value",choice_c_radio_value);
                            Log.d("choice_d_radio_value",choice_d_radio_value);


                            String question_name_text_value = question_name_text.getText().toString();
                            String question_text_value = question_text.getText().toString();
                            String choice_a_text_value = choice_a_text.getText().toString();
                            String choice_b_text_value = choice_b_text.getText().toString();
                            String choice_c_text_value = choice_c_text.getText().toString();
                            String choice_d_text_value = choice_d_text.getText().toString();



                            if (choice_a_radio_value.equals("true")){
                                correctAnswer = choice_a_text_value;
                            }
                            else if (choice_b_radio_value.equals("true")){
                                correctAnswer = choice_b_text_value;
                            }
                            else if (choice_c_radio_value.equals("true")){
                                correctAnswer = choice_c_text_value;
                            }
                            else if (choice_d_radio_value.equals("true")){
                                correctAnswer = choice_d_text_value;
                            }


                            if(question_text_value.isEmpty() || choice_a_text_value.isEmpty() || choice_b_text_value.isEmpty() || choice_c_text_value.isEmpty() || choice_d_text_value.isEmpty()){
                                Toast.makeText(QuizQuestionActivity.this, "Please complete question and choice.", Toast.LENGTH_LONG).show();
                            }
                            else {

                                if (        choice_a_text_value.equals(choice_b_text_value) ||
                                        choice_a_text_value.equals(choice_c_text_value) ||
                                        choice_a_text_value.equals(choice_d_text_value)||
                                        choice_b_text_value.equals(choice_c_text_value) ||
                                        choice_b_text_value.equals(choice_d_text_value) ||
                                        choice_c_text_value.equals(choice_d_text_value)) {
                                    Toast.makeText(QuizQuestionActivity.this, "Some choices is same.", Toast.LENGTH_LONG).show();
                                } else {

                                    add_question.setVisibility(View.VISIBLE);
                                    Submit_Edit_Button.setVisibility(View.GONE);
                                    finishButton.setVisibility(View.VISIBLE);
                                    Cancel_Edit_Button.setVisibility(View.GONE);
                                    question_obj = new Question(question_text_value, choice_a_text_value, choice_b_text_value, choice_c_text_value, choice_d_text_value, correctAnswer);

                                    listItem.set(questionPosition, question_obj);
                                    listItem = quiz.questionArray;
                                    adapter.setListData(listItem);



                                    choice_a_radio.setChecked(true);


                                    questionPosition = 0;

                                    question_text.setText("");
                                    choice_a_text.setText("");
                                    choice_b_text.setText("");
                                    choice_c_text.setText("");
                                    choice_d_text.setText("");

                                    QuestionNumber.setText("" + count);

                                }
                            }
                        }

                    });

                    alertYesNo_Edit.show();

                }
            });

            Cancel_Edit_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    add_question.setVisibility(View.VISIBLE);
                    Submit_Edit_Button.setVisibility(View.GONE);
                    finishButton.setVisibility(View.VISIBLE);
                    Cancel_Edit_Button.setVisibility(View.GONE);


                    question_text.setText("");
                    choice_a_text.setText("");
                    choice_b_text.setText("");
                    choice_c_text.setText("");
                    choice_d_text.setText("");

                    QuestionNumber.setText(""+count);
                    choice_a_radio.setChecked(true);


                }
            });

            listViewTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                    questionPosition = arg2;
                    question_obj = (Question) arg0.getItemAtPosition(arg2);
                    question_text.setText(question_obj.question);
                    choice_a_text.setText(question_obj.ans1);
                    choice_b_text.setText(question_obj.ans2);
                    choice_c_text.setText(question_obj.ans3);
                    choice_d_text.setText(question_obj.ans4);

//                String choice_a_radio_value =   String.valueOf(choice_a_radio.isChecked());
//                String choice_b_radio_value =   String.valueOf(choice_b_radio.isChecked());
//                String choice_c_radio_value =   String.valueOf(choice_c_radio.isChecked());
//                String choice_d_radio_value =   String.valueOf(choice_d_radio.isChecked());


                    String question_name_text_value = question_name_text.getText().toString();
                    String question_text_value = question_text.getText().toString();
                    String choice_a_text_value = choice_a_text.getText().toString();
                    String choice_b_text_value = choice_b_text.getText().toString();
                    String choice_c_text_value = choice_c_text.getText().toString();
                    String choice_d_text_value = choice_d_text.getText().toString();

                    correctAnswer = question_obj.correctaAnswer;





                    add_question.setVisibility(View.GONE);
                    Submit_Edit_Button.setVisibility(View.VISIBLE);
                    finishButton.setVisibility(View.GONE);
                    Cancel_Edit_Button.setVisibility(View.VISIBLE);



                    if (correctAnswer.equals(choice_a_text_value)){

                        choice_a_radio.setChecked(true);

                    }
                    else if (correctAnswer.equals(choice_b_text_value)){
                        choice_b_radio.setChecked(true);
                        Log.d("EEEEEEEEEE","BBB");
                    }
                    else if (correctAnswer.equals(choice_c_text_value)){
                        choice_c_radio.setChecked(true);
                        Log.d("EEEEEEEEEE","CCC");
                    }
                    else if (correctAnswer.equals(choice_d_text_value)){
                        choice_d_radio.setChecked(true);
                        Log.d("EEEEEEEEEE","DDD");
                    }


                    QuestionNumber.setText(""+ (arg2+1));




                }
            });



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

                    String choice_a_radio_value =   String.valueOf(choice_a_radio.isChecked());
                    String choice_b_radio_value =   String.valueOf(choice_b_radio.isChecked());
                    String choice_c_radio_value =   String.valueOf(choice_c_radio.isChecked());
                    String choice_d_radio_value =   String.valueOf(choice_d_radio.isChecked());

                    Log.e("deeeeeeeeeee",quiz.questionArray.toString());
//                    Toast.makeText(getActivity(), choice_a_text_value+choice_b_text_value+choice_c_text_value+choice_d_text_value, Toast.LENGTH_LONG).show();
                    Log.d("question_name_value",question_name_text_value);
                    Log.d("question_text_value",question_text_value);
                    Log.d("choice_a_text_value",choice_a_text_value);
                    Log.d("choice_b_text_value",choice_b_text_value);
                    Log.d("choice_c_text_value",choice_c_text_value);
                    Log.d("choice_d_text_value",choice_d_text_value);


                    if(question_text_value.isEmpty() || choice_a_text_value.isEmpty() || choice_b_text_value.isEmpty() || choice_c_text_value.isEmpty() || choice_d_text_value.isEmpty()){
                        Toast.makeText(QuizQuestionActivity.this, "Please complete question and choice.", Toast.LENGTH_LONG).show();
                    }else {

                        if(             choice_a_text_value.equals(choice_b_text_value) ||
                                choice_a_text_value.equals(choice_c_text_value) ||
                                choice_a_text_value.equals(choice_d_text_value)||
                                choice_b_text_value.equals(choice_c_text_value) ||
                                choice_b_text_value.equals(choice_d_text_value) ||
                                choice_c_text_value.equals(choice_d_text_value))
                        {
                            Toast.makeText(QuizQuestionActivity.this, "Some choices is same.", Toast.LENGTH_LONG).show();
                        }
                        else {
                            correctAnswer="";

                            if (choice_a_radio_value.equals("true")){
                                correctAnswer = choice_a_text_value;
                            }
                            else if (choice_b_radio_value.equals("true")){
                                correctAnswer = choice_b_text_value;
                            }
                            else if (choice_c_radio_value.equals("true")){
                                correctAnswer = choice_c_text_value;
                            }
                            else if (choice_d_radio_value.equals("true")){
                                correctAnswer = choice_d_text_value;
                            }

                            quiz.addQuestionforQuiz(question_text_value, choice_a_text_value, choice_b_text_value, choice_c_text_value, choice_d_text_value, correctAnswer);
                            Log.d("TESTESS", "" + correctAnswer);
                            listItem = quiz.questionArray;
                            adapter.setListData(listItem);
                            question_text.setText("");
                            choice_a_text.setText("");
                            choice_b_text.setText("");
                            choice_c_text.setText("");
                            choice_d_text.setText("");

                            count++;
                            Log.d("count_value", "" + count);

                            QuestionNumber.setText("" + count);



                            choice_a_radio.setChecked(true);


                        }

                    }

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
        else{  //Student

        }
        //question_name_text.setText(""+text);

//        if (text.contains("Release to Student?:Yes")&& text.contains("Release to Student?:No")){
//            CheckboxRelease.setChecked(true);
//        }


        //ListViewShow Question_alreadyCreate
//        listView1 = (ListView)findViewById(R.id.Question_alreadyCreate);

    }
    public void onPause() {
        super.onPause();
//        mHelper.close();
//        mDb.close();
    }
}
