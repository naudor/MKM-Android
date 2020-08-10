package com.MKMAndroid.classes;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

import com.MKMAndroid.activities.ArticlesActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class BitmapInternetLoader extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private int data = 0;
    private String url;
    private ProgressDialog dialog;
    private ActivityWithLoading act;
    private ArticlesActivity activity;

    
    public BitmapInternetLoader(ImageView imageView, ProgressDialog dialog, ActivityWithLoading act) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<ImageView>(imageView);
        this.dialog = dialog;
        this.act = act;
    }
    
    public BitmapInternetLoader(ImageView imageView, ArticlesActivity activity) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<ImageView>(imageView);
        this.activity = activity;
    }

    protected void onPreExecute() {
    	if (this.dialog!=null){
	    	this.dialog.setMessage(act.getStringResourceByName("loading"));
	        this.dialog.show();
    	}
    }
    
    // Decode image in background.
    @Override
    protected Bitmap doInBackground(String... params) {
        url = params[0];
        URL newurl;
        Bitmap imatgeBitmap = null;
        
		try {
			newurl = new URL(url);
			imatgeBitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        return imatgeBitmap;
        //return decodeSampledBitmapFromResource(this.contex.getResources(), data, 100, 100);
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
    	
        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
                activity.setImageBitMap(bitmap);
            }
        }
        
        if (this.dialog!=null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
    }
}