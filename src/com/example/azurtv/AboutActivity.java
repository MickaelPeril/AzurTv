package com.example.azurtv;

import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.view.Menu;
import android.widget.TextView;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		TextView tv_about;
		String html="<p><strong>AZUR TV</strong><br/><br>16, avenue Edouard Grinda - 06200 NICE<br>SAS au Capital de 5000 �uros- RCS Nice 790 322 291 00010 - APE 5911A</p><p> </p><p>Pr�sident : Herv� RAYNAUD<br>Responsable de la r�daction et de l'antenne : Philippe CODET</p><p>Site �dit� par : AZUR TV - h�berg� chez : OVH, SAS au capital de 500 k� RCS Roubaix � Tourcoing 424 761 419 00011 � Code APE 721Z - N� TVA : FR 22-424-761-419-00011<br>Si�ge social : 140 Quai du Sartel - 59100 Roubaix - France</p><p>D�claration CNIL N � 1646951 v 0 : LOI N� 78-17 DU 6 JANVIER 1978 RELATIVE � L'INFORMATIQUE, AUX FICHIERS ET AUX LIBERT�S Le pr�sent site fait l'objet de d�clarations � la CNIL. En regard de la loi 78-17 du 6 Janvier 1978, vous disposez d'un droit d'acc�s et de rectification aux donn�es personnelles vous concernant.</p><p> </p><p>Registrar  azur-tv.fr / azur-tv.com / azur-tv.org / azur-tv.net / azur-tv.eu / azur-tv.be / azur-tv.biz / azurtv.tv / azurtv.net : OVH - SAS au capital de 500 k� RCS Roubaix � Tourcoing 424 761 419 00011 � Code APE 721Z - N� TVA : FR 22-424-761-419-00011<br>Si�ge social : 140 Quai du Sartel - 59100 Roubaix - France";
		tv_about= (TextView)findViewById(R.id.tv1);
		tv_about.setText(Html.fromHtml(html));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}

}
