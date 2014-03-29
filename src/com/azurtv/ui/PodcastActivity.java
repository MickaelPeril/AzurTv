package com.azurtv.ui;

import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.azurtv.R;
import com.azurtv.network.NetworkUtil;

public class PodcastActivity
    extends Activity
    implements OnClickListener, OnPreparedListener, OnCompletionListener, OnErrorListener
{
  private final Pattern videoPattern = Pattern.compile("<video(.*?)</video>", Pattern.DOTALL);

  private final Pattern videoSrcPattern = Pattern.compile("src=\"(.*?)\"", Pattern.DOTALL);

  private TextView titleTextView;

  private WebView contentWebView;

  private String title;

  private String description;

  private String videoUrl;

  private Display display;

  private Handler handler;

  private Runnable runnable;

  private SurfaceView video;

  private SurfaceHolder holder;

  private MediaPlayer player;

  private ImageButton play;

  private ImageButton fullscreen;

  private TextView currentPosition;

  private TextView duration;

  private SeekBar seekBar;

  @SuppressWarnings("deprecation")
  @SuppressLint("SetJavaScriptEnabled")
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    // on set la vue a afficher
    setContentView(R.layout.activity_podcast);

    display = getWindowManager().getDefaultDisplay();

    handler = new Handler();
    runnable = new Runnable()
    {
      @Override
      public void run()
      {
        currentPosition.setText(computeCurrentPosition());
        seekBar.setProgress(player.getCurrentPosition() / 1000);
        startVideo();
      }
    };

    // on recupere les elements graphiques
    titleTextView = (TextView) findViewById(R.id.podcastTitle);

    contentWebView = (WebView) findViewById(R.id.contentWebView);
    contentWebView.setVerticalScrollBarEnabled(false);
    contentWebView.getSettings().setJavaScriptEnabled(true);

    final int videoHeight = (260 * display.getWidth()) / 460;

    video = (SurfaceView) findViewById(R.id.video);
    video.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, videoHeight));
    holder = video.getHolder();

    currentPosition = (TextView) findViewById(R.id.currentPosition);
    duration = (TextView) findViewById(R.id.duration);

    seekBar = (SeekBar) findViewById(R.id.seekBar);
    seekBar.setEnabled(false);

    play = (ImageButton) findViewById(R.id.play);
    play.setOnClickListener(this);

    fullscreen = (ImageButton) findViewById(R.id.fullscreen);
    fullscreen.setOnClickListener(this);

    fillWithData();
  }

  @Override
  public void onResume()
  {
    super.onResume();
    initVideo();
  }

  @Override
  public void onPause()
  {
    super.onPause();
    pauseVideo();
    player.setOnPreparedListener(null);
    player.setOnErrorListener(null);
    player.setOnCompletionListener(null);
    player.reset();
  }

  @Override
  public void onDestroy()
  {
    super.onDestroy();
    player.release();
  }

  @Override
  public void onPrepared(final MediaPlayer mp)
  {
    player.setDisplay(holder);
    updateVideoInformation();
    // player.start();
    // startVideo();
    // updatePlay();

    play.setEnabled(true);
  }

  @Override
  public void onCompletion(final MediaPlayer mp)
  {
    pauseVideo();
    seekBar.setProgress(0);
    currentPosition.setText(getString(R.string.default_time));
    player.seekTo(0);
  }

  @Override
  public boolean onError(final MediaPlayer mp, final int what, final int extra)
  {
    player.setOnPreparedListener(null);
    player.setOnErrorListener(null);
    player.setOnCompletionListener(null);

    if (NetworkUtil.isOnline(this))
    {
      Toast.makeText(this, getString(R.string.error_video), Toast.LENGTH_LONG).show();
    }
    else
    {
      Toast.makeText(this, getString(R.string.error_network_connexion), Toast.LENGTH_LONG).show();
    }

    return false;
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
    if (view.equals(fullscreen))
    {
      final Intent intent = new Intent(this, VideoPlayerActivity.class);
      intent.putExtra(HTML5WebView.VIDEO_URL, videoUrl);
      startActivity(intent);
    }
    else if (view.equals(play))
    {
      if (getString(R.string.play).equals(play.getTag()))
      {
        player.start();
        startVideo();
        updatePlay();
      }
      else
      {
        pauseVideo();
      }
    }
  }

  private void initVideo()
  {
    player = new MediaPlayer();
    play.setEnabled(false);
    play.setTag(getString(R.string.play));
    play.setImageResource(R.drawable.ic_play_pressed);

    try
    {
      player.setDataSource(videoUrl);
    }
    catch (final Exception e)
    {
      Toast.makeText(this, getString(R.string.error_video), Toast.LENGTH_LONG).show();
    }

    player.setOnPreparedListener(this);
    player.setOnCompletionListener(this);
    player.setOnErrorListener(this);
    player.prepareAsync();
  }

  private void updateVideoInformation()
  {
    duration.setText(computeDuration());
    seekBar.setMax(player.getDuration() / 1000);
  }

  private void updatePlay()
  {
    if (getString(R.string.play).equals(play.getTag()))
    {
      play.setTag(getString(R.string.pause));
      play.setImageResource(R.drawable.ic_pause_pressed);
    }
    else
    {
      play.setTag(getString(R.string.play));
      play.setImageResource(R.drawable.ic_play_pressed);
    }
  }

  private String computeCurrentPosition()
  {
    final int currentPosition = player.getCurrentPosition();
    return String.format(Locale.getDefault(), "%d:%02d", TimeUnit.MILLISECONDS.toMinutes(currentPosition),
        TimeUnit.MILLISECONDS.toSeconds(currentPosition) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentPosition)));
  }

  private String computeDuration()
  {
    final int duration = player.getDuration();
    return String.format(Locale.getDefault(), "%d:%02d", TimeUnit.MILLISECONDS.toMinutes(duration),
        TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
  }

  private void startVideo()
  {
    handler.postDelayed(runnable, 500);
  }

  private void pauseVideo()
  {
    player.pause();
    updatePlay();
    handler.removeCallbacks(runnable);
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
