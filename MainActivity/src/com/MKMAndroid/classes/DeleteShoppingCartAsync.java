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
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.XmlAwareFormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.ListView;

/**
 * Created by adunjo on 14/03/14.
 */
public class DeleteShoppingCartAsync extends AsyncTask<String, Integer, ArrayList<ShoppingCart>> {

    private ShoppingCartHandler handler;
	private ArrayList<ShoppingCart> shoppingCarts;
    private XMLReader reader;
    private ActivityWithLoading activity;
    private ShoppingCartRequest request;
    private ListView list;
    ProgressDialog dialog;


    public DeleteShoppingCartAsync(ActivityWithLoading activity, ProgressDialog dialog)
    {
        this.activity = activity;
        this.dialog = dialog;
    }
    
    protected void onPreExecute() {
    	if (activity!=null){
    		this.dialog.setMessage(activity.getStringResourceByName("loading"));
    	}
        this.dialog.show();
    }

    @Override
    protected ArrayList<ShoppingCart> doInBackground(String... url) {
    	this.shoppingCarts = new ArrayList<ShoppingCart>();
    	
    	RestTemplate rt = new RestTemplate();
        rt.getMessageConverters().add(new StringHttpMessageConverter());
        rt.getMessageConverters().add(new XmlAwareFormHttpMessageConverter());

    	//I do the DELETE
        rt.delete(url[0]);
        
        
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
    	if (shoppingsCarts.size()==1 && shoppingsCarts.get(0).getArticleValue()==null){
    		shoppingsCarts.remove(0);
    	}
    	
		activity.callBack(shoppingsCarts);
		
		dialog.dismiss();
    }
}
