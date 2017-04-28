package com.gsb.suividevosfrais;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private static AccesDistant accesDistant;
    public static final int RESULT_Main = 1; //pour le r�sultat

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Appel de la page de Login
        if (Global.loginVisiteur.size() <= 2) {
            startActivityForResult(new Intent(MainActivity.this, AccueilActivity.class), RESULT_Main);
        }
        setContentView(R.layout.activity_main);
        // r�cup�ration des informations s�rialis�es
        recupSerialize() ;
        // chargement des m�thodes �v�nementielles
        cmdMenu_clic(((Button)findViewById(R.id.cmdKm)), KmActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdRepas)), RepasActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdNuitee)), NuiteeActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdEtape)), EtapeActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdHf)), HfActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdHfRecap)), HfRecapActivity.class) ;
        cmdMenu_clic(((Button) findViewById(R.id.cmdTransfert)), Transfert.class);
        //cmdLogout_clic();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /**
     * R�cup�re la s�rialisation si elle existe
     */
    private void recupSerialize() {
    	Global.listFraisMois = (Hashtable<Integer, FraisMois>) Serializer.deSerialize(Global.filename, MainActivity.this) ;
    	// si rien n'a �t� r�cup�r�, il faut cr�er la liste
    	if (Global.listFraisMois==null) {
    		Global.listFraisMois = new Hashtable<Integer, FraisMois>() ;
    	}
    }

    /**
     * Sur la s�lection d'un bouton dans l'activit� principale ouverture de l'activit� correspondante
     */
    private void cmdMenu_clic(Button button, final Class classe) {
    	button.setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {
    			// ouvre l'activit� 
    			Intent intent = new Intent(MainActivity.this, classe) ;
    			startActivity(intent) ;  			
    		}
    	}) ;
    }


    /**
     * Ecouteur sur le bouton logout
     */
    private void cmdLogout_clic() {
        Button button = (Button) findViewById(R.id.cmdLogout);
        // Cr�ation du listener du bouton cancel (on sort de l'appli)
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                quit(false, null);
            }

        });
    }

    /**
     * M�thode pour sortir de l'application
     *
     * @param success
     * @param i
     */
    public void quit(boolean success, Intent i) {
        // On envoie un r�sultat qui va permettre de quitter l'appli
        Global.loginVisiteur.clear();
        Global.repServeur = false;
        setResult((success) ? Activity.RESULT_OK : Activity.RESULT_CANCELED, i);
        finish();

    }



}
