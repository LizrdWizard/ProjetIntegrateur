package com.example.projetintegrateur;

import java.util.ArrayList;

public class Produit {

    public static ArrayList<Produit> produitArrayList = new ArrayList<>();
    private int id;
    private String nom;
    private Float prix;
    private String description;
    private int quantite;
    private int idCategorie;

    public Produit(int id, String nom, Float prix, String description, int quantite, int idCategorie) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.quantite = quantite;
        this.idCategorie = idCategorie;
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
    public int getIdCategorie() {return idCategorie;}
    public void setIdCategorie(int idCategorie) {this.idCategorie = idCategorie;}

    public static Produit getProduitById(int idProduit) {
        for (Produit produit : produitArrayList) {
            if (produit.getId() == idProduit)
                return produit;
        }
        return null;
    }

    public static int produitSize() {return produitArrayList.size();}
}
