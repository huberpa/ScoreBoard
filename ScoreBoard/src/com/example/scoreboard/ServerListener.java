package com.example.scoreboard;

import android.content.Intent;

public class ServerListener implements Runnable{
	public void run(){
	
		//WIRD NICHT VERWENDET!!!
		int i=0;
		while(i<10){
		System.out.println("Thread geht");
		i++;
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}
