package com.example.projetintegrateur;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Objects;

public class SQLiteManager extends SQLiteOpenHelper {
    private static SQLiteManager sqLiteManager;
    private static final String DATABASE_NAME = "SherbOrdi";
    private static final int DATABASE_VERSION = 1;
    //NOMS TABLES
    private static final String CAT_TABLE_NAME = "Catégories";
    private static final String USER_TABLE_NAME = "User";
    private static final String VILLE_TABLE_NAME = "Ville";
    private static final String PROV_TABLE_NAME = "Province";
    private static final String PROV_VILLE_TABLE_NAME = "ProvinceVille";

    //NOMS FIELDS
    private static final String ID_FIELD = "id";
    private static final String NOM_FIELD = "nom";
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

    private static final String USER_ACCOUNT_FILE = "userfile.txt";


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
        //Table programmes
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(CAT_TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" TEXT, ")
                .append(NOM_FIELD)
                .append(" TEXT, ");
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
                .append(" TEXT)");
        sqLiteDatabase.execSQL(sql.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Peut ajouter des choses içi
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
                    String description = result.getString(3);
                    Categorie categorie = new Categorie(id, nom);
                    Categorie.categorieArrayList.add(categorie);
                }
            }
        }
    }


    //populate db from files

    public void populateVilleListArray()
    {

        SQLiteDatabase database = this.getReadableDatabase();

        Ville.villeArrayList.clear();

        try (Cursor result = database.rawQuery("SELECT * FROM " + VILLE_TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(1);
                    String libelle = result.getString(2);
                    Ville ville = new Ville(id, libelle);

                    Ville.villeArrayList.add(ville);
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
                    int id = result.getInt(1);
                    String libelle = result.getString(2);
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
                        int idVille = result.getInt(2);

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

    public void insertUserFromFile(SQLiteDatabase database, Context context)
    {
        long code = 0;
        ArrayList<String> users = new ArrayList<String>();

        if (database == null) {
            database = this.getWritableDatabase();
        }

        //load user from file
        users = FileManager.loadFromFile(USER_ACCOUNT_FILE, context);

        for (String content : users) {

            String[] elements = content.split(" +"); //one or more spaces

            ContentValues contentValues = new ContentValues();
            contentValues.put(ID_FIELD, elements[0]);
            contentValues.put(NOM_FIELD, elements[1]);
            contentValues.put(PRENOM_FIELD, elements[2]);
            contentValues.put(MAIL_FIELD, elements[3]);
            contentValues.put(TEL_FIELD, elements[4]);
            contentValues.put(ADDR_FIELD, elements[5]);
            contentValues.put(CP_FIELD, elements[6]);
            contentValues.put(ID_VILLE_FIELD, elements[7]);
            contentValues.put(ID_PROV_FIELD, elements[8]);
            contentValues.put(PW_FIELD, elements[9]);

           database.insert(USER_TABLE_NAME, null, contentValues);
        }

    }

    public void writeUserIntoFile(SQLiteDatabase database, Context context)
    {

        ArrayList<String> output = new ArrayList<String>();

        //select all user
        try (Cursor result = database.rawQuery("SELECT * FROM " + USER_TABLE_NAME, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {

                    String user = Integer.toString(result.getInt(1)) +
                            " " +
                            result.getString(2) +
                            " " +
                            result.getString(3) +
                            " " +
                            result.getString(4) +
                            " " +
                            result.getString(5) +
                            " " +
                            result.getString(6) +
                            " " +
                            result.getInt(7) +
                            " " +
                            result.getInt(8) +
                            " " +
                            result.getString(9) +
                            " " +
                            result.getString(10) +
                            " ";

                    output.add(user);
                }
            }
        }

        FileManager.saveToFile(output, USER_ACCOUNT_FILE, context);

    }
    //create user(put user in a txt file)
    public long insertUser(User user)
    {
        SQLiteDatabase database = this.getReadableDatabase();

        long code = 0;

        if (database == null) {
            database = this.getWritableDatabase();
        }

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

        code = database.insert(USER_TABLE_NAME, null, contentValues);

        return code; //-1 if error
    }

    //look for user and verify info
    public int connectUser(String mail, String pw)
    {
        SQLiteDatabase database = this.getReadableDatabase();

        int code = 0;

        try (Cursor result = database.rawQuery("SELECT id FROM " + USER_TABLE_NAME + " WHERE mail = " + mail + " AND pw = " + pw, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    User.currentUserID = result.getInt(1);
                }
            }
            else
            {
                code = 1; //user not found
            }
        }
        return code;
    }

    //disconnect user
    public void disconnectUser()
    {
        User.currentUserID = 0;
    }

    //update user info
    public void updateUser(User user)
    {
        SQLiteDatabase database = this.getReadableDatabase();

        if (database == null) {
            database = this.getWritableDatabase();
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(NOM_FIELD, user.getNom());
        contentValues.put(PRENOM_FIELD, user.getPrenom());
        contentValues.put(MAIL_FIELD, user.getMail());
        contentValues.put(TEL_FIELD, user.getTel());
        contentValues.put(ADDR_FIELD, user.getAddr());
        contentValues.put(CP_FIELD, user.getCp());
        contentValues.put(ID_VILLE_FIELD, user.getIdVille());
        contentValues.put(ID_PROV_FIELD, user.getIdProv());

        database.update(USER_TABLE_NAME, contentValues, "id = ?", new String[]{Integer.toString(User.currentUserID)});
    }

    //update user PW
    public void updateUserPW(String oldpw, String newpw)
    {
        SQLiteDatabase database = this.getReadableDatabase();

        int code = 0;

        try (Cursor result = database.rawQuery("SELECT pw FROM " + USER_TABLE_NAME + " WHERE id = " + User.currentUserID, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    if(!Objects.equals(result.getString(1), oldpw))
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

                    users.add(new User(result.getInt(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            result.getString(5),
                            result.getString(6),
                            result.getString(7),
                            result.getInt(8),
                            result.getInt(9),
                            result.getString(10)));

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
                    id = result.getInt(1);
                    String nom = result.getString(2);
                    String prenom = result.getString(3);
                    String mail = result.getString(4);
                    String tel = result.getString(5);
                    String cp = result.getString(6);
                    int idVille = result.getInt(7);
                    int idProv = result.getInt(8);

                }
            }
            output = new User();
        }

        return output;
    }
}