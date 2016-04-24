package com.example.arashi.myapplication.Activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;


/**
 * Created by Ooppo on 24/4/2559.
 */
public class ResultQuiz extends Activity {
    LinearLayout linearChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_quiz);
//        DisplayMetrics dm =new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//
//        int width = dm.widthPixels;
//        int heighht = dm.heightPixels;
//
//        getWindow().setLayout((int)(width*.8),(int)(heighht*.4));
//        //Questiontext = (EditText) findViewById(R.id.Questiontext);

        linearChart = (LinearLayout)findViewById(R.id.linearChart);
        drawChart(5);

    }
    public void drawChart(int count) {
        System.out.println(count);

        for (int k = 1; k <= count; k++) {
            View view = new View(this);
            view.setBackgroundColor(Color.parseColor("#ff6233"));
            view.setLayoutParams(new LinearLayout.LayoutParams(30, 400));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.setMargins(5, 0, 0, 0);  // substitute parameters for left,top, right, bottom view.setLayoutParams(params); linearChart.addView(view); }
            view.setLayoutParams(params);
            linearChart.addView(view);


        }
    }
}