package com.MKMAndroid.classes;

import java.io.InputStream;
import java.net.URL;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.MKMAndroid.fragments.ArticlesFragment;

public class DownloadImageBackground extends AsyncTask<String, Void, Bitmap> {

private ImageView imageView = null;
private String myURL = null;
private ActivityWithLoading activity;
private ProgressDialog dialog;
private Bitmap bitmap;
private ArticlesFragment fragment;

	public DownloadImageBackground(ActivityWithLoading activity, ArticlesFragment frag, ProgressDialog dialog)
	{
	    this.activity = activity;
	    this.dialog = dialog;
	    this.fragment = frag;
	}

	protected void onPreExecute() {
		if (activity!=null){
			this.dialog.setMessage(activity.getStringResourceByName("loading"));
		}
	    this.dialog.show();
	}
	
	@Override
	protected Bitmap doInBackground(String...strings) {
	    this.myURL = strings[0];
	    
	    try 
	    {
	        InputStream is = (InputStream) new URL(myURL).getContent();
	        bitmap = BitmapFactory.decodeStream(is);
	       
	    } catch (Exception e) {
	
	    }
	    return bitmap;
	}
	
	
	@Override
	protected void onPostExecute(Bitmap bitmap) {
	    this.fragment.showArticleDetail(bitmap);
	    this.dialog.dismiss();
	}

}