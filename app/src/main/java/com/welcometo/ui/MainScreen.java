package com.welcometo.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.welcometo.R;
import com.welcometo.helpers.Constants;
import com.welcometo.helpers.Country;
import com.welcometo.helpers.CountryHelper;
import com.welcometo.helpers.DataBaseHelper;
import com.welcometo.helpers.InitCurrencyRate;
import com.welcometo.service.CurrencyRateService;

public class MainScreen extends Activity implements SettingsFragment.OnSettingsListener {
	
  public static String countryCode;

  private static String TAG = "welcometo";

  // current country
  private Country mCountry;
  private DataBaseHelper mDBHelper;
  private DrawerLayout mDrawerLayout;
  private ListView mDrawerList;
  private LocationManager mLocationManager;
  private String[] mMenuItems;

  private JobScheduler mInitCurrencyJobSheduler;
  
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

    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

    setContentView(R.layout.main_screen);

    this.mDrawerList = ((ListView) findViewById(R.id.left_drawer));
    this.mMenuItems = getResources().getStringArray(R.array.main_menu);
    this.mDrawerLayout = ((DrawerLayout) findViewById(R.id.drawer_layout));
    this.mDrawerList.setAdapter(new ArrayAdapter(this, R.layout.main_menu_item, this.mMenuItems));

    setProgressBarIndeterminateVisibility(true);

    this.mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

    // connect to local database
    this.mDBHelper = connectToDB();

    // calc current country code
    countryCode = ((TelephonyManager) getSystemService("phone")).getNetworkCountryIso().toUpperCase();

    this.mCountry = this.mDBHelper.getCountryByCode(countryCode);

    // open sidebar menu
    if (this.mCountry != null) {
      new Handler().postDelayed(openDrawerRunnable(), 1000L);
    }

    // init currency
    //if (ConnectionHelper.isConnected(this)) {
    //  new InitCurrencyRate(this).execute();
    //}

    mInitCurrencyJobSheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

    JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(getPackageName(),
            CurrencyRateService.class.getName())).setPeriodic(24*3600*1000).setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);

    mInitCurrencyJobSheduler.schedule(builder.build());

    new InitCurrencyRate(getApplicationContext(), null).execute();
  }

  public static String getEmergencyNumberByCountryCode(String paramString) {
    if (paramString.equals("UA")) {
      return "112";
    }
    return "112";
  }

  @Override
  public void onCurrentCountry(String newCountryCode) {
    countryCode = newCountryCode;

    this.mCountry = this.mDBHelper.getCountryByCode(countryCode);

    MainScreen.this.getActionBar().setTitle("Welcome To " + MainScreen.this.mCountry.getName());
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

        case 4:
            showSettingsFragment();
            break;
    }

    this.mDrawerList.setItemChecked(paramInt, true);

    getActionBar().setSubtitle(this.mMenuItems[paramInt]);

    this.mDrawerLayout.closeDrawer(this.mDrawerList);
  }
  
  private void showCurrencyFragment() {
    //InputMethodManager localInputMethodManager = (InputMethodManager)getSystemService("input_method");
    //if (localInputMethodManager != null) {
      //localInputMethodManager.toggleSoftInput(2, 0);
    //}

    Bundle paramBundle = new Bundle();

    // get current currency
    String currentCurrency = this.mDBHelper.getCurrencyByCountry(this.mCountry.getCode());
    if (currentCurrency != null) {
      paramBundle.putString(CurrencyFragment.PARAM_CURRENT_CURRENCY, currentCurrency);
    }

    // get native currency
    String ownCountryCode = CountryHelper.getSavedNativeCountry(this);
    String ownCurrency = this.mDBHelper.getCurrencyByCountry(ownCountryCode);
    if (ownCurrency != null) {
      paramBundle.putString(CurrencyFragment.PARAM_NATIVE_CURRENCY, ownCurrency);
    }

    showFragment(new CurrencyFragment(), paramBundle);
  }
  
  private void showEmergencyButtonFragment()  {
    showFragment(new EmergencyButtonFragment());
  }

  private void showSettingsFragment() {
    showFragment(new SettingsFragment());
  }

  private void showInfoFragment() {
    showFragment(new InfoFragment());
  }
  
  private void showTranslatorFragment() {
    showFragment(new TranslatorFragment());
  }

  private void showFragment(Fragment paramFragment)  {
    showFragment(paramFragment, null);
  }

  private void showFragment(Fragment paramFragment, Bundle paramBundle) {
    if (paramBundle == null) {
      paramBundle = new Bundle();
    }

    paramBundle.putParcelable(Constants.PARAM_COUNTRY, this.mCountry);
    paramFragment.setArguments(paramBundle);
    getFragmentManager().beginTransaction().replace(R.id.content_frame, paramFragment).commit();
  }

  private class DrawerItemClickListener implements ListView.OnItemClickListener {
	
	  @Override
	 public void onItemClick(AdapterView paramAdapterView, View paramView, int paramInt, long paramLong) {
		 MainScreen.this.selectItem(paramInt);
		System.out.println("TEST");
	 }
  }
  
  private DataBaseHelper connectToDB() {
    DataBaseHelper localDataBaseHelper = DataBaseHelper.getInstance(this);
    localDataBaseHelper.connectToDB();
    return localDataBaseHelper;
  }
}
