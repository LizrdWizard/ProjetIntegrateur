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

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.text.ParseException;
import java.util.ArrayList;

public class SQLiteManager extends SQLiteOpenHelper {
    private static SQLiteManager sqLiteManager;
    private static final String DATABASE_NAME = "SherbOrdi";
    private static final int DATABASE_VERSION = 1;
    //NOMS TABLES
    private static final String CATEGORIE_TABLE_NAME = "Catégories";
    private static final String STATUS_TABLE_NAME = "Status";
    private static final String REPARATION_TABLE_NAME = "Réparation";

    private static final String PRODUIT_TABLE_NAME = "Produits";
    private static final String COMMANDE_TABLE_NAME = "Commande";
    private static final String STATUSCOMMANDE_TABLE_NAME = "StatusCommande";
    private static final String PRODUITCLIENT_TABLE_NAME ="ProduitClient";
    //NOMS FIELDS
    private static final String ID_FIELD = "id";
    private static final String NOM_FIELD = "nom";
    private static final String PRIX_FIELD = "prix";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String QUANTITE_FIELD = "quantite";
    private static final String PHOTO_FIELD = "photo";
    private static final String IDCATEGORIE_FIELD = "idCategorie";
    private static final String IDSTATUS_FIELD = "idStatus";
    private static final String IDPRODUIT_FIELD = "idProduit";
    private static final String DATEDEBUT_FIELD = "dateDebut";
    private static final String DATEFIN_FIELD = "dateFin";
    private static final String IDCLIENT_FIELD = "idClient";
    private static final String COUNTER = "Counter";

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context) {
        //Pour reset
        //context.deleteDatabase(DATABASE_NAME);
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
                .append(CATEGORIE_TABLE_NAME)
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

        //Table status
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(STATUS_TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(NOM_FIELD)
                .append(" TEXT)");
        sqLiteDatabase.execSQL(sql.toString());

        ajouterStatusDatabase(sqLiteDatabase, new Status(1, "Pas commencée"));
        ajouterStatusDatabase(sqLiteDatabase, new Status(2, "En cours"));
        ajouterStatusDatabase(sqLiteDatabase, new Status(3, "Terminée"));

        //Table réparation
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(REPARATION_TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(NOM_FIELD)
                .append(" TEXT,")
                .append(DESCRIPTION_FIELD)
                .append(" TEXT, ")
                .append(IDSTATUS_FIELD)
                .append(" INT, ")
                .append(IDPRODUIT_FIELD)
                .append(" INT, ")
                .append(" FOREIGN KEY (")
                .append(IDSTATUS_FIELD)
                .append(") REFERENCES ")
                .append(STATUS_TABLE_NAME)
                .append("(")
                .append(IDSTATUS_FIELD)
                .append("), ")
                .append(" FOREIGN KEY (")
                .append(IDPRODUIT_FIELD)
                .append(") REFERENCES ")
                .append(PRODUIT_TABLE_NAME)
                .append("(")
                .append(IDPRODUIT_FIELD)
                .append("));");
        sqLiteDatabase.execSQL(sql.toString());

        ajouterReparationDatabase(sqLiteDatabase, new Reparation(1, "Jean guy", "sa marche pas", 2, 1));

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
                .append(CATEGORIE_TABLE_NAME)
                .append("(")
                .append(IDCATEGORIE_FIELD)
                .append("));");
        sqLiteDatabase.execSQL(sql.toString());


        //Table produitClient
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(PRODUITCLIENT_TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(IDPRODUIT_FIELD)
                .append(" INT, ")
                .append(IDCLIENT_FIELD)
                .append(" INT)");
        sqLiteDatabase.execSQL(sql.toString());
        //Table Commande
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(COMMANDE_TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(DATEDEBUT_FIELD)
                .append(" DATE, ")
                .append(DATEFIN_FIELD)
                .append(" DATE, ")
                .append(DESCRIPTION_FIELD)
                .append(" TEXT, ")
                .append(IDSTATUS_FIELD)
                .append(" INT, ")
                .append(IDCLIENT_FIELD)
                .append(" INT); ");
        sqLiteDatabase.execSQL(sql.toString());
        //Table STATUSCOMMANDE
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(STATUSCOMMANDE_TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(NOM_FIELD)
                .append(" TEXT );");
        sqLiteDatabase.execSQL(sql.toString());
        ajouterStatusCommandeDatabase(sqLiteDatabase, new StatusCommande(1, "Pas commencée"));
        ajouterStatusCommandeDatabase(sqLiteDatabase, new StatusCommande(2, "En cours"));
        ajouterStatusCommandeDatabase(sqLiteDatabase, new StatusCommande(3, "Terminée"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + PRODUIT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + STATUS_TABLE_NAME) ;
        db.execSQL("DROP TABLE IF EXISTS " + REPARATION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + COMMANDE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + STATUSCOMMANDE_TABLE_NAME);
        onCreate(db);
    }

    public void ajouterCategorieDatabase(SQLiteDatabase database, Categorie categorie) {
        if (database == null) {
            database = this.getWritableDatabase();
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, categorie.getId());
        contentValues.put(NOM_FIELD, categorie.getNom());

        database.insert(CATEGORIE_TABLE_NAME, null, contentValues);
    }
    public void ajouterStatusDatabase(SQLiteDatabase database, Status status) {
        if (database == null) {
            database = this.getWritableDatabase();
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, status.getId());
        contentValues.put(NOM_FIELD, status.getNom());

        database.insert(STATUS_TABLE_NAME, null, contentValues);
    }

    public void populateCategorieListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Categorie.categorieArrayList.clear();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + CATEGORIE_TABLE_NAME, null)) {
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
    public void populateStatusListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Status.statusArrayList.clear();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + STATUS_TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(1);
                    String nom = result.getString(2);
                    Status status = new Status(id, nom);
                    Status.statusArrayList.add(status);
                }
            }
        }
    }

    public void ajouterProduitDatabase(SQLiteDatabase database, Produit produit) {
        if (database == null) {
            database = this.getWritableDatabase();
        }
        ContentValues contentValues = new ContentValues();

        //Les produits initialisés dans cette classe on toujours le bon Id
        //Les produits ajoutés plus tard utilisent Produit.produitSize() + 1 pour déterminer leur Id
        if (Produit.produitArrayList.isEmpty()) {contentValues.put(ID_FIELD, 1);}
        else {contentValues.put(ID_FIELD, Produit.produitArrayList.size() + 1);}

        contentValues.put(NOM_FIELD, produit.getNom());
        contentValues.put(PRIX_FIELD, produit.getPrix().floatValue());
        contentValues.put(DESCRIPTION_FIELD, produit.getDescription());
        contentValues.put(QUANTITE_FIELD, produit.getQuantite());
        contentValues.put(PHOTO_FIELD, produit.getPhotoByte());
        contentValues.put(IDCATEGORIE_FIELD, produit.getIdCategorie());

        database.insert(PRODUIT_TABLE_NAME, null, contentValues);
    }
    public void ajouterReparationDatabase(SQLiteDatabase database, Reparation reparation) {
        if (database == null) {
            database = this.getWritableDatabase();
        }
        ContentValues contentValues = new ContentValues();

        if (reparation.getId() != 0) {contentValues.put(ID_FIELD, reparation.getId());}
        else {contentValues.put(ID_FIELD, Reparation.reparationSize() + 1);}

        contentValues.put(NOM_FIELD, reparation.getNom());
        contentValues.put(DESCRIPTION_FIELD, reparation.getDescription());
        contentValues.put(IDSTATUS_FIELD, reparation.getIdStatus());
        contentValues.put(IDPRODUIT_FIELD, reparation.getIdProduit());

        database.insert(REPARATION_TABLE_NAME, null, contentValues);
    }

    public void updaterProduitDatabase(SQLiteDatabase database, Produit produit) {
        if (database == null) {
            database = this.getWritableDatabase();
        }

        StringBuilder sql;
        sql = new StringBuilder()
                .append(ID_FIELD)
                .append(" = '")
                .append(produit.getId())
                .append("'");
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOM_FIELD, produit.getNom());
        contentValues.put(PRIX_FIELD, produit.getPrix());
        contentValues.put(DESCRIPTION_FIELD, produit.getDescription());
        contentValues.put(QUANTITE_FIELD, produit.getQuantite());
        contentValues.put(PHOTO_FIELD, produit.getPhotoByte());
        contentValues.put(IDCATEGORIE_FIELD, produit.getIdCategorie());

        database.update(PRODUIT_TABLE_NAME, contentValues, sql.toString(), null);
    }

    public void updaterReparationDatabase(SQLiteDatabase database, Reparation reparation) {
        if (database == null) {
            database = this.getWritableDatabase();
        }

        StringBuilder sql;
        sql = new StringBuilder()
                .append(ID_FIELD)
                .append(" = '")
                .append(reparation.getId())
                .append("'");
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOM_FIELD, reparation.getNom());
        contentValues.put(IDSTATUS_FIELD, reparation.getIdStatus());

        database.update(REPARATION_TABLE_NAME, contentValues, sql.toString(), null);
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

                    Produit.produitArrayList.add(new Produit(id, nom, prix, description, quantite, Produit.toBitmap(image), idCategorie));
                }
            }
        }
    }
    public void populateReparationListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Reparation.reparationArrayList.clear();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + REPARATION_TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(1);
                    String nom = result.getString(2);
                    String description = result.getString(3);
                    int idStatus = result.getInt(4);
                    int idProduit = result.getInt(4);

                    Reparation.reparationArrayList.add(new Reparation(id, nom, description, idStatus, idProduit));
                }
            }
        }
    }

    public void ajouterProduitClientDatabase(SQLiteDatabase database, ProduitClient produitClient) {
        if (database == null) {
            database = this.getWritableDatabase();
        }

        produitClient.setId(ProduitClient.produitClientArrayList.size() + 1);

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, produitClient.getId());
        contentValues.put(IDPRODUIT_FIELD, produitClient.getIdProduit());
        contentValues.put(IDCLIENT_FIELD, produitClient.getIdClient());

        database.insert(PRODUITCLIENT_TABLE_NAME, null, contentValues);
    }

    public void populateProduitClientListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        ProduitClient.produitClientArrayList.clear();
        ProduitClient produitClient = new ProduitClient();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + PRODUITCLIENT_TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    produitClient.setId(result.getInt(1));
                    produitClient.setIdProduit(result.getInt(2));
                    produitClient.setIdClient(result.getInt(3));
                    ProduitClient.produitClientArrayList.add(produitClient);
                }
            }
        }
    }
    public void ajouterStatusCommandeDatabase(SQLiteDatabase database, StatusCommande statusCommande) {
        if (database == null) {
            database = this.getWritableDatabase();
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, statusCommande.getId());
        contentValues.put(NOM_FIELD, statusCommande.getNom());

        database.insert(STATUSCOMMANDE_TABLE_NAME, null, contentValues);
    }
    public void populateStatusCommandeListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        StatusCommande.statusCommandeArrayList.clear();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + STATUSCOMMANDE_TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(1);
                    String nom = result.getString(2);
                    StatusCommande statusCommande = new StatusCommande(id, nom);
                    StatusCommande.statusCommandeArrayList.add(statusCommande);
                }
            }
        }
    }
    public void ajouterCommandeDatabase(SQLiteDatabase database, Commande commande) {
        if (database == null) {
            database = this.getWritableDatabase();
        }
        ContentValues contentValues = new ContentValues();

        if (Commande.commandeSize() == 0) {contentValues.put(ID_FIELD, 1);}
        else {contentValues.put(ID_FIELD, Commande.commandeSize() + 1);}

        contentValues.put(DATEDEBUT_FIELD, "commande.getDateDebut()");
        contentValues.put(DATEFIN_FIELD, "commande.getDateFin()");
        contentValues.put(DESCRIPTION_FIELD, commande.getDescription());
        contentValues.put(PHOTO_FIELD, commande.getIdStatus());
        contentValues.put(IDCATEGORIE_FIELD, commande.getIdClient());

        database.insert(COMMANDE_TABLE_NAME, null, contentValues);
    }
    public void populateCommandeListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Commande.commandeArrayList.clear();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + COMMANDE_TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(1);
                    String sDateDebut = result.getString(2);
                    String sDateFin = result.getString(3);
                    String description = result.getString(4);
                    int idStatus = result.getInt(5);
                    int idClient = result.getInt(6);
                    Date dateDebut = getDateFromString(sDateDebut);
                    Date dateFin = getDateFromString(sDateFin);
                    Commande.commandeArrayList.add(new Commande(id, dateDebut, dateFin, description, idStatus, idClient));
                }
            }
        }
    }
    private String getStringFromDate(Date date){
        if(date == null){
            return null;
        }
        return dateFormat.format(date);

    }
    private Date getDateFromString(String string){
        try{
            return dateFormat.parse(string);
        } catch(NullPointerException | ParseException e){
            return null;
        }
    }

}