package com.MKMAndroid.fragments;

/**
 * Created by Arnau on 9/02/14.
 */

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.MKMAndroid.activities.ShoppingCartActivity;
import com.MKMAndroid.app.R;
import com.MKMAndroid.classes.ActivityWithLoading;
import com.MKMAndroid.classes.AdapterShoppingCart;
import com.MKMAndroid.classes.Article;
import com.MKMAndroid.classes.Constants;
import com.MKMAndroid.classes.DeleteShoppingCartAsync;
import com.MKMAndroid.classes.DialogModifyArticleShoppingCart;
import com.MKMAndroid.classes.DialogPassword;
import com.MKMAndroid.classes.ShoppingCart;
import com.MKMAndroid.classes.ShoppingCartCheckOutAsync;
import com.smaato.soma.BannerView;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShoppingCartFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener
{
	private View rootView = null;
	private ProgressDialog dialog = null;
	private ShoppingCartActivity activity = null;
	
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "1";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ShoppingCartFragment newInstance(int sectionNumber)
    {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ShoppingCartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
    	View rootView = inflater.inflate(R.layout.fragment_shoppingcarts, container, false);
    	this.rootView = rootView;
    	
    	this.activity = (ShoppingCartActivity) getActivity();

        //acció buto de cercar
    	Button checkoutButton = (Button) rootView.findViewById(R.id.buttonCheckOut);
    	checkoutButton.setOnClickListener(this);
    	
    	//If there aren't shopping carts, I hide the button
    	if(this.activity.getShoppingCarts().size()<1){
    		//checkoutButton.setVisibility(View.INVISIBLE);
    		ViewManager viewManager = (ViewManager) checkoutButton.getParent();
			viewManager.removeView(checkoutButton);
    	}
    	
    	ListView listShoppingCarts = (ListView) rootView.findViewById(R.id.listViewArticlesShoppingCart);
    	TextView numOrders = (TextView) rootView.findViewById(R.id.textViewNumOrderValue);
    	TextView numArticlesValue = (TextView) rootView.findViewById(R.id.textViewNumArticlesValue);
    	TextView numArticlesValueValue = (TextView) rootView.findViewById(R.id.textViewArticlesValueValue);
    	TextView shipCost = (TextView) rootView.findViewById(R.id.textViewShipCostValue);
    	TextView totalCost = (TextView) rootView.findViewById(R.id.textViewTotalCost);
        ArrayList<ShoppingCart> shoppingCarts = this.activity.getShoppingCarts();
        
        numOrders.setText(String.valueOf(this.activity.getShoppingCarts().size()));
        
        int numTotalArticles = 0;
        float articlesValues = 0;
        float shipCostValues = 0;
        
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        
        ArrayList<ShoppingCart> carts =this.activity.getShoppingCarts();
        for(int x=0; x < carts.size(); x++){
        	ArrayList<Article> articles = carts.get(x).getArticles();
        	
        	for(int y=0; y < articles.size(); y++){
        		numTotalArticles = numTotalArticles + articles.get(y).getCount();
        	}

        	articlesValues = articlesValues + carts.get(x).getArticleValue();
        	shipCostValues = shipCostValues + carts.get(x).getShippingMethod().getPrice();
        }
        
        numArticlesValue.setText(String.valueOf(numTotalArticles));
        numArticlesValueValue.setText(String.valueOf(df.format(articlesValues)));
        shipCost.setText(String.valueOf(df.format(shipCostValues)));
        totalCost.setText(String.valueOf(df.format(shipCostValues + articlesValues)));
        
        if (shoppingCarts.size()>0){
        	AdapterShoppingCart adapter = new AdapterShoppingCart(this.activity, shoppingCarts);
        	listShoppingCarts.setAdapter(adapter);
        	listShoppingCarts.setOnItemClickListener(this);
        }
        
        listShoppingCarts.setEmptyView(rootView.findViewById(R.id.textNoItemFoundProducts));
        
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
	public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) {
		
		ShoppingCart shoppingCartSelected = (ShoppingCart)parent.getItemAtPosition(position);
    	ShoppingCartArticlesFragment detailsFragment = new ShoppingCartArticlesFragment();
    	
    	activity.setShoppingCartSelected(shoppingCartSelected);
    	
        Bundle args = new Bundle();
        args.putParcelable("shoppingCart", shoppingCartSelected);
        detailsFragment.setArguments(args);
        
        FragmentTransaction transaction = this.activity.getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.container, detailsFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
		
	}

	@Override
	public void onClick(View view) {
		DialogPassword customDialog = new DialogPassword(this.activity,1);
		customDialog.show();
	}

}
