/****************************************
 Fichier : Catégorie
 Auteur : Jasmin Dubuc
 Fonctionnalité : Page qui décrit la classe Catégorie, à lier avec la classe Produit et à utiliser avec la database
 Date : 2024-08-03

 Vérification :
 2024-05-27         Jasmin Dubuc        Approuvé
 =========================================================
 ****************************************/
package com.example.projetintegrateur;
import java.util.ArrayList;

public class Categorie {
    public static ArrayList<Categorie> categorieArrayList = new ArrayList<>();
    private int id;
    private String nom;
    public Categorie(int id, String nom) {
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
    public static Categorie getCategorieById(int idCategorie) {
        for (Categorie categorie : categorieArrayList) {
            if (categorie.getId() == idCategorie)
                return categorie;
        }
        return null;
    }
}