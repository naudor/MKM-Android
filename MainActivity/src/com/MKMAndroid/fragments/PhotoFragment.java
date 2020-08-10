package com.MKMAndroid.fragments;

import java.net.URL;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.MKMAndroid.activities.ArticlesActivity;
import com.MKMAndroid.app.R;


/**
 * Created by adunjo on 19/03/14.
 */
public class PhotoFragment extends Fragment {

    private URL photo;
    private ImageView image;
    private ProgressDialog dialog;
    private ArticlesActivity activity;

    public PhotoFragment() {}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    	Bitmap bmp = (Bitmap) this.getArguments().get("bitmap");
    	
    	View rootView = inflater.inflate(R.layout.fragment_zoom_image, container, false);
    	this.activity = (ArticlesActivity) getActivity();
    	
        //get the button view
        ImageView image = (ImageView) rootView.findViewById(R.id.photoImage);

		image.setImageBitmap(bmp);
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
