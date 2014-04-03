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

		// Get the view from fragmenttab1.xml
		View view = inflater.inflate(R.layout.news_layout, container, false);
		// /

		myApp = activity.getApplication();

		// Get feed form the file
		feed = (RSSFeed) activity.getIntent().getExtras().get("feed");

		// Initialize the variables:
		lv = (ListView) view.findViewById(R.id.listViewNews);
		lv.setVerticalFadingEdgeEnabled(true);

		// Set an Adapter to the ListView
		adapter = new CustomListAdapter(this);
		lv.setAdapter(adapter);

		// Set on item click listener to the ListView
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// actions to be performed when a list item clicked
				int pos = arg2;

				Bundle bundle = new Bundle();
				bundle.putSerializable("feed", feed);
				Intent intent = new Intent(activity, DetailNewsActivity.class);
				intent.putExtras(bundle);
				intent.putExtra("pos", pos);
				startActivity(intent);

			}
		});

		// /
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

			layoutInflater = (LayoutInflater) newsfragment.activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			imageLoader = new ImageLoader(
					newsfragment.activity.getApplicationContext());
		}

		@Override
		public int getCount() {

			// Set the total list item count
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

			// Inflate the item layout and set the views
			View listItem = convertView;
			int pos = position;
			if (listItem == null) {
				listItem = layoutInflater.inflate(R.layout.list_item, null);
			}

			// Initialize the views in the layout
			ImageView iv = (ImageView) listItem.findViewById(R.id.thumb);
			TextView tvTitle = (TextView) listItem.findViewById(R.id.title);
			TextView tvDate = (TextView) listItem.findViewById(R.id.date);

			// Set the views in the layout
			imageLoader.DisplayImage(feed.getItem(pos).getImage(), iv);
			tvTitle.setText(feed.getItem(pos).getTitle());
			tvDate.setText(feed.getItem(pos).getDate());

			return listItem;
		}

	}

}
