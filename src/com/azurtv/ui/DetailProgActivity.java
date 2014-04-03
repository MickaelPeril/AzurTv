package com.azurtv.ui;

import com.azurtv.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.webkit.WebView;
import android.widget.TextView;

public class DetailProgActivity extends Activity {
	
	//Declaration de variables
	private TextView titleTextView = null;
	private WebView contentWebView = null;

	private String title = null;;
	private String description = null;

	@SuppressLint("SetJavaScriptEnabled")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// on set la vue a afficher
		setContentView(R.layout.detailprog_layout);

		// on recupere les elements graphiques
		titleTextView = (TextView) findViewById(R.id.podcastTitle);
		contentWebView = (WebView) findViewById(R.id.contentWebView);
		contentWebView.getSettings().setJavaScriptEnabled(true);

		fillWithData();
	}

	// methodes de calcul de la taille de l'ecran 
	private void fillWithData() {

		Intent intent = getIntent();

		if (intent != null) {
			// on recupere les donnees stoques lors de l'appel
			title = intent.getStringExtra("podcastTitle");
			description = intent.getStringExtra("podcastDescription");

			// ensuite, on va changer la taille de la video contenue dans la
			// description pour un affichage plus agreable.

			// on recupere des informations sur la taille de l'ecran du
			// smartphone
			Display display = getWindowManager().getDefaultDisplay();
			DisplayMetrics metrics = new DisplayMetrics();
			display.getMetrics(metrics);


		}
		// et on affiche les données dans les elements graphiques
		titleTextView.setText(title);
		contentWebView.loadData(description, "text/html; charset=UTF-8", null);

	}

	

	// cette methode est appele lorque la configuration de l'ecran change (par
	// exemple quand l'ecran est tourne)
	// quand ca arrive, on re-appelle fillWithData pour recalculer la taille de
	// la video et l'afficher correctement
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		fillWithData();
	}
}
