package com.MKMAndroid.classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by adunjo on 6/03/14.
 */
public class Seller implements Parcelable
{
    private int idUser;
    private String username;
    private String country;
    private boolean isCommercial;
    private int riskGroup;
    private int reputation;  

    public Seller(){
    	
    }
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isCommercial() {
        return isCommercial;
    }

    public void setCommercial(boolean isCommercial) {
        this.isCommercial = isCommercial;
    }

    public int getRiskGroup() {
        return riskGroup;
    }

    public void setRiskGroup(int riskGroup) {
        this.riskGroup = riskGroup;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public static final Parcelable.Creator<Seller> CREATOR = new Parcelable.Creator() {

        public Seller createFromParcel(Parcel parcel) {
            return new Seller(parcel);
        }

        public Seller[] newArray(int size) {
            return new Seller[size];
        }


    };
    
    public Seller(Parcel parcel) {
        readToParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idUser);
        parcel.writeString(username);
        parcel.writeString(country);
        parcel.writeByte((byte)(isCommercial ? 1 : 0));
        parcel.writeInt(riskGroup);
        parcel.writeInt(reputation);
    }

    public void readToParcel(Parcel parcel){
    	idUser = parcel.readInt();
    	username = parcel.readString();
    	country = parcel.readString();
    	isCommercial = parcel.readByte() != 0;
    	riskGroup = parcel.readInt();
    	reputation = parcel.readInt();
    }
}
