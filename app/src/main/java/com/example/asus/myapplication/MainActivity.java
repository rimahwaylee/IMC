package com.example.asus.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
String  tag= "Life Cycle Event ";

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(tag, "In  onCreate()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(tag, "In  onPause()");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d(tag, "In  onResume()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(tag, "In  onStop()");
    }

    // La chaîne de caractères par défaut
    private final String defautMsg = "Vous devez cliquer sur le bouton « Calculer l'IMC » pour obtenir un résultat.";

    // La chaîne de caractères de la megafonction
    private final String megaString = "Wahou ! Vous avez un poids parfait !";

    Button envoyer = null;

    Button raz = null;

    EditText poids = null;

    EditText taille = null;

    RadioGroup group = null;

    TextView result = null;

    CheckBox mega = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On récupère toutes les vues dont on a besoin
        envoyer = (Button) findViewById(R.id.calcul);
        raz = (Button) findViewById(R.id.raz);
        taille = (EditText) findViewById(R.id.taille);
        poids = (EditText) findViewById(R.id.poids);
        mega = (CheckBox) findViewById(R.id.mega);
        group = (RadioGroup) findViewById(R.id.group);
        result = (TextView) findViewById(R.id.result);

        // On attribue un listener adapté aux vues qui en ont besoin
        envoyer.setOnClickListener(envoyerListener);
        raz.setOnClickListener(razListener);
        taille.addTextChangedListener(textWatcher);
        poids.addTextChangedListener(textWatcher);
        mega.setOnClickListener(checkedListener);
        Log.d(tag, "In  onCreate()");
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            result.setText(defautMsg);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    // Uniquement pour le bouton "envoyer"
    private OnClickListener envoyerListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (!mega.isChecked()) {
                // Si la megafonction n'est pas activée
                // On récupère la taille
                float tailleValue = Float.valueOf(taille.getText().toString());

                // On récupère le poids
                float poidValue = Float.valueOf(poids.getText().toString());

                // Puis on vérifie que la taille est cohérente
                if (tailleValue == 0)
                    Toast.makeText(MainActivity.this, "Ouups, merci de vérifer votre taille !", Toast.LENGTH_SHORT).show();
                else {
                    // Si l'utilisateur a indiqué que la taille était en centimètres
                    // On vérifie que la Checkbox sélectionnée est la deuxième à l'aide de son identifiant
                    if (group.getCheckedRadioButtonId() == R.id.radio2) {
                        tailleValue = tailleValue / 100;
                    }
                    tailleValue = (float) Math.pow(tailleValue, 2);
                    float imc = poidValue / tailleValue;
                    result.setText("Votre IMC est : " + String.valueOf(imc));
                }
            } else
                result.setText(megaString);
        }
    };

    // Listener du bouton de remise à zéro
    private OnClickListener razListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            poids.getText().clear();
            taille.getText().clear();
            result.setText(defautMsg);
        }
    };

    // Listener du bouton de la megafonction.
    private OnClickListener checkedListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // On remet le texte par défaut si c'était le texte de la megafonction qui était écrit
            if (!((CheckBox) v).isChecked() && result.getText().equals(megaString)) {
                result.setText(defautMsg);
            }
        }
    };
}
