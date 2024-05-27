/****************************************
 Fichier : Ville.java
 Auteur : Ogbeiwi Bright
 Fonctionnalité : Implementation en objet de la table Ville de la DB
 Date : 21/05/2024
 ****************************************/
package com.example.projetintegrateur;

import java.util.ArrayList;

public class Ville {
    public static ArrayList<Ville> villeArrayList = new ArrayList<Ville>();

    private int id;
    private String libelle;

    public Ville(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

}
