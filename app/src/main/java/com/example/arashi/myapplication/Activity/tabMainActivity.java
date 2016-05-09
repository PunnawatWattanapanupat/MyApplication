package com.example.arashi.myapplication.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Button;

import com.example.arashi.myapplication.Store.AnnounceLocalStore;
import com.example.arashi.myapplication.Store.ClassLocalStore;
import com.example.arashi.myapplication.Store.UserLocalStore;
import com.parse.Parse;
import com.parse.ParseInstallation;

//import android.app.FragmentManager;

public class tabMainActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE=1;
    ImageView imageToUpload;

    UserLocalStore userLocalStore;
    ClassLocalStore classLocalStore;
    AnnounceLocalStore announceLocalStore;

//    public interface FragmentRefreshListener{
//        void onRefresh();
//    }

//    private static final int RESULT_LOAD_IMAGE=1;
//    ImageView imageToUpload;
//    Button bUploadImage;
//    EditText uploadImageName;

    private boolean viewGroupIsVisible = true;

    private View mViewGroup;

//    public FragmentRefreshListener getFragmentRefreshListener() {
//        return fragmentRefreshListener;
//    }
//
//    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
//        this.fragmentRefreshListener = fragmentRefreshListener;
//    }
//
//    private FragmentRefreshListener fragmentRefreshListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_main);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Roster"));
        tabLayout.addTab(tabLayout.newTab().setText("Announcements"));
        tabLayout.addTab(tabLayout.newTab().setText("Q&A"));
        tabLayout.addTab(tabLayout.newTab().setText("Quiz"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        Button createAnnounce = (Button)findViewById(R.id.createAnnounce);
//
//        createAnnounce.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(getFragmentRefreshListener()!=null){
//                    getFragmentRefreshListener().onRefresh();
//                }
//            }
//        });





//        Button b = (Button) findViewById(R.id.testPopup);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(tabMainActivity.this,Pop.class));
//            }
//        });



//        imageToUpload = (ImageView) findViewById(R.id.imageToUpload);
//        // bUploadImage = (Button) v.findViewById(R.id.bUploadImage);
//
//        imageToUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
//            }
//        });




    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && data != null){
//            Uri selectedImage = data.getData();
//
//            imageToUpload.setImageURI(selectedImage);
//        }
//    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.imageToUpload:
//                Toast.makeText(tabMainActivity.this, "CLICKKK!!! =)",
//                        Toast.LENGTH_LONG).show();
////                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
////                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
//                break;
//            case R.id.bUploadImage:
//                break;
//        }
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
//            Uri selectedImage = data.getData();
//            imageToUpload.setImageURI(selectedImage);
//        }
//
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sign_out:
//                userLocalStore.cleanUserData();
//                announceLocalStore.cleanAnnounceData();
//                classLocalStore.cleanClassData();
                AlertDialog alertbox = new AlertDialog.Builder(this)
                        .setMessage("Do you want to exit application?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            // do something when the button is clicked
                            public void onClick(DialogInterface arg0, int arg1) {

                                startActivity(new Intent(tabMainActivity.this,MainActivity.class));
                                //close();


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {

                            // do something when the button is clicked
                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        })
                        .show();
               // finish();
                return true;
            case R.id.action_settings:
                // Settings option clicked.
                startActivity(new Intent(this,home.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void dateChoose(View view) {

    }


//    public void ActiveClick(View v) {
//        //code to check if this checkbox is checked!
//        // CheckBox checkBox = (CheckBox)v;
//
//        final TabFragment1 tabFragment1 = new TabFragment1();
//        CheckBox checkBox = (CheckBox) findViewById(R.id.check_activate);
//        checkBox.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (((CheckBox) v).isChecked())
//                    Toast.makeText(tabMainActivity.this, "students are checked !!! =)",
//                            Toast.LENGTH_LONG).show();
//                else {
//                    Toast.makeText(tabMainActivity.this, "uncheck !!! =)",
//                            Toast.LENGTH_LONG).show();
//
//
//                    mViewGroup = findViewById(R.id.viewsContainer);
//                    //mViewGroup.setVisibility(View.INVISIBLE);
//                    showFeedChickenDialog();
//
////                    tabFragment1.setInvis();
////                    RelativeLayout testLayout = (RelativeLayout) v.findViewById(R.id.onetest);
//////                    testLayout.setVisibility(RelativeLayout.GONE);
//                }
//            }
//        });
//    }

//                if(checkBox22.isChecked()==true){
////            if(checkBox.isChecked()==false){
////                RelativeLayout testLayout = (RelativeLayout) v.findViewById(R.id.onetest);
////                testLayout.setVisibility(RelativeLayout.GONE);
////            }
//            Log.v("blah", "blah blah");
//
//    public void showDialog(View v) {
//        FragmentManager manager = getSupportFragmentManager();
//        MyDialog myDialog = new MyDialog();
//        myDialog.show(manager,"l");
//
//    }

//    @Override
//    public void onDialogMessage(String message) {
//        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
//    }
    protected void showFeedChickenDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title);
        builder.setMessage("                    Attendance" +
                "                    Day 21/2/2016 \n" +
                "                    Present : 3" +
                "                    Absent : 0");
        builder.setPositiveButton(getString(R.string.positive_text), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast toast = Toast.makeText(tabMainActivity.this, getString(R.string.button_response, getString(R.string.positive_text)), Toast.LENGTH_SHORT);
                toast.show();

            }
        });

        builder.setNegativeButton(getString(R.string.negative_text), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast toast = Toast.makeText(tabMainActivity.this, getString(R.string.button_response, getString(R.string.negative_text)), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }


}






