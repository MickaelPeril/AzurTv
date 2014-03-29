package com.azurtv.ui;

import android.annotation.TargetApi;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.azurtv.R;
import com.azurtv.network.NetworkUtil;

public class WebtvFragment
    extends Fragment
{

  private HTML5WebView html5WebView;

  private WakeLock wakeLock;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    if (Build.VERSION.SDK_INT >= 14)
    {
      if (NetworkUtil.isOnline(getActivity()))
      {
        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);

        html5WebView = new HTML5WebView(getActivity(), R.layout.video_player_html5, R.id.mainContent, R.id.customContent, -1, -1, false);
        html5WebView.loadUrl(HTML5WebView.VIDEO_URL_EXTRA);

        return html5WebView.getLayout();
      }
      else
      {
        final View view = inflater.inflate(R.layout.webtv_layout, container, false);
        final TextView error = (TextView) view.findViewById(R.id.error);
        error.setText(R.string.webTv_error_network);

        return view;
      }
    }

    final View view = inflater.inflate(R.layout.webtv_layout, container, false);
    final TextView error = (TextView) view.findViewById(R.id.error);
    error.setText(R.string.webTv_error);

    return view;
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