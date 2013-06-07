package com.example.scoreboard;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.scoreboard.R;

public class LoginActivity extends Activity {

	ProgressBar bar;
	Intent access;
	int authorisierterNutzer = -2;
	static TextView errormsg;

//änderbar
	int aktuellerSpieltag;
	int aktuelleGruppe;
//abhängig
	ArrayList<String> Begegnungen = new ArrayList<String>();
	ArrayList<String> meineTipps = new ArrayList<String>();
	ArrayList<String> realErg = new ArrayList<String>();
	boolean tippenErlaubt = false;
	ArrayList<String> TTabelle = new ArrayList<String>();
//unabhängig
	ArrayList<String> BTabelle = new ArrayList<String>();
	ArrayList<String> Groups = new ArrayList<String>();
	String Name;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		access=new Intent(this, TippenActivity.class);
		
		//WARTEZEICHEN
		bar = (ProgressBar) findViewById(R.id.progressBar);
    	bar.setVisibility(View.INVISIBLE);

		//Nutzer hinzufügen
//		user[0] = "Name";
//		pw[0] = "Passwort";
		
		//ERROR-Button
		errormsg = (TextView) findViewById(R.id.errormessage);
		errormsg.setText("");
		errormsg.setTextColor(Color.RED);
		
		//LOGIN/REGISTER Buttons
		Button Login = (Button) findViewById(R.id.tl1);
		Button Register = (Button) findViewById(R.id.Button01);
		
		//INPUT Boxen
		final EditText Name = (EditText) findViewById(R.id.name);
		final EditText Passwort = (EditText) findViewById(R.id.pwfeld);
		
		Login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				errormsg.setText("");
				System.out.println("Button gedrückt");
				String name = Name.getText().toString();
				String passwort = Passwort.getText().toString();
				
//				connectlocal(name,passwort,v);
				connectServer(name,passwort);		
			}
		});
		
		Register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 gotoRegister(v);
				}
			});
	}

	//VERBINDUNG MIT SERVER
	void connectServer(final String name,final String passwort){

    	bar.setVisibility(View.VISIBLE);
		
    	if((name.indexOf(";")==-1)&&(passwort.indexOf(";")==-1)){
    	if((name.length()<21)&&(passwort.length()<21)){
	    new Thread(new Runnable() {
	        public void run() {
	        	ServerSchnittstelle Connect = new ServerSchnittstelle();
	    		if(Connect.verbindungAufbauen()){
	    			Connect.sendLoginData(name, passwort);
	    			int result = Connect.receiveData();
	    			Connect.verbindungStop();
	    				    			
	    			if(result == 0){
	    				authorisierterNutzer = 0;
	    			}
	    			
	    			if(result == 1){
	    				authorisierterNutzer = 1;
	    				Name = name;
	    				getMoreInformation();	
	    			}
	    			
	    			if(result == -1){
	    				authorisierterNutzer = -1;
	    			}
	    		}
	    		else{
	    			authorisierterNutzer = -2;
	    		}
	    				    
	        	bar.post(new Runnable() {
	                public void run() {
	                	
	                	if(authorisierterNutzer == 0){
	                		errormsg.setText("Authentifizierung Fehlgeschlagen");
	       				 	bar.setVisibility(View.INVISIBLE);
	                	}
	                	
	                	if(authorisierterNutzer == 1){
		    				errormsg.setText("");			
		    				Bundle korb = new Bundle();
		    				korb.putString("Name", Name);
		    				korb.putInt("Spieltag", aktuellerSpieltag);
		    				korb.putInt("Gruppe", aktuelleGruppe);
		    				korb.putStringArrayList("Begegnungen", Begegnungen);
		    				korb.putStringArrayList("meineTipps", meineTipps);
		    				korb.putStringArrayList("realErg", realErg);
		    				korb.putStringArrayList("TTabelle", TTabelle);
		    				korb.putStringArrayList("BTabelle", BTabelle);
		    				korb.putStringArrayList("Groups", Groups);
		    				korb.putBoolean("tippenErlaubt", tippenErlaubt);
		    				access.putExtras(korb);
		    	            startActivityForResult(access, 0);
	       				 	bar.setVisibility(View.INVISIBLE);
	                	}
	                	
	                	if(authorisierterNutzer == -1){
	                		errormsg.setText("Verbindung Fehlgeschlagen");
	       				 	bar.setVisibility(View.INVISIBLE);
	                	}
	                	
	                	if(authorisierterNutzer == -2){
	                		errormsg.setText("Verbindungsaufbau Fehlgeschlagen");
	       				 	bar.setVisibility(View.INVISIBLE);
	                	}
	                }
	            });
	        }
	    }).start();
    	}
		else{
			errormsg.setText("Name und Passwort dürfen nicht länger als 20 Zeichen sein");
			bar.setVisibility(View.INVISIBLE);
    	}
		}
    	else{
			errormsg.setText("Name und Passwort dürfen kein ; enthalten");
			bar.setVisibility(View.INVISIBLE);
		}
	}

	//DATEN NACHLADEN
	void getMoreInformation(){
		ServerSchnittstelle Connect = new ServerSchnittstelle();
		
		aktuellerSpieltag = 0;
		aktuelleGruppe = 0;
		tippenErlaubt = true;
		
	if(Connect.verbindungAufbauen()){
			
		//Frage BT ab
			Connect.sendBTanfrage();
			BTabelle = Connect.receiveInhalt();
			
		//Frage Gruppen ab -> erste Gruppe ist aktuelle
			Connect.sendGruppenanfrage(Name);
			Groups = Connect.receiveInhalt();			
		
		//Frage aktuelle Gruppentabelle ab
			Connect.sendGruppenTabelleanfrage(Groups.get(aktuelleGruppe));
			TTabelle = Connect.receiveInhalt();		
		
		//Frage Partien aktueller Spieltag ab  + noch änderbar?
			Connect.sendASTanfrage(aktuellerSpieltag, Groups.get(aktuelleGruppe), Name);
			Begegnungen = Connect.receiveInhalt();

		//Frage Tipps ab
			Connect.sendGetTipps(aktuellerSpieltag, Groups.get(aktuelleGruppe), Name);
			meineTipps = Connect.receiveInhalt();	
		
		//Frage RealErgebnisse ab
			Connect.sendASEanfrage(aktuellerSpieltag);
			realErg = Connect.receiveInhalt();
			
			Connect.verbindungStop();
	}		
}	
	
	//INTENT ZU REGISTRIERUNG
	void gotoRegister(View v){
		Intent myIntent = new Intent(v.getContext(), RegisterActivity.class);
		myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		System.out.println("wechsel zu register");
        startActivityForResult(myIntent, 0);	
	}
		
	//Zurück-Button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println("Da wollte jemnd zurück");
            Intent myIntent = new Intent(this, StartActivity.class);
    		myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(myIntent, 0);
            return true;
        }
		return false;}
}

