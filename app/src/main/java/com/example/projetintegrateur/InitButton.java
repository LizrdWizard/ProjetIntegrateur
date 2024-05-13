package com.example.projetintegrateur;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
public class InitButton{
    private Intent intent;
    InitButton(){}

    public void click(Context c, View v) {
        if(R.id.buttonRetour == v.getId()){
            ((Activity) c).finish();
        }
        else if(R.id.buttonCoucou == v.getId()){
           intent = new Intent(c, pageCommande.class);
        }
        else if(R.id.boutonFooter1 == v.getId()){
            //intent = new Intent(c, pageCommande.class);
        }
        else if(R.id.boutonFooter2 == v.getId()){
            //intent = new Intent(c, pageCommande.class);
        }
        else if(R.id.boutonFooter3 == v.getId()){
            //intent = new Intent(c, pageCommande.class);
        }
        else if(R.id.boutonFooter4 == v.getId()){
            // intent = new Intent(c, pageCommande.class);
        }
        else if(R.id.boutonFooter5 == v.getId()){
            // intent = new Intent(c, pageCommande.class);
        }
        c.startActivity(intent);
    }
}
