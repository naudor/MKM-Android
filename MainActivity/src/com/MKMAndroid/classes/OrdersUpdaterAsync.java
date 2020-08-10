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

import com.MKMAndroid.activities.MainActivity;
import com.MKMAndroid.activities.OrdersActivity;
import com.MKMAndroid.activities.ShoppingCartActivity;

/**
 * Created by adunjo on 14/03/14.
 */
public class OrdersUpdaterAsync extends AsyncTask<String, Integer, ArrayList<Order>> {

    private OrderHandler handler;
	private ArrayList<Order> orders;
    private XMLReader reader;
    private ActivityWithLoading activity;
    private int orderTypeCode;
    private int actor;
    ProgressDialog dialog;


    public OrdersUpdaterAsync(ActivityWithLoading activity, ProgressDialog dialog, int actor, int orderTypeCode)
    {
        this.activity = activity;
        this.dialog = dialog;
        this.actor = actor;
        this.orderTypeCode = orderTypeCode;
        
    }
    
    protected void onPreExecute() {
    	if (activity!=null){
    		this.dialog.setMessage(activity.getStringResourceByName("loading"));
    	}
    	
    	if(!this.dialog.isShowing()){
    		this.dialog.show();
    	}
    }

    @Override
    protected ArrayList<Order> doInBackground(String... url) {
    	this.orders = new ArrayList<Order>();

    	HttpGet request;
    	

        SAXParserFactory _f = SAXParserFactory.newInstance();
        SAXParser _p = null;

        HttpResponse response = null;
        HttpClient client = new DefaultHttpClient();
        
       
        request = new HttpGet(url[0]);

        try {
            _p = _f.newSAXParser();
            reader = _p.getXMLReader();
            response = client.execute(request);
            handler = new OrderHandler(this.orders);
            reader.setContentHandler(handler);
            if (response.getEntity()!=null) {
                InputStream input = response.getEntity().getContent();
                reader.parse(new InputSource(input));
            }

            
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return this.orders;

    }


    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(ArrayList<Order> orders)
    {
    	ArrayList<Order> filtered = new ArrayList<Order>();
    	
    	//I want only the valid orders
    	for(int x=0; x < orders.size(); x++){
    		if(orders.get(x).getIdOrder()!=null){
    			filtered.add(orders.get(x));
    		}
    	}
    	 
    	Intent intent = new Intent((Activity)activity, OrdersActivity.class);
		intent.putExtra("actor", actor);
		intent.putExtra("orderTypeCode", orderTypeCode);
		intent.putExtra("orders", filtered);
		this.dialog.dismiss();
		((Activity)activity).startActivity(intent);
		
    }
}
