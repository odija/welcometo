package com.welcometo.ui;

import android.app.Fragment;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.welcometo.R;
import com.welcometo.helpers.Country;

import java.io.FileNotFoundException;
import java.util.Locale;

public class TranslatorFragment extends Fragment {

  private static final int MY_DATA_CHECK_CODE = 123;
  private Country mCurrentCountry;
  private Locale mCurrentLocale;

  private TextView mTxtHi;
    private TextView mTxtHRU;
    private TextView mTxtThanks;
    private TextView mTxtHowMuch;

  public void audioPlayer(String paramString) {
    MediaPlayer localMediaPlayer = new MediaPlayer();

    try {
      AssetFileDescriptor localAssetFileDescriptor = getActivity().getAssets().openFd(paramString);
      localMediaPlayer.setDataSource(localAssetFileDescriptor.getFileDescriptor(), localAssetFileDescriptor.getStartOffset(), localAssetFileDescriptor.getLength());
      localMediaPlayer.prepare();
      localMediaPlayer.start();
    }
    catch (FileNotFoundException exception) {
      exception.printStackTrace();
      Toast.makeText(getActivity(), "File not found:" + exception.getMessage(), Toast.LENGTH_LONG).show();
    } catch (Exception localException) {
      localException.printStackTrace();
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    View localView = paramLayoutInflater.inflate(R.layout.translator, paramViewGroup, false);
    
    localView.findViewById(R.id.btnHi).setOnClickListener(new View.OnClickListener() {
      public void onClick(View paramAnonymousView) {
        String countryCode = TranslatorFragment.this.getCountryCode();
        TranslatorFragment.this.audioPlayer(countryCode + "_hi.mp3");
      }
    });
    
    localView.findViewById(R.id.btnHowAreYou).setOnClickListener(new View.OnClickListener() {
      public void onClick(View paramAnonymousView) {
        String countryCode = TranslatorFragment.this.getCountryCode();
        TranslatorFragment.this.audioPlayer(countryCode + "_hru.mp3");
      }
    });
    
    localView.findViewById(R.id.btnThanks).setOnClickListener(new View.OnClickListener() {
      public void onClick(View paramAnonymousView) {
        String countryCode = TranslatorFragment.this.getCountryCode();
        TranslatorFragment.this.audioPlayer(countryCode + "_thx.mp3");
      }
    });

    localView.findViewById(R.id.btnHowMuch).setOnClickListener(new View.OnClickListener() {
      public void onClick(View paramAnonymousView) {
        String countryCode = TranslatorFragment.this.getCountryCode();
        TranslatorFragment.this.audioPlayer(countryCode + "_hm.mp3");
      }
    });

     mTxtHi = (TextView)localView.findViewById(R.id.txtHi);
      mTxtHRU = (TextView)localView.findViewById(R.id.txtHRU);
      mTxtThanks = (TextView)localView.findViewById(R.id.txtThanks);
    mTxtHowMuch = (TextView)localView.findViewById(R.id.txtHowMuch);

    // init
    Bundle localBundle = getArguments();

    if (localBundle != null) {
      this.mCurrentCountry = localBundle.getParcelable("dc");

      if (this.mCurrentCountry.getLanguage() != null) {
    	  if (this.mCurrentCountry.getLanguage().equals("fr")) {
    	        this.mCurrentLocale = Locale.FRANCE;
          } else if (this.mCurrentCountry.getLanguage().equals("de")) {
    	        this.mCurrentLocale = Locale.GERMANY;
          } else if (this.mCurrentCountry.getLanguage().equals("es")) {
    	        this.mCurrentLocale = new Locale("es", "ES");
          } else if (this.mCurrentCountry.getLanguage().equals("it")) {
    	        this.mCurrentLocale = Locale.ITALY;
          }
      } else
       this.mCurrentLocale = Locale.getDefault();
    }

      Configuration conf = getResources().getConfiguration();
      conf.locale = new Locale(getCountryCode());
      DisplayMetrics metrics = new DisplayMetrics();
      getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
      Resources resources = new Resources(getActivity().getAssets(), metrics, conf);

      mTxtHi.setText(resources.getString(R.string.hi));
    mTxtHRU.setText(resources.getString(R.string.hru));
    mTxtThanks.setText(resources.getString(R.string.thanks));
    mTxtHowMuch.setText(resources.getString(R.string.howmuch));

    return localView;
  }

  private String getCountryCode() {
      return mCurrentCountry.getCode().toLowerCase();
  }
}