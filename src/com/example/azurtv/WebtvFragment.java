package com.example.azurtv;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WebtvFragment extends Fragment {
 
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	 
    	 
        // Get the view from fragmenttab1.xml
        View view = inflater.inflate(R.layout.webtv_layout, container, false);
        return view;
    }
 
}