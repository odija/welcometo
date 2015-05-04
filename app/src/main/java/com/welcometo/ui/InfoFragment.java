package com.welcometo.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.welcometo.R;
import com.welcometo.helpers.Constants;
import com.welcometo.helpers.Country;

public class InfoFragment extends Fragment
{
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {

    View localView = paramLayoutInflater.inflate(R.layout.info, paramViewGroup, false);
    WebView localWebView = (WebView)localView.findViewById(R.id.webBrowser);

    getActivity().setProgressBarIndeterminateVisibility(true);

    // init web client
    localWebView.setWebViewClient(new WebViewClient()
    {
      public void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
      {
        InfoFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
      }
    });

    //
    Bundle localBundle = getArguments();
    if (localBundle != null) {
      Country localCountry = (Country)localBundle.getParcelable(Constants.PARAM_COUNTRY);
      if (localCountry != null) {
        localWebView.loadUrl("http://en.m.wikipedia.org/wiki/" + localCountry.getName());
      }
    }
    return localView;
  }
}