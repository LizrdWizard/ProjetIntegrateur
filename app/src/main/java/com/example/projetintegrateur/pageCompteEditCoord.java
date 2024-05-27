/****************************************
 Fichier : pageCompteEditCoord.java
 Auteur : Ogbeiwi Bright
 Fonctionnalité : Affichage et gestion de l'edition d'un compte utilisateur (sauf mot de passe)
 Date : 13/05/2024
 =========================================================
 Historique de modifications :
 Date       Nom                             Description
 21/05/2024 Activités User : Implementation Implementation des fonctions, non testé
 26/05/2024 Merge et Correction             Merge et corrections dans la branche main
 26/05/2024 Fix Champs textuelles           Correction des champs textuelles n'affichant pas tout le texte
 =========================================================
 ****************************************/
package com.example.projetintegrateur;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class pageCompteEditCoord extends AppCompatActivity implements View.OnClickListener{

    public String currentProv;
    public String currentCity;

    User currentUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page_compte_edit_coord);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadFromDBToMemory();
        initWidgets();

    }

    private void initWidgets()
    {

        //spinners

        Spinner provSpin = (Spinner) findViewById(R.id.accCrea_spin_prov);
        ArrayList<String> provNameList = new ArrayList<String>();
        ArrayList<ArrayList<String>> cityNames = new ArrayList<ArrayList<String>>();

        for (Province content: Province.provinceArrayList) {
            provNameList.add(content.getLibelle());

            ArrayList<String> cities = new ArrayList<String>();
            for (Ville ville: content.getVilles())
            {
                cities.add(ville.getLibelle());
            }

            cityNames.add(cities);

        }

        ArrayAdapter<String> provAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, provNameList);
        provSpin.setAdapter(provAdapter);

        Spinner villeSpin = (Spinner) findViewById(R.id.accCrea_spin_city);
        ArrayAdapter<String> villeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cityNames.get(0));


        provSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                villeSpin.setAdapter(null);

                villeAdapter.clear();
                villeAdapter.addAll(cityNames.get(position));
                villeSpin.setAdapter(villeAdapter);

                currentProv = parent.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                villeSpin.setAdapter(null);

                parent.setSelection(0);
                villeAdapter.clear();
                villeAdapter.addAll(cityNames.get(0));
                villeSpin.setAdapter(villeAdapter);

                currentProv = parent.getSelectedItem().toString();
            }

        });

        villeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                currentCity = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(0);

                currentCity = parent.getSelectedItem().toString();
            }

        });

        Button cancelBtn = (Button) findViewById(R.id.accCrea_btn_cancel);
        Button confBtn = (Button) findViewById(R.id.accCrea_btn_conf);

        cancelBtn.setOnClickListener(this);
        confBtn.setOnClickListener(this);

        EditText name = (EditText) findViewById(R.id.acc_txtin_name);
        EditText surname = (EditText) findViewById(R.id.acc_txtin_surname);
        EditText mail = (EditText) findViewById(R.id.accConn_txtin_mail);
        EditText tel = (EditText) findViewById(R.id.accCrea_txtin_tel);
        EditText addr = (EditText) findViewById(R.id.accCrea_txtin_addr);
        EditText cp = (EditText) findViewById(R.id.accCrea_txtin_cp);

        name.setText(currentUser.getNom());
        surname.setText(currentUser.getPrenom());
        mail.setText(currentUser.getMail());
        tel.setText(currentUser.getTel());
        addr.setText(currentUser.getAddr());
        cp.setText(currentUser.getCp());
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.accCrea_btn_cancel)
        {
            finish();
        } else if (v.getId() == R.id.accCrea_btn_conf) {

            int code = 0;

            //get all inputs
            EditText name = (EditText) findViewById(R.id.acc_txtin_name);
            EditText surname = (EditText) findViewById(R.id.acc_txtin_surname);
            EditText mail = (EditText) findViewById(R.id.accConn_txtin_mail);
            EditText tel = (EditText) findViewById(R.id.accCrea_txtin_tel);
            EditText addr = (EditText) findViewById(R.id.accCrea_txtin_addr);
            EditText cp = (EditText) findViewById(R.id.accCrea_txtin_cp);

            ArrayList<String> content = new ArrayList<String>();
            content.add(name.getText().toString());
            content.add(surname.getText().toString());
            content.add(mail.getText().toString());
            content.add(tel.getText().toString());
            content.add(addr.getText().toString());
            content.add(cp.getText().toString());

            int index = 0;

            while(index < content.size() && code == 0)
            {
                if(content.get(index).matches("^\\w*[$&+,:;=?@#|'<>.^*()%!-].*$") || TextUtils.isEmpty(content.get(index)))
                {
                    Toast.makeText(getApplicationContext(), "Un champ contient des characrères speciaux ou est vide", Toast.LENGTH_LONG).show();
                    code = 1;
                }
                index++;
            }

            //mail correct
            if(!Patterns.EMAIL_ADDRESS.matcher(content.get(0)).matches())
            {
                Toast.makeText(getApplicationContext(), "Un courriel est requis", Toast.LENGTH_LONG).show();
                code = 2;
            }

            if(code == 0)
            {
                SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);

                User user = new User(0,
                        content.get(0),
                        content.get(1),
                        content.get(2),
                        content.get(3),
                        content.get(4),
                        content.get(5),
                        sqLiteManager.getVilleIdByName(currentCity),
                        sqLiteManager.getProvinceIdByName(currentProv),
                        "",
                        0);

                sqLiteManager.updateUser(user);

                finish();
            }
        }
    }


    private void loadFromDBToMemory()
    {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);

        currentUser = sqLiteManager.selectUserById(User.currentUserID);

        sqLiteManager.populateProvListArray();
        sqLiteManager.populateVilleListArray();
    }
}