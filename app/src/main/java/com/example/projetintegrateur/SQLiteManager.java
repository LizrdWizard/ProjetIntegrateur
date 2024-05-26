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
    private static final String ADMIN_FIELD = "admin";

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

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Peut ajouter des choses içi
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

    public void insertUser(User user)
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
        contentValues.put(PW_FIELD, user.getPw());
        contentValues.put(ADMIN_FIELD, user.isAdmin());


        database.insert(USER_TABLE_NAME, null, contentValues);

    }

    public int connectUser(String mail, String pw)
    {
        int code = 0;
        SQLiteDatabase database = this.getReadableDatabase();

        try (Cursor result = database.rawQuery("SELECT id FROM " + USER_TABLE_NAME + " WHERE mail = " + mail + " AND pw = " + pw, null)) {
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

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT id FROM " + PROV_TABLE_NAME + " WHERE " + LIBELLE_FIELD + " = " + libelle, null)) {
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

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT id FROM " + VILLE_TABLE_NAME + " WHERE " + LIBELLE_FIELD + " = " + libelle, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    output = result.getInt(0);
                }
            }
        }

        return output;
    }

}