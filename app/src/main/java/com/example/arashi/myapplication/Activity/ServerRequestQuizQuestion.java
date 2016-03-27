package com.example.arashi.myapplication.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.arashi.myapplication.Object.Announcement;
import com.example.arashi.myapplication.Object.Class;
import com.example.arashi.myapplication.Object.Quiz;
import com.example.arashi.myapplication.Object.Roster;
import com.example.arashi.myapplication.Object.User;

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
 * Created by Ooppo on 16/3/2559.
 */


public class ServerRequestQuizQuestion {
    ProgressDialog progressDialog;
    //public static final int CONNECTION_TIMEOUT = 1000*15;
    public static final String SERVER_ADDRESS = "http://54.169.74.141/";


    public ServerRequestQuizQuestion(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait...");
    }

    public void storeQuizDataInBackground(Quiz quiz, String phpfile, GetQuizCallback quizCallback){
        progressDialog.show();
        new StoreQuizDataAsyncTask(quiz, phpfile, quizCallback).execute();
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



    public class StoreQuizDataAsyncTask extends AsyncTask<Void, Void, Quiz> {
        Quiz quiz;
        String phpfile;
        GetQuizCallback quizCallback;

        public StoreQuizDataAsyncTask(Quiz quiz, String phpfile, GetQuizCallback quizCallback){
            this.quiz = quiz;
            this.phpfile = phpfile;
            this.quizCallback = quizCallback;

        }
        @Override
        protected Quiz doInBackground(Void... params){
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("quiz_id","" + quiz.quizID);
            dataToSend.put("quiz_name", quiz.quiz_name);
            dataToSend.put("is_active", ""+ quiz.is_active);
            dataToSend.put("question", quiz.QuizConcat());

            Quiz returnQuiz = null;

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
                    String quiz_name = jObj.getString("quiz_name");
                    int quizID = jObj.getInt("quizID");
                    int is_active = jObj.getInt("is_active");
                    int class_id=  jObj.getInt("class_id");

                    returnQuiz = new Quiz(quiz_name,quizID,is_active,class_id);
                }
            } catch (Exception e) {
                Log.e("custom_check", e.toString());
            }

            return returnQuiz;
        }


        protected void onPostExecute(Quiz returnQuiz){
            progressDialog.dismiss();
            quizCallback.done(returnQuiz);
            super.onPostExecute(returnQuiz);
        }
    }
}
