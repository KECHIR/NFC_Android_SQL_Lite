package com.example.cash.nfc_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class list extends AppCompatActivity {
    TextView listeetudiant;
    ArrayList<String> maliste = new <String> ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

         //listeetudiant  = (TextView) findViewById(R.id.listetudiant);

        Intent intentreculiste = getIntent();


        maliste = intentreculiste.getStringArrayListExtra("Liste_etd");


        if (maliste.size() !=0) {

            for(int i=0;i<=maliste.size()-1;i++){

                //listeetudiant.setText(listeetudiant.getText().toString()+"\n"+maliste.get(i));

                System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvv"+maliste.get(i));

                GridView gd = (GridView) findViewById(R.id.grid_view);
                //data bind GridView with ArrayAdapter
                gd.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, maliste));


            }

        }else {

            Toast.makeText(this, "ta qalwa", Toast.LENGTH_SHORT).show();
        }

   }


}
