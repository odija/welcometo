package com.welcometo.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionHelper
{
  private static ConnectivityManager getConnectivityManager(Context paramContext)
  {
    return (ConnectivityManager)paramContext.getSystemService("connectivity");
  }
  
  public static boolean isConnected(Context paramContext)
  {
    ConnectivityManager localConnectivityManager = getConnectivityManager(paramContext);
    return (localConnectivityManager.getActiveNetworkInfo() != null) && (localConnectivityManager.getActiveNetworkInfo().isAvailable()) && (localConnectivityManager.getActiveNetworkInfo().isConnected());
  }
  
  public static boolean isWifiConnected(Context paramContext)
  {
    NetworkInfo localNetworkInfo = getConnectivityManager(paramContext).getNetworkInfo(1);
    return (localNetworkInfo != null) && (localNetworkInfo.isAvailable()) && (localNetworkInfo.isConnected());
  }
}


/* Location:           D:\projects\decompilation\dex2jar-0.0.9.15\WelcomeTo_dex2jar.jar
 * Qualified Name:     com.welcometo.helpers.ConnectionHelper
 * JD-Core Version:    0.7.0.1
 */