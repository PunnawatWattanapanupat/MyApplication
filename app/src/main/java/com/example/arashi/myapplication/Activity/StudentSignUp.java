package com.example.arashi.myapplication.Activity;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arashi.myapplication.Object.User;
import com.example.arashi.myapplication.Secure;

public class StudentSignUp extends Activity implements View.OnClickListener {

    public EditText txt_name,txt_username,txt_email,txt_password,txt_confirm_password;
    public Button btn_confirm;
    Secure secure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);
        //getSupportActionBar().hide();

        txt_name =  (EditText) findViewById(R.id.fullName_text);
        txt_username = (EditText) findViewById(R.id.username_text);
        txt_email = (EditText) findViewById(R.id.email_text);
        txt_password = (EditText) findViewById(R.id.password_text);
        txt_confirm_password = (EditText) findViewById(R.id.repassword_text);
        btn_confirm = (Button) findViewById(R.id.button_confirm);

        btn_confirm.setOnClickListener(this);

        secure = new Secure();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.button_confirm:
                //Toast.makeText(StudentSignUp.this,"Please check your password!", Toast.LENGTH_SHORT).show();
                String name = txt_name.getText().toString();
                String username = txt_username.getText().toString();
                String email = txt_email.getText().toString();
                String password = txt_password.getText().toString();
                String confirm_password = txt_confirm_password.getText().toString();

                if(name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirm_password.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(StudentSignUp.this,"Please fill all completely", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (password.equals(confirm_password)) {

                        password = secure.getHash(password);
                        User user = new User(username, name, password, email, 0);
                        registerUser(user);
                    } else {
                        Toast.makeText(StudentSignUp.this, "Please check your password!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void registerUser(User user){

        SeverRequests serverRequests = new SeverRequests(this);
        serverRequests.storeUserDataInBackground(user,"Register.php", new GetUserCallback(){

            public void done(User returnedUser){
                Toast.makeText(StudentSignUp.this, "Register is completed", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(StudentSignUp.this, MainActivity.class));
                finish();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_sign_up, menu);
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

}
