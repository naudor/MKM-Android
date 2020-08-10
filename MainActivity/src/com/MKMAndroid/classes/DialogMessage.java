package com.MKMAndroid.classes;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.MKMAndroid.activities.OrderActivity;
import com.MKMAndroid.activities.SettingsActivity;
import com.MKMAndroid.activities.ShoppingCartActivity;
import com.MKMAndroid.app.R;
import com.MKMAndroid.fragments.SettingsFragment;

public class DialogMessage extends Dialog implements
android.view.View.OnClickListener {

	private Button accept;
	private Button cancel;
	private ActivityWithLoading activity;
	private int dialogType;
	
	public DialogMessage(ActivityWithLoading activity, int dialogType) {
		super((Activity)activity);
		this.activity = activity;
		this.dialogType = dialogType;
	}

	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.dialog_message);
	    TextView message = (TextView) findViewById(R.id.textMessage);
	    TextView title = (TextView) findViewById(R.id.textTitle);
	    
	    switch(this.dialogType){
		    case 1:
		    	message.setText(this.activity.getStringResourceByName("save_data_ok"));
		    	title.setText(this.activity.getStringResourceByName("operation_ok"));
		    	break;
		    case 2:
		    	message.setText(this.activity.getStringResourceByName("error_no_password"));
		    	title.setText( this.activity.getStringResourceByName("error"));
		    	break;
		    case 3:
		    	message.setText( this.activity.getStringResourceByName("error_password_not_match"));
		    	title.setText( this.activity.getStringResourceByName("error"));
		    	break;
		    case 4:
		    	message.setText( this.activity.getStringResourceByName("error_identification_not_correct"));
		    	title.setText( this.activity.getStringResourceByName("error"));
		    	break;
	    	case 5:
	    		message.setText(this.activity.getStringResourceByName("error_no_internet"));
	    		title.setText( this.activity.getStringResourceByName("error"));
	    		break;
	    }
	    accept = (Button) findViewById(R.id.buttonAccept);
	    accept.setOnClickListener(this);
	  }
	  
	@Override
	public void onClick(View view) {

		   switch(view.getId()) {
		   	case R.id.buttonAccept:
		   		this.dismiss();
		   			switch(this.dialogType){
		   				case 1:
		   					((SettingsActivity) this.activity).openDrawer(); 
		   					break;
		   			}
			   break;
		   }
	}

}
