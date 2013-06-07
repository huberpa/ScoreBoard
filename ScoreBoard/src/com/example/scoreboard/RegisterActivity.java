package com.example.scoreboard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.scoreboard.R;

public class RegisterActivity extends Activity {
	
	ProgressBar bar;
	Intent access;
	static TextView errormsg;
    int authorisierterNutzer = -2;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		access=new Intent(this, LoginActivity.class);
		
		//WARTEZEICHEN
		bar = (ProgressBar) findViewById(R.id.progressBar2);
    	bar.setVisibility(View.INVISIBLE);
		
		//Für Klassendiagramm
		activity_register st = new activity_register();

		//FELDER
		final EditText Name = (EditText) findViewById(R.id.name);
		final EditText Passwort = (EditText) findViewById(R.id.pwfeld);
		final EditText Passwort2 = (EditText) findViewById(R.id.pwfeld2);
		Button Register = (Button) findViewById(R.id.tl1);
		
		//ERRORMSG
		errormsg = (TextView) findViewById(R.id.errormessage);
		errormsg.setText("");
		errormsg.setTextColor(Color.RED);

		Register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("Button gedrückt");
				String name = Name.getText().toString();
				String passwort = Passwort.getText().toString();
				String passwort2 = Passwort2.getText().toString();

				connectServer(name,passwort,passwort2);
//				connectLocal(name,passwort);
			}
		});
	}
	
	void connectServer(final String name,final String passwort,String passwort2){
		
		bar.setVisibility(View.VISIBLE);
		
		if(passwort.equals(passwort2)){
		if((name.indexOf(";")==-1)&&(passwort.indexOf(";")==-1)){
		if((name.length()<21)&&(passwort.length()<21)){
	    new Thread(new Runnable() {
			public void run() {
	        	ServerSchnittstelle Connect = new ServerSchnittstelle();
	    		if(Connect.verbindungAufbauen()){
	    			Connect.sendRegisterData(name, passwort);
	    			int result = Connect.receiveData();
	    			Connect.verbindungStop();
	    			
	    			if(result == 0){
	    				authorisierterNutzer = 0;
	    			}
	    			
	    			else if(result == 1){
	    				authorisierterNutzer = 1;
	    			}
	    			
	    			else if(result == -1){
	    				authorisierterNutzer = -1;
	    			}
	    		}
	    		else{
	    			authorisierterNutzer = -2;
	    		}
	    				    
	        	bar.post(new Runnable() {
	                public void run() {
	                	
	                	if(authorisierterNutzer == 0){
	                		errormsg.setText("Username schon verwendet");
	       				 	bar.setVisibility(View.INVISIBLE);
	                	}
	                	
	                	else if(authorisierterNutzer == 1){
		    				errormsg.setText("");
		    				access.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		    	            startActivityForResult(access, 0);
	       				 	bar.setVisibility(View.INVISIBLE);
	                	}
	                	
	                	else if(authorisierterNutzer == -1){
	                		errormsg.setText("Verbindung Fehlgeschlagen");
	       				 	bar.setVisibility(View.INVISIBLE);
	                	}
	                	
	                	else if(authorisierterNutzer == -2){
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
		else{
			errormsg.setText("Passwörter stimmen nicht überein");
			bar.setVisibility(View.INVISIBLE);
		}
}
	
//	void connectLocal(String name, String passwort){
//		LoginActivity log = new LoginActivity();
//		if(log.neueruser(name,passwort)){
//		 Intent myIntent = new Intent(this, LoginActivity.class);
//		 startActivityForResult(myIntent, 0);
//		}
//		else{
//			errormsg.setText("User schon vorhanden");
//		}
//	}
	
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println("Da wollte jemnd zurück");
            Intent myIntent = new Intent(this, LoginActivity.class);
    		myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(myIntent, 0);
            return true;
        }
		return false;
}
	
}
	
