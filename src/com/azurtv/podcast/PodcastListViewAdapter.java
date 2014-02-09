package com.azurtv.podcast;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.azurtv.R;

// cette classe permet de remplir une listview avec les podcasts contenus dans une liste.
public class PodcastListViewAdapter extends BaseAdapter
{
	private List<Podcast>	podcasts = null;
	private LayoutInflater	inflater = null;
	
	// on initialise la liste des podcasts a afficher, et on set le layout inflater qui sera utilise pour construire les vues filles de la listview
	public PodcastListViewAdapter(List<Podcast> podcasts, LayoutInflater inflater)
	{
		this.podcasts = podcasts;
		this.inflater = inflater;
	}

	//
	// Toutes les methodes suivantes sont appelées automatiquement par la classe BaseAdapter lors du remplissage de la listview pour recuperer les elements a afficher.
	//
	
	@Override
	public int getCount()
	{
		return podcasts.size();
	}

	@Override
	public Object getItem(int position)
	{
		return podcasts.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	// cree un item de la liste view a une position donnée.
	@Override
	public View getView(int position, View view, ViewGroup viewGroup)
	{
		// si une vue a deja été créée, on utilise cette vue et on la remplie avec le podcast situé à la position données dans la list.
		if (view != null)
			fillView(view, podcasts.get(position));
		else
		{
			// sinon, on créé une nouvelle vue et on la remplie avec le podcast correspondant.
			view = inflater.inflate(R.layout.podcast_listview_item, null);
			fillView(view, podcasts.get(position));
		}
		return view;
	}
	
	// remplie la vue avec les informations du podcast.
	private void	fillView(View view, Podcast podcast)
	{
		TextView	title = (TextView)view.findViewById(R.id.podcastTitle);
		title.setText(podcast.getTitle());
	}

}
