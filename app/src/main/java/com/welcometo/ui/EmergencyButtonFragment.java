package com.welcometo.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class EmergencyButtonFragment
  extends Fragment
{
  void makeEmergencyCall() {}
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903043, paramViewGroup, false);
    ((Button)localView.findViewById(2131296263)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        EmergencyButtonFragment.this.makeEmergencyCall();
      }
    });
    return localView;
  }
}


/* Location:           D:\projects\decompilation\dex2jar-0.0.9.15\WelcomeTo_dex2jar.jar
 * Qualified Name:     com.welcometo.ui.EmergencyButtonFragment
 * JD-Core Version:    0.7.0.1
 */