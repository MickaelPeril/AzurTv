package com.azurtv.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager.WakeLock;
import android.widget.TextView;

import com.azurtv.R;
import com.azurtv.network.NetworkUtil;

public class WebTvActivity
    extends Activity
{

  private HTML5WebView html5WebView;

  private WakeLock wakeLock;

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    if (NetworkUtil.isOnline(this))
    {
      setVolumeControlStream(AudioManager.STREAM_MUSIC);

      html5WebView = new HTML5WebView(this, R.layout.video_player_html5, R.id.mainContent, R.id.customContent, -1, -1, true);
      html5WebView.loadUrl(HTML5WebView.VIDEO_URL_EXTRA);

      setContentView(html5WebView.getLayout());
    }
    else
    {
      setContentView(R.layout.webtv_layout);
      final TextView error = (TextView) findViewById(R.id.error);
      error.setText(R.string.webTv_error_network);
    }
  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  @Override
  public void onResume()
  {
    super.onResume();
    if (html5WebView != null)
    {
      html5WebView.onResume();
    }
  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  @Override
  public void onPause()
  {
    try
    {
      if (html5WebView != null)
      {
        html5WebView.onPause();
      }
    }
    finally
    {
      super.onPause();
    }
  }

  @Override
  public void onStop()
  {
    try
    {
      if (wakeLock != null)
      {
        wakeLock.release();
      }

      if (html5WebView != null)
      {
        html5WebView.stopLoading();
      }
    }
    finally
    {
      super.onStop();
    }
  }

  @Override
  public void onDestroy()
  {
    try
    {
      if (html5WebView != null)
      {
        html5WebView.destroy();
      }
    }
    finally
    {
      super.onDestroy();
    }
  }

}