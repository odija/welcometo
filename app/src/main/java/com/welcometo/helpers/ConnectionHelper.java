package com.welcometo.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionHelper {
  private static ConnectivityManager getConnectivityManager(Context paramContext) {
    return (ConnectivityManager)paramContext.getSystemService(Context.CONNECTIVITY_SERVICE);
  }
  
  public static boolean isConnected(Context paramContext) {
    ConnectivityManager localConnectivityManager = getConnectivityManager(paramContext);
    return (localConnectivityManager.getActiveNetworkInfo() != null) && (localConnectivityManager.getActiveNetworkInfo().isAvailable()) && (localConnectivityManager.getActiveNetworkInfo().isConnected());
  }
  
  public static boolean isWifiConnected(Context paramContext) {
    NetworkInfo localNetworkInfo = getConnectivityManager(paramContext).getNetworkInfo(1);
    return (localNetworkInfo != null) && (localNetworkInfo.isAvailable()) && (localNetworkInfo.isConnected());
  }
}