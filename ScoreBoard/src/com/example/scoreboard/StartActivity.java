package com.example.scoreboard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StartActivity extends Activity {

	boolean verbindungsteht = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		final LinearLayout clickLayout = (LinearLayout) findViewById(R.id.clicklayout);
		final TextView errormsg = (TextView) findViewById(R.id.textView1);
		errormsg.setTextColor(Color.RED);
		final Intent myIntent = new Intent(this, LoginActivity.class);

		clickLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				verbindungsteht = false;

				final ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar1);
				bar.setVisibility(View.VISIBLE);

				new Thread(new Runnable() {
					public void run() {

						ServerSchnittstelle ConnectionTest = new ServerSchnittstelle();
						if (ConnectionTest.verbindungAufbauen()) {
							ConnectionTest.sendCheckData();
							if (ConnectionTest.receiveData() == 1) {
								verbindungsteht = true;
							} else
								verbindungsteht = false;

							ConnectionTest.verbindungStop();

						} else
							verbindungsteht = false;

						bar.post(new Runnable() {
							public void run() {
								if (verbindungsteht == true) {
									myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
									startActivityForResult(myIntent, 0);
									bar.setVisibility(View.INVISIBLE);
								} else {
									System.out.println("keine Verbindung");
									errormsg.setText("Konnte keine Verbindung aufbauen. Bitte �berpr�fen Sie Ihre Internetverbindung");
									bar.setVisibility(View.INVISIBLE);
								}
							}
						});
					}
				}).start();

				// startActivityForResult(myIntent, 0);
			}
		});
	}
	
	//Zur�ck-Button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println("Da wollte jemnd zur�ck");
            return true;
        }
		return false;
	}
}
