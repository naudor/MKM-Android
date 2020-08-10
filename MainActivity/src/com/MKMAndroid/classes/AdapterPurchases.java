package com.MKMAndroid.classes;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.MKMAndroid.app.R;

/**
 * Created by adunjo on 5/03/14.
 */

public class AdapterPurchases extends BaseAdapter {

    protected ArrayList<Order> items;
    private LayoutInflater mInflater;
    private Activity activity;


    public AdapterPurchases(Activity activity, ArrayList<Order> items) {
        this.items = items;
        this.activity = activity;
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public AdapterPurchases(LayoutInflater inflater, ArrayList<Order> items) {
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
        //ImageView nextImage = null;
        /////////////////////////////////////////////////////
        ViewHolder holder = null;
        View item = convertView;

        if (item == null || !( item.getTag() instanceof ViewHolder)) {
            item = mInflater.inflate(R.layout.list_item_shoppingcart, null);
            holder = new ViewHolder();

            holder.sellerName = (TextView)item.findViewById(R.id.textViewCardName);
            holder.sellerReputation = (ImageView)item.findViewById(R.id.imageReputationSeller);
            holder.sellerCountry = (ImageView)item.findViewById(R.id.imageCountrySeller);
            
            holder.numArticle = (TextView)item.findViewById(R.id.textViewNumArticle);
            holder.boughtDate = (TextView)item.findViewById(R.id.textViewBoughtDateValue);
            holder.paidDate = (TextView)item.findViewById(R.id.textViewPaidDateValue);
            //holder.itemValue = (TextView)item.findViewById(R.id.textViewItemValue);
            //holder.shippingCost = (TextView)item.findViewById(R.id.textViewShippingCost);
            //holder.descriptionShippingMethod = (TextView)item.findViewById(R.id.textViewDescriptionShippingMethod);
            holder.totalCost = (TextView)item.findViewById(R.id.textViewTotalCost);
            
            item.setTag(holder);

        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        if (order.getSellerInfo()!=null) {
        	Seller basicInfo = order.getSellerInfo().getBasicInfo();
            holder.sellerName.setText(basicInfo.getUsername());

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
        
        holder.numArticle.setText(String.valueOf(order.getArticles().size()));
        //holder.itemValue.setText(String.valueOf(cart.getArticleValue()));
        //holder.shippingCost.setText(String.valueOf(cart.getShippingMethod().getPrice()));
        //holder.descriptionShippingMethod.setText(cart.getShippingMethod().getName());
        holder.totalCost.setText(String.valueOf(order.getTotalValue()));
        holder.boughtDate.setText(String.valueOf(order.getState().getDateBought()));
        holder.paidDate.setText(String.valueOf(order.getState().getDatePaid()));
      
        //envio la vista
        return item;
    }


    public static class ViewHolder {
        public TextView sellerName;
        public ImageView sellerReputation;
        public ImageView sellerCountry;
        public TextView numArticle;
        public TextView boughtDate;
        public TextView paidDate;
        //public TextView shippingCost;
       // public TextView descriptionShippingMethod;
        public TextView totalCost;
        //public ListView listArticles;
    }
}
