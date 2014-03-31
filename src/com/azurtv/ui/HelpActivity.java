package com.azurtv.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.azurtv.R;

public class HelpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		final TextView redaction,GALUP,emission,charay,decorte,ogcn,tierche, lefebre,general ;
		
		
		String html="<tr><td><h1> Le Journal</h1><p>(envoyer un message a la redaction)<br></p></td><td></a></td></tr>";
		redaction= (TextView)findViewById(R.id.tv1);redaction.setText(Html.fromHtml(html));
		
		String html1="<tr><td>Nicolas GALUP<p>Rédacteur en Chef<br></p><p>Le Brief<br></p></td><td></a></td></tr>";
		GALUP= (TextView)findViewById(R.id.tv2);GALUP.setText(Html.fromHtml(html1));
		
		String html2="<tr><td><h1>La Grande Emission</h1><p>(Envoyer un message à toute l'Equipe de la Grande Emission)<br></p></td><td></a></td></tr>";
		emission= (TextView)findViewById(R.id.tv3);emission.setText(Html.fromHtml(html2));
		
		String html3="<tr><td>Eric CHARAY<p>Animateur de La Grande Emission<br></p></td><td></a></td></tr>";
		charay= (TextView)findViewById(R.id.tv4);charay.setText(Html.fromHtml(html3));
		
		String html4="<tr><td>Viriginie DECORTE<p>Animateur de La Grande Emission<br></p></td><td></a></td></tr>";
		decorte= (TextView)findViewById(R.id.tv5);decorte.setText(Html.fromHtml(html4));
		
		String html5="<tr><td><h1>OGCN TV</h1><p>(Envoyer un message à toute l'Equipe de l'OGCN TV)<br></p></td><td></a></td></tr>";
		ogcn= (TextView)findViewById(R.id.tv6);ogcn.setText(Html.fromHtml(html5));
		
		String html6="<tr><td>Nassim TIRECHE<p>JRI OGCN TV<br></p></td><td></a></td></tr>";
		tierche= (TextView)findViewById(R.id.tv7);tierche.setText(Html.fromHtml(html6));
		
		String html7="<tr><td>Nassim TIRECHE<p>LE DEBRIEF<br></p></td><td></a></td></tr>";
		lefebre= (TextView)findViewById(R.id.tv8);lefebre.setText(Html.fromHtml(html7));
		
		String html8="<tr><td><h1>Contact général</h1></td><td></a></td></tr>";
		general= (TextView)findViewById(R.id.tv9);general.setText(Html.fromHtml(html8));
		
		redaction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent email = new Intent(Intent.ACTION_SEND);
        		email.setType("text/plain");
        		email.putExtra(android.content.Intent.EXTRA_EMAIL,"email@gmail.com");
        		email.putExtra(Intent.EXTRA_SUBJECT, "Obtenir de l'aide");
        		email.putExtra(Intent.EXTRA_TEXT, "Saisir votre demande ici...");
        		email.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        		startActivity(email);
            
            }
        });
		
		
		GALUP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	GALUP.setText("Cocoa");
            
            }
        });
		
		emission.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	emission.setText("Cocoa");
            
            }
        });
		
		charay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	charay.setText("Cocoa");
            
            }
        });


		decorte.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	decorte.setText("Cocoa");
            
            }
        });
		
		ogcn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	ogcn.setText("Cocoa");
            
            }
        });
		

		tierche.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	tierche.setText("Cocoa");
            
            }
        });
		
		lefebre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	lefebre.setText("Cocoa");
            
            }
        });
		
		general.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	general.setText("Cocoa");
            
            }
        });
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help, menu);
		return true;
	}

}
