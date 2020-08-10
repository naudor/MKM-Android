package com.MKMAndroid.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.MKMAndroid.app.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AdapterNavigationDrawer extends BaseExpandableListAdapter {

	private Context _context;
	private List<String> _listDataHeader; // header titles
	private List<Integer> _listIcons; // header icons
	// child data in format of header title, child title
	private HashMap<String, List<String>> _listDataChild;
	
	public AdapterNavigationDrawer(Context context, List<String> listDataHeader,
	        HashMap<String, List<String>> listChildData, List<Integer> listIcons) {
	    this._context = context;
	    this._listDataHeader = listDataHeader;
	    this._listDataChild = listChildData;
	    this._listIcons = listIcons;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosititon) {
	    return this._listDataChild.get(this._listDataHeader.get(groupPosition))
	            .get(childPosititon);
	}
	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
	    return childPosition;
	}
	
	@Override
	public View getChildView(int groupPosition, final int childPosition,
	        boolean isLastChild, View convertView, ViewGroup parent) {
	
	    final String childText = (String) getChild(groupPosition, childPosition);
	
	    if (convertView == null) {
	        LayoutInflater infalInflater = (LayoutInflater) this._context
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        convertView = infalInflater.inflate(R.layout.navigation_drawer_item_level2, null);
	    }
	
	    TextView txtListChild = (TextView) convertView
	            .findViewById(R.id.txtLabel);
	
	    txtListChild.setText(childText);
	    return convertView;
	}
	
	@Override
	public int getChildrenCount(int groupPosition) {
		if (this._listDataChild.get(this._listDataHeader.get(groupPosition))==null){
			return 0;
		}
		else{
			return this._listDataChild.get(this._listDataHeader.get(groupPosition))
	            .size();
		}
	}
	
	@Override
	public Object getGroup(int groupPosition) {
	    return this._listDataHeader.get(groupPosition);
	}
	
	@Override
	public int getGroupCount() {
	    return this._listDataHeader.size();
	}
	
	@Override
	public long getGroupId(int groupPosition) {
	    return groupPosition;
	}
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
	        View convertView, ViewGroup parent) {
		
	    String headerTitle = (String) getGroup(groupPosition);
	    
	    if (convertView == null) {
	        LayoutInflater infalInflater = (LayoutInflater) this._context
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        convertView = infalInflater.inflate(R.layout.navigation_drawer_item_level1, null);
	    }
	
	    TextView lblListHeader = (TextView) convertView
	            .findViewById(R.id.txtLabel);
	    lblListHeader.setText(headerTitle);
	    
	    //I check that the menu have childrens, if it haven't I hidde the indicator
	    ImageView ind = (ImageView)convertView.findViewById(R.id.imageIndicator);
	    ImageView icon = (ImageView)convertView.findViewById(R.id.imageIcon);
	    
	    if (getChildrenCount( groupPosition ) == 0 ) {
	    	ind.setVisibility( View.INVISIBLE );
	     }else {
		 	ind.setVisibility( View.VISIBLE );
	     }
	    
	   
	    if (isExpanded) {
	        //set the indicator when expandable list is in expanded form
	    	ind.setImageResource(R.drawable.ic_action_collapse);
	    } else {
	        //set the indicator when expandable list is not in expanded form
	    	ind.setImageResource(R.drawable.ic_action_expand);
	    }
	    
	    icon.setImageResource(_listIcons.get(groupPosition));
	
	    return convertView;
	}
	
	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		//return false;
		return true;
	}
	
	@Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

}
