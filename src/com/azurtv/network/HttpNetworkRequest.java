package com.azurtv.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

// Classe pour envoyer des requetes https et recuperer le resultat de facon generique.
public class HttpNetworkRequest
{
	private String					url = null;
	private OnNetworkRequestResult	onNetworkRequestResult = null;
	
	public HttpNetworkRequest(String url, OnNetworkRequestResult onNetworkRequestResult)
	{
		// url ou envoyer la requete
		this.url = url;
		// interface contenant des methodes a appeler une fois que la requete est terminée
		this.onNetworkRequestResult = onNetworkRequestResult;
	}
	
	public void		sendRequest()
	{
		// creation d'un thread pour envoyer la requete sur un thread different que le thread graphique
		Thread		thread = new Thread()
		{
			@Override
			public void		run()
			{
				try
				{
					// creation d'un client http et envoie de la requete internet
					HttpClient		httpclient = new DefaultHttpClient();
				    HttpResponse 	response = httpclient.execute(new HttpGet(url));
				    
				    StatusLine		statusLine = response.getStatusLine();

				    // si la requete a reussi, on recupere le resultat
				    if(statusLine.getStatusCode() == HttpStatus.SC_OK)
				    {
				        ByteArrayOutputStream out = new ByteArrayOutputStream();
				        response.getEntity().writeTo(out);
				        out.close();
				        
				        // et on envoie le resultat la callback de succes precedemment defini
				        if (onNetworkRequestResult != null)
				        	onNetworkRequestResult.onSuccess(out.toString("utf-8"));
				    }
				    else
				    {
				    	// si la requete a echoue, on appelle la callback d'erreur precedemment defini
				        response.getEntity().getContent().close();
				        if (onNetworkRequestResult != null)
				        	onNetworkRequestResult.onError(statusLine.getStatusCode(), statusLine.getReasonPhrase());			        
				    }

				}
				// si des erreurs se sont produites, on appellle aussi la callback d'erreur.
				catch (ClientProtocolException e)
				{
			        if (onNetworkRequestResult != null)
			        	onNetworkRequestResult.onError(-1, null);			        
				}
				catch (IOException e)
				{
			        if (onNetworkRequestResult != null)
			        	onNetworkRequestResult.onError(-2, null);			        
				}			    
			}
		};
		
		// on lance le thread
		thread.start();
	}
}
