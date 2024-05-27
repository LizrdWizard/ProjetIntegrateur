/****************************************
 Fichier : User.java
 Auteur : Ogbeiwi Bright
 Fonctionnalité : Implementation en objet de la table User de la DB
 Date : 21/05/2024
 ****************************************/

package com.example.projetintegrateur;


public class User {
    public static int currentUserID = 0;

    private int id;
    private String nom;
    private String prenom;
    private String mail;
    private String tel;
    private String addr;
    private String cp;
    private int idVille;
    private int idProv;
    private String pw;

    private int admin;

    public User() {}

    public User(int id, String nom, String prenom, String mail, String tel, String addr, String cp, int idVille, int idProv, String pw, int admin) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.tel = tel;
        this.addr = addr;
        this.cp = cp;
        this.idVille = idVille;
        this.idProv = idProv;
        this.pw = pw;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public int getIdVille() {
        return idVille;
    }

    public void setIdVille(int idVille) {
        this.idVille = idVille;
    }

    public int getIdProv() {
        return idProv;
    }

    public void setIdProv(int idProv) {
        this.idProv = idProv;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public int isAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }
}
