package com.azurtv.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azurtv.R;

public class ProgrammeFragment  extends Fragment {
 
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	 
    	 
        // Get the view from fragmenttab1.xml
        View view = inflater.inflate(R.layout.programme_layout, container, false);
        return view;
    }
 
}