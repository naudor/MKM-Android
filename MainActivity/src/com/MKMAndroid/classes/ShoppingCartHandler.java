package com.MKMAndroid.classes;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Arnau on 22/03/2014.
 */
public class ShoppingCartHandler extends DefaultHandler {

    private ArrayList<ShoppingCart> shoppingCarts;
    private ShoppingCart shoppingCart;
    private ArrayList<Article> articles;
    private Article article;
    private Seller seller;
    private ShippingMethod shippingMethod;
    private Language language;
    private String currentElement;
    private int contadorPrice = 0;

   /* public ArrayList<ShoppingCart> getResult() {
    	if (shoppingCarts.size()==0 || shoppingCarts.get(0).getArticles().size()==0){
    		shoppingCarts = new ArrayList<ShoppingCart>();
        }
        return shoppingCarts;
    }*/

    public ShoppingCartHandler (ArrayList<ShoppingCart> shoppingCarts){
        this.shoppingCarts = shoppingCarts;
    }

    public ShoppingCartHandler(){

    }

    @Override
    public void startDocument() throws SAXException {
        if (this.shoppingCarts==null) {
        	shoppingCarts = new ArrayList<ShoppingCart>();
        }
    }

    @Override
    public void startElement( String aUri,
                              String aLocalName,
                              String aQName, Attributes aAttributes
    ) throws SAXException {
        currentElement = aQName;
        if ("article".equals(aQName)) {
            articles.add(article = new Article());
        } else if ("language".equals(aQName)) {
            article.setLanguage(language = new Language());
        } else if ("seller".equals(aQName)) {
            shoppingCart.setSeller(seller = new Seller());
        } else if ("shippingMethod".equals(aQName)) {
        	shoppingCart.setShippingMethod(shippingMethod = new ShippingMethod());
        } else if ("shoppingCart".equals(aQName)) {
        	shoppingCarts.add(shoppingCart = new ShoppingCart(articles = new ArrayList<Article>()));
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
            article.setIdProduct(Integer.parseInt(new String(aCh, aStart, aLength)));
        } else if (("idArticle".equals(currentElement))) {
            article.setIdArticle(Integer.parseInt(new String(aCh, aStart, aLength)));
        } else if ("idLanguage".equals(currentElement)) {
            language.setIdLanguage(Integer.parseInt(new String(aCh, aStart, aLength)));
        } else if ("languageName".equals(currentElement)) {
            language.setLanguageName(new String(aCh, aStart, aLength));
        } else if ("comments".equals(currentElement)) {
            article.setComments(new String(aCh, aStart, aLength));
        } else if ("price".equals(currentElement)) {
        	if (this.article.getPrice()==0){
        		article.setPrice(Float.parseFloat(new String(aCh, aStart, aLength)));
        	}
        	else
        	{
        		shippingMethod.setPrice(Float.parseFloat(new String(aCh, aStart, aLength)));
        		this.contadorPrice = 0;
        	}
        	
        } else if ("count".equals(currentElement)) {
            article.setCount(Integer.parseInt(new String(aCh, aStart, aLength)));
        } else if ("idUser".equals(currentElement)) {
            seller.setIdUser(Integer.parseInt(new String(aCh, aStart, aLength)));
        } else if ("username".equals(currentElement)) {
            seller.setUsername(new String(aCh, aStart, aLength));
        } else if ("country".equals(currentElement)) {
            seller.setCountry(new String(aCh, aStart, aLength));
        } else if ("isCommercial".equals(currentElement)) {
            seller.setCommercial(Boolean.parseBoolean(new String(aCh, aStart, aLength)));
        } else if ("riskGroup".equals(currentElement)) {
            seller.setRiskGroup(Integer.parseInt(new String(aCh, aStart, aLength)));
        } else if ("reputation".equals(currentElement)) {
            seller.setReputation(Integer.parseInt(new String(aCh, aStart, aLength)));
        } else if ("condition".equals(currentElement)) {
            article.setCondition(new String(aCh, aStart, aLength));
        } else if ("isFoil".equals(currentElement)) {
            article.setFoil(Boolean.parseBoolean(new String(aCh, aStart, aLength)));
        } else if ("isSignet".equals(currentElement)) {
            article.setSigned(Boolean.parseBoolean(new String(aCh, aStart, aLength)));
        } else if ("isAltered".equals(currentElement)) {
            article.setAltered(Boolean.parseBoolean(new String(aCh, aStart, aLength)));
        } else if ("isPlayset".equals(currentElement)) {
            article.setPlayset(Boolean.parseBoolean(new String(aCh, aStart, aLength)));
        } else if ("name".equals(currentElement)) {
        	shippingMethod.setName(new String(aCh, aStart, aLength));
       /* } else if ("price".equals(currentElement)) {
        	article.setPrice(Float.parseFloat(new String(aCh, aStart, aLength)));	*/
        } else if ("articleValue".equals(currentElement)) {
        	shoppingCart.setArticleValue(Float.parseFloat(new String(aCh, aStart, aLength)));
        } else if ("totalValue".equals(currentElement)) {
        	shoppingCart.setTotalValue(Float.parseFloat(new String(aCh, aStart, aLength)));
        }
    }
}
