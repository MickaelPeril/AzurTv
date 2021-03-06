package com.azurtv.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.azurtv.R;

public class MainActivity extends FragmentActivity {

	// Declaration des Attributs
	private ViewPagerAdapter viewPagerAdapter = null;
	private ViewPager viewPager = null;

	private String[] drawerItemsList;
	private ListView myDrawer;

	public int nbFragment;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// recupere la vue de activity_main.xml
		setContentView(R.layout.activity_main);
		
		// rend le logo de l'actionbar cliquable
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);

		// localise le viewpager dans activity_main.xml
		viewPager = (ViewPager) findViewById(R.id.pager);

		// On met en place le viewPager
		viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(viewPagerAdapter);

		// recupere l'intent envoyer par HomeActivity afin d'afficher le bon
		// fragment lors de l'appui sur le bouton correspondant
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		viewPager.setCurrentItem(bundle.getInt("fragment"));

		// implementation du Navigation Drawer
		drawerItemsList = getResources().getStringArray(R.array.items);
		myDrawer = (ListView) findViewById(R.id.my_drawer);
		myDrawer.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_item, drawerItemsList));
		myDrawer.setOnItemClickListener(new OnItemClickListener() {

			// COmportement des items lors d'un click
			@SuppressWarnings("deprecation")
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long id) {
				if (id == 0) {
					itemSelector((int) id);
				} else if (id == 1) {
					itemSelector((int) id);
				} else if (id == 2) {
					itemSelector((int) id);
				} else if (id == 3) {
					itemSelector((int) id);
				} else if (id == 4) {
					itemSelector((int) id);
				} else if (id == 5) {
					Intent intent = new Intent(MainActivity.this, AboutActivity.class);
					startActivity(intent);
				} else if (id == 6) {
					showDialog(1);
				}
			}

			// appel du bon fragment lors de l'appui sur un item
			public void itemSelector(int num) {
				Intent intent2 = getIntent();
				final Bundle b2 = intent2.getExtras();
				Intent intent;
				intent = new Intent(MainActivity.this, MainActivity.class);
				intent.putExtras(b2);
				intent.putExtra("fragment", num);
				startActivity(intent);
			}
		});
	}

	// lance la popup qui permet la connexion a l'user
	protected Dialog onCreateDialog(int id) {
		AlertDialog dialogDetails = null;

		// Appel de layout du popup
		LayoutInflater inflater = LayoutInflater.from(this);
		View dialogview = inflater.inflate(R.layout.dialog_layout, null);

		// Affiche le popup
		AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
		dialogbuilder.setTitle("Connexion");
		dialogbuilder.setView(dialogview);
		dialogDetails = dialogbuilder.create();

		return dialogDetails;
	}

	// Comportement du popup lorsque l'utilisateur rentre ses identifiants et
	// appuis sur les bouton "connexion" et "annuler"
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {

		// Declaration des boutons et des editTexts
		final AlertDialog alertDialog = (AlertDialog) dialog;
		Button loginbutton = (Button) alertDialog.findViewById(R.id.btn_login);
		Button cancelbutton = (Button) alertDialog
				.findViewById(R.id.btn_cancel);
		final EditText userName = (EditText) alertDialog
				.findViewById(R.id.txt_name);
		final EditText password = (EditText) alertDialog
				.findViewById(R.id.password);

		// bouton connexion
		loginbutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				alertDialog.dismiss();
				Toast.makeText(
						MainActivity.this,
						"erreur de Connexion ",
						/*"User Name : " + userName.getText().toString()
								+ "  Password : "
								+ password.getText().toString(),*/
						Toast.LENGTH_LONG).show();
			}
		});

		// bouton annuler
		cancelbutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				alertDialog.dismiss();
			}
		});

	}

	// Cr�ation du menu en haut a droite
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	// Comportement des items dans le menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// Comportement du bouton logo (retour au menu)
			Intent intent_menu;
			intent_menu = new Intent(this, SplashActivity.class);
			startActivity(intent_menu);
			return true;
		case R.id.menu_refresh:
			// Comportement du bouton "Rafraichir"

			// on recupere le fragment courant
			int position = viewPager.getCurrentItem();
			Fragment currentFragment = viewPagerAdapter.getItem(position);

			// si le fragment courant est le fragment des podcasts, on recherche
			// les podcasts
			if (currentFragment instanceof PodcastFragment) {
				((PodcastFragment) currentFragment).loadPodcast();
			}
			if (currentFragment instanceof ProgrammeFragment) {
				((ProgrammeFragment) currentFragment).loadProgramme();
			}
//			if (currentFragment instanceof NewsFragment) {
//				SplashActivity splash= new SplashActivity();
//				splash.LoadNews();
//				HomeActivity home =new HomeActivity();
//				home.loadNews();
//				NewsFragment news = new NewsFragment();
//				news.loadNews();
//			}

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
