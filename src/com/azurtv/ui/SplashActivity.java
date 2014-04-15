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

public class SplashActivity extends Activity
{
	// recuperation du xml
  private final String RSSFEEDURL = "http://www.azur-tv.fr/news.xml";
  RSSFeed feed;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    //on associe la vue
    setContentView(R.layout.splash_layout);
    //on regarde si le smartphone est connecté ou non
    ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    if (conMgr == null || conMgr.getActiveNetworkInfo() == null || !conMgr.getActiveNetworkInfo().isConnected() || !conMgr.getActiveNetworkInfo().isAvailable())
    {
      //Si pas de connexion, message d'erreur
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setMessage("Impossible de se connecter au serveur, \n S'il vous plaît vérifier votre connection.").setTitle("AzurTv").setCancelable(false).setPositiveButton(
          "Exit", new DialogInterface.OnClickListener()
          {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
              finish();
            }
          });

      AlertDialog alert = builder.create();
      alert.show();
    }
    else
    {
      // Si connexion alors on commence à parser le xml
      new AsyncLoadXMLFeed().execute();
    }
  }

  private class AsyncLoadXMLFeed extends AsyncTask<Void, Void, Void>
  {
    @Override
    protected Void doInBackground(Void... params)
    {
      // On recupere le feed ( le resultat du parseur )
      DOMParser myParser = new DOMParser();
      feed = myParser.parseXml(RSSFEEDURL);
      
      return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {
      super.onPostExecute(result);
      //on associe le feed à une clé pour ensuite le recuperer dans une autre Activity
      Bundle bundle = new Bundle();
      bundle.putSerializable("feed", feed); 
      //on lance la HomeActivity en lui envoyant le feed
      Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
      intent.putExtras(bundle);
      startActivity(intent);
      //on ferme cette Activity
      finish();
    }
  }
}
