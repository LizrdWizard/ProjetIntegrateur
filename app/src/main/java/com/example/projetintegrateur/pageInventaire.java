/****************************************
 Fichier : pageInventaire
 Auteur : Jasmin Dubuc
 Fonctionnalité : Page ou l'on peut voir l'inventaire disponible en magasin
 Date : 2024-05-03

 Vérification :
 *Date*               *Nom*             *Approuvé*
 =========================================================

 Historique de modifications :
 *Date*               *Nom*             *Approuvé*
 =========================================================
 ****************************************/

package com.example.projetintegrateur;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class pageInventaire extends AppCompatActivity {

    ListView produitsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page_inventaire);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initWidget();
        loadFromDBToMemory();
        setProduitAdapter(Produit.produitArrayList);
    }

    private void initWidget() {produitsView = (ListView) findViewById(R.id.listInventaire);}
    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateCategorieListeArray();
        sqLiteManager.populateProduitListArray();
    }

    private void setProduitAdapter(ArrayList<Produit> listeProduit) {
        ProduitAdapter produitAdapter = new ProduitAdapter(getApplicationContext(), listeProduit);
        produitsView.setAdapter(produitAdapter);
    }
}