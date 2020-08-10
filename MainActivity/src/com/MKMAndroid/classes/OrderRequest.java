package com.MKMAndroid.classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by adunjo on 6/02/14.
 */
public class OrderRequest implements Parcelable
{
    private String action;
	private String reason;
	private int relist;


    public OrderRequest(Parcel parcel) {
        readToParcel(parcel);
    }

    public OrderRequest() {

    }
    
    public OrderRequest(String action, String reason, int amount) {
    	this.action = action;
    	this.reason = reason;
    	this.relist = amount;
    }

    public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

    public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getRelist() {
		return relist;
	}

	public void setRelist(int relist) {
		this.relist = relist;
	}





	public static final Parcelable.Creator<OrderRequest> CREATOR = new Parcelable.Creator() {

        public OrderRequest createFromParcel(Parcel parcel) {
            return new OrderRequest(parcel);
        }

        public OrderRequest[] newArray(int size) {
            return new OrderRequest[size];
        }


    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(action);
        parcel.writeString(reason);
        parcel.writeInt(relist);
    }

    public void readToParcel(Parcel parcel){
        action = parcel.readString();
        reason = parcel.readString();
        relist = parcel.readInt();
    }
}
