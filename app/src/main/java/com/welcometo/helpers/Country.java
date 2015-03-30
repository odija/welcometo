package com.welcometo.helpers;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Country
  implements Parcelable
{
  public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
  {
    public Country createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Country(paramAnonymousParcel);
    }
    
    public Country[] newArray(int paramAnonymousInt)
    {
      return new Country[paramAnonymousInt];
    }
  };
  private String mCode;
  private String mLanguage;
  private String mName;
  private Integer mPhoneCode;
  
  public Country() {}
  
  public Country(Parcel paramParcel)
  {
    String[] arrayOfString = new String[4];
    paramParcel.readStringArray(arrayOfString);
    this.mCode = arrayOfString[0];
    this.mName = arrayOfString[1];
    this.mPhoneCode = Integer.valueOf(arrayOfString[2]);
    this.mLanguage = arrayOfString[3];
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String getCode()
  {
    return this.mCode;
  }
  
  public String getLanguage()
  {
    return this.mLanguage;
  }
  
  public String getName()
  {
    return this.mName;
  }
  
  public Integer getPhoneCode()
  {
    return this.mPhoneCode;
  }
  
  public void setCode(String paramString)
  {
    this.mCode = paramString;
  }
  
  public void setLanguage(String paramString)
  {
    this.mLanguage = paramString;
  }
  
  public void setName(String paramString)
  {
    this.mName = paramString;
  }
  
  public void setPhoneCode(Integer paramInteger)
  {
    this.mPhoneCode = paramInteger;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    String[] arrayOfString = new String[4];
    arrayOfString[0] = this.mCode;
    arrayOfString[1] = this.mName;
    arrayOfString[2] = String.valueOf(this.mPhoneCode);
    arrayOfString[3] = this.mLanguage;
    paramParcel.writeStringArray(arrayOfString);
  }
}


/* Location:           D:\projects\decompilation\dex2jar-0.0.9.15\WelcomeTo_dex2jar.jar
 * Qualified Name:     com.welcometo.helpers.Country
 * JD-Core Version:    0.7.0.1
 */