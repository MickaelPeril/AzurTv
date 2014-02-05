package com.example.azurtv;

import android.os.Bundle;
import android.app.Activity;
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
                    return true;
            case R.id.menu_help:
                    // Comportement du bouton "Aide"
                    return true;
            case R.id.menu_refresh:
                    // Comportement du bouton "Rafraichir"
                    return true;
            case R.id.menu_search:
                    // Comportement du bouton "Recherche"
                    return true;
            case R.id.menu_settings:
                    // Comportement du bouton "Paramètres"
                    return true;
            default:
                    return super.onOptionsItemSelected(item);
            }
    }
}
