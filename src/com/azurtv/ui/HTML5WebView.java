package com.azurtv.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.azurtv.R;

/**
 * A component which is supposed to support the HTML5 video player.
 * 
 * @author Édouard Mercier
 * @since 2012.12.01
 */
// Taken from http://code.google.com/p/html5webview/source/browse/trunk/HTML5WebView/src/org/itri/html5webview/HTML5WebView.java
@SuppressLint("ViewConstructor")
public final class HTML5WebView
    extends WebView
    implements OnClickListener
{

  @SuppressLint("NewApi")
  private final class HTML5WebChromeClient
      extends WebChromeClient
  {

    private Bitmap videoPoster;

    private View loadingView;

    @SuppressLint("NewApi")
    @Override
    public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback)
    {
      super.onShowCustomView(view, callback);
      onShowCustomViewInternal(view, callback);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    @Override
    public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback)
    {
      super.onShowCustomView(view, requestedOrientation, callback);
      onShowCustomViewInternal(view, callback);
    }

    @SuppressLint("NewApi")
    private void onShowCustomViewInternal(View view, WebChromeClient.CustomViewCallback callback)
    {
      HTML5WebView.this.setVisibility(View.GONE);

      // If a View already exists then immediately terminate the new one
      if (customView != null)
      {
        callback.onCustomViewHidden();
        return;
      }

      customContentView.addView(view);
      customView = view;
      customViewCallback = callback;
      customContentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideCustomView()
    {
      if (customView == null)
      {
        return;
      }

      // We hide the custom view
      customView.setVisibility(View.GONE);

      // We remove the custom view from its container
      customContentView.removeView(customView);
      customView = null;
      customContentView.setVisibility(View.GONE);
      customViewCallback.onCustomViewHidden();

      HTML5WebView.this.setVisibility(View.VISIBLE);
    }

    @SuppressLint("NewApi")
    @Override
    public Bitmap getDefaultVideoPoster()
    {
      if (videoPosterDrawableResourceId < 0)
      {
        return super.getDefaultVideoPoster();
      }
      if (videoPoster == null)
      {
        videoPoster = BitmapFactory.decodeResource(getResources(), videoPosterDrawableResourceId);
      }
      return videoPoster;
    }

    @SuppressLint("NewApi")
    @Override
    public View getVideoLoadingProgressView()
    {
      if (loadingLayoutResourceId < 0)
      {
        return super.getVideoLoadingProgressView();
      }
      if (loadingView == null)
      {
        loadingView = activity.getLayoutInflater().inflate(loadingLayoutResourceId, null);
      }
      return loadingView;
    }

    @Override
    public void onReceivedTitle(WebView view, String title)
    {
      super.onReceivedTitle(view, title);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress)
    {
      super.onProgressChanged(view, newProgress);
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback)
    {
      callback.invoke(origin, true, false);
    }
  }

  private class HTML5WebViewClient
      extends WebViewClient
  {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url)
    {
      // view.loadUrl(url);
      // return true;
      return super.shouldOverrideUrlLoading(view, url);
    }

  }

  public static final String VIDEO_URL = "videoUrl";

  public static final String VIDEO_URL_EXTRA = "http://www.dailymotion.com/embed/video/x13x1q2?autoPlay=1";

  private final int videoPosterDrawableResourceId;

  private final int loadingLayoutResourceId;

  private final Activity activity;

  private final FrameLayout customContentView;

  private final FrameLayout mainContentView;

  private final FrameLayout browserFrameLayout;

  private final FrameLayout layout;

  private final ImageButton fullscreen;

  private final HTML5WebChromeClient webChromeClient;

  private WebChromeClient.CustomViewCallback customViewCallback;

  private View customView;

  @SuppressLint({ "NewApi", "SetJavaScriptEnabled" })
  public HTML5WebView(Activity activity, int customLayoutResourceId, int mainContentId, int customContentId, int loadingLayoutResourceId,
      int videoPosterDrawableResourceId, boolean isFullscreen)
  {
    super(activity);
    this.activity = activity;
    this.loadingLayoutResourceId = loadingLayoutResourceId;
    this.videoPosterDrawableResourceId = videoPosterDrawableResourceId;

    layout = new FrameLayout(activity);

    browserFrameLayout = (FrameLayout) activity.getLayoutInflater().inflate(customLayoutResourceId, null);
    mainContentView = (FrameLayout) browserFrameLayout.findViewById(mainContentId);
    customContentView = (FrameLayout) browserFrameLayout.findViewById(customContentId);
    fullscreen = (ImageButton) browserFrameLayout.findViewById(R.id.fullscreen);

    if (isFullscreen == true)
    {
      fullscreen.setVisibility(View.INVISIBLE);
    }
    else
    {
      fullscreen.setVisibility(View.VISIBLE);
      fullscreen.setOnClickListener(this);
    }

    layout.addView(browserFrameLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    // We set the Chrome client
    webChromeClient = new HTML5WebChromeClient();
    setWebChromeClient(webChromeClient);

    // We set the WebView client
    setWebViewClient(new HTML5WebViewClient());

    // We configure the WebView
    final WebSettings settings = getSettings();
    settings.setBuiltInZoomControls(true);
    settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
    settings.setUseWideViewPort(true);
    settings.setLoadWithOverviewMode(true);
    settings.setJavaScriptEnabled(true);

    setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

    mainContentView.addView(this);
  }

  // Just here for visibility issues before Android release 11
  @SuppressLint("NewApi")
  @Override
  public void onResume()
  {
    super.onResume();
  }

  // Just here for visibility issues before Android release 11
  @SuppressLint("NewApi")
  @Override
  public void onPause()
  {
    super.onPause();
  }

  public FrameLayout getLayout()
  {
    return layout;
  }

  public boolean isInCustomView()
  {
    return (customView != null);
  }

  @SuppressLint("NewApi")
  public void hideCustomView()
  {
    webChromeClient.onHideCustomView();
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event)
  {
    if (keyCode == KeyEvent.KEYCODE_BACK)
    {
      if ((customView == null) && canGoBack())
      {
        goBack();
        return true;
      }
    }
    return super.onKeyDown(keyCode, event);
  }

  @Override
  public void onClick(View view)
  {
    if (view.equals(fullscreen) == true)
    {
      final Intent intent = new Intent(activity, WebTvActivity.class);
      activity.startActivity(intent);
    }
  }
}