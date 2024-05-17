/****************************************
 Fichier : ProduitAdapter
 Auteur : Jasmin Dubuc
 Fonctionnalité : Page qui gère la classe Produit en relation avec le layout row_inventaire_admin
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProduitAdapter extends ArrayAdapter<Produit> implements View.OnClickListener {
    Produit produit;
    public ProduitAdapter(Context context, List<Produit> produit) {
        super(context, 0, produit);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        this.produit = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_inventaire, parent, false);
        }

        TextView nom = convertView.findViewById(R.id.rowNom);
        TextView description = convertView.findViewById(R.id.rowDescription);
        TextView montant = convertView.findViewById(R.id.rowMontant);
        TextView quantite = convertView.findViewById(R.id.rowQuantite);
        ImageView image = convertView.findViewById(R.id.rowImage);

        nom.setText(this.produit.getNom());
        description.setText(this.produit.getDescription());
        montant.setText(this.produit.getPrix().toString());
        image.setImageBitmap(this.produit.getPhoto());
        quantite.setText(convertView.getContext().getString(R.string.showQuantite));
        quantite.append(" " + String.valueOf(this.produit.getQuantite()));

        image.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.rowImage) {
            Intent intent = new Intent(v.getContext(), pageProduit.class);
            intent.putExtra("id", this.produit.getId());
            v.getContext().startActivity(intent);
        }
    }
}
