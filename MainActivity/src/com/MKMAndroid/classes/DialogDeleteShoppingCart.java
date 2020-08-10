package com.MKMAndroid.classes;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
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
import com.MKMAndroid.activities.ShoppingCartActivity;
import com.MKMAndroid.app.R;
import com.MKMAndroid.fragments.SettingsFragment;

public class DialogDeleteShoppingCart extends Dialog implements
android.view.View.OnClickListener {

	private Button accept;
	private Button cancel;
	private TextView message;
	private ShoppingCartActivity activity;
	private boolean deleteAll;
	
	public DialogDeleteShoppingCart(ShoppingCartActivity activity, boolean deleteAll) {
		super(activity);
		this.activity = activity;
		this.deleteAll = deleteAll;
	}

	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.dialog_general);
	    
	    message = (TextView) findViewById(R.id.textMessage);
	    accept = (Button) findViewById(R.id.buttonAccept);
	    cancel = (Button) findViewById(R.id.buttonCancel);
	    accept.setOnClickListener(this);
	    cancel.setOnClickListener(this);
	    
	    if(deleteAll==true){
	    	message.setText(activity.getStringResourceByName("ask_delete_all_shoppingCarts"));
	    }else{
	    	message.setText(activity.getStringResourceByName("ask_delete_selected_shoppingCart"));
	    }

	  }
	  
	@Override
	public void onClick(View view) {
	   switch(view.getId()) {
	   	case R.id.buttonAccept:
	   		if(deleteAll==true){
	   			activity.deleteAllShoppingCarts();
	   			this.dismiss();
	   		}else{
	   			activity.deleteShoppingCartSelected();
	   			this.dismiss();
	   		}
		   break;
	   	case R.id.buttonCancel:
	   		this.dismiss();
	   		break;
	   
	   }
	}
}
