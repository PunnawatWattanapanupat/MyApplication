package com.example.arashi.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by Ooppo on 24/4/2559.
 */
public class Popup_Result__Quiz_First_Page extends Activity{

    Button choiceGraphButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_result_quiz_first_page);
        choiceGraphButton = (Button) findViewById(R.id.choiceGraphButton);

//        DisplayMetrics dm =new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//
//        int width = dm.widthPixels;
//        int heighht = dm.heightPixels;
//
//        getWindow().setLayout((int)(width*.8),(int)(heighht*.4));
        //Questiontext = (EditText) findViewById(R.id.Questiontext);

        DisplayMetrics dm =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int width = dm.widthPixels;
        int heighht = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(heighht*.4));
        //Questiontext = (EditText) findViewById(R.id.Questiontext);

        BarChart barChart = (BarChart) findViewById(R.id.bar1chart);
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        BarDataSet dataset = new BarDataSet(entries, "# of Answers");


        // creating labels
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("A");
        labels.add("B");
        labels.add("C");
        labels.add("D");


        barChart.setDescription("Description");  // set the description

        barChart.animateY(1000);
        BarData data = new BarData(labels, dataset);
        barChart.setData(data); // set the data and list of lables into chart




        choiceGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Popup_Result__Quiz_First_Page.this,ResultQuiz.class));
            }
        });





    }
}