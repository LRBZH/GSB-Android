package com.gsb.suividevosfrais;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by l.r. on 05/04/17.
 */

public class AccueilActivity extends Activity {

    private static AccesDistant accesDistant;

    //Propri�t�s de login g�r�es dans la vue
    private String login;
    private String password;
    private Integer idVisiteur;
    private String nomVisiteur;
    private String prenomVisiteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);


        // chargement des m�thodes �v�nementielles
        cmdLogin_clic(this.login, this.password);


    }


    /**
     * Sur le clic du bouton valider : s�rialisation
     */
    private void cmdLogin_clic(final String leLogin, final String lePwd) {
        ((Button) findViewById(R.id.cmdLogin)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //Envoi au serveur pour r�cup id, nom et pr�nom du visiteur
                accesDistant = new AccesDistant();
                Log.d("MyLog", "accesDistant: " + accesDistant);
                valoriseProprietes();

                accesDistant.envoi("connexion", convertLoginToJSONArray());

                //S�rialisation des infos
                Serializer.serialize(Global.filename, Global.listFraisMois, AccueilActivity.this);
                Log.d("MyLog", "onClick:fin, login OK " + Global.loginVisiteur);
                retourActivityPrincipale();

            }
        });
    }

    /**
     * Valorisation des propri�t�s avec les informations affich�es
     */
    private void valoriseProprietes() {
        this.login = ((EditText) findViewById(R.id.txtLogin)).getText().toString();
        this.password = ((EditText) findViewById(R.id.txtPassword)).getText().toString();

        Global.loginVisiteur.add(this.login);
        Global.loginVisiteur.add(this.password);

        Log.d("MyLog", "valoriseProprietes, valeurs saisies : " + this.login + "." + this.password);

    }


    /**
     * Retour � l'activit� principale (le menu)
     */
    private void retourActivityPrincipale() {
        Intent intent = new Intent(AccueilActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Cr�ation d'un JSONArray pour utilisation dans la page serveur PHP
     *
     * @return un JSONArray
     */
    public JSONArray convertLoginToJSONArray() {
        List liste = new ArrayList();
        //infos visiteur
        liste.add(this.login);
        liste.add(this.password);

        return new JSONArray(liste);
    }

}
