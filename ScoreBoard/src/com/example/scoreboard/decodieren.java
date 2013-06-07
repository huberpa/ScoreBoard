package com.example.scoreboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.TextView;

public class decodieren {

	ArrayList<String> decode(String s){
		ArrayList<String> list = new ArrayList<String>();
		String[] antwort = s.split(";");
		for(int i=0;i<antwort.length;i++){
			list.add(antwort[i]);
		}
		return list;
	}
}
