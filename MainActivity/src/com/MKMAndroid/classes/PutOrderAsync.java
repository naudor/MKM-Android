package com.MKMAndroid.classes;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
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

/**
 * Created by adunjo on 14/03/14.
 */
public class PutOrderAsync extends AsyncTask<String, Integer, ArrayList<Order>> {

    private OrderHandler handler;
	private ArrayList<Order> orders;
    private XMLReader reader;
    private ActivityWithLoading activity;
    private OrderRequest request;
    //private ListView list;
    ProgressDialog dialog;


    public PutOrderAsync(ActivityWithLoading activity, ProgressDialog dialog,
                                OrderRequest request)
    {
        this.activity = activity;
        this.dialog = dialog;
        this.request = request;
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
    	
    	/*RestTemplate rt = new RestTemplate();
        rt.getMessageConverters().add(new StringHttpMessageConverter());
        rt.getMessageConverters().add(new XmlAwareFormHttpMessageConverter());
        
        String xmlContentToSend = new String();
        xmlContentToSend += "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
        xmlContentToSend += "<request>";
    	xmlContentToSend += "<action>"+this.request.getAction()+"</action>";
    	
    	if(this.request.getReason()!=""){
    		xmlContentToSend +="<reason>"+this.request.getReason()+"</reason>";
    	}
    	if(this.request.getRelist()>0){
    		xmlContentToSend +="<relist>true</relist>";
    	}
    	else
    	{
    		xmlContentToSend +="<relist>false</relist>";
    	}
    	xmlContentToSend += "</relist>";
    	xmlContentToSend += "</request>";*/
    	
    	//I do the PUT
        //t.put(url[0],xmlContentToSend);
        
        
        //I'm going to read the status of the shopping carts
        SAXParserFactory _f = SAXParserFactory.newInstance();
        SAXParser _p = null;

        HttpResponse response = null;
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url[0]);
        
        HttpPost postRequest = new HttpPost(url[0]);
        HttpPut putRequest = new HttpPut(url[0]);
       
        
        String xmlContentToSend = new String();
        xmlContentToSend += "<request>";
    	xmlContentToSend += "<action>"+this.request.getAction()+"</action>";
    	
    	if(this.request.getReason()!=""){
    		xmlContentToSend +="<reason>"+this.request.getReason()+"</reason>";
    	}
    	if(this.request.getRelist()>0){
    		xmlContentToSend +="<relist>true</relist>";
    	}
    	/*else
    	{
    		xmlContentToSend +="<relist>false</relist>";
    	}*/
    	xmlContentToSend += "</request>";
    	putRequest.addHeader("Content-Type", "application/xml; charset=utf-8");

        try {
        	StringEntity entity = new StringEntity(xmlContentToSend);
        	putRequest.setEntity(entity);
        	HttpResponse responseFromPOST = client.execute(putRequest);
        	
        	
        	
            _p = _f.newSAXParser();
            reader = _p.getXMLReader();
            response = client.execute(request);
            handler = new OrderHandler(this.orders);
            reader.setContentHandler(handler);
            if (response.getEntity()!=null) {
                InputStream input = response.getEntity().getContent();
                reader.parse(new InputSource(input));
            }
            
            for(int x=0; x < this.orders.size(); x++)
            {
            	Order order = this.orders.get(x);
            	ArrayList<Article> articles = order.getArticles();
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
                 
                order.setProducts(products);
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
    	if (orders.size()==1 && orders.get(0).getArticleValue()==null){
    		orders.remove(0);
    	}
    	
		activity.callBack(orders);
		
		dialog.dismiss();
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
