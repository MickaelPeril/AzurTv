package com.azurtv.ui;

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
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.azurtv.R;
import com.azurtv.network.NetworkUtil;

public class VideoPlayerActivity
    extends Activity
    implements OnPreparedListener, OnCompletionListener, OnErrorListener, MediaPlayerControl, OnTouchListener
{
  private String videoUrl;

  private Display display;

  private Handler handler;

  private Runnable runnable;

  private SurfaceView video;

  private SurfaceHolder holder;

  private MediaPlayer player;

  private MediaController controller;

  @SuppressWarnings("deprecation")
  @SuppressLint("SetJavaScriptEnabled")
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    // on set la vue a afficher
    setContentView(R.layout.video_player);

    display = getWindowManager().getDefaultDisplay();

    handler = new Handler();
    runnable = new Runnable()
    {
      @Override
      public void run()
      {
        startVideo();
      }
    };

    final int videoHeight = (260 * display.getWidth()) / 460;

    video = (SurfaceView) findViewById(R.id.video);
    video.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, videoHeight));
    holder = video.getHolder();

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
    video.setOnTouchListener(this);

    controller.setMediaPlayer(this);
    controller.setAnchorView(video);
    controller.setEnabled(true);
    controller.show();

    player.setDisplay(holder);
    player.start();
    startVideo();
  }

  @Override
  public void onCompletion(final MediaPlayer mp)
  {
    pauseVideo();
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
      videoUrl = intent.getStringExtra(HTML5WebView.VIDEO_URL);
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

  private void initVideo()
  {
    player = new MediaPlayer();
    controller = new MediaController(this);

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

  private void startVideo()
  {
    handler.postDelayed(runnable, 500);
  }

  private void pauseVideo()
  {
    player.pause();
    handler.removeCallbacks(runnable);
  }

  @Override
  public boolean canPause()
  {
    return true;
  }

  @Override
  public boolean canSeekBackward()
  {
    return true;
  }

  @Override
  public boolean canSeekForward()
  {
    return true;
  }

  @Override
  public int getAudioSessionId()
  {
    return 0;
  }

  @Override
  public int getBufferPercentage()
  {
    return 0;
  }

  @Override
  public int getCurrentPosition()
  {
    return player.getCurrentPosition();
  }

  @Override
  public int getDuration()
  {
    return player.getDuration();
  }

  @Override
  public boolean isPlaying()
  {
    return player.isPlaying();
  }

  @Override
  public void pause()
  {
    player.pause();
  }

  @Override
  public void seekTo(int pos)
  {
    player.seekTo(pos);
  }

  @Override
  public void start()
  {
    player.start();
  }

  @Override
  public boolean onTouch(View view, MotionEvent event)
  {
    controller.show();
    return false;
  }
}
