package com.example.scoreboard;

import java.util.ArrayList;

public class decodieren {

	ArrayList<String> decode(String s) {
		ArrayList<String> list = new ArrayList<String>();
		String[] antwort = s.split(";");
		for (int i = 0; i < antwort.length; i++) {
			list.add(antwort[i]);
		}
		return list;
	}
}
