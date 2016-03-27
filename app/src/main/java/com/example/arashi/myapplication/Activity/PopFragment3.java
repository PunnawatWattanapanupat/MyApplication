package com.example.arashi.myapplication.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.arashi.myapplication.Object.User;
import com.example.arashi.myapplication.Store.UserLocalStore;


import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Ooppo on 21/2/2559.
 */


public class PopFragment3 extends Activity implements View.OnClickListener /*implements View.OnClickListener */{
    EditText Questiontext;
    String question,date;
    int user_id;
    final String testPREFTOPIC1 = "SamplePreferences";
    final String testTOPIC1 = "UserName";
    SharedPreferences sp1;
    SharedPreferences.Editor editor1;
    Button Done,Back;
    UserLocalStore userLocalStore;
    User user;
    ServerRequestsQA serverRequestsQA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_qa);
        DisplayMetrics dm =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int width = dm.widthPixels;
        int heighht = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(heighht*.8));
        Questiontext = (EditText) findViewById(R.id.Questiontext);

        Done = (Button)findViewById(R.id.Done);
        Back = (Button)findViewById(R.id.Back);

        Done.setOnClickListener(this);
        Back.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this);
        user = userLocalStore.getLoggedInUser();


//        sp1 = getSharedPreferences(testPREFTOPIC1, Context.MODE_PRIVATE);
//        editor1 = sp1.edit();
//
//        edtTopic1 = (EditText)findViewById(R.id.Questiontext);
//        edtTopic1.setText(sp1.getString(testTOPIC1, ""));
//
//        edtTopic1.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) { }
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
//
//            public void afterTextChanged(Editable s) {
//                editor1.putString(testTOPIC1, s.toString());
//                editor1.commit();
//            }
//        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Done:
                question = Questiontext.getText().toString();
                user_id = user.user_id;
                date = DateFormat.getDateTimeInstance().format(new Date());
                addquestion(question,user_id,date);
                break;
            case R.id.Back:
                onBackPressed();
                break;
        }
    }

    public void addquestion(String q,int uid,String dmy){
        ServerRequestsQA serverRequestsQA = new ServerRequestsQA(this);
        serverRequestsQA.AddQuestion(q, uid, dmy, new GetAddQuestion() {
            @Override
            public void done(String booboo) {
                if(booboo == null){
                    showErrorMessage("Fuck");
                }
                else{
                    finish();
                }
            }
        });
    }
    private void showErrorMessage(String Errmes){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage(Errmes);
        dialogBuilder.setPositiveButton("OK",null);
        dialogBuilder.show();
    }
}
