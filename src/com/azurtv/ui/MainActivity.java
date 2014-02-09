package com.azurtv.ui;

import com.azurtv.ui.HelpActivity;
import com.example.azurtv.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentActivity;

/**
 *  
 * @author Cesar
 *
 */
public class MainActivity extends FragmentActivity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from activity_main.xml
        setContentView(R.layout.activity_main);
 
        // Locate the viewpager in activity_main.xml
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
 
        // Set the ViewPagerAdapter into ViewPager
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
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
