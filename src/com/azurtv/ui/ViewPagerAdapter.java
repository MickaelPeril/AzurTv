package com.azurtv.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class ViewPagerAdapter extends FragmentPagerAdapter {
 
    final int PAGE_COUNT = 5;
    // Tab Titles
    private String 		tabtitles[] = new String[] { "News", "Podcast", "WebTV", "Programmes", "Jeu" };
    private Fragment	fragments[] = new Fragment[PAGE_COUNT];
    
    Context context;
 
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
 
    @Override
    public Fragment getItem(int position) {
        switch (position) {
 
            // Open FragmentTab1.java
        case 0:
        	if (fragments[0] == null)
        		fragments[0] = new NewsFragment();
        	return fragments[0];
 
            // Open FragmentTab2.java
        case 1:
        	if (fragments[1] == null)
        		fragments[1] = new PodcastFragment();
        	return fragments[1];
 
            // Open FragmentTab3.java
        case 2:
        	if (fragments[2] == null)
        		fragments[2] = new WebtvFragment();
        	return fragments[2];
        case 3:
        	if (fragments[3] == null)
        		fragments[3] = new ProgrammeFragment();
        	return fragments[3];
        case 4:
        	if (fragments[4] == null)
        		fragments[4] = new JeuFragment();
        	return fragments[4];
        }
        return null;
    }
 
    
    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }
}