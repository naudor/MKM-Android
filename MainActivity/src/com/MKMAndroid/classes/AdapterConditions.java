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

public class AdapterConditions extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Condition> items;
    private LayoutInflater mInflater;

    public AdapterConditions(Activity activity) {
        this.activity = activity;
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = loadLanguages();

    }

    public AdapterConditions() {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = loadLanguages();
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Condition cadena = items.get(position);

        /////////////////////////////////////////////////////
        ViewHolder holder = null;
        View item = convertView;

        if (item == null || !( item.getTag() instanceof ViewHolder)) {
            item = mInflater.inflate(R.layout.list_item_simple, null);
            holder = new ViewHolder();

            holder.label = (TextView)item.findViewById(R.id.txtLabel);

            item.setTag(holder);
        } else {
            holder = (ViewHolder)item.getTag();
        }

        holder.label.setText(cadena.getDescription());

        return item;
    }

    public static class ViewHolder {
        public TextView label;
    }

    public ArrayList<Condition> loadLanguages()
    {
        ArrayList<Condition> list = new ArrayList<Condition>();

        Condition condition;

        condition = new Condition("Mint","MT");
        list.add(condition);

        condition = new Condition("Near Mint","NM");
        list.add(condition);

        condition = new Condition("Excellet","EX");
        list.add(condition);

        condition = new Condition("Good","GD");
        list.add(condition);

        condition = new Condition("Light played", "LP");
        list.add(condition);

        condition = new Condition("Played", "PL");
        list.add(condition);

        condition = new Condition("Poor", "PO");
        list.add(condition);

        return list;
    }

    /**
     * Return de position of the object passed
     * @param condition
     * @return int
     */
    public int getPosition(Condition condition)
    {
        int position = -1;

        for (int x=0; x<getCount(); x++)
        {
            if (this.items.get(x).getCode() == condition.getCode()) position = x;
        }

        return position;
    }
}
