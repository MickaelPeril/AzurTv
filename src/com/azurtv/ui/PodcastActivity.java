package com.azurtv.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.TextView;

import com.azurtv.R;

public class PodcastActivity extends Activity
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// on set la vue a afficher
		setContentView(R.layout.activity_podcast);
		
		Intent	intent = getIntent();
		
		if (intent != null)
		{
			// on recupere les donnees stoques lors de l'appel
			String	title = intent.getStringExtra("podcastTitle");
			String	description = intent.getStringExtra("podcastDescription");

			// on recupere les elements graphiques
			TextView	titleTextView = (TextView)findViewById(R.id.podcastTitle);
			WebView		contentWebView = (WebView)findViewById(R.id.contentWebView);

			// et on affiche les données dans les elements graphiques
			titleTextView.setText(title);
			contentWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			contentWebView.getSettings().setJavaScriptEnabled(true);
			contentWebView.loadData(description, "text/html; charset=UTF-8", null);
		}
	}
}
