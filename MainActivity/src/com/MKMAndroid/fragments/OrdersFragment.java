package com.MKMAndroid.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.MKMAndroid.activities.OrdersActivity;
import com.MKMAndroid.app.R;
import com.MKMAndroid.classes.AdapterOrders;
import com.MKMAndroid.classes.Constants;
import com.MKMAndroid.classes.Order;
import com.MKMAndroid.classes.OrderUpdaterAsync;
import com.MKMAndroid.classes.OrdersUpdaterListAsync;
import com.MKMAndroid.classes.State;
import com.smaato.soma.BannerView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrdersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class OrdersFragment extends Fragment implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    private static final String ARG_SECTION_NUMBER = "1";
    private ArrayList<Order> orders;
    private OrdersActivity activity;
    private ListView listOrders = null;
    private ProgressDialog dialog;
    private int processes = 0;
    private static final String argumentName = "orderTypeCode";
    private int orderTypeCode = 0;
    ArrayList<Order> ordersFiltered; 

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static OrdersFragment newInstance(int sectionNumber) {
    	OrdersFragment fragment = new OrdersFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    
    
    public OrdersFragment(){}
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_orders, null);

        this.listOrders = (ListView) rootView.findViewById(R.id.listOrders);
        this.activity = (OrdersActivity) this.getActivity();
        this.dialog = activity.getDialog();
        this.orders = this.activity.getOrders();
        
        
        if (container == null) {
        	return null;
        }
        
        //ordersFiltered = this.getFilteredOrders(orders, orderTypeCode);

        AdapterOrders adapter = new AdapterOrders(activity, this.orders, this.activity.getActor());
    	this.listOrders.setAdapter(adapter);
    	adapter.notifyDataSetChanged();
        
        this.listOrders.setClickable(true);
        this.listOrders.setOnScrollListener(this);
        this.listOrders.setOnItemClickListener(this);
        this.listOrders.setEmptyView(rootView.findViewById(R.id.textNoItemsFound));
        this.listOrders.setSelection(0);

        this.bannerSetup(rootView);
        
        this.processes = 0;
        
        return rootView;
    }

    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
*/
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {

        //ArrayList<Article> moreArticles;
       // ArrayList<Article> moreArticlesFiltrats;
        boolean loadMore;

        int ultimRegistreVist= firstVisibleItem + visibleItemCount;
        boolean loadMoreTmp = firstVisibleItem + visibleItemCount >= totalItemCount;

        //I prefered avoid load again the first page onLoad frame
        if (firstVisibleItem>0){
	        if ((this.orders.size() == 100) && (loadMoreTmp)){
	            loadMore = true;
	        }
	        else{
	            loadMore = false;
	        }
	
	        if(loadMore && this.processes==0) {
	           this.processes = 1;
	           OrdersUpdaterListAsync updater = new OrdersUpdaterListAsync(this.activity,this.dialog, 
	        		   this.activity.getActor(), this.listOrders, this.activity.getOrders() ,(ultimRegistreVist - visibleItemCount));
	         
	           String url = this.activity.getUrl()+"/orders/"+this.activity.getActor()+"/"+this.activity.getOrderTypeCode()+"/101";
	           if(this.activity.checkInternetConnection()){
	        	   updater.execute(url);
	           }
	        }
        }
    }

    public ListView getListOrders(){
        return this.listOrders;
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		Order orderSelected = (Order)parent.getItemAtPosition(position);
		
		//***** IMPORTANT *****
		//I would not be necessary, but I need to get the Products of every Order, and I prefer
		//get the products in this step, instead take more time loading all orders and it Products
		String url = this.activity.getUrl() +"/order/" + String.valueOf(orderSelected.getIdOrder());	
        OrderUpdaterAsync updaterOrder = new OrderUpdaterAsync(this.activity, this.activity.getDialog(), this.activity.getActor());
        if(this.activity.checkInternetConnection()){
        	updaterOrder.execute(url);
        }
    	
	}
	
	public void bannerSetup(View view){
    	/*BannerView mBanner = (BannerView) view.findViewById((R.id.banner_orders));
        mBanner.getAdSettings().setPublisherId(Constants.publisherId);
        mBanner.getAdSettings().setAdspaceId(Constants.adspaceId);
        mBanner.setAutoReloadFrequency(Constants.bannerRefreshTime);
        mBanner.getUserSettings().setKeywordList(Constants.keywordList); 
        mBanner.getUserSettings().setSearchQuery(Constants.searchQuery);
        mBanner.asyncLoadNewBanner();*/
    }
	
	
	
	/*public void showArticleDetail(Bitmap bitmap)
	{
		ArticleDetailsFragment detailsFragment = new ArticleDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("articleSelected", this.activity.getArticleSelected());
        args.putParcelable("productSelected", this.activity.getProductSelected());
        args.putParcelable("image", bitmap);
        detailsFragment.setArguments(args);
        
        FragmentTransaction transaction = this.activity.getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.container, detailsFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
		
	}*/

}
