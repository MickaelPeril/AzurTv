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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.azurtv.R;
import com.azurtv.ui.ProgrammeFragment.CustomListAdapter;
import com.azurtv.parser.RSSFeed;
import com.azurtv.ui.DetailNewsActivity;
import com.azurtv.ui.ProgrammeFragment;
import com.azurtv.image.ImageLoader;

public class ProgrammeFragment  extends Fragment {
 
	Application myApp;
	RSSFeed feedprog;
	ListView lv;
	CustomListAdapter adapter;
	private Activity activity = null;

	
	@Override
	public void	 onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) { 
        // Get the view from fragmenttab1.xml
        View view = inflater.inflate(R.layout.programme_layout, container, false);
        //////
        myApp =  activity.getApplication();

		// Get feed form the file
		feedprog = (RSSFeed)  activity.getIntent().getExtras().get("feedprog");

		// Initialize the variables:
		lv = (ListView) view.findViewById(R.id.listViewProg);
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
				bundle.putSerializable("feedprog", feedprog);
				Intent intent = new Intent( activity,
						DetailProgActivity.class);
				intent.putExtras(bundle);
				intent.putExtra("pos", pos);
				startActivity(intent);

			}
		});
        /////
        return view;
    }
    
    @Override
	public void onDestroy() {
		super.onDestroy();
		adapter.imageLoader.clearCache();
		adapter.notifyDataSetChanged();
	}

	class CustomListAdapter extends BaseAdapter {

		private LayoutInflater layoutInflater;
		public ImageLoader imageLoader;

		public CustomListAdapter(ProgrammeFragment programmefragment) {

			layoutInflater = (LayoutInflater) programmefragment.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			imageLoader = new ImageLoader(programmefragment.activity.getApplicationContext());
		}

		@Override
		public int getCount() {

			// Set the total list item count
			return feedprog.getItemCount();
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
			imageLoader.DisplayImage(feedprog.getItem(pos).getImage(), iv);
			tvTitle.setText(feedprog.getItem(pos).getTitle());
			tvDate.setText(feedprog.getItem(pos).getDate());

			return listItem;
		}

	}
	
	

}