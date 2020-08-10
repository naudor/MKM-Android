package com.MKMAndroid.fragments;

/**
 * Created by Arnau on 9/02/14.
 */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.MKMAndroid.app.R;
import com.MKMAndroid.classes.ActivityWithLoading;
import com.MKMAndroid.classes.CheckIdentificationAsync;
import com.MKMAndroid.classes.Constants;
import com.MKMAndroid.classes.DialogMessage;
import com.smaato.soma.BannerView;

/**
 * A placeholder fragment containing a simple view.
 */
public class SettingsFragment extends Fragment implements  View.OnClickListener
{
	View rootView = null;
	Dialog dialog = null;
	ActivityWithLoading activity;
	
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "1";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SettingsFragment newInstance(int sectionNumber)
    {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
    	View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
    	this.rootView = rootView;
    	this.activity = (ActivityWithLoading) this.getActivity();

        //acció buto de cercar
        final Button button = (Button) rootView.findViewById(R.id.buttonConfimSettings);
        button.setOnClickListener(this);
        
        setHasOptionsMenu(true);
        
        String FILENAME = Constants.file_settings_name;
        SharedPreferences settings = getActivity().getSharedPreferences(FILENAME, 0);
        
        TextView userName = (TextView) rootView.findViewById(R.id.editTextUserName);
        TextView api_key = (TextView) rootView.findViewById(R.id.editTextApiKey);
    	
        //userName.setText(settings.getString("userName", ""));
    	userName.setText(settings.getString("userName", "naudor"));
    	//api_key.setText(settings.getString("apiKey", ""));
    	api_key.setText("9105b1554d800860b3c7f424fd0c439d");
    	

        bannerSetup(rootView);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
    }

    public void bannerSetup(View view){
    	/*BannerView mBanner = (BannerView) view.findViewById((R.id.banner_main));
        mBanner.getAdSettings().setPublisherId(Constants.publisherId);
        mBanner.getAdSettings().setAdspaceId(Constants.adspaceId);
        mBanner.setAutoReloadFrequency(Constants.bannerRefreshTime);
        mBanner.getUserSettings().setKeywordList(Constants.keywordList); 
        mBanner.getUserSettings().setSearchQuery(Constants.searchQuery);
        mBanner.asyncLoadNewBanner();*/
    }

	@Override
	public void onClick(View view) {
		  /*SettingsFragment settingsFragment = new SettingsFragment();
        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();*/

		String FILENAME = Constants.file_settings_name;
		
		//Load the user preferences
    	SharedPreferences settings = getActivity().getSharedPreferences(FILENAME, 0);
    	
    	TextView api_key = (TextView) rootView.findViewById(R.id.editTextApiKey);
    	TextView password = (TextView) rootView.findViewById(R.id.editTextPassword);
    	TextView userName = (TextView) rootView.findViewById(R.id.editTextUserName);
    	
    	//AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getActivity());
    	DialogMessage alertDialog = new DialogMessage((ActivityWithLoading)this.getActivity(), 1);
    	
        switch(view.getId()) {
            case R.id.buttonConfimSettings:
            	TextView passwordConfirm = (TextView) rootView.findViewById(R.id.editTextPasswordConfirm);

            	//if the password are not null and equal
            	if (password.getText().toString().compareTo(passwordConfirm.getText().toString())==0 && 
            			password.getText().length()>0){
            		
            		String final_url= new String(Constants.domain+userName.getText() +"/"+
            				api_key.getText() + "/products/" + Uri.encode("island")+"/1"+"/1/false");
            		
            		CheckIdentificationAsync checker = new CheckIdentificationAsync((ActivityWithLoading)this.getActivity(), this);
            		if(this.activity.checkInternetConnection()){
            			checker.execute(final_url);
            		}
            			
            	} else {
            		
                     // set title
            		 if (password.getText().length()<1){
            			 alertDialog = new DialogMessage((ActivityWithLoading)this.getActivity(),2);
            		 }else{
            			 alertDialog = new DialogMessage((ActivityWithLoading)this.getActivity(),3);
            		 }
            		 
            		 alertDialog.show();
            	}
            	
                break;


            case R.id.buttonAccept:
            	EditText oldPassword = (EditText) dialog.findViewById(R.id.editTextPassword);
            	String passwordPref = settings.getString("password",null);
            	
            	//If the password put it's equal to the old password
            	if(passwordPref.compareTo(SettingsFragment.converToMD5(oldPassword.getText().toString()))==0){
            		SharedPreferences.Editor editor= settings.edit();
        			editor.putString("apiKey", api_key.getText().toString().trim());
        			editor.putString("userName", userName.getText().toString().trim());
        			editor.putString("password", SettingsFragment.converToMD5(password.getText().toString().trim()));
        			editor.commit();
        			dialog.dismiss();

        			alertDialog.show();
                    
            	}else{
            		TextView errorMessage = (TextView) dialog.findViewById(R.id.textViewErrorPasswordMessage);
            		errorMessage.setVisibility(TextView.VISIBLE);
            	}
        		
                break;
            case R.id.buttonCancel:
            	dialog.dismiss();
            	break;
        }
		
	}

