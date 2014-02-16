package com.azurtv.podcast;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class ExtractPodcast
{
	private static final String ITEM = "item";
	private static final String TITLE = "title";
	private static final String DESCRIPTION = "description";
	private static final String DATE = "pubDate";
	
	// extrait les podcasts depuis le flux rss obtenue avec la requete internet
	public List<Podcast> extractItems(String result) {
		List<Podcast> podcasts = new LinkedList<Podcast>();
		Podcast currentItem = null;
		
		// on utilise un parser XML pour extraire les informations du flux rss
		XmlPullParser parser = Xml.newPullParser();
		try {
			
			parser.setInput(new StringReader(result));
			
			int		eventType = parser.getEventType();
			
			// on parcoure l'arbre XML jusqu'a la fin
			while (eventType != XmlPullParser.END_DOCUMENT) {

				// si on rencontre une balise xml ouvrante
				if (eventType == XmlPullParser.START_TAG) {

					// on recupere le nom de la balise
					String	name = parser.getName();
					
					if (name != null) {
						// si il s'agit d'un item, on instancie un nouveau podcast
						if (name.equals(ITEM))
							currentItem = new Podcast();
						else if (currentItem != null) {
							// sinon s'il s'agit d'un titre, on rempli le podcast precedemment instancie avec la valeur.
							if (name.equals(TITLE))
								currentItem.setTitle(parser.nextText());
							// pareil pour la description
							else if (name.equals(DESCRIPTION))
								currentItem.setDescription(parser.nextText());
							else if (name.equals(DATE))
							{
								String	date = parser.nextText();
								currentItem.setDate(date.substring(0, date.length() - 6));
							}
								
						}
					}
				}
				// si on rencontre une balise xml fermante
				else if (eventType == XmlPullParser.END_TAG) {

					String	name = parser.getName();
					
					// si c'est une balise fermante item, on ajoute le podcast courant dans la liste.
					if (name.equals(ITEM) && currentItem != null) {
						
						podcasts.add(currentItem);
						currentItem = null;
					}
				}
				// on passe a la balise suivante
				eventType = parser.next();
			}
			
			// une fois qu'on a extrait tout le fichier, on retourne la liste de podcasts.
			return podcasts;
		}
		// si des erreurs se sont produites lors de l'extraction des données, on retourne null.
		catch (XmlPullParserException e) {
			e.printStackTrace();
			return null;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void		extractImages(String result, List<Podcast> podcasts) {
		
		//extrait les images de la page html avec une regex
		String imgRegex = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
		Pattern p = Pattern.compile(imgRegex, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(result);
		
		// on recupere les images et on les set dans les podcasts
		int		it = 0, length = podcasts.size();
		boolean	isFirst = true;
		while (m.find() && it < length) {
			String src = m.group(1);
			
			if (src != null && isFirst == false)
			{
				Podcast	podcast = podcasts.get(it);
				podcast.setImage(src);
				++it;
			}
			else if (isFirst == true)
				isFirst = false;

		}
	}
}
