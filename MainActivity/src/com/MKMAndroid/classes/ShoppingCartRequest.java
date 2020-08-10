package com.MKMAndroid.classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by adunjo on 6/02/14.
 */
public class ShoppingCartRequest implements Parcelable
{
    private String action;
	private int idArticle;
	private int amount;


    public ShoppingCartRequest(Parcel parcel) {
        readToParcel(parcel);
    }

    public ShoppingCartRequest() {

    }
    
    public ShoppingCartRequest(String action, int idArticle, int amount) {
    	this.action = action;
    	this.idArticle = idArticle;
    	this.amount = amount;
    }

    public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getIdArticle() {
		return idArticle;
	}

	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}



    public static final Parcelable.Creator<ShoppingCartRequest> CREATOR = new Parcelable.Creator() {

        public ShoppingCartRequest createFromParcel(Parcel parcel) {
            return new ShoppingCartRequest(parcel);
        }

        public ShoppingCartRequest[] newArray(int size) {
            return new ShoppingCartRequest[size];
        }


    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(action);
        parcel.writeInt(idArticle);
        parcel.writeInt(amount);
    }

    public void readToParcel(Parcel parcel){
        action = parcel.readString();
        idArticle = parcel.readInt();
        amount = parcel.readInt();
    }
}
