package com.glautob.questionquiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private EditText ET_Joueur1;
    private EditText ET_Joueur2;
    private Button BT_Start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mainToolBar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolBar);

        getGraphicalItems();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setInterface();
        checkUserData();
        enableSecondEditText();

        ET_Joueur1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            /**
             * Lors de la modification du texte
             * Vérifie les données de l'utilisateur pour activer le bouton de démarrage
             * Active le deuxième champ de texte si le premier est rempli
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkUserData();
                enableSecondEditText();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        ET_Joueur2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            /**
             * Lors de la modification du texte
             * Vérifie les données de l'utilisateur pour activer le bouton de démarrage
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkUserData();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        BT_Start.setOnClickListener(new View.OnClickListener() {
            /**
             * Lors du clic sur le bouton de démarrage
             * Lance une activité de jeu
             * @param view Vue
             */
            @Override
            public void onClick(View view) {
                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                gameActivity.putExtra("nom_joueur1", ET_Joueur1.getText().toString());
                gameActivity.putExtra("nom_joueur2", ET_Joueur2.getText().toString());
                startActivity(gameActivity);
            }
        });
    }

    /**
     * Vérifie si les champs de saisie des noms des joueurs sont remplis
     * et active le bouton de démarrage de la partie si c'est le cas
     */
    private void checkUserData() {
        BT_Start.setEnabled(ET_Joueur1.getText().length() > 0 && ET_Joueur2.getText().length() > 0);
    }

    /**
     * Active le champ de saisie du nom du joueur 2 si le nom du joueur 1 est renseigné
     */
    private void enableSecondEditText() {
        ET_Joueur2.setEnabled(ET_Joueur1.getText().length() > 0);
    }

    /**
     * Création du menu
     *
     * @param menu le menu à créer
     * @return true
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
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
            case R.id.menu_settings:
                Intent settingsActivity = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsActivity);
                return true;
            case R.id.menu_about:
                openDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Ouvre une boite de dialogue pour l'affichage du A propos
     */
    public void openDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle(R.string.about_title);

        String versionName = BuildConfig.VERSION_NAME;

        alertDialog.setMessage(getString(R.string.about_presentation_text) + versionName);

        alertDialog.setPositiveButton(R.string.about_validation,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    /**
     * Initialise l'interface
     */
    private void setInterface() {
        BT_Start.setEnabled(false);
        ET_Joueur2.setEnabled(false);
    }

    /**
     * Récupère les éléments graphiques
     */
    private void getGraphicalItems() {
        ET_Joueur1 = findViewById(R.id.main_joueur1_et);
        ET_Joueur2 = findViewById(R.id.main_joueur2_et);
        BT_Start = findViewById(R.id.main_start_bt);
    }
}