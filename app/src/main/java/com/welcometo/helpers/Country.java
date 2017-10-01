package com.welcometo.helpers;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;

public class Country implements Parcelable {

  public static final Creator CREATOR = new Creator() {
    public Country createFromParcel(Parcel paramAnonymousParcel) {
      return new Country(paramAnonymousParcel);
    }
    
    public Country[] newArray(int paramAnonymousInt) {
      return new Country[paramAnonymousInt];
    }
  };

  private String mCode = "0";
  private String mLanguage;
  private String mName;
  private Integer mPhoneCode = -9999;

  @Override
  public int hashCode() {
    return mPhoneCode;
  }

  @Override
  public boolean equals(Object o) {
    return hashCode() == o.hashCode();
  }

  public Country() {

  }
  public Country(Parcel paramParcel) {
    String[] arrayOfString = new String[4];
    paramParcel.readStringArray(arrayOfString);
    this.mCode = arrayOfString[0];
    this.mName = arrayOfString[1];
    this.mPhoneCode = Integer.valueOf(arrayOfString[2]);
    this.mLanguage = arrayOfString[3];
  }


  public int describeContents() {
    return 0;
  }
  
  public String getCode() {
    return this.mCode;
  }
  
  public String getLanguage() {
    return this.mLanguage;
  }
  
  public String getName() {
    return this.mName;
  }
  
  public Integer getPhoneCode() {
    return this.mPhoneCode;
  }
  
  public void setCode(String paramString) {
    this.mCode = paramString;
  }
  
  public void setLanguage(String paramString) {
    this.mLanguage = paramString;
  }
  
  public void setName(String paramString) {
    this.mName = paramString;
  }
  
  public void setPhoneCode(Integer paramInteger) {
    this.mPhoneCode = paramInteger;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    String[] arrayOfString = new String[4];
    arrayOfString[0] = this.mCode;
    arrayOfString[1] = this.mName;
    arrayOfString[2] = String.valueOf(this.mPhoneCode);
    arrayOfString[3] = this.mLanguage;
    paramParcel.writeStringArray(arrayOfString);
  }
}