package com.MKMAndroid.classes;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by adunjo on 6/03/14.
 */
public class Article implements Parcelable
{
    private int idArticle;
    private int idProduct;
    private Language language;
    private String comments;
    private float price;
    private int count;
    private Seller seller;
    private String condition;
    private boolean isFoil = false;
    private boolean isSigned = false;
    private boolean isAltered =false;
    private boolean isPlayset = false;
    
    public Article(){
    	
    }
    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator() {

        public Article createFromParcel(Parcel parcel) {
            return new Article(parcel);
        }

        public Article[] newArray(int size) {
            return new Article[size];
        }


    };
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public Article(Parcel parcel){
        readToParcel(parcel);
    }
    
    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language name) {
        this.language = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean isFoil() {
        return isFoil;
    }

    public void setFoil(boolean isFoil) {
        this.isFoil = isFoil;
    }

    public boolean isSigned() {
        return isSigned;
    }

    public void setSigned(boolean isSigned) {
        this.isSigned = isSigned;
    }

    public boolean isAltered() {
        return isAltered;
    }

    public void setAltered(boolean isAltered) {
        this.isAltered = isAltered;
    }

    public boolean isPlayset() {
        return isPlayset;
    }

    public void setPlayset(boolean isPlayset) {
        this.isPlayset = isPlayset;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idArticle);
        parcel.writeInt(idProduct);
        parcel.writeParcelable(language,1);
        parcel.writeString(comments);
        parcel.writeFloat(price);
        parcel.writeInt(count);
        parcel.writeParcelable(seller,1);
        parcel.writeString(condition);
        parcel.writeByte((byte)(isFoil ? 1 : 0));
        parcel.writeByte((byte)(isSigned ? 1 : 0));
        parcel.writeByte((byte)(isAltered ? 1 : 0));
        parcel.writeByte((byte)(isPlayset ? 1 : 0));
    }

    public void readToParcel(Parcel parcel){

    	idArticle = parcel.readInt();
    	idProduct = parcel.readInt();
    	language = (Language) parcel.readParcelable(Language.class.getClassLoader());
    	comments = parcel.readString();
    	price = parcel.readFloat();
    	count = parcel.readInt();
    	seller = (Seller) parcel.readParcelable(Seller.class.getClassLoader());
    	condition = parcel.readString();
    	isFoil = parcel.readByte() != 0;
    	isSigned = parcel.readByte() != 0;
    	isAltered = parcel.readByte() != 0;
    	isPlayset = parcel.readByte() != 0;
    	
    }
}
