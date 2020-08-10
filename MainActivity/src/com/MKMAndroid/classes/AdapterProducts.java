package com.MKMAndroid.classes;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.MKMAndroid.app.R;

/**
 * Created by adunjo on 5/03/14.
 */

public class AdapterProducts extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Product> items;
    private LayoutInflater mInflater;

    public AdapterProducts(Activity activity, ArrayList<Product> items) {
        this.activity = activity;
        this.items = items;
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public AdapterProducts() {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        Product producte = items.get(position);

        /////////////////////////////////////////////////////
        ViewHolder holder = null;
        View item = convertView;

        if (item == null || !( item.getTag() instanceof ViewHolder)) {
            item = mInflater.inflate(R.layout.list_item_product, null);
            holder = new ViewHolder();

            holder.nomCarta = (TextView)item.findViewById(R.id.textCardName);
            holder.expansio = (TextView)item.findViewById(R.id.textExpansionName);
            holder.rareza = (TextView)item.findViewById(R.id.textRarityName);
            holder.preuMinim = (TextView)item.findViewById(R.id.textMiniumPrice);
            holder.guio = (TextView)item.findViewById(R.id.textGuio);
            item.setTag(holder);
        } else {
            holder = (ViewHolder)item.getTag();
        }

        holder.nomCarta.setText(producte.getName());
        holder.expansio.setText(producte.getExpansion());
        holder.rareza.setText(producte.getRarity());
        
        if(producte.getRarity()==null || producte.getRarity().length()==0)
        	holder.guio.setVisibility(View.GONE);
        else
        	holder.guio.setVisibility(View.VISIBLE);
        
        holder.preuMinim.setText(String.valueOf(producte.getPriceGuide().getLow()));
        return item;
    }

    public static class ViewHolder {
        public TextView nomCarta;
        public TextView expansio;
        public TextView rareza;
        public TextView preuMinim;
        public TextView guio;
    }
}
