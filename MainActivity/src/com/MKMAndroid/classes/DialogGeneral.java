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
import com.MKMAndroid.activities.OrderActivity;
import com.MKMAndroid.activities.ShoppingCartActivity;
import com.MKMAndroid.app.R;
import com.MKMAndroid.fragments.SettingsFragment;

public class DialogGeneral extends Dialog implements
android.view.View.OnClickListener {

	private Button accept;
	private Button cancel;
	private ActivityWithLoading activity;
	private int dialogType;
	
	public DialogGeneral(ActivityWithLoading activity, int dialogType) {
		super((Activity)activity);
		this.activity = activity;
		this.dialogType = dialogType;
	}

	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.dialog_general);
	    TextView message = (TextView) findViewById(R.id.textMessage);
	    TextView title = (TextView) findViewById(R.id.textTitle);
	    
	    switch(this.dialogType){
	    	case 1:
	    		message.setText(this.activity.getStringResourceByName("arrived_question"));
	    		title.setText(this.activity.getStringResourceByName("are_you_sure"));
	    		break;
	    	case 2:
	    		message.setText(this.activity.getStringResourceByName("sent_question"));
	    		title.setText(this.activity.getStringResourceByName("are_you_sure"));
	    		break;
	    	case 3:
	    		message.setText(this.activity.getStringResourceByName("ask_delete_all_shoppingCarts"));
	    		title.setText(this.activity.getStringResourceByName("are_you_sure"));
	    		break;
	    	case 4:
	    		message.setText(this.activity.getStringResourceByName("ask_delete_selected_shoppingCart"));
	    		title.setText(this.activity.getStringResourceByName("are_you_sure"));
	    		break;
	    }
	    accept = (Button) findViewById(R.id.buttonAccept);
	    cancel = (Button) findViewById(R.id.buttonCancel);
	    accept.setOnClickListener(this);
	    cancel.setOnClickListener(this);

	  }
	  
	@Override
	public void onClick(View view) {

		   switch(view.getId()) {
		   	case R.id.buttonAccept:
		   		switch(this.dialogType){
		   			case 1:
		   				((OrderActivity) this.activity).confirmReception();
		   				this.dismiss();
		   				break;
		   			case 2:
		   				((OrderActivity)activity).confirmSend();
		   				this.dismiss();
		   				break;
		   			case 3:
		   				((ShoppingCartActivity)activity).deleteAllShoppingCarts();
		   				this.dismiss();
		   				break;
		   			case 4:
		   				((ShoppingCartActivity)activity).deleteShoppingCartSelected();
		   				this.dismiss();
		   				break;
		   		}

				
		   		/*EditText passwordText = (EditText) findViewById(R.id.editTextPasswordValue);
		   		String actualPassword = SettingsFragment.converToMD5(passwordText.getText().toString().trim());
		   		String savedPassword = this.getUserPassword();
		   		
		   		if(actualPassword.compareTo(savedPassword)==0){
		   			this.dismiss();
		   		}else{
		   			TextView errorMessage = (TextView)findViewById(R.id.textViewErrorMesage);
		   			errorMessage.setVisibility(View.VISIBLE);
		   		}*/
		   		
		   		
			   break;
		   	case R.id.buttonCancel:
		   		this.dismiss();
		   		break;
		   
		   }
	}
}
