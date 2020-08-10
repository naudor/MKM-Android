package com.MKMAndroid.classes;

/**
 * Created by adunjo on 5/02/14.
 */
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.MKMAndroid.app.R;

// Custom list item class for menu items
public class AdaptadorJocLlista extends BaseAdapter {

    private List<Game> items;
    private Context context;
    private int numItems = 0;

    public AdaptadorJocLlista(final List<Game> items, Context context) {
        this.items = items;
        this.context = context;
        this.numItems = items.size();
    }

    public int getCount() {
        return numItems;
    }

    public Game getItem(int position) {
        return items.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the current list item
        final Game item = items.get(position);
        // Get the layout for the list item
        final RelativeLayout itemLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.list_item_simple, parent, false);
        // Set the icon as defined in our list item
        //ImageView imgIcon = (ImageView) itemLayout.findViewById(R.id.imgIcon);
        //imgIcon.setImageDrawable(item.getPicture());

        // Set the text label as defined in our list item
        TextView txtLabel = (TextView) itemLayout.findViewById(R.id.txtLabel);
        txtLabel.setText(item.getName());

        return itemLayout;
    }

}
