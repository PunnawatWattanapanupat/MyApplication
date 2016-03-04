package com.example.arashi.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Ooppo on 4/3/2559.
 */
public class ResultActivity extends Activity {
    TextView tv;
    Button brnRestart;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        tv=(TextView)findViewById(R.id.tvres);
        brnRestart=(Button)findViewById(R.id.btnRestart) ;

        StringBuffer sb = new StringBuffer();
        sb.append("Correct Ans:"+QuizActivity.correct);
        sb.append("\nWrong Ans"+QuizActivity.wrong);
        sb.append("\nFinal Score:"+QuizActivity.marks);
        tv.setText(sb);

        QuizActivity.correct=0;
        QuizActivity.wrong=0;

        brnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),QuizActivity.class);
                startActivity(in);
            }
        });

    }
}
