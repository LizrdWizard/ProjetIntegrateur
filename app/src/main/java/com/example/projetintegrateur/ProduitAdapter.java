/****************************************
 Fichier : ProduitAdapter
 Auteur : Jasmin Dubuc
 Fonctionnalité : Page qui gère la classe Produit en relation avec le layout row_inventaire_admin
 Date : 2024-08-03

 Vérification :
 2024-05-23         Jasmin Dubuc        Approuvé
 =========================================================

 Historique de modifications :
 2024-05-24         Jasmin Dubuc        Preparation pour merge avec les autres parties
 =========================================================
 ****************************************/
package com.example.projetintegrateur;
//import static com.example.projetintegrateur.SQLiteManager.sqLiteManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
//import android.database.sqlite.SQLiteDatabase;
public class ProduitAdapter extends ArrayAdapter<Produit> {

    public ProduitAdapter(Context context, List<Produit> produit) {
        super(context, 0, produit);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Produit produit = new Produit(getItem(position));

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_inventaire, parent, false);
        }

        TextView nom = convertView.findViewById(R.id.rowNom);
        TextView description = convertView.findViewById(R.id.rowDescription);
        TextView montant = convertView.findViewById(R.id.rowMontant);
        TextView quantite = convertView.findViewById(R.id.rowQuantite);
        ImageView image = convertView.findViewById(R.id.rowImage);
        Button buttonAjouter = convertView.findViewById(R.id.buttonAjouter);
        Button buttonEnlever = convertView.findViewById(R.id.buttonEnlever);

        nom.setText(produit.getNom());
        description.setText(produit.getDescription());
        montant.setText(produit.getPrix().toString());
        montant.append("$");
        image.setImageBitmap(produit.getPhoto());
        quantite.setText(convertView.getContext().getString(R.string.showQuantite));
        quantite.append(" " + String.valueOf(produit.getQuantite()));

        convertView.setClickable(true);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), pageProduit.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idProduit", produit.getId());
                //intent.putExtra("idClient", Bright.getIdClient());
                v.getContext().startActivity(intent);
            }
        });

        buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(v.getContext());
                SQLiteDatabase sqLiteDatabase = sqLiteManager.getReadableDatabase();
                ProduitCommande nouveauProduitCommande = new ProduitCommande(produit.getId(), Bright.getIdClient());
                sqLiteManager.ajouterProduitCommandeDatabase(sqLiteDatabase, nouveauProduitCommande);
                */

            }
        });
        /*
        buttonEnlever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(v.getContext());
                SQLiteDatabase sqLiteDatabase = sqLiteManager.getReadableDatabase();
                ProduitCommande nouveauProduitCommande = new ProduitCommande(produit.getId(), Bright.getIdClient());
                sqLiteManager.ajouterProduitCommandeDatabase(sqLiteDatabase, nouveauProduitCommande);
            }
        });

        */
        return convertView;
    }
}
