
/****************************************
 Fichier : CommandeAdapter
 Auteur : Jeremie Gaudet
 Fonctionnalité : Page qui gère la classe Commande en relation avec le layout row_inventaire_admin
 Date : 2024-08-03

 Vérification :
 *Date*               *Nom*             *Approuvé*
 =========================================================

 Historique de modifications :

 =========================================================
 ****************************************/
package com.example.projetintegrateur;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CommandeAdapter extends ArrayAdapter<Commande> implements View.OnClickListener {
    Commande commande;

    public CommandeAdapter(Context context, List<Commande> commande) {
        super(context, 0, commande);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        this.commande = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_inventaire, parent, false);
        }

        TextView dateDebut = convertView.findViewById(R.id.dateDebut);
        TextView dateFin = convertView.findViewById(R.id.dateFin);
        TextView description = convertView.findViewById(R.id.desc);
        TextView idStatus = convertView.findViewById(R.id.idStatus);
        TextView idClient = convertView.findViewById(R.id.idClient);

        description.setText(commande.getDescription());
        idStatus.setText(commande.getIdStatus());
        idClient.setText(commande.getIdClient());
        return convertView;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.rowImage) {
            Intent intent = new Intent(v.getContext(), pageProduit.class);
            intent.putExtra("id", this.commande.getId());
            v.getContext().startActivity(intent);
        }
    }
}
