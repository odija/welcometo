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
import com.welcometo.helpers.Constants;
import com.welcometo.helpers.Country;
import com.welcometo.helpers.DataBaseHelper;
import com.welcometo.helpers.SharedPreferencesHelper;

import java.util.ArrayList;

public class InitScreen extends Activity {

  private ArrayList<Country> mCountries;
  private DataBaseHelper mDBHelper;

  @Override
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);

    setContentView(R.layout.init_screen);

    this.mDBHelper = connectToDB();

    Country localCountry = new Country();
    localCountry.setName(getResources().getString(R.string.where_are_you_from));
    localCountry.setCode("0");
    this.mCountries = new ArrayList<Country>();
    this.mCountries.add(localCountry);
    this.mCountries.addAll(this.mDBHelper.getCountries());

    CountryAdapterItem localCountryAdapterItem = new CountryAdapterItem(this, R.layout.country_item, this.mCountries);
    localCountryAdapterItem.setDropDownViewResource(R.layout.country_list_item);
    Spinner localSpinner = (Spinner)findViewById(R.id.countryList);
    localSpinner.setAdapter(localCountryAdapterItem);
    localSpinner.setOnItemSelectedListener(new mySpinnerListener());
    if (SharedPreferencesHelper.getInstance(this).getString(Constants.OWN_COUNTRY_CODE, null) != null) {
      startMainActivity();
    }
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu) {
    getMenuInflater().inflate(R.menu.init_screen, paramMenu);
    return true;
  }
  
  protected void onStop() {
    super.onStop();
    if (this.mDBHelper != null) {
      this.mDBHelper.close();
    }
  }

  private DataBaseHelper connectToDB()  {
    DataBaseHelper localDataBaseHelper = DataBaseHelper.getInstance(this);

    localDataBaseHelper.open();

    return localDataBaseHelper;
  }

  private void startMainActivity()
  {
    startActivity(new Intent(this, MainScreen.class));
  }

  class mySpinnerListener implements AdapterView.OnItemSelectedListener {
    mySpinnerListener() {}
    
    public void onItemSelected(AdapterView paramAdapterView, View paramView, int paramInt, long paramLong) {
      if (paramInt > 0) {
        Log.d("", (InitScreen.this.mCountries.get(paramInt)).getCode());
        SharedPreferencesHelper.getInstance(InitScreen.this).putString(Constants.OWN_COUNTRY_CODE, (InitScreen.this.mCountries.get(paramInt)).getCode());
        InitScreen.this.startMainActivity();
      }
    }
    
    public void onNothingSelected(AdapterView paramAdapterView) {}
  }
}