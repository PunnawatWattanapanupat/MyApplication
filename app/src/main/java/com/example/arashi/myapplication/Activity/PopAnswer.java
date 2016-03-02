package com.example.arashi.myapplication.Activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Ooppo on 21/2/2559.
 */


public class PopAnswer extends Activity{
    EditText edtTopic1;
    final String testPREFTOPIC1 = "SamplePreferences";
    final String testTOPIC1 = "UserName";
    SharedPreferences sp1;
    SharedPreferences.Editor editor1;
Button Done ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_answer);
        DisplayMetrics dm =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int width = dm.widthPixels;
        int heighht = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(heighht*.8));

        Done = (Button) findViewById(R.id.Done);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });


        Button Back = (Button) findViewById(R.id.Back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
}
