package com.example.projetintegrateur;

import java.util.ArrayList;

public class Province {

    public static ArrayList<Province> provinceArrayList = new ArrayList<Province>();

    private int id;
    private String libelle;

    private ArrayList<Ville> villes = new ArrayList<Ville>();

    public Province(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public ArrayList<Ville> getVilles() {
        return villes;
    }

    public void addVille(Ville ville)
    {
        if(!villes.contains(ville))
        {
            villes.add(ville);
        }
    }

    public void removeVille(int id)
    {
        assert(id > -1 && id < villes.size());
        villes.remove(id);
    }

    public void addProvince(Province province)
    {
        if(!provinceArrayList.contains(province))
        {
            provinceArrayList.add(province);
        }
    }

    public void removeProvince(int id)
    {
        assert(id > -1 && id < provinceArrayList.size());
        provinceArrayList.remove(id);
    }
}

