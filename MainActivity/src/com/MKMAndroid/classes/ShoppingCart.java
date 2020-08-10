package com.MKMAndroid.classes;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class ShoppingCart implements Parcelable {
	private Seller seller;
	private ShippingMethod shippingMethod;
	private ArrayList<Article> articles;
	private ArrayList<Product> products;
	private Float articleValue;
	private Float totalValue;
	
	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	public ShippingMethod getShippingMethod() {
		return shippingMethod;
	}
	public void setShippingMethod(ShippingMethod shippingMethod) {
		this.shippingMethod = shippingMethod;
	}
	public ArrayList<Article> getArticles() {
		return articles;
	}
	public void setArticle(ArrayList<Article> articles) {
		this.articles = articles;
	}
	public Float getArticleValue() {
		return articleValue;
	}
	public void setArticleValue(Float articleValue) {
		this.articleValue = articleValue;
	}
	public Float getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(Float totalValue) {
		this.totalValue = totalValue;
	}
	
	public ShoppingCart(Parcel parcel) {
        readToParcel(parcel);
    }
	
	public ShoppingCart() {
	}
	
	public ShoppingCart(ArrayList<Article> articles) {
		this.articles = articles;
	}
	
	public ArrayList<Product> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	
	public static final Parcelable.Creator<ShoppingCart> CREATOR = new Parcelable.Creator() {

        public ShoppingCart createFromParcel(Parcel parcel) {
            return new ShoppingCart(parcel);
        }

        public ShoppingCart[] newArray(int size) {
            return new ShoppingCart[size];
        }


    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    	parcel.writeParcelable(seller, 1);
    	parcel.writeParcelable(shippingMethod, 1);
    	parcel.writeList(articles);
    	parcel.writeList(products);
        parcel.writeFloat(articleValue);
        parcel.writeFloat(totalValue);
    }

    public void readToParcel(Parcel parcel){
    	
    	seller = (Seller) parcel.readParcelable(Seller.class.getClassLoader());
    	shippingMethod = (ShippingMethod) parcel.readParcelable(ShippingMethod.class.getClassLoader());
    	
    	ArrayList<Article> prova = new ArrayList<Article>();
        parcel.readList(prova, Article.class.getClassLoader());
        articles = prova;
        
        ArrayList<Product> provaP = new ArrayList<Product>();
        parcel.readList(provaP, Product.class.getClassLoader());
        products = provaP;
        
    	articleValue = parcel.readFloat();
    	totalValue = parcel.readFloat();
    }

}
