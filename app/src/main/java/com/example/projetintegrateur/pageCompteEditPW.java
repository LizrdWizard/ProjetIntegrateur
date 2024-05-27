/****************************************
 Fichier : pageCompteEditPW.java
 Auteur : Ogbeiwi Bright
 Fonctionnalité : Affichage et gestion de l'edition du mot de passe de l'utilisateur
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
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class pageCompteEditPW extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page_compte_edit_pw);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initWidgets();
    }

    private void initWidgets()
    {


        Button cancelBtn = (Button) findViewById(R.id.accEditPw_btn_cancel);
        Button confBtn = (Button) findViewById(R.id.accEditPw_btn_conf);
        ImageButton account = (ImageButton) findViewById(R.id.boutonFooter5);

        cancelBtn.setOnClickListener(this);
        confBtn.setOnClickListener(this);
        account.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.accEditPw_btn_cancel)
        {
            finish();
        } else if (v.getId() == R.id.accEditPw_btn_conf) {

            int code = 0;

            //get all inputs
            EditText oldPw = (EditText) findViewById(R.id.accEditPw_txtpw_oldpw);
            EditText newPw = (EditText) findViewById(R.id.accEditPw_txtpw_newpw);
            EditText confPw = (EditText) findViewById(R.id.accEditPw_txtpw_confpw);

            ArrayList<String> content = new ArrayList<String>();
            content.add(oldPw.getText().toString());
            content.add(newPw.getText().toString());
            content.add(confPw.getText().toString());

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

            if(!newPw.toString().equals(confPw.toString()))
            {
                Toast.makeText(getApplicationContext(), "Le champ nouveau mot de passe et confirmer mot de passe ne sont pas identique", Toast.LENGTH_LONG).show();
                code = 3;
            }

            if(code == 0)
            {
                SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
                sqLiteManager.updateUserPW(content.get(0), content.get(1));

                finish();
            }
        }
        else if(v.getId() == R.id.boutonFooter5)
        {
            Intent intent = intent = new Intent(pageCompteEditPW.this, pageCompteMenu.class);
            startActivity(intent);
        }
    }
}