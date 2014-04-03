package com.azurtv.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
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


public class MainActivity
    extends FragmentActivity
{

  private ViewPagerAdapter viewPagerAdapter = null;

  private ViewPager viewPager = null;

  private String[] drawerItemsList;

  private ListView myDrawer;

  public int nbFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    // Get the view from activity_main.xml
    setContentView(R.layout.activity_main);

    // Locate the viewpager in activity_main.xml
    viewPager = (ViewPager) findViewById(R.id.pager);

    // Set the ViewPagerAdapter into ViewPager
    viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    viewPager.setAdapter(viewPagerAdapter);

    Intent intent = getIntent();
    Bundle bundle = intent.getExtras();
    viewPager.setCurrentItem(bundle.getInt("fragment"));
    // ///
    drawerItemsList = getResources().getStringArray(R.array.items);
    myDrawer = (ListView) findViewById(R.id.my_drawer);
    myDrawer.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item, drawerItemsList));
    myDrawer.setOnItemClickListener(new OnItemClickListener()
    {

        @SuppressWarnings("deprecation")
		@Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long id)
        {
        	if(id==0){
        		itemSelector((int)id);
        	}else if(id==1){
        		itemSelector((int)id);
        	}else if(id==2){
        		itemSelector((int)id);
        	}else if(id==3){
        		itemSelector((int)id);
        	}else if(id==4){
        		itemSelector((int)id);
        	}else if(id==5){
        	
        		
        		 showDialog(1);
        		
        		//mon compte
        	}    	
        }
        
        public void itemSelector(int num){
        	Intent intent2= getIntent();
    		final Bundle b2 = intent2.getExtras();		
    		Intent intent;
    		intent = new Intent( MainActivity.this, MainActivity.class) ;
    		intent.putExtras(b2);
    		intent.putExtra("fragment", num);
    		startActivity(intent);	       	
        }    
    });
    
    // //
  }

  
  protected Dialog onCreateDialog(int id) {

	  AlertDialog dialogDetails = null;

	  
	   LayoutInflater inflater = LayoutInflater.from(this);
	   View dialogview = inflater.inflate(R.layout.dialog_layout, null);

	   AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
	   dialogbuilder.setTitle("Connexion");
	   dialogbuilder.setView(dialogview);
	   dialogDetails = dialogbuilder.create();

	 

	  return dialogDetails;
	 }

	 @Override
	 protected void onPrepareDialog(int id, Dialog dialog) {

	  
	   final AlertDialog alertDialog = (AlertDialog) dialog;
	   Button loginbutton = (Button) alertDialog
	     .findViewById(R.id.btn_login);
	   Button cancelbutton = (Button) alertDialog
	     .findViewById(R.id.btn_cancel);
	   final EditText userName = (EditText) alertDialog
	     .findViewById(R.id.txt_name);
	   final EditText password = (EditText) alertDialog
	     .findViewById(R.id.password);

	   loginbutton.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
	    	
	     alertDialog.dismiss();
	     Toast.makeText(
	       MainActivity.this,
	       "User Name : " + userName.getText().toString()
	         + "  Password : "
	         + password.getText().toString(),
	       Toast.LENGTH_LONG).show();
	    }
	   });

	   cancelbutton.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
	     alertDialog.dismiss();
	    }
	   });
	  
	 }

  
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    switch (item.getItemId())
    {
    case R.id.menu_about:
      // Comportement du bouton "a Propos"
      Intent intent_about;
      intent_about = new Intent(this, AboutActivity.class);
      startActivity(intent_about);
      return true;
    case R.id.menu_help:
      // Comportement du bouton "Aide"
      Intent intent_help;
      intent_help = new Intent(this, HelpActivity.class);
      startActivity(intent_help);
      return true;
    case R.id.menu_refresh:
      // Comportement du bouton "Rafraichir"

      // on recupere le fragment courant
      int position = viewPager.getCurrentItem();
      Fragment currentFragment = viewPagerAdapter.getItem(position);

      // si le fragment courant est le fragment des podcasts, on recherche les podcasts
      if (currentFragment instanceof PodcastFragment)
      {
        ((PodcastFragment) currentFragment).loadPodcast();
      }
      if (currentFragment instanceof ProgrammeFragment)
      {
        ((ProgrammeFragment) currentFragment).loadPodcast();
      }


      return true;
    case R.id.menu_search:
      // Comportement du bouton "Recherche"
      return true;
    case R.id.menu_settings:
      // Comportement du bouton "Param�tres"
      Intent intent_settings;
      intent_settings = new Intent(this, SettingsActivity.class);
      startActivity(intent_settings);
      return true;
    default:
      return super.onOptionsItemSelected(item);
    }
  }
}
