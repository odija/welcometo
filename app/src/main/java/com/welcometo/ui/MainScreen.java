package com.welcometo.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.SQLException;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.welcometo.R;
import com.welcometo.helpers.Country;
import com.welcometo.helpers.CountryHelper;
import com.welcometo.helpers.DataBaseHelper;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainScreen extends Activity {
	
  public static String countryCode;
  private Country mCountry;
  private DataBaseHelper mDBHelper;
  private DrawerLayout mDrawerLayout;
  private ListView mDrawerList;
  private LocationManager mLocationManager;
  private String[] mMenuItems;
  
  /*public static Address getAddress(Context paramContext, double paramDouble1, double paramDouble2)
  {
    Geocoder localGeocoder = new Geocoder(paramContext, Locale.US);
    try
    {
      List localList2 = localGeocoder.getFromLocation(paramDouble1, paramDouble2, 1);
      localList1 = localList2;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        List localList1 = null;
      }
    }
    if ((localList1 != null) && (!localList1.isEmpty())) {
      return (Address)localList1.get(0);
    }
    return null;
  }*/
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    requestWindowFeature(5);
    setContentView(R.layout.main_screen);
    this.mDrawerList = ((ListView)findViewById(R.id.left_drawer));
    this.mMenuItems = getResources().getStringArray(R.array.main_menu);
    this.mDrawerLayout = ((DrawerLayout)findViewById(R.id.drawer_layout));
    this.mDrawerList.setAdapter(new ArrayAdapter(this, R.layout.main_menu_item, this.mMenuItems));
    setProgressBarIndeterminateVisibility(true);
    this.mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    countryCode = ((TelephonyManager)getSystemService("phone")).getNetworkCountryIso().toUpperCase();
    this.mDBHelper = connectToDB();
    this.mCountry = this.mDBHelper.getCountryByCode(countryCode);
    if (this.mCountry != null) {
      new Handler().postDelayed(openDrawerRunnable(), 1000L);
    }
  }
  
  public static String getEmergencyNumberByCountryCode(String paramString)
  {
    if (paramString.equals("UA")) {
      return "112";
    }
    return "112";
  }
  
  private Runnable openDrawerRunnable()
  {
    return new Runnable()
    {
      public void run()
      {
        MainScreen.this.setProgressBarIndeterminateVisibility(false);
        MainScreen.this.getActionBar().setTitle("Welcome To " + MainScreen.this.mCountry.getName());
        MainScreen.this.mDrawerLayout.openDrawer(3);
        MainScreen.this.mDrawerList.performItemClick(MainScreen.this.mDrawerList.getAdapter().getView(0, null, null), 0, 0L);
        MainScreen.this.mDrawerLayout.openDrawer(3);
      }
    };
  }
  
  private void selectItem(int paramInt) {
    switch (paramInt) {
	    case 0:
	    	showInfoFragment();
	    	break;
	    	
	    case 1:
	    	showCurrencyFragment();
	    	break;
	    	
	    case 2:
	    	showTranslatorFragment();
	    	break;
	    	
	    case 3:
	    	showEmergencyButtonFragment();
	    	break;
    }

    this.mDrawerList.setItemChecked(paramInt, true);
    getActionBar().setSubtitle(this.mMenuItems[paramInt]);
    this.mDrawerLayout.closeDrawer(this.mDrawerList);
  }
  
  private void showCurrencyFragment() {
    InputMethodManager localInputMethodManager = (InputMethodManager)getSystemService("input_method");
    if (localInputMethodManager != null) {
      //localInputMethodManager.toggleSoftInput(2, 0);
    }
    Bundle localBundle = new Bundle();
    String str1 = this.mDBHelper.getCurrencyByCountry(this.mCountry.getCode());
    if (str1 != null) {
      localBundle.putString("currency", str1);
    }
    String str2 = CountryHelper.getSavedNativeCountry(this);
    String str3 = this.mDBHelper.getCurrencyByCountry(str2);
    if (str3 != null) {
      localBundle.putString("ncurrency", str3);
    }
    showFragment(new CurrencyFragment(), localBundle);
  }
  
  private void showEmergencyButtonFragment()
  {
    showFragment(new EmergencyButtonFragment());
  }
  
  private void showFragment(Fragment paramFragment)
  {
    showFragment(paramFragment, null);
  }
  
  private void showFragment(Fragment paramFragment, Bundle paramBundle) {
    if (paramBundle == null) {
    	paramBundle = new Bundle();
    }
    
    paramBundle.putParcelable("dc", this.mCountry);
    paramFragment.setArguments(paramBundle);
    getFragmentManager().beginTransaction().replace(R.id.content_frame, paramFragment).commit();
  }
  
  private void showInfoFragment() {
    showFragment(new InfoFragment());
  }
  
  private void showTranslatorFragment() {
    showFragment(new TranslatorFragment());
  }
  
  private class DrawerItemClickListener implements ListView.OnItemClickListener {
	
	  @Override
	 public void onItemClick(AdapterView paramAdapterView, View paramView, int paramInt, long paramLong) {
		 MainScreen.this.selectItem(paramInt);
		System.out.println("TEST");
	 }
  }
  
  private DataBaseHelper connectToDB() {
    DataBaseHelper localDataBaseHelper = new DataBaseHelper(this);
    try {
      localDataBaseHelper.openDataBase();
      return localDataBaseHelper;
    }
    catch (SQLException localSQLException)
    {
      throw localSQLException;
    }
  }
  
  public static class EmergencyButtonFragment extends Fragment
  {
    
    void makeEmergencyCall()
    {
      Intent localIntent = new Intent("android.intent.action.CALL", Uri.parse("tel://" + MainScreen.getEmergencyNumberByCountryCode(MainScreen.countryCode)));
      localIntent.setFlags(268697600);
      startActivity(localIntent);
    }
    
    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
      View localView = paramLayoutInflater.inflate(2130903043, paramViewGroup, false);
      ((Button)localView.findViewById(2131296263)).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          MainScreen.EmergencyButtonFragment.this.makeEmergencyCall();
        }
      });
      return localView;
    }
  }
}
