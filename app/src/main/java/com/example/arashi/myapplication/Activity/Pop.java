package com.example.arashi.myapplication.Activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.arashi.myapplication.Object.Announcement;
import com.example.arashi.myapplication.Store.AnnounceLocalStore;
import com.example.arashi.myapplication.Store.ClassLocalStore;
import com.example.arashi.myapplication.Store.UserLocalStore;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParsePushBroadcastReceiver;
import com.parse.ParseQuery;
import com.parse.PushService;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Ooppo on 10/2/2559.
 */
public class Pop extends Activity  {

    private static final int RESULT_LOAD_IMAGE=1;
    ImageView imageToUpload;

    EditText edtTopic1;
    final String testPREFTOPIC1 = "SamplePreferences";
    final String testTOPIC1 = "UserName";
    SharedPreferences sp1;
    SharedPreferences.Editor editor1;



    EditText edtTopic2;
    final String testPREFTOPIC2 = "SamplePreferences";
    final String testTOPIC2 = "UserName";
    SharedPreferences sp2;
    SharedPreferences.Editor editor2;
    AnnounceLocalStore announceLocalStore;
    ClassLocalStore classLocalStore;
    Button Done, Back;


//    public tabMainActivity.FragmentRefreshListener getFragmentRefreshListener() {
//        return fragmentRefreshListener;
//    }
//    public void setFragmentRefreshListener(tabMainActivity.FragmentRefreshListener fragmentRefreshListener) {
//        this.fragmentRefreshListener = fragmentRefreshListener;
//    }
//
//    tabMainActivity.FragmentRefreshListener fragmentRefreshListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);
        DisplayMetrics dm =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);



        int width = dm.widthPixels;
        int heighht = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(heighht*.8));


        sp1 = getSharedPreferences(testPREFTOPIC1, Context.MODE_PRIVATE);
        editor1 = sp1.edit();

        edtTopic1 = (EditText)findViewById(R.id.topicAnn);
        edtTopic1.setText(sp1.getString(testTOPIC1, ""));

        edtTopic1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void afterTextChanged(Editable s) {
                editor1.putString(testTOPIC1, s.toString());
                editor1.commit();
            }
        });


        sp2 = getSharedPreferences(testPREFTOPIC2, Context.MODE_PRIVATE);
        editor2 = sp2.edit();

        edtTopic2 = (EditText)findViewById(R.id.datailAnn);
        edtTopic2.setText(sp2.getString(testTOPIC2, ""));

        edtTopic2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void afterTextChanged(Editable s) {
                editor2.putString(testTOPIC2, s.toString());
                editor2.commit();
            }
        });




        imageToUpload = (ImageView) findViewById(R.id.imageToUpload);
       // bUploadImage = (Button) v.findViewById(R.id.bUploadImage);

        imageToUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
            }
        });

        announceLocalStore = new AnnounceLocalStore(this);
        classLocalStore = new ClassLocalStore(this);

        //Click Done
        Done = (Button) findViewById(R.id.Done);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor1.clear();
                editor1.commit();
                editor2.clear();
                editor2.commit();
                Log.v("Test","Test");

                String topic = edtTopic1.getText().toString();
                String detail = edtTopic2.getText().toString();
                String photo = "cannot save photo";
                int user_id = classLocalStore.getJoinedInClass().user_id;
                int class_id = classLocalStore.getJoinedInClass().class_id;
                String date_post = DateFormat.getDateTimeInstance().format(new Date());

                if(topic.isEmpty() || detail.isEmpty() || user_id == 0 || class_id == 0){
                    Toast.makeText(Pop.this,"Please fill all completely", Toast.LENGTH_SHORT).show();
                }
                else {
                        Announcement announcement = new Announcement(topic, detail, photo,date_post, user_id, class_id);
                        postAnnounce(announcement);
                }

            }
        });

        //Click Back
        Back = (Button) findViewById(R.id.Back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void postAnnounce(Announcement announcement){

        SeverRequests serverRequests = new SeverRequests(this);

        // Create our Installation query
        ParseQuery pushQuery = ParseInstallation.getQuery();
        pushQuery.whereEqualTo("classAnnounce", true);

        // Send push notification to query
        ParsePush push = new ParsePush();
        push.setQuery(pushQuery); // Set our Installation query
      //  push.se
        push.setMessage(announcement.topic);
        push.sendInBackground();

        //

        // Add custom intent
        Intent cIntent = new Intent(this, TabFragment2.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                cIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create custom notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentText(announcement.detail)
                .setContentTitle(announcement.topic)
                .setContentIntent(pendingIntent);

        Notification notification = builder.build();
        NotificationManager nm = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        nm.notify(1410, notification);



       // PendingIntent.getActivities()

        serverRequests.storeAnnounceDataInBackground(announcement, new GetAnnounceCallBack() {
            @Override
            public void done(Announcement returnAnnounce) {

                Toast.makeText(Pop.this, "Posing is completed", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Pop.this, tabMainActivity.class));
//                finish();
            }
        });

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && data != null){
            Uri selectedImage = data.getData();

            imageToUpload.setImageURI(selectedImage);
        }
    }










}
