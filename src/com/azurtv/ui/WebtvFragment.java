package com.azurtv.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;


import com.azurtv.R;

public class WebtvFragment extends Fragment {
 
	WebView webView;
	String videoHtml;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        // Get the view from fragmenttab1.xml
        View view = inflater.inflate(R.layout.webtv_layout, container, false);
        
        //ajout de la progressbar
        
        // lecture de la webTV
        
        webView = (WebView) view.findViewById(R.id.WebView1); 
  	  videoHtml = "<iframe  frameborder='0' height='100%' src='http://www.dailymotion.com/embed/video/x13x1q2?autoPlay=1' allowfullscreen width='100%'/>"; 
  	  webView.loadData(videoHtml, "text/html", "UTF-8"); 
  	  webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN); 
  	  webView.getSettings().setJavaScriptEnabled(true);
        
  	
  	  
        return view;
    }
 
}