package com.example.scoreboard;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TableRow;
import android.widget.TextView;

public class TippenActivity extends Activity {

	// Allgemein
	TextView usr;
	TextView gru;
	TextView spi;
	int width;
	RadioGroup radio_gr;
	RadioButton[] group;
	Context haupt = this;
	int fehler = 1;
	TextView submitresponse;
	TextView GroupAddResponse;
	TextView[] team = new TextView[9];
	TextView[] realscore = new TextView[9];
	EditText[] erg1 = new EditText[9];
	EditText[] erg2 = new EditText[9];
	TableRow[] row = new TableRow[9];
	LinearLayout textspace1;
	LinearLayout textspace2;
	LinearLayout[] tipparea = new LinearLayout[9];
	LinearLayout[] teamarea = new LinearLayout[9];

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
		setContentView(R.layout.activity_tippen);

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
		final ImageView tippen = (ImageView) findViewById(R.id.tippen_button);
		final ImageView bt = (ImageView) findViewById(R.id.bt_button);
		final ImageView tt = (ImageView) findViewById(R.id.tt_button);
		final ImageView esc = (ImageView) findViewById(R.id.esc);
		Button submit = (Button) findViewById(R.id.button1);
		submitresponse = (TextView) findViewById(R.id.savetipps);

		// Params
		final LinearLayout.LayoutParams aussen = new LinearLayout.LayoutParams(
				width / 6, width / 6);
		LinearLayout.LayoutParams mitte = new LinearLayout.LayoutParams(
				width / 5, width / 5);
		final LinearLayout.LayoutParams mitteKlein = new LinearLayout.LayoutParams(
				width / 6, width / 6);
		mitteKlein.setMargins(width / 5, 0, width / 5, 0);
		mitte.setMargins(width / 5, 0, width / 5, 0);
		aussen.setMargins(0, 20, 0, 20);
		tippen.setLayoutParams(mitte);
		bt.setLayoutParams(aussen);
		tt.setLayoutParams(aussen);

		// Erzeuge Hauptbild
		createTable();

		// Erzeuge Slider
		createSlider();

		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ScaleAnimation scaleSmall;
				TranslateAnimation transGotoMitte;
				TranslateAnimation transComeFromMitte;
				TranslateAnimation transHinten;

				tippen.setLayoutParams(mitteKlein);

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

