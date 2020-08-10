package com.MKMAndroid.fragments;

/**
 * Created by Arnau on 9/02/14.
 */

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.MKMAndroid.activities.OrderActivity;
import com.MKMAndroid.app.R;
import com.MKMAndroid.classes.AdapterShoppingCartArticles;
import com.MKMAndroid.classes.Article;
import com.MKMAndroid.classes.Constants;
import com.MKMAndroid.classes.DialogGeneral;
import com.MKMAndroid.classes.DialogReason;
import com.MKMAndroid.classes.Order;
import com.MKMAndroid.classes.OrderRequest;
import com.MKMAndroid.classes.PutOrderAsync;
import com.MKMAndroid.classes.Seller;
import com.MKMAndroid.classes.State;
import com.smaato.soma.BannerView;

/**
 * A placeholder fragment containing a simple view.
 */
public class OrderDetailsFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener
{
	private View rootView = null;
	private ProgressDialog dialog = null;
	private OrderActivity activity = null;
	private Order orderSelected = null;
	
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "1";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static OrderDetailsFragment newInstance(int sectionNumber)
    {
        OrderDetailsFragment fragment = new OrderDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public OrderDetailsFragment() {
    	
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
    	View rootView = inflater.inflate(R.layout.fragment_order_details, container, false);
    	this.activity = (OrderActivity) getActivity();
    	this.orderSelected = this.activity.getOrderSelected();
    	int actor = this.activity.getActor();
    	this.rootView = rootView;
    	this.dialog = this.activity.getDialog();
    	Seller infoUser = null;
    	   	

        //acció buto de cercar
    	ListView listArticles = (ListView) rootView.findViewById(R.id.listArticles);
    	TextView userName = (TextView) rootView.findViewById(R.id.textViewUserNameValue);
    	TextView userNameLabel = (TextView) rootView.findViewById(R.id.textViewUserNameLabel);
    	TextView itemsValue = (TextView) rootView.findViewById(R.id.textViewItemValue);
    	TextView shippingCostValue = (TextView) rootView.findViewById(R.id.textViewShippingCost);
    	TextView shippingMethod = (TextView) rootView.findViewById(R.id.textViewDescriptionShippingMethod);
    	TextView totalCost = (TextView) rootView.findViewById(R.id.textViewTotalCost);
    	TextView numItems = (TextView) rootView.findViewById(R.id.textViewContentsValue);
    	TextView fullName = (TextView) rootView.findViewById(R.id.textViewFullNameValue);
    	TextView street = (TextView) rootView.findViewById(R.id.textViewStreetValue);
    	TextView zipCode = (TextView) rootView.findViewById(R.id.textViewZipCodeValue);
    	TextView city = (TextView) rootView.findViewById(R.id.textViewCityValue);
    	TextView country = (TextView) rootView.findViewById(R.id.textViewCountryValue);
    	ImageView sellerReputation = (ImageView)rootView.findViewById(R.id.imageReputationSeller);
    	ImageView sellerCountry = (ImageView)rootView.findViewById(R.id.imageCountrySeller);
    	LinearLayout shippingLayout = (LinearLayout)rootView.findViewById(R.id.shippingAddressLayout);
    	LinearLayout orderDetailsLayout = (LinearLayout)rootView.findViewById(R.id.orderDetailsLayout);
    	LinearLayout layoutButtons = (LinearLayout)rootView.findViewById(R.id.layout_buttons);
    	Button button = (Button) rootView.findViewById(R.id.buttonOrderDetails);
    	Button buttonCancel = (Button) rootView.findViewById(R.id.buttonOrderCancel);
    	button.setOnClickListener(this);
    	buttonCancel.setOnClickListener(this);
        
    	State state = this.orderSelected.getState();
    	ViewManager viewManager;

    	//If I'm the seller
    	if(actor==1){
    		//I need put the label and the values for buyer
    		userNameLabel.setText(this.activity.getStringResourceByName("buyer_label"));
    		userName.setText(this.orderSelected.getBuyerInfo().getBasicInfo().getUsername());
    		infoUser = this.orderSelected.getBuyerInfo().getBasicInfo();
    		
    		//Depends on the status of the order, change the label of the action button
    		if(state.getState().compareTo("bought")==0 || state.getState().compareTo("paid")==0){
    			fullName.setText(this.orderSelected.getBuyerInfo().getAddress().getFullName());
    			street.setText(this.orderSelected.getBuyerInfo().getAddress().getStreet());
    			zipCode.setText(this.orderSelected.getBuyerInfo().getAddress().getZip());
    			city.setText(this.orderSelected.getBuyerInfo().getAddress().getCity());
    			country.setText(this.orderSelected.getBuyerInfo().getAddress().getCountry());
    			
    			if(state.getState().compareTo("paid")==0){
    				viewManager = (ViewManager) button.getParent();
        			viewManager.removeView(buttonCancel);
    				button.setText(this.activity.getStringResourceByName("confirm_shipment"));
    			}else{
    				viewManager = (ViewManager) button.getParent();
        			viewManager.removeView(button);
    				buttonCancel.setText(this.activity.getStringResourceByName("request_cancellation"));
    				buttonCancel.getLayoutParams().width = LayoutParams.FILL_PARENT;
    				//buttonCancel.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
    			}
    		}else{
    			orderDetailsLayout.removeView(shippingLayout);
    			viewManager = (ViewManager) button.getParent();
    			viewManager.removeView(button);
    			viewManager.removeView(buttonCancel);
    		}
    	}
    	
    	//If I'm the buyer
    	if(actor==2){
    		//I need put the label and the values for buyer
    		userNameLabel.setText(this.activity.getStringResourceByName("seller_label"));
    		userName.setText(this.orderSelected.getSellerInfo().getBasicInfo().getUsername());
    		infoUser = this.orderSelected.getSellerInfo().getBasicInfo();
    		
    		if(state.getState().compareTo("cancelled")!=0){
	    		if(state.getState().compareTo("bought")==0){
	    			fullName.setText(this.orderSelected.getSellerInfo().getAddress().getFullName());
	    			street.setText(this.orderSelected.getSellerInfo().getAddress().getStreet());
	    			zipCode.setText(this.orderSelected.getSellerInfo().getAddress().getZip());
	    			city.setText(this.orderSelected.getSellerInfo().getAddress().getCity());
	    			country.setText(this.orderSelected.getSellerInfo().getAddress().getCountry());
	    			
	    			//button.setText(this.activity.getStringResourceByName("checkout"));
	    			viewManager = (ViewManager) button.getParent();
	    			viewManager.removeView(button);
	    			viewManager.removeView(buttonCancel);
	    			
	    		}else if(state.getState().compareTo("paid")==0){
	    			fullName.setText(this.orderSelected.getSellerInfo().getAddress().getFullName());
	    			street.setText(this.orderSelected.getSellerInfo().getAddress().getStreet());
	    			zipCode.setText(this.orderSelected.getSellerInfo().getAddress().getZip());
	    			city.setText(this.orderSelected.getSellerInfo().getAddress().getCity());
	    			country.setText(this.orderSelected.getSellerInfo().getAddress().getCountry());
	    			
	    			viewManager = (ViewManager) button.getParent();
        			viewManager.removeView(button);
	    			buttonCancel.setText(this.activity.getStringResourceByName("request_cancellation"));
	    			buttonCancel.getLayoutParams().width = LayoutParams.FILL_PARENT;
	    			//buttonCancel.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	    		}else if(state.getState().compareTo("sent")==0){
	    			fullName.setText(this.orderSelected.getSellerInfo().getAddress().getFullName());
	    			street.setText(this.orderSelected.getSellerInfo().getAddress().getStreet());
	    			zipCode.setText(this.orderSelected.getSellerInfo().getAddress().getZip());
	    			city.setText(this.orderSelected.getSellerInfo().getAddress().getCity());
	    			country.setText(this.orderSelected.getSellerInfo().getAddress().getCountry());
	    			
	    			viewManager = (ViewManager) button.getParent();
	    			viewManager.removeView(buttonCancel);
	    			button.setText(this.activity.getStringResourceByName("confirm_arrival"));
	    			
	    		}else{
	    			orderDetailsLayout.removeView(shippingLayout);
	    			viewManager = (ViewManager) button.getParent();
	    			viewManager.removeView(button);
	    			viewManager.removeView(buttonCancel);
	    		}
    		}
    		else
    		{
    			orderDetailsLayout.removeView(shippingLayout);
    			viewManager = (ViewManager) button.getParent();
    			viewManager.removeView(button);
    			viewManager.removeView(buttonCancel);
    		}
    	}
    		
    	DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        
        ArrayList<Article> articles = this.orderSelected.getArticles();
    	int numArticles = 0;
    	for(int x=0; x < articles.size(); x++){
    		numArticles = numArticles + articles.get(x).getCount();
    	}
        
    	itemsValue.setText(String.valueOf(df.format(this.orderSelected.getArticleValue())));
    	shippingCostValue.setText(String.valueOf(df.format(this.orderSelected.getShippingMethod().getPrice())));
    	shippingMethod.setText(this.orderSelected.getShippingMethod().getName());
    	totalCost.setText(String.valueOf(df.format(this.orderSelected.getTotalValue())));
    	numItems.setText(String.valueOf(numArticles));
    	
    	AdapterShoppingCartArticles adapter = new AdapterShoppingCartArticles(this.activity, this.orderSelected.getArticles(), 
    			this.orderSelected.getProducts());
    	listArticles.setAdapter(adapter);
    	listArticles.setOnItemClickListener(this);
    	
    	
    	if (infoUser!=null) {

            if (infoUser.getCountry().equals("PT")) {
                sellerCountry.setImageResource(R.drawable.portugal);
            } else if (infoUser.getCountry().equals("ES")) {
                sellerCountry.setImageResource(R.drawable.espanya);
            } else if (infoUser.getCountry().equals("GB")) {
                sellerCountry.setImageResource(R.drawable.anglaterra);
            } else if (infoUser.getCountry().equals("FR")) {
                sellerCountry.setImageResource(R.drawable.francia);
            } else if (infoUser.getCountry().equals("D")) {
            	sellerCountry.setImageResource(R.drawable.alemania);
            } else if (infoUser.getCountry().equals("DK")) {
                sellerCountry.setImageResource(R.drawable.dinamarca);
            } else if (infoUser.getCountry().equals("AT")) {
                sellerCountry.setImageResource(R.drawable.austria);
            } else if (infoUser.getCountry().equals("CH")) {
                sellerCountry.setImageResource(R.drawable.suisa);
            } else if (infoUser.getCountry().equals("SI")) {
                sellerCountry.setImageResource(R.drawable.eslovenia);
            } else if (infoUser.getCountry().equals("BE")) {
                sellerCountry.setImageResource(R.drawable.belgica);
            } else if (infoUser.getCountry().equals("FI")) {
                sellerCountry.setImageResource(R.drawable.finlandia);
            } else if (infoUser.getCountry().equals("NL")) {
                sellerCountry.setImageResource(R.drawable.holanda);
            } else if (infoUser.getCountry().equals("PL")) {
                sellerCountry.setImageResource(R.drawable.polonia);
            } else if (infoUser.getCountry().equals("CZ")) {
                sellerCountry.setImageResource(R.drawable.republicatxeca);
            } else if (infoUser.getCountry().equals("GR")) {
                sellerCountry.setImageResource(R.drawable.grecia);
            }else if (infoUser.getCountry().equals("IT")) {
            	sellerCountry.setImageResource(R.drawable.italia);
            }

            if(infoUser.getReputation()==1){
                sellerReputation.setImageResource(R.drawable.reputation1);
            }else if(infoUser.getReputation()==2){
                sellerReputation.setImageResource(R.drawable.reputation2);
            }else if(infoUser.getReputation()==3) {
                sellerReputation.setImageResource(R.drawable.reputation3);
            }else if(infoUser.getReputation()==4) {
            	sellerReputation.setImageResource(R.drawable.reputation0);
            }else if(infoUser.getReputation()==0) {
            	if(infoUser.getRiskGroup()==1){
            		sellerReputation.setImageResource(R.drawable.no_reputation);
            	}else{
            		sellerReputation.setImageResource(R.drawable.reputation0);
            	}
            }
        }
    	
    	
    	////WAITING FOR THE NEW API VERSION////
    	viewManager = (ViewManager) layoutButtons.getParent();
    	viewManager.removeView(layoutButtons);
    	///////////////////////////////////////
        
        bannerSetup(rootView);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
    }

    public void bannerSetup(View view){
    	/*BannerView mBanner = (BannerView) view.findViewById((R.id.banner_orderDetail));
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
		/*Article articleSelected = (Article)parent.getItemAtPosition(position);
		
		DialogModifyArticleShoppingCart customDialog = new DialogModifyArticleShoppingCart(this.activity, 
															this.shoppingCartSelected, articleSelected, this.activity);

		customDialog.show();*/ 
		
	}

	@Override
	public void onClick(View view) {
		State state = this.orderSelected.getState();
		
		if(this.activity.getActor()==1){
			if(state.getState().compareTo("bought")==0 || state.getState().compareTo("paid")==0){
				if(view.getId()==R.id.buttonOrderDetails){
					/*OrderRequest request = new OrderRequest("send","",0); 
					PutOrderAsync updater = new PutOrderAsync(this.activity, this.dialog, request);
					String url = this.activity.generateUrlConsulta()+"/"+String.valueOf(this.activity.getOrderSelected().getIdOrder());
					updater.execute(url);*/
					DialogGeneral dialogGeneral = new DialogGeneral(this.activity, 2);
					dialogGeneral.show();
				}
				
				if(view.getId()==R.id.buttonOrderCancel){

					DialogReason dialogReason = new DialogReason(this.activity, 1);
					dialogReason.show();
					//Show popup to write the reason on the cancellation
				}
			}
		}
		
		if(this.activity.getActor()==2){
			if(state.getState().compareTo("paid")==0){
				//DialogGeneral dialogGeneral = new DialogGeneral(this.activity, 5);
				//dialogGeneral.show();
				//show the dialog where you can write the reason
				DialogReason dialogReason = new DialogReason(this.activity, 1);
				dialogReason.show();
			}
			if(state.getState().compareTo("sent")==0){
				/*OrderRequest request = new OrderRequest("confirmReception","",0); 
				PutOrderAsync updater = new PutOrderAsync(this.activity, this.dialog, request);
				String url = this.activity.generateUrlConsulta()+"/"+String.valueOf(this.activity.getOrderSelected().getIdOrder());
				updater.execute(url);*/
				DialogGeneral dialogGeneral = new DialogGeneral(this.activity, 1); 
				dialogGeneral.show();
			}
			
		}
	}
	
	

}
