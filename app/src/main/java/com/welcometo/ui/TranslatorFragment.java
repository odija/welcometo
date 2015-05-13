package com.welcometo.ui;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.welcometo.R;
import com.welcometo.helpers.Country;

import java.io.FileNotFoundException;
import java.util.Locale;

public class TranslatorFragment extends Fragment {

  private static final int MY_DATA_CHECK_CODE = 123;
  private Country mCurrentCountry;
  private Locale mCurrentLocale;

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
        String countryCode = TranslatorFragment.this.mCurrentCountry.getCode().toLowerCase();
        TranslatorFragment.this.audioPlayer(countryCode + "_hi.mp3");
      }
    });
    
    localView.findViewById(R.id.btnHowAreYou).setOnClickListener(new View.OnClickListener() {
      public void onClick(View paramAnonymousView) {
        String countryCode = TranslatorFragment.this.mCurrentCountry.getCode().toLowerCase();
        TranslatorFragment.this.audioPlayer(countryCode + "_hru.mp3");
      }
    });
    
    localView.findViewById(R.id.btnThanks).setOnClickListener(new View.OnClickListener() {
      public void onClick(View paramAnonymousView) {
        String countryCode = TranslatorFragment.this.mCurrentCountry.getCode().toLowerCase();
        TranslatorFragment.this.audioPlayer(countryCode + "_thx.mp3");
      }
    });
    
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
      }
      this.mCurrentLocale = Locale.getDefault();
    }

    /*if (!Locale.getDefault().getCountry().equals(this.mCurrentLocale.getCountry()))
      {
        Locale.setDefault(this.mCurrentLocale);
        Configuration localConfiguration = new Configuration();
        localConfiguration.locale = this.mCurrentLocale;
        getActivity().getResources().updateConfiguration(localConfiguration, getActivity().getResources().getDisplayMetrics());
        FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();
        localFragmentTransaction.detach(this);
        localFragmentTransaction.attach(this);
        localFragmentTransaction.commit();
      }

*/
    return localView;

  }
}