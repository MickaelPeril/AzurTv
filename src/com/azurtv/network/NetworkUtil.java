package com.azurtv.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Ludovic ROLAND
 * @since 2013.08.20
 */
public class NetworkUtil
{
  public static boolean isOnline(final Context context)
  {
    final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    final NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();

    if (null != netInfo && netInfo.isConnectedOrConnecting())
    {
      return true;
    }

    return false;
  }
}
