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
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListView;

import com.MKMAndroid.activities.MainActivity;
import com.MKMAndroid.activities.ProductsActivity;
import com.MKMAndroid.fragments.SettingsFragment;

/**
 * Created by adunjo on 14/03/14.
 */
public class CheckIdentificationAsync extends AsyncTask<String, Integer, ArrayList<Product>> {

    private ActivityWithLoading activity;
    private XMLReader reader;
    private ProductHandler handler;
    private SettingsFragment fragment; 
    private ProgressDialog dialog;


    public CheckIdentificationAsync(ActivityWithLoading activity, SettingsFragment fragment)
    {
        this.activity = activity;
        this.dialog = activity.getDialog();
        this.fragment = fragment;

    }
    


    protected void onPreExecute() {
    	if (activity!=null){
    		this.dialog.setMessage(activity.getStringResourceByName("loading"));
    		 this.dialog.show();
    	}
    	
       
    }

    @Override
    protected ArrayList<Product> doInBackground(String... url) {

    	ArrayList<Product> products = new ArrayList<Product>();
    	
        SAXParserFactory _f = SAXParserFactory.newInstance();
        SAXParser _p = null;

        HttpResponse response = null;
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url[0]);

        try {
            _p = _f.newSAXParser();
            reader = _p.getXMLReader();
            response = client.execute(request);
            handler = new ProductHandler(products);
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

        return products;

    }


    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(ArrayList<Product> totalProducts)
    {
    	
		//if the webservice return empty response, clean the ArrayList
		if(totalProducts.size()==1 && totalProducts.get(0).getIdProduct()==0){
			totalProducts.clear();
		}

		if(totalProducts.size()>0) fragment.savePreferences(true);
		else fragment.savePreferences(false);
    	
		
		this.dialog.dismiss();
    }

}
