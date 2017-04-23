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

    //Propriétés de login gérées dans la vue
    private String login;
    private String password;
    private Integer idVisiteur;
    private String nomVisiteur;
    private String prenomVisiteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);


        // chargement des méthodes événementielles
        cmdLogin_clic(this.login, this.password);

        Global.loginVisiteur.clear();


    }


    /**
     * Sur le clic du bouton valider : sérialisation
     */
    private synchronized void cmdLogin_clic(final String leLogin, final String lePwd) {
        ((Button) findViewById(R.id.cmdLogin)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //On clear le loginVisiteur au cas où il resterai une info d'un essai failed
                Global.loginVisiteur.clear();
                //Envoi au serveur pour récup id, nom et prénom du visiteur
                accesDistant = new AccesDistant();
                Log.d("MyLog", "accesDistant: " + accesDistant);
                valoriseProprietes();
                accesDistant.envoi("connexion", convertLoginToJSONArray());

                Log.d("MyLog", "onClick:connexion : loginVisiteur.size(): " + Global.loginVisiteur.size());
                //Sérialisation des infos
                //Serializer.serialize(Global.filename, Global.listFraisMois, AccueilActivity.this);
                Log.d("EtatLoginVisiteur", "accesDistant" + Global.loginVisiteur);
                do {
                    retourActivityPrincipale();
                } while (Global.loginVisiteur.size() > 2 && Global.repServeur == true);
            }
        });
    }

    /**
     * Valorisation des propriétés avec les informations affichées
     */
    private void valoriseProprietes() {
        this.login = ((EditText) findViewById(R.id.txtLogin)).getText().toString();
        this.password = ((EditText) findViewById(R.id.txtPassword)).getText().toString();

        Global.loginVisiteur.add(this.login);
        Global.loginVisiteur.add(this.password);

    }


    /**
     * Retour à l'activité principale (le menu)
     */
    public void retourActivityPrincipale() {
        Intent intent = new Intent(AccueilActivity.this, MainActivity.class);
        startActivity(intent);
        Global.repServeur = false;
    }

    /**
     * Création d'un JSONArray pour utilisation dans la page serveur PHP
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
