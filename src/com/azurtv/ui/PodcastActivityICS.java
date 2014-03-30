package com.azurtv.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.azurtv.R;

public class PodcastActivityICS
    extends Activity
    implements OnClickListener
{
  private final Pattern videoPattern = Pattern.compile("<video(.*?)</video>", Pattern.DOTALL);

  private final Pattern videoSrcPattern = Pattern.compile("src=\"(.*?)\"", Pattern.DOTALL);

  private TextView titleTextView;

  private WebView contentWebView;

  private String title;

  private String description;

  private String videoUrl;

  private Button video;

  @SuppressLint("SetJavaScriptEnabled")
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    // on set la vue a afficher
    setContentView(R.layout.activity_podcast_ics);

    // on recupere les elements graphiques
    titleTextView = (TextView) findViewById(R.id.podcastTitle);

    contentWebView = (WebView) findViewById(R.id.contentWebView);
    contentWebView.setVerticalScrollBarEnabled(false);
    contentWebView.getSettings().setJavaScriptEnabled(true);

    video = (Button) findViewById(R.id.video);
    video.setEnabled(false);
    video.setOnClickListener(this);

    fillWithData();
  }

  private void fillWithData()
  {

    Intent intent = getIntent();

    if (intent != null)
    {
      // on recupere les donnees stoques lors de l'appel
      title = intent.getStringExtra("podcastTitle");
      description = intent.getStringExtra("podcastDescription");

      removeVideoFromDescription();

      titleTextView.setText(title);
      contentWebView.loadData(description, "text/html; charset=UTF-8", null);
      video.setEnabled(true);
    }
  }

  // cette methode est appele lorque la configuration de l'ecran change (par exemple quand l'ecran est tourne)
  // quand ca arrive, on re-appelle fillWithData pour recalculer la taille de la video et l'afficher correctement
  @Override
  public void onConfigurationChanged(Configuration newConfig)
  {
    super.onConfigurationChanged(newConfig);
    fillWithData();
  }

  @Override
  public void onClick(View view)
  {
    if (view.equals(video))
    {
      final Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setDataAndType(Uri.parse(videoUrl), "video/mp4");
      startActivity(intent);
    }
  }

  private void removeVideoFromDescription()
  {
    final Matcher videoMatcher = videoPattern.matcher(description);

    if (videoMatcher.find())
    {
      final String videoBalise = videoMatcher.group();
      description = description.replace(videoBalise, "");

      final Matcher videoUrlMatcher = videoSrcPattern.matcher(videoBalise);

      if (videoUrlMatcher.find())
      {
        videoUrl = videoUrlMatcher.group();
        videoUrl = videoUrl.replace("src=", "");
        videoUrl = videoUrl.replaceAll("\"", "");
      }
    }
  }
}
