package com.glautob.questionquiz.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.glautob.questionquiz.Models.Question;
import com.glautob.questionquiz.Models.QuestionQuizSQLite;

import java.util.ArrayList;

public class QuestionManager {
    private ArrayList<Question> questionsList = new ArrayList<>();
//    private static int currentQuestionIndex;

    /**
     * Constructeur de la classe QuestionManager
     *
     * @param context contexte de l'application
     */
    public QuestionManager(Context context) {
        questionsList = initQuestionList(context);
    }

//    /**
//     * Constructeur de la classe QuestionManager
//     */
//    public Question getQuestion() {
//        return questionsList.get(currentQuestionIndex);
//    }

    /**
     * Passe à la question suivante
     *
     * @return la question suivante
     */
    public Question getNextQuestion() {
        Question question = questionsList.get((int) (Math.random() * questionsList.size()));

        questionsList.remove(question);
        return question;
    }

    /**
     * Dit s'il reste des questions dans la liste
     *
     * @return true s'il reste des questions, false sinon
     */
    public boolean questionsLeft() {
        return questionsList.size() > 0;
    }

    public void addQuestion(Question question, Context context) {
        addQuestionInDB(question, context);
    }

    /**
     * Initialise une nouvelle liste de questions
     *
     * @param context le contexte de l'application
     */
    public void newQuestionList(Context context) {
        questionsList = initQuestionList(context);
    }

//    /**
//     * Initialise la liste des questions
//     */
//    public void initQuestList() {
//        currentQuestionIndex = -1;
//        questionsList.clear();
//        questionsList.add(new Question("La terre est plate", 0));
//        questionsList.add(new Question("Le soleil tourne autour de la terre", 0));
//        questionsList.add(new Question("La neige est rose", 0));
//        questionsList.add(new Question("L'homme a marché sur la lune", 1));
//        questionsList.add(new Question("La France est un pays d'Europe", 1));
//        questionsList.add(new Question("Le Canada est un pays d'Amérique du Nord", 1));
//        questionsList.add(new Question("Les chiens sont des herbivores", 0));
//    }

    /**
     * Initialise la liste des questions
     *
     * @param context le contexte de l'application
     * @return la liste des questions
     */
    private ArrayList<Question> initQuestionList(Context context) {
        ArrayList<Question> initQuestion = new ArrayList<>();
        QuestionQuizSQLite helper = new QuestionQuizSQLite(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(true, "quiz", new String[]{"idQuiz", "question", "response"}, null, null, null, null, "idQuiz", null);

        while (cursor.moveToNext()) {
            initQuestion.add(new Question(cursor));
        }

        cursor.close();
        db.close();

        return initQuestion;
    }

    /**
     * Ajoute une question dans la base de données
     *
     * @param question la question à ajouter
     * @param context  contexte de l'application
     */
    public void addQuestionInDB(Question question, Context context) {
        QuestionQuizSQLite helper = new QuestionQuizSQLite(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = question.getContentValues();
        values.put("idQuiz", getLastQuestionId(context) + 1);
        try {
            db.insertOrThrow("quiz", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Rcéupère la valeur du plus grand id de la table quiz
     *
     * @param context contexte de l'application
     * @return la valeur du plus grand id de la table quiz
     */
    private int getLastQuestionId(Context context) {
        QuestionQuizSQLite helper = new QuestionQuizSQLite(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(true, "quiz", new String[]{"MAX(idQuiz)"}, null, null, null, null, null, null);

        int lastId = 0;
        while (cursor.moveToNext()) {
            lastId = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return lastId;
    }
}
