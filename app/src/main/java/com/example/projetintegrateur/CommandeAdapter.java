
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

public class CommandeAdapter extends ArrayAdapter<Commande> {
    Commande commande;

    public CommandeAdapter(Context context, List<Commande> commande) {
        super(context, 0, commande);
        System.out.println("9dk de mm");
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        this.commande = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_commande_admin, parent, false);
        }
        System.out.println(commande.getDescription());
        System.out.println(position);
        TextView dateDebut = convertView.findViewById(R.id.dateDebut);
        TextView dateFin = convertView.findViewById(R.id.dateFin);
        TextView description = convertView.findViewById(R.id.desc);
        TextView idStatus = convertView.findViewById(R.id.idStatus);
        TextView idClient = convertView.findViewById(R.id.idClient);
        System.out.println("5");
        description.setText(commande.getDescription());
        idStatus.setText(commande.getIdStatus());
        idClient.setText(commande.getIdClient());
        dateDebut.setText(commande.getDateDebut().toString());
        dateFin.setText(commande.getDateFin().toString());
        description.setText(commande.getDescription());

        System.out.println("apsmkd");
        System.out.println("6");
        return convertView;
    }
}