				bt.startAnimation(transGotoMitte);
				tippen.startAnimation(transComeFromMitte);
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
										BundesligaActivity.class);
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

				tippen.setLayoutParams(mitteKlein);

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

				bt.startAnimation(transHinten);
				tippen.startAnimation(transComeFromMitte);
				tt.startAnimation(transGotoMitte);

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

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int result = 0;
				int i = 0;
				boolean ok = true;
				while (i < 9) {
					if ((!(erg1[i].getText().toString().equals("")) && (erg2[i]
							.getText().toString().equals("")))
							|| ((erg1[i].getText().toString().equals("")) && !(erg2[i]
									.getText().toString().equals(""))))
						ok = false;
					i++;
				}
				if (ok == true) {
					String tipps = "";

					i = 0;
					String home;
					String gast;
					while (i < 9) {
						if (erg1[i].getText().toString().matches(""))
							home = "-1";
						else
							home = erg1[i].getText().toString();

						if (Integer.parseInt(home) > 99)
							home = "99";

						if (erg2[i].getText().toString().matches(""))
							gast = "-1";
						else
							gast = erg2[i].getText().toString();

						if (Integer.parseInt(gast) > 99)
							gast = "99";

						tipps += Begegnungen.get(i + 1).substring(0, 3) + ";"
								+ Begegnungen.get(i + 1).substring(6, 9) + ";"
								+ home + ";" + gast + ";";
						i++;
					}

					ServerSchnittstelle Connect = new ServerSchnittstelle();
					if (Connect.verbindungAufbauen()) {
						Connect.sendMeineTipps(aktuellerSpieltag, Groups.get(aktuelleGruppe), Name, tipps);
						result = Connect.receiveData();
					}
					if (result == 1) {
						submitresponse.setText("Tipps wurden gespeichert");
						submitresponse.setTextColor(Color.GREEN);
					} else {
						submitresponse.setText("Fehler beim Speichern");
						submitresponse.setTextColor(Color.RED);
					}
				} else {
					submitresponse.setText("Tipps sind unvollständig...Bitte Tipps überprüfen");
					submitresponse.setTextColor(Color.RED);
				}

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

		submitresponse.setText("");

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(60,
				60);
		LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
				width * 2 / 3, 60);
		LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(40,
				40);
		params.gravity = Gravity.CENTER_VERTICAL;
		params4.gravity = Gravity.CENTER_VERTICAL;
		params2.gravity = Gravity.CENTER_VERTICAL;
		params4.setMargins(10, 0, 10, 0);
		params3.setMargins(0, 0, 10, 10);
		params2.setMargins(0, 4, 0, 6);

		int i = 0;
		for (i = 0; i < Begegnungen.size() - 1; i++) {
			// View bearbeiten
			textspace1 = (LinearLayout) findViewById(R.id.begeg);
			textspace2 = (LinearLayout) findViewById(R.id.dotipps);
			tipparea[i] = new LinearLayout(this);
			teamarea[i] = new LinearLayout(this);
			team[i] = new TextView(this);
			team[i].setLayoutParams(params);
			team[i].setGravity(Gravity.CENTER_VERTICAL);
			team[i].setText(Begegnungen.get(i + 1));
			realscore[i] = new TextView(this);
			realscore[i].setLayoutParams(params);
			realscore[i].setGravity(Gravity.CENTER_VERTICAL);
			erg1[i] = new EditText(this);
			erg1[i].setInputType(InputType.TYPE_CLASS_NUMBER);
			erg2[i] = new EditText(this);
			erg2[i].setInputType(InputType.TYPE_CLASS_NUMBER);
			erg2[i].setWidth(10);
			erg1[i].setGravity(Gravity.CENTER);
			erg2[i].setGravity(Gravity.CENTER);
			erg1[i].setLayoutParams(params2);
			erg2[i].setLayoutParams(params2);

			// Tipps einfügen
			if (!meineTipps.get(0).equals("-2")) {
				if (!meineTipps.get(i * 2).equals("-1")) {
					erg1[i].setText(meineTipps.get(i * 2));
					erg2[i].setText(meineTipps.get(i * 2 + 1));
				}
			}

			// Ergebnisse einfügen
			if (!realErg.get(0).equals("-2")) {
				realscore[i].setText(realErg.get(i));
			} else
				realscore[i].setText("( - : - )");

			// Edittexts editable?
			if (Begegnungen.get(0).equals("0")) {
				erg1[i].setEnabled(false);
				erg2[i].setEnabled(false);
			}

			// Logos einfügen
			ImageView iv = new ImageView(this);
			ImageView iv2 = new ImageView(this);
			selectLogo(Begegnungen.get(i + 1), iv, iv2, params4);

			// Views hinzufügen
			teamarea[i].setLayoutParams(params3);
			textspace1.addView(teamarea[i]);
			teamarea[i].setBackgroundResource(R.drawable.begegnungen_hg);
			teamarea[i].addView(iv);
			teamarea[i].addView(team[i]);
			teamarea[i].addView(iv2);
			teamarea[i].addView(realscore[i]);
			textspace2.addView(tipparea[i]);
			tipparea[i].addView(erg1[i]);
			tipparea[i].addView(erg2[i]);
		}
	}

	void selectLogo(String s, ImageView v1, ImageView v2,
			LinearLayout.LayoutParams params4) {

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

		if (s.substring(6).equals("BVB")) {
			v2.setImageResource(R.drawable.bvb);
			v2.setLayoutParams(params4);
		}
		if (s.substring(6).equals("FCB")) {
			v2.setImageResource(R.drawable.fcb);
			v2.setLayoutParams(params4);
		}
		if (s.substring(6).equals("FCA")) {
			v2.setImageResource(R.drawable.fca);
			v2.setLayoutParams(params4);
		}
		if (s.substring(6).equals("FCN")) {
			v2.setImageResource(R.drawable.fcn);
			v2.setLayoutParams(params4);
		}
		if (s.substring(6).equals("S04")) {
			v2.setImageResource(R.drawable.s04);
			v2.setLayoutParams(params4);
		}
		if (s.substring(6).equals("SCF")) {
			v2.setImageResource(R.drawable.scf);
			v2.setLayoutParams(params4);
		}
		if (s.substring(6).equals("SGF")) {
			v2.setImageResource(R.drawable.sgf);
			v2.setLayoutParams(params4);
		}
		if (s.substring(6).equals("SVW")) {
			v2.setImageResource(R.drawable.svw);
			v2.setLayoutParams(params4);
		}
		if (s.substring(6).equals("B04")) {
			v2.setImageResource(R.drawable.b04);
			v2.setLayoutParams(params4);
		}
		if (s.substring(6).equals("BMG")) {
			v2.setImageResource(R.drawable.bmg);
			v2.setLayoutParams(params4);
		}
		if (s.substring(6).equals("FDD")) {
			v2.setImageResource(R.drawable.fdd);
			v2.setLayoutParams(params4);
		}
		if (s.substring(6).equals("FRA")) {
			v2.setImageResource(R.drawable.fra);
			v2.setLayoutParams(params4);
		}
		if (s.substring(6).equals("H96")) {
			v2.setImageResource(R.drawable.h96);
			v2.setLayoutParams(params4);
		}
		if (s.substring(6).equals("HSV")) {
			v2.setImageResource(R.drawable.hsv);
			v2.setLayoutParams(params4);
		}
		if (s.substring(6).equals("M05")) {
			v2.setImageResource(R.drawable.m05);
			v2.setLayoutParams(params4);
		}
		if (s.substring(6).equals("TSG")) {
			v2.setImageResource(R.drawable.tsg);
			v2.setLayoutParams(params4);
		}
		if (s.substring(6).equals("VFB")) {
			v2.setImageResource(R.drawable.vfb);
			v2.setLayoutParams(params4);
		}
		if (s.substring(6).equals("WOB")) {
			v2.setImageResource(R.drawable.wob);
			v2.setLayoutParams(params4);
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

				// Update
				textspace1.removeAllViews();
				textspace2.removeAllViews();
				createTable();

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

				// Update
				textspace1.removeAllViews();
				textspace2.removeAllViews();
				createTable();

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

				if (newgroupname.indexOf(";") == -1 && pw.indexOf(";") == -1) {
					if (newgroupname.length() < 21 && pw.length() < 21) {

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

								submitresponse.post(new Runnable() {
									public void run() {
										prog.setVisibility(View.INVISIBLE);
										dialog.dismiss();
										if (fehler == 0) {
											GroupAddResponse
													.setTextColor(Color.RED);
											GroupAddResponse
													.setText("Gruppe oder Passwort falsch");
										}
										if (fehler == -1) {
											GroupAddResponse
													.setTextColor(Color.RED);
											GroupAddResponse
													.setText("Keine Verbindung herstellen können");
										}
										if (fehler == 1) {
											GroupAddResponse
													.setTextColor(Color.GREEN);
											GroupAddResponse
													.setText("Gruppe hinzugefügt");

											int i = 0;
											radio_gr.removeAllViews();
											while (i < Groups.size()) {
												group[i] = new RadioButton(
														haupt);
												group[i].setText(Groups.get(i));
												radio_gr.addView(group[i]);

												System.out
														.println("Gruppenanfrage: "
																+ newgroupname);

												if (Groups.get(i).equals(
														newgroupname
																.toLowerCase())) {
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

					} else {
						TextView er = (TextView) findViewById(R.id.errorresp);
						er.setText("Gruppenname ist zu lang");
						er.setTextColor(Color.RED);
					}
				} else {
					TextView er = (TextView) findViewById(R.id.errorresp);
					er.setText("Gruppenname enthält unzulässiges Zeichen");
					er.setTextColor(Color.RED);
				}
			}

		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			System.out.println("Da wollte jemnd zurück");

			return true;
		}
		return false;
	}
}
