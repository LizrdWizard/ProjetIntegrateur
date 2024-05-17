/****************************************
 Fichier : Reparation
 Auteur : Yassine Adibe
 Fonctionnalité : Page qui décrit la classe Reparation, à utiliser avec la database
 Date : 2024-05-03

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
import java.math.BigDecimal;
import java.util.ArrayList;

public class Reparation {

    public static ArrayList<Reparation> reparationArrayList = new ArrayList<>();
    private int id;
    private String nom;
    private String description;
    private int idStatus;
    private int idProduit;

    public Reparation() {}

    public Reparation(int id, String nom, String description,  int idStatus, int idProduit) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.idStatus = idStatus;
        this.idProduit = idProduit;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public int getIdStatus() {return idStatus;}
    public void setIdStatus(int idStatus) {this.idStatus = idStatus;}
    public int getIdProduit() {return idStatus;}
    public void setIdProduit(int idStatus) {this.idStatus = idStatus;}

    //Autre
    public static Reparation getReparationById(int idReparation) {
        for (Reparation reparation : reparationArrayList) {
            if (reparation.getId() == idReparation)
                return reparation;
        }
        return null;
    }
    public static int reparationSize() {return reparationArrayList.size();}
}
