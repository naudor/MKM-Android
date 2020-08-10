package com.MKMAndroid.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class ShippingMethod implements Parcelable{
	private String name;
	private Float price;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	
	public ShippingMethod(){}
	
	public ShippingMethod(Parcel parcel){
	        readToParcel(parcel);
	    }
	 
	public static final Parcelable.Creator<ShippingMethod> CREATOR = new Parcelable.Creator() {

        public ShippingMethod createFromParcel(Parcel parcel) {
            return new ShippingMethod(parcel);
        }

        public ShippingMethod[] newArray(int size) {
            return new ShippingMethod[size];
        }


    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeFloat(price);
    }

    public void readToParcel(Parcel parcel){
    	name = parcel.readString();
    	price = parcel.readFloat();
    }
}
