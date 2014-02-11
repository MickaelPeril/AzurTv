package com.azurtv.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;

import com.azurtv.R;
import com.azurtv.parser.DOMParser;
import com.azurtv.parser.RSSFeed;

public class SplashActivity extends Activity {

	private String RSSFEEDURL = "http://www.azur-tv.fr/news.xml";
	RSSFeed feed;
	private String RSSFEEDURLprog = "http://www.azur-tv.fr/taxonomy/term/120/feed";
	RSSFeed feedprog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.splash_layout);

		ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conMgr.getActiveNetworkInfo() == null
				&& !conMgr.getActiveNetworkInfo().isConnected()
				&& !conMgr.getActiveNetworkInfo().isAvailable()) {
			// No connectivity - Show alert
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(
					"Unable to reach server, \nPlease check your connectivity.")
					.setTitle("TD RSS Reader")
					.setCancelable(false)
					.setPositiveButton("Exit",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int id) {
									finish();
								}
							});

			AlertDialog alert = builder.create();
			alert.show();

		} else {
			// Connected - Start parsing
			new AsyncLoadXMLFeed().execute();

		}

	}

	private class AsyncLoadXMLFeed extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			// Obtain feed
			DOMParser myParser = new DOMParser();
			feed = myParser.parseXml(RSSFEEDURL);
			
			DOMParser myParserprog = new DOMParser();
			feedprog = myParserprog.parseXml(RSSFEEDURLprog);
			
			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			Bundle bundle = new Bundle();
			bundle.putSerializable("feed", feed);
			
			Bundle bundleprog = new Bundle();
			bundleprog.putSerializable("feedprog", feedprog);

			// launch List activity
			Intent intent = new Intent(SplashActivity.this, MainActivity.class);
			intent.putExtras(bundle);
			intent.putExtras(bundleprog);
			startActivity(intent);

			// kill this activity
			finish();
		}

	}

}
