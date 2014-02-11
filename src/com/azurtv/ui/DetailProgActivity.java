package com.azurtv.ui;

import com.azurtv.R;
import com.azurtv.R.layout;
import com.azurtv.R.menu;
import com.azurtv.parser.RSSFeed;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.widget.ScrollView;
import android.widget.TextView;

public class DetailProgActivity extends Activity {

	RSSFeed feedprog;
	TextView title;
	WebView desc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailprog_layout);

		// Enable the vertical fading edge (by default it is disabled)
		ScrollView sv = (ScrollView) findViewById(R.id.sv);
		sv.setVerticalFadingEdgeEnabled(true);

		// Get the feed object and the position from the Intent
		feedprog = (RSSFeed) getIntent().getExtras().get("feedprog");
		int pos = getIntent().getExtras().getInt("pos");

		// Initialize the views
		title = (TextView) findViewById(R.id.title);
		desc = (WebView) findViewById(R.id.desc);

		// set webview properties
		WebSettings ws = desc.getSettings();
		ws.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		ws.getPluginState();
		ws.setPluginState(PluginState.ON);
		ws.setJavaScriptEnabled(true);
		ws.setBuiltInZoomControls(true);

		// Set the views
		title.setText(feedprog.getItem(pos).getTitle());
		desc.loadDataWithBaseURL("http://www.azur-tv.fr/taxonomy/term/120/feed", feedprog
				.getItem(pos).getDescription(), "text/html", "UTF-8", null);
	}

}