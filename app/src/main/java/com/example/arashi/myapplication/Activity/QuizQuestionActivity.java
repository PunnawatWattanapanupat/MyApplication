package com.example.arashi.myapplication.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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

import com.example.arashi.myapplication.Object.Question;
import com.example.arashi.myapplication.Object.Quiz;
import com.example.arashi.myapplication.Object.StudentQuiz;
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
    TextView question_name_text,quiz_question_id;
    Integer count=1;
    Integer quizquestionpack_id;
    ListView listViewTest;
    // ListView listView1;
    CheckBox CheckboxRelease;
    Button finishButton;
    int count_prev=0;
    int arrayIndex=0;
    SQLiteDatabase mDb;
    ArrayList<Question> listItem;
    Cursor mCursor;
    CustomAdapterQuizQuestion adapter;
    Button add_question;
    Button Cancel_Edit_Button;
    TextView NumberCount;
    String passedVar=null;
    String correctAnswer,student_answer;
    Button Submit_Edit_Button;

    ListView listView;
    TextView textClassName;
    ServerRequestQuizQuestion serverRequestQuizQuestion;
    UserLocalStore userLocalStore;
    ClassLocalStore classLocalStore;
    //AnnounceLocalStore announceLocalStore;
    Class classroom;
    StudentQuiz studentQuizQuestion;
    LinearLayout teacherQuiz;
    LinearLayout studentQuiz;

    RadioButton choice_a_radio,choice_b_radio,choice_c_radio,choice_d_radio;

    // student
    TextView student_QuestionNumber,student_question_text;
    TextView student_choice_a_text;
    TextView student_choice_b_text;
    TextView student_choice_c_text;
    TextView student_choice_d_text;
    RadioButton student_choice_a_radio;
    RadioButton student_choice_b_radio;
    RadioButton student_choice_c_radio;
    RadioButton student_choice_d_radio;
    Button student_next_question,student_prev_question;
    Button student_Submit_Quiz_Button;

    Button seeResultButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question);
        finishButton = (Button) findViewById(R.id.finishButton);

        quiz_question_id = (TextView) findViewById(R.id.quiz_question_id );
        question_name_text = (TextView) findViewById(R.id.question_name_text);
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


        //student

        student_QuestionNumber = (TextView) findViewById(R.id.student_QuestionNumber);
        student_question_text = (TextView) findViewById(R.id.student_question_text);
        student_choice_a_text = (TextView) findViewById(R.id.student_choice_a_text);
        student_choice_b_text = (TextView) findViewById(R.id.student_choice_b_text);
        student_choice_c_text = (TextView) findViewById(R.id.student_choice_c_text);
        student_choice_d_text = (TextView) findViewById(R.id.student_choice_d_text);
        student_choice_a_radio = (RadioButton) findViewById(R.id.student_choice_a_radio);
        student_choice_b_radio = (RadioButton) findViewById(R.id.student_choice_b_radio);
        student_choice_c_radio = (RadioButton) findViewById(R.id.student_choice_c_radio);
        student_choice_d_radio = (RadioButton) findViewById(R.id.student_choice_d_radio);

        student_next_question = (Button) findViewById(R.id.student_next_question);
        student_prev_question = (Button) findViewById(R.id.student_prev_question);
        student_Submit_Quiz_Button = (Button) findViewById(R.id.student_Submit_Quiz_Button);


        seeResultButton = (Button) findViewById(R.id.seeResultButton);






        serverRequestQuizQuestion = new ServerRequestQuizQuestion(this);
        userLocalStore = new UserLocalStore(this);
        classLocalStore = new ClassLocalStore(this);
        final AlertDialog.Builder alertYesNo_Edit = new AlertDialog.Builder(this);
        final AlertDialog.Builder ShowScoreStudent = new AlertDialog.Builder(this);
        Bundle bundle = getIntent().getExtras();
        String text = bundle.getString("MyValue");
        final int quiz_id = bundle.getInt("quizID");

        question_name_text.setText(text);

        QuestionNumber.setText("" + count);



        if(userLocalStore.getLoggedInUser().is_teacher == 1){ //Teacher
            teacherQuiz.setVisibility(View.VISIBLE);
            studentQuiz.setVisibility(View.GONE);
            listViewTest = (ListView) findViewById(R.id.Question_TEST);
            listItem = new ArrayList<>();
            adapter = new CustomAdapterQuizQuestion(this,listItem);
            listViewTest.setAdapter(adapter);

            question_obj = new Question(quiz_id);
            serverRequestQuizQuestion.showQuizQuestionDataInBackground(question_obj, new GetShowQuestionCallback() {
                @Override
                public void done(ArrayList<Question> returnShowQuestion) {
                    if (returnShowQuestion.size() > 0) {
                        listItem = returnShowQuestion;
                        adapter.setListData(listItem);
                        count = returnShowQuestion.size()+1;
                        QuestionNumber.setText(""+count);
                    }
                }
            });

            Cancel_Edit_Button = (Button) findViewById(R.id.Cancel_Edit_Button);
            Submit_Edit_Button = (Button) findViewById(R.id.Submit_Edit_Button);
            add_question = (Button) findViewById(R.id.add_question);
            quiz = new Quiz("",-1,0,4);

            seeResultButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(QuizQuestionActivity.this,Popup_Result__Quiz_First_Page.class));
                }
            });

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


                            int quiz_questionpack_id = Integer.parseInt(quiz_question_id.getText().toString());
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

                                if (    choice_a_text_value.equals(choice_b_text_value) ||
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
                                    question_obj = new Question(quiz_questionpack_id, question_text_value, choice_a_text_value, choice_b_text_value, choice_c_text_value, choice_d_text_value, correctAnswer, quiz_id, Integer.parseInt(QuestionNumber.getText().toString()));
                                    serverRequestQuizQuestion.updateQuizQuestionDataInBackground(question_obj, new GetQuestionCallback() {
                                        @Override
                                        public void done(Question returnQuestion) {
                                            Toast.makeText(QuizQuestionActivity.this, "Question is Update", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    question_obj = new Question(quiz_id);
                                    serverRequestQuizQuestion.showQuizQuestionDataInBackground(question_obj, new GetShowQuestionCallback() {
                                        @Override
                                        public void done(ArrayList<Question> returnShowQuestion) {
                                            if (returnShowQuestion.size() > 0) {
                                                listItem = returnShowQuestion;
                                                adapter.setListData(listItem);
                                                count = returnShowQuestion.size();
                                                count++;
                                                QuestionNumber.setText("" + count);
                                            }
                                        }
                                    });


                                    choice_a_radio.setChecked(true);


                                    questionPosition = 0;

                                    question_text.setText("");
                                    choice_a_text.setText("");
                                    choice_b_text.setText("");
                                    choice_c_text.setText("");
                                    choice_d_text.setText("");

                                    count++;

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
                    quiz_question_id.setText(question_obj.quizquestionpack_id+"");
                    question_text.setText(question_obj.question);
                    choice_a_text.setText(question_obj.ans1);
                    choice_b_text.setText(question_obj.ans2);
                    choice_c_text.setText(question_obj.ans3);
                    choice_d_text.setText(question_obj.ans4);


//                String choice_a_radio_value =   String.valueOf(choice_a_radio.isChecked());
//                String choice_b_radio_value =   String.valueOf(choice_b_radio.isChecked());
//                String choice_c_radio_value =   String.valueOf(choice_c_radio.isChecked());
//                String choice_d_radio_value =   String.valueOf(choice_d_radio.isChecked());


//                    String question_name_text_value = question_name_text.getText().toString();
                    //                  String question_text_value = question_text.getText().toString();
//                    String choice_a_text_value = choice_a_text.getText().toString();
//                    String choice_b_text_value = choice_b_text.getText().toString();
//                    String choice_c_text_value = choice_c_text.getText().toString();
//                    String choice_d_text_value = choice_d_text.getText().toString();

                    correctAnswer = question_obj.correctaAnswer;

                    // Toast.makeText(QuizQuestionActivity.this, correctAnswer+" "+question_obj.ans1 +" ", Toast.LENGTH_LONG).show();

                    if (question_obj.correctaAnswer.equals(question_obj.ans1)){

                        choice_a_radio.setChecked(true);

                    }
                    else if (question_obj.correctaAnswer.equals(question_obj.ans2)){
                        choice_b_radio.setChecked(true);
                        Log.d("EEEEEEEEEE","BBB");
                    }
                    else if (question_obj.correctaAnswer.equals(question_obj.ans3)){
                        choice_c_radio.setChecked(true);
                        Log.d("EEEEEEEEEE","CCC");
                    }
                    else if (question_obj.correctaAnswer.equals(question_obj.ans4)){
                        choice_d_radio.setChecked(true);
                        Log.d("EEEEEEEEEE","DDD");
                    }

                    add_question.setVisibility(View.GONE);
                    Submit_Edit_Button.setVisibility(View.VISIBLE);
                    finishButton.setVisibility(View.GONE);
                    Cancel_Edit_Button.setVisibility(View.VISIBLE);
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

                            question_obj = new Question(question_text_value, choice_a_text_value, choice_b_text_value, choice_c_text_value, choice_d_text_value, correctAnswer,quiz_id,Integer.parseInt(QuestionNumber.getText().toString()));

                            serverRequestQuizQuestion.storeQuizQuestionDataInBackground(question_obj, new GetQuestionCallback() {
                                @Override
                                public void done(Question returnQuestion) {
                                    //Log.d("Checkkk", returnQuestion.quiz_id+"");
                                }
                            });
                            //show
                            question_obj = new Question(quiz_id);
                            serverRequestQuizQuestion.showQuizQuestionDataInBackground(question_obj, new GetShowQuestionCallback() {
                                @Override
                                public void done(ArrayList<Question> returnShowQuestion) {
                                    if (returnShowQuestion.size() > 0) {
                                        listItem = returnShowQuestion;
                                        adapter.setListData(listItem);
                                        count = returnShowQuestion.size();
                                        count++;
                                    }
                                }
                            });

                            //SQL Lite
                            quiz.addQuestionforQuiz(question_text_value, choice_a_text_value, choice_b_text_value, choice_c_text_value, choice_d_text_value, correctAnswer,quiz_id, count);
                            Log.d("TESTESS", "" + correctAnswer);
                            // listItem = quiz.questionArray;
                            // adapter.setListData(listItem);
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
                        quiz.is_active = 1;

                    }else {
                        strSend="No";
                        quiz.is_active = 0;
                    }
                    quiz.quizID = quiz_id;
                    quiz.class_id = classLocalStore.getJoinedInClass().class_id ;
                    serverRequestQuizQuestion.updateQuizDataInBackground(quiz, new GetQuizCallback() {
                        @Override
                        public void done(Quiz returnQuiz) {
                            // Toast.makeText(QuizQuestionActivity.this, returnQuiz.quizID+"", Toast.LENGTH_LONG).show();
                        }
                    });

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result",strSend);
                    setResult(Activity.RESULT_OK,returnIntent);

                    Log.e("dddddddddddddddddd",quiz.QuizConcat());
                    finish();
                }
            });

            //Active checkbox
            quiz = new Quiz(text,classLocalStore.getJoinedInClass().class_id);
            serverRequestQuizQuestion.fetchQuizDataInBackground(quiz, new GetQuizCallback() {
                @Override
                public void done(Quiz returnQuiz) {
                    if(returnQuiz.is_active == 1){
                        CheckboxRelease.setChecked(true);
                    }
                    else{
                        CheckboxRelease.setChecked(false);
                    }
                }
            });


        }
        else{  //Student

            teacherQuiz.setVisibility(View.GONE);
            studentQuiz.setVisibility(View.VISIBLE);
            if(count == 1){
                student_prev_question.setVisibility(View.INVISIBLE);
                student_Submit_Quiz_Button.setVisibility(View.GONE);
            }
//            else if (count < 1){
//                student_prev_question.setVisibility(View.INVISIBLE);
//            }
            student_Submit_Quiz_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowScoreStudent.setTitle("Score Result");

                    ShowScoreStudent.setMessage("Your Score is X/10");

                    ShowScoreStudent.setPositiveButton("Ok",null);
                    ShowScoreStudent.show();

                    //insert
                    studentQuizQuestion = new StudentQuiz(student_answer, quiz_id, quizquestionpack_id, userLocalStore.getLoggedInUser().user_id);
                    serverRequestQuizQuestion.storeStudentQuizDataInBackground(studentQuizQuestion,"Post_Student_Quiz.php", new GetStudentQuizCallback() {
                        @Override
                        public void done(StudentQuiz studentQuiz) {
                            Toast.makeText(QuizQuestionActivity.this, "Your answer1", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
            student_next_question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count > 0){
                        student_prev_question.setVisibility(View.VISIBLE);
                    }
                    count++;
                    student_QuestionNumber.setText(count.toString());
                    //show quiz question
                    question_obj = new Question(quiz_id, count);
                    serverRequestQuizQuestion.checkLastDataInBackground(question_obj, new GetQuestionCallback() {
                        @Override
                        public void done(Question returnQuestion) {
                            if(returnQuestion.numberQuestion == count){
                                student_next_question.setVisibility(View.GONE);
                                student_Submit_Quiz_Button.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    show_student_quiz_question(quiz_id);
                    //quizquestionpack_id = show_student_quiz_question(quiz_id);

                    String choice_a_radio_value =   String.valueOf(student_choice_a_radio.isChecked());
                    String choice_b_radio_value =   String.valueOf(student_choice_b_radio.isChecked());
                    String choice_c_radio_value =   String.valueOf(student_choice_c_radio.isChecked());
                    String choice_d_radio_value =   String.valueOf(student_choice_d_radio.isChecked());





                    if (choice_a_radio_value.equals("true")){
                        student_answer = student_choice_a_radio.getText().toString();
                    }
                    else if (choice_b_radio_value.equals("true")){
                        student_answer = student_choice_b_radio.getText().toString();
                    }
                    else if (choice_c_radio_value.equals("true")){
                        student_answer = student_choice_c_radio.getText().toString();
                    }
                    else if (choice_d_radio_value.equals("true")){
                        student_answer = student_choice_d_radio.getText().toString();
                    }
                    else{
                        Toast.makeText(QuizQuestionActivity.this, "Please choose your choice!", Toast.LENGTH_LONG).show();
                    }

                   // Toast.makeText(QuizQuestionActivity.this, student_answer + quizquestionpack_id + userLocalStore.getLoggedInUser().user_id, Toast.LENGTH_LONG).show();

                    if(count_prev == 0) {
                        //insert
                        studentQuizQuestion = new StudentQuiz(student_answer, quiz_id, quizquestionpack_id, userLocalStore.getLoggedInUser().user_id);
                        serverRequestQuizQuestion.storeStudentQuizDataInBackground(studentQuizQuestion,"Post_Student_Quiz.php", new GetStudentQuizCallback() {
                            @Override
                            public void done(StudentQuiz studentQuiz) {
                                Toast.makeText(QuizQuestionActivity.this, "Your answer1", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else
                    {
                        //update
                        studentQuizQuestion = new StudentQuiz(student_answer, quiz_id, quizquestionpack_id, userLocalStore.getLoggedInUser().user_id);
                        serverRequestQuizQuestion.storeStudentQuizDataInBackground(studentQuizQuestion,"Update_Student_Quiz.php", new GetStudentQuizCallback() {
                            @Override
                            public void done(StudentQuiz studentQuiz) {
                                Toast.makeText(QuizQuestionActivity.this, "Your answer2", Toast.LENGTH_LONG).show();
                            }
                        });
                        count_prev--;
                    }

                }
            });
            student_prev_question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count > 1){
                        if(count == 2)student_prev_question.setVisibility(View.INVISIBLE);
                        student_next_question.setVisibility(View.VISIBLE);
                        student_Submit_Quiz_Button.setVisibility(View.GONE);
                    }
//                    else {
//                        student_prev_question.setVisibility(View.INVISIBLE);
//                    }
                    count--;
                    student_QuestionNumber.setText(count.toString());
                    show_student_quiz_question(quiz_id);
                    count_prev++;
                }
            });

            show_student_quiz_question(quiz_id);
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

    public void show_student_quiz_question(int quiz_id){
        question_obj = new Question(quiz_id, count);
        serverRequestQuizQuestion.fetchQuizQuestionDataInBackground(question_obj, new GetQuestionCallback() {
            @Override
            public void done(Question returnQuestion) {
                quizquestionpack_id = returnQuestion.quizquestionpack_id;
                student_QuestionNumber.setText(count+" ");
                student_question_text.setText(returnQuestion.question);
                student_choice_a_radio.setText(returnQuestion.ans1);
                student_choice_b_radio.setText(returnQuestion.ans2);
                student_choice_c_radio.setText(returnQuestion.ans3);
                student_choice_d_radio.setText(returnQuestion.ans4);
            }
        });
    }





    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            //moveTaskToBack(false);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {
        if(userLocalStore.getLoggedInUser().is_teacher != 1) {
            AlertDialog alertbox = new AlertDialog.Builder(this)
                    .setMessage("Do you want to exit to do quiz?(Your quiz data will be delete)")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {

                            finish();
                            //close();


                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {

                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    })
                    .show();
        }
        else {
            finish();
        }

    }



}
