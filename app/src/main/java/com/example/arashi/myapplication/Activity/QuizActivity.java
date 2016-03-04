package com.example.arashi.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Ooppo on 4/3/2559.
 */
public class QuizActivity extends Activity {
    TextView tv;
    Button btnNext;
    RadioGroup rg;
    RadioButton rb1,rb2,rb3,rb4;

    String questions[]={"First Android Phone?","Name of Android Version 4.4?","Android is a which kind of software?"};
    String ans[]={"Motorola Droid","KitKat","Operating System"};
    String opt[]={"HTC-G1","HTC-One","Motorola Droid","HTC-G2","JellyBean","Froyo","KitKat","Meow","Operating System","Anti-Virus","Computer","Smart Phone"};

    int flag=0;
    public static int marks,correct,wrong;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tv=(TextView)findViewById(R.id.tvque);
        btnNext=(Button)findViewById(R.id.btnnxt) ;
        rg=(RadioGroup)findViewById(R.id.radioGroup);
        rb1=(RadioButton) findViewById(R.id.radioButton);
        rb2=(RadioButton)findViewById(R.id.radioButton2);
        rb3=(RadioButton)findViewById(R.id.radioButton3);
        rb4=(RadioButton)findViewById(R.id.radioButton4);

        tv.setText(questions[flag]);
        rb1.setText(opt[0]);
        rb2.setText(opt[1]);
        rb3.setText(opt[2]);
        rb4.setText(opt[3]);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton uans =(RadioButton)findViewById(rg.getCheckedRadioButtonId());
                String ansText=uans.getText().toString();

                if (ansText.equals(ans[flag])){

                    correct++;
                }
                else {
                    wrong++;
                }
                flag++;
                if(flag<questions.length){
                    tv.setText(questions[flag]);
                    rb1.setText(opt[flag*4]);
                    rb2.setText(opt[flag*4+1]);
                    rb3.setText(opt[flag*4+2]);
                    rb4.setText(opt[flag*4+3]);
                }
                else    {
                    marks=correct;
                    Intent in = new Intent(getApplicationContext(),ResultActivity.class);
                    startActivity(in);
                }

            }
        });
    }

}
