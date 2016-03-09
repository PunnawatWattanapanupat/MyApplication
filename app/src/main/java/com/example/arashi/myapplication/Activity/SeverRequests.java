package com.example.arashi.myapplication.Activity;

import com.example.arashi.myapplication.Object.Announcement;
import com.example.arashi.myapplication.Object.Class;
import com.example.arashi.myapplication.Object.Roster;
import com.example.arashi.myapplication.Object.User;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arashi on 1/30/2016.
 */
public class SeverRequests {
    ProgressDialog progressDialog;
   //public static final int CONNECTION_TIMEOUT = 1000*15;
    public static final String SERVER_ADDRESS = "http://54.169.74.141/";


    public SeverRequests(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait...");
    }

    public void storeUserDataInBackground(User user, String phpfile, GetUserCallback userCallback){
        progressDialog.show();
        new StoreUserDataAsyncTask(user, phpfile, userCallback).execute();
    }

    public void fetchUserDataInBackground(User user, GetUserCallback userCallback){
        progressDialog.show();
        new fetchUserDataAsyncTask(user, userCallback).execute();
    }

    public void updateUserDataInBackground(User user, GetUserCallback userCallback){
        progressDialog.show();
        new updateUserDataAsyncTask(user, userCallback).execute();
    }

    public void storeClassDataInBackground(Class classroom, GetClassCallback classCallback){
        progressDialog.show();
        new  storeClassDataAsyncTask(classroom, classCallback).execute();
    }

    public void fetchClassDataInBackground(Class classroom, GetClassCallback classCallback){
        progressDialog.show();
        new  fetchClassDataAsyncTask(classroom, classCallback).execute();
    }

    public void showClassListInBackground(User user, GetShowClassCallback showClassCallback) {
        new showClassListAsyncTask(user, showClassCallback).execute();
    }

    public void storeRosterDataInBackground(Roster roster, GetRosterCallback rosterCallback){
        progressDialog.show();
        new  storeRosterDataAsyncTask(roster, rosterCallback).execute();
    }

    public void showRosterStudentListInBackground(Class classroom,GetShowRosterStudentCallback  getShowRosterStudentCallback) {
        new showRosterStudentListAsyncTask(classroom, getShowRosterStudentCallback).execute();
    }

    public void storeAnnounceDataInBackground(Announcement announcement, GetAnnounceCallBack announceCallBack){
        progressDialog.show();
        new StoreAnnounceDataAsyncTask(announcement, announceCallBack).execute();
    }

    public void showAnnounceListInBackground(Class classroom,GetShowAnnounceCallback  getShowAnnounceCallback) {
        new showAnnounceListAsyncTask(classroom, getShowAnnounceCallback).execute();
    }


    public String getEncodeData(Map<String, String> data) {
        StringBuilder sb = new StringBuilder();
        for (String key : data.keySet()) {
            String value = null;
            try {
                value = URLEncoder.encode(data.get(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (sb.length() > 0)
                sb.append("&");

            sb.append(key + "=" + value);
        }
        return sb.toString();
    }



    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, User>{
        User user;
        String phpfile;
        GetUserCallback userCallback;
        public StoreUserDataAsyncTask(User user, String phpfile, GetUserCallback userCallback){
            this.user = user;
            this.phpfile = phpfile;
            this.userCallback = userCallback;

        }
        @Override
        protected User doInBackground(Void... params){
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("username", user.username);
            dataToSend.put("name", user.name);
            dataToSend.put("password", user.password);
            dataToSend.put("email", user.email);
            dataToSend.put("is_teacher", user.is_teacher+"");


            User returnUser = null;

            try {

                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + phpfile);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                    writer.write(encode);
                    writer.flush();

                    StringBuilder stringBuilder = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));


                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    line = stringBuilder.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close(); // Close Reader
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Log.i("custom_check", line);

                JSONObject jObj = new JSONObject(line);

                if (jObj.length() != 0) {
                    String name = jObj.getString("name");
                    String email = jObj.getString("email");
                    int is_teacher = jObj.getInt("is_teacher");

                    returnUser = new User(user.username,name, user.password, email, is_teacher);
                }
            } catch (Exception e) {
                Log.e("custom_check", e.toString());
            }

            return returnUser;
        }


        protected void onPostExecute(User returnUser){
            progressDialog.dismiss();
            userCallback.done(returnUser);
            super.onPostExecute(returnUser);
        }
    }
    public class fetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        GetUserCallback userCallback;

