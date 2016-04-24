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
        DisplayMetrics dm =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int width = dm.widthPixels;
        int heighht = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(heighht*.4));
        //Questiontext = (EditText) findViewById(R.id.Questiontext);

        BarChart barChart = (BarChart) findViewById(R.id.chart);
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


        BarData data = new BarData(labels, dataset);
        barChart.setData(data); // set the data and list of lables into chart

    }

}