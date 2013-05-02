package com.example.scoreboard;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Spinnerverwaltung {

	ArrayAdapter<String> createSpieltage(Context context){
		List<String> spieltage = new ArrayList<String>();
		int i = 0;
		while(i<34){
        spieltage.add((i+1)+". Spieltag");
        i++;
		}
    	ArrayAdapter<String> spieltageadapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, spieltage);
    	spieltageadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	
		return spieltageadapter;
	}
	
	ArrayAdapter<String> createGruppen(Context context,String[] groups){
		List<String> gruppen = new ArrayList<String>();
		int i = 0;
		while(i<groups.length){
			gruppen.add(groups[i]);
        i++;
		}
    	ArrayAdapter<String> groupadapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, gruppen);
    	groupadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	
		return groupadapter;
	}
	
}
