/****************************************
 Fichier : Status
 Auteur : Yassine Adibe
 Fonctionnalité : Page qui décrit la classe Status, à lier avec la classe Reparation et à utiliser avec la database
 Date : 2024-08-03

 Vérification :
 *Date*               *Nom*             *Approuvé*
 =========================================================

 Historique de modifications :

 =========================================================
 ****************************************/
package com.example.projetintegrateur;

import java.util.ArrayList;

public class StatusCommande {
    public static ArrayList<StatusCommande> statusCommandeArrayList = new ArrayList<>();
    private int id;
    private String nom;

    public StatusCommande(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    @Override
    public String toString() {
        return this.nom;
    }

    public static StatusCommande getStatusCommandeById(int idStatusCommande) {
        for (StatusCommande statusCommande : statusCommandeArrayList) {
            if (statusCommande.getId() == idStatusCommande)
                return statusCommande;
        }
        return null;
    }
    public static int statusCommandeSize() {return statusCommandeArrayList.size();}
}