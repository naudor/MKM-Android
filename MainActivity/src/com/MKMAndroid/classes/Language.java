package com.MKMAndroid.classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by adunjo on 6/03/14.
 */
public class Language implements Parcelable
{
    private int idLanguage;
    private String languageName;
   
    public Language(){
    	
    }
    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public int getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(int idLanguage) {
        this.idLanguage = idLanguage;
    }

    
    public static final Parcelable.Creator<Language> CREATOR = new Parcelable.Creator() {

        public Language createFromParcel(Parcel parcel) {
            return new Language(parcel);
        }

        public Language[] newArray(int size) {
            return new Language[size];
        }


    };

    public Language(Parcel parcel) {
        readToParcel(parcel);
    }
    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idLanguage);
        parcel.writeString(languageName);
    }

    public void readToParcel(Parcel parcel){
        idLanguage = parcel.readInt();
        languageName = parcel.readString();
    }

}
