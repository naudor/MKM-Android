package com.MKMAndroid.classes;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.MKMAndroid.activities.ShoppingCartActivity;
import com.MKMAndroid.app.R;

public class DialogModifyArticleShoppingCart extends Dialog implements
android.view.View.OnClickListener {

	private Button accept;
	private Button cancel;
	private ShoppingCart shoppingCartSelected; 
	private Article articleSelected;
	private ShoppingCartActivity activity;
	

	  public DialogModifyArticleShoppingCart(Context context, ShoppingCart shoppingCartSelected, Article articleSelected, ShoppingCartActivity activity ) {
		super(context);
		this.shoppingCartSelected = shoppingCartSelected;
		this.articleSelected = articleSelected;
		this.activity = activity;
	}

	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.modify_article_shoppingcart);
	    accept = (Button) findViewById(R.id.buttonBuyArticleShopping);
	    cancel = (Button) findViewById(R.id.buttonCancel);
	    accept.setOnClickListener(this);
	    cancel.setOnClickListener(this);
	    
	    TextView cardName = (TextView) findViewById(R.id.textCardNameDetailShopping);
		TextView expansionName = (TextView) findViewById(R.id.textViewExpansionShopping);
		Spinner nunmCards = (Spinner) findViewById(R.id.spinnerNumArticlesToBuyShopping);

		ArrayList<Product> productes = this.shoppingCartSelected.getProducts();
		Product product = null;
		
		for (int x=0; x < productes.size(); x++){
			if (productes.get(x).getIdProduct() == articleSelected.getIdProduct()){
				product = productes.get(x);
			}
		}
		
		cardName.setText(product.getName());
		expansionName.setText(product.getExpansion());
		
		List<String> numsArticles = new ArrayList<String>();
		
		for(int x = articleSelected.getCount(); x > 0;x--){
    		 numsArticles.add(Integer.toString(x));
    	}
    	 
    	 ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, numsArticles);
    	 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	 nunmCards.setAdapter(adapter);
	  }
	  
	@Override
	public void onClick(View view) {

		   switch(view.getId()) {
		   	case R.id.buttonBuyArticleShopping:
		   		Spinner amountToBuy = (Spinner) view.getRootView().findViewById(R.id.spinnerNumArticlesToBuyShopping);
		   		int position = amountToBuy.getSelectedItemPosition();
		   		String tmp = amountToBuy.getItemAtPosition(position).toString();
            	
		   		ShoppingCartRequest request = new ShoppingCartRequest("remove", this.articleSelected.getIdArticle(), Integer.parseInt(tmp));
		   		ProgressDialog dialog = this.activity.getDialog();
		   		
		   		ArrayList<ShoppingCartRequest> requests = new ArrayList<ShoppingCartRequest>();
		   		requests.add(request);
		   		
		   		PutShoppingCartAsync putter = new PutShoppingCartAsync(this.activity, dialog, requests);
		   		putter.execute(generateUrlConsultaShoppingCart());
		   		this.dismiss();
            	/*ShoppingCartRequest request = new ShoppingCartRequest("remove", this.articleSelected.getIdArticle(), Integer.parseInt(amountString));

            	 if (request!=null){
            		 String url = generateUrlConsultaShoppingCart();
            		 PutShoppingCartAsync updater = new PutShoppingCartAsync(this, dialog, request);
            		 updater.execute(url);
                 }*/
			   break;
		   	case R.id.buttonCancel:
		   		this.dismiss();
		   		break;
		   
		   }
	}
	
	private String generateUrlConsultaShoppingCart()
    {
    	String FILENAME = Constants.file_settings_name;
    	SharedPreferences settings = this.activity.getSharedPreferences(FILENAME, 0);
    	String keyMKM = settings.getString("apiKey",null);
    	String userMKM = settings.getString("userName",null);
    	
    	return Constants.domain + userMKM +"/" + keyMKM + "/shoppingcart";
    }

}
