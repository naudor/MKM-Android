package com.MKMAndroid.classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by adunjo on 6/02/14.
 */
public class PriceGuide implements Parcelable
{
    private float sell;
    private float low;
    private float avg;

    public PriceGuide(Parcel parcel) {
        readToParcel(parcel);
    }

    public PriceGuide() {

    }

    public float getAvg() {
        return avg;
    }

    public void setAvg(float avg) {
        this.avg = avg;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public float getSell() {
        return sell;
    }

    public void setSell(float sell) {
        this.sell = sell;
    }


    public static final Parcelable.Creator<PriceGuide> CREATOR = new Parcelable.Creator() {

        public PriceGuide createFromParcel(Parcel parcel) {
            return new PriceGuide(parcel);
        }

        public PriceGuide[] newArray(int size) {
            return new PriceGuide[size];
        }


    };
    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(sell);
        parcel.writeFloat(low);
        parcel.writeFloat(avg);
    }

    public void readToParcel(Parcel parcel){
        sell = parcel.readFloat();
        low = parcel.readFloat();
        avg = parcel.readFloat();
    }
}
