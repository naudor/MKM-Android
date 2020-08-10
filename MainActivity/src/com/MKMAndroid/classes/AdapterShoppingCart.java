package com.MKMAndroid.classes;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.MKMAndroid.app.R;

/**
 * Created by adunjo on 5/03/14.
 */

public class AdapterShoppingCart extends BaseAdapter {

    protected ArrayList<ShoppingCart> items;
    private LayoutInflater mInflater;
    private Activity activity;


    public AdapterShoppingCart(Activity activity, ArrayList<ShoppingCart> items) {
        this.items = items;
        this.activity = activity;
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public AdapterShoppingCart(LayoutInflater inflater, ArrayList<ShoppingCart> items) {
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


    	ShoppingCart cart = items.get(position);
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
            holder.itemValue = (TextView)item.findViewById(R.id.textViewItemValue);
            holder.shippingCost = (TextView)item.findViewById(R.id.textViewShippingCost);
            holder.descriptionShippingMethod = (TextView)item.findViewById(R.id.textViewDescriptionShippingMethod);
            holder.totalCost = (TextView)item.findViewById(R.id.textViewTotalCost);
            //holder.listArticles = (ListView) item.findViewById(R.id.listViewArticlesShoppingCart);
            
            item.setTag(holder);

        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        if (cart.getSeller()!=null) {
            holder.sellerName.setText(cart.getSeller().getUsername());

            if (cart.getSeller().getCountry().equals("PT")) {
                holder.sellerCountry.setImageResource(R.drawable.portugal);
            } else if (cart.getSeller().getCountry().equals("ES")) {
                holder.sellerCountry.setImageResource(R.drawable.espanya);
            } else if (cart.getSeller().getCountry().equals("GB")) {
                holder.sellerCountry.setImageResource(R.drawable.anglaterra);
            } else if (cart.getSeller().getCountry().equals("FR")) {
                holder.sellerCountry.setImageResource(R.drawable.francia);
            } else if (cart.getSeller().getCountry().equals("D")) {
                holder.sellerCountry.setImageResource(R.drawable.alemania);
            }else if (cart.getSeller().getCountry().equals("DK")){
                holder.sellerCountry.setImageResource(R.drawable.dinamarca);
            } else if (cart.getSeller().getCountry().equals("AT")) {
                holder.sellerCountry.setImageResource(R.drawable.austria);
            } else if (cart.getSeller().getCountry().equals("CH")) {
                holder.sellerCountry.setImageResource(R.drawable.suisa);
            } else if (cart.getSeller().getCountry().equals("SI")) {
                holder.sellerCountry.setImageResource(R.drawable.eslovenia);
            } else if (cart.getSeller().getCountry().equals("BE")) {
                holder.sellerCountry.setImageResource(R.drawable.belgica);
            } else if (cart.getSeller().getCountry().equals("FI")) {
                holder.sellerCountry.setImageResource(R.drawable.finlandia);
            } else if (cart.getSeller().getCountry().equals("NL")) {
                holder.sellerCountry.setImageResource(R.drawable.holanda);
            } else if (cart.getSeller().getCountry().equals("PL")) {
                holder.sellerCountry.setImageResource(R.drawable.polonia);
            } else if (cart.getSeller().getCountry().equals("CZ")) {
                holder.sellerCountry.setImageResource(R.drawable.republicatxeca);
            } else if (cart.getSeller().getCountry().equals("GR")) {
                holder.sellerCountry.setImageResource(R.drawable.grecia);
            }else if (cart.getSeller().getCountry().equals("IT")) {
            	holder.sellerCountry.setImageResource(R.drawable.italia);
            }

            if(cart.getSeller().getReputation()==1){
                holder.sellerReputation.setImageResource(R.drawable.reputation1);
            }else if(cart.getSeller().getReputation()==2){
                holder.sellerReputation.setImageResource(R.drawable.reputation2);
            }else if(cart.getSeller().getReputation()==3) {
                holder.sellerReputation.setImageResource(R.drawable.reputation3);
            }else if(cart.getSeller().getReputation()==4) {
            	holder.sellerReputation.setImageResource(R.drawable.reputation0);
            }else if(cart.getSeller().getReputation()==0) {
            	if(cart.getSeller().getRiskGroup()==1){
            		holder.sellerReputation.setImageResource(R.drawable.no_reputation);
            	}else{
            		holder.sellerReputation.setImageResource(R.drawable.reputation0);
            	}
            }
        }
        
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        
        int numArticles = 0;
        ArrayList<Article> articles = cart.getArticles();
    	for (int x=0; x < articles.size(); x++){
    		numArticles = numArticles + articles.get(x).getCount();
    	}
        holder.numArticle.setText(String.valueOf(numArticles));
        
        holder.itemValue.setText(String.valueOf(df.format(cart.getArticleValue())));
        holder.shippingCost.setText(String.valueOf(df.format(cart.getShippingMethod().getPrice())));
        holder.descriptionShippingMethod.setText(cart.getShippingMethod().getName());
        holder.totalCost.setText(String.valueOf(df.format(cart.getTotalValue())));
        
        //I do the adapter for the list inside this adapter
        /*AdapterArticlesShoppingCart adapter = new AdapterArticlesShoppingCart(this.activity, cart.getArticles(), cart.getProducts());
        holder.listArticles.setAdapter(adapter);*/
    
        //envio la vista
        return item;
    }


    public static class ViewHolder {
        public TextView sellerName;
        public ImageView sellerReputation;
        public ImageView sellerCountry;
        public TextView numArticle;
        public TextView itemValue;
        public TextView shippingCost;
        public TextView descriptionShippingMethod;
        public TextView totalCost;
        public ListView listArticles;
    }
}
