/****************************************
 Fichier : ReparationAdapter
 Auteur : Yassine Adibe
 Fonctionnalité : Page qui gère la classe Reparation en relation avec le layout row_reparation_admin
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
import android.icu.math.BigDecimal;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ReparationAdapter extends ArrayAdapter<Reparation> implements View.OnClickListener {
    Reparation reparation;
    public ReparationAdapter(Context context, List<Reparation> reparation) {
        super(context, 0, reparation);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        this.reparation = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_inventaire_admin, parent, false);
        }

        TextView nom = convertView.findViewById(R.id.rowNom);
        TextView description = convertView.findViewById(R.id.rowDescription);
        TextView montant = convertView.findViewById(R.id.rowMontant);
        TextView quantite = convertView.findViewById(R.id.rowQuantite);
        ImageView image = convertView.findViewById(R.id.rowImage);

        nom.setText(this.reparation.getNom());
        description.setText(this.reparation.getDescription());
        quantite.setText(convertView.getContext().getString(R.string.showEtat));
        quantite.append(" " + String.valueOf(this.reparation.getIdStatus()));

        image.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.rowReparation) {
            Intent intent = new Intent(v.getContext(), pageReparation.class);
            intent.putExtra("id", this.reparation.getId());
            v.getContext().startActivity(intent);
        }
    }
}
