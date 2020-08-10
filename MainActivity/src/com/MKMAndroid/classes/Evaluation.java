package com.MKMAndroid.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Evaluation implements Parcelable {
	private String overall; 
	private String articles; 
	private String packaging; 
	private String speed; 
	private String comments;
	
	public String getOverall() {
		return overall;
	}
	public void setOverall(String overall) {
		this.overall = overall;
	}
	public String getArticles() {
		return articles;
	}
	public void setArticles(String articles) {
		this.articles = articles;
	}
	public String getPackaging() {
		return packaging;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public static final Parcelable.Creator<Evaluation> CREATOR = new Parcelable.Creator() {

        public Evaluation createFromParcel(Parcel parcel) {
            return new Evaluation(parcel);
        }

        public Evaluation[] newArray(int size) {
            return new Evaluation[size];
        }


    };
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public Evaluation(Parcel parcel){
        readToParcel(parcel);
    }
    
    public Evaluation(){
    	
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int i) { 
    	parcel.writeString(overall);
    	parcel.writeString(articles);
    	parcel.writeString(packaging);
    	parcel.writeString(speed);
    	parcel.writeString(comments);
    }

    public void readToParcel(Parcel parcel){
    	overall = parcel.readString();
    	articles = parcel.readString();
    	packaging = parcel.readString();
    	speed = parcel.readString();
    	comments = parcel.readString();
    }
}
