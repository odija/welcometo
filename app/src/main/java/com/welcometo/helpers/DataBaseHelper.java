package com.welcometo.helpers;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
  private static String DB_NAME = "welcometo";
  private static String DB_PATH = "/data/data/com.welcometo/databases/";
  private static String TABLE_COUNTRY = "country";
  private static String TABLE_COUNTRY_CURRENCY = "country_currency";
  private final Context myContext;
  private SQLiteDatabase myDataBase;
  
  public DataBaseHelper(Context paramContext) {
    super(paramContext, DB_NAME, null, 1);
    this.myContext = paramContext;
  }
  
  private boolean checkDataBase() {
    try {
      SQLiteDatabase localSQLiteDatabase2 = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, 1);
      myDataBase = localSQLiteDatabase2;
    }
    catch (SQLiteException localSQLiteException)
    {
    }
    if (myDataBase != null) {
      myDataBase.close();
    }
    return myDataBase != null;
  }
  
  private void copyDataBase() throws IOException {
    InputStream localInputStream = this.myContext.getAssets().open(DB_NAME);
    FileOutputStream localFileOutputStream = new FileOutputStream(DB_PATH + DB_NAME);
    byte[] arrayOfByte = new byte[1024];
    for (;;)
    {
      int i = localInputStream.read(arrayOfByte);
      if (i <= 0)
      {
        localFileOutputStream.flush();
        localFileOutputStream.close();
        localInputStream.close();
        return;
      }
      localFileOutputStream.write(arrayOfByte, 0, i);
    }
  }
  
  private Country createCountryFromCursor(Cursor paramCursor)
  {
    String str1 = paramCursor.getString(0);
    String str2 = paramCursor.getString(1);
    Integer localInteger = Integer.valueOf(paramCursor.getInt(2));
    String str3 = paramCursor.getString(3);
    Log.d("Country CODE:", str1 + "\n");
    Log.d("Country NAME:", str2 + "\n");
    Log.d("Country PHONE CODE:", localInteger + "\n");
    Log.d("Country LANGUAGE:", str3 + "\n");
    Country localCountry = new Country();
    localCountry.setCode(str1);
    localCountry.setName(str2);
    localCountry.setPhoneCode(localInteger);
    localCountry.setLanguage(str3);
    return localCountry;
  }
  
  public void close()
  {
    try
    {
      if (this.myDataBase != null) {
        this.myDataBase.close();
      }
      super.close();
      return;
    }
    finally {}
  }
  
  public void createDataBase()
    throws IOException
  {
    if (!checkDataBase()) {
      getReadableDatabase();
    }
    try
    {
      copyDataBase();
      return;
    }
    catch (IOException localIOException)
    {
      throw new Error("Error copying database");
    }
  }
  
  public ArrayList<Country> getCountries()
  {
    ArrayList localArrayList = new ArrayList();
    String str = "SELECT  * FROM " + TABLE_COUNTRY;
    Cursor localCursor = getWritableDatabase().rawQuery(str, null);
    if (localCursor.moveToFirst()) {
      do
      {
        localArrayList.add(createCountryFromCursor(localCursor));
      } while (localCursor.moveToNext());
    }
    return localArrayList;
  }
  
  public Country getCountryByCode(String paramString)
  {
    String str = "SELECT  * FROM " + TABLE_COUNTRY + " WHERE id = '" + paramString + "'";
    Cursor localCursor = getWritableDatabase().rawQuery(str, null);
    boolean bool = localCursor.moveToFirst();
    Country localCountry = null;
    if (bool) {
      localCountry = createCountryFromCursor(localCursor);
    }
    return localCountry;
  }
  
  public String getCurrencyByCountry(String paramString)
  {
    String str1 = "SELECT  currency FROM " + TABLE_COUNTRY_CURRENCY + " WHERE country = '" + paramString + "'";
    Cursor localCursor = getWritableDatabase().rawQuery(str1, null);
    boolean bool = localCursor.moveToFirst();
    String str2 = null;
    if (bool) {
      str2 = localCursor.getString(0);
    }
    return str2;
  }
  
  public void onCreate(SQLiteDatabase paramSQLiteDatabase) {}
  
  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {}
  
  public void openDataBase()
    throws SQLException
  {
    this.myDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, 1);
  }
}