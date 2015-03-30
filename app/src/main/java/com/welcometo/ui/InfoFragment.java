package com.welcometo.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.welcometo.helpers.Country;

public class InfoFragment
  extends Fragment
{
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903044, paramViewGroup, false);
    WebView localWebView = (WebView)localView.findViewById(2131296264);
    getActivity().setProgressBarIndeterminateVisibility(true);
    localWebView.setWebViewClient(new WebViewClient()
    {
      public void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
      {
        InfoFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
      }
    });
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      Country localCountry = (Country)localBundle.getParcelable("dc");
      if (localCountry != null) {
        localWebView.loadUrl("http://en.m.wikipedia.org/wiki/" + localCountry.getName());
      }
    }
    return localView;
  }
}


/* Location:           D:\projects\decompilation\dex2jar-0.0.9.15\WelcomeTo_dex2jar.jar
 * Qualified Name:     com.welcometo.ui.InfoFragment
 * JD-Core Version:    0.7.0.1
 */