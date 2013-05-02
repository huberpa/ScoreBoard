package com.example.scoreboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Path.FillType;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnTouchListener{

	static int CoordX = 0;
	static LinearLayout screen;
	static FrameLayout.LayoutParams screenparams;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        int masstab = (int)getResources().getDisplayMetrics().density;
        
        String[] AlleGruppen = {"Gruppe A","Gruppe B","Gruppe C","Gruppe D"};
        
        //Spinner
//        Spinner spieltag = (Spinner) findViewById(R.id.spieltag);
//        Spinner gruppe = (Spinner) findViewById(R.id.gruppe);
//        spieltag.setAdapter(new Spinnerverwaltung().createSpieltage(getApplicationContext()));
//        gruppe.setAdapter(new Spinnerverwaltung().createGruppen(getApplicationContext(),AlleGruppen));
        
        
        //LayoutTest
        screen = (LinearLayout) findViewById(R.id.lay);
        FrameLayout totalscreen = (FrameLayout) findViewById(R.id.totalscreen);
        screenparams = new FrameLayout.LayoutParams(480*masstab,800*masstab);
        
        screenparams.gravity = Gravity.TOP;
        screen.setLayoutParams(screenparams);

        totalscreen.setOnTouchListener(this);
    }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			CoordX = (int) event.getX();
			return true;
			}
			
		if(event.getAction() == MotionEvent.ACTION_MOVE){
			screenparams.leftMargin = -(CoordX - (int) event.getX());
			screen.setLayoutParams(screenparams);
			return true;
			}

		
		if(event.getAction() == MotionEvent.ACTION_UP){
			if(Math.abs((int) event.getX()-CoordX)>240)
				weiter();
			else
				zurueck();
			return true;
		}

		return false;
	}

	
	private void zurueck() {
		
	}

	private void weiter() {
		
	} 
}
