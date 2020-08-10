package com.MKMAndroid.classes;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Arnau on 22/03/2014.
 */
public class ProductHandler  extends DefaultHandler {

    private ArrayList<Product> products;
    private Product product;
    private PriceGuide priceGuide;
    private ArrayList<Name> names;
    private Name name;
    private String currentElement;

    public ArrayList<Product> getResult() {
        //If the card doesn't exist I will have a Arraylist with one element,
        // but this element is empty
        if (products.get(0).getIdProduct()==0){
            products = new ArrayList<Product>();
        }
        return products;
    }

    public ProductHandler (ArrayList<Product> products){
        this.products = products;
    }

    public ProductHandler(){

    }

    @Override
    public void startDocument() throws SAXException {
        if (this.products==null) {
            products = new ArrayList<Product>();
        }
    }

    @Override
    public void startElement( String aUri,
                              String aLocalName,
                              String aQName, Attributes aAttributes
    ) throws SAXException {
        currentElement = aQName;
        //currentElement.isEmpty();
        //currentElement.endsWith("/");
        if ("product".equals(aQName)) {
            products.add(product = new Product());
            product.setNames(names = new ArrayList<Name>());
        } else if ("priceGuide".equals(aQName)) {
            product.setPriceGuide(priceGuide = new PriceGuide());
        } else if ("name".equals(aQName)) {
            names.add(name = new Name());
        }
    }

    @Override
    public void endElement(
            String aUri, String aLocalName, String aQName
    ) throws SAXException {
        currentElement = null;
    }

    @Override
    public void characters(char[] aCh, int aStart, int aLength)
            throws SAXException {
        if ("idProduct".equals(currentElement)) {
            product.setIdProduct(Integer.parseInt(new String(aCh, aStart, aLength)));
        } else if (("idMetaproduct".equals(currentElement))) {
            product.setIdMetaproduct(Integer.parseInt(new String(aCh, aStart, aLength)));
        } else if ("idLanguage".equals(currentElement)) {
            name.setIdLanguage(Integer.parseInt(new String(aCh, aStart, aLength)));
        } else if ("languageName".equals(currentElement)) {
            name.setLanguageName(new String(aCh, aStart, aLength));
        } else if ("productName".equals(currentElement)) {
            name.setProductName(new String(aCh, aStart, aLength));
        } else if ("image".equals(currentElement)) {
            product.setImatge(new String(aCh, aStart, aLength));
        } else if ("expansion".equals(currentElement)) {
            product.setExpansion(new String(aCh, aStart, aLength));
        } else if ("rarity".equals(currentElement)) {
            product.setRarity(new String(aCh, aStart, aLength));
        } else if ("SELL".equals(currentElement)) {
            priceGuide.setSell(Float.parseFloat(new String(aCh, aStart, aLength)));
        } else if ("LOW".equals(currentElement)) {
            priceGuide.setLow(Float.parseFloat(new String(aCh, aStart, aLength)));
        } else if ("AVG".equals(currentElement)) {
            priceGuide.setAvg(Float.parseFloat(new String(aCh, aStart, aLength)));
        }
    }
}
