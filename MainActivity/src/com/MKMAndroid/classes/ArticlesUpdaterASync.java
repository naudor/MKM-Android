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
import android.os.AsyncTask;
import android.widget.ListView;

import com.MKMAndroid.activities.ProductsActivity;

/**
 * Created by adunjo on 14/03/14.
 */
public class ArticlesUpdaterASync extends AsyncTask<String, Integer, ArrayList<Article>> {

    private XMLReader reader;
    private ArticleHandler handler;
    private ProgressDialog dialog;
    private ListView list;
    private ArrayList<Article> articles;
    private ActivityWithLoading activity;
    private ProductsActivity pActivity;
    private int scroll;


    public ArticlesUpdaterASync(ActivityWithLoading activity, ProgressDialog dialog,
                                ArrayList<Article> articles, ListView list,int scroll)
    {
        this.activity = activity;
        this.dialog = dialog;
        this.articles = articles;
        this.list = list;
        this.scroll = scroll;
    }
    
    public ArticlesUpdaterASync(ProductsActivity activity, ProgressDialog dialog,
            ArrayList<Article> articles)
	{
		this.pActivity = activity;
		this.dialog = dialog;
		this.articles = articles;
	}

    protected void onPreExecute() {
    	if(activity!=null)
    	{
    		this.dialog.setMessage(activity.getStringResourceByName("loading"));
    	}
    	else
    	{
    		this.dialog.setMessage(pActivity.getStringResourceByName("loading"));
    	}
        this.dialog.show();
    }


    @Override
    protected ArrayList<Article> doInBackground(String... url) {
        SAXParserFactory _f = SAXParserFactory.newInstance();
        SAXParser _p = null;

        HttpResponse response = null;
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url[0]);

        try {
            _p = _f.newSAXParser();
            reader = _p.getXMLReader();
            response = client.execute(request);
            handler = new ArticleHandler(this.articles);
            reader.setContentHandler(handler);

            if (response.getEntity()!=null) {
                InputStream input = response.getEntity().getContent();
                reader.parse(new InputSource(input));
            }

            //reader.parse(new InputSource(response.getEntity().getContent()));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.articles;

    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(ArrayList<Article> totalArticles)
    {
    	if (this.list!=null){
	        if (totalArticles != null && totalArticles.size() > 0) {
	            AdapterArticles adaptadorArticles = new AdapterArticles((Activity)this.activity, totalArticles);
	
	            this.list.setAdapter(adaptadorArticles);
	            adaptadorArticles.notifyDataSetChanged();
	            this.list.setSelection(this.scroll+1);
	        }
	
	        if (this.dialog.isShowing()) {
	            this.dialog.dismiss();
	        }
    	}
    	else
    	{
    		pActivity.callBack(totalArticles);	
    	}
    }
}
