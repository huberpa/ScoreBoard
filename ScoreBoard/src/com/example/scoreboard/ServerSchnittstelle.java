package com.example.scoreboard;

import android.content.Intent;
import android.view.View;

public class ServerSchnittstelle {
	
	int i = 0;

	void sendData(String name, String passwort){
		//Senden der Daten
//		Socket sock = null;
//		PrintWriter writer = null;
//		BufferedReader reader = null;
//		
//		try 
//		{
//			sock = new Socket("192.168.1.2", 999);
//			writer = new PrintWriter(sock.getOutputStream(), true);
//			reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
//		}
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//		}
//		
//		writer.println("123");
	}
	
	
	boolean getData(){
		//Warten auf Antwort
		while(i<10){
		System.out.println("Warten auf Antwort");
		i++;
			try {Thread.sleep(500);} 
			catch (InterruptedException e) {e.printStackTrace();}
		}
		
		return true;
	}
}

