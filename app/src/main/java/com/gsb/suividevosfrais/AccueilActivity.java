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

    //Proprietes de login gerees dans la vue
    private String login;
    private String password;

    /**
     * Creation de la vue
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Global.repServeur = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        // chargement des methodes evenementielles
        cmdLogin_clic(this.login, this.password);
    }


    /**
     * Sur le clic du bouton valider : serialisation
     */
    private synchronized void cmdLogin_clic(final String leLogin, final String lePwd) {
        ((Button) findViewById(R.id.cmdLogin)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //On clear le loginVisiteur au cas ou il resterai une info d'un essai failed
                Global.loginVisiteur.clear();
                //Envoi au serveur pour recup id, nom et prenom du visiteur
                accesDistant = new AccesDistant();
                Log.d("MyLog", "accesDistant: " + accesDistant);
                valoriseProprietes();
                accesDistant.envoi("connexion", convertLoginToJSONArray());

                //A REVOIR!!
                Log.d("EtatLoginVisiteur", "accesDistant" + Global.loginVisiteur);

                do {
                    Log.d("MyLog", "repServeur : " + Global.repServeur);
                    retourActivityPrincipale();
                } while (Global.repServeur == true);
            }
        });
    }

    /**
     * Valorisation des proprietes avec les informations entrees
     */
    private void valoriseProprietes() {
        this.login = ((EditText) findViewById(R.id.txtLogin)).getText().toString();
        this.password = ((EditText) findViewById(R.id.txtPassword)).getText().toString();

        Global.loginVisiteur.add(this.login);
        Global.loginVisiteur.add(this.password);
    }


    /**
     * Retour a l'activite principale (le menu)
     */
    public void retourActivityPrincipale() {
        Intent intent = new Intent(AccueilActivity.this, MainActivity.class);
        startActivity(intent);
        Global.repServeur = false;
    }

    /**
     * Creation d'un JSONArray pour utilisation dans la page serveur PHP
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
