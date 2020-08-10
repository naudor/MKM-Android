package com.MKMAndroid.fragments;

/**
 * Created by Arnau on 9/02/14.
 */

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.MKMAndroid.activities.ShoppingCartActivity;
import com.MKMAndroid.app.R;
import com.MKMAndroid.classes.AdapterShoppingCartArticles;
import com.MKMAndroid.classes.Article;
import com.MKMAndroid.classes.Constants;
import com.MKMAndroid.classes.DialogModifyArticleShoppingCart;
import com.MKMAndroid.classes.ShoppingCart;
import com.smaato.soma.BannerView;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShoppingCartArticlesFragment extends Fragment implements AdapterView.OnItemClickListener
{
	private View rootView = null;
	private Dialog dialog = null;
	private ShoppingCartActivity activity = null;
	private ShoppingCart shoppingCartSelected = null;
	
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "1";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ShoppingCartArticlesFragment newInstance(int sectionNumber)
    {
        ShoppingCartArticlesFragment fragment = new ShoppingCartArticlesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ShoppingCartArticlesFragment() {
    	
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
    	View rootView = inflater.inflate(R.layout.fragment_shoppingcart_details, container, false);
    	this.shoppingCartSelected = this.getArguments().getParcelable("shoppingCart");
    	this.rootView = rootView;
    	
    	this.activity = (ShoppingCartActivity) getActivity();

        //acció buto de cercar
    	ListView listArticles = (ListView) rootView.findViewById(R.id.listArticles);
    	TextView sellerName = (TextView) rootView.findViewById(R.id.textViewUserName);
    	TextView itemsValue = (TextView) rootView.findViewById(R.id.textViewItemValue);
    	TextView shippingCostValue = (TextView) rootView.findViewById(R.id.textViewShippingCost);
    	TextView shippingMethod = (TextView) rootView.findViewById(R.id.textViewDescriptionShippingMethod);
    	TextView totalCost = (TextView) rootView.findViewById(R.id.textViewTotalCost);
    	TextView numItems = (TextView) rootView.findViewById(R.id.textViewContentsValue);
    	ImageView sellerReputation = (ImageView)rootView.findViewById(R.id.imageReputationSeller);
    	ImageView sellerCountry = (ImageView)rootView.findViewById(R.id.imageCountrySeller);
        
    	sellerName.setText(this.shoppingCartSelected.getSeller().getUsername());
    	itemsValue.setText(String.valueOf(this.shoppingCartSelected.getArticleValue()));
    	shippingCostValue.setText(String.valueOf(this.shoppingCartSelected.getShippingMethod().getPrice()));
    	shippingMethod.setText(this.shoppingCartSelected.getShippingMethod().getName());
    	totalCost.setText(String.valueOf(this.shoppingCartSelected.getTotalValue()));
    	
    	int numArticles = 0;
    	ArrayList<Article> articles = this.shoppingCartSelected.getArticles();
    	for (int x=0; x < articles.size(); x++){
    		numArticles = numArticles + articles.get(x).getCount();
    	}
    	numItems.setText(String.valueOf(numArticles));
    	
    	AdapterShoppingCartArticles adapter = new AdapterShoppingCartArticles(this.activity, this.shoppingCartSelected.getArticles(), 
    			this.shoppingCartSelected.getProducts());
    	listArticles.setAdapter(adapter);
    	listArticles.setOnItemClickListener(this);
    	
    	if (this.shoppingCartSelected.getSeller()!=null) {

            if (this.shoppingCartSelected.getSeller().getCountry().equals("PT")) {
                sellerCountry.setImageResource(R.drawable.portugal);
            } else if (this.shoppingCartSelected.getSeller().getCountry().equals("ES")) {
                sellerCountry.setImageResource(R.drawable.espanya);
            } else if (this.shoppingCartSelected.getSeller().getCountry().equals("GB")) {
                sellerCountry.setImageResource(R.drawable.anglaterra);
            } else if (this.shoppingCartSelected.getSeller().getCountry().equals("FR")) {
                sellerCountry.setImageResource(R.drawable.francia);
            } else if (this.shoppingCartSelected.getSeller().getCountry().equals("D")) {
            	sellerCountry.setImageResource(R.drawable.alemania);
            } else if (this.shoppingCartSelected.getSeller().getCountry().equals("DK")) {
                sellerCountry.setImageResource(R.drawable.dinamarca);
            } else if (this.shoppingCartSelected.getSeller().getCountry().equals("AT")) {
                sellerCountry.setImageResource(R.drawable.austria);
            } else if (this.shoppingCartSelected.getSeller().getCountry().equals("CH")) {
                sellerCountry.setImageResource(R.drawable.suisa);
            } else if (this.shoppingCartSelected.getSeller().getCountry().equals("SI")) {
                sellerCountry.setImageResource(R.drawable.eslovenia);
            } else if (this.shoppingCartSelected.getSeller().getCountry().equals("BE")) {
                sellerCountry.setImageResource(R.drawable.belgica);
            } else if (this.shoppingCartSelected.getSeller().getCountry().equals("FI")) {
                sellerCountry.setImageResource(R.drawable.finlandia);
            } else if (this.shoppingCartSelected.getSeller().getCountry().equals("NL")) {
                sellerCountry.setImageResource(R.drawable.holanda);
            } else if (this.shoppingCartSelected.getSeller().getCountry().equals("PL")) {
                sellerCountry.setImageResource(R.drawable.polonia);
            } else if (this.shoppingCartSelected.getSeller().getCountry().equals("CZ")) {
                sellerCountry.setImageResource(R.drawable.republicatxeca);
            } else if (this.shoppingCartSelected.getSeller().getCountry().equals("GR")) {
                sellerCountry.setImageResource(R.drawable.grecia);
            }else if (this.shoppingCartSelected.getSeller().getCountry().equals("IT")) {
            	sellerCountry.setImageResource(R.drawable.italia);
            }

            if(this.shoppingCartSelected.getSeller().getReputation()==1){
                sellerReputation.setImageResource(R.drawable.reputation1);
            }else if(this.shoppingCartSelected.getSeller().getReputation()==2){
                sellerReputation.setImageResource(R.drawable.reputation2);
            }else if(this.shoppingCartSelected.getSeller().getReputation()==3) {
                sellerReputation.setImageResource(R.drawable.reputation3);
            }else if(this.shoppingCartSelected.getSeller().getReputation()==4) {
            	sellerReputation.setImageResource(R.drawable.reputation0);
            }else if(this.shoppingCartSelected.getSeller().getReputation()==0) {
            	if(this.shoppingCartSelected.getSeller().getRiskGroup()==1){
            		sellerReputation.setImageResource(R.drawable.no_reputation);
            	}else{
            		sellerReputation.setImageResource(R.drawable.reputation0);
            	}
            }
        }
    	
    	
        //ArrayList<ShoppingCart> shoppingCarts = this.activity.getShoppingCarts();
        
        /*AdapterShoppingCart adapter = new AdapterShoppingCart(this.activity, shoppingCarts);
        listShoppingCarts.setAdapter(adapter);*/
        
        
        bannerSetup(rootView);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
    }

    public void bannerSetup(View view){
    	/*BannerView mBanner = (BannerView) view.findViewById((R.id.banner_shoppingcart_articles));
        mBanner.getAdSettings().setPublisherId(Constants.publisherId);
        mBanner.getAdSettings().setAdspaceId(Constants.adspaceId);
        mBanner.setAutoReloadFrequency(Constants.bannerRefreshTime);
        mBanner.getUserSettings().setKeywordList(Constants.keywordList); 
        mBanner.getUserSettings().setSearchQuery(Constants.searchQuery);
        mBanner.asyncLoadNewBanner();*/
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Article articleSelected = (Article)parent.getItemAtPosition(position);
		
		DialogModifyArticleShoppingCart customDialog = new DialogModifyArticleShoppingCart(this.activity, 
															this.shoppingCartSelected, articleSelected, this.activity);

		customDialog.show(); 
		
	}

}
