package com.welcometo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.welcometo.R;
import com.welcometo.helpers.Country;
import com.welcometo.helpers.DataBaseHelper;
import com.welcometo.helpers.SharedPreferencesHelper;

import java.io.IOException;
import java.util.ArrayList;

public class InitScreen
  extends Activity
{
  private ArrayList<Country> mCountries;
  private DataBaseHelper mDBHelper;
  
  private DataBaseHelper connectToDB()
  {
	DataBaseHelper localDataBaseHelper = new DataBaseHelper(this);
	
	if (localDataBaseHelper != null) {
		try {
			localDataBaseHelper.createDataBase();
		} catch(IOException e) {}
		
		localDataBaseHelper.openDataBase();
	}
	
	return localDataBaseHelper;
  }
  
  private void startMainActivity()
  {
    startActivity(new Intent(this, MainScreen.class));
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.init_screen);
    this.mDBHelper = connectToDB();
    Country localCountry = new Country();
    localCountry.setName("Where are you from?");
    localCountry.setCode("0");
    this.mCountries = new ArrayList();
    this.mCountries.add(localCountry);
    this.mCountries.addAll(this.mDBHelper.getCountries());
    CountryAdapterItem localCountryAdapterItem = new CountryAdapterItem(this, R.layout.country_item, this.mCountries);
    localCountryAdapterItem.setDropDownViewResource(R.layout.country_list_item);
    Spinner localSpinner = (Spinner)findViewById(2131296265);
    localSpinner.setAdapter(localCountryAdapterItem);
    localSpinner.setOnItemSelectedListener(new mySpinnerListener());
    if (SharedPreferencesHelper.getInstance(this).getString("occ", null) != null) {
      startMainActivity();
    }
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(R.menu.init_screen, paramMenu);
    return true;
  }
  
  protected void onStop()
  {
    super.onStop();
    if (this.mDBHelper != null) {
      this.mDBHelper.close();
    }
  }
  
  class mySpinnerListener
    implements AdapterView.OnItemSelectedListener
  {
    mySpinnerListener() {}
    
    public void onItemSelected(AdapterView paramAdapterView, View paramView, int paramInt, long paramLong)
    {
      if (paramInt > 0)
      {
        Log.d("", ((Country)InitScreen.this.mCountries.get(paramInt)).getCode());
        SharedPreferencesHelper.getInstance(InitScreen.this).putString("occ", ((Country)InitScreen.this.mCountries.get(paramInt)).getCode());
        InitScreen.this.startMainActivity();
      }
    }
    
    public void onNothingSelected(AdapterView paramAdapterView) {}
  }
}