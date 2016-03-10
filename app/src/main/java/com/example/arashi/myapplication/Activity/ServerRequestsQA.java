package com.example.arashi.myapplication.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.arashi.myapplication.Object.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ooppo on 4/3/2559.
 */
public class ServerRequestsQA {

    ProgressDialog progressDialog;
    public static final String SERVER_ADDRESS = "http://54.169.74.141/";
    public ServerRequestsQA(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait...");
    }
    public void AddQuestion(String question,int user_id,String date,GetAddQuestion getAddQuestion){
        progressDialog.show();
        new AddQuestionAsyncTask(question,user_id,date,getAddQuestion).execute();
    }

    private String getEncodeData(Map<String, String> data) {
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


    public class AddQuestionAsyncTask extends AsyncTask<Void, Void, Void> {
        String question;
        int user_id;
        String date;
        String php = "AddQuestion.php";
        GetAddQuestion getAddQuestion;
        public AddQuestionAsyncTask(String question,int user_id,String date,GetAddQuestion getAddQuestion){
            this.question = question;
            this.user_id = user_id;
            this.date = date;
            this.getAddQuestion = getAddQuestion;

        }
        @Override
        protected Void doInBackground(Void... params) {
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("question", question);
            dataToSend.put("user_id", Integer.toString(user_id));
            dataToSend.put("date", date);
            String returnedAddQ = null;
            try {
                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + php);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                    writer.write(encode);
                    writer.flush();

                    StringBuilder stringBuilder = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                Log.e("custom_check", e.toString());
            }


            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            getAddQuestion.done(question);
            super.onPostExecute(aVoid);
        }
    }
}
