package com.welcometo.ui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.welcometo.helpers.Country;
import java.util.ArrayList;

public class CountryAdapterItem extends ArrayAdapter<Country> {
  ArrayList<Country> data = null;
  int layoutResourceId;
  Context mContext;
  
  public CountryAdapterItem(Context paramContext, int paramInt, ArrayList<Country> paramArrayList) {
    super(paramContext, paramInt, paramArrayList);
    this.layoutResourceId = paramInt;
    this.mContext = paramContext;
    this.data = paramArrayList;
  }
  
  public View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    if (paramView == null) {
      paramView = ((Activity)this.mContext).getLayoutInflater().inflate(this.layoutResourceId, paramViewGroup, false);
    }
    Country localCountry = this.data.get(paramInt);
    TextView localTextView = (TextView)paramView;
    localTextView.setText(localCountry.getName());
    localTextView.setTag(localCountry.getCode());
    return paramView;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    if (paramView == null) {
      paramView = ((Activity)this.mContext).getLayoutInflater().inflate(this.layoutResourceId, paramViewGroup, false);
    }
    Country localCountry = this.data.get(paramInt);
    TextView localTextView = (TextView)paramView;
    localTextView.setText(localCountry.getName());
    localTextView.setTag(localCountry.getCode());
    return paramView;
  }
}