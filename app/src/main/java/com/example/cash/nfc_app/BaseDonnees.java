package com.example.cash.nfc_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cash on 01/02/18.
 */

public class BaseDonnees extends SQLiteOpenHelper {

    private static final String table_etudiant = "table_etudiant";
    private static final String serial_number = "serial_number";
    private static final String nom = "nom";
    private static final String prenom = "prenom";
    private static final String etat = "etat";

    private static final String create_bd = "CREATE TABLE " + table_etudiant + " ("
            + serial_number + " TEXT PRIMARY KEY, "
            + nom + " TEXT NOT NULL, "
            + prenom + " TEXT NOT NULL, "
            + etat + " INTEGER DEFAULT 0);";

    public BaseDonnees(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version){
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL(create_bd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {
        bd.execSQL("DROP TABLE " + table_etudiant + ";");
        onCreate(bd);
    }
}
