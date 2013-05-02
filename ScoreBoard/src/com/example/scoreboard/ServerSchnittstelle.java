package com.example.scoreboard;

import java.io.PrintWriter;
import java.net.Socket;

import android.content.Intent;
import android.view.View;

public class ServerSchnittstelle extends Thread{
	
	static Socket sock = null;
	static PrintWriter writer = null;
	
	public ServerSchnittstelle(){
	}
	
	void sendData(String name, String passwort){
//		Senden der Daten
		try 
		{
			sock = new Socket("192.168.1.157", 9999);
			
			writer = new PrintWriter(sock.getOutputStream(), true);
			writer.println(name+";"+passwort);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		System.out.println("gesendet");
	}
	
	@Override
	public void run(){
		
	}
	
	int getData(){
		//Warten auf Antwort
		int i = 0;
		
		while(i<10){

			System.out.println("Warten auf Antwort...");
			
		i++;
			try {Thread.sleep(10);} 
			catch (InterruptedException e) {e.printStackTrace();}
		}
		
		return 1;
	}


}

