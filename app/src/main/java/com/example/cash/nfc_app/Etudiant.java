package com.example.cash.nfc_app;

/**
 * Created by cash on 01/02/18.
 */

public class Etudiant {

    private String serial_number;
    private String nom;
    private String prenom;
    private int etat;

    public Etudiant(){};

    public Etudiant(String serial_number, String nom, String prenom, int etat){
        this.serial_number = serial_number;
        this.nom = nom;
        this.prenom = prenom;
        this.etat = etat;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getEtat() {
        return etat;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "serial_number='" + serial_number + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", etat=" + etat +
                '}';
    }
}
