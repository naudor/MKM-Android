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

import com.MKMAndroid.activities.ShoppingCartActivity;

/**
 * Created by adunjo on 14/03/14.
 */
public class ShoppingCartUpdaterAsync extends AsyncTask<String, Integer, ArrayList<ShoppingCart>> {

    private ShoppingCartHandler handler;
	private ArrayList<ShoppingCart> shoppingCarts;
    private XMLReader reader;
    private ActivityWithLoading activity;
    ProgressDialog dialog;


    public ShoppingCartUpdaterAsync(ActivityWithLoading activity, ProgressDialog dialog)
    {
        this.activity = activity;
        this.dialog = dialog;
    }
    
    protected void onPreExecute() {
    	if (activity!=null){
    		this.dialog.setMessage(activity.getStringResourceByName("loading"));
    		this.dialog.show();
    	}
        
    }

    @Override
    protected ArrayList<ShoppingCart> doInBackground(String... url) {
    	this.shoppingCarts = new ArrayList<ShoppingCart>();

        String uri = new String(url[0]);

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
            handler = new ShoppingCartHandler(this.shoppingCarts);
            reader.setContentHandler(handler);
            if (response.getEntity()!=null) {
                InputStream input = response.getEntity().getContent();
                reader.parse(new InputSource(input));
            }
            
            for(int x=0; x < this.shoppingCarts.size(); x++)
            {
            	ShoppingCart cart = this.shoppingCarts.get(x);
            	ArrayList<Article> articles = cart.getArticles();
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
                 
                cart.setProducts(products);
            }
            
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.shoppingCarts;

    }


    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(ArrayList<ShoppingCart> shoppingsCarts)
    {
    	 Intent intent;
         intent = new Intent((Activity)this.activity, ShoppingCartActivity.class);
         if (shoppingCarts.size()==0 || shoppingCarts.get(0).getArticles().size()==0){
        	 intent.putExtra("shoppingCarts", new ArrayList <ShoppingCart>());
    	 }else{
    		 intent.putExtra("shoppingCarts", shoppingsCarts); 
    	 }
         
         
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
