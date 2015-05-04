package com.welcometo.helpers;

import android.content.Context;

public class CountryHelper  {
  /*
  Return own county code
   */
  public static String getSavedNativeCountry(Context paramContext) {
    return SharedPreferencesHelper.getInstance(paramContext).getString(Constants.OWN_COUNTRY_CODE, "");
  }
  
  public static void saveNativeCountry(Context paramContext, String paramString) {
    SharedPreferencesHelper.getInstance(paramContext).putString(Constants.OWN_COUNTRY_CODE, paramString);
  }
}