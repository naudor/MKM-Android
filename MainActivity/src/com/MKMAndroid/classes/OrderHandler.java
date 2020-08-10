package com.MKMAndroid.classes;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Arnau on 22/03/2014.
 */
public class OrderHandler  extends DefaultHandler {

	private UserInfo sellerInfo;
	private UserInfo buyerInfo;
	private State state;
	private ShippingMethod shippingMethod;
	private Evaluation evaluation;
	private ArrayList<Article> articles;
    private String currentElement;
    private ArrayList<Order> orders;
    
    private Order order;
    private Article article;
    private Seller sellerBasic;
    private Seller buyerBasic;
    private Seller userBasic;
    private PersonName name;
    private Address address;
    private Language language;
    private Seller seller;

    public OrderHandler (ArrayList<Order> products){
        this.orders = products;
    }

    public OrderHandler(){

    }

    @Override
    public void startDocument() throws SAXException {
        if (this.orders==null) {
        	orders = new ArrayList<Order>();
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
        if ("order".equals(aQName) || "shipment".equals(aQName)) {
        	orders.add(order = new Order());
            order.setArticles(articles = new ArrayList<Article>());
        } else if ("seller".equals(aQName)) {
        	order.setSellerInfo(sellerInfo = new UserInfo());
        	sellerInfo.setBasicInfo(sellerBasic = new Seller());
        	sellerInfo.setName(name = new PersonName());
        	sellerInfo.setAddress(address = new Address());
        	
        	userBasic = sellerBasic;
        } else if ("buyer".equals(aQName)) {
        	order.setBuyerInfo(buyerInfo = new UserInfo());
        	buyerInfo.setBasicInfo(buyerBasic = new Seller());
        	buyerInfo.setName(name = new PersonName());
        	buyerInfo.setAddress(address = new Address());
        	
        	userBasic = buyerBasic;
        } else if ("state".equals(aQName)) {
        	if(order.getState()==null){
        		order.setState(state = new State());
        	}
        } else if ("shippingMethod".equals(aQName)) {
        	order.setShippingMethod(shippingMethod = new ShippingMethod());
        } else if ("evaluation".equals(aQName)) {
        	order.setEvaluation(evaluation = new Evaluation());
        } else if ("shippingAddress".equals(aQName)) {
        	order.setAddress(address = new Address());
        } else if ("article".equals(aQName)) {
        	order.getArticles().add(article = new Article());
        	article.setLanguage(language = new Language());
        	article.setSeller(seller = new Seller());
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
        if ("idOrder".equals(currentElement)) {
            order.setIdOrder(Integer.parseInt(new String(aCh, aStart, aLength)));
        } else if (("idUser".equals(currentElement))) {
        	userBasic.setIdUser(Integer.parseInt(new String(aCh, aStart, aLength)));
        } else if (("username".equals(currentElement))) {
        	userBasic.setUsername(new String(aCh, aStart, aLength));
        } else if (("country".equals(currentElement))) {

        	if (sellerBasic.getCountry()==null)
        	{
        		userBasic.setCountry(new String(aCh, aStart, aLength));
        	}
        	else if (sellerBasic.getCountry()!=null && sellerInfo.getAddress().getCountry()==null)
        	{
        		address.setCountry(new String(aCh, aStart, aLength));
        	}
        	else if(buyerBasic.getCountry()==null && buyerInfo.getAddress().getCountry()==null)
        	{
        		userBasic.setCountry(new String(aCh, aStart, aLength));
        	}
        	else if (buyerBasic.getCountry()!=null && buyerInfo.getAddress().getCountry()==null)
        	{
        		address.setCountry(new String(aCh, aStart, aLength));
        	}
        	else if(buyerBasic.getCountry()!=null && buyerInfo.getAddress().getCountry()!=null)
        	{
        		address.setCountry(new String(aCh, aStart, aLength));
        	}

        } else if (("isCommercial".equals(currentElement))) {
        	userBasic.setCommercial(Boolean.parseBoolean(new String(aCh, aStart, aLength)));
        } else if (("riskGroup".equals(currentElement))) {
        	userBasic.setRiskGroup(Integer.parseInt(new String(aCh, aStart, aLength)));
        } else if (("reputation".equals(currentElement))) {
        	userBasic.setReputation(Integer.parseInt(new String(aCh, aStart, aLength)));
        } else if (("firstName".equals(currentElement))) {
        	name.setFirstName(new String(aCh, aStart, aLength));
        } else if (("lastName".equals(currentElement))) {
        	name.setLastName(new String(aCh, aStart, aLength));
        } else if (("name".equals(currentElement))) {
        	
        	if (sellerInfo.getName().getFirstName()!=null)
        	{
        		if (sellerInfo.getAddress().getFullName()==null)
        		{
        			address.setFullName(new String(aCh, aStart, aLength));
        			
        		}
        		else if (	sellerInfo.getAddress().getFullName()!=null
        					&& buyerInfo.getName().getFirstName()!=null
        					&& buyerInfo.getAddress().getFullName()==null)
        		{
        			address.setFullName(new String(aCh, aStart, aLength));	
        		}
        		else if(	sellerInfo.getAddress().getFullName()!=null 
        					&& buyerInfo.getAddress().getFullName()!=null
        					&& shippingMethod.getName()==null)
        		{
        			shippingMethod.setName(new String(aCh, aStart, aLength));
        		}
        		else if(	sellerInfo.getAddress().getFullName()!=null 
    						&& buyerInfo.getAddress().getFullName()!=null
    						&& shippingMethod.getName()!=null
    						&& order!=null && order.getAddress()!=null && order.getAddress().getFullName()==null)
        		{
        			order.getAddress().setFullName(new String(aCh, aStart, aLength));
        		}
        	}
        } else if (("extra".equals(currentElement))) {
        	address.setExtra(new String(aCh, aStart, aLength));    
        } else if (("street".equals(currentElement))) {
        	address.setStreet(new String(aCh, aStart, aLength));    
        } else if (("zip".equals(currentElement))) {
        	address.setZip(new String(aCh, aStart, aLength));    
        } else if (("city".equals(currentElement))) {
        	address.setCity(new String(aCh, aStart, aLength));    
        } else if (("country".equals(currentElement))) {
        	address.setCountry(new String(aCh, aStart, aLength));    
        } else if (("state".equals(currentElement)) && aLength>0) {
        	state.setState(new String(aCh, aStart, aLength));    
        } else if (("dateBought".equals(currentElement))) {
        	state.setDateBought(new String(aCh, aStart, aLength));    
        } else if (("datePaid".equals(currentElement))) {
        	state.setDatePaid(new String(aCh, aStart, aLength));    
        } else if (("price".equals(currentElement))) {
        	if(order.getArticles().size() == 0){
        		shippingMethod.setPrice(Float.parseFloat(new String(aCh, aStart, aLength)));
        	}else{
        		article.setPrice(Float.parseFloat(new String(aCh, aStart, aLength)));
        	}
        } else if (("overall".equals(currentElement))) {
        	evaluation.setOverall(new String(aCh, aStart, aLength));    
        } else if (("articles".equals(currentElement))) {
        	if(evaluation!=null){
        		evaluation.setArticles(new String(aCh, aStart, aLength));
        	}
        	    
        } else if (("packaging".equals(currentElement))) {
        	evaluation.setPackaging(new String(aCh, aStart, aLength));    
        } else if (("speed".equals(currentElement))) {
        	evaluation.setSpeed(new String(aCh, aStart, aLength));    
        } else if (("comments".equals(currentElement))) {
        	if(order.getArticles().size() == 0){
        		evaluation.setSpeed(new String(aCh, aStart, aLength));
        	}else{
        		article.setComments(new String(aCh, aStart, aLength));
        	}
        } else if (("idArticle".equals(currentElement))) {
        	article.setIdArticle(Integer.parseInt(new String(aCh, aStart, aLength)));   
        } else if (("idProduct".equals(currentElement))) {
        	article.setIdProduct(Integer.parseInt(new String(aCh, aStart, aLength)));   
        } else if (("idProduct".equals(currentElement))) {
        	article.setIdProduct(Integer.parseInt(new String(aCh, aStart, aLength)));   
        } else if (("idLanguage".equals(currentElement))) {
        	language.setIdLanguage(Integer.parseInt(new String(aCh, aStart, aLength)));   
        } else if (("languageName".equals(currentElement))) {
        	language.setLanguageName(new String(aCh, aStart, aLength));   
        } else if (("count".equals(currentElement))) {
        	article.setCount(Integer.parseInt(new String(aCh, aStart, aLength))); 
        } else if (("condition".equals(currentElement))) {
        	article.setCondition(new String(aCh, aStart, aLength)); 
        } else if (("isFoil".equals(currentElement))) {
        	article.setFoil(Boolean.parseBoolean(new String(aCh, aStart, aLength))); 
        } else if (("isSigned".equals(currentElement))) {
        	article.setSigned(Boolean.parseBoolean(new String(aCh, aStart, aLength))); 
        } else if (("isAltered".equals(currentElement))) {
        	article.setAltered(Boolean.parseBoolean(new String(aCh, aStart, aLength))); 
        } else if (("isPlayset".equals(currentElement))) {
        	article.setPlayset(Boolean.parseBoolean(new String(aCh, aStart, aLength))); 
        } else if (("articleValue".equals(currentElement))) {
        	order.setArticleValue(Float.parseFloat(new String(aCh, aStart, aLength))); 
        } else if (("totalValue".equals(currentElement))) {
        	order.setTotalValue(Float.parseFloat(new String(aCh, aStart, aLength))); 
        } else if (("dateSent".equals(currentElement))) {
        	state.setDateSent(new String(aCh, aStart, aLength)); 
        } else if (("dateReceived".equals(currentElement))) {
        	state.setDateReceived(new String(aCh, aStart, aLength)); 
        } else if (("dateCancelled".equals(currentElement))){
        	state.setDateCancelled(new String(aCh, aStart, aLength));
        }
    }
}
