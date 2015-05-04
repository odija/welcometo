package com.welcometo.ui;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.welcometo.R;

public class EmergencyButtonFragment extends Fragment {
  void makeEmergencyCall() {
    Intent localIntent = new Intent("android.intent.action.CALL", Uri.parse("tel://" + MainScreen.getEmergencyNumberByCountryCode(MainScreen.countryCode)));
    localIntent.setFlags(268697600);
    startActivity(localIntent);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    View localView = paramLayoutInflater.inflate(R.layout.emergency_button, paramViewGroup, false);

    ((Button)localView.findViewById(R.id.btnEmergency)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        EmergencyButtonFragment.this.makeEmergencyCall();
      }
    });

    return localView;
  }
}