package com.MKMAndroid.classes;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.MKMAndroid.activities.ShoppingCartActivity;

/**
 * Created by adunjo on 14/03/14.
 */
public class ShoppingCartCheckOutAsync extends AsyncTask<String, Integer, ArrayList<ShoppingCart>> {

	private OrderHandler handler;
    private ShoppingCartHandler handlerShoppingCart;
    private ArrayList<Order> orders;
	private ArrayList<ShoppingCart> carts;
    private XMLReader reader;
    private ActivityWithLoading activity;
    ProgressDialog dialog;


    public ShoppingCartCheckOutAsync(ActivityWithLoading activity, ProgressDialog dialog)
    {
        this.activity = activity;
        this.dialog = dialog;
        carts = new ArrayList<ShoppingCart>();
    }
    
    protected void onPreExecute() {
    	if (activity!=null){
    		this.dialog.setMessage(activity.getStringResourceByName("loading"));
    	}
        this.dialog.show();
    }

    @Override
    protected ArrayList<ShoppingCart> doInBackground(String... url) {
    	this.orders = new ArrayList<Order>();
    	this.carts = new ArrayList<ShoppingCart>();
    	HttpGet request;
    	InputStream input;

        //String uri = new String(url[0]);

        //I'm going to read the status of the shopping carts
        SAXParserFactory _f = SAXParserFactory.newInstance();
        SAXParser _p = null;

        HttpResponse response = null;
        HttpClient client = new DefaultHttpClient();
        request = new HttpGet(url[0]+"/checkout");

        try {
            _p = _f.newSAXParser();
            reader = _p.getXMLReader();
            response = client.execute(request);
            handler = new OrderHandler(this.orders);
            reader.setContentHandler(handler);
           // if (response.getEntity()!=null) {
                input = response.getEntity().getContent();
                reader.parse(new InputSource(input));
            //}
                
            request = new HttpGet(url[0]);
            response = client.execute(request);
            handlerShoppingCart = new ShoppingCartHandler(this.carts);
            reader.setContentHandler(handlerShoppingCart);
            if (response.getEntity()!=null) {
                input = response.getEntity().getContent();
                reader.parse(new InputSource(input));
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.carts;

    }


    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(ArrayList<ShoppingCart> orders)
    {         
		 if (orders.size()==0 || orders.get(0).getArticles().size()==0){
	    	 orders = new ArrayList <ShoppingCart>();
		 }
	    	 
	     ((ActivityWithLoading) this.activity).callBack(orders);
	     
	     this.dialog.dismiss();
    }
}