        public fetchUserDataAsyncTask(User user, GetUserCallback userCallback) {
            this.user = user;
            this.userCallback = userCallback;

        }
        @Override
        protected User doInBackground(Void... params){
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("username", user.username);
            dataToSend.put("password", user.password);


            User returnUser = null;

            try {

                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + "FetchUserData.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                    writer.write(encode);
                    writer.flush();

                    StringBuilder stringBuilder = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));


                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    line = stringBuilder.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close(); // Close Reader
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Log.i("custom_check", line);

                JSONObject jObj = new JSONObject(line);

                if (jObj.length() != 0) {
                    int user_id = jObj.getInt("user_id");
                    String name = jObj.getString("name");
                    String email = jObj.getString("email");
                    int is_teacher = jObj.getInt("is_teacher");

                    returnUser = new User(user_id, user.username,name, user.password, email, is_teacher);
                }
            } catch (Exception e) {
                Log.e("custom_check", e.toString());
            }

            return returnUser;
        }



        protected void onPostExecute(User returnedUser){
            progressDialog.dismiss();
            userCallback.done(returnedUser);
            super.onPostExecute(returnedUser);
        }
    }

    public class updateUserDataAsyncTask extends AsyncTask<Void, Void, User>{
        User user;
        GetUserCallback userCallback;
        public updateUserDataAsyncTask(User user, GetUserCallback userCallback){
            this.user = user;
            this.userCallback = userCallback;

        }
        @Override
        protected User doInBackground(Void... params){
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("user_id", user.user_id+"");
            dataToSend.put("username", user.username);
            dataToSend.put("name", user.name);
            dataToSend.put("password", user.password);
            dataToSend.put("email", user.email);
            dataToSend.put("is_teacher", user.is_teacher+"");


            User returnUser = null;

            try {

                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + "UpdateInfo.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                    writer.write(encode);
                    writer.flush();

                    StringBuilder stringBuilder = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));


                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    line = stringBuilder.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close(); // Close Reader
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Log.i("custom_check", line);

                JSONObject jObj = new JSONObject(line);

                if (jObj.length() != 0) {
                    int user_id = jObj.getInt("user_id");
                    String username = jObj.getString("username");
                    String name = jObj.getString("name");
                    String password = jObj.getString("password");
                    String email = jObj.getString("email");
                    int is_teacher = jObj.getInt("is_teacher");

                    returnUser = new User(user_id,username,name, password, email, is_teacher);
                }
            } catch (Exception e) {
                Log.e("custom_check", e.toString());
            }

            return returnUser;
        }


        protected void onPostExecute(User returnUser){
            progressDialog.dismiss();
            userCallback.done(returnUser);
            super.onPostExecute(returnUser);
        }
    }

    public class storeClassDataAsyncTask extends AsyncTask<Void, Void, Class> {
        Class classroom;
        GetClassCallback classCallback;


        public storeClassDataAsyncTask(Class classroom, GetClassCallback classCallback) {
            this.classroom = classroom;
            this.classCallback = classCallback;

        }
        @Override
        protected Class doInBackground(Void... params){
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("classname", classroom.classname);
            dataToSend.put("class_code", classroom.class_code);
            dataToSend.put("user_id", classroom.user_id+"");

            Class returnClass = null;

            try {

                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + "CreateClass.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                    writer.write(encode);
                    writer.flush();

                    StringBuilder stringBuilder = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));


                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    line = stringBuilder.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close(); // Close Reader
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Log.i("custom_check", line);

                JSONObject jObj = new JSONObject(line);

                if (jObj.length() != 0) {
                   // int user_id = jObj.getInt("user_id");
                    String classname = jObj.getString("classname");
                    String class_code = jObj.getString("class_code");


                    returnClass = new Class(classname,class_code, classroom.user_id);
                }
            } catch (Exception e) {
                Log.e("custom_check", e.toString());
            }

            return returnClass;
        }


        protected void onPostExecute(Class returnClass){
            progressDialog.dismiss();
            classCallback.done(returnClass);
            super.onPostExecute(returnClass);
        }
    }

    public class fetchClassDataAsyncTask extends AsyncTask<Void, Void, Class> {
        Class classroom;
        GetClassCallback classCallback;


        public fetchClassDataAsyncTask(Class classroom, GetClassCallback classCallback) {
            this.classroom = classroom;
            this.classCallback = classCallback;

        }
        @Override
        protected Class doInBackground(Void... params){
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("classname", classroom.classname);
            dataToSend.put("class_code", classroom.class_code);

            Class returnClass = null;

            try {

                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + "FetchClassData.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                    writer.write(encode);
                    writer.flush();

                    StringBuilder stringBuilder = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));


                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    line = stringBuilder.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close(); // Close Reader
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Log.i("custom_check", line);

                JSONObject jObj = new JSONObject(line);

                if (jObj.length() != 0) {
                    // int user_id = jObj.getInt("user_id");
                    int class_id = jObj.getInt("class_id");
                    String classname = jObj.getString("classname");
                    String class_code = jObj.getString("class_code");


                    returnClass = new Class(class_id,classname,class_code, classroom.user_id);
                }
            } catch (Exception e) {
                Log.e("custom_check", e.toString());
            }

            return returnClass;
        }


        protected void onPostExecute(Class returnClass){
            progressDialog.dismiss();
            classCallback.done(returnClass);
            super.onPostExecute(returnClass);
        }
    }

    public class showClassListAsyncTask extends AsyncTask<Void, Void, ArrayList<Class>> {

        GetShowClassCallback showClassCallback;
        ArrayList<Class> showClass;
       // int user_id;
        User user;


        public showClassListAsyncTask(User user, GetShowClassCallback showClassCallback) {
            this.user = user;
            this.showClassCallback = showClassCallback;
            showClass = new ArrayList<>();

        }
        @Override
        protected ArrayList<Class> doInBackground(Void... params){
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("user_id", user.user_id+"");


            try {

                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url;
                    //teacher
                    if(user.is_teacher == 1) {
                        url = new URL(SERVER_ADDRESS + "ShowClass.php");
                    }
                    //student
                    else {
                        url = new URL(SERVER_ADDRESS + "ShowClassStudent.php");
                    }
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                    writer.write(encode);
                    writer.flush();

                    StringBuilder stringBuilder = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));


                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    line = stringBuilder.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close(); // Close Reader
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Log.i("custom_check", line);

                JSONObject jObj = new JSONObject(line);
                JSONArray noticeArray = jObj.getJSONArray("classroom");
                Class classroom;
                for (int i = 0; i < noticeArray.length(); i++) {
                    JSONObject classrooms = noticeArray.getJSONObject(i);
                    int class_id = classrooms.getInt("class_id");
                    String classname = classrooms.getString("classname");
                    String class_code = classrooms.getString("class_code");
                    int user_id = classrooms.getInt("user_id");
                    classroom = new Class(class_id, classname, class_code, user_id);
                    showClass.add(classroom);
                }
            } catch (Exception e) {
                Log.e("custom_check", e.toString());
            }

            return showClass;
        }


        protected void onPostExecute(ArrayList<Class> returnShowClass){
            progressDialog.dismiss();
            showClassCallback.done(returnShowClass);
            super.onPostExecute(returnShowClass);
        }
    }



    public class storeRosterDataAsyncTask extends AsyncTask<Void, Void, Roster> {
        Roster roster;
        GetRosterCallback rosterCallback;


        public storeRosterDataAsyncTask(Roster roster, GetRosterCallback rosterCallback) {
            this.roster = roster;
            this.rosterCallback = rosterCallback;

        }
        @Override
        protected Roster doInBackground(Void... params){
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("user_id", roster.user_id+"");
            dataToSend.put("class_id", roster.class_id+"");
           // dataToSend.put("check_student", roster.check_student+"");


            Roster returnRoster = null;

            try {

                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + "CreateRoster.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                    writer.write(encode);
                    writer.flush();

                    StringBuilder stringBuilder = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));


                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    line = stringBuilder.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close(); // Close Reader
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Log.i("custom_check", line);

                JSONObject jObj = new JSONObject(line);

                if (jObj.length() != 0) {
                    int user_id = jObj.getInt("user_id");
                    int class_id = jObj.getInt("class_id");
 //                 int check_student = jObj.getInt("check_student");


                    returnRoster = new Roster(user_id,class_id);
                }
            } catch (Exception e) {
                Log.e("custom_check", e.toString());
            }

            return returnRoster;
        }


        protected void onPostExecute(Roster returnRoster){
            progressDialog.dismiss();
            rosterCallback.done(returnRoster);
            super.onPostExecute(returnRoster);
        }
    }

    public class showRosterStudentListAsyncTask extends AsyncTask<Void, Void, ArrayList<User>> {

        GetShowRosterStudentCallback showRosterStudentCallback;
        ArrayList<User> showRosterStudent;
        Class classroom;


        public showRosterStudentListAsyncTask(Class classroom, GetShowRosterStudentCallback showRosterStudentCallback) {
            this.classroom = classroom;
            this.showRosterStudentCallback = showRosterStudentCallback;
            showRosterStudent = new ArrayList<>();

        }
        @Override
        protected ArrayList<User> doInBackground(Void... params){
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("class_id", classroom.class_id+"");


            try {

                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + "showRosterStudent.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                    writer.write(encode);
                    writer.flush();

                    StringBuilder stringBuilder = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));


                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    line = stringBuilder.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close(); // Close Reader
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Log.i("custom_check", line);

                JSONObject jObj = new JSONObject(line);
                JSONArray noticeArray = jObj.getJSONArray("roster_student");
                User roster_student;
                for (int i = 0; i < noticeArray.length(); i++) {
                    JSONObject student = noticeArray.getJSONObject(i);
                    String username = student.getString("username");
                    String name = student.getString("name");
                    String email = student.getString("email");
                    int user_id = student.getInt("user_id");
                    roster_student = new User(user_id, username, name, email);
                    showRosterStudent.add(roster_student);
                }
            } catch (Exception e) {
                Log.e("custom_check", e.toString());
            }

            return showRosterStudent;
        }


        protected void onPostExecute(ArrayList<User> returnedRosterStudent){
            progressDialog.dismiss();
            showRosterStudentCallback.done(returnedRosterStudent);
            super.onPostExecute(returnedRosterStudent);
        }
    }

    public class StoreAnnounceDataAsyncTask extends AsyncTask<Void, Void, Announcement> {
        Announcement announcement;
        GetAnnounceCallBack announceCallBack;
        public StoreAnnounceDataAsyncTask(Announcement announcement, GetAnnounceCallBack announceCallBack){
            this.announcement = announcement;
            this.announceCallBack = announceCallBack;

        }

        protected Announcement doInBackground(Void... params){
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("topic", announcement.topic);
            dataToSend.put("detail",announcement.detail );
            dataToSend.put("photo", announcement.photo);
            dataToSend.put("date_post", announcement.date_post);
            dataToSend.put("user_id", announcement.user_id+"");
            dataToSend.put("class_id", announcement.class_id+"");

            Announcement returnAnnounce = null;

            try {

                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + "Post_Announcement.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                    writer.write(encode);
                    writer.flush();

                    StringBuilder stringBuilder = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));


                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    line = stringBuilder.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close(); // Close Reader
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Log.i("custom_check", line);

                JSONObject jObj = new JSONObject(line);

                if (jObj.length() != 0) {
                    String topic = jObj.getString("topic");
                    String detail = jObj.getString("detail");
                    String photo = jObj.getString("photo");
                    String date_post = jObj.getString("date_post");
                    int user_id = jObj.getInt("user_id");
                    int class_id = jObj.getInt("class_id");

                    returnAnnounce = new Announcement(topic,detail, photo, date_post, user_id, class_id);
                }
            } catch (Exception e) {
                Log.e("custom_check", e.toString());
            }

            return returnAnnounce;
        }


        protected void onPostExecute(Announcement returnAnnounce){
            progressDialog.dismiss();
            announceCallBack.done(returnAnnounce);
            super.onPostExecute(returnAnnounce);
        }
    }


    public class showAnnounceListAsyncTask extends AsyncTask<Void, Void, ArrayList<Announcement>> {

        GetShowAnnounceCallback showAnnounceCallback;
        ArrayList<Announcement> showAnnounce;
        Class classroom;


        public showAnnounceListAsyncTask(Class classroom, GetShowAnnounceCallback showAnnounceCallback) {
            this.classroom = classroom;
            this.showAnnounceCallback = showAnnounceCallback;
            showAnnounce = new ArrayList<>();

        }
        @Override
        protected ArrayList<Announcement> doInBackground(Void... params){
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("user_id", classroom.user_id+"");
            dataToSend.put("class_id", classroom.class_id+"");


            try {

                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + "showAnnouncement.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                    writer.write(encode);
                    writer.flush();

                    StringBuilder stringBuilder = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));


                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    line = stringBuilder.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close(); // Close Reader
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Log.i("custom_check", line);

                JSONObject jObj = new JSONObject(line);
                JSONArray noticeArray = jObj.getJSONArray("announcement");
                Announcement announcement;
                for (int i = 0; i < noticeArray.length(); i++) {
                    JSONObject announcements = noticeArray.getJSONObject(i);
                    String topic = announcements.getString("topic");
                    String detail = announcements.getString("detail");
                    String photo = announcements.getString("photo");
                    String date_post = announcements.getString("date_post");
                    int user_id = announcements.getInt("user_id");
                    int class_id = announcements.getInt("class_id");
                    announcement = new Announcement(topic, detail, photo,date_post, user_id, class_id);
                    showAnnounce.add(announcement);
                }
            } catch (Exception e) {
                Log.e("custom_check", e.toString());
            }

            return showAnnounce;
        }


        protected void onPostExecute(ArrayList<Announcement> returnShowAnnounce){
            progressDialog.dismiss();
            showAnnounceCallback.done(returnShowAnnounce);
            super.onPostExecute(returnShowAnnounce);
        }
    }

}
