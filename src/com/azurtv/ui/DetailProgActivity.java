package com.azurtv.ui;

import com.azurtv.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class DetailProgActivity extends Activity {
	
	//Declaration de variables
	private TextView titleTextView = null;
	private WebView contentWebView = null;

	private String title = null;
	private String description = null;

	@SuppressLint("SetJavaScriptEnabled")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// on set la vue a afficher
		setContentView(R.layout.detailprog_layout);

		// on recupere les elements graphiques
		titleTextView = (TextView) findViewById(R.id.podcastTitle);
		contentWebView = (WebView) findViewById(R.id.contentWebView);
		contentWebView.setVerticalScrollBarEnabled(false);
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

			//le contenus du webview prends la taille de l'ecran
			 WebSettings settings =contentWebView.getSettings();
			    settings.setLoadWithOverviewMode(true);
			    settings.setUseWideViewPort(true);
			    settings.setDomStorageEnabled(true);
			    settings.setJavaScriptEnabled(true);
			    settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		}
	
		// et on affiche les données dans les elements graphiques
		titleTextView.setText(title);
		contentWebView.loadData(description, "text/html; charset=UTF-8", null);
		//permet le zoom sur le webview
		contentWebView.getSettings().setBuiltInZoomControls(true);	
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
