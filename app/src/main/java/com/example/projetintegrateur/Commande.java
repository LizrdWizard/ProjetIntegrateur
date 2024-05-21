/****************************************
 Fichier : Produit
 Auteur : Jasmin Dubuc
 Fonctionnalité : Page qui décrit la classe Produit, à utiliser avec la database
 Date : 2024-08-03

 Vérification :
 *Date*               *Nom*             *Approuvé*
 =========================================================

 Historique de modifications :

 =========================================================
 ****************************************/
package com.example.projetintegrateur;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Commande {

    public static ArrayList<Commande> commandeArrayList = new ArrayList<>();
    private int id;
    private Date dateDebut;
    private Date dateFin;
    private String description;
    private int idStatus;
    private int idClient;

    public Commande() {}

    public Commande(int id, Date dateDebut, Date dateFin, String description, int idStatus, int idClient ){
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.description = description;
        this.idStatus = idStatus;
        this.idClient = idClient;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public Date getDateDebut() {return dateDebut;}
    public void setDateDebut(Date dateDebut) {this.dateDebut = dateDebut;}
    public Date getDateFin() {return dateFin;}
    public void setDateFin(Date dateFin) {this.dateFin = dateFin;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public int getIdStatus() {return idStatus;}
    public void setIdStatus(int idStatus) {this.idStatus = idStatus;}
    public int getIdClient() {return idClient;}
    public void setIdClient(int idClient) {this.idClient = idClient;}

    //Autre
    public static Commande getCommandeById(int idCommande) {
        for (Commande commande : commandeArrayList) {
            if (commande.getId() == idCommande)
                return commande;
        }
        return null;
    }
    public static int commandeSize() {return commandeArrayList.size();}
}
