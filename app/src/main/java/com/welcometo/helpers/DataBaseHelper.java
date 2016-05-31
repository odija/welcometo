package com.welcometo.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class DataBaseHelper extends SQLiteOpenHelper {
  private static String DB_NAME = "welcometo";
  private static String DB_PATH = "/data/data/com.welcometo/databases/";

  private static String TABLE_COUNTRY = "country";
  private static String TABLE_COUNTRY_CURRENCY = "country_currency";
  private static String TABLE_CURRENCY_RATE = "currency_rate";

  private static String FIELD_CURRENCY = "currency";
  private static String FIELD_COUNTRY = "country";
  private static String FIELD_RATE = "rate";

  private static DataBaseHelper mDBHelper;

  private final Context myContext;
  private SQLiteDatabase mDataBase;

  public static DataBaseHelper getInstance(Context context) {
    if (mDBHelper == null) {
      synchronized (DataBaseHelper.class) {
        if (mDBHelper == null) {
          mDBHelper = new DataBaseHelper(context);
          return mDBHelper;
        }
      }
    }
    return mDBHelper;
  }

  public DataBaseHelper(Context paramContext) {
    super(paramContext, DB_NAME, null, 1);
    this.myContext = paramContext;
  }

  public void open() {

      try {
        openDataBase();
      } catch (SQLException e) {
        try {
          createDataBase();
          openDataBase();
        }
        catch (SQLException e1) {

        }
        catch (IOException e2) {
            LogHelper.e("Cant create DB " + e2.getMessage());
        }
      }
  }

  public void close() {
    try {
      if (this.mDataBase != null) {
        this.mDataBase.close();
      }
      super.close();
    }
    finally {}
  }

  public ArrayList<Country> getCountries() {
    ArrayList<Country> localArrayList = new ArrayList<Country>();
    String str = "SELECT  * FROM " + TABLE_COUNTRY;
    Cursor localCursor = getWritableDatabase().rawQuery(str, null);
    if (localCursor.moveToFirst()) {
      do {
        localArrayList.add(createCountryFromCursor(localCursor));
      } while (localCursor.moveToNext());
    }
    return localArrayList;
  }

  public Country getCountryByCode(String paramString) {
    String str = "SELECT  * FROM " + TABLE_COUNTRY + " WHERE id = '" + paramString + "'";
    Cursor localCursor = getWritableDatabase().rawQuery(str, null);
    boolean bool = localCursor.moveToFirst();
    Country localCountry = null;
    if (bool) {
      localCountry = createCountryFromCursor(localCursor);
    }
    return localCountry;
  }

  public String getCurrencyByCountry(String paramString) {
    String str1 = "SELECT  currency FROM " + TABLE_COUNTRY_CURRENCY + " WHERE country = '" + paramString + "'";
    Cursor localCursor = getWritableDatabase().rawQuery(str1, null);
    boolean bool = localCursor.moveToFirst();
    String str2 = null;
    if (bool) {
      str2 = localCursor.getString(0);
    }
    return str2;
  }

  public void initCurrencyRates(JSONObject rates) {

    if (rates != null) {
      // delete all rates
      getWritableDatabase().delete(TABLE_CURRENCY_RATE, null, null);

      // init new rates
      ContentValues values = new ContentValues();
      Iterator<String> iter = rates.keys();
      while (iter.hasNext()) {

        String currency = iter.next();

        try {
          String rate = rates.get(currency).toString();
          values.put(FIELD_CURRENCY, currency);
          values.put(FIELD_RATE, rate);
        } catch (JSONException e) {
          // Something went wrong!
        }

        getWritableDatabase().insert(TABLE_CURRENCY_RATE, FIELD_COUNTRY, values);
      }
    }
  }

  public double getCurrencyRate(String currency) {
    String query = "SELECT  " + FIELD_RATE + " FROM " + TABLE_CURRENCY_RATE + " WHERE " + FIELD_CURRENCY + " = '" + currency + "'";
    Cursor localCursor = getWritableDatabase().rawQuery(query, null);
    boolean bool = localCursor.moveToFirst();

    if (bool) {
      return localCursor.getDouble(0);
    }

    return 0;
  }

  public void onCreate(SQLiteDatabase paramSQLiteDatabase) {}

  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {}

  private void copyDataBase() throws IOException {

    File databaseFolder = new File(DB_PATH);

    // check if databases folder exists, if not create one and its subfolders
    if (!databaseFolder.exists()){
      databaseFolder.mkdir();
    }


    LogHelper.d("1");
    InputStream localInputStream = this.myContext.getAssets().open(DB_NAME);
    LogHelper.d("2 " + DB_PATH + "," + DB_NAME);
    FileOutputStream localFileOutputStream = new FileOutputStream(DB_PATH + DB_NAME);
    LogHelper.d("3");

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

  private void openDataBase() throws SQLException {
    if (this.mDataBase == null) {
      this.mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, 1);
    }
  }

  private void createDataBase() throws IOException {

    try {
      copyDataBase();
    }
    catch (IOException localIOException) {
        throw new Error("Error copying database: " + localIOException.getMessage());
    }
  }

  private Country createCountryFromCursor(Cursor paramCursor) {
    String str1 = paramCursor.getString(0);
    String str2 = paramCursor.getString(1);
    Integer localInteger = paramCursor.getInt(2);
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

}