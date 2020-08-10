package com.MKMAndroid.classes;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by adunjo on 6/02/14.
 */
public class Product implements Parcelable {
    private int idProduct;
    private int idMetaproduct;
    private ArrayList<Name> names;
    private PriceGuide priceGuide;
    private String imatge;
    private String expansion;
    private String rarity;

    private static final String ns = null;
    private static final String domain = "https://www.mkmapi.eu/";

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator() {

        public Product createFromParcel(Parcel parcel) {
            return new Product(parcel);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }


    };

    public Product() {
    }

    public void Product(int idProduct, int idMetaproduct, ArrayList<Name> names, PriceGuide priceGuide, String imatge, String expansion, String rarity) {
        this.idProduct = idProduct;
        this.idMetaproduct = idMetaproduct;
        this.names = names;
        this.priceGuide = priceGuide;
        this.imatge = imatge;
        this.expansion = expansion;
        this.rarity = rarity;
    }

    public Product(Parcel parcel){
        readToParcel(parcel);
    }

    public int getIdProduct() {
        return idProduct;
    }

    public int getIdMetaproduct() {
        return idMetaproduct;
    }

    public ArrayList<Name> getNames() {
        return names;
    }

    public PriceGuide getPriceGuide() {
        return priceGuide;
    }

    public String getImatge() {
        return imatge;
    }

    public String getExpansion() {
        return expansion;
    }

    public String getRarity() {
        return rarity;
    }

    public static String getNs() {
        return ns;
    }

    public String getName()
    {
        //Afago la posicio 0 per defecte que es l'angles pero s'hauria
        //de mirar la posicio X depenent de les preferencies
        //de l'usuari
        return this.names.get(0).getProductName();
    }


    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public void setIdMetaproduct(int idMetaproduct) {
        this.idMetaproduct = idMetaproduct;
    }

    public void setNames(ArrayList<Name> names) {
        this.names = names;
    }

    public void setPriceGuide(PriceGuide priceGuide) {
        this.priceGuide = priceGuide;
    }

    public void setImatge(String imatge) {
        this.imatge = imatge;
    }

    public void setExpansion(String expansion) {
        this.expansion = expansion;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idProduct);
        parcel.writeInt(idMetaproduct);
        parcel.writeList(names);
        parcel.writeParcelable(priceGuide,1);
        String cutValue = new String(imatge);
        if (cutValue.substring(0,2).compareTo("./")==0) {
            parcel.writeString(this.domain + cutValue.substring(2));
        }
        else
        {
            parcel.writeString(cutValue);
        }
        parcel.writeString(expansion);
        parcel.writeString(rarity);
    }

    public void readToParcel(Parcel parcel){

        idProduct = parcel.readInt();
        idMetaproduct = parcel.readInt();
        ArrayList<Name> prova = new ArrayList<Name>();
        parcel.readList(prova, Name.class.getClassLoader());
        names = prova;
        priceGuide = (PriceGuide) parcel.readParcelable(PriceGuide.class.getClassLoader());
        imatge = parcel.readString();
        expansion = parcel.readString();
        rarity = parcel.readString();
    }

}
