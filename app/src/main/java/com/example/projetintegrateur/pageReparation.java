/****************************************
 Fichier : pageReparation
 Auteur : Yassine Adibe
 Fonctionnalité : Page ou l'on peut voir la liste de réparations
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class pageReparation extends AppCompatActivity implements View.OnClickListener{
    ListView reparationView;
    EditText IDReparation;
    Spinner spinnerStatus;
    Button buttonAjouterReparation;
    Button buttonFiltrer;
    InitButton initButton = new InitButton();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page_reparation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initWidget();
        loadFromDBToMemory();
        setReparationAdapter(Reparation.reparationArrayList);
        preparerSpinnerStatus();

        reparationView.setClickable(true);
        reparationView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(pageReparation.this, pageAjouterReparation.class);
                intent.putExtra("id", Reparation.reparationArrayList.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void initWidget() {
        reparationView = (ListView) findViewById(R.id.listReparation);
        spinnerStatus = (Spinner) findViewById(R.id.spinnerStatus);
        buttonAjouterReparation = (Button) findViewById(R.id.bouttonAjouterReparation);
        buttonFiltrer = (Button) findViewById(R.id.bouttonFiltrer);
        buttonAjouterReparation.setOnClickListener(this);
        buttonFiltrer.setOnClickListener(this);
    }
    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateStatusListArray();
        sqLiteManager.populateReparationListArray();
    }

    private void setReparationAdapter(ArrayList<Reparation> listeReparation) {
        ReparationAdapter reparationAdapter = new ReparationAdapter(getApplicationContext(), listeReparation);
        reparationView.setAdapter(reparationAdapter);
    }
    public void preparerSpinnerStatus() {
        ArrayList<Status> statusHolder = new ArrayList<>(Status.statusArrayList);
        statusHolder.add(0, new Status(0, "Toutes"));
        ArrayAdapter<Status> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, statusHolder);
        statusAdapter.setDropDownViewResource(R.layout.my_spinner_list);
        spinnerStatus.setAdapter(statusAdapter);
    }

    public ArrayList<Reparation> filtrerReparations() {

        int idStatus = spinnerStatus.getSelectedItemPosition();

        ArrayList<Reparation> listeFiltree = new ArrayList<>();

        for (Reparation reparation : Reparation.reparationArrayList) {
                if(reparation.getIdStatus() == idStatus || reparation.getIdStatus() == 0) {
            listeFiltree.add(reparation);}
        }
        return listeFiltree;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.bouttonAjouterReparation) {
            startActivity(new Intent(pageReparation.this, pageAjouterReparation.class));
        }
        else if (v.getId() == R.id.bouttonFiltrer) {
            setReparationAdapter(filtrerReparations());
        }
    }

    public void bInit(View v){

        initButton.click(pageReparation.this, v);
    }
}
