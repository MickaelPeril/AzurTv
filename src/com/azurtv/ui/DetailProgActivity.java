package com.azurtv.ui;

import com.azurtv.R;
import com.azurtv.parser.RSSFeed;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.widget.ScrollView;
import android.widget.TextView;



public class DetailProgActivity extends  Activity {
	private TextView titleTextView = null;
	private WebView contentWebView = null;
	
	private String title = null;;
	private String description = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// on set la vue a afficher
		setContentView(R.layout.detailprog_layout);

		// on recupere les elements graphiques
		titleTextView = (TextView)findViewById(R.id.podcastTitle);
		contentWebView = (WebView)findViewById(R.id.contentWebView);
		contentWebView.getSettings().setJavaScriptEnabled(true);

		fillWithData();
	}
	
	private void	fillWithData() {
		
		Intent intent = getIntent();
		
		if (intent != null) {
			// on recupere les donnees stoques lors de l'appel
			title = intent.getStringExtra("podcastTitle");
			description = intent.getStringExtra("podcastDescription");
			
			// ensuite, on va changer la taille de la video contenue dans la description pour un affichage plus agreable.

			// on recupere des informations sur la taille de l'ecran du smartphone
			Display display = getWindowManager().getDefaultDisplay();
			DisplayMetrics metrics = new DisplayMetrics();
			display.getMetrics(metrics);

			// on recupere la taille de la video dans la description
			/*int oldWidth = Integer.parseInt(getStringBetween(description, "width=\"", "\""));
			int oldHeight = Integer.parseInt(getStringBetween(description, "height=\"", "\""));
			
			// on calcule la nouvelle taille de la video par rapport a la taille de l'ecran
			int newWidth = (int)(((float)metrics.widthPixels) / ((float)metrics.density)) - 10; // -10 c'est pour avoir une petite marge
			int newHeight = (int)(((float)newWidth) / ((float)oldWidth) * ((float)oldHeight));*/

			// et on set la nouvelle taille de la video dans la description
		/*	description = description.replace("width=\"" + oldWidth + "\"", "width=\"" + newWidth + "\"");
			description = description.replace("height=\"" + oldHeight + "\"", "height=\"" + newHeight + "\"");*/
			
			
		}
		// et on affiche les données dans les elements graphiques
			titleTextView.setText(title);
			contentWebView.loadData(description, "text/html; charset=UTF-8", null);
		
	}
	
	// permet d'extraire une partie de chaine contenue entre deux autres chaines
	private String	getStringBetween(String data, String beginString, String endString)	{
		int start = description.indexOf(beginString) + beginString.length();
		int end = description.indexOf(endString, start);
		
		return description.substring(start, end);
	}
	
	// cette methode est appele lorque la configuration de l'ecran change (par exemple quand l'ecran est tourne)
	// quand ca arrive, on re-appelle fillWithData pour recalculer la taille de la video et l'afficher correctement
	@Override
	public void onConfigurationChanged(Configuration newConfig)	{
		super.onConfigurationChanged(newConfig);
		fillWithData();
	}
}
