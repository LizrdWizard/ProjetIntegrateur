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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ProduitCommandeAdapter extends ArrayAdapter<ProduitClient> implements View.OnClickListener{
    ProduitClient produitClient;
    Produit produit;
    float prixTot;
    TextView plus;
    TextView moins;

    Button del;
    TextView qt;
    public ProduitCommandeAdapter(Context context, List<ProduitClient> produitClient) {
        super(context, 0, produitClient);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        this.produitClient = getItem(position);
        this.produit = Produit.getProduitById(produitClient.getIdProduit());
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_commande, parent, false);
        }

        TextView nom = convertView.findViewById(R.id.rowNom);
        TextView description = convertView.findViewById(R.id.rowDescription);
        TextView montant = convertView.findViewById(R.id.rowMontant);
        plus = convertView.findViewById(R.id.plus1);
        moins = convertView.findViewById(R.id.moins1);
        del = convertView.findViewById(R.id.del);
        qt = convertView.findViewById(R.id.qt1);
        ImageView image = convertView.findViewById(R.id.rowImage);

        plus.setClickable(true);
        moins.setClickable(true);
        nom.append(this.produit.getNom());
        description.append(this.produit.getDescription());
        montant.append(this.produit.getPrix().toString());
        image.setImageBitmap(this.produit.getPhoto());
        plus.setOnClickListener(this);
        moins.setOnClickListener(this);
        image.setOnClickListener(this);

        qt.setText("1");
        return convertView;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.rowImage) {
            Intent intent = new Intent(v.getContext(), pageProduit.class);
            intent.putExtra("id", this.produit.getId());
            v.getContext().startActivity(intent);
        }
        else if (v.getId() == R.id.moins1) {

            int nb = Integer.parseInt(qt.getText().toString());
            nb--;
            if (nb >= 0) {
                qt.setText(String.valueOf(nb));
            }
        }
        else if (v.getId() == R.id.plus1) {
            int nb = Integer.parseInt(qt.getText().toString());
            nb++;
            if (nb <= produit.getQuantite()) {
                qt.setText(String.valueOf(nb));
            }
        }
        else if (v.getId() == R.id.del) {
            SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this.getContext());
            sqLiteManager.deleteProduitClient(String.valueOf(produitClient.getId()));
        }
    }
    public String getQt(){
        return "1";
    }
}
