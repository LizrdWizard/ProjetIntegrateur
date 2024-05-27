/****************************************
 Fichier : pageCompteMenu.java
 Auteur : Ogbeiwi Bright
 Fonctionnalité : Affichage et gestion du menu utilisateur
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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class pageCompteMenu extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page_compte_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if(User.currentUserID == 0)
        {
            Intent intent = new Intent(pageCompteMenu.this, pageCompteConnection.class);
            finish();
            startActivity(intent);
        }

        initWidget();
    }

    public void initWidget()
    {
        Button editAcc = (Button) findViewById(R.id.accMenu_btn_editAcc);
        Button editPw = (Button) findViewById(R.id.accMenu_btn_editpw);
        Button ena2Fa = (Button) findViewById(R.id.accMenu_btn_ena2FA);
        Button histComm = (Button) findViewById(R.id.accMenu_btn_historyCommand);
        Button histRep = (Button) findViewById(R.id.accMenu_btn_historyRepair);
        Button disconn = (Button) findViewById(R.id.accMenu_btn_disconn);

        editAcc.setOnClickListener(this);
        editPw.setOnClickListener(this);
        ena2Fa.setOnClickListener(this);
        histComm.setOnClickListener(this);
        histRep.setOnClickListener(this);
        disconn.setOnClickListener(this);

    }

    public void onClick(View v)
    {
        if(v.getId() == R.id.accMenu_btn_editAcc)
        {

            Intent intent = new Intent(pageCompteMenu.this, pageCompteEditCoord.class);
            startActivity(intent);

        } else if (v.getId() == R.id.accMenu_btn_editpw) {

            Intent intent = new Intent(pageCompteMenu.this, pageCompteEditPW.class);
            startActivity(intent);

        }
        else if (v.getId() == R.id.accMenu_btn_ena2FA) {

            Toast.makeText(getApplicationContext(), "Cette fonction est indisponible pour le moment", Toast.LENGTH_LONG).show();


        }
        else if (v.getId() == R.id.accMenu_btn_historyCommand) {
            Intent intent = new Intent(pageCompteMenu.this, pageCommande.class);
            startActivity(intent);
        }
        else if (v.getId() == R.id.accMenu_btn_historyRepair) {
            Intent intent = new Intent(pageCompteMenu.this, pageReparation.class);
            startActivity(intent);
        }
        else if (v.getId() == R.id.accMenu_btn_disconn) {

            SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
            sqLiteManager.disconnectUser();
            Intent intent = new Intent(pageCompteMenu.this, pageMain.class);
            finish();
            startActivity(intent);
        }
    }
}