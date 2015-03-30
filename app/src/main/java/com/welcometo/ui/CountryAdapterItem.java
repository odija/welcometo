package com.welcometo.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.welcometo.helpers.Country;
import java.util.ArrayList;

public class CountryAdapterItem
  extends ArrayAdapter<Country>
{
  ArrayList<Country> data = null;
  int layoutResourceId;
  Context mContext;
  
  public CountryAdapterItem(Context paramContext, int paramInt, ArrayList<Country> paramArrayList)
  {
    super(paramContext, paramInt, paramArrayList);
    this.layoutResourceId = paramInt;
    this.mContext = paramContext;
    this.data = paramArrayList;
  }
  
  public View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null) {
      paramView = ((Activity)this.mContext).getLayoutInflater().inflate(this.layoutResourceId, paramViewGroup, false);
    }
    Country localCountry = (Country)this.data.get(paramInt);
    TextView localTextView = (TextView)paramView;
    localTextView.setText(localCountry.getName());
    localTextView.setTag(localCountry.getCode());
    return paramView;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null) {
      paramView = ((Activity)this.mContext).getLayoutInflater().inflate(this.layoutResourceId, paramViewGroup, false);
    }
    Country localCountry = (Country)this.data.get(paramInt);
    TextView localTextView = (TextView)paramView;
    localTextView.setText(localCountry.getName());
    localTextView.setTag(localCountry.getCode());
    return paramView;
  }
}


/* Location:           D:\projects\decompilation\dex2jar-0.0.9.15\WelcomeTo_dex2jar.jar
 * Qualified Name:     com.welcometo.ui.CountryAdapterItem
 * JD-Core Version:    0.7.0.1
 */