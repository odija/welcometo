package com.welcometo.helpers;

import android.content.Context;

public class CountryHelper
{
  public static String getSavedNativeCountry(Context paramContext)
  {
    return SharedPreferencesHelper.getInstance(paramContext).getString("occ", "");
  }
  
  public static void saveNativeCountry(Context paramContext, String paramString)
  {
    SharedPreferencesHelper.getInstance(paramContext).putString("occ", paramString);
  }
}


/* Location:           D:\projects\decompilation\dex2jar-0.0.9.15\WelcomeTo_dex2jar.jar
 * Qualified Name:     com.welcometo.helpers.CountryHelper
 * JD-Core Version:    0.7.0.1
 */