/****************************************
 Fichier : pageMain
 Auteur : Jasmin Dubuc
 Fonctionnalité : Page d'accuiel
 Date : 2024-05-27

 Vérification :
 2024-05-23         Jasmin Dubuc        Approuvé
 =========================================================
 =========================================================
 ****************************************/package com.example.projetintegrateur;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class pageMain extends AppCompatActivity implements View.OnClickListener{
    /*
    Signature des membres de l'équipe
    Jasmin  : Jasmin Dubuc
    Bright  : Bright Ogbeiwi
    Jérémie : Jeremie Gaudet
    Yassine : Yassine Adibe
     */
    private InitButton initButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initButton = new InitButton();

        TextView viewHeader = (TextView) findViewById(R.id.textHeader);
        viewHeader.setText(R.string.pageAccueil);

        ImageButton account = (ImageButton) findViewById(R.id.boutonFooter5);
        account.setOnClickListener(this);
    }
    public void bInit(View v){initButton.click(pageMain.this, v);}

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.boutonFooter5)
        {
            Intent intent = intent = new Intent(pageMain.this, pageCompteMenu.class);
            startActivity(intent);
        }
    }
}