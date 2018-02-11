package com.example.cash.nfc_app;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class MainActivity extends AppCompatActivity implements Listener{

    public static final String TAG = MainActivity.class.getSimpleName();

    TextView  mTvMessage;
    private NFCReadFragment mNfcReadFragment;

    private boolean isDialogDisplayed = false ;
    private NfcAdapter mNfcAdapter;
    String nom;

    ArrayList <String> maliste = new <String> ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initNFC();

        mTvMessage = (TextView) findViewById(R.id.tv_message);

        EtudiantBDD etdBdd = new EtudiantBDD(this);


        //On ouvre la base de données pour écrire dedans
        etdBdd.open();
        List<Etudiant> etdList = etdBdd.getAllEtudiant();
        for(Etudiant etd : etdList)
            etdBdd.updateEtudiant(etd.getSerial_number(), 0);
        etdBdd.close();


    }


    private void initNFC(){

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

/*
    private void showReadFragment() {

        mNfcReadFragment = (NFCReadFragment) getFragmentManager().findFragmentByTag(NFCReadFragment.TAG);

        if (mNfcReadFragment == null) {

            mNfcReadFragment = NFCReadFragment.newInstance();
        }
        mNfcReadFragment.show(getFragmentManager(),NFCReadFragment.TAG);

    }
*/
    @Override
    public void onDialogDisplayed() {

        isDialogDisplayed = true;
    }

    @Override
    public void onDialogDismissed() {

        isDialogDisplayed = false;

    }



    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        IntentFilter[] nfcIntentFilter = new IntentFilter[]{techDetected,tagDetected,ndefDetected};

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        if(mNfcAdapter!= null)
            mNfcAdapter.enableForegroundDispatch(this, pendingIntent, nfcIntentFilter, null);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mNfcAdapter!= null)
            mNfcAdapter.disableForegroundDispatch(this);
    }

    public String getSerialNumber(Tag tag){
        String serial_nbr = new String();

        for(int i = 0; i < tag.getId().length; i++){
            String x = Integer.toHexString(((int) tag.getId()[i] & 0xff));
            if(x.length() == 1){
                x = '0' + x;
            }
            serial_nbr += x + ' ';
        }

        return serial_nbr;
    }

    public void GoToList(View v){
        Intent in = new Intent(this, list.class);
       // Intent intentliste = new  Intent(this,list.class);
      //  in.putExtra("Nom_Etudiant",nom);

        EtudiantBDD etdBdd = new EtudiantBDD(this);


        //On ouvre la base de données pour écrire dedans
        etdBdd.open();

        List<Etudiant> etdList = etdBdd.getAllEtudiant();

        etdBdd.close();

        maliste.clear();
        for (Etudiant etd : etdList) {
            String nom = etd.getNom();
            String prenom = etd.getPrenom();
            String etat = "";
            if(etd.getEtat() == 0){
                etat = "Absent";

            }

            else{
                etat = "Présent";

            }


            maliste.add(nom);maliste.add(prenom);maliste.add(etat);
        }

       // in.putExtra("Liste_etd",maliste)
        in.putStringArrayListExtra("Liste_etd",maliste);

        startActivity(in);
    }


    public  int f(String sn, int taille){
     int nb=0;

     if (taille!=0) {
        for(int i=0;i <=maliste.size()-1;i++){
            if (maliste.get(i)==sn) {
                nb++;

            }
        }

    }
        return nb;
    }


    @Override
    protected void onNewIntent(Intent intent) {


            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);


            Log.d(TAG, "onNewIntent: " + intent.getAction());

           //programme lire donnée


            if (tag != null) {
                Toast.makeText(this, getString(R.string.message_tag_detected), Toast.LENGTH_SHORT).show();
                Ndef ndef = Ndef.get(tag);

                //getting tag serial number
                Toast.makeText(this, "serial num from tag : " + getSerialNumber(tag), Toast.LENGTH_SHORT).show();

                //Création d'une instance de ma classe LivresBDD
                EtudiantBDD etdBdd = new EtudiantBDD(this);


                //On ouvre la base de données pour écrire dedans
                etdBdd.open();
                //On insère le tag que l'on vient de créer
                //    etdBdd.insertEtudiant(etd);

                 //  Toast.makeText(this, "etudiant add", Toast.LENGTH_SHORT).show();


                Etudiant etdFromBdd = etdBdd.getEtudiantWithSN(getSerialNumber(tag));

                if(etdFromBdd != null){
                    /*List<Etudiant> etudiants= new ArrayList<>();
                    etudiants=etdBdd.getAllEtudiant();
                    for (int i=0;i<etudiants.size();i++)
                    {
                        Etudiant etudiant=
                    }
                    */


                    etdBdd.open();
                    etdBdd.updateEtudiant(etdFromBdd.getSerial_number(), 1);
                    etdBdd.close();

                    Toast.makeText(this, "etudiant nom : " + etdFromBdd.getNom(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "etudiant etat : " + etdFromBdd.getEtat(), Toast.LENGTH_SHORT).show();

                   // if ( f(etdFromBdd.getSerial_number(),maliste.size())==0){






                                Toast.makeText(this, "etudiant existe dans la liste ", Toast.LENGTH_SHORT).show();



                                maliste.add(etdFromBdd.getNom()+""+etdFromBdd.getPrenom());

                                nom = etdFromBdd.getNom();




                }
                else{
                   Intent intentaformulaire = new Intent(this, Formulairetud.class);
                    intentaformulaire.putExtra("SerialNumber",getSerialNumber(tag));
                    startActivity(intentaformulaire);

                }

                etdBdd.close();

                 if (isDialogDisplayed) {


                    mNfcReadFragment = (NFCReadFragment) getFragmentManager().findFragmentByTag(NFCReadFragment.TAG);
                    mNfcReadFragment.onNfcDetected(ndef);

                }
            }
        }

}






