package com.MKMAndroid.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {
	private String fullName;
	private String extra;
	private String street;
	private String zip;
	private String city;
	private String country;
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator() {

        public Address createFromParcel(Parcel parcel) {
            return new Address(parcel);
        }

        public Address[] newArray(int size) {
            return new Address[size];
        }


    };
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public Address(Parcel parcel){
        readToParcel(parcel);
    }
    
    public Address(){
    	
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int i) {   	
    	parcel.writeString(fullName);
    	parcel.writeString(extra);
    	parcel.writeString(street);
    	parcel.writeString(zip);
    	parcel.writeString(city);
    	parcel.writeString(country);

    }

    public void readToParcel(Parcel parcel){

    	fullName = parcel.readString();
    	extra = parcel.readString();
    	street = parcel.readString();
    	zip = parcel.readString();
    	city = parcel.readString();
    	country = parcel.readString();	
    }

}
