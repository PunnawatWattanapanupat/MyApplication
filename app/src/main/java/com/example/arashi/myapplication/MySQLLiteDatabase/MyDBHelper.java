package com.example.arashi.myapplication.MySQLLiteDatabase;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Ooppo on 8/3/2559.
 */
public class MyDbHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "ActiveLearningQuiz";
    private static final int DB_VERSION = 1;




   // public static final String TABLE_NAME = "quiz_question";
   public static final String TABLE_NAME_QUIZ = "quiz";

//    public static final String COL_NAME = "name";
//    public static final String COL_PIECE_PRICE = "pieceprice";
//    public static final String COL_CAKE_PRICE = "cakeprice";

    public static final String QUIZ_ID = "quiz_id";
    public static final String QUIZ_NAME = "quiz_name";
    public static final String IS_ACTIVE = "is_active";



    public static final String TABLE_NAME_QUIZ_QUESTION = "quiz_question";

    public static final String QUIZ_QUESTION_ID = "quiz_question_id";
    //public static final String QUIZ_ID = "quiz_id";
    public static final String QUIZ_QUESTION_TEXT = "quiz_question_text";


//    public static final String QUIZ_CHOICE = "quiz";
//    public static final String CHOICE_ID = "quiz_question";
//    public static final String IS_ACTIVE = "is_active";
//    public static final String cakeID = "_id";
//    public static final String TABLE_NAME_QUIZ = "quiz_question";
//    // public static final String QUIZ_QUESTION_ID = "quiz_question_id";
//    public static final String QUIZ_QUESTION = "pieceprice";


    public MyDbHelper(Context context) {
        super(context, DB_NAME, null,
                DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db)
    {
//        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + cakeID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + COL_NAME + " TEXT, "
//                + COL_PIECE_PRICE + " INTEGER, "
//                + COL_CAKE_PRICE + " INTEGER);");


        db.execSQL("CREATE TABLE " + TABLE_NAME_QUIZ + " (" + QUIZ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + QUIZ_NAME + " TEXT, "
                + IS_ACTIVE + " INTEGER);");


        db.execSQL("CREATE TABLE " + TABLE_NAME_QUIZ_QUESTION + " (" + QUIZ_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + QUIZ_QUESTION_TEXT + " TEXT);");




//        db.execSQL("INSERT INTO " +
//                TABLE_NAME + " (" + COL_NAME + ", " +
//                COL_PIECE_PRICE
//                + ", " + COL_CAKE_PRICE +
//                ") VALUES ('Chocolate Fudge', 95, 750);");
//        db.execSQL("INSERT INTO " +
//                TABLE_NAME + " (" + COL_NAME + ", " +
//                COL_PIECE_PRICE
//                + ", " + COL_CAKE_PRICE +
//                ") VALUES ('Banana Choc Cake', 55, 500);");
//        db.execSQL("INSERT INTO " +
//                TABLE_NAME + " (" + COL_NAME + ", " +
//                COL_PIECE_PRICE
//                + ", " + COL_CAKE_PRICE +
//                ") VALUES ('Banoffee', 75, 700);");
//        db.execSQL("INSERT INTO " +
//                TABLE_NAME + " (" + COL_NAME + ", " +
//                COL_PIECE_PRICE
//                + ", " + COL_CAKE_PRICE +
//                ") VALUES ('Cheese Cake', 85, 800);");
//        db.execSQL("INSERT INTO " +
//                TABLE_NAME + " (" + COL_NAME + ", " +
//                COL_PIECE_PRICE
//                + ", " + COL_CAKE_PRICE +
//                ") VALUES ('Tiramisu', 85, 800);");


        //createDB QUIZ
//        db.execSQL("CREATE TABLE " +
//                TABLE_NAME_QUIZ +" (quiz_question_id INTEGER PRIMARY KEY AUTOINCREMENT, "
//                        + QUIZ_QUESTION + " TEXT);");

    }


    public void onUpgrade(SQLiteDatabase
                                  db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "
                + TABLE_NAME_QUIZ);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS "
                + TABLE_NAME_QUIZ_QUESTION);
        onCreate(db);
    }
}



