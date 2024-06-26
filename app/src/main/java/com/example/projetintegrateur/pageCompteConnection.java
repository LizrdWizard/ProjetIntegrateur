/****************************************
 Fichier : pageCompteConnection.java
 Auteur : Ogbeiwi Bright
 Fonctionnalité : Affichage et gestion de la connection à une session utilisateur
 Date : 13/05/2024
 =========================================================
 Historique de modifications :
 Date       Nom                             Description
 21/05/2024 Activités User : Implementation Implementation des fonctions, non testé
 26/05/2024 Merge et Correction              Merge et corrections dans la branche main
 =========================================================
 ****************************************/
package com.example.projetintegrateur;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class pageCompteConnection extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page_compte_connection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initWidget();
    }

    public void initWidget()
    {
        Button cancelBtn = (Button) findViewById(R.id.accConn_btn_cancel);
        Button confBtn = (Button) findViewById(R.id.accConn_btn_conf);
        TextView redirect = (TextView) findViewById(R.id.accConn_lbl_redirect);
        ImageButton footerConnect = (ImageButton) findViewById(R.id.boutonFooter2);


        cancelBtn.setOnClickListener(this);
        confBtn.setOnClickListener(this);
        redirect.setOnClickListener(this);
        footerConnect.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        if(v.getId() == R.id.accConn_btn_cancel)
        {
            finish();
        } else if (v.getId() == R.id.accConn_btn_conf) {

            int code = 0;

            //get all inputs
            EditText mail = (EditText) findViewById(R.id.accConn_txtin_mail);
            EditText pw = (EditText) findViewById(R.id.accConn_txtpw_pw);

            ArrayList<String> content = new ArrayList<String>();

            content.add(mail.getText().toString());
            content.add(pw.getText().toString());


            int index = 0;

            while(index < content.size() && code == 0)
            {

                if(content.get(index).matches("^\\w*[$&+,:;=?#|'<>^*()%!-].*$") || TextUtils.isEmpty(content.get(index)))
                {
                    Toast.makeText(getApplicationContext(), "Un champ contient des characrères speciaux ou est vide", Toast.LENGTH_LONG).show();
                    code = 1;
                }
                index++;
            }

            //mail correct
            if(!Patterns.EMAIL_ADDRESS.matcher(content.get(0)).matches())
            {
                Toast.makeText(getApplicationContext(), "Un courriel est requis pour se connecter", Toast.LENGTH_LONG).show();
                code = 2;
            }


            if(code == 0)
            {

                SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
                int err = sqLiteManager.connectUser(content.get(0), content.get(1));

                if(err == 1)
                {
                    Toast.makeText(getApplicationContext(), "Un courriel ou le mot de passe est invalide", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent = new Intent(pageCompteConnection.this, pageCompteMenu.class);
                    finish();
                    startActivity(intent);
                }

            }
        } else if (v.getId() == R.id.accConn_lbl_redirect) {
            Intent intent = new Intent(pageCompteConnection.this, pageCompteCreation.class);
            finish();
            startActivity(intent);
        }
        else if(v.getId() == R.id.boutonFooter2)
        {
            Intent intent = new Intent(pageCompteConnection.this, pageCompteConnection.class);
            finish();
            startActivity(intent);
        }
    }



}