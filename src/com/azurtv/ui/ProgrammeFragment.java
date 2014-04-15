package com.azurtv.ui;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.azurtv.R;
import com.azurtv.network.HttpNetworkRequest;
import com.azurtv.network.OnNetworkRequestResult;
import com.azurtv.podcast.ExtractPodcast;
import com.azurtv.podcast.Podcast;
import com.azurtv.podcast.PodcastListViewAdapter;
import com.azurtv.ui.ProgrammeFragment;

public class ProgrammeFragment extends Fragment {

	private static final String programme_XML_URL = "http://www.azur-tv.fr/taxonomy/term/120/feed";

	private Activity activity = null;
	private LinearLayout loadingLayout = null;
	private ListView programmeListView = null;
	private TextView messageTextView = null;

	private List<Podcast> programme = null;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// on associe la vue de programme 
		View view = inflater.inflate(R.layout.programme_layout, container,
				false);

		// recuperation des elements graphiques
		loadingLayout = (LinearLayout) view.findViewById(R.id.loadingLayout);
		programmeListView = (ListView) view.findViewById(R.id.podcastsListView);
		messageTextView = (TextView) view.findViewById(R.id.messageTextView);

		loadProgramme();

		return view;
	}

	public void loadProgramme() {

		// on affichage une barre de chargement pendant que les podcasts se
		// chargent
		loadingLayout.setVisibility(View.VISIBLE);
		programmeListView.setVisibility(View.GONE);
		messageTextView.setVisibility(View.GONE);

		// on cree la requete internet et on l'envoie. On utilise l'object
		// ProgrammeFragment en tant que callback,
		// et les methodes onSuccess ou onError seront appele par la classe
		// HttpNetworkRequest en cas de succes ou d'erreur de la requete
		// internet
		HttpNetworkRequest networkRequest = new HttpNetworkRequest(
				programme_XML_URL, new OnRssFeedResult());
		networkRequest.sendRequest();
	}

	private class OnRssFeedResult implements OnNetworkRequestResult {

		@Override
		public void onSuccess(String result) {
			ExtractPodcast extractor = new ExtractPodcast();

			// on recupere une liste de programme a partir du resultat de la
			// recherche
			programme = extractor.extractItems(result);

			// si la liste n'est pas vide, on recupere les images des podcasts
			if (programme != null && programme.isEmpty() == false) {
				loadList();
			}
			// sinon il y a eu une erreur et on affiche un message d'erreur.
			else
				onError(0, null);
		}

		public void loadList() {
			activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {

					// on cache le chargement et on affiche la liste des
					// resultats
					loadingLayout.setVisibility(View.GONE);
					messageTextView.setVisibility(View.GONE);
					programmeListView.setVisibility(View.VISIBLE);

					// on rempli la listview avec les podcasts grace a un
					// adapter
					programmeListView.setAdapter(new PodcastListViewAdapter(
							activity, programme, LayoutInflater.from(activity)));

					// et on defini un evenement lorsqu'on click sur un item de
					// la listview
					programmeListView
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(
										AdapterView<?> viewAdapter, View view,
										int position, long id) {

									// lorsque l'utilisateur clique sur un item
									// de la liste, on recupere le podcast
									// correspondant
									Podcast podcast = programme.get(position);
									Intent intent = new Intent(activity,
											DetailProgActivity.class);

									// et on stoque les informations du podcast
									// dans l'intent utilise pour demarer la
									// nouvelle activite.
									intent.putExtra("podcastTitle",
											podcast.getTitle());
									intent.putExtra("podcastDescription",
											podcast.getDescription());
									startActivity(intent);
								}
							});
				}
			});
		}

		@Override
		public void onError(int errorCode, String message) {
			// meme chose que dans la methode onSuccess : on repasse sur le UI
			// thread et on affiche un message d'erreur
			activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {

					// en cas d'erreur, on cache le chargement et on affiche un
					// message d'erreur
					loadingLayout.setVisibility(View.GONE);
					messageTextView.setText(activity
							.getString(R.string.error_programme));
					messageTextView.setVisibility(View.VISIBLE);
				}
			});
		}

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

	}
}