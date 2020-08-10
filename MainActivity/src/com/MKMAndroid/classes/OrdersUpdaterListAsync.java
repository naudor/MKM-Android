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
import android.widget.ListView;

import com.MKMAndroid.activities.OrdersActivity;

/**
 * Created by adunjo on 14/03/14.
 */
public class OrdersUpdaterListAsync extends AsyncTask<String, Integer, ArrayList<Order>> {

    private OrderHandler handler;
	//private ArrayList<Order> ordersFiltered;
	private ArrayList<Order> ordersAll;
    private XMLReader reader;
    private ActivityWithLoading activity;
    private ListView list;
    /*private int status;*/
    private int actor;
    private int scroll;
    private ProgressDialog dialog;


    public OrdersUpdaterListAsync(ActivityWithLoading activity, ProgressDialog dialog, int actor, ListView list, /*ArrayList<Order> ordersFiltered,*/ ArrayList<Order> ordersAll, int scroll)
    {
        this.activity = activity;
        this.dialog = dialog;
        this.list = list;
        this.actor = actor;
        this.scroll = scroll;
        //this.ordersFiltered = ordersFiltered;
        this.ordersAll = ordersAll;
    }
    
    protected void onPreExecute() {
    	if (activity!=null){
    		this.dialog.setMessage(activity.getStringResourceByName("loading"));
    	}
        this.dialog.show();
    }

    @Override
    protected ArrayList<Order> doInBackground(String... url) {
    	ArrayList newOrders = new ArrayList<Order>();

    	HttpGet request;
    	//String[] orderTypeCodes = new String[] {"1","2","4","8","32","128"};
        String uri = new String(url[0]);

        SAXParserFactory _f = SAXParserFactory.newInstance();
        SAXParser _p = null;

        HttpResponse response = null;
        HttpClient client = new DefaultHttpClient();

        request = new HttpGet(url[0]);

        try {
            _p = _f.newSAXParser();
            reader = _p.getXMLReader();
            response = client.execute(request);
            //handler = new OrderHandler(this.orders);
            handler = new OrderHandler(newOrders);
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


        return newOrders;

    }


    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(ArrayList<Order> orders)
    {
    	for(int x=0; x < orders.size();x++){
    		//this.ordersFiltered.add(orders.get(x));
    		this.ordersAll.add(orders.get(x));
    	}
    	
        if (orders != null && orders.size() > 0) {
        	AdapterOrders adaptadorOrders = new AdapterOrders((Activity)this.activity, this.ordersAll, this.actor);

            this.list.setAdapter(adaptadorOrders);
            adaptadorOrders.notifyDataSetChanged();
            this.list.setSelection(this.scroll+1);
        }

        if (this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
    }
}
