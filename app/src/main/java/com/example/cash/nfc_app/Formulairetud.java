package com.example.cash.nfc_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Formulairetud extends AppCompatActivity {

    EditText nom;
    EditText prenom;
    Button   ajout;
    String SN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulairetud);


        nom =(EditText) findViewById(R.id.Nom);
        prenom= (EditText) findViewById(R.id.Prenom);
        ajout= findViewById(R.id.Ajout);

        Intent intentrecuSN = getIntent();
        SN=intentrecuSN.getStringExtra("SerialNumber");







    }

    public  void add(View V) {

        EtudiantBDD etdBdd = new EtudiantBDD(this);

        Etudiant etd = new Etudiant(SN, nom.getText().toString(),prenom.getText().toString(), 1);

        etdBdd.open();
        etdBdd.insertEtudiant(etd);

        Etudiant etdFromBdd = etdBdd.getEtudiantWithSN(SN);
        if(etdFromBdd != null){
            Toast.makeText(this, "etudiant nom : " + etdFromBdd.getNom(), Toast.LENGTH_SHORT).show();

        }
        etdBdd.close();



    }




}
