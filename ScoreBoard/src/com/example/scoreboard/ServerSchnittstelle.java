package com.example.scoreboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.TextView;

public class ServerSchnittstelle extends Thread {

	static Socket sock;
	static Socket testsock;
	static BufferedReader reader;
	static int i = 0;

	boolean verbindungAufbauen() {
		try {
			System.out.println("Connection Try...");
			sock = new Socket();
			sock.connect(new InetSocketAddress("193.196.7.45", 1991), 10000);
			// sock.connect(new InetSocketAddress("192.168.1.29", 9999), 3000);
			System.out.println("Connection Passt...");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	void verbindungStop() {
		
		System.out.println(sock.isClosed());
		
		try {
			PrintWriter writer = new PrintWriter(sock.getOutputStream(), true);
			writer.println("STOP");

			System.out.println("gestoppt");

			sock.close();
		} catch (Exception e) {
			System.out.println("Ich bin sicher das die exception beim abbauen ist");
			e.printStackTrace();
		}
	}

	void sendCheckData() {
		try {
			PrintWriter writer = new PrintWriter(sock.getOutputStream(), true);
			writer.println("CHECK");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void sendLoginData(String name, String passwort) {
		try {
			PrintWriter writer = new PrintWriter(sock.getOutputStream(), true);
			writer.println("LOGIN;" + name + ";" + passwort);
			System.out.println("Sende: LOGIN;" + name + ";" + passwort);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void sendRegisterData(String name, String passwort) {
		try {
			PrintWriter writer = new PrintWriter(sock.getOutputStream(), true);
			writer.println("REG;" + name + ";" + passwort);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void sendGruppenAnfrageData(String name, String gruppe, String passwort) {
		try {
			PrintWriter writer = new PrintWriter(sock.getOutputStream(), true);
			writer.println("GA;" + name + ";" + gruppe + ";" + passwort);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void sendBTanfrage() {
		try {
			PrintWriter writer = new PrintWriter(sock.getOutputStream(), true);
			writer.println("BT");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void sendGruppenanfrage(String name) {
		try {
			PrintWriter writer = new PrintWriter(sock.getOutputStream(), true);
			writer.println("GRUPPE;" + name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void sendGruppenTabelleanfrage(String s) {
		try {
			PrintWriter writer = new PrintWriter(sock.getOutputStream(), true);
			writer.println("GT;" + s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void sendASTanfrage(int s, String v, String t) {
		try {
			PrintWriter writer = new PrintWriter(sock.getOutputStream(), true);
			writer.println("AST;" + (s + 1) + ";" + v + ";" + t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void sendASEanfrage(int s) {
		try {
			PrintWriter writer = new PrintWriter(sock.getOutputStream(), true);
			writer.println("ASE;" + (s + 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void sendMeineTipps(int s, String v, String t, String tipps) {
		try {
			PrintWriter writer = new PrintWriter(sock.getOutputStream(), true);
			String senden = "SETTIPP;" + (s + 1) + ";" + v + ";" + t + ";"
					+ tipps;
			System.out.println("sende als Tipps: " + senden);
			writer.println(senden);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void sendGetTipps(int s, String v, String t) {
		try {
			PrintWriter writer = new PrintWriter(sock.getOutputStream(), true);
			String senden = "GETTIPP;" + (s + 1) + ";" + v + ";" + t;
			writer.println(senden);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	int receiveData() {

		try {
			reader = new BufferedReader(new InputStreamReader(
					sock.getInputStream()));

		} catch (IOException e) {
			e.printStackTrace();
		}

		i = 0;

		while (true) {
			if (i == 1000)
				return -1;
			i++;

			try {
				if (reader.ready()) {
					String antwort = reader.readLine();
					System.out.println(antwort);
					return Integer.parseInt(antwort);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {
				Thread.sleep(10);
//				System.out.println("sleepin'" + i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	ArrayList<String> receiveInhalt() {
		try {
			reader = new BufferedReader(new InputStreamReader(
					sock.getInputStream()));

		} catch (IOException e) {
			e.printStackTrace();
		}

		i = 0;

		while (true) {
			if (i == 1000)
				return null;
			i++;

			try {
				if (reader.ready()) {
					String antwort = reader.readLine();
					System.out.println(antwort);
					decodieren deco = new decodieren();
					return deco.decode(antwort);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {
				Thread.sleep(10);
//				System.out.println("sleepin'");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
