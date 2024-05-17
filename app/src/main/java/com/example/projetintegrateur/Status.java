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
import java.util.Objects;

public class Status {
    public static ArrayList<Status> statusArrayList = new ArrayList<>();
    private int id;
    private String nom;

    public Status(int id, String nom) {
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

    public static Status getStatusById(int idStatus) {
        for (Status status : statusArrayList) {
            if (status.getId() == idStatus)
                return status;
        }
        return null;
    }
    public static int statusSize() {return statusArrayList.size();}
}