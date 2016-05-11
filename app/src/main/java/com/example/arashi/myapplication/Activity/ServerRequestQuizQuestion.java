package com.example.arashi.myapplication.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.arashi.myapplication.Object.Announcement;
import com.example.arashi.myapplication.Object.Class;
import com.example.arashi.myapplication.Object.Question;
import com.example.arashi.myapplication.Object.Quiz;
import com.example.arashi.myapplication.Object.Roster;
import com.example.arashi.myapplication.Object.StudentQuiz;
import com.example.arashi.myapplication.Object.User;
import com.example.arashi.myapplication.Store.ClassLocalStore;
import com.example.arashi.myapplication.Store.UserLocalStore;

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
    public void fetchQuizDataInBackground(Quiz quiz, GetQuizCallback quizCallback){
        progressDialog.show();
        new FetchQuizDataAsyncTask(quiz, quizCallback).execute();
    }
    public void showQuizDataInBackground(Quiz quiz, String php_file, GetShowQuizCallback showQuizCallback){
        progressDialog.show();
        new ShowQuizDataAsyncTask(quiz, php_file, showQuizCallback).execute();
    }
    public void updateQuizDataInBackground(Quiz quiz, GetQuizCallback quizCallback){
        progressDialog.show();
        new UpdateQuizDataAsyncTask(quiz, quizCallback).execute();
    }
    public void storeQuizQuestionDataInBackground(Question question, GetQuestionCallback getQuestionCallback){
        progressDialog.show();
        new StoreQuizQuestionDataAsyncTask(question, getQuestionCallback).execute();
    }

    public void fetchQuizQuestionDataInBackground(Question question, GetQuestionCallback getQuestionCallback){
        progressDialog.show();
        new FetchQuizQuestionDataAsyncTask(question, getQuestionCallback).execute();
    }

    public void checkLastDataInBackground(Question question, GetQuestionCallback getQuestionCallback){
        progressDialog.show();
        new CheckLastDataAsyncTask(question, getQuestionCallback).execute();
    }

    public void checkStudentQuizInBackground(StudentQuiz studentQuiz, GetStudentQuizCallback getStudentQuizCallback){
        progressDialog.show();
        new checkStudentQuizAsyncTask(studentQuiz, getStudentQuizCallback).execute();
    }

    public void updateQuizQuestionDataInBackground(Question question,  GetQuestionCallback getQuestionCallback){
        progressDialog.show();
        new UpdateQuizQuestionDataAsyncTask(question, getQuestionCallback).execute();
    }
    public void showQuizQuestionDataInBackground(Question question, GetShowQuestionCallback getShowQuestionCallback){
        progressDialog.show();
        new ShowQuizQuestionDataAsyncTask(question, getShowQuestionCallback).execute();
    }

    public void storeStudentQuizDataInBackground(StudentQuiz studentQuiz, String phpFile, GetStudentQuizCallback getStudentQuizCallback){
        progressDialog.show();
        new StoreStudentQuizDataAsyncTask(studentQuiz, phpFile, getStudentQuizCallback).execute();
    }
    public void deleteStudentQuizDataInBackground(StudentQuiz studentQuiz, GetStudentQuizCallback getStudentQuizCallback){
        progressDialog.show();
        new deleteStudentQuizDataAsyncTask(studentQuiz, getStudentQuizCallback).execute();
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
            dataToSend.put("quizname", quiz.quiz_name);
            dataToSend.put("is_active", ""+ quiz.is_active);
            dataToSend.put("class_id", ""+ quiz.class_id);

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
                Log.i("custom_check1", line);

                JSONObject jObj = new JSONObject(line);

                if (jObj.length() != 0) {
                    String quiz_name = jObj.getString("quizname");
                    int quizID = jObj.getInt("quiz_id");
                    int is_active = jObj.getInt("is_active");
                    int class_id=  jObj.getInt("class_id");

                    returnQuiz = new Quiz(quiz_name,quizID,is_active,class_id);
                }
            } catch (Exception e) {
                Log.e("custom_check2_storeQ", e.toString());
            }

            return returnQuiz;
        }


        protected void onPostExecute(Quiz returnQuiz){
            progressDialog.dismiss();
            quizCallback.done(returnQuiz);
            super.onPostExecute(returnQuiz);
        }
    }
    public class FetchQuizDataAsyncTask extends AsyncTask<Void, Void, Quiz> {
        Quiz quiz;
        GetQuizCallback quizCallback;

        public FetchQuizDataAsyncTask(Quiz quiz, GetQuizCallback quizCallback){
            this.quiz = quiz;
            this.quizCallback = quizCallback;

        }
        @Override
        protected Quiz doInBackground(Void... params){
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("quizname", quiz.quiz_name);
            dataToSend.put("class_id", ""+ quiz.class_id);

            Quiz returnQuiz = null;

            try {

                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + "FetchQuizData.php" );
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
                Log.i("custom_check1", line);

                JSONObject jObj = new JSONObject(line);

                if (jObj.length() != 0) {
                    String quiz_name = jObj.getString("quizname");
                    int quizID = jObj.getInt("quiz_id");
                    int is_active = jObj.getInt("is_active");
                    int class_id=  jObj.getInt("class_id");

                    returnQuiz = new Quiz(quiz.quiz_name,quizID,is_active,quiz.class_id);
                }
            } catch (Exception e) {
                Log.e("custom_check2FetchQ", e.toString());
            }

            return returnQuiz;
        }


        protected void onPostExecute(Quiz returnQuiz){
            progressDialog.dismiss();
            quizCallback.done(returnQuiz);
            super.onPostExecute(returnQuiz);
        }
    }

    public class ShowQuizDataAsyncTask extends AsyncTask<Void, Void, ArrayList<Quiz>> {

        GetShowQuizCallback getShowQuizCallback;
        ArrayList<Quiz> showQuiz;
        Quiz quiz;
        String php_file;


        public ShowQuizDataAsyncTask(Quiz quiz, String php_file,  GetShowQuizCallback getShowQuizCallback) {
            this.quiz = quiz;
            this.getShowQuizCallback = getShowQuizCallback;
            showQuiz = new ArrayList<>();
            this.php_file = php_file;

        }
        @Override
        protected ArrayList<Quiz> doInBackground(Void... params){
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("class_id", ""+ quiz.class_id);


            try {

                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + php_file);
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
                JSONArray noticeArray = jObj.getJSONArray("show_quiz");
                Quiz quiz;
                for (int i = 0; i < noticeArray.length(); i++) {
                    JSONObject quiztext = noticeArray.getJSONObject(i);
                    int quiz_id = quiztext.getInt("quiz_id");
                    int class_id = quiztext.getInt("class_id");
                    int is_active = quiztext.getInt("is_active");
                    String quiz_name = quiztext.getString("quizname");


                    quiz = new Quiz (quiz_name,quiz_id,is_active,class_id);
                    showQuiz.add(quiz);

                }
            } catch (Exception e) {
                Log.e("custom_check", e.toString());
            }

            return showQuiz;
        }


        protected void onPostExecute(ArrayList<Quiz> returnedQuiz){
            progressDialog.dismiss();
            getShowQuizCallback.done(returnedQuiz);
            super.onPostExecute(returnedQuiz);
        }
    }

    public class UpdateQuizDataAsyncTask extends AsyncTask<Void, Void, Quiz> {
        Quiz quiz;
        GetQuizCallback quizCallback;

        public UpdateQuizDataAsyncTask(Quiz quiz, GetQuizCallback quizCallback){
            this.quiz = quiz;
            this.quizCallback = quizCallback;

        }
        @Override
        protected Quiz doInBackground(Void... params){
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("is_active", quiz.is_active+"");
            dataToSend.put("quiz_id", ""+ quiz.quizID);
            dataToSend.put("class_id", ""+ quiz.class_id);

            Quiz returnQuiz = null;

            try {

                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + "Update_Quiz.php" );
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
                Log.i("custom_check1", line);

                JSONObject jObj = new JSONObject(line);

                if (jObj.length() != 0) {
                    int quizID = jObj.getInt("quiz_id");
                    int is_active = jObj.getInt("is_active");
                    int class_id=  jObj.getInt("class_id");
                    returnQuiz = new Quiz(quizID,is_active,class_id);
                }
            } catch (Exception e) {
                Log.e("custom_check888", e.toString());
            }

            return returnQuiz;
        }


        protected void onPostExecute(Quiz returnQuiz){
            progressDialog.dismiss();
            quizCallback.done(returnQuiz);
            super.onPostExecute(returnQuiz);
        }
    }
    public class StoreQuizQuestionDataAsyncTask extends AsyncTask<Void, Void, Question> {
        Question quiz_question;
        GetQuestionCallback questionCallback;

        public StoreQuizQuestionDataAsyncTask(Question quiz_question, GetQuestionCallback questionCallback){
            this.quiz_question = quiz_question;
            this.questionCallback = questionCallback;

        }
        @Override
        protected Question doInBackground(Void... params){
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("quizquestion_text", quiz_question.question);
            dataToSend.put("choice_a",  quiz_question.ans1);
            dataToSend.put("choice_b",  quiz_question.ans2);
            dataToSend.put("choice_c", quiz_question.ans3);
            dataToSend.put("choice_d",  quiz_question.ans4);
            dataToSend.put("correct_choice", quiz_question.correctaAnswer);
            dataToSend.put("quiz_id", ""+ quiz_question.quiz_id);
            dataToSend.put("number_question", ""+ quiz_question.numberQuestion);

            Question returnQuestion = null;

            try {

                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + "Post_Quiz_Question.php" );
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
                Log.i("custom_check1", line);

                JSONObject jObj = new JSONObject(line);

                if (jObj.length() != 0) {
                    String quizquestion_text = jObj.getString("quizquestion_text");
                    String ans1 = jObj.getString("choice_a");
                    String ans2 = jObj.getString("choice_b");
                    String ans3 = jObj.getString("choice_c");
                    String ans4 = jObj.getString("choice_d");
                    String correctaAnswer = jObj.getString("correct_choice");
                    int quiz_id =  jObj.getInt("quiz_id");
                    int numberQuestion = jObj.getInt("number_question");

                    returnQuestion = new Question( quizquestion_text, ans1, ans2, ans3, ans4, correctaAnswer, quiz_id, numberQuestion);
                }
            } catch (Exception e) {
                Log.e("custom_check2_storeQQ", e.toString());
            }

            return returnQuestion;
        }


        protected void onPostExecute(Question returnQuestion){
            progressDialog.dismiss();
            questionCallback.done(returnQuestion);
            super.onPostExecute(returnQuestion);
        }
    }

    public class FetchQuizQuestionDataAsyncTask extends AsyncTask<Void, Void, Question> {
        Question quiz_question;
        GetQuestionCallback questionCallback;

        public FetchQuizQuestionDataAsyncTask(Question quiz_question, GetQuestionCallback questionCallback){
            this.quiz_question = quiz_question;
            this.questionCallback = questionCallback;

        }
        @Override
        protected Question doInBackground(Void... params){
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("quiz_id", ""+ quiz_question.quiz_id);
            dataToSend.put("number_question", ""+ quiz_question.numberQuestion);

            Question returnQuestion = null;

            try {

                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + "FetchQuizQuestionData.php" );
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
                Log.i("custom_check1", line);

                JSONObject jObj = new JSONObject(line);

                if (jObj.length() != 0) {
                    int quizquestionpack_id = jObj.getInt("quizquestionpack_id");
                    String quizquestion_text = jObj.getString("quizquestion_text");
                    String ans1 = jObj.getString("choice_a");
                    String ans2 = jObj.getString("choice_b");
                    String ans3 = jObj.getString("choice_c");
                    String ans4 = jObj.getString("choice_d");

                    returnQuestion = new Question(quizquestionpack_id, quizquestion_text, ans1, ans2, ans3, ans4);
                }
            } catch (Exception e) {
                Log.e("custom_check2_UpdateFQ", e.toString());
            }

            return returnQuestion;
        }


        protected void onPostExecute(Question returnQuestion){
            progressDialog.dismiss();
            questionCallback.done(returnQuestion);
            super.onPostExecute(returnQuestion);
        }
    }


    public class CheckLastDataAsyncTask extends AsyncTask<Void, Void, Question> {
        Question quiz_question;
        GetQuestionCallback questionCallback;

        public CheckLastDataAsyncTask(Question quiz_question, GetQuestionCallback questionCallback){
            this.quiz_question = quiz_question;
            this.questionCallback = questionCallback;

        }
        @Override
        protected Question doInBackground(Void... params){
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("quiz_id", ""+ quiz_question.quiz_id);

            Question returnQuestion = null;

            try {

                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + "FetchCheckLastQuizQuestionData.php" );
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
                Log.i("custom_check1", line);

                JSONObject jObj = new JSONObject(line);

                if (jObj.length() != 0) {
                    int quiz_id = jObj.getInt("quiz_id");
                    int count_question = jObj.getInt("count_question");

                    returnQuestion = new Question(quiz_id, count_question);
                }
            } catch (Exception e) {
                Log.e("custom_check2_UpdateFQ", e.toString());
            }

            return returnQuestion;
        }


        protected void onPostExecute(Question returnQuestion){
            progressDialog.dismiss();
            questionCallback.done(returnQuestion);
            super.onPostExecute(returnQuestion);
        }
    }

    public class checkStudentQuizAsyncTask extends AsyncTask<Void, Void, StudentQuiz> {
        StudentQuiz studentQuiz;
        GetStudentQuizCallback getStudentQuizCallback;

        public checkStudentQuizAsyncTask(StudentQuiz studentQuiz, GetStudentQuizCallback getStudentQuizCallback){
            this.studentQuiz = studentQuiz;
            this.getStudentQuizCallback = getStudentQuizCallback;

        }
        @Override
        protected StudentQuiz doInBackground(Void... params){
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("quiz_id", ""+ studentQuiz.quiz_id);
            dataToSend.put("user_id", ""+ studentQuiz.user_id);
            StudentQuiz returnStudentQuiz = null;

            try {

                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + "FetchCheckStudentQuiz.php" );
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
                Log.i("custom_check1", line);

                JSONObject jObj = new JSONObject(line);

                if (jObj.length() != 0) {
                    int quiz_id = jObj.getInt("quiz_id");
                    int user_id = jObj.getInt("user_id");
                    int student_score = jObj.getInt("student_score");

                    returnStudentQuiz = new StudentQuiz(quiz_id, user_id, student_score);
                }
            } catch (Exception e) {
                Log.e("custom_check2_UpdateFQ", e.toString());
            }

            return returnStudentQuiz;
        }


        protected void onPostExecute(StudentQuiz returnQuestion){
            progressDialog.dismiss();
            getStudentQuizCallback.done(returnQuestion);
            super.onPostExecute(returnQuestion);
        }
    }


    public class UpdateQuizQuestionDataAsyncTask extends AsyncTask<Void, Void, Question> {
        Question quiz_question;
        GetQuestionCallback questionCallback;

        public UpdateQuizQuestionDataAsyncTask(Question quiz_question, GetQuestionCallback questionCallback){
            this.quiz_question = quiz_question;
            this.questionCallback = questionCallback;

        }
        @Override
        protected Question doInBackground(Void... params){
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("quizquestionpack_id", quiz_question.quizquestionpack_id+"");
            dataToSend.put("quizquestion_text", quiz_question.question);
            dataToSend.put("choice_a",  quiz_question.ans1);
            dataToSend.put("choice_b",  quiz_question.ans2);
            dataToSend.put("choice_c", quiz_question.ans3);
            dataToSend.put("choice_d",  quiz_question.ans4);
            dataToSend.put("correct_choice", quiz_question.correctaAnswer);
            dataToSend.put("quiz_id", ""+ quiz_question.quiz_id);
            dataToSend.put("number_question", ""+ quiz_question.numberQuestion);

            Question returnQuestion = null;

            try {

                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + "Update_Quiz_Question.php" );
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
                Log.i("custom_check1", line);

                JSONObject jObj = new JSONObject(line);

                if (jObj.length() != 0) {
                    String quizquestion_text = jObj.getString("quizquestion_text");
                    String ans1 = jObj.getString("choice_a");
                    String ans2 = jObj.getString("choice_b");
                    String ans3 = jObj.getString("choice_c");
                    String ans4 = jObj.getString("choice_d");
                    String correctaAnswer = jObj.getString("correct_choice");
                    int quiz_id =  jObj.getInt("quiz_id");
                    int numberQuestion = jObj.getInt("number_question");

                    returnQuestion = new Question(quizquestion_text, ans1, ans2, ans3, ans4, correctaAnswer, quiz_id , numberQuestion);
                }
            } catch (Exception e) {
                Log.e("custom_check2_UpdateQQ", e.toString());
            }

            return returnQuestion;
        }


        protected void onPostExecute(Question returnQuestion){
            progressDialog.dismiss();
            questionCallback.done(returnQuestion);
            super.onPostExecute(returnQuestion);
        }
    }

    public class ShowQuizQuestionDataAsyncTask extends AsyncTask<Void, Void, ArrayList<Question>> {

        GetShowQuestionCallback getShowQuestionCallback;
        ArrayList<Question> showQuestion;
        Question question;


        public ShowQuizQuestionDataAsyncTask(Question question,  GetShowQuestionCallback getShowQuestionCallback) {
            this.question = question;
            this.getShowQuestionCallback = getShowQuestionCallback;
            showQuestion = new ArrayList<>();

        }
        @Override
        protected ArrayList<Question> doInBackground(Void... params){
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("quiz_id", ""+ question.quiz_id);


            try {

                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + "showQuizQuestion.php");
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
                JSONArray noticeArray = jObj.getJSONArray("show_quiz_question");
                Question question;
                for (int i = 0; i < noticeArray.length(); i++) {
                    JSONObject quiz_question = noticeArray.getJSONObject(i);
                    int quizquestion_id = quiz_question.getInt("quizquestionpack_id");
                    String quizquestion_text = quiz_question.getString("quizquestion_text");
                    String ans1 = quiz_question.getString("choice_a");
                    String ans2 = quiz_question.getString("choice_b");
                    String ans3 = quiz_question.getString("choice_c");
                    String ans4 = quiz_question.getString("choice_d");
                    String correctAnswer = quiz_question.getString("correct_choice");

                    question = new Question(quizquestion_id,quizquestion_text, ans1, ans2, ans3, ans4,correctAnswer);
                    showQuestion.add(question);
                }
            } catch (Exception e) {
                Log.e("custom_check", e.toString());
            }

            return showQuestion;
        }


        protected void onPostExecute(ArrayList<Question> returnedQuestion){
            progressDialog.dismiss();
            getShowQuestionCallback.done(returnedQuestion);
            super.onPostExecute(returnedQuestion);
        }
    }

    public class StoreStudentQuizDataAsyncTask extends AsyncTask<Void, Void, StudentQuiz> {
        StudentQuiz studentQuiz;
        String phpFile;
        GetStudentQuizCallback getStudentQuizCallback;

        public StoreStudentQuizDataAsyncTask(StudentQuiz studentQuiz, String phpFile,GetStudentQuizCallback getStudentQuizCallback){
            this.studentQuiz = studentQuiz;
            this.phpFile = phpFile;
            this.getStudentQuizCallback = getStudentQuizCallback;

        }
        @Override
        protected StudentQuiz doInBackground(Void... params){
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("user_id", studentQuiz.user_id+"");
            dataToSend.put("quiz_id", studentQuiz.quiz_id+"");
            dataToSend.put("quizquestionpack_id",  studentQuiz.quizquestionpack_id+"");
            dataToSend.put("student_answer",  studentQuiz.student_answer);

            StudentQuiz returnStudentQuiz = null;

            try {

                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + phpFile );
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
                Log.i("custom_check1", line);

                JSONObject jObj = new JSONObject(line);

                if (jObj.length() != 0) {
                    String student_answer = jObj.getString("student_answer");
                    int quiz_id =  jObj.getInt("quiz_id");
                    int quizquestionpack_id = jObj.getInt("quizquestionpack_id");
                    int user_id =  jObj.getInt("user_id");

                    returnStudentQuiz = new StudentQuiz( student_answer, quiz_id, quizquestionpack_id, user_id);
                }
            } catch (Exception e) {
                Log.e("custom_check2_storeSQQ", e.toString());
            }

            return returnStudentQuiz;
        }


        protected void onPostExecute(StudentQuiz returnQuestion){
            progressDialog.dismiss();
            getStudentQuizCallback.done(returnQuestion);
            super.onPostExecute(returnQuestion);
        }
    }

    public class deleteStudentQuizDataAsyncTask extends AsyncTask<Void, Void, StudentQuiz> {
        StudentQuiz studentQuiz;
        String phpFile;
        GetStudentQuizCallback getStudentQuizCallback;

        public deleteStudentQuizDataAsyncTask(StudentQuiz studentQuiz, GetStudentQuizCallback getStudentQuizCallback){
            this.studentQuiz = studentQuiz;
            this.getStudentQuizCallback = getStudentQuizCallback;

        }
        @Override
        protected StudentQuiz doInBackground(Void... params){
            Map<String, String> dataToSend = new HashMap<>();
            dataToSend.put("quiz_id", studentQuiz.quiz_id+"");
            dataToSend.put("user_id", studentQuiz.user_id+"");

            StudentQuiz returnStudentQuiz = null;

            try {

                String encode = getEncodeData(dataToSend);
                BufferedReader reader = null; // Read some data from server
                String line = "";

                try {
                    URL url = new URL(SERVER_ADDRESS + "Delete_student_Quiz.php" );
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
                Log.i("custom_check1", line);

                JSONObject jObj = new JSONObject(line);

                if (jObj.length() != 0) {
//                    String student_answer = jObj.getString("student_answer");
                    int quiz_id =  jObj.getInt("quiz_id");
//                    int quizquestionpack_id = jObj.getInt("quizquestionpack_id");
//                    int user_id =  jObj.getInt("user_id");

                    returnStudentQuiz = new StudentQuiz(quiz_id);
                }
            } catch (Exception e) {
                Log.e("custom_check2_storeSQQ", e.toString());
            }

            return returnStudentQuiz;
        }


        protected void onPostExecute(StudentQuiz returnQuestion){
            progressDialog.dismiss();
            getStudentQuizCallback.done(returnQuestion);
            super.onPostExecute(returnQuestion);
        }
    }
}
