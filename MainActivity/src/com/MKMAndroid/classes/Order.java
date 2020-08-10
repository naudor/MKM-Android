package com.MKMAndroid.classes;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable {
	private Integer idOrder;
	private UserInfo sellerInfo;
	private UserInfo buyerInfo;
	private State state;
	private ShippingMethod shippingMethod;
	private Evaluation evaluation;
	private ArrayList<Article> articles;
	private Float articleValue;
	private Float totalValue;
	private Address address;
	private ArrayList<Product> products;
	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Integer getIdOrder() {
		return idOrder;
	}
	public void setIdOrder(Integer idOrder) {
		this.idOrder = idOrder;
	}
	public UserInfo getSellerInfo() {
		return sellerInfo;
	}
	public void setSellerInfo(UserInfo sellerInfo) {
		this.sellerInfo = sellerInfo;
	}
	public UserInfo getBuyerInfo() {
		return buyerInfo;
	}
	public void setBuyerInfo(UserInfo buyerInfo) {
		this.buyerInfo = buyerInfo;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public ShippingMethod getShippingMethod() {
		return shippingMethod;
	}
	public void setShippingMethod(ShippingMethod shippingMethod) {
		this.shippingMethod = shippingMethod;
	}
	public Evaluation getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}
	public ArrayList<Article> getArticles() {
		return articles;
	}
	public void setArticles(ArrayList<Article> articles) {
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
	
	public void setProducts(ArrayList<Product> products){
		this.products = products;
	}
	
	public ArrayList<Product> getProducts(){
		return this.products;
	}
	
	
	public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator() {

        public Order createFromParcel(Parcel parcel) {
            return new Order(parcel);
        }

        public Order[] newArray(int size) {
            return new Order[size];
        }


    };
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public Order(Parcel parcel){
    	readToParcel(parcel);
    }
    
    public Order(){
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int i) { 
    	parcel.writeInt(idOrder);
    	parcel.writeParcelable(sellerInfo, 1);
    	parcel.writeParcelable(buyerInfo, 1);
    	parcel.writeParcelable(state, 1);
    	parcel.writeParcelable(shippingMethod, 1);
    	parcel.writeParcelable(evaluation, 1);
    	parcel.writeList(articles);
    	parcel.writeFloat(articleValue);
    	parcel.writeFloat(totalValue);
    	parcel.writeParcelable(address, 1);
    	parcel.writeList(products);
    }

    public void readToParcel(Parcel parcel){
    	idOrder = parcel.readInt();
    	sellerInfo =(UserInfo) parcel.readParcelable(UserInfo.class.getClassLoader());
    	buyerInfo =(UserInfo) parcel.readParcelable(UserInfo.class.getClassLoader());
    	state =(State) parcel.readParcelable(State.class.getClassLoader());
    	shippingMethod =(ShippingMethod) parcel.readParcelable(ShippingMethod.class.getClassLoader());
    	evaluation =(Evaluation) parcel.readParcelable(Evaluation.class.getClassLoader());
    	ArrayList<Article> prova = new ArrayList<Article>();
        parcel.readList(prova, Article.class.getClassLoader());
        articles = prova;
        articleValue = parcel.readFloat();
        totalValue = parcel.readFloat();
        address = (Address) parcel.readParcelable(Address.class.getClassLoader());
        ArrayList<Product> prova2 = new ArrayList<Product>();
        parcel.readList(prova2, Product.class.getClassLoader());
        products = prova2;
    }
}
