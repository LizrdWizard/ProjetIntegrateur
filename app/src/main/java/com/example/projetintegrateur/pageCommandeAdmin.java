package com.example.projetintegrateur;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class pageCommandeAdmin extends AppCompatActivity {
    CommandeAdapter commandeAdapter;
    ListView commandeView;
    SQLiteManager sqLiteManager;
    ArrayList<Commande> commande;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page_commande_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        System.out.println("1");
        commande = new ArrayList<>();
        commandeView = (ListView) findViewById(R.id.listCommandeAdmin);
        loadFromDBToMemory();
        setCommandeAdminAdapter(Commande.commandeArrayList);
    }

    //Commande.commandeArrayList;
    private void loadFromDBToMemory() {
        sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateCommandeListArray();
        sqLiteManager.ajouterCommandeDatabase(sqLiteManager.getReadableDatabase(), new Commande(1,null,null,"lol", 1, 1));
        sqLiteManager.populateProduitCommandeListArray();
    }

    private void setCommandeAdminAdapter(ArrayList<Commande> commande) {
        commandeAdapter = new CommandeAdapter(getApplicationContext(), commande);
        commandeView.setAdapter(commandeAdapter);
    }
}