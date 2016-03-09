package com.example.arashi.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arashi.myapplication.Object.User;
import com.example.arashi.myapplication.Secure;
import com.example.arashi.myapplication.Store.UserLocalStore;

public class EditProfile extends Activity implements View.OnClickListener {

    public EditText txt_name,txt_username,txt_email,txt_password,txt_confirm_password;
    public Button button_edit;
    UserLocalStore userLocalStore;
    Secure secure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        txt_name =  (EditText) findViewById(R.id.fullName_text);
        txt_username = (EditText) findViewById(R.id.username_text);
        txt_email = (EditText) findViewById(R.id.email_text);
        txt_password = (EditText) findViewById(R.id.password_text);
        txt_confirm_password = (EditText) findViewById(R.id.repassword_text);
        button_edit = (Button) findViewById(R.id.button_edit);

        button_edit.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);
        secure = new Secure();

    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.button_edit:
                String name = txt_name.getText().toString();
                String username = txt_username.getText().toString();
                String email = txt_email.getText().toString();
                String password = txt_password.getText().toString();
                String confirm_password = txt_confirm_password.getText().toString();
                int status = userLocalStore.getLoggedInUser().is_teacher ;
                int user_id = userLocalStore.getLoggedInUser().user_id;
            //    Toast.makeText(this,Integer.toString(userLocalStore.getLoggedInUser().user_id) +" "+userLocalStore.getLoggedInUser().username , Toast.LENGTH_SHORT).show();


                    if (password.equals(confirm_password)) {
                        if (password.isEmpty()) {
                            password = userLocalStore.getLoggedInUser().password;
                        }
                        else {
                            password = secure.getHash(password);
                        }

                        if (username.isEmpty()) {
                            username = userLocalStore.getLoggedInUser().username;
                        }
                        if (name.isEmpty()) {
                            name = userLocalStore.getLoggedInUser().name;
                        }
                        if (email.isEmpty()) {
                            email = userLocalStore.getLoggedInUser().email;
                        }

                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                            Toast.makeText(this, "Please check your email!", Toast.LENGTH_SHORT).show();

                        }
                            User user = new User(user_id, username, name, password, email, status);
                            registerUser(user);

                            break;

                    }

                    else {
                        Toast.makeText(this, "Please check your password!", Toast.LENGTH_SHORT).show();

                    }
        }
    }

    private void registerUser(User user){

        SeverRequests serverRequests = new SeverRequests(this);
        serverRequests.updateUserDataInBackground(user, new GetUserCallback(){
            public void done(User returnedUser){

            }
        });

        serverRequests.fetchUserDataInBackground(user, new GetUserCallback(){
            public void done(User returnedUser){
                if(returnedUser != null){
                    userLocalStore.storeUserData(returnedUser);
                    userLocalStore.setUserLoggedIn(true);
                    Toast.makeText(EditProfile.this, "Edit is completed", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditProfile.this, home.class));
                    finish();
                }
                else {
                    Toast.makeText(EditProfile.this, "Cannot Edit data", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
