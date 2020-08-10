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
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.widget.ListView;

import com.MKMAndroid.activities.MainActivity;
import com.MKMAndroid.activities.ProductsActivity;

/**
 * Created by adunjo on 14/03/14.
 */
public class ProductsUpdaterAsync extends AsyncTask<String, Integer, ArrayList<Product>> {

    private ArrayList<Product> products;
    private ListView list;
    private int scroll;
    private ActivityWithLoading activity;
    private MainActivity mActivity;
    private XMLReader reader;
    private ProductHandler handler;
    ProgressDialog dialog;


    public ProductsUpdaterAsync(ActivityWithLoading activity, ProgressDialog dialog,
                                ArrayList<Product> products, ListView list,int scroll)
    {
        this.activity = activity;
        this.dialog = dialog;
        this.products = products;
        this.list = list;
        this.scroll = scroll;
    }
    
    public ProductsUpdaterAsync(ProductsActivity activity, ProgressDialog dialog,
            ArrayList<Product> products)
	{
		this.activity = activity;
		this.dialog = dialog;
		this.products = products;
	}
    
    public ProductsUpdaterAsync(MainActivity activity, ProgressDialog dialog,
            ArrayList<Product> products)
	{
		this.mActivity = activity;
		this.dialog = dialog;
		this.products = products;
	}


    protected void onPreExecute() {
    	if (activity!=null){
    		this.dialog.setMessage(activity.getStringResourceByName("loading"));
    	}
    	else
    	{
    		this.dialog.setMessage(mActivity.getStringResourceByName("loading"));
    	}
        this.dialog.show();
    }

    @Override
    protected ArrayList<Product> doInBackground(String... url) {

        SAXParserFactory _f = SAXParserFactory.newInstance();
        SAXParser _p = null;

        HttpResponse response = null;
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url[0]);

        try {
            _p = _f.newSAXParser();
            reader = _p.getXMLReader();
            response = client.execute(request);
            handler = new ProductHandler(this.products);
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

        return this.products;

    }


    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(ArrayList<Product> totalProducts)
    {
    	if (this.list!=null){
	        if (totalProducts != null && totalProducts.size() > 0) {
	            AdapterProducts adaptadorProducts = new AdapterProducts((Activity)this.activity, totalProducts);
	
	            this.list.setAdapter(adaptadorProducts);
	            adaptadorProducts.notifyDataSetChanged();
	            this.list.setSelection(this.scroll);
	        }
	
	        if (this.dialog.isShowing()) {
	            this.dialog.dismiss();
	        }
    	}
    	else
    	{
    		//if the webservice return empty response, clean the ArrayList
    		if(totalProducts.size()==1 && totalProducts.get(0).getIdProduct()==0){
    			totalProducts.clear();
    		}

    		mActivity.callBack(totalProducts);
    	}
    }
}