	public static FileOutputStream openFileOutput(String filename, Context ctx) {
		FileOutputStream fos = null;
		
        try {
            fos = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        return fos;
	}
	
	public String getStringResourceByName(String aString) {
        String packageName = this.getActivity().getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }
	
	public static final String converToMD5(final String s) {
	    final String MD5 = "MD5";
	    try {
	        // Create MD5 Hash
	        MessageDigest digest = java.security.MessageDigest
	                .getInstance(MD5);
	        digest.update(s.getBytes());
	        byte messageDigest[] = digest.digest();

	        // Create Hex String
	        StringBuilder hexString = new StringBuilder();
	        for (byte aMessageDigest : messageDigest) {
	            String h = Integer.toHexString(0xFF & aMessageDigest);
	            while (h.length() < 2)
	                h = "0" + h;
	            hexString.append(h);
	        }
	        return hexString.toString();

	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return "";
	}
	
	/**
	 * After the identification, if it was correct save the preferences, if it wasn't correct
	 * show the error message
	 * @param Boolean save
	 */
	public void savePreferences(Boolean save)
	{
		DialogMessage alertDialog;
		
    	if (save==true){
    		alertDialog = new DialogMessage((ActivityWithLoading)this.getActivity(), 1);
			String FILENAME = Constants.file_settings_name;
			
			//Load the user preferences
	    	SharedPreferences settings = getActivity().getSharedPreferences(FILENAME, 0);
	    	
			TextView api_key = (TextView) rootView.findViewById(R.id.editTextApiKey);
	    	TextView password = (TextView) rootView.findViewById(R.id.editTextPassword);
	    	TextView userName = (TextView) rootView.findViewById(R.id.editTextUserName);
	    	
			SharedPreferences.Editor editor= settings.edit();
			editor.putString("apiKey", api_key.getText().toString().trim());
			editor.putString("userName", userName.getText().toString().trim());
			editor.putString("password", SettingsFragment.converToMD5(password.getText().toString().trim()));
			editor.commit();
			
			
    	}else{
    		alertDialog = new DialogMessage((ActivityWithLoading)this.getActivity(), 4);
    	}
    	
    	
    	alertDialog.show();
	}
	
	// I need hide the button on action bar
	@Override
	public void onPrepareOptionsMenu(Menu menu) {

		MenuItem item= menu.findItem(R.id.action_filter);
		
		if (item!=null){
			item.setEnabled(false);
			item.setVisible(false);
			
		    item= menu.findItem(R.id.action_legend);
		    item.setEnabled(false);
		    item.setVisible(false);
		}
	    super.onPrepareOptionsMenu(menu);
	}
}
