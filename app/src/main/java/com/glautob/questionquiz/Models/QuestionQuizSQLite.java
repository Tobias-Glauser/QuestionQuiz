package com.glautob.questionquiz.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuestionQuizSQLite extends SQLiteOpenHelper {

    static String DB_NAME = "QuestionQuiz.db";
    static int DB_VERSION = 1;

    /**
     * Constructeur de la classe QuestionQuizSQLite
     * @param context contexte de l'application
     */
    public QuestionQuizSQLite(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Création de la base de données
     * @param db base de données
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateDatabase = "CREATE TABLE quiz(idQuiz INTEGER PRIMARY KEY, question TEXT, response INTEGER);";
        db.execSQL(sqlCreateDatabase);
        db.execSQL("INSERT INTO quiz VALUES(1, \"La capitale de l'australie est Sidney\", 0);");
        db.execSQL("INSERT INTO quiz VALUES(2, \"La terre est plate\", 0);");
        db.execSQL("INSERT INTO quiz VALUES(3, \"Le soleil tourne autour de la terre\", 0);");
        db.execSQL("INSERT INTO quiz VALUES(4, \"L'homme a marché sur la lune\", 1);");
        db.execSQL("INSERT INTO quiz VALUES(5, \"La France est un pays d'Europe\", 1);");
        db.execSQL("INSERT INTO quiz VALUES(6, \"Le Canada est un pays d'Amérique du Nord\", 1);");
        db.execSQL("INSERT INTO quiz VALUES(7, \"Les chiens sont des herbivores\", 0);");
    }

    /**
     * Mise à jour de la base de données
     * @param sqLiteDatabase base de données
     * @param i ancienne version
     * @param i1 nouvelle version
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
