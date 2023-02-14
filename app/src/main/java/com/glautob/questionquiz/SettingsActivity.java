package com.glautob.questionquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

    /**
     * Lors de la création de la fenêtre de jeu
     * @param savedInstanceState Instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setActionBar();

        getGraphicalItems();

        initialiseActivity();
    }

    /**
     * Lors du démarrage de la fenêtre de jeu
     */
    @Override
    protected void onStart() {
        super.onStart();

        ET_Question.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            /**
             * Lors de la modification du texte
             * Vérifie les données utilisateur pour activer le bouton d'ajout de question
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkUserData();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        Switch_AddQuestion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             * Lors du changement de l'état du switch
             * Affiche ou non le formulaire d'ajout de question
             * @param compoundButton Bouton
             * @param b Etat
             */
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Layout_AddQuestion.setVisibility(LinearLayout.VISIBLE);
                } else {
                    Layout_AddQuestion.setVisibility(LinearLayout.GONE);
                }
            }
        });

        BT_Close.setOnClickListener(new View.OnClickListener() {
            /**
             * Lors du clic sur le bouton de fermeture
             * Ferme la fenêtre sans transition
             * @param view Vue
             */
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
            }
        });

        BT_AddQuestion.setOnClickListener(new View.OnClickListener() {
            /**
             * Lors du clic sur le bouton d'ajout de question
             * Ajoute la question à la base de données et réinitialise le formulaire
             * @param view Vue
             */
            @Override
            public void onClick(View view) {
                if (!ET_Question.getText().toString().isEmpty()) {
                    // Ajoute la question à la DB
                    Question question = new Question(ET_Question.getText().toString(), CB_Response.isChecked() ? 1 : 0);
                    QuestionManager questionManager = new QuestionManager(SettingsActivity.this);
                    questionManager.addQuestion(question, SettingsActivity.this);

                    // Réinitialise les champs
                    ET_Question.setText("");
                    CB_Response.setChecked(false);

                    // informe l'utilisateur de l'ajout de la question
                    Toast.makeText(SettingsActivity.this, "Question ajoutée", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Slider_QuestionsSpeed.addOnChangeListener(new Slider.OnChangeListener() {
            /**
             * Lors du changement de la vitesse des questions
             * met à jour la valeur dans les préférences
             * @param slider Slider
             * @param value Valeur
             * @param fromUser Utilisateur
             */
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                sharedPref.edit().putInt("questionSpeed", (int) (value * 1000)).apply();
            }
        });
    }

    /**
     * Gère les actions à effectuer lors du clic sur un item du menu
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

    /**
     * Gère l'affichage du menu
     */
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

    /**
     * Vérifie si les données entrées par l'utilisateur sont valides
     */
    private void checkUserData() {
        BT_AddQuestion.setEnabled(ET_Question.getText().length() > 0);
    }

    /**
     * Récupère les éléments graphiques
     */
    private void getGraphicalItems() {
        Layout_AddQuestion = findViewById(R.id.settings_add_question_layout);
        Switch_AddQuestion = findViewById(R.id.settings_add_question_switch);
        BT_AddQuestion = findViewById(R.id.settings_add_question_bt);
        ET_Question = findViewById(R.id.settings_add_question_question_et);
        CB_Response = findViewById(R.id.settings_add_question_response_cb);
        BT_Close = findViewById(R.id.settings_close_bt);
        Slider_QuestionsSpeed = findViewById(R.id.settings_question_speed_sl);
    }

    /**
     * Initialise l'activité
     */
    private void initialiseActivity() {
        // cache la partie d'ajout de question
        Layout_AddQuestion.setVisibility(LinearLayout.GONE);

        // Récupération des préférences
        sharedPref = getSharedPreferences(this.getPackageName() + "_preferences", MODE_PRIVATE);
        // Récupération de la vitesse des questions
        Slider_QuestionsSpeed.setValue(((float) sharedPref.getInt("questionSpeed", 2000)) / 1000);

        // Désactive le bouton d'ajout de question
        BT_AddQuestion.setEnabled(false);
    }
}