/****************************************
 Fichier : pageInventaire
 Auteur : Jasmin Dubuc
 Fonctionnalité : Page ou l'on peut voir l'inventaire disponible en magasin
 Date : 2024-05-03

 Vérification :
 2024-05-23         Jasmin Dubuc        Approuvé
 =========================================================

 Historique de modifications :
 2024-05-23         Jasmin Dubuc        Optimisation
 =========================================================
 ****************************************/
package com.example.projetintegrateur;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class pageInventaire extends AppCompatActivity implements View.OnClickListener {
    TextView textHeader;
    ListView produitsView;
    EditText editMax;
    EditText editMin;
    Spinner spinnerCategorie;
    CheckBox checkInventaire;
    Button buttonAjouterProduit;
    Button buttonFiltrer;
    InitButton initButton = new InitButton();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page_inventaire);

        initWidget();
        loadFromDBToMemory();
        setProduitAdapter(Produit.produitArrayList);
        preparerSpinnerCategorie();

    }
    private void initWidget() {
        produitsView = findViewById(R.id.listInventaire);
        editMax =findViewById(R.id.editMax);
        editMin = findViewById(R.id.editMin);
        spinnerCategorie = findViewById(R.id.spinnerCategorie);
        checkInventaire =  findViewById(R.id.checkInventaire);
        buttonAjouterProduit =  findViewById(R.id.bouttonAjouterProduit);
        buttonFiltrer = findViewById(R.id.bouttonFiltrer);
        buttonAjouterProduit.setOnClickListener(this);
        buttonFiltrer.setOnClickListener(this);

        textHeader = findViewById(R.id.textHeader);
        textHeader.setText(R.string.pageInventaire);
    }
    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateCategorieListArray();
        sqLiteManager.populateProduitListArray();
    }
    private void setProduitAdapter(ArrayList<Produit> listeProduit) {
        ProduitAdapter produitAdapter = new ProduitAdapter(getApplicationContext(), listeProduit);
        produitsView.setAdapter(produitAdapter);
    }
    public void preparerSpinnerCategorie() {
        ArrayList<Categorie> categorieHolder = new ArrayList<>(Categorie.categorieArrayList);
        categorieHolder.add(0, new Categorie(0, "Toutes"));
        ArrayAdapter<Categorie> categorieAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_list, categorieHolder);
        categorieAdapter.setDropDownViewResource(R.layout.my_spinner_list);
        spinnerCategorie.setAdapter(categorieAdapter);
    }
    public ArrayList<Produit> filtrerProduits() {

        int idCategorie = 0;
        int min = 0;
        int max = 0;

        ArrayList<Produit> listeFiltree = new ArrayList<>();
        if (spinnerCategorie.getSelectedItemPosition() != 1) {idCategorie = spinnerCategorie.getSelectedItemPosition() - 1;}
        else {idCategorie = 0;}
        if (!editMin.getText().toString().trim().isEmpty()){min = Integer.parseInt(editMin.getText().toString());}
        if (!editMax.getText().toString().trim().isEmpty()){max = Integer.parseInt(editMax.getText().toString());}

        for (Produit produit : Produit.produitArrayList) {
            //Si la case est cochée et que le produit soit en inventaire ou si la case n'est pas cochée
            if ((checkInventaire.isChecked() && produit.getQuantite() != 0) || !checkInventaire.isChecked())
                //idCategorie == l'id de la catégorie dans la bd && 0 == option spinner "Toutes", voir preparerSpinnerCategorie
                if(produit.getIdCategorie() == idCategorie || spinnerCategorie.getSelectedItemPosition() == 0) {
                    //Si max == min, on ignore les valeurs
                    if (min == max) {listeFiltree.add(produit);}
                    //Si max == 0 et que max != min, ça veut dire que juste minimum est entré
                    else if (max == 0 && produit.getPrix() >= min) {listeFiltree.add(produit);}
                    //Si min == 0 et que min != max, ça veut dire que juste max est entré
                    else if (min == 0 && produit.getPrix() <= max) {listeFiltree.add(produit);}
                    //Si tout le reste est faux, ça veut dire que les deux champs ont été entrés correctement
                    else if (produit.getPrix() <= max && produit.getPrix() >= min) {listeFiltree.add(produit);}
                }
        }
        return listeFiltree;
    }
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.bouttonAjouterProduit) {
            startActivity(new Intent(pageInventaire.this, pageProduit.class));
        }
        else if (v.getId() == R.id.bouttonFiltrer) {
            setProduitAdapter(filtrerProduits());
        }
    }
    public void bInit(View v){

        initButton.click(pageInventaire.this, v);
    }

}