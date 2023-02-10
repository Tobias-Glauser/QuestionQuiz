package com.glautob.questionquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.glautob.questionquiz.Controllers.QuestionManager;
import com.glautob.questionquiz.Models.Question;

public class GameActivity extends AppCompatActivity {

    Runnable questionRunnable = null;
    Handler handler;
    private TextView TV_Joueur1;
    private TextView TV_Joueur2;
    private TextView TV_PointsJoueur1;
    private TextView TV_PointsJoueur2;
    private TextView TV_Question_Joueur1;
    private TextView TV_Question_Joueur2;
    private TextView TV_Coutdown;
    private Button BT_Joueur1;
    private Button BT_Joueur2;
    private Button BT_Rejouer;
    private Button BT_Menu;
    private LinearLayout Layout_MenuButtons;
    private QuestionManager questionManager;
    private Question actualQuestion;
    private int questionSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TV_Joueur1 = findViewById(R.id.game_joueur1_tv);
        TV_Joueur2 = findViewById(R.id.game_joueur2_tv);
        TV_PointsJoueur1 = findViewById(R.id.game_score_joueur1_tv);
        TV_PointsJoueur2 = findViewById(R.id.game_score_joueur2_tv);
        TV_Question_Joueur1 = findViewById(R.id.game_question_joueur1_tv);
        TV_Question_Joueur2 = findViewById(R.id.game_question_joueur2_tv);
        TV_Coutdown = findViewById(R.id.game_countdown_tv);
        BT_Joueur1 = findViewById(R.id.game_joueur1_bt);
        BT_Joueur2 = findViewById(R.id.game_joueur2_bt);
        BT_Rejouer = findViewById(R.id.game_rejouer_bt);
        BT_Menu = findViewById(R.id.game_menu_bt);
        Layout_MenuButtons = findViewById(R.id.game_menu_buttons_layout);

        // Affiche le nom des joueurs
        setPlayersName();

        // Récupération du questionManager
        questionManager = new QuestionManager(this);

        // https://www.tutorialspoint.com/android/android_shared_preferences.htm
        // Récupération des préférences
        SharedPreferences sharedPref = getSharedPreferences(this.getPackageName() + "_preferences", Context.MODE_PRIVATE);
        // Récupération de la vitesse des questions
        questionSpeed = sharedPref.getInt("questionSpeed", 2000);
    }

    @Override
    protected void onStart() {
        super.onStart();

        BT_Joueur1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setJoueurButtonsEnable(false);
                if (actualQuestion.getResponse() == 1) {
                    TV_PointsJoueur1.setText(String.valueOf(Integer.parseInt(TV_PointsJoueur1.getText().toString()) + 1));
                } else {
                    TV_PointsJoueur1.setText(String.valueOf(Integer.parseInt(TV_PointsJoueur1.getText().toString()) - 1));
                }
            }
        });

        BT_Joueur2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setJoueurButtonsEnable(false);
                if (actualQuestion.getResponse() == 1) {
                    TV_PointsJoueur2.setText(String.valueOf(Integer.parseInt(TV_PointsJoueur2.getText().toString()) + 1));
                } else {
                    TV_PointsJoueur2.setText(String.valueOf(Integer.parseInt(TV_PointsJoueur2.getText().toString()) - 1));
                }
            }
        });

        BT_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        BT_Rejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewGame();
            }
        });

        startNewGame();
    }

    /**
     * passe à la question suivante
     */
    private void changeQuestion() {
        actualQuestion = questionManager.getNextQuestion();
        TV_Question_Joueur1.setText(actualQuestion.getQuestion());
        TV_Question_Joueur2.setText(actualQuestion.getQuestion());
        setJoueurButtonsEnable(true);
    }

    /**
     * Active ou désactive les boutons des joueurs
     * @param enable true pour activer, false pour désactiver
     */
    private void setJoueurButtonsEnable(boolean enable) {
        if (enable) {
            BT_Joueur1.setEnabled(true);
            BT_Joueur2.setEnabled(true);
        } else {
            BT_Joueur1.setEnabled(false);
            BT_Joueur2.setEnabled(false);
        }
    }

    /**
     * Démarre un nouvel itérateur de questions
     */
    private void startQuestionIterative() {
        handler = new Handler();

        questionRunnable = new Runnable() {
            @Override
            public void run() {
                TV_Coutdown.setVisibility(View.INVISIBLE);
                if (!questionManager.questionsLeft()) {
                    // exécute le code de fin de partie
                    setJoueurButtonsEnable(false);
                    Layout_MenuButtons.setVisibility(View.VISIBLE);
                    TV_Question_Joueur2.setText(R.string.game_fin_jeu);
                    TV_Question_Joueur1.setText(R.string.game_fin_jeu);
                    handler.removeCallbacks(this);
                } else {
                    // code pour demander et afficher une question
                    changeQuestion();
                    handler.postDelayed(this, questionSpeed);
                }
            }
        };

        handler.postDelayed(questionRunnable, 1000);
    }

    /**
     * Démarre un nouveau compte à rebours
     */
    private void startCountDownTimer() {
        new CountDownTimer(6000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Afficher le compteur à l'utilisateur
                TV_Coutdown.setText(Long.toString(millisUntilFinished / 1000 + 1));
            }

            public void onFinish() {
                // Afficher le départ à l'utilisateur
                TV_Coutdown.setText(R.string.game_lancement_jeu);
                startQuestionIterative();
            }
        }.start();
    }

    /**
     * Démarre une nouvelle partie
     */
    private void startNewGame() {
        questionManager.newQuestionList(this);
        resetInterface();
        startCountDownTimer();
    }

    /**
     * Remet l'interface à zéro
     */
    private void resetInterface() {
        TV_PointsJoueur1.setText("0");
        TV_PointsJoueur2.setText("0");
        TV_Question_Joueur1.setText("");
        TV_Question_Joueur2.setText("");
//        TV_Coutdown.setText("7");
        Layout_MenuButtons.setVisibility(View.INVISIBLE);
        TV_Coutdown.setVisibility(View.VISIBLE);
        setJoueurButtonsEnable(false);
    }

    /**
     * Affiche le nom des joueurs
     */
    private void setPlayersName() {
        Intent gameActivity = getIntent();

        String joueur1 = gameActivity.getStringExtra("nom_joueur1");
        String joueur2 = gameActivity.getStringExtra("nom_joueur2");

        TV_Joueur1.setText(joueur1);
        TV_Joueur2.setText(joueur2);
    }

}