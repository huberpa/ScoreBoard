package com.example.scoreboard;

import java.io.PrintWriter;
import java.net.Socket;

import android.content.Intent;
import android.view.View;

public class ServerSchnittstelle {
	
	void sendData(String name, String passwort){
		
		//Senden der Daten
//		Socket sock = null;
//		PrintWriter writer = null;
//		
//		try 
//		{
//			sock = new Socket("192.168.2.103", 9999);
//			writer = new PrintWriter(sock.getOutputStream(), true);
//			writer.println(name+";"+passwort);
//		}
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//		}
		
		System.out.println("gesendet");
	}
	
	int getData(){
		//Warten auf Antwort
		int i = 0;
		
//		while(i<10){
//
//			System.out.println("Warten auf Antwort...");
//			
//		i++;
//			try {Thread.sleep(10);} 
//			catch (InterruptedException e) {e.printStackTrace();}
//		}
		
		return 1;
	}
}

