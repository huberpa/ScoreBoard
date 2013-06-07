package com.example.scoreboard;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class BundesligaActivity extends Activity {

	// Allgemein
	TextView usr;
	TextView gru;
	TextView spi;
	int width;
	RadioGroup radio_gr;
	RadioButton[] group;
	Context haupt = this;
	int fehler = 1;
	TextView GroupAddResponse;
	// änderbar
	int aktuellerSpieltag;
	int aktuelleGruppe;
	// abhängig
	ArrayList<String> Begegnungen = new ArrayList<String>();
	ArrayList<String> meineTipps = new ArrayList<String>();
	ArrayList<String> realErg = new ArrayList<String>();
	boolean tippenErlaubt = false;
	ArrayList<String> TTabelle = new ArrayList<String>();
	// unabhängig
	ArrayList<String> BTabelle = new ArrayList<String>();
	ArrayList<String> Groups = new ArrayList<String>();
	String Name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bundesliga);

		// UML
		activity_tippen st = new activity_tippen();

		// Aus Intent
		getUebertrag();

		// DisplayMetriken
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		width = displaymetrics.widthPixels;

		// View Referenzieren
		usr = (TextView) findViewById(R.id.usr);
		gru = (TextView) findViewById(R.id.gru);
		spi = (TextView) findViewById(R.id.spi);
		usr.setText(Name);
		gru.setText(Groups.get(aktuelleGruppe));
		spi.setText("" + (aktuellerSpieltag + 1) + ". Spieltag");

		// ButtonBilder
		final ImageView tippen = (ImageView) findViewById(R.id.tippen_button2);
		final ImageView bt = (ImageView) findViewById(R.id.bt_button2);
		final ImageView tt = (ImageView) findViewById(R.id.tt_button2);
		final ImageView esc = (ImageView) findViewById(R.id.esc2);

		// Params
		LinearLayout.LayoutParams aussen = new LinearLayout.LayoutParams(
				width / 6, width / 6);
		LinearLayout.LayoutParams mitte = new LinearLayout.LayoutParams(
				width / 5, width / 5);
		final LinearLayout.LayoutParams mitteKlein = new LinearLayout.LayoutParams(
				width / 6, width / 6);
		mitteKlein.setMargins(width / 5, 0, width / 5, 0);
		mitte.setMargins(width / 5, 0, width / 5, 0);
		aussen.setMargins(0, 20, 0, 20);
		tippen.setLayoutParams(aussen);
		bt.setLayoutParams(mitte);
		tt.setLayoutParams(aussen);

		// Erzeuge Hauptbild
		createTable();

		// Erzeuge Slider
		createSlider();

		tippen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				TranslateAnimation transGotoMitte;
				TranslateAnimation transComeFromMitte;
				TranslateAnimation transHinten;

				bt.setLayoutParams(mitteKlein);

				// Animations
				transGotoMitte = new TranslateAnimation(0,
						(width / 5 + width / 6), 0, 0);
				transGotoMitte.setDuration(200);
				transGotoMitte.setFillAfter(true);
				transGotoMitte.setFillEnabled(true);

				transComeFromMitte = new TranslateAnimation(0,
						(width / 5 + width / 6), 0, 0);
				transComeFromMitte.setDuration(200);
				transComeFromMitte.setFillAfter(true);
				transComeFromMitte.setFillEnabled(true);

				transHinten = new TranslateAnimation(0,
						-(2 * width / 5 + 2 * width / 6), 0, 0);
				transHinten.setDuration(200);
				transHinten.setFillAfter(true);
				transHinten.setFillEnabled(true);

				tippen.startAnimation(transGotoMitte);
				bt.startAnimation(transComeFromMitte);
				tt.startAnimation(transHinten);

				transGotoMitte
						.setAnimationListener(new Animation.AnimationListener() {

							@Override
							public void onAnimationStart(Animation animation) {
								System.out.println("Animation Start");

							}

							@Override
							public void onAnimationRepeat(Animation animation) {
							}

							@Override
							public void onAnimationEnd(Animation animation) {
								Intent myIntent = new Intent(haupt,
										TippenActivity.class);
								myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
								Bundle korb = new Bundle();
								korb.putString("Name", Name);
								korb.putInt("Spieltag", aktuellerSpieltag);
								korb.putInt("Gruppe", aktuelleGruppe);
								korb.putStringArrayList("Begegnungen",
										Begegnungen);
								korb.putStringArrayList("meineTipps",
										meineTipps);
								korb.putStringArrayList("realErg", realErg);
								korb.putStringArrayList("TTabelle", TTabelle);
								korb.putStringArrayList("BTabelle", BTabelle);
								korb.putStringArrayList("Groups", Groups);
								korb.putBoolean("tippenErlaubt", tippenErlaubt);
								myIntent.putExtras(korb);
								startActivityForResult(myIntent, 0);
							}
						});
			}
		});

		tt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				TranslateAnimation transGotoMitte;
				TranslateAnimation transComeFromMitte;
				TranslateAnimation transHinten;

				bt.setLayoutParams(mitteKlein);

				// Animations
				transGotoMitte = new TranslateAnimation(0,
						-(width / 5 + width / 6), 0, 0);
				transGotoMitte.setDuration(200);
				transGotoMitte.setFillAfter(true);
				transGotoMitte.setFillEnabled(true);

				transComeFromMitte = new TranslateAnimation(0,
						-(width / 5 + width / 6), 0, 0);
				transComeFromMitte.setDuration(200);
				transComeFromMitte.setFillAfter(true);
				transComeFromMitte.setFillEnabled(true);

				transHinten = new TranslateAnimation(0,
						(2 * width / 5 + 2 * width / 6), 0, 0);
				transHinten.setDuration(200);
				transHinten.setFillAfter(true);
				transHinten.setFillEnabled(true);

				tippen.startAnimation(transHinten);
				tt.startAnimation(transComeFromMitte);
				bt.startAnimation(transGotoMitte);

				transGotoMitte
						.setAnimationListener(new Animation.AnimationListener() {

							@Override
							public void onAnimationStart(Animation animation) {
								System.out.println("Animation Start");

							}

							@Override
							public void onAnimationRepeat(Animation animation) {
							}

							@Override
							public void onAnimationEnd(Animation animation) {
								Intent myIntent = new Intent(haupt,
										TippTabelleActivity.class);
								myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
								Bundle korb = new Bundle();
								korb.putString("Name", Name);
								korb.putInt("Spieltag", aktuellerSpieltag);
								korb.putInt("Gruppe", aktuelleGruppe);
								korb.putStringArrayList("Begegnungen",
										Begegnungen);
								korb.putStringArrayList("meineTipps",
										meineTipps);
								korb.putStringArrayList("realErg", realErg);
								korb.putStringArrayList("TTabelle", TTabelle);
								korb.putStringArrayList("BTabelle", BTabelle);
								korb.putStringArrayList("Groups", Groups);
								korb.putBoolean("tippenErlaubt", tippenErlaubt);
								myIntent.putExtras(korb);
								startActivityForResult(myIntent, 0);
							}
						});

			}
		});

		esc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(),
						LoginActivity.class);
				startActivityForResult(myIntent, 0);

			}
		});
	}

	void getUebertrag() {
		// aktuelle Daten speichern
		Bundle zielkorb = getIntent().getExtras();
		Name = zielkorb.getString("Name");
		aktuellerSpieltag = zielkorb.getInt("Spieltag");
		aktuelleGruppe = zielkorb.getInt("Gruppe");
		System.out.println("Check1");

		Begegnungen = zielkorb.getStringArrayList("Begegnungen");
		meineTipps = zielkorb.getStringArrayList("meineTipps");
		realErg = zielkorb.getStringArrayList("realErg");
		TTabelle = zielkorb.getStringArrayList("TTabelle");
		BTabelle = zielkorb.getStringArrayList("BTabelle");
		Groups = zielkorb.getStringArrayList("Groups");
		tippenErlaubt = zielkorb.getBoolean("tippenErlaubt");
		System.out.println("Check2");
	}

	void createTable() {
		LinearLayout tabelle = (LinearLayout) findViewById(R.id.bund);
		LinearLayout head = (LinearLayout) findViewById(R.id.header);
		LinearLayout foot = (LinearLayout) findViewById(R.id.footer);
		TextView Mannschaft = null;
		LinearLayout[] Zeilen = new LinearLayout[19];
		TextView[] Platz = new TextView[19];
		ImageView[] MannschaftLogo = new ImageView[19];
		TextView[] Mannschaften = new TextView[19];
		TextView[] Spiele = new TextView[19];
		TextView[] Tore = new TextView[19];
		TextView[] GTore = new TextView[19];
		TextView[] Tordiff = new TextView[19];
		TextView[] Pkt = new TextView[19];

		// Von Server
		// int i = 0;
		// while(i*3+2 <= BTabelle.size()){
		// Mannschaften[i] = new TextView(this);
		// Punkte[i] = new TextView(this);
		// Tordiff[i] = new TextView(this);
		//
		// Mannschaften[i].setText(BTabelle.get(i*3));
		// Punkte[i].setText(BTabelle.get(i*3+1));
		// Tordiff[i].setText(BTabelle.get(i*3+2));
		//
		// MannschaftZeile.addView(Mannschaften[i]);
		// PunkteZeile.addView(Punkte[i]);
		// TordiffZeile.addView(Tordiff[i]);
		// i++;
		// }

		LinearLayout.LayoutParams background = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, 60);
		LinearLayout.LayoutParams background_header = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 60);
		LinearLayout.LayoutParams background_footer = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 100);
		LinearLayout.LayoutParams LogoParam = new LinearLayout.LayoutParams(40,
				40);
		LinearLayout.LayoutParams AttrParam = new LinearLayout.LayoutParams(
				width / 7, 40);
		LinearLayout.LayoutParams ErklaerParam1 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 30);
		LinearLayout.LayoutParams ErklaerParam2 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 30);
		LinearLayout.LayoutParams AttrParamMann = new LinearLayout.LayoutParams(
				width / 7 + 50, 40);
		LinearLayout.LayoutParams FirstAttrParam = new LinearLayout.LayoutParams(
				40, 40);
		FirstAttrParam.gravity = Gravity.CENTER_VERTICAL;
		FirstAttrParam.setMargins(10, 0, 0, 0);
		background_header.setMargins(0, 0, 0, 20);
		background_footer.setMargins(0, 20, 0, 0);
		LogoParam.setMargins(0, 0, 10, 0);
		ErklaerParam1.gravity = Gravity.CENTER_VERTICAL;
		ErklaerParam1.setMargins(0, 20, 0, 0);
		ErklaerParam2.gravity = Gravity.CENTER_VERTICAL;
		LogoParam.gravity = Gravity.CENTER_VERTICAL;
		AttrParam.gravity = Gravity.CENTER_VERTICAL;
		AttrParamMann.gravity = Gravity.CENTER_VERTICAL;

		// Überschrift
		Platz[0] = new TextView(this);
		Platz[0].setText("");
		Mannschaft = new TextView(this);
		Mannschaft.setText("Team");
		Mannschaft.setLayoutParams(AttrParam);
		Spiele[0] = new TextView(this);
		Spiele[0].setText("Sp");
		Tore[0] = new TextView(this);
		Tore[0].setText("T");
		GTore[0] = new TextView(this);
		GTore[0].setText("GT");
		Tordiff[0] = new TextView(this);
		Tordiff[0].setText("Diff");
		Pkt[0] = new TextView(this);
		Pkt[0].setText("Pkt");

		Mannschaft.setLayoutParams(AttrParamMann);
		Platz[0].setLayoutParams(FirstAttrParam);
		Spiele[0].setLayoutParams(AttrParam);
		Tore[0].setLayoutParams(AttrParam);
		GTore[0].setLayoutParams(AttrParam);
		Tordiff[0].setLayoutParams(AttrParam);
		Pkt[0].setLayoutParams(AttrParam);

		head.setLayoutParams(background_header);
		head.setBackgroundResource(R.drawable.tabelle_hg);
		head.addView(Platz[0]);
		head.addView(Mannschaft);
		head.addView(Spiele[0]);
		head.addView(Tore[0]);
		head.addView(GTore[0]);
		head.addView(Tordiff[0]);
		head.addView(Pkt[0]);

		int i = 0;
		while (i < 18) {

			// Tupel
			Zeilen[i] = new LinearLayout(this);
			Zeilen[i].setLayoutParams(background);
			if (i < 3)
				Zeilen[i].setBackgroundResource(R.drawable.tabelle_hg_top);
			else if (i == 15)
				Zeilen[i].setBackgroundResource(R.drawable.tabelle_hg_rel);
			else if (i > 15)
				Zeilen[i].setBackgroundResource(R.drawable.tabelle_hg_flop);
			else
				Zeilen[i].setBackgroundResource(R.drawable.tabelle_hg);

			// Attribute
			Platz[i] = new TextView(this);
			Platz[i].setText("" + (i + 1));

			MannschaftLogo[i] = new ImageView(this);
			selectLogo(BTabelle.get(i * 6), MannschaftLogo[i], LogoParam);

			Mannschaften[i] = new TextView(this);
			Mannschaften[i].setText(BTabelle.get(i * 6));

			Spiele[i] = new TextView(this);
			Spiele[i].setText(BTabelle.get(i * 6 + 1));

			Tore[i] = new TextView(this);
			Tore[i].setText(BTabelle.get(i * 6 + 2));

			GTore[i] = new TextView(this);
			GTore[i].setText(BTabelle.get(i * 6 + 3));

			if (Integer.parseInt(BTabelle.get(i * 6 + 4)) > 0) {
				Tordiff[i] = new TextView(this);
				Tordiff[i].setText("+" + BTabelle.get(i * 6 + 4));
			} else {
				Tordiff[i] = new TextView(this);
				Tordiff[i].setText(BTabelle.get(i * 6 + 4));
			}

			Pkt[i] = new TextView(this);
			Pkt[i].setText(BTabelle.get(i * 6 + 5));

			// Params
			Platz[i].setLayoutParams(FirstAttrParam);
			Spiele[i].setLayoutParams(AttrParam);
			Tore[i].setLayoutParams(AttrParam);
			Mannschaften[i].setLayoutParams(AttrParam);
			GTore[i].setLayoutParams(AttrParam);
			Tordiff[i].setLayoutParams(AttrParam);
			Pkt[i].setLayoutParams(AttrParam);

			// Adding
			tabelle.addView(Zeilen[i]);
			Zeilen[i].addView(Platz[i]);
			Zeilen[i].addView(MannschaftLogo[i]);
			Zeilen[i].addView(Mannschaften[i]);
			Zeilen[i].addView(Spiele[i]);
			Zeilen[i].addView(Tore[i]);
			Zeilen[i].addView(GTore[i]);
			Zeilen[i].addView(Tordiff[i]);
			Zeilen[i].addView(Pkt[i]);

			// System.out.println("test5");

			i++;
		}

		// Footer
		foot.setOrientation(LinearLayout.VERTICAL);
		TextView abk = new TextView(this);
		TextView abk2 = new TextView(this);
		abk.setText("   Sp: Spieltag  T: Tore  GT: Gegentore");
		abk2.setText("   Diff: Tordifferenz  Pkt: Punkte");
		abk.setLayoutParams(ErklaerParam1);
		abk2.setLayoutParams(ErklaerParam2);
		foot.setLayoutParams(background_footer);
		foot.setBackgroundResource(R.drawable.tabelle_hg);
		foot.addView(abk);
		foot.addView(abk2);
	}

	void selectLogo(String s, ImageView v1, LinearLayout.LayoutParams params4) {

		if (s.substring(0, 3).equals("BVB")) {
			v1.setImageResource(R.drawable.bvb);
			v1.setLayoutParams(params4);
		}
		if (s.substring(0, 3).equals("FCB")) {
			v1.setImageResource(R.drawable.fcb);
			v1.setLayoutParams(params4);
		}
		if (s.substring(0, 3).equals("FCA")) {
			v1.setImageResource(R.drawable.fca);
			v1.setLayoutParams(params4);
		}
		if (s.substring(0, 3).equals("FCN")) {
			v1.setImageResource(R.drawable.fcn);
			v1.setLayoutParams(params4);
		}
		if (s.substring(0, 3).equals("S04")) {
			v1.setImageResource(R.drawable.s04);
			v1.setLayoutParams(params4);
		}
		if (s.substring(0, 3).equals("SCF")) {
			v1.setImageResource(R.drawable.scf);
			v1.setLayoutParams(params4);
		}
		if (s.substring(0, 3).equals("SGF")) {
			v1.setImageResource(R.drawable.sgf);
			v1.setLayoutParams(params4);
		}
		if (s.substring(0, 3).equals("SVW")) {
			v1.setImageResource(R.drawable.svw);
			v1.setLayoutParams(params4);
		}
		if (s.substring(0, 3).equals("B04")) {
			v1.setImageResource(R.drawable.b04);
			v1.setLayoutParams(params4);
		}
		if (s.substring(0, 3).equals("BMG")) {
			v1.setImageResource(R.drawable.bmg);
			v1.setLayoutParams(params4);
		}
		if (s.substring(0, 3).equals("FDD")) {
			v1.setImageResource(R.drawable.fdd);
			v1.setLayoutParams(params4);
		}
		if (s.substring(0, 3).equals("FRA")) {
			v1.setImageResource(R.drawable.fra);
			v1.setLayoutParams(params4);
		}
		if (s.substring(0, 3).equals("H96")) {
			v1.setImageResource(R.drawable.h96);
			v1.setLayoutParams(params4);
		}
		if (s.substring(0, 3).equals("HSV")) {
			v1.setImageResource(R.drawable.hsv);
			v1.setLayoutParams(params4);
		}
		if (s.substring(0, 3).equals("M05")) {
			v1.setImageResource(R.drawable.m05);
			v1.setLayoutParams(params4);
		}
		if (s.substring(0, 3).equals("TSG")) {
			v1.setImageResource(R.drawable.tsg);
			v1.setLayoutParams(params4);
		}
		if (s.substring(0, 3).equals("VFB")) {
			v1.setImageResource(R.drawable.vfb);
			v1.setLayoutParams(params4);
		}
		if (s.substring(0, 3).equals("WOB")) {
			v1.setImageResource(R.drawable.wob);
			v1.setLayoutParams(params4);
		}
	}

	void createSlider() {
		final RadioButton[] spielt = new RadioButton[35];
		final RadioGroup radio_st = new RadioGroup(this);

		group = new RadioButton[100];
		radio_gr = new RadioGroup(this);

		final Button neueGruppe = new Button(this);
		neueGruppe.setText("Gruppe beitreten");

		GroupAddResponse = new TextView(this);

		TextView spielt_header = new TextView(this);
		spielt_header.setText("Spieltage: ");
		TextView group_header = new TextView(this);
		group_header.setText("Gruppen: ");

		LinearLayout Spieltage = (LinearLayout) findViewById(R.id.Spieltage);
		LinearLayout Gruppen = (LinearLayout) findViewById(R.id.Gruppenauswahl);

		Spieltage.addView(spielt_header);
		Spieltage.addView(radio_st);

		Gruppen.addView(group_header);
		Gruppen.addView(radio_gr);
		Gruppen.addView(neueGruppe);
		Gruppen.addView(GroupAddResponse);

		int i = 0;
		for (i = 0; i < 34; i++) {
			spielt[i] = new RadioButton(this);
			spielt[i].setText((i + 1) + ". Spieltag");
			radio_st.addView(spielt[i]);
			if (i == aktuellerSpieltag)
				spielt[i].setChecked(true);
		}

		i = 0;
		if (!Groups.get(0).equals("0")) {
			while (i < Groups.size()) {
				group[i] = new RadioButton(this);
				group[i].setText(Groups.get(i));
				radio_gr.addView(group[i]);
				if (i == aktuelleGruppe)
					group[i].setChecked(true);
				i++;
			}
		}

		neueGruppe.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				erstelleDialog();
			}
		});

		radio_gr.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group2, int checkedId) {

				group[aktuelleGruppe].setChecked(false);
				RadioButton radioButton = (RadioButton) group2
						.findViewById(checkedId);
				int indexOfButton = group2.indexOfChild(radioButton);
				System.out.println("Neue Gruppe: " + indexOfButton);
				aktuelleGruppe = indexOfButton;
				group[aktuelleGruppe].setChecked(true);
				gru.setText(Groups.get(aktuelleGruppe));

				ServerSchnittstelle Connect = new ServerSchnittstelle();
				if (Connect.verbindungAufbauen()) {

					// Lade TTabelle
					Connect.sendGruppenTabelleanfrage(Groups
							.get(aktuelleGruppe));
					TTabelle = Connect.receiveInhalt();

					// Lade meine Tipps
					Connect.sendGetTipps(aktuellerSpieltag,
							Groups.get(aktuelleGruppe), Name);
					meineTipps = Connect.receiveInhalt();

					Connect.verbindungStop();
				}
			}
		});

		radio_st.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				spielt[aktuellerSpieltag].setChecked(false);
				RadioButton radioButton = (RadioButton) group
						.findViewById(checkedId);
				int indexOfButton = group.indexOfChild(radioButton);
				System.out.println("Neuer Spieltag: " + indexOfButton);
				aktuellerSpieltag = indexOfButton;
				spielt[aktuellerSpieltag].setChecked(true);
				spi.setText("" + (aktuellerSpieltag + 1) + ". Spieltag");

				ServerSchnittstelle Connect = new ServerSchnittstelle();
				if (Connect.verbindungAufbauen()) {

					// Lade aktuelle Begegnungen
					Connect.sendASTanfrage(aktuellerSpieltag,
							Groups.get(aktuelleGruppe), Name);
					Begegnungen = Connect.receiveInhalt();

					// Lade meine Tipps
					Connect.sendGetTipps(aktuellerSpieltag,
							Groups.get(aktuelleGruppe), Name);
					meineTipps = Connect.receiveInhalt();

					// Lade richtige Ergebnisse
					Connect.sendASEanfrage(aktuellerSpieltag);
					realErg = Connect.receiveInhalt();

					Connect.verbindungStop();
				}
			}
		});
	}

	void erstelleDialog() {
		final Dialog dialog = new Dialog(haupt);
		dialog.setContentView(R.layout.dialog);
		final EditText nameGruppe = (EditText) dialog
				.findViewById(R.id.newgroup);
		final EditText pwGruppe = (EditText) dialog.findViewById(R.id.newpw);
		final ProgressBar prog = (ProgressBar) dialog
				.findViewById(R.id.progressBarGroup);
		prog.setVisibility(View.INVISIBLE);
		dialog.setTitle("Gruppe und Passwort eingeben");
		dialog.setCancelable(true);
		dialog.show();
		final Button accept = (Button) dialog.findViewById(R.id.acceptnewgroup);

		accept.setOnClickListener(new OnClickListener() {

			String newgroupname;

			@Override
			public void onClick(final View v) {
				newgroupname = nameGruppe.getText().toString();
				final String pw = pwGruppe.getText().toString();
				prog.setVisibility(View.VISIBLE);

				new Thread(new Runnable() {
					public void run() {
						ServerSchnittstelle Connect = new ServerSchnittstelle();
						if (Connect.verbindungAufbauen()) {
							Connect.sendGruppenAnfrageData(Name,
									newgroupname, pw);
							int result = Connect.receiveData();

							if (result == 1) {
								Connect.sendGruppenanfrage(Name);
								Groups = Connect.receiveInhalt();

								Connect.sendGruppenTabelleanfrage(Groups.get(aktuelleGruppe));
								TTabelle = Connect.receiveInhalt();

								fehler = 1;
								
							} else {
								fehler = 0;
							}
							Connect.verbindungStop();

						} else
							fehler = -1;

						radio_gr.post(new Runnable() {
							public void run() {
								prog.setVisibility(View.INVISIBLE);
								dialog.dismiss();
								if (fehler == 0) {
									GroupAddResponse.setTextColor(Color.RED);
									GroupAddResponse
											.setText("Gruppe oder Passwort falsch");
								}
								if (fehler == -1) {
									GroupAddResponse.setTextColor(Color.RED);
									GroupAddResponse
											.setText("Keine Verbindung herstellen können");
								}
								if (fehler == 1) {
									GroupAddResponse.setTextColor(Color.GREEN);
									GroupAddResponse
											.setText("Gruppe hinzugefügt");

									int i = 0;
									radio_gr.removeAllViews();
									while (i < Groups.size()) {
										group[i] = new RadioButton(haupt);
										group[i].setText(Groups.get(i));
										radio_gr.addView(group[i]);

										System.out.println("Gruppenanfrage: "
												+ newgroupname);

										if (Groups.get(i).equals(
												newgroupname.toLowerCase())) {
											group[i].setChecked(true);
											aktuelleGruppe = i;
											System.out
													.println("TREFFER DER GRUPPE BEI "
															+ i);
										}

										i++;
									}
								}
							}
						});
					}
				}).start();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			System.out.println("Da wollte jemnd zurück");
			Intent myIntent = new Intent(this, TippenActivity.class);
			myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			Bundle korb = new Bundle();
			korb.putString("Name", Name);
			korb.putInt("Spieltag", aktuellerSpieltag);
			korb.putInt("Gruppe", aktuelleGruppe);
			korb.putStringArrayList("Begegnungen", Begegnungen);
			korb.putStringArrayList("meineTipps", meineTipps);
			korb.putStringArrayList("TTabelle", TTabelle);
			korb.putStringArrayList("BTabelle", BTabelle);
			korb.putStringArrayList("Groups", Groups);
			korb.putBoolean("tippenErlaubt", tippenErlaubt);
			myIntent.putExtras(korb);
			startActivityForResult(myIntent, 0);
			return true;
		}
		return false;
	}
}
