package com.MKMAndroid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.MKMAndroid.activities.ArticlesActivity;
import com.MKMAndroid.app.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.MKMAndroid.fragments.LegendFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link com.MKMAndroid.fragments.LegendFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class LegendFragment extends Fragment {
	
	private ArticlesActivity activity;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    	View rootView = inflater.inflate(R.layout.fragment_legend, container, false);
    	this.activity = (ArticlesActivity) getActivity();
    	
    	((ArticlesActivity) getActivity()).setActionBarTitle("legend_title");
    	
		 setHasOptionsMenu(true);
        
        
        return rootView;
    }
    
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item;
        
        item= menu.findItem(R.id.action_filter);
        item.setVisible(false);
        item.setEnabled(false);
        
        item= menu.findItem(R.id.action_legend);
        item.setVisible(false);
        item.setEnabled(false);
        super.onPrepareOptionsMenu(menu);
    }
}
