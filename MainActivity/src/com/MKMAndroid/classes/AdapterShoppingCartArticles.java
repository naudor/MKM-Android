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
import android.widget.TextView;

import com.MKMAndroid.app.R;

/**
 * Created by adunjo on 5/03/14.
 */

public class AdapterShoppingCartArticles extends BaseAdapter {

    protected ArrayList<Article> items;
    protected ArrayList<Product> products;
    private LayoutInflater mInflater;


    public AdapterShoppingCartArticles(Activity activity, ArrayList<Article> items, ArrayList<Product> products) {
        this.items = items;
        this.products = products;
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public AdapterShoppingCartArticles(LayoutInflater inflater, ArrayList<Article> items, ArrayList<Product> products) {
        this.items = items;
        this.products = products;
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
        return items.get(position).getIdProduct();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Article article = items.get(position);
        ImageView nextImage = null;
        /////////////////////////////////////////////////////
        ViewHolder holder = null;
        View item = convertView;

        if (item == null || !( item.getTag() instanceof ViewHolder)) {
            item = mInflater.inflate(R.layout.list_item_shoppingcart_article, null);
            holder = new ViewHolder();

            holder.cardName = (TextView)item.findViewById(R.id.textCardName);
            holder.cardExpansion = (TextView)item.findViewById(R.id.textViewExpansion);
            holder.cardPrice = (TextView)item.findViewById(R.id.textCardPrice);
            holder.avaliable = (TextView)item.findViewById(R.id.textAvailable);
            holder.imageLanguageCard = (ImageView)item.findViewById(R.id.imageLanguageCard);
            holder.condition = (ImageView)item.findViewById(R.id.imageCondition);
            holder.imageFirst = (ImageView)item.findViewById(R.id.imageFirst);
            holder.imageSecond = (ImageView)item.findViewById(R.id.imageSecond);
            holder.imageThird = (ImageView)item.findViewById(R.id.imageThird);
            holder.imageFourth = (ImageView)item.findViewById(R.id.imageFourth);

            nextImage = holder.imageFirst;
            item.setTag(holder);

        } else {
            holder = (ViewHolder)convertView.getTag();
            nextImage = holder.imageFirst;
        }
        
        
        
        
        for(int x=0; x < this.products.size(); x++)
        {
        	Product product = this.products.get(x);
        	if (article.getIdProduct() == product.getIdProduct()){
        		holder.cardName.setText(product.getName());
        		holder.cardExpansion.setText(product.getExpansion());
        	}
        }
        
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        
        holder.cardPrice.setText(String.valueOf(df.format(article.getPrice())));
        holder.avaliable.setText(String.valueOf(article.getCount()));

        switch (article.getLanguage().getIdLanguage()) {
            case 1:
                holder.imageLanguageCard.setImageResource(R.drawable.anglaterra);
                break;
            case 2:
                holder.imageLanguageCard.setImageResource(R.drawable.francia);
                break;
            case 3:
                holder.imageLanguageCard.setImageResource(R.drawable.alemania);
                break;
            case 4:
                holder.imageLanguageCard.setImageResource(R.drawable.espanya);
                break;
            case 5:
                holder.imageLanguageCard.setImageResource(R.drawable.italia);
                break;
            case 6:
                holder.imageLanguageCard.setImageResource(R.drawable.xina);
                break;   
            case 7:
                holder.imageLanguageCard.setImageResource(R.drawable.japo);
                break;
            case 8:
                holder.imageLanguageCard.setImageResource(R.drawable.portugal);
                break;
            case 10:
                holder.imageLanguageCard.setImageResource(R.drawable.korea);
                break;
            case 11:
                holder.imageLanguageCard.setImageResource(R.drawable.xinot);
                break;
        }

        if (article.getCondition()!=null){
            if (article.getCondition().equals("MT")) {
                holder.condition.setImageResource(R.drawable.mt);
            } else if (article.getCondition().equals("NM")) {
                holder.condition.setImageResource(R.drawable.nm);
            } else if (article.getCondition().equals("EX")) {
                holder.condition.setImageResource(R.drawable.ex);
            } else if (article.getCondition().equals("GD")) {
                holder.condition.setImageResource(R.drawable.gd);
            }
            if (article.getCondition().equals("LP")) {
                holder.condition.setImageResource(R.drawable.lp);
            }
            if (article.getCondition().equals("PL")) {
                holder.condition.setImageResource(R.drawable.pl);
            }
            if (article.getCondition().equals("PO")) {
                holder.condition.setImageResource(R.drawable.po);
            }
        }

        if (article.isFoil()) {
            nextImage.setImageResource(R.drawable.foil);
            nextImage = changeNextImage(nextImage, holder);
        }else{
            nextImage.setImageDrawable(null);
        }

        if (article.isSigned()) {
            nextImage.setImageResource(R.drawable.signed);
            nextImage = changeNextImage(nextImage, holder);
        }else{
            nextImage.setImageDrawable(null);
        }

        if (article.isPlayset()) {
            nextImage.setImageResource(R.drawable.playset);
            nextImage = changeNextImage(nextImage, holder);
        }else{
            nextImage.setImageDrawable(null);
        }

        if (article.isAltered()) {
            nextImage.setImageResource(R.drawable.altered);
            nextImage = changeNextImage(nextImage, holder);
        }else{
            nextImage.setImageDrawable(null);
        }

        //envio la vista
        return item;
    }

    //Torno es seguent camp imatge que es pot fer servir
    private ImageView changeNextImage(ImageView actualImage, ViewHolder holder)
    {
        ImageView nextImage = actualImage;

        if(actualImage==holder.imageFirst) nextImage = holder.imageSecond;
        else if (actualImage==holder.imageSecond) nextImage = holder.imageThird;
        else if (actualImage==holder.imageThird) nextImage = holder.imageFourth;

        return nextImage;
    }

    public static class ViewHolder {
        public TextView cardName;
        public TextView cardExpansion;
        public TextView cardPrice;
        public TextView avaliable;
        public ImageView imageLanguageCard;
        public ImageView condition;
        public ImageView imageFirst;
        public ImageView imageSecond;
        public ImageView imageThird;
        public ImageView imageFourth;
    }
}
