package com.MKMAndroid.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class State implements Parcelable {
	private String state;
	private String dateBought;
	private String datePaid;
	private String dateSent;
	private String dateReceived;
	private String dateCancelled; 
	
	public String getDateCancelled() {
		return dateCancelled;
	}
	public void setDateCancelled(String dateCancelled) {
		this.dateCancelled = dateCancelled;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDateBought() {
		return dateBought;
	}
	public void setDateBought(String dateBought) {
		this.dateBought = dateBought;
	}
	public String getDatePaid() {
		return datePaid;
	}
	public void setDatePaid(String datePaid) {
		this.datePaid = datePaid;
	}
	public String getDateSent() {
		return dateSent;
	}
	public void setDateSent(String dateSent) {
		this.dateSent = dateSent;
	}
	public String getDateReceived() {
		return dateReceived;
	}
	public void setDateReceived(String dateReceived) {
		this.dateReceived = dateReceived;
	}


	public static final Parcelable.Creator<State> CREATOR = new Parcelable.Creator() {

        public State createFromParcel(Parcel parcel) {
            return new State(parcel);
        }

        public State[] newArray(int size) {
            return new State[size];
        }


    };
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public State(Parcel parcel){
        readToParcel(parcel);
    }
    
    public State(){
    	
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int i) {   	
    	parcel.writeString(state);
    	parcel.writeString(dateBought);
    	parcel.writeString(datePaid);
    	parcel.writeString(dateSent);
    	parcel.writeString(dateReceived);
    	parcel.writeString(dateCancelled);
    }

    public void readToParcel(Parcel parcel){
    	state = parcel.readString();
    	dateBought = parcel.readString();
    	datePaid = parcel.readString();
    	dateSent = parcel.readString();
    	dateReceived = parcel.readString();
    	dateCancelled = parcel.readString();
    }
}
