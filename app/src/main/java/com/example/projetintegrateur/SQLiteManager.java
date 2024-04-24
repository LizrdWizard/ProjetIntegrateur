package com.example.projetintegrateur;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteManager extends SQLiteOpenHelper {
    private static SQLiteManager sqLiteManager;
    private static final String DATABASE_NAME = "SherbOrdi";
    private static final int DATABASE_VERSION = 1;
    //NOMS TABLES
    private static final String CAT_TABLE_NAME = "Catégories";
    //NOMS FIELDS
    private static final String ID_FIELD = "id";
    private static final String NOM_FIELD = "nom";
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
}