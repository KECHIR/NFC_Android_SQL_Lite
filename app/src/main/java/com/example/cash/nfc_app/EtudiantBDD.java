package com.example.cash.nfc_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cash on 01/02/18.
 */

public class EtudiantBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "etudiant.db";

    private static final String table_etudiant = "table_etudiant";
    private static final String serial_number = "serial_number";
    private static final int NUM_COL_SN = 0;
    private static final String nom = "nom";
    private static final int NUM_COL_NOM = 1;
    private static final String prenom = "prenom";
    private static final int NUM_COL_PRENOM = 2;
    private static final String etat = "etat";
    private static final int NUM_COL_ETAT = 3;

    private SQLiteDatabase bdd;

    private BaseDonnees maBaseSQLite;

    public EtudiantBDD(Context context){
        //On crée la BDD et sa table
        maBaseSQLite = new BaseDonnees(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertEtudiant(Etudiant etd){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(serial_number, etd.getSerial_number());
        values.put(nom, etd.getNom());
        values.put(prenom, etd.getPrenom());
        values.put(etat, etd.getEtat());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(table_etudiant, null, values);
    }

    public Etudiant getEtudiantWithSN(String sn){
        //Récupère dans un Cursor les valeurs correspondant à un étudiant contenu dans la BDD (ici on sélectionne un étudiant grâce à son numéro de série de la carte)
        Cursor c = bdd.query(table_etudiant, new String[] {serial_number, nom, prenom, etat}, serial_number + " LIKE \"" + sn +"\"", null, null, null, null);
        return cursorToEtudiant(c);
    }

    //Cette méthode permet de convertir un cursor en un etudiant
    private Etudiant cursorToEtudiant(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un étudiant
        Etudiant etd = new Etudiant();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        etd.setSerial_number(c.getString(NUM_COL_SN));
        etd.setNom(c.getString(NUM_COL_NOM));
        etd.setPrenom(c.getString(NUM_COL_PRENOM));
        etd.setEtat(c.getInt(NUM_COL_ETAT));
        //On ferme le cursor
        c.close();

        //On retourne le livre
        return etd;
    }

    public List<Etudiant> getAllEtudiant(){
        List<Etudiant> etdList = new ArrayList<Etudiant>();

        Cursor  cursor = bdd.rawQuery("select * from  " + table_etudiant,null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Etudiant etd = new Etudiant();
                etd.setSerial_number(cursor.getString(NUM_COL_SN));
                etd.setNom(cursor.getString(NUM_COL_NOM));
                etd.setPrenom(cursor.getString(NUM_COL_PRENOM));
                etd.setEtat(cursor.getInt(NUM_COL_ETAT));

                etdList.add(etd);
                cursor.moveToNext();
            }
        }

        return etdList;
    }

    public void updateEtudiant(String serial, int etats){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
        //ContentValues values = new ContentValues();
        //values.put(etat, etats);

        String strSQL = "UPDATE "+table_etudiant+" SET "+etat+" = "+etats+" WHERE "+serial_number+" = '"+ serial+"'";

        bdd.execSQL(strSQL);

        //return bdd.update(table_etudiant, values, serial_number + " LIKE " +serial, null);
    }
}
