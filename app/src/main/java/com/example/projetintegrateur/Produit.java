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
import java.math.BigDecimal;
import java.util.ArrayList;

public class Produit {

    public static ArrayList<Produit> produitArrayList = new ArrayList<>();
    private int id;
    private String nom;
    private Float prix;
    private String description;
    private int quantite;
    private Bitmap photo;
    private int idCategorie;

    public Produit() {}

    public Produit(int id, String nom, Float prix, String description, int quantite, Bitmap photo, int idCategorie) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.quantite = quantite;
        this.photo = photo;
        this.idCategorie = idCategorie;
    }

    public Produit(Produit produit) {
        this.id = produit.getId();
        this.nom = produit.getNom();
        this.prix = produit.getPrix();
        this.description = produit.getDescription();
        this.quantite = produit.getQuantite();
        this.photo = produit.getPhoto();
        this.idCategorie = produit.getIdCategorie();
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}
    public Float getPrix() {return prix;}
    public void setPrix(Float prix) {this.prix = prix;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public int getQuantite() {return quantite;}
    public void setQuantite(int quantite) {this.quantite = quantite;}
    public Bitmap getPhoto() {return photo;}
    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
    public int getIdCategorie() {return idCategorie;}
    public void setIdCategorie(int idCategorie) {this.idCategorie = idCategorie;}

    //Autre
    public static Produit getProduitById(int idProduit) {
        for (Produit produit : produitArrayList) {
            if (produit.getId() == idProduit)
                return produit;
        }
        return null;
    }
    public byte[] getPhotoByte() {

        try {
            Bitmap bmp = this.photo;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 0, stream);
            return stream.toByteArray();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Bitmap toBitmap(byte[] photo) {
        try {
            return BitmapFactory.decodeByteArray(photo, 0, photo.length);
        }
        catch(NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static int produitSize() {return produitArrayList.size();}
}
