package com.azurtv.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.azurtv.R;
import com.azurtv.image.ImageLoader;
import com.azurtv.parser.RSSFeed;

public class NewsFragment extends Fragment {

	//Declaration des Attributs
	Application myApp;
	RSSFeed feed;
	ListView lv;

	CustomListAdapter adapter;

	private Activity activity = null;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// On recupere la vue de fragmenttab1.xml (vue news)
		View view = inflater.inflate(R.layout.news_layout, container, false);

		myApp = activity.getApplication();

		// on recupere le feed qui vient de l'activity de ce fragment ( MainActivity )
		feed = (RSSFeed) activity.getIntent().getExtras().get("feed");

		//on initialise la listview pour les news
		lv = (ListView) view.findViewById(R.id.listViewNews);
		lv.setVerticalFadingEdgeEnabled(true);

		// Adapter sur le listview
		adapter = new CustomListAdapter(this);
		lv.setAdapter(adapter);

		// click sur les differentes news
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// l'action quand on clique sur un item
				int pos = arg2;
				//on envois le feed à l'activity detail news pour afficher en detail la news
				Bundle bundle = new Bundle();
				bundle.putSerializable("feed", feed);
				Intent intent = new Intent(activity, DetailNewsActivity.class);
				intent.putExtras(bundle);
				intent.putExtra("pos", pos);
				startActivity(intent);
			}
		});
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		adapter.imageLoader.clearCache();
		adapter.notifyDataSetChanged();
	}

	class CustomListAdapter extends BaseAdapter {
		private final LayoutInflater layoutInflater;
		public ImageLoader imageLoader;

		public CustomListAdapter(NewsFragment newsfragment) {
			layoutInflater = (LayoutInflater) newsfragment.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			imageLoader = new ImageLoader(newsfragment.activity.getApplicationContext());
		}

		@Override
		public int getCount() {
			// retourne le nombre d'item de la liste
			return feed.getItemCount();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			// on met en place la vue
			View listItem = convertView;
			int pos = position;
			if (listItem == null) {
				listItem = layoutInflater.inflate(R.layout.list_item, null);
			}

			// on initialise les elements de la vue
			ImageView iv = (ImageView) listItem.findViewById(R.id.thumb);
			TextView tvTitle = (TextView) listItem.findViewById(R.id.title);
			TextView tvDate = (TextView) listItem.findViewById(R.id.date);

			// on affiche les elements
			imageLoader.DisplayImage(feed.getItem(pos).getImage(), iv);
			tvTitle.setText(feed.getItem(pos).getTitle());
			tvDate.setText(feed.getItem(pos).getDate());

			return listItem;
		}
	}
}
