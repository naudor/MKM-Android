package com.MKMAndroid.classes;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.MKMAndroid.app.R;

/**
 * Created by adunjo on 5/03/14.
 */

public class AdapterOrders extends BaseAdapter {

    protected ArrayList<Order> items;
    private LayoutInflater mInflater;
    private Activity activity;
    private int actor;


    public AdapterOrders(Activity activity, ArrayList<Order> items, int actor) {
        this.items = items;
        this.activity = activity;
        this.actor = actor;
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public AdapterOrders(LayoutInflater inflater, ArrayList<Order> items) {
        this.items = items;
        mInflater = inflater;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


    	Order order = items.get(position);
    	Seller basicInfo = null;
    	
        //ImageView nextImage = null;
        /////////////////////////////////////////////////////
        ViewHolder holder = null;
        View item = convertView;

        if (item == null || !( item.getTag() instanceof ViewHolder)) {
            item = mInflater.inflate(R.layout.list_item_order, null);
            holder = new ViewHolder();

            holder.actorLabel = (TextView)item.findViewById(R.id.textActorLabel);
            holder.userName = (TextView)item.findViewById(R.id.textViewSellerValue);
            holder.sellerReputation = (ImageView)item.findViewById(R.id.imageReputationSeller);
            holder.sellerCountry = (ImageView)item.findViewById(R.id.imageCountrySeller);
            
            holder.numArticle = (TextView)item.findViewById(R.id.textViewNumArticle);
            holder.boughtDateLabel = (TextView)item.findViewById(R.id.textViewBoughtDateLabel);
            holder.boughtDate = (TextView)item.findViewById(R.id.textViewBoughtDateValue);
            holder.paidDateLabel = (TextView)item.findViewById(R.id.textViewPaidDateLabel);
            holder.paidDate = (TextView)item.findViewById(R.id.textViewPaidDateValue);
            holder.sentDateLabel = (TextView)item.findViewById(R.id.textViewSentDateLabel);
            holder.sentDate = (TextView)item.findViewById(R.id.textViewSentDateValue);
            holder.receivedDateLabel = (TextView)item.findViewById(R.id.textViewReceivedDateLabel);
            holder.receivedDate = (TextView)item.findViewById(R.id.textViewArrivedDateValue);
            holder.cancelledDate = (TextView)item.findViewById(R.id.textViewCancelledDateValue);
            //holder.itemValue = (TextView)item.findViewById(R.id.textViewItemValue);
            //holder.shippingCost = (TextView)item.findViewById(R.id.textViewShippingCost);
            //holder.descriptionShippingMethod = (TextView)item.findViewById(R.id.textViewDescriptionShippingMethod);
            holder.totalCost = (TextView)item.findViewById(R.id.textViewTotalCost);
            
            item.setTag(holder);

        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        
        //I get the basic information of the another person
        if (order.getSellerInfo()!=null && actor==2) {
        	basicInfo = order.getSellerInfo().getBasicInfo();   
        }
        
        if (order.getBuyerInfo()!=null && actor==1) {
        	basicInfo = order.getBuyerInfo().getBasicInfo();
        }
        
        holder.userName.setText(basicInfo.getUsername());
        
        if(basicInfo!=null){
	        if (basicInfo.getCountry().equals("PT")) {
	            holder.sellerCountry.setImageResource(R.drawable.portugal);
	        } else if (basicInfo.getCountry().equals("ES")) {
	            holder.sellerCountry.setImageResource(R.drawable.espanya);
	        } else if (basicInfo.getCountry().equals("GB")) {
	            holder.sellerCountry.setImageResource(R.drawable.anglaterra);
	        } else if (basicInfo.getCountry().equals("FR")) {
	            holder.sellerCountry.setImageResource(R.drawable.francia);
	        } else if (basicInfo.getCountry().equals("D")) {
	            holder.sellerCountry.setImageResource(R.drawable.alemania);
	        }else if (basicInfo.getCountry().equals("DK")){
	            holder.sellerCountry.setImageResource(R.drawable.dinamarca);
	        } else if (basicInfo.getCountry().equals("AT")) {
	            holder.sellerCountry.setImageResource(R.drawable.austria);
	        } else if (basicInfo.getCountry().equals("CH")) {
	            holder.sellerCountry.setImageResource(R.drawable.suisa);
	        } else if (basicInfo.getCountry().equals("SI")) {
	            holder.sellerCountry.setImageResource(R.drawable.eslovenia);
	        } else if (basicInfo.getCountry().equals("BE")) {
	            holder.sellerCountry.setImageResource(R.drawable.belgica);
	        } else if (basicInfo.getCountry().equals("FI")) {
	            holder.sellerCountry.setImageResource(R.drawable.finlandia);
	        } else if (basicInfo.getCountry().equals("NL")) {
	            holder.sellerCountry.setImageResource(R.drawable.holanda);
	        } else if (basicInfo.getCountry().equals("PL")) {
	            holder.sellerCountry.setImageResource(R.drawable.polonia);
	        } else if (basicInfo.getCountry().equals("CZ")) {
	            holder.sellerCountry.setImageResource(R.drawable.republicatxeca);
	        } else if (basicInfo.getCountry().equals("GR")) {
	            holder.sellerCountry.setImageResource(R.drawable.grecia);
	        }else if (basicInfo.getCountry().equals("IT")) {
	        	holder.sellerCountry.setImageResource(R.drawable.italia);
	        }
	        
	        if(basicInfo.getReputation()==1){
	            holder.sellerReputation.setImageResource(R.drawable.reputation1);
	        }else if(basicInfo.getReputation()==2){
	            holder.sellerReputation.setImageResource(R.drawable.reputation2);
	        }else if(basicInfo.getReputation()==3) {
	            holder.sellerReputation.setImageResource(R.drawable.reputation3);
	        }else if(basicInfo.getReputation()==4) {
	        	holder.sellerReputation.setImageResource(R.drawable.reputation0);
	        }else if(basicInfo.getReputation()==0) {
	        	if(basicInfo.getRiskGroup()==1){
	        		holder.sellerReputation.setImageResource(R.drawable.no_reputation);
	        	}else{
	        		holder.sellerReputation.setImageResource(R.drawable.reputation0);
	        	}
	        }
        }
        
        ArrayList<Article> articles = order.getArticles();
        int numArticles = 0;
        
        for(int x=0; x < articles.size(); x++){
        	numArticles = numArticles + articles.get(x).getCount();
        }
        
        if (actor==1) holder.actorLabel.setText(getStringResourceByName("buyer_label"));
        if (actor==2) holder.actorLabel.setText(getStringResourceByName("seller_label"));

        holder.numArticle.setText(String.valueOf(numArticles));
        
        //holder.itemValue.setText(String.valueOf(cart.getArticleValue()));
        //holder.shippingCost.setText(String.valueOf(cart.getShippingMethod().getPrice()));
        //holder.descriptionShippingMethod.setText(cart.getShippingMethod().getName());
        
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        
        holder.totalCost.setText(String.valueOf(df.format(order.getTotalValue())));
        
        State state = order.getState();
        String datetSring;
        Date dateBought = new Date();
        Date datePaid = new Date();
        Date dateSent = new Date();
        Date dateReceived = new Date();
        Date dateCancellet = new Date();
        
        ViewManager viewManager;
		
        
        if(state.getState().compareTo("bought")==0){
        	try {
				dateBought = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(order.getState().getDateBought());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	datetSring = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(dateBought);
        	holder.boughtDate.setText(datetSring);
        	
        	holder.paidDate.setText("--");
        	holder.sentDate.setText("--");
        	holder.receivedDate.setText("--");
        	holder.cancelledDate.setText("--");
        	/*viewManager = (ViewManager) holder.paidDate.getParent();
        	viewManager.removeView(holder.paidDate);
        	viewManager.removeView(holder.paidDateLabel);
        	viewManager = (ViewManager) holder.sentDate.getParent();
        	viewManager.removeView(holder.sentDate);
        	viewManager.removeView(holder.sentDateLabel);
        	viewManager = (ViewManager) holder.receivedDate.getParent();
        	viewManager.removeView(holder.receivedDate);
    		viewManager.removeView(holder.receivedDateLabel);*/
        }
        
        if(state.getState().compareTo("paid")==0){
        	try {
        		dateBought = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(order.getState().getDateBought());
        		datePaid = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(order.getState().getDatePaid());
        	} catch (ParseException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        	datetSring = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(datePaid);
        	holder.paidDate.setText(datetSring);
        	datetSring = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(dateBought);
        	holder.boughtDate.setText(datetSring);
        	
        	holder.sentDate.setText("--");
        	holder.receivedDate.setText("--");
        	holder.cancelledDate.setText("--");
        	
        	/*viewManager = (ViewManager) holder.sentDate.getParent();
        	viewManager.removeView(holder.sentDate);
        	viewManager.removeView(holder.sentDateLabel);
        	viewManager = (ViewManager) holder.receivedDate.getParent();
        	viewManager.removeView(holder.receivedDate);
    		viewManager.removeView(holder.receivedDateLabel);*/
        }
        
        if(state.getState().compareTo("sent")==0){
        	try {
        		dateBought = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(order.getState().getDateBought());
        		datePaid = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(order.getState().getDatePaid());
        		dateSent = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(order.getState().getDateSent());
        	} catch (ParseException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        	datetSring = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(dateSent);
        	holder.sentDate.setText(datetSring);
        	datetSring = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(datePaid);
        	holder.paidDate.setText(datetSring);
        	datetSring = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(dateBought);
        	holder.boughtDate.setText(datetSring);
        	
        	holder.receivedDate.setText("--");
        	holder.cancelledDate.setText("--");
        	/*viewManager = (ViewManager) holder.receivedDate.getParent();
        	viewManager.removeView(holder.receivedDate);
    		viewManager.removeView(holder.receivedDateLabel);*/
        }
        
        if(state.getState().compareTo("received")==0){
        	try {
        		dateBought = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(order.getState().getDateBought());
        		datePaid = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(order.getState().getDatePaid());
        		dateSent = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(order.getState().getDateSent());
        		dateReceived = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(order.getState().getDateReceived());
        	} catch (ParseException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        	datetSring = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(dateReceived);
        	holder.receivedDate.setText(datetSring);
        	datetSring = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(dateSent);
        	holder.sentDate.setText(datetSring);
        	datetSring = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(datePaid);
        	holder.paidDate.setText(datetSring);
        	datetSring = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(dateBought);
        	holder.boughtDate.setText(datetSring);
        	
        	holder.cancelledDate.setText("--");
        }
        
        if(state.getState().compareTo("lost")==0){
        	try {
        		dateBought = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(order.getState().getDateBought());
        		datePaid = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(order.getState().getDatePaid());
        		dateSent = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(order.getState().getDateSent());
        	} catch (ParseException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        	datetSring = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(dateSent);
        	holder.sentDate.setText(datetSring);
        	datetSring = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(datePaid);
        	holder.paidDate.setText(datetSring);
        	datetSring = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(dateBought);
        	holder.boughtDate.setText(datetSring);
        	
        	holder.receivedDate.setText("--");
        	holder.cancelledDate.setText("--");
        }
        
        if(state.getState().compareTo("cancelled")==0){
        	try {
        		dateCancellet = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(order.getState().getDateCancelled());
        		dateBought = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(order.getState().getDateBought());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			datetSring = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(dateCancellet);
			holder.cancelledDate.setText(datetSring);
			datetSring = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(dateBought);
			holder.boughtDate.setText(datetSring);
			
			holder.paidDate.setText("--");
        	holder.sentDate.setText("--");
        	holder.receivedDate.setText("--");
        }

        //envio la vista
        return item;
    }


    public static class ViewHolder {
        public TextView userName;
        public TextView actorLabel;
        public ImageView sellerReputation;
        public ImageView sellerCountry;
        public TextView numArticle;
        public TextView boughtDate;
        public TextView boughtDateLabel;
        public TextView paidDate;
        public TextView paidDateLabel;
        public TextView sentDate;
        public TextView sentDateLabel;
        public TextView receivedDate;
        public TextView receivedDateLabel;
        public TextView cancelledDate;
        //public TextView shippingCost;
       // public TextView descriptionShippingMethod;
        public TextView totalCost;
        //public ListView listArticles;
    }
    
    public String getStringResourceByName(String aString) {
        String packageName = this.activity.getPackageName();
        int resId = this.activity.getResources().getIdentifier(aString, "string", packageName);
        return this.activity.getString(resId);
    }
}
