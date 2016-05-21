package com.example.arashi.myapplication.Activity;

import android.app.Activity;
//import android.content.DialogInterface;
//import android.content.Context;
import android.app.AlertDialog;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arashi.myapplication.Object.User;
import com.example.arashi.myapplication.Secure;
import com.example.arashi.myapplication.Store.UserLocalStore;
import com.parse.Parse;
import com.parse.ParseInstallation;
//import android.widget.TextView;
//import android.widget.Button;
//import android.widget.Toast;

//import com.example.arashi.myapplication.UserManager;



public class MainActivity extends Activity implements OnClickListener {
    public EditText mUsername;
    public EditText mPassword;
    public Button button_confirm, button_cancel;
    public TextView SignUpText;
    UserLocalStore userLocalStore;
    Secure secure;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        mUsername=(EditText)findViewById(R.id.username_text);
        mPassword=(EditText)findViewById(R.id.password_text);
        button_confirm = (Button) findViewById(R.id.button_confirm);
        button_cancel = (Button) findViewById(R.id.button_cancel);
        SignUpText = (TextView) findViewById(R.id.SignUpText);

        button_confirm.setOnClickListener(this);
        button_cancel.setOnClickListener(this);
        SignUpText.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);
        secure = new Secure();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClick(View v){

            switch (v.getId()) {
                case R.id.button_confirm:
//                    insert();
                    String username = mUsername.getText().toString();
                    String password = mPassword.getText().toString();

                    password = secure.getHash(password);
                    User user = new User(username, password);
                    authenticate(user);
                    //Clear data before start
                    mUsername.getText().clear();
                    mPassword.getText().clear();
                    break;
                case R.id.SignUpText:
                    startActivity(new Intent(this, SignUpActivity.class));
                    break;
                case R.id.button_cancel:
                    System.exit(0);
                    break;
            }
        }

    private void authenticate(User user){
        SeverRequests serverRequests = new SeverRequests(this);
        serverRequests.fetchUserDataInBackground(user, new GetUserCallback(){
            public void done(User returnedUser){
                if(returnedUser == null){
                    showErrorMessage();
                }
                else{
                    logUserIn(returnedUser);
                }
            }
        });
    }
    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        dialogBuilder.setMessage("Invalid user's detail");
        dialogBuilder.setPositiveButton("OK",null);
        dialogBuilder.show();
    }
    private void logUserIn(User returnedUser){
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);
        Toast.makeText(this,"Log In is Completed", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, home.class);
        startActivity(intent);
    }
}
