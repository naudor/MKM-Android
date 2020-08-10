package com.MKMAndroid.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo implements Parcelable{
	private Seller basicInfo;
	private PersonName name;
	private Address address;
	
	public Seller getBasicInfo() {
		return basicInfo;
	}
	public void setBasicInfo(Seller basicInfo) {
		this.basicInfo = basicInfo;
	}
	public PersonName getName() {
		return name;
	}
	public void setName(PersonName name) {
		this.name = name;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator() {

        public UserInfo createFromParcel(Parcel parcel) {
            return new UserInfo(parcel);
        }

        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }


    };
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public UserInfo(Parcel parcel){
        readToParcel(parcel);
    }
    
    public UserInfo(){
    	
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int i) { 
    	parcel.writeParcelable(basicInfo, 1);
    	parcel.writeParcelable(name, 1);
    	parcel.writeParcelable(address, 1);
    }

    public void readToParcel(Parcel parcel){
    	basicInfo = (Seller) parcel.readParcelable(Seller.class.getClassLoader());
    	name = (PersonName) parcel.readParcelable(PersonName.class.getClassLoader());
    	address = (Address) parcel.readParcelable(Address.class.getClassLoader());
    }
}
