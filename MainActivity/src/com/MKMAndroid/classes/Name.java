package com.MKMAndroid.classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by adunjo on 6/02/14.
 */
public class Name implements Parcelable
{
    private int idLanguage;
    private String languageName;
    private String productName;

    public Name(Parcel parcel) {
        readToParcel(parcel);
    }

    public Name() {

    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public static final Parcelable.Creator<Name> CREATOR = new Parcelable.Creator() {

        public Name createFromParcel(Parcel parcel) {
            return new Name(parcel);
        }

        public Name[] newArray(int size) {
            return new Name[size];
        }


    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idLanguage);
        parcel.writeString(languageName);
        parcel.writeString(productName);
    }

    public void readToParcel(Parcel parcel){
        idLanguage = parcel.readInt();
        languageName = parcel.readString();
        productName = parcel.readString();
    }
}
