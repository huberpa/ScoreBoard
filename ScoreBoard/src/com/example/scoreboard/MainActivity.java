package com.example.scoreboard;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.Spinner;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        String[] AlleGruppen = {"Gruppe A","Gruppe B","Gruppe C","Gruppe D"};
        Spinner spieltag = (Spinner) findViewById(R.id.spieltag);
        Spinner gruppe = (Spinner) findViewById(R.id.gruppe);

        spieltag.setAdapter(new Spinnerverwaltung().createSpieltage(getApplicationContext()));
        gruppe.setAdapter(new Spinnerverwaltung().createGruppen(getApplicationContext(),AlleGruppen));
        
    } 
}
