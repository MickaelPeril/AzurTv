package com.azurtv.ui;

import com.azurtv.R;
import com.azurtv.R.id;
import com.azurtv.R.layout;
import com.azurtv.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		Intent intent2= getIntent();
		final Bundle b2 = intent2.getExtras();
		final Button news , podcast, webtv, programme, jeu;
		
		news = (Button) findViewById(R.id.button1);
		podcast = (Button) findViewById(R.id.button2);
		webtv = (Button) findViewById(R.id.button3);
		programme = (Button) findViewById(R.id.button4);
		jeu = (Button) findViewById(R.id.button5);
		
		news.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	Intent intent;
	    		intent = new Intent( HomeActivity.this, MainActivity.class) ;
	    		
	    		intent.putExtras(b2);
	    		intent.putExtra("fragment", 0);
	    		startActivity(intent);
            	
            }
        });
		
		
		podcast.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	Intent intent;
	    		intent = new Intent( HomeActivity.this, MainActivity.class) ;
	    		
	    		intent.putExtras(b2);
	    		intent.putExtra("fragment", 1);
	    		startActivity(intent);
            	
            }
        });
		
		
		webtv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	Intent intent;
	    		intent = new Intent( HomeActivity.this, MainActivity.class) ;
	    		
	    		intent.putExtras(b2);
	    		intent.putExtra("fragment", 2);
	    		startActivity(intent);
            	
            }
        });
		
		programme.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	Intent intent;
	    		intent = new Intent( HomeActivity.this, MainActivity.class) ;
	    		
	    		intent.putExtras(b2);
	    		intent.putExtra("fragment", 3);
	    		startActivity(intent);
            	
            }
        });
		
		
		jeu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	Intent intent;
	    		intent = new Intent( HomeActivity.this, MainActivity.class) ;
	    		
	    		intent.putExtras(b2);
	    		intent.putExtra("fragment", 4);
	    		startActivity(intent);
            	
            }
        });
	}

	

}
