package com.MKMAndroid.classes;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.MKMAndroid.activities.MainActivity;
import com.MKMAndroid.activities.SettingsActivity;
import com.MKMAndroid.activities.ShoppingCartActivity;
import com.MKMAndroid.app.R;
import com.MKMAndroid.fragments.SettingsFragment;

public class DialogPassword extends Dialog implements
android.view.View.OnClickListener {

	private Button accept;
	private Button cancel;
	private ActivityWithLoading activity;
	private int type =0;
	
	public DialogPassword(ActivityWithLoading activity, int type) {
		super((Activity)activity);
		this.activity = activity;
		this.type = type;
	}

	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.dialog_password);
	    accept = (Button) findViewById(R.id.buttonAccept);
	    cancel = (Button) findViewById(R.id.buttonCancel);
	    TextView title = (TextView) this.findViewById(R.id.textTitle);
	    
	    if (type==1){
	    	title.setText(this.activity.getStringResourceByName("are_you_sure"));
	    }
	    
	    if (type==2){
	    	title.setText(this.activity.getStringResourceByName("security_question"));
	    }
	    
	    accept.setOnClickListener(this);
	    cancel.setOnClickListener(this);

	  }
	  
	@Override
	public void onClick(View view) {

		EditText passwordText = (EditText) findViewById(R.id.editTextPassword);
   		String actualPassword = SettingsFragment.converToMD5(passwordText.getText().toString().trim());
   		String savedPassword = this.getUserPassword();
   		
		   switch(view.getId()) {
		   	case R.id.buttonAccept:
			   		if(actualPassword.compareTo(savedPassword)==0){
			   			if (type==1){
				   			ShoppingCartCheckOutAsync updater = new ShoppingCartCheckOutAsync(this.activity, this.activity.getDialog());
				   			updater.execute(generateUrlConsultaShoppingCart());
				   			
			   			}
			   			if (type==2){
			   				Intent intent;
							intent = new Intent((Activity)this.activity, SettingsActivity.class);
							((Activity)this.activity).startActivity(intent);
			   			}
			   			
			   			this.dismiss();
			   		}else{
			   			TextView errorMessage = (TextView)findViewById(R.id.textViewErrorPasswordMessage);
			   			errorMessage.setVisibility(View.VISIBLE);
			   		}
			   break;
		   	case R.id.buttonCancel:
		   		this.dismiss();
		   		break;
		   
		   }
	}
	
	private String generateUrlConsultaShoppingCart()
    {
    	String FILENAME = Constants.file_settings_name;
    	SharedPreferences settings = ((Activity) this.activity).getSharedPreferences(FILENAME, 0);
    	String keyMKM = settings.getString("apiKey",null);
    	String userMKM = settings.getString("userName",null);
    	
    	return Constants.domain + userMKM +"/" + keyMKM + "/shoppingcart";
    }
	
	private String getUserPassword(){
		String FILENAME = Constants.file_settings_name;
    	SharedPreferences settings = ((Activity) this.activity).getSharedPreferences(FILENAME, 0);
    	
    	return settings.getString("password",null);
	}

}
