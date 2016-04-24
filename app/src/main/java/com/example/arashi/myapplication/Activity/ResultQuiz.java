package com.example.arashi.myapplication.Activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


/**
 * Created by Ooppo on 24/4/2559.
 */
public class ResultQuiz extends Activity {
    LinearLayout linearChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_quiz);
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;
//
//        getWindow().setLayout((int)(width*.8),(int)(height*.4));
//        //Questiontext = (EditText) findViewById(R.id.Questiontext);

        BarChart barChart = (BarChart) findViewById(R.id.bar2chart);


// creating labels
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");




        // create BarEntry for group 1
        ArrayList<BarEntry> group1 = new ArrayList<>();
        group1.add(new BarEntry(4f, 0));
        group1.add(new BarEntry(8f, 1));
        group1.add(new BarEntry(6f, 2));
        group1.add(new BarEntry(12f, 3));
        group1.add(new BarEntry(1f, 4));
        group1.add(new BarEntry(9f, 5));

// create BarEntry for group 2
        ArrayList<BarEntry> group2 = new ArrayList<>();
        group2.add(new BarEntry(6f, 0));
        group2.add(new BarEntry(7f, 1));
        group2.add(new BarEntry(8f, 2));
        group2.add(new BarEntry(12f, 3));
        group2.add(new BarEntry(15f, 4));
        group2.add(new BarEntry(10f, 5));

        // create BarEntry for group 3
        ArrayList<BarEntry> group3 = new ArrayList<>();
        group3.add(new BarEntry(6f, 0));
        group3.add(new BarEntry(7f, 1));
        group3.add(new BarEntry(8f, 2));
        group3.add(new BarEntry(12f, 3));
        group3.add(new BarEntry(15f, 4));
        group3.add(new BarEntry(10f, 5));

        // create BarEntry for group 4
        ArrayList<BarEntry> group4 = new ArrayList<>();
        group4.add(new BarEntry(6f, 0));
        group4.add(new BarEntry(7f, 1));
        group4.add(new BarEntry(8f, 2));
        group4.add(new BarEntry(12f, 3));
        group4.add(new BarEntry(8f, 4));
        group4.add(new BarEntry(10f, 5));

        BarDataSet barDataSet1 = new BarDataSet(group1, "Choice A");  // creating dataset for group1
//barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setColor(Color.parseColor("#CC0066"));

        BarDataSet barDataSet2 = new BarDataSet(group2, "Choice B"); // creating dataset for group1
        barDataSet2.setColor(Color.parseColor("#FF8000"));

        BarDataSet barDataSet3 = new BarDataSet(group3, "Choice C"); // creating dataset for group1
        barDataSet3.setColor(Color.parseColor("#CDC44D"));

        BarDataSet barDataSet4 = new BarDataSet(group4, "Choice D"); // creating dataset for group1
        barDataSet4.setColor(Color.parseColor("#4C9900"));

        ArrayList<BarDataSet> dataSets = new ArrayList<>();  // combined all dataset into an arraylist
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        dataSets.add(barDataSet3);
        dataSets.add(barDataSet4);


        barChart.setDescription("Description");  // set the description

        barChart.animateY(1000);
        BarData data = new BarData(labels, dataSets); // initialize the Bardata with argument labels and dataSet
        barChart.setData(data);







    }

}