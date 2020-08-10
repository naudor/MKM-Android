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

import android.app.ProgressDialog;
import android.os.AsyncTask;

/**
 * Created by adunjo on 14/03/14.
 */
public class ProductsUpdater extends AsyncTask<String, Integer, ArrayList<Product>> {

    private XMLReader reader;
    private ProductHandler handler;
    ProgressDialog dialog;


    public ProductsUpdater(ProgressDialog dialog)
    {
        this.dialog = dialog;
    }

    public ProductsUpdater()
    {

    }

    protected void onPreExecute() {
        if (this.dialog!=null) {
            this.dialog.setMessage("Progress start");
            this.dialog.show();
        }
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
            handler = new ProductHandler();
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

        return handler.getResult();

    }


    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(ArrayList<Product> newProducts)
    {
        if (this.dialog!=null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
    }

}
