/****************************************
 Fichier : pageReparation
 Auteur : Yassine Adibe
 Fonctionnalité : Page ou l'on peut voir les reparations en cours
 Date : 2024-05-03

 Vérification :
 *Date*               *Nom*             *Approuvé*
 =========================================================

 Historique de modifications :
 *Date*               *Nom*             *Approuvé*
 =========================================================
 ****************************************/

package com.example.projetintegrateur;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class pageAjouterReparation extends AppCompatActivity implements View.OnClickListener {
    SQLiteManager sqLiteManager;
    SQLiteDatabase sqLiteDatabase;
    Button buttonRetour;
    Button buttonAjouterReparation;
    Button buttonModifier;
    EditText editNom;
    EditText editDescription;
    TextView viewNom;
    TextView viewStatus;
    TextView viewDescription;
    Spinner spinnerStatus;
    InitButton initButton = new InitButton();
    int idReparation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page_ajouter_reparation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initWidget();
        loadFromDbToMemory();
        preparerSpinnerStatus();

        idReparation = getIntent().getIntExtra("idReparation", 0);
        //idClient = getIntent().getIntExtra("idClient", 1);

        //Si aucun idProduit, c'est l'admin qui en rajoute un nouveau
        if (idReparation != 0) {
            //viewProduit();
            viewReparationAdmin();
        }
    }
    private void initWidget() {
        buttonRetour = (Button) findViewById(R.id.buttonRetour);
        buttonAjouterReparation = (Button) findViewById(R.id.buttonReparation);
        buttonModifier = (Button) findViewById(R.id.buttonModifier);
        editNom = (EditText) findViewById(R.id.editNom);
        spinnerStatus = (Spinner) findViewById(R.id.spinnerStatus);
        editDescription = (EditText) findViewById(R.id.editDescription);
        viewNom = (TextView) findViewById(R.id.viewNom);
        viewStatus = (TextView) findViewById(R.id.viewStatus);
        viewDescription = (TextView) findViewById(R.id.viewDescription);

        buttonRetour.setOnClickListener(this);
        buttonAjouterReparation.setOnClickListener(this);
        buttonModifier.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.buttonRetour) {
            Intent intent = new Intent(pageAjouterReparation.this, pageReparation.class);
            startActivity(intent);
        }
        else if (v.getId() == R.id.buttonReparation) {
            Reparation nouvelleReparation = new Reparation();

            if (TextUtils.isEmpty(editNom.getText().toString()) || TextUtils.isEmpty(editDescription.getText().toString())) {
                showDialog();
            }
            else {
                nouvelleReparation.setNom(editNom.getText().toString());
                nouvelleReparation.setDescription(editDescription.getText().toString());
                nouvelleReparation.setIdStatus(spinnerStatus.getSelectedItemPosition());

                sqLiteManager.ajouterReparationDatabase(sqLiteDatabase, nouvelleReparation);
                Intent intent = new Intent(pageAjouterReparation.this, pageReparation.class);
                startActivity(intent);
            }
        }
        else if (v.getId() == R.id.buttonModifier) {
            updaterReparation();
            startActivity(new Intent(pageAjouterReparation.this, pageReparation.class));
        }
    }
    public void preparerSpinnerStatus() {
        ArrayAdapter<Status> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Status.statusArrayList);
        spinnerStatus.setAdapter(statusAdapter);
    }
    private void showDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_produit);
        TextView popUpText = (TextView)dialog.findViewById(R.id.popUpText);
        popUpText.setText("Les champs ne sont pas tous rempli");
        dialog.show();


        Button buttonOK;
    }
    public void  loadFromDbToMemory(){
        sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteDatabase = sqLiteManager.getReadableDatabase();
        sqLiteManager.populateCategorieListArray();
        sqLiteManager.populateReparationListArray();
    }

    private void viewReparation(){
        Reparation reparation = Reparation.getReparationById(idReparation);
        buttonAjouterReparation.setVisibility(View.INVISIBLE);
        buttonAjouterReparation.setEnabled(false);


        editNom.setVisibility(View.GONE);
        viewNom.setVisibility(View.VISIBLE);
        viewNom.setText(reparation.getNom());

        spinnerStatus.setVisibility(View.GONE);
        viewStatus.setVisibility(View.VISIBLE);
        viewStatus.setText(Categorie.getCategorieById(reparation.getIdStatus()).toString());

        editDescription.setVisibility(View.GONE);
        viewDescription.setVisibility(View.VISIBLE);
        viewDescription.setText(reparation.getDescription());


        /*Reparation reparation = Reparation.getReparationById(idReparation);
        buttonAjouterReparation.setVisibility(View.INVISIBLE);
        buttonAjouterReparation.setEnabled(false);

        editNom.setVisibility(View.GONE);
        viewNom.setVisibility(View.VISIBLE);
        viewNom.setText(reparation.getNom());

        editDescription.setVisibility(View.GONE);
        viewDescription.setVisibility(View.VISIBLE);
        viewDescription.setText(reparation.getDescription());

        spinnerStatus.setVisibility(View.GONE);
        viewStatus.setVisibility(View.VISIBLE);
        viewStatus.setText(Status.getStatusById(reparation.getIdStatus()).toString());*/

    }

    private void viewReparationAdmin(){
        Reparation reparation = Reparation.getReparationById(idReparation);
        editNom.setHint(reparation.getNom());
        spinnerStatus.setSelection(reparation.getIdStatus());
        editDescription.setHint(reparation.getDescription());

        buttonAjouterReparation.setVisibility(View.INVISIBLE);
        buttonAjouterReparation.setEnabled(false);
        buttonModifier.setVisibility(View.VISIBLE);
        buttonModifier.setEnabled(true);

        /*Reparation reparation = Reparation.getReparationById(idReparation);
        editNom.setHint(reparation.getNom());
        spinnerStatus.setSelection(reparation.getIdStatus());
        editDescription.setHint(reparation.getDescription());

        buttonAjouterReparation.setVisibility(View.INVISIBLE);
        buttonAjouterReparation.setEnabled(false);
        buttonModifier.setVisibility(View.VISIBLE);
        buttonModifier.setEnabled(true);*/
    }

    private void updaterReparation(){
        Reparation vieuxReparation = Reparation.getReparationById(idReparation);
        int idStatus;
        if (!editNom.getText().toString().isEmpty() || !editDescription.getText().toString().isEmpty() || spinnerStatus.getSelectedItemPosition() != vieuxReparation.getIdStatus()) {
            Reparation reparationUpdate = new Reparation();
            reparationUpdate.setId(vieuxReparation.getId());
        //Status
        if (spinnerStatus.getSelectedItemPosition() == vieuxReparation.getIdStatus()) {idStatus = vieuxReparation.getIdStatus();}
        else {idStatus = spinnerStatus.getSelectedItemPosition();}

        sqLiteManager.updaterReparationDatabase(sqLiteDatabase, reparationUpdate);
        buttonRetour.performClick();
            sqLiteManager.updaterReparationDatabase(sqLiteDatabase, reparationUpdate);
            Intent intent = new Intent(pageAjouterReparation.this, pageReparation.class);
            startActivity(intent);
        }
    }
    public void bInit(View v){

        initButton.click(pageAjouterReparation.this, v);
    }
}