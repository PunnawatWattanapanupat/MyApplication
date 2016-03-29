package com.example.arashi.myapplication.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.arashi.myapplication.Object.Answerstack;
import com.example.arashi.myapplication.Object.Questionstack;
import com.example.arashi.myapplication.Store.ClassLocalStore;
import com.example.arashi.myapplication.Object.Class;

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
 * Created by Ooppo on 4/3/2559.
 */
public class ServerRequestsQA {
    ProgressDialog progressDialog;
    public static final String SERVER_ADDRESS = "http://54.169.74.141/";

    public ServerRequestsQA(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait...");
    }

    public void AddQuestion(String question, int user_id, String date, Class classroom, GetAddQuestion getAddQuestion) {
        progressDialog.show();
        new AddQuestionAsyncTask(question, user_id, date, classroom, getAddQuestion).execute();
    }

    public void SelectQuestion(Class classroom, GetSelection getSelection) {
        progressDialog.show();
        new SelectQuestionAsyncTask(classroom, getSelection).execute();
    }

    public void AddAnswer(int qid,String answer,String dateanswer,String useranswer,GetAddAnswer getAddAnswer){
        progressDialog.show();
        new AddAnswerAsyncTask(qid, answer, dateanswer, useranswer,getAddAnswer).execute();
    };

    public void SelectAnswer(int question_id, Class classroom,GetSelectionAnswer getSelectionAnswer) {
        new SelectAnswerAsyncTask(question_id, classroom,getSelectionAnswer).execute();
    };

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
        Class classroom;

        public AddQuestionAsyncTask(String question, int user_id, String date,Class classroom, GetAddQuestion getAddQuestion) {
            this.question = question;
            this.user_id = user_id;
            this.date = date;
            this.classroom = classroom;
            this.getAddQuestion = getAddQuestion;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("question", question);
            dataToSend.put("user_id", Integer.toString(user_id));
            dataToSend.put("date", date);
            dataToSend.put("class_id", Integer.toString(classroom.class_id));
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

    public class SelectQuestionAsyncTask extends AsyncTask<Void, Void, ArrayList<Questionstack>> {
        GetSelection getSelection;
        ArrayList<Questionstack> questionstackArrayList;
        String php = "SelectQuestion.php";
        Class classroom;
        public SelectQuestionAsyncTask(Class classroom, GetSelection getSelection ) {
            this.classroom = classroom;
            this.getSelection = getSelection;
            questionstackArrayList = new ArrayList<>();
        }

        @Override
        protected ArrayList<Questionstack> doInBackground(Void... params) {
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("class_id", classroom.class_id+"");

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
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    line = stringBuilder.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if (reader != null) {
                        try {
                            reader.close(); // Close Reader
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Log.i("custom_check1", line);
                JSONObject jObj = new JSONObject(line);
//                JSONArray noticeArray = new JSONArray(line);
                JSONArray noticeArray = jObj.getJSONArray("questionstack");
                Questionstack questionstack;
                for (int i = 0; i < noticeArray.length(); i++) {
                        JSONObject questionstacks = noticeArray.getJSONObject(i);
                        int noAnswerint;
                        int question_id = questionstacks.getInt("question_id");
                        String question = questionstacks.getString("question");
                        int user_id = questionstacks.getInt("user_id");
                        String date = questionstacks.getString("datequestion");
                        String noAnswer =  questionstacks.getString("numberCount");
                        if(noAnswer!="null"){
                            noAnswerint = Integer.parseInt(noAnswer);
                        }
                        else noAnswerint=0;
                        questionstack = new Questionstack(question_id,question,user_id,date,noAnswerint);
                        questionstackArrayList.add(questionstack);
                        //Log.d("databuff",questionstack.toString());
                }
            } catch (Exception e) {
                Log.e("custom_check2", e.toString());
            }
            return questionstackArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Questionstack> returnedQuestionstack) {
            progressDialog.dismiss();
            getSelection.done(returnedQuestionstack);
            super.onPostExecute(returnedQuestionstack);
        }
    }

    public class AddAnswerAsyncTask extends AsyncTask<Void, Void, Void>{
        int qid;
        String answer;
        String dateanswer;
        GetAddAnswer getAddAnswer;
        String useranswer;
        public AddAnswerAsyncTask(int qid,String answer,String dateanswer,String useranswer,GetAddAnswer getAddAnswer){
            this.qid=qid;
            this.answer=answer;
            this.dateanswer=dateanswer;
            this.useranswer=useranswer;
            this.getAddAnswer=getAddAnswer;

        }
        @Override
        protected Void doInBackground(Void... params) {
            Log.i("answer1",qid+answer+dateanswer+useranswer);
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("question_id", Integer.toString(qid));
            dataToSend.put("answer", answer);
            dataToSend.put("dateanswer", dateanswer);
            dataToSend.put("useranswer", useranswer);
            String returnedAddA = null;
            try {
                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + "AddAnswer.php");
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
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            getAddAnswer.done(answer);
            super.onPostExecute(aVoid);
        }
    }

    public class SelectAnswerAsyncTask extends AsyncTask<Void, Void, ArrayList<Answerstack>>{
        int question_id;
        GetSelectionAnswer getSelectionAnswer;
        ArrayList<Answerstack> answerstackArrayList;
        Class classroom;
        public SelectAnswerAsyncTask(int question_id, Class classroom,GetSelectionAnswer getSelectionAnswer){
            this.question_id = question_id;
            this.classroom = classroom;
            this.getSelectionAnswer=getSelectionAnswer;
            answerstackArrayList = new ArrayList<>();
        }

        @Override
        protected ArrayList<Answerstack> doInBackground(Void... params) {
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("question_id", Integer.toString(question_id));
            dataToSend.put("class_id", Integer.toString(classroom.class_id));

            try {
                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + "SelectAnswer.php");
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
                }finally {
                    if (reader != null) {
                        try {
                            reader.close(); // Close Reader
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Log.i("custom_check12", line);
                JSONObject jObj = new JSONObject(line);
                JSONArray noticeArray = jObj.getJSONArray("answerstack");
                Answerstack answerstack;
                for (int i = 0; i < noticeArray.length(); i++) {
                    JSONObject answerstacks = noticeArray.getJSONObject(i);
                    int answer_id_tosend = 0;
                    String answer_id  = answerstacks.getString("answer_id");
                    String answer     = answerstacks.getString("answer");
                    String dateanswer = answerstacks.getString("dateanswer");
                    String useranswer = answerstacks.getString("useranswer");
                    if(answer_id.equals("null") && answer.equals("null") && dateanswer.equals("null") && useranswer.equals("null")){
                        answer = "No answer";
                        dateanswer = "No data";
                        useranswer = "No data";
                    }
                    else {
                        answer_id_tosend = Integer.parseInt(answer_id);
                    }
                    Log.i("custom_check123",answer+dateanswer+useranswer);
                    answerstack = new Answerstack(answer_id_tosend,answer,dateanswer,useranswer);
                    answerstackArrayList.add(answerstack);
                }
            } catch (Exception e) {
                Log.e("custom_check2", e.toString());
            }
            Log.i("asdasd",answerstackArrayList.toString());
            return answerstackArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Answerstack> answerstacks) {
            getSelectionAnswer.done(answerstacks);
            super.onPostExecute(answerstacks);

        }
    }
}