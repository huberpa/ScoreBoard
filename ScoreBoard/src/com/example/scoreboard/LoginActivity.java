package com.example.scoreboard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        final EditText Name = (EditText) findViewById(R.id.name);
        final EditText Passwort = (EditText) findViewById(R.id.pwfeld);
        final TextView errormsg = (TextView) findViewById(R.id.errormessage);
        errormsg.setText("");
        errormsg.setTextColor(Color.RED);
        Button Login = (Button) findViewById(R.id.button);
        
        Login.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String name = Name.getText().toString();
				String passwort = Passwort.getText().toString();
				ServerSchnittstelle Connect = new ServerSchnittstelle();
				Connect.sendData(name, passwort);
				int freigabe = Connect.getData();
				
				if(freigabe == 1)
					{
					Intent myIntent = new Intent(v.getContext(), MainActivity.class);
		            startActivityForResult(myIntent, 0);
					}
				
				else if(freigabe == 0)
					{
					errormsg.setText("Es konnte keine Verbindung zum Server hergestellt werden. Bitte versuchen Sie es erneut");
					}
				
				else if(freigabe == -1)
					{
					errormsg.setText("Nutzername oder Passwort sind falsch. Versuchen Sie es erneut");
					}
			}});
    } 
}
