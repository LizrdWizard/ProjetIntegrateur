package com.example.projetintegrateur;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.icu.text.SymbolTable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.databinding.ObservableList;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class pageCommande extends AppCompatActivity implements View.OnClickListener{
    private InitButton initButton;
    ProduitCommandeAdapter produitCommandeAdapter;
    ListView commandeView;
    SQLiteManager sqLiteManager;
    TextView totPrix;
    ArrayList<ProduitClient> listeFiltre;

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
        listeFiltre = new ArrayList<>();

        commandeView = (ListView) findViewById(R.id.listCommande);
        initButton = new InitButton();
        totPrix = findViewById(R.id.totPrix);
        loadFromDBToMemory();
        setProduitCommandeAdapter(produitsPanier());
    }

    public void bInit(View v) {
        initButton.click(pageCommande.this, v);
    }
    public void buttonCommande(View v){
        System.out.println("salut");
        Date c = Calendar.getInstance().getTime();
        sqLiteManager.ajouterCommandeDatabase(sqLiteManager.getReadableDatabase(), new Commande(1, c,null,"commande",1,1));
        for(ProduitClient produitClient : ProduitClient.produitClientArrayList){
            sqLiteManager.ajouterProduitCommandeDatabase(sqLiteManager.getReadableDatabase(),new ProduitCommande(1,produitClient.getIdProduit(),Commande.commandeSize()));
        }
        listeFiltre.clear();
        setProduitCommandeAdapter(listeFiltre);
    }
    public void onClick(View v) {
    }
    public void onChanged(ObservableList sender) {
        setProduitCommandeAdapter(produitsPanier());
    }
    private void loadFromDBToMemory() {
        sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateProduitListArray();
        sqLiteManager.populateCommandeListArray();
        sqLiteManager.ajouterProduitClientDatabase(sqLiteManager.getReadableDatabase(), new ProduitClient(1,1,1));
        sqLiteManager.populateProduitClientListArray();
    }

    private void setProduitCommandeAdapter(ArrayList<ProduitClient> listeProduit) {
        produitCommandeAdapter = new ProduitCommandeAdapter(getApplicationContext(), listeProduit);
        commandeView.setAdapter(produitCommandeAdapter);
    }

    private ArrayList<ProduitClient> produitsPanier() {
        for (ProduitClient produitClient : ProduitClient.produitClientArrayList) {
            if (produitClient.getIdClient() == 1) {
                listeFiltre.add(produitClient);
            }
        }
        return listeFiltre;
    }

    /*public void modifierPrix() {
        float prix = 0;
        for (Produit produit : listeFiltre) {
            prix += produit.getPrix() * Float.parseFloat(produitCommandeAdapter.getQt());
        }
        totPrix.setText(String.valueOf(prix));
    }*/

}