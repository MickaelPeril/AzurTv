package com.azurtv.ui;

import com.example.azurtv.R;

import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.view.Menu;
import android.widget.TextView;

public class HelpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);

		TextView tv_about;
		String html="<tr><td><h1> Le Journal</h1><p>(Envoyer un message à la rédaction)<br></p></td><td></a></td></tr>";
		tv_about= (TextView)findViewById(R.id.tv1);		tv_about.setText(Html.fromHtml(html));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help, menu);
		return true;
	}

}