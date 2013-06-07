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
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class TippTabelleActivity extends Activity {

	// Allgemein
	TextView usr;
	TextView gru;
	TextView spi;
	int width;
	LinearLayout tabelle;
	RadioGroup radio_gr;
	RadioButton[] group;
	Context haupt = this;
	int fehler = 1;
	TextView GroupAddResponse;
	TextView[] team = new TextView[9];
	EditText[] erg1 = new EditText[9];
	EditText[] erg2 = new EditText[9];
	TableRow[] row = new TableRow[9];
	TextView[] seperator = new TextView[9];
	LinearLayout[] textspace1 = new LinearLayout[9];
	LinearLayout[] textspace2 = new LinearLayout[9];

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
		setContentView(R.layout.activity_ttabelle);

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
		final ImageView tippen = (ImageView) findViewById(R.id.tippen_button3);
		final ImageView bt = (ImageView) findViewById(R.id.bt_button3);
		final ImageView tt = (ImageView) findViewById(R.id.tt_button3);
		final ImageView esc = (ImageView) findViewById(R.id.esc3);
		Button submit = (Button) findViewById(R.id.button1);

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
		bt.setLayoutParams(aussen);
		tt.setLayoutParams(mitte);

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

				tt.setLayoutParams(mitteKlein);

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

				tippen.startAnimation(transGotoMitte);
				tt.startAnimation(transComeFromMitte);
				bt.startAnimation(transHinten);

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

		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				TranslateAnimation transGotoMitte;
				TranslateAnimation transComeFromMitte;
				TranslateAnimation transHinten;

				tt.setLayoutParams(mitteKlein);

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

				bt.startAnimation(transGotoMitte);
				tippen.startAnimation(transHinten);
				tt.startAnimation(transComeFromMitte);

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

		tabelle = (LinearLayout) findViewById(R.id.bund2);
		LinearLayout head = (LinearLayout) findViewById(R.id.header2);
		LinearLayout head1 = new LinearLayout(this);
		LinearLayout head2 = new LinearLayout(this);
		LinearLayout[] Zeilen = new LinearLayout[100];
		LinearLayout[] Zeilen1 = new LinearLayout[100];
		LinearLayout[] Zeilen2 = new LinearLayout[100];
		TextView Nutzer[] = new TextView[100];
		TextView[] Platz = new TextView[100];
		TextView[] Pkt = new TextView[100];

		LinearLayout.LayoutParams background1 = new LinearLayout.LayoutParams(
				width / 2, 60);
		LinearLayout.LayoutParams background2 = new LinearLayout.LayoutParams(
				width / 4, 60);
		LinearLayout.LayoutParams background_header = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 60);
		LinearLayout.LayoutParams AttrParam = new LinearLayout.LayoutParams(
				width / 4, 40);
		LinearLayout.LayoutParams FirstAttrParam = new LinearLayout.LayoutParams(
				40, 40);
		FirstAttrParam.gravity = Gravity.CENTER_VERTICAL;
		FirstAttrParam.setMargins(10, 0, 0, 0);
		background_header.setMargins(0, 0, 0, 20);
		AttrParam.gravity = Gravity.CENTER_VERTICAL;
		AttrParam.setMargins(width / 14, 0, 0, 0);
		background2.setMargins(width / 4, 0, 0, 0);

		// Überschrift
		Platz[0] = new TextView(this);
		Platz[0].setText("");
		Nutzer[0] = new TextView(this);
		Nutzer[0].setText("Spieler");
		Pkt[0] = new TextView(this);
		Pkt[0].setText("Punkte");

		Nutzer[0].setLayoutParams(AttrParam);
		Platz[0].setLayoutParams(FirstAttrParam);
		Pkt[0].setLayoutParams(AttrParam);

		head.setLayoutParams(background_header);
		head.setBackgroundResource(R.drawable.tabelle_hg);
		head.addView(head1);
		head.addView(head2);

		head1.setLayoutParams(background1);
		head2.setLayoutParams(background2);

		head1.addView(Platz[0]);
		head1.addView(Nutzer[0]);
		head2.addView(Pkt[0]);

		int i = 0;
		while (i * 2 + 1 < TTabelle.size()) {
			// Tupel
			Zeilen[i] = new LinearLayout(this);

			Zeilen1[i] = new LinearLayout(this);
			Zeilen1[i].setLayoutParams(background1);
			Zeilen1[i].setBackgroundResource(R.drawable.begegnungen_hg);

			Zeilen2[i] = new LinearLayout(this);
			Zeilen2[i].setLayoutParams(background2);
			Zeilen2[i].setBackgroundResource(R.drawable.begegnungen_hg_invers);

			// Attribute
			Platz[i] = new TextView(this);
			Platz[i].setText("" + (i + 1));

			Nutzer[i] = new TextView(this);
			Nutzer[i].setText(TTabelle.get(i * 2));

			Pkt[i] = new TextView(this);
			Pkt[i].setText(TTabelle.get(i * 2 + 1));

			// Params
			Platz[i].setLayoutParams(FirstAttrParam);
			Nutzer[i].setLayoutParams(AttrParam);
			Pkt[i].setLayoutParams(AttrParam);

			// Adding
			tabelle.addView(Zeilen[i]);
			Zeilen[i].addView(Zeilen1[i]);
			Zeilen[i].addView(Zeilen2[i]);
			Zeilen1[i].addView(Platz[i]);
			Zeilen1[i].addView(Nutzer[i]);
			Zeilen2[i].addView(Pkt[i]);

			i++;
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

				// UPDATE TABELLE
				tabelle.removeAllViews();
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

									// UPDATE TABELLE

									tabelle.removeAllViews();
									createTable();

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
