package com.welcometo.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesHelper
{
  private static SharedPreferencesHelper sharedPreferencesHelper = null;
  private Context context;
  
  public static SharedPreferencesHelper getInstance(Context paramContext)
  {
    if (sharedPreferencesHelper == null) {}
    try
    {
      if (sharedPreferencesHelper == null)
      {
        sharedPreferencesHelper = new SharedPreferencesHelper();
        sharedPreferencesHelper.setContext(paramContext);
        SharedPreferencesHelper localSharedPreferencesHelper = sharedPreferencesHelper;
        return localSharedPreferencesHelper;
      }
      return sharedPreferencesHelper;
    }
    finally {}
  }
  
  private SharedPreferences getSP()
  {
    return this.context.getSharedPreferences("sp", 0);
  }
  
  public void clear()
  {
    try
    {
      SharedPreferences.Editor localEditor = getSP().edit();
      localEditor.clear();
      localEditor.commit();
      return;
    }
    catch (NullPointerException localNullPointerException) {}
  }
  
  public boolean getBoolean(String paramString, boolean paramBoolean)
  {
    try
    {
      boolean bool = getSP().getBoolean(paramString, paramBoolean);
      return bool;
    }
    catch (NullPointerException localNullPointerException) {}
    return paramBoolean;
  }
  
  public int getInt(String paramString, int paramInt)
  {
    try
    {
      int i = getSP().getInt(paramString, paramInt);
      return i;
    }
    catch (Exception localException) {}
    return paramInt;
  }
  
  public long getLong(String paramString, long paramLong)
  {
    try
    {
      long l = getSP().getLong(paramString, paramLong);
      return l;
    }
    catch (NullPointerException localNullPointerException) {}
    return paramLong;
  }

  public String getString(String key) {
    return getString(key, null);
  }

  public String getString(String key, String defValue) {
    try {
      String str = getSP().getString(key, defValue);
      return str;
    }
    catch (NullPointerException localNullPointerException) {}

    return defValue;
  }
  
  public void putBoolean(String paramString, boolean paramBoolean)
  {
    try
    {
      SharedPreferences.Editor localEditor = getSP().edit();
      localEditor.putBoolean(paramString, paramBoolean);
      localEditor.commit();
      return;
    }
    catch (NullPointerException localNullPointerException) {}
  }
  
  public void putInt(String paramString, int paramInt)
  {
    try
    {
      SharedPreferences.Editor localEditor = getSP().edit();
      localEditor.putInt(paramString, paramInt);
      localEditor.commit();
      return;
    }
    catch (Exception localException) {}
  }
  
  public void putLong(String paramString, long paramLong)
  {
    try
    {
      SharedPreferences.Editor localEditor = getSP().edit();
      localEditor.putLong(paramString, paramLong);
      localEditor.commit();
      return;
    }
    catch (NullPointerException localNullPointerException) {}
  }
  
  public void putString(String paramString1, String paramString2)
  {
    try
    {
      SharedPreferences.Editor localEditor = getSP().edit();
      localEditor.putString(paramString1, paramString2);
      localEditor.commit();
      return;
    }
    catch (NullPointerException localNullPointerException) {}
  }
  
  public void setContext(Context paramContext)
  {
    this.context = paramContext;
  }
}
