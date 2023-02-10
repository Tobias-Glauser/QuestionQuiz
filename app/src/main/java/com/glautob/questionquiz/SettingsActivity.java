package com.glautob.questionquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.glautob.questionquiz.Controllers.QuestionManager;
import com.glautob.questionquiz.Models.Question;
import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {

    private LinearLayout Layout_AddQuestion;
    private SwitchMaterial Switch_AddQuestion;
    private Button BT_AddQuestion;
    private EditText ET_Question;
    private CheckBox CB_Response;
    private Button BT_Close;
    private Slider Slider_QuestionsSpeed;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setActionBar();

        Layout_AddQuestion = findViewById(R.id.settings_add_question_layout);
        Switch_AddQuestion = findViewById(R.id.settings_add_question_switch);
        BT_AddQuestion = findViewById(R.id.settings_add_question_bt);
        ET_Question = findViewById(R.id.settings_add_question_question_et);
        CB_Response = findViewById(R.id.settings_add_question_response_cb);
        BT_Close = findViewById(R.id.settings_close_bt);
        Slider_QuestionsSpeed = findViewById(R.id.settings_question_speed_sl);

        // cache la partie d'ajout de question
        Layout_AddQuestion.setVisibility(LinearLayout.GONE);

        // Récupération des préférences
        sharedPref = getSharedPreferences(this.getPackageName() + "_preferences", MODE_PRIVATE);
        // Récupération de la vitesse des questions
        Slider_QuestionsSpeed.setValue(((float) sharedPref.getInt("questionSpeed", 2000)) / 1000);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Switch_AddQuestion.setOnCheckedChangeListener((compoundButton, b) -> {
            // affiche ou cahce le layout d'ajout de question
            if (b) {
                Layout_AddQuestion.setVisibility(LinearLayout.VISIBLE);
            } else {
                Layout_AddQuestion.setVisibility(LinearLayout.GONE);
            }
        });

        BT_Close.setOnClickListener(View -> {
            // ferme la fenêtre en désactivant la transition
            finish();
            overridePendingTransition(0, 0);
        });

        BT_AddQuestion.setOnClickListener(View -> {
            if (!ET_Question.getText().toString().isEmpty()) {
                // Ajoute la question à la DB
                Question question = new Question(ET_Question.getText().toString(), CB_Response.isChecked() ? 1 : 0);
                QuestionManager questionManager = new QuestionManager(this);
                questionManager.addQuestion(question, this);

                // Réinitialise les champs
                ET_Question.setText("");
                CB_Response.setChecked(false);
            }
        });

        Slider_QuestionsSpeed.addOnChangeListener((slider, value, fromUser) -> {
            // Sauvegarde la vitesse des questions
            sharedPref.edit().putInt("questionSpeed", (int) (value * 1000)).apply();
        });
    }

    /**
     * Gère les actions à effectuer lors du clic sur un item du menu
     *
     * @param item l'item du menu sur lequel on a cliqué
     * @return true si l'action a été traitée, false sinon
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(0, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setActionBar() {
        Toolbar settingsToolBar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(settingsToolBar);
        try {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(R.string.settings_title_text);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }
}