package com.glautob.questionquiz.Models;

import android.content.ContentValues;
import android.database.Cursor;

public class Question {
    private String question;
    private int response;

    /**
     * Constructeur de la classe Question
     * @param question 'intitulé de la question
     * @param response la réponse à la question
     */
    public Question(String question, int response) {
        this.setQuestion(question);
        this.setResponse(response);
    }

    /**
     * Constructeur de la classe Question avec un curseur
     * @param cursor curseur
     */
    public Question(Cursor cursor) {
        question = cursor.getString(cursor.getColumnIndexOrThrow("question"));
        response = cursor.getInt(cursor.getColumnIndexOrThrow("response"));
    }

    /**
     * @return L'intitule de la question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Initialise l'intitulé de la question
     * @param question de la question
     */
    public void setQuestion(String question) {
        if (!question.isEmpty()) {
            this.question = question;
        } else {
            this.question = "Question vide";
        }
    }

    /**
     * @return la réponse de la question
     */
    public int getResponse() {
        return response;
    }

    /**
     * Initialise la réponse de la question
     * @param response de la question
     */
    public void setResponse(int response) {
        this.response = response;
    }

    /**
     * @return les valeurs de la question sous forme de ContentValues
     * pour l'insertion dans la base de données
     */
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put("idQuiz", 0);
        values.put("question", question);
        values.put("response", response);
        return values;
    }
}
