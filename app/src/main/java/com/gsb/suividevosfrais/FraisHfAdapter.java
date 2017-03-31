package com.gsb.suividevosfrais;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.BaseAdapter;

public class FraisHfAdapter extends BaseAdapter {

	ArrayList<FraisHf> lesFrais ; // liste des frais du mois
	LayoutInflater inflater ;
	Integer key ;  // annee et mois (clé dans la liste)
	Context context ; // contexte pour gérer la sérialisation
	
	/**
	 * Constructeur de l'adapter pour valoriser les propriétés
	 * @param context
	 * @param lesFrais
	 * @param key
	 */
	public FraisHfAdapter(Context context, ArrayList<FraisHf> lesFrais, Integer key) {
		inflater = LayoutInflater.from(context) ;
		this.lesFrais = lesFrais ;
		this.key = key ;
		this.context = context ;
	}
	
	/**
	 * retourne le nombre d'éléments de la listview
	 */
	@Override
	public int getCount() {
		return lesFrais.size() ;
	}

	/**
	 * retourne l'item de la listview à un index précis
	 */
	@Override
	public Object getItem(int index) {
		return lesFrais.get(index) ;
	}

	/**
	 * retourne l'index de l'élément actuel
	 */
	@Override
	public long getItemId(int index) {
		return index;
	}

	/**
	 * structure contenant les éléments d'une ligne
	 */
	private class ViewHolder {
		TextView txtListJour ;
		TextView txtListMontant ;
		TextView txtListMotif ;
		ImageButton btnHfSuppr;
	}
	
	/**
	 * Affichage dans la liste
	 */
	@Override
	public View getView(int index, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder() ;
			convertView = inflater.inflate(R.layout.layout_liste, null) ;
			holder.txtListJour = (TextView)convertView.findViewById(R.id.txtListJour) ;
			holder.txtListMontant = (TextView)convertView.findViewById(R.id.txtListMontant) ;
			holder.txtListMotif = (TextView)convertView.findViewById(R.id.txtListMotif) ;
			holder.btnHfSuppr = (ImageButton) convertView.findViewById(R.id.btnHfSuppr);
			holder.btnHfSuppr.setImageResource(R.drawable.suppr);
			convertView.setTag(holder) ;
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		holder.txtListJour.setText(lesFrais.get(index).getJour().toString()) ;
		holder.txtListMontant.setText(lesFrais.get(index).getMontant().toString()) ;
		holder.txtListMotif.setText(lesFrais.get(index).getMotif()) ;
		holder.btnHfSuppr.setTag(index);


		//paramétrage du bouton Suppr avec un écouteur
		holder.btnHfSuppr.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {//code à la suppression

				//On récupère l'indice de la ligne concernée
				int position = (Integer) v.getTag();
				Log.d("MyLog", "position : " + position);
				//on avertit le controleur qu'il faut supprimer un profil
				Log.d("MyLog", "onclickSuppr : key + " + key);
				Log.d("MyLog", "onclickSuppr : listFraisMois.get(key) + " + Global.listFraisMois.get(key));
				Global.listFraisMois.get(key).getLesFraisHf().remove(position);
				Log.d("MyLog", "suppression OK ");
				//on raffraichit la liste visuelle
				notifyDataSetChanged();
				Log.d("MyLog", "refresh OK ");
			}
		});

		return convertView ;
	}
	
}
