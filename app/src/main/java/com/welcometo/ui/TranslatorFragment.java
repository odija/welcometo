package com.welcometo.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.welcometo.R;
import com.welcometo.helpers.Country;
import java.util.Locale;

public class TranslatorFragment
  extends Fragment
  implements TextToSpeech.OnInitListener
{
  private static final int MY_DATA_CHECK_CODE = 123;
  private Country mCurrentCountry;
  private Locale mCurrentLocale;
  private TextToSpeech mTts;
  
  public void audioPlayer(String paramString)
  {
    MediaPlayer localMediaPlayer = new MediaPlayer();
    try
    {
      AssetFileDescriptor localAssetFileDescriptor = getActivity().getAssets().openFd(paramString);
      localMediaPlayer.setDataSource(localAssetFileDescriptor.getFileDescriptor(), localAssetFileDescriptor.getStartOffset(), localAssetFileDescriptor.getLength());
      localMediaPlayer.prepare();
      localMediaPlayer.start();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == 123)
    {
      if (paramInt2 == 1) {
        this.mTts = new TextToSpeech(getActivity(), this);
      }
    }
    else {
      return;
    }
    Intent localIntent = new Intent();
    localIntent.setAction("android.speech.tts.engine.INSTALL_TTS_DATA");
    startActivity(localIntent);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.translator, paramViewGroup, false);
    
    localView.findViewById(R.id.btnHi).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (TranslatorFragment.this.mCurrentCountry.getCode().equals("UA"))
        {
          TranslatorFragment.this.audioPlayer("pruvit.m4a");
          return;
        }
        TranslatorFragment.this.mTts.speak(TranslatorFragment.this.getString(R.string.hi), 0, null);
      }
    });
    
    localView.findViewById(R.id.btnHowAreYou).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (TranslatorFragment.this.mCurrentCountry.getCode().equals("UA"))
        {
          TranslatorFragment.this.audioPlayer("yakspravu.m4a");
          return;
        }
        TranslatorFragment.this.mTts.speak(TranslatorFragment.this.getString(R.string.hru), 0, null);
      }
    });
    
    localView.findViewById(R.id.btnThanks).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (TranslatorFragment.this.mCurrentCountry.getCode().equals("UA"))
        {
          TranslatorFragment.this.audioPlayer("dyakuy.m4a");
          return;
        }
        TranslatorFragment.this.mTts.speak(TranslatorFragment.this.getString(R.string.thanks), 0, null);
      }
    });
    
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      this.mCurrentCountry = ((Country)localBundle.getParcelable("dc"));
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
    for (;;)
    {
      if (!Locale.getDefault().getCountry().equals(this.mCurrentLocale.getCountry()))
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
      Intent localIntent = new Intent();
      localIntent.setAction("android.speech.tts.engine.CHECK_TTS_DATA");
      startActivityForResult(localIntent, 123);
      return localView;
    }
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    if (this.mTts != null) {
      this.mTts.shutdown();
    }
  }
  
  public void onInit(int paramInt)
  {
    if (paramInt == 0)
    {
      int i = this.mTts.setLanguage(this.mCurrentLocale);
      if ((i == -1) || (i == -2)) {
        Log.e("TTS", "This Language is not supported");
      }
      return;
    }
    Log.e("TTS", "Initilization Failed!");
  }
}


/* Location:           D:\projects\decompilation\dex2jar-0.0.9.15\WelcomeTo_dex2jar.jar
 * Qualified Name:     com.welcometo.ui.TranslatorFragment
 * JD-Core Version:    0.7.0.1
 */