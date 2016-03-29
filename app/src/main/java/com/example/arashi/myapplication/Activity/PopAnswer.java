package com.example.arashi.myapplication.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arashi.myapplication.Object.Answerstack;
import com.example.arashi.myapplication.Object.Class;
import com.example.arashi.myapplication.Object.Questionstack;
import com.example.arashi.myapplication.Store.ClassLocalStore;
import com.example.arashi.myapplication.Store.UserLocalStore;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ooppo on 21/2/2559.
 */


public class PopAnswer extends Activity implements View.OnClickListener {
//    EditText edtTopic1;
    String answer,date,useranswer;
    int question_id;
//    final String testPREFTOPIC1 = "SamplePreferences";
//    final String testTOPIC1 = "UserName";
//    SharedPreferences sp1;
//    SharedPreferences.Editor editor1;
    EditText Answertext;
    UserLocalStore userLocalStore;
    String qid;
    CustomAdapterAnswer adapter;
    ArrayList<Answerstack> listitem;
    ListView listView;
    Button Done ;
    Class classroom;
    ClassLocalStore classLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_answer);
        DisplayMetrics dm =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int width = dm.widthPixels;
        int heighht = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(heighht*.8));

        Done = (Button) findViewById(R.id.DoneAnswer);
        Button Back = (Button) findViewById(R.id.Back);
        Done.setOnClickListener(this);
        Back.setOnClickListener(this);

        Answertext = (EditText) findViewById(R.id.Answertext);

        Bundle bundle = getIntent().getExtras();
        qid = bundle.getString("MyValue");
        int question_id = Integer.parseInt(qid);
        Toast.makeText(PopAnswer.this, qid, Toast.LENGTH_LONG).show();
        userLocalStore = new UserLocalStore(this);
        listitem = new ArrayList<>();

        adapter = new CustomAdapterAnswer(getApplicationContext(), listitem);
        listView = (ListView)findViewById(R.id.listViewAnswerData);
        listView.setAdapter(adapter);

        classLocalStore = new ClassLocalStore(this);
        classroom = classLocalStore.getJoinedInClass();
        ServerRequestsQA serverRequestsQA = new ServerRequestsQA(this);
        serverRequestsQA.SelectAnswer(question_id, classroom, new GetSelectionAnswer() {
            @Override
            public void done(ArrayList<Answerstack> returnAnswerstack) {
                if(returnAnswerstack.size()>0){
                    listitem = returnAnswerstack;
                    adapter.setListData(listitem);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Back:
                finish();
                break;
            case R.id.DoneAnswer:
                question_id = Integer.parseInt(qid);
                answer = Answertext.getText().toString();
                useranswer = userLocalStore.getLoggedInUser().username;
                date = DateFormat.getDateTimeInstance().format(new Date());
                Log.i("answer",question_id+answer+date+useranswer);
                ServerRequestsQA serverRequestsQA = new ServerRequestsQA(this);
                serverRequestsQA.AddAnswer(question_id, answer, date, useranswer, new GetAddAnswer() {
                    @Override
                    public void done(String boobee) {
                        if (boobee == null) {
                            showErrorMessage("Fuck");
                        } else {
                            finish();
                        }
                    }
                });
                break;
        }

    }
    private void showErrorMessage(String Errmes){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage(Errmes);
        dialogBuilder.setPositiveButton("OK",null);
        dialogBuilder.show();
    }
}
