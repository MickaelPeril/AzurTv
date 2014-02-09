package com.azurtv.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class ViewPagerAdapter extends FragmentPagerAdapter {
 
    final int PAGE_COUNT = 5;
    // Tab Titles
    private String tabtitles[] = new String[] { "News", "Podcast", "WebTV", "Programmes", "Jeu" };
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
            NewsFragment newsfragment = new NewsFragment();
            return  newsfragment;
 
            // Open FragmentTab2.java
        case 1:
            PodcastFragment podcastfragment = new PodcastFragment();
            return podcastfragment ;
 
            // Open FragmentTab3.java
        case 2:
            WebtvFragment  webtvfragment= new WebtvFragment();
            return webtvfragment;
        case 3:
           ProgrammeFragment  programmefragment= new ProgrammeFragment();
            return programmefragment;
        case 4:
            JeuFragment  jeufragment= new JeuFragment();
             return jeufragment;
        }
        return null;
    }
 
    
    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }
}