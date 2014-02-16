package com.azurtv.podcast;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.azurtv.R;

// cette classe permet de remplir une listview avec les podcasts contenus dans une liste.
public class PodcastListViewAdapter extends BaseAdapter {
	private Activity activity = null;
	private List<Podcast> podcasts = null;
	private LayoutInflater inflater = null;
	
	// on initialise la liste des podcasts a afficher, et on set le layout inflater qui sera utilise pour construire les vues filles de la listview
	public PodcastListViewAdapter(Activity activity, List<Podcast> podcasts, LayoutInflater inflater) {
		this.activity = activity;
		this.podcasts = podcasts;
		this.inflater = inflater;
	}

	//
	// Toutes les methodes suivantes sont appelées automatiquement par la classe BaseAdapter lors du remplissage de la listview pour recuperer les elements a afficher.
	//
	
	@Override
	public int getCount() {
		
		return podcasts.size();
	}

	@Override
	public Object getItem(int position) {
		
		return podcasts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// cree un item de la liste view a une position donnée.
	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
			// on créé une nouvelle vue et on la remplie avec le podcast correspondant.
		view = inflater.inflate(R.layout.list_item, null);
		fillView(view, podcasts.get(position));
		return view;
	}
	
	// remplie la vue avec les informations du podcast.
	private void	fillView(View view, final Podcast podcast) {
		TextView		title = (TextView)view.findViewById(R.id.title);
		TextView		date = (TextView)view.findViewById(R.id.date);
		final ImageView	image = (ImageView)view.findViewById(R.id.thumb);
		
		title.setText(podcast.getTitle());
		date.setText(podcast.getDate());
		
		Thread	thread = new Thread() {
			@Override
			public void	run() {
				final Drawable	drawable = loadImageFromURL(podcast.getImage(), podcast.getImage());
				
				activity.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						if (drawable != null)
							image.setImageDrawable(drawable);
						else
							image.setImageResource(R.drawable.video_icon);
					}
				});
			}
		};
		thread.start();
	}

	private Drawable loadImageFromURL(String url, String name) {
	    try {
	        InputStream is = (InputStream) new URL(url).getContent();
	        Drawable d = Drawable.createFromStream(is, name);
	        return d;
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return null;
	    }
	}

	
}
