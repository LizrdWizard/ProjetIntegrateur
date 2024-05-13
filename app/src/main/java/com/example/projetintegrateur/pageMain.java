package com.example.projetintegrateur;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class pageMain extends AppCompatActivity{

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

        //À ENLEVER APRÈS CAMÉRA
        SQLiteManager.PHOTO_TEMP = BitmapFactory.decodeResource(this.getApplicationContext().getResources(), R.drawable.luffyyeah);

    }
    public void bRetour(View v){initButton.click(pageMain.this, v);}
}