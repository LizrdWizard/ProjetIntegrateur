package com.example.projetintegrateur;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class pageCommande extends AppCompatActivity {
    private InitButton initButton;
    ListView commandeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page_commande);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        commandeView = (ListView) findViewById(R.id.listCommande);
        initButton = new InitButton();
        loadFromDBToMemory();

        setProduitAdapter(produitsPanier());
    }

    public void bRetour(View v) {
        initButton.click(pageCommande.this, v);
    }

    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateProduitListArray();
    }

    private void setProduitAdapter(ArrayList<Produit> listeProduit) {
        ProduitAdapter produitAdapter = new ProduitAdapter(getApplicationContext(), listeProduit);
        commandeView.setAdapter(produitAdapter);
    }

    private ArrayList<Produit> produitsPanier() {
        ArrayList<Produit> listeFiltre = new ArrayList<>();
        for (Produit produit : Produit.produitArrayList) {
            if (produit.getId() == 1) {
                listeFiltre.add(produit);
            }
        }
        return listeFiltre;
    }
}