package com.example.projetintegrateur;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    private static final String IDCATEGORIE_FIELD = "idCategorie";
    private static final String COUNTER = "Counter";
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

        ajouterCategorieDatabase(sqLiteDatabase, new Categorie(1, "Écouteur"));
        ajouterCategorieDatabase(sqLiteDatabase, new Categorie(2, "Écran"));
        ajouterCategorieDatabase(sqLiteDatabase, new Categorie(3, "Clavier"));
        ajouterCategorieDatabase(sqLiteDatabase, new Categorie(4, "Souris"));

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

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, produit.getId());
        contentValues.put(NOM_FIELD, produit.getNom());
        contentValues.put(PRIX_FIELD, produit.getPrix());
        contentValues.put(DESCRIPTION_FIELD, produit.getDescription());
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
                    int idCategorie = result.getInt(6);

                    Produit.produitArrayList.add(new Produit(id, nom, prix, description, quantite, idCategorie));
                }
            }
        }
    }
}