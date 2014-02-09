package com.azurtv.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.azurtv.R;

/**
 *  
 * @author Cesar
 *
 */
public class MainActivity extends FragmentActivity {
 
	private ViewPagerAdapter	viewPagerAdapter = null;
	private ViewPager			viewPager = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from activity_main.xml
        setContentView(R.layout.activity_main);
 
        // Locate the viewpager in activity_main.xml
        viewPager = (ViewPager) findViewById(R.id.pager);
 
        // Set the ViewPagerAdapter into ViewPager
        
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
    }
 
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
            case R.id.menu_about:
                    // Comportement du bouton "À Propos"
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
            		int			position = viewPager.getCurrentItem();
            		Fragment	currentFragment = viewPagerAdapter.getItem(position);
            		
            		// si le fragment courant est le fragment des podcasts, on recherche les podcasts
            		if (currentFragment instanceof PodcastFragment)
            		{
            			((PodcastFragment)currentFragment).loadPodcast();
            		}
            	
                    return true;
            case R.id.menu_search:
                    // Comportement du bouton "Recherche"
                    return true;
            case R.id.menu_settings:
                    // Comportement du bouton "Paramètres"
            	Intent intent_settings;
            	intent_settings = new Intent(this, SettingsActivity.class);
        		startActivity(intent_settings);
                    return true;
            default:
                    return super.onOptionsItemSelected(item);
            }
    }
}
