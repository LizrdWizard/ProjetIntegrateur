package com.example.projetintegrateur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class pageCompteEditCoord extends AppCompatActivity implements View.OnClickListener{

    public int currentProv;
    public int currentCity;

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

                villeAdapter.clear();
                villeAdapter.addAll(cityNames.get(position));
                villeSpin.setAdapter(villeAdapter);

                currentProv = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(0);
                villeAdapter.clear();
                villeAdapter.addAll(cityNames.get(0));
                villeSpin.setAdapter(villeAdapter);

                currentProv = 0;
            }

        });

        villeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                currentCity = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(0);

                currentCity = 0;
            }

        });

        Button cancelBtn = (Button) findViewById(R.id.accCrea_btn_cancel);
        Button confBtn = (Button) findViewById(R.id.accCrea_btn_conf);

        cancelBtn.setOnClickListener(this);
        confBtn.setOnClickListener(this);

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
            content.add(name.toString());
            content.add(surname.toString());
            content.add(mail.toString());
            content.add(tel.toString());
            content.add(addr.toString());
            content.add(cp.toString());

            int index = 0;

            while(index < content.size() && code == 0)
            {
                if(content.get(index).matches("[$&+,\\/\\\\\\[\\]:;=?@#|'<>.^*()%!-]") || content.get(index).trim().isEmpty())
                {
                    code = 1;
                }
                index++;
            }

            //mail correct
            if(!mail.toString().matches("^\\w+@\\w+\\.\\w+$"))
            {
                code = 2;
            }

            if(code == 0)
            {
                User user = new User(0,
                        name.toString(),
                        surname.toString(),
                        mail.toString(),
                        tel.toString(),
                        addr.toString(),
                        cp.toString(),
                        currentCity,
                        currentProv,
                        "");

                SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
                sqLiteManager.updateUser(user);

                finish();
            }
        }
    }


    private void loadFromDBToMemory()
    {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateProvListArray();
        sqLiteManager.populateVilleListArray();
    }
}