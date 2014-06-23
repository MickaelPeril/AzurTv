package com.azurtv.ui;

import com.azurtv.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity {
	
	 private void setLayout(int orientation) {
	    	// Les layouts R.layout.layoutHorizontal et R.layout.layoutVertical ci-dessous 
	    	// correspondent aux fichiers layoutHorizontal.xml et layoutVertical.xml 
	    	// définis dans le répertoire res/layout de l'arborescence projet (cf étape 
	    	// précédente).
	 
	    	final int res = (orientation == Configuration.ORIENTATION_LANDSCAPE ? 
	    	    	    	     R.layout.activity_home_horizontale : 
	    	    	    	     R.layout.activity_home);
	 
	    	setContentView(res);
	    }
	
	 @Override
	    public void onConfigurationChanged(Configuration newConfig) {        
	        super.onConfigurationChanged(newConfig);
	 
	        setLayout(newConfig.orientation);
	    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setLayout(getResources().getConfiguration().orientation);
		//setContentView(R.layout.activity_home);

		// recupération de l'intent envoyé par SplashActivity
		Intent intent2 = getIntent();
		final Bundle b2 = intent2.getExtras();
		final Button news, podcast, webtv, programme, about, contact;

		// Declaration de variables
		news = (Button) findViewById(R.id.News);
		podcast = (Button) findViewById(R.id.Podcast);
		webtv = (Button) findViewById(R.id.WebTv);
		programme = (Button) findViewById(R.id.ProgrammeTV);
		about = (Button) findViewById(R.id.About);
		contact = (Button) findViewById(R.id.Contact);

		// Comportement des boutons

		// bouton news
		news.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent intent;
				intent = new Intent(HomeActivity.this, MainActivity.class);

				intent.putExtras(b2);
				intent.putExtra("fragment", 0);
				startActivity(intent);

			}
		});

		// bouton podcast
		podcast.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent intent;
				intent = new Intent(HomeActivity.this, MainActivity.class);

				intent.putExtras(b2);
				intent.putExtra("fragment", 1);
				startActivity(intent);

			}
		});

		// bouton webtv
		webtv.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent intent;
				intent = new Intent(HomeActivity.this, MainActivity.class);

				intent.putExtras(b2);
				intent.putExtra("fragment", 2);
				startActivity(intent);

			}
		});

		// bouton programme
		programme.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent intent;
				intent = new Intent(HomeActivity.this, MainActivity.class);

				intent.putExtras(b2);
				intent.putExtra("fragment", 3);
				startActivity(intent);

			}
		});

		// bouton a propos
		about.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent intent;
				intent = new Intent(HomeActivity.this, AboutActivity.class);

				intent.putExtras(b2);
				intent.putExtra("fragment", 4);
				startActivity(intent);

			}
		});
		
		// bouton contact
				contact.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						Intent intent;
						intent = new Intent(HomeActivity.this, MainActivity.class);

						intent.putExtras(b2);
						intent.putExtra("fragment", 4);
						startActivity(intent);

					}
				});
	}

}
