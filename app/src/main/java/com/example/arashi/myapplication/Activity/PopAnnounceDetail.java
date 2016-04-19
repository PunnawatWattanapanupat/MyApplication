package com.example.arashi.myapplication.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.arashi.myapplication.Store.AnnounceLocalStore;

public class PopAnnounceDetail extends AppCompatActivity {

    TextView topic_announce, detail_announce;
    Button ok_announce;
    AnnounceLocalStore announceLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_announce_detail);

        DisplayMetrics dm =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int width = dm.widthPixels;
        int heighht = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(heighht*.6));

        topic_announce = (TextView) findViewById(R.id.topic_announce);
        detail_announce = (TextView) findViewById(R.id.detail_announce);
        ok_announce = (Button) findViewById(R.id.ok_announce);

        announceLocalStore = new AnnounceLocalStore(this);

        topic_announce.setText(announceLocalStore.getShowAnnounce().topic);
        detail_announce.setText(announceLocalStore.getShowAnnounce().detail);
        ok_announce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
