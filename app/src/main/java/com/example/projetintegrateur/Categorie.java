package com.example.projetintegrateur;

import java.util.ArrayList;
import java.util.Objects;

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
    public static int provinceSize() {return categorieArrayList.size();}
}