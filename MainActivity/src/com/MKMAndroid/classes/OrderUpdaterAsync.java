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
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.MKMAndroid.activities.OrderActivity;

/**
 * Created by adunjo on 14/03/14.
 */
public class OrderUpdaterAsync extends AsyncTask<String, Integer, ArrayList<Order>> {

    private OrderHandler handler;
	private ArrayList<Order> orders;
	private ArrayList<Product> products;
    private XMLReader reader;
    private ActivityWithLoading activity;
    /*private int status;*/
    private int actor;
    ProgressDialog dialog;


    public OrderUpdaterAsync(ActivityWithLoading activity, ProgressDialog dialog, int actor)
    {
        this.activity = activity;
        this.dialog = dialog;
        this.actor = actor;
    }
    
    protected void onPreExecute() {
    	if (activity!=null){
    		this.dialog.setMessage(activity.getStringResourceByName("loading"));
    	}
        this.dialog.show();
    }

    @Override
    protected ArrayList<Order> doInBackground(String... url) {
    	this.orders = new ArrayList<Order>();

        String uri = new String(url[0]);
        String[] tmp = uri.split("/");
        /*this.status = Integer.parseInt(tmp[tmp.length-1]);
        this.actor = Integer.parseInt(tmp[tmp.length-2]);*/

        //I'm going to read the status of the shopping carts
        SAXParserFactory _f = SAXParserFactory.newInstance();
        SAXParser _p = null;

        HttpResponse response = null;
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url[0]);

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
            
            //I need get the products of the articles to show on articles details
            ArrayList<Article> articles = this.orders.get(0).getArticles();
            ArrayList<Product> products = new ArrayList<Product>();
            
            for(int y=0; y < articles.size(); y++)
        	{
        		Article article = articles.get(y);
        		request = new HttpGet(generateURLProduct() + String.valueOf(article.getIdProduct()));
        		_p = _f.newSAXParser();
                reader = _p.getXMLReader();
                response = client.execute(request);
                ProductHandler handler = new ProductHandler(products);
                reader.setContentHandler(handler);
                if (response.getEntity()!=null) {
                    InputStream input = response.getEntity().getContent();
                    reader.parse(new InputSource(input));
                }
        	}

            this.orders.get(0).setProducts(products);
            
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
    	 Intent intent;
         intent = new Intent((Activity)this.activity, OrderActivity.class);
         if (orders.size()==0 || orders.get(0).getArticles().size()==0){
        	 intent.putExtra("order", new Order());
    	 }else{
    		 intent.putExtra("order", orders.get(0)); 
    	 }
         
         intent.putExtra("actor", this.actor);
         ((Activity) this.activity).startActivity(intent);
         
         this.dialog.dismiss();
         
    }
    
    private String generateURLProduct()
    {
    	String FILENAME = Constants.file_settings_name;
    	SharedPreferences settings = ((Activity) this.activity).getSharedPreferences(FILENAME, 0);
    	String keyMKM = settings.getString("apiKey",null);
    	String userMKM = settings.getString("userName",null);
    	
    	return Constants.domain + userMKM +"/" + keyMKM + "/product/";
    	
    	
    }
}
