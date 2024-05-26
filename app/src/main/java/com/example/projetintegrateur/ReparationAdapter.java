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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_reparation_admin, parent, false);
        }

        TextView nom = convertView.findViewById(R.id.rowNom);
        TextView description = convertView.findViewById(R.id.rowDescription);
        TextView ID = convertView.findViewById(R.id.rowID);
        TextView Status = convertView.findViewById(R.id.rowStatus);

        String idString = String.valueOf(reparation.getId());

        nom.setText(reparation.getNom());
        description.setText(reparation.getDescription());
        ID.setText(idString);
        if (reparation.getIdStatus() == 0)
            Status.setText("Pas commencée");
        else if (reparation.getIdStatus() == 1)
            Status.setText("En cours");
        else if(reparation.getIdStatus() == 2)
            Status.setText("Terminée");
        return convertView;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.rowReparation) {
            Intent intent = new Intent(v.getContext(), pageReparation.class);
            intent.putExtra("idReparation", this.reparation.getId());
            v.getContext().startActivity(intent);
        }
    }
}
