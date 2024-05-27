package com.example.projetintegrateur;

import java.util.ArrayList;

public class ProduitCommande {

    public static ArrayList<ProduitCommande> produitCommandeArrayList = new ArrayList<>();

    private int id;
    private int idProduit;
    private int idCommande;

    public ProduitCommande(){}
    public ProduitCommande(int id, int idProduit, int idCommande) {
        this.id = id;
        this.idProduit = idProduit;
        this.idCommande = idCommande;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdProduit() {return this.idProduit;}
    public void setIdProduit(int idProduit) {this.idProduit = idProduit;}
    public int getIdCommande() {return this.idCommande;}
    public void setIdCommande(int idCommande) {this.idCommande = idCommande;}

    public static ArrayList<Produit> getIdProduitByIdClient(int idCommande) {
        ArrayList<Produit> produitHolder = new ArrayList<>();

        if (!Produit.produitArrayList.isEmpty()) {
            for (ProduitCommande produitCommande : produitCommandeArrayList) {
                if (produitCommande.getIdCommande() == idCommande)
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
            holder.setIdCommande(produitCommande.getIdCommande());
            cleanList.add(holder);
            i++;
        }

        produitCommandeArrayList = cleanList;
    }
}
