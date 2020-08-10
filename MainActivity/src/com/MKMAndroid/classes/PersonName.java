package com.MKMAndroid.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class PersonName implements Parcelable {
	private String firstName;
	private String lastName;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public static final Parcelable.Creator<PersonName> CREATOR = new Parcelable.Creator() {

        public PersonName createFromParcel(Parcel parcel) {
            return new PersonName(parcel);
        }

        public PersonName[] newArray(int size) {
            return new PersonName[size];
        }


    };
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public PersonName(Parcel parcel){
        readToParcel(parcel);
    }
    
    public PersonName(){
    	
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int i) { 
    	parcel.writeString(firstName);
    	parcel.writeString(lastName);
    }

    public void readToParcel(Parcel parcel){
    	firstName = parcel.readString();
    	lastName = parcel.readString();
    }
	
}
