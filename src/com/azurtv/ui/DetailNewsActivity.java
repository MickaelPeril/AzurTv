package com.azurtv.ui;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.azurtv.R;
import com.azurtv.parser.RSSFeed;

public class DetailNewsActivity extends Activity {

	//Declaration de variables
	RSSFeed feed;

	TextView title;

	WebView desc;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_news_layout);

		// mettre en visible le ScrollView car par default il est desactiver
		ScrollView sv = (ScrollView) findViewById(R.id.sv);
		sv.setVerticalFadingEdgeEnabled(true);

		// recupere les positions du "feed" depuis l'intent
		feed = (RSSFeed) getIntent().getExtras().get("feed");
		int pos = getIntent().getExtras().getInt("pos");

		// Initialisation des vues
		title = (TextView) findViewById(R.id.title);
		desc = (WebView) findViewById(R.id.desc);

		// set les propriété du webView
		WebSettings ws = desc.getSettings();
		ws.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		ws.getPluginState();
		ws.setPluginState(PluginState.ON);
		ws.setJavaScriptEnabled(true);
		ws.setBuiltInZoomControls(true);

		// Set les vues
		title.setText(feed.getItem(pos).getTitle());
		desc.loadDataWithBaseURL("http://www.azur-tv.fr/news.xml", feed
				.getItem(pos).getDescription(), "text/html", "UTF-8", null);
	}

}
