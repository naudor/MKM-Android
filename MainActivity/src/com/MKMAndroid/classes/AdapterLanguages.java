package com.MKMAndroid.classes;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.MKMAndroid.app.R;

/**
 * Created by adunjo on 5/03/14.
 */

public class AdapterLanguages extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Language> items;
    private LayoutInflater mInflater;

    public AdapterLanguages(Activity activity) {
        this.activity = activity;
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = loadLanguages();

    }

    public AdapterLanguages() {
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
        return items.get(position).getIdLanguage();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Language language = items.get(position);

        /////////////////////////////////////////////////////
        ViewHolder holder = null;
        View item = convertView;

        if (item == null || !( item.getTag() instanceof ViewHolder)) {
            //item = mInflater.inflate(R.layout.list_item_simple, null);
            item = mInflater.inflate(R.layout.multi_choice_spinner_simple, null);
            holder = new ViewHolder();

            holder.label = (CheckBox)item.findViewById(R.id.checkBoxMultipleChoiceSpinner);

            item.setTag(holder);
        } else {
            holder = (ViewHolder)item.getTag();
        }

        holder.label.setText(String.valueOf(language.getLanguageName()));

        return item;
    }

    public static class ViewHolder {
        public CheckBox label;
    }

    public ArrayList<Language> loadLanguages()
    {
        ArrayList<Language> list = new ArrayList<Language>();

        Language language = new Language();
        language.setIdLanguage(1);
        language.setLanguageName("English");

        list.add(language);

        language = new Language();
        language.setIdLanguage(8);
        language.setLanguageName("Portuguese");

        list.add(language);

        language = new Language();
        language.setIdLanguage(4);
        language.setLanguageName("Spanish");

        list.add(language);

        return list;
    }

    /**
     * Return de position of the object passed
     * @param language
     * @return int
     */
    public int getPosition(Language language)
    {
        int position = -1;

        for (int x=0; x<getCount(); x++)
        {
            if (this.items.get(x).getIdLanguage() == language.getIdLanguage()) position = x;
        }

        return position;
    }

    public List<String> getStrings()
    {
        ArrayList<String> nameList = new ArrayList<String>();

        for (int x=0; x<this.getCount();x++){
            nameList.add(this.items.get(x).getLanguageName());
        }

        return nameList;
    }
}
