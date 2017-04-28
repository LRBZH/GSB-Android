package com.gsb.suividevosfrais;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by l.r. on 03/04/17.
 */

public class Transfert extends Activity {

    private static AccesDistant accesDistant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfert);

        // chargement des méthodes événementielles
        cmdTransfert(Global.loginVisiteur.get(0), Global.loginVisiteur.get(1));
        imgTransfertReturn_clic();
        Log.d("MyLog", "onCreate: données présentes : " + Global.listFraisMois.size());
    }

    /**
     * A l'ouverture de la vue, le transfert des informations est lancé
     *
     * @param leLogin
     * @param lePwd
     */
    private void cmdTransfert(final String leLogin, final String lePwd) {
        Log.d("MyLog", "transfert, login+pw : " + leLogin + "." + lePwd);
        accesDistant = new AccesDistant();
        Log.d("MyLog", "accesDistant: " + accesDistant);
        Log.d("MyLog", "listFraisMois  " + Global.listFraisMois);
        Log.d("MyLog", "onClick:transfert : listFraisMois.size(): " + Global.listFraisMois.size());

        //parcours de listFraisMois et récup des données au format JSONArray avant envoi

        Log.d("test", "anneMois: " + Global.anneeMois);
        for (int k = (Global.anneeMois - 2); k <= Global.anneeMois; k++) {
            try {
                Log.d("MyLog", "cmdTransfert: Global.listFraisMois.get(k).getLesFraisHf().get(0).getMotif() : " + Global.listFraisMois.get(k).getLesFraisHf().get(0).getMotif());
                accesDistant.envoi("enreg", convertToJSONArray(k));
            } catch (Exception e) {
                Log.d("Exception", "valeur nulle ");
            }


        }

        //Vider les données sérialisées après envoi

    }

    /**
     * Sur la selection de l'image : retour au menu principal
     */
    private void imgTransfertReturn_clic() {
        ((ImageView) findViewById(R.id.imgTransfertReturn)).setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                retourActivityPrincipale();
            }
        });
    }


    /**
     * Retour à l'activité principale (le menu)
     */
    public void retourActivityPrincipale() {
        Intent intent = new Intent(Transfert.this, MainActivity.class);
        startActivity(intent);
        Global.repServeur = false;
    }

    /**
     * Création d'un JSONArray pour utilisation dans la page serveur PHP
     *
     * @return un JSONArray
     */
    public JSONArray convertToJSONArray(Integer k) {
        List liste = new ArrayList();
        //infos visiteur
        Log.d("MyLog", "convertToJSONArray ");
        liste.add(Global.loginVisiteur.get(2));
        Log.d("MyLog", "visiteur : " + Global.loginVisiteur.get(2));
        liste.add(k.toString());//1
        Log.d("MyLog", "mois : " + k.toString());
        liste.add(Global.listFraisMois.get(k).getEtape());//2
        Log.d("MyLog", "etapes :  " + Global.listFraisMois.get(k).getEtape());
        liste.add(Global.listFraisMois.get(k).getKm());//3
        Log.d("MyLog", "km :  " + Global.listFraisMois.get(k).getKm());
        liste.add(Global.listFraisMois.get(k).getNuitee());//4
        Log.d("MyLog", "nuitées " + Global.listFraisMois.get(k).getNuitee());
        liste.add(Global.listFraisMois.get(k).getRepas());//5
        Log.d("MyLog", "repas :  " + Global.listFraisMois.get(k).getRepas());

        Log.d("Affichage des données ", liste.toString());

            /*//parcours des frais HF, à revoir !! (comment les insérer ds la requete ??
            ArrayList<FraisHf> lesFraisHf = Global.listFraisMois.get(k).getLesFraisHf();
            Log.d("MyLog", "lesfraishf longueur : " + lesFraisHf.size());
            for(Integer j = 0; j < lesFraisHf.size(); j++){
                liste.add(Global.listFraisMois.get(k).getLesFraisHf().get(j).getJour());//6
                liste.add(Global.listFraisMois.get(k).getLesFraisHf().get(j).getMontant());//7
                liste.add(Global.listFraisMois.get(k).getLesFraisHf().get(j).getMotif());//8
            }*/


        return new JSONArray(liste);
    }
}
