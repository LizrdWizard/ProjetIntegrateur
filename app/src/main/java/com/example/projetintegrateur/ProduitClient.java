package com.example.projetintegrateur;

import java.util.ArrayList;

public class ProduitClient {

    public static ArrayList<ProduitClient> produitClientArrayList = new ArrayList<>();

    private int id;
    private int idProduit;
    private int idClient;

    public ProduitClient(){}
    public ProduitClient(int id, int idProduit, int idClient) {
        this.id = id;
        this.idProduit = idProduit;
        this.idClient = idClient;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdProduit() {return this.idProduit;}
    public void setIdProduit(int idProduit) {this.idProduit = idProduit;}
    public int getIdClient() {return this.idClient;}
    public void setIdClient(int idClient) {this.idClient = idClient;}

    public static ArrayList<Produit> getIdProduitByIdClient(int idClient) {
        ArrayList<Produit> produitHolder = new ArrayList<>();

        if (!Produit.produitArrayList.isEmpty()) {
            for (ProduitClient produitClient : produitClientArrayList) {
                if (produitClient.getIdClient() == idClient)
                    produitHolder.add(Produit.getProduitById(produitClient.idProduit));
            }
        }

        return produitHolder;
    }

    public static void resetArrayListId() {
        ArrayList<ProduitClient> cleanList = new ArrayList<>();
        ProduitClient holder = new ProduitClient();
        int i = 1;

        for (ProduitClient produitClient : produitClientArrayList) {
            holder.setId(i);
            holder.setIdProduit(produitClient.getIdProduit());
            holder.setIdClient(produitClient.getIdClient());
            cleanList.add(holder);
            i++;
        }

        produitClientArrayList = cleanList;
    }
}
