/****************************************
 Fichier : ProduitAdapter
 Auteur : Jasmin Dubuc
 Fonctionnalité : Page qui gère la classe Produit en relation avec le layout row_inventaire_admin
 Date : 2024-08-03

 Vérification :

 =========================================================

 Historique de modifications :

 =========================================================
 ****************************************/
package com.example.projetintegrateur;

import java.util.ArrayList;

public class ProduitCommande {
    public static ArrayList<ProduitCommande> produitCommandeArrayList = new ArrayList<>();
    private int id;
    private int idProduit;
    private int idClient;
    public ProduitCommande(){}
    public ProduitCommande(int idProduit, int idClient) {
        this.idProduit = idProduit;
        this.idClient = idClient;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdProduit() {return this.idProduit;}
    public void setIdProduit(int idProduit) {this.idProduit = idProduit;}
    public int getIdClient() {return this.idClient;}
    public void setIdClient(int idClient) {this.idClient = idClient;}

    public static ArrayList<Produit> getProduitArrayListByIdClient(int idClient) {
        ArrayList<Produit> produitHolder = new ArrayList<>();

        if (!Produit.produitArrayList.isEmpty()) {
            for (ProduitCommande produitCommande : produitCommandeArrayList) {
                if (produitCommande.getIdClient() == idClient)
                    produitHolder.add(Produit.getProduitById(produitCommande.idProduit));
            }
        }

        return produitHolder;
    }
    public static void resetArrayListId() {
        ArrayList<ProduitCommande> cleanList = new ArrayList<>();
        ProduitCommande holder = new ProduitCommande();
        int i = 1;

        for (ProduitCommande produitCommande : produitCommandeArrayList) {
            holder.setId(i);
            holder.setIdProduit(produitCommande.getIdProduit());
            holder.setIdClient(produitCommande.getIdClient());
            cleanList.add(holder);
            i++;
        }
        produitCommandeArrayList = cleanList;
    }
}
