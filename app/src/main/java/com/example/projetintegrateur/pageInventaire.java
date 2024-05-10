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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class pageInventaire extends AppCompatActivity implements View.OnClickListener {
    ListView produitsView;

    Button buttonAjouterProduit;
    Button buttonFiltrer;

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

        buttonAjouterProduit = (Button) findViewById(R.id.bouttonAjouterProduit);
        buttonFiltrer = (Button) findViewById(R.id.bouttonFiltrer);
        buttonAjouterProduit.setOnClickListener(this);
        buttonFiltrer.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.bouttonAjouterProduit) {
            Intent intent = new Intent(pageInventaire.this, pageProduit.class);
            startActivity(intent);
        }
    }
}