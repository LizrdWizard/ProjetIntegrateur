/****************************************
 Fichier : SQLiteManager
 Auteur : Jasmin Dubuc
 Fonctionnalité : Page qui gère la requêtes de la database
 Date : 2024-08-03

 Vérification :
 *Date*               *Nom*             *Approuvé*
 =========================================================

 Historique de modifications :

 =========================================================
 ****************************************/
package com.example.projetintegrateur;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.math.BigDecimal;
import java.util.ArrayList;

public class SQLiteManager extends SQLiteOpenHelper {
    private static SQLiteManager sqLiteManager;
    private static final String DATABASE_NAME = "SherbOrdi";
    private static final int DATABASE_VERSION = 1;
    //NOMS TABLES
    private static final String CAT_TABLE_NAME = "Catégories";
    private static final String PRODUIT_TABLE_NAME = "Produits";
    //NOMS FIELDS
    private static final String ID_FIELD = "id";
    private static final String NOM_FIELD = "nom";
    private static final String PRIX_FIELD = "prix";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String QUANTITE_FIELD = "quantite";
    private static final String PHOTO_FIELD = "photo";
    private static final String IDCATEGORIE_FIELD = "idCategorie";
    private static final String COUNTER = "Counter";
    //À ENLEVER APRÈS CAMÉRA
    public static Bitmap PHOTO_TEMP;
    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context) {
        if (sqLiteManager == null) {
            sqLiteManager = new SQLiteManager(context);
        }
        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sql;
        //Table catégories
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(CAT_TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(NOM_FIELD)
                .append(" TEXT)");
        sqLiteDatabase.execSQL(sql.toString());

        ajouterCategorieDatabase(sqLiteDatabase, new Categorie(1, "Processeur"));
        ajouterCategorieDatabase(sqLiteDatabase, new Categorie(2, "RAM"));
        ajouterCategorieDatabase(sqLiteDatabase, new Categorie(3, "GPU"));
        ajouterCategorieDatabase(sqLiteDatabase, new Categorie(4, "Écouteur"));
        ajouterCategorieDatabase(sqLiteDatabase, new Categorie(5, "Écran"));
        ajouterCategorieDatabase(sqLiteDatabase, new Categorie(6, "Clavier"));
        ajouterCategorieDatabase(sqLiteDatabase, new Categorie(7, "Souris"));

        //Table produits
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(PRODUIT_TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(NOM_FIELD)
                .append(" TEXT, ")
                .append(PRIX_FIELD)
                .append(" FLOAT, ")
                .append(DESCRIPTION_FIELD)
                .append(" TEXT, ")
                .append(QUANTITE_FIELD)
                .append(" INT, ")
                .append(PHOTO_FIELD)
                .append(" BLOB, ")
                .append(IDCATEGORIE_FIELD)
                .append(" INT, ")
                .append(" FOREIGN KEY (")
                .append(IDCATEGORIE_FIELD)
                .append(") REFERENCES ")
                .append(CAT_TABLE_NAME)
                .append("(")
                .append(IDCATEGORIE_FIELD)
                .append("));");
        sqLiteDatabase.execSQL(sql.toString());

        ajouterProduitDatabase(sqLiteDatabase, new Produit(1, "Processeur1", BigDecimal.valueOf(349.99), "Bon processeur pour le prix, super fort", 5, null, 1));
        ajouterProduitDatabase(sqLiteDatabase, new Produit(2, "Barrettes de RAM1", BigDecimal.valueOf(99.99), "Deux barettes de 16Gb DDR5", 3, null, 2));
        ajouterProduitDatabase(sqLiteDatabase, new Produit(3, "Graphic Processing Unit1", BigDecimal.valueOf(799.99), "Deux barettes de 16Gb DDR5", 1, null, 3));
        ajouterProduitDatabase(sqLiteDatabase, new Produit(4, "Bose QC45", BigDecimal.valueOf(349.99), "Noise cancelling, bluetooth, adjustable", 4, null, 4));
        ajouterProduitDatabase(sqLiteDatabase, new Produit(5, "Écran LG", BigDecimal.valueOf(599.99), "4k resolution avec 120Hz, très fiable", 6, null, 5));
        ajouterProduitDatabase(sqLiteDatabase, new Produit(2, "Clavier modulaire", BigDecimal.valueOf(199.99), "Clavier modulaire et qui peut afficher toutes les couleurs de l'arc en ciel", 9, null, 6));
        ajouterProduitDatabase(sqLiteDatabase, new Produit(2, "Souris sans fil", BigDecimal.valueOf(1999.99), "Aim-hack intégré. Last-hit les minions pour toi", 1, null, 7));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS" + PRODUIT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS" + CAT_TABLE_NAME);
        onCreate(db);
    }

    public void ajouterCategorieDatabase(SQLiteDatabase database, Categorie categorie) {
        if (database == null) {
            database = this.getWritableDatabase();
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, categorie.getId());
        contentValues.put(NOM_FIELD, categorie.getNom());

        database.insert(CAT_TABLE_NAME, null, contentValues);
    }

    public void populateCategorieListeArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Categorie.categorieArrayList.clear();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + CAT_TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(1);
                    String nom = result.getString(2);
                    Categorie categorie = new Categorie(id, nom);
                    Categorie.categorieArrayList.add(categorie);
                }
            }
        }
    }

    public void ajouterProduitDatabase(SQLiteDatabase database, Produit produit) {
        if (database == null) {
            database = this.getWritableDatabase();
        }

        //En attendant la fonction caméra
        produit.setPhoto(PHOTO_TEMP);

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, produit.getId());
        contentValues.put(NOM_FIELD, produit.getNom());
        contentValues.put(PRIX_FIELD, produit.getPrix().floatValue());
        contentValues.put(DESCRIPTION_FIELD, produit.getDescription());
        contentValues.put(QUANTITE_FIELD, produit.getQuantite());
        contentValues.put(PHOTO_FIELD, produit.getPhotoByte());
        contentValues.put(IDCATEGORIE_FIELD, produit.getIdCategorie());

        database.insert(PRODUIT_TABLE_NAME, null, contentValues);
    }

    public void populateProduitListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Produit.produitArrayList.clear();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + PRODUIT_TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(1);
                    String nom = result.getString(2);
                    Float prix = result.getFloat(3);
                    String description = result.getString(4);
                    int quantite = result.getInt(5);
                    byte[] image = result.getBlob(6);
                    int idCategorie = result.getInt(7);

                    Produit.produitArrayList.add(new Produit(id, nom, BigDecimal.valueOf(prix), description, quantite, Produit.toBitmap(image), idCategorie));
                }
            }
        }
    }
}