/****************************************
 Fichier : SQLiteManager
 Auteur : Jasmin Dubuc, Ogbeiwi Bright
 Fonctionnalité : Page qui gère la requêtes de la database
 Date : 23/04/2024

 Vérification :
 2024-05-27         Jasmin Dubuc        Approuvé?
 =========================================================

 Historique de modifications :
 26/05/2024 Merge des fonctions compte utilisateur  Merge des fonctions de la branche userAccountFork
 =========================================================
 ****************************************/
package com.example.projetintegrateur;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;

public class SQLiteManager extends SQLiteOpenHelper {
    private static SQLiteManager sqLiteManager;
    private static final String DATABASE_NAME = "SherbOrdi";
    private static final int DATABASE_VERSION = 1;
    //NOMS TABLES

    private static final String USER_TABLE_NAME = "User";
    private static final String VILLE_TABLE_NAME = "Ville";
    private static final String PROV_TABLE_NAME = "Province";
    private static final String PROV_VILLE_TABLE_NAME = "ProvinceVille";
    private static final String CATEGORIE_TABLE_NAME = "Catégories";
    private static final String STATUS_TABLE_NAME = "Status";
    private static final String REPARATION_TABLE_NAME = "Réparation";

    private static final String PRODUIT_TABLE_NAME = "Produits";
    private static final String COMMANDE_TABLE_NAME = "Commande";
    private static final String STATUSCOMMANDE_TABLE_NAME = "StatusCommande";
    private static final String PRODUITCLIENT_TABLE_NAME ="ProduitClient";
    private static final String PRODUITCOMMANDE_TABLE_NAME = "ProduitCommande";
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
    private static final String IDCOMMANDE_FIELD = "idCommande";
    private static final String COUNTER = "Counter";

    private static final String PRENOM_FIELD = "prenom";
    private static final String MAIL_FIELD = "mail";
    private static final String TEL_FIELD = "tel";
    private static final String ADDR_FIELD = "addr";
    private static final String CP_FIELD = "cp";
    private static final String ID_VILLE_FIELD = "idVille";
    private static final String ID_PROV_FIELD = "idProv";
    private static final String PW_FIELD = "pw";
    private static final String LIBELLE_FIELD = "libelle";
    private static final String ADMIN_FIELD = "admin";


    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context) {
        //Pour reset
        context.deleteDatabase(DATABASE_NAME);
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

        ajouterReparationDatabase(sqLiteDatabase, new Reparation(1, "TestNom", "TestDesc", 2, 1));

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


        //Table produitCommande
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(PRODUITCOMMANDE_TABLE_NAME)
                .append("(")
                .append(ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(LIBELLE_FIELD)
                .append(" TEXT)");
        sqLiteDatabase.execSQL(sql.toString());


        //Create Ville Table
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(VILLE_TABLE_NAME)
                .append("(")
                .append(ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(LIBELLE_FIELD)
                .append(" TEXT)");
        sqLiteDatabase.execSQL(sql.toString());

        insertVille(sqLiteDatabase, new Ville(1, "Sherbrooke"));
        insertVille(sqLiteDatabase, new Ville(2, "Magog"));
        insertVille(sqLiteDatabase, new Ville(3, "Drummondville"));
        insertVille(sqLiteDatabase, new Ville(4, "Montréal"));
        insertVille(sqLiteDatabase, new Ville(5, "Toronto"));
        insertVille(sqLiteDatabase, new Ville(6, "Ottawa"));
        insertVille(sqLiteDatabase, new Ville(7, "Kingston"));

        //Create Province Table
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(PROV_TABLE_NAME)
                .append("(")
                .append(ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(LIBELLE_FIELD)
                .append(" TEXT)");
        sqLiteDatabase.execSQL(sql.toString());

        insertProvince(sqLiteDatabase, new Province(1, "Québec"));
        insertProvince(sqLiteDatabase, new Province(2, "Ontario"));

        //Create ProvinceVille Table
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(PROV_VILLE_TABLE_NAME)
                .append("(")
                .append(ID_PROV_FIELD)
                .append(" INT, ")
                .append(ID_VILLE_FIELD)
                .append(" INT)");
        sqLiteDatabase.execSQL(sql.toString());

        insertProvinceVille(sqLiteDatabase, 1, 1);
        insertProvinceVille(sqLiteDatabase, 2, 1);
        insertProvinceVille(sqLiteDatabase, 3, 1);
        insertProvinceVille(sqLiteDatabase, 4, 1);

        insertProvinceVille(sqLiteDatabase, 5, 2);
        insertProvinceVille(sqLiteDatabase, 6, 2);
        insertProvinceVille(sqLiteDatabase, 7, 2);

        //Create User Table
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(USER_TABLE_NAME)
                .append("(")
                .append(ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(NOM_FIELD)
                .append(" TEXT, ")
                .append(PRENOM_FIELD)
                .append(" TEXT, ")
                .append(MAIL_FIELD)
                .append(" TEXT, ")
                .append(TEL_FIELD)
                .append(" TEXT, ")
                .append(ADDR_FIELD)
                .append(" TEXT, ")
                .append(CP_FIELD)
                .append(" TEXT, ")
                .append(ID_VILLE_FIELD)
                .append(" INT, ")
                .append(ID_PROV_FIELD)
                .append(" INT, ")
                .append(PW_FIELD)
                .append(" TEXT,")
                .append(ADMIN_FIELD)
                .append(" INT) ");
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

    public void insertProvince(SQLiteDatabase database, Province province)
    {
        if (database == null) {
            database = this.getWritableDatabase();
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(LIBELLE_FIELD, province.getLibelle());

        database.insert(PROV_TABLE_NAME, null, contentValues);
    }

    public void insertVille(SQLiteDatabase database, Ville ville)
    {
        if (database == null) {
            database = this.getWritableDatabase();
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIBELLE_FIELD, ville.getLibelle());

        database.insert(VILLE_TABLE_NAME, null, contentValues);
    }

    public void insertProvinceVille(SQLiteDatabase database, int idVille, int idProvince)
    {
        if (database == null) {
            database = this.getWritableDatabase();
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_VILLE_FIELD, idVille);
        contentValues.put(ID_PROV_FIELD, idProvince);


        database.insert(PROV_VILLE_TABLE_NAME, null, contentValues);
    }

    public void populateVilleListArray()
    {

        SQLiteDatabase database = this.getReadableDatabase();

        Ville.villeArrayList.clear();

        try (Cursor result = database.rawQuery("SELECT * FROM " + VILLE_TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(0);
                    String libelle = result.getString(1);
                    Ville ville = new Ville(id, libelle);

                    Ville.villeArrayList.add(ville);
                }
            }
        }

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

    public void populateProvListArray()
    {
        SQLiteDatabase database = this.getReadableDatabase();

        Province.provinceArrayList.clear();

        try (Cursor result = database.rawQuery("SELECT * FROM " + PROV_TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(0);
                    String libelle = result.getString(1);
                    Province province = new Province(id, libelle);

                    Province.provinceArrayList.add(province);
                }
            }
        }

        for (Province current : Province.provinceArrayList)
        {
            try (Cursor result = database.rawQuery("SELECT * FROM " + PROV_VILLE_TABLE_NAME + " WHERE idProv = " + current.getId(), null))
            {
                if (result.getCount() != 0)
                {
                    while (result.moveToNext())
                    {
                        int idVille = result.getInt(1);

                        int index = 0;
                        boolean found = false;

                        while(index < Ville.villeArrayList.size() && !found)
                        {
                            if(Ville.villeArrayList.get(index).getId() == idVille)
                            {
                                found = true;
                            }
                            else
                            {
                                index++;
                            }
                        }

                        if(found){
                            current.addVille((Ville.villeArrayList.get(index)));
                        }
                    }
                }
            }
        }
    }

    public long insertUser(User user)
    {
        long code = 0;

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NOM_FIELD, user.getNom());
        contentValues.put(PRENOM_FIELD, user.getPrenom());
        contentValues.put(MAIL_FIELD, user.getMail());
        contentValues.put(TEL_FIELD, user.getTel());
        contentValues.put(ADDR_FIELD, user.getAddr());
        contentValues.put(CP_FIELD, user.getCp());
        contentValues.put(ID_VILLE_FIELD, user.getIdVille());
        contentValues.put(ID_PROV_FIELD, user.getIdProv());
        contentValues.put(PW_FIELD, user.getPw());
        contentValues.put(ADMIN_FIELD, user.isAdmin());


        code = database.insert(USER_TABLE_NAME, null, contentValues);

        return code;
    }

    public int connectUser(String mail, String pw)
    {
        int code = 0;
        SQLiteDatabase database = this.getReadableDatabase();

        try (Cursor result = database.rawQuery("SELECT id FROM " + USER_TABLE_NAME + " WHERE " + MAIL_FIELD + " = '" + mail + "' AND " + PW_FIELD + " = '" + pw + "'", null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    User.currentUserID = result.getInt(0);
                }
            }
            else
            {
                code = 1;
            }
        }
        return code;
    }

    public void disconnectUser()
    {
        User.currentUserID = 0;
    }

    public void updateUser(User user)
    {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NOM_FIELD, user.getNom());
        contentValues.put(PRENOM_FIELD, user.getPrenom());
        contentValues.put(MAIL_FIELD, user.getMail());
        contentValues.put(TEL_FIELD, user.getTel());
        contentValues.put(ADDR_FIELD, user.getAddr());
        contentValues.put(CP_FIELD, user.getCp());
        contentValues.put(ID_VILLE_FIELD, user.getIdVille());
        contentValues.put(ID_PROV_FIELD, user.getIdProv());
        contentValues.put(ADMIN_FIELD, user.isAdmin());

        database.update(USER_TABLE_NAME, contentValues, "id = ?", new String[]{Integer.toString(User.currentUserID)});
    }

    public void updateUserPW(String oldpw, String newpw)
    {
        SQLiteDatabase database = this.getWritableDatabase();

        int code = 0;

        try (Cursor result = database.rawQuery("SELECT pw FROM " + USER_TABLE_NAME + " WHERE id = " + User.currentUserID, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    if(!Objects.equals(result.getString(0), oldpw))
                    {
                        code = 1; //password does not match
                    }
                }
            }
        }

        if(code == 0)
        {
            ContentValues args = new ContentValues();
            args.put(PW_FIELD, newpw);
            database.update(USER_TABLE_NAME, args, "id = ?", new String[]{Integer.toString(User.currentUserID)});
        }
    }

    //dump all user
    public ArrayList<User> selectUsers()
    {
        SQLiteDatabase database = this.getReadableDatabase();

        ArrayList<User> users = new ArrayList<User>();

        try (Cursor result = database.rawQuery("SELECT * FROM " + USER_TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {

                    users.add(new User(result.getInt(0),
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            result.getString(5),
                            result.getString(6),
                            result.getInt(7),
                            result.getInt(8),
                            result.getString(9),
                            result.getInt(10)));

                }
            }
        }

        return users;
    }

    public User selectUserById(int id)
    {
        User output = null;


        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE id = " + id, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    output = new User(result.getInt(0),
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            result.getString(5),
                            result.getString(6),
                            result.getInt(7),
                            result.getInt(8),
                            result.getString(9),
                            result.getInt(10));

                }
            }
        }

        return output;
    }

    public int getProvinceIdByName(String libelle)
    {
        int output = 0;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT id FROM " + PROV_TABLE_NAME + " WHERE " + LIBELLE_FIELD + " = '" + libelle + "'", null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    output = result.getInt(0);
                }
            }
        }

        return output;
    }

    public int getVilleIdByName(String libelle)
    {
        int output = 0;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT id FROM " + VILLE_TABLE_NAME + " WHERE " + LIBELLE_FIELD + " = '" + libelle + "'", null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    output = result.getInt(0);
                }
            }
        }

        return output;
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
        contentValues.put(IDSTATUS_FIELD, commande.getIdStatus());
        contentValues.put(IDCLIENT_FIELD, commande.getIdClient());

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
    public void deleteProduitClient(String id) {

        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(PRODUITCLIENT_TABLE_NAME, "id=?", new String[]{id});
        db.close();
    }
    public void ajouterProduitCommandeDatabase(SQLiteDatabase database, ProduitCommande produitCommande) {
        if (database == null) {
            database = this.getWritableDatabase();
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, produitCommande.getId());
        contentValues.put(IDPRODUIT_FIELD, produitCommande.getIdProduit());
        contentValues.put(IDCOMMANDE_FIELD, produitCommande.getIdCommande());

        database.insert(PRODUITCOMMANDE_TABLE_NAME, null, contentValues);
    }
    public void populateProduitCommandeListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ProduitCommande.produitCommandeArrayList.clear();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + PRODUITCOMMANDE_TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(1);
                    int idProduit= result.getInt(2);
                    int idCommande= result.getInt(3);
                    ProduitCommande produitCommande = new ProduitCommande(id, idProduit,idCommande);
                    ProduitCommande.produitCommandeArrayList.add(produitCommande);
                }
            }
        }
    }
}