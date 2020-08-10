package com.MKMAndroid.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.MKMAndroid.app.R;
import com.MKMAndroid.classes.ActivityWithLoading;
import com.MKMAndroid.classes.Constants;
import com.MKMAndroid.classes.DialogMessage;
import com.MKMAndroid.classes.Order;
import com.MKMAndroid.fragments.ArticlesFragment;
import com.MKMAndroid.fragments.NavigationDrawerFragment;
import com.MKMAndroid.fragments.OrderDetailsFragment;
import com.MKMAndroid.fragments.OrdersFragment;
import com.MKMAndroid.fragments.ProductsFragment;
import com.MKMAndroid.fragments.ShoppingCartFragment;
import com.smaato.soma.BannerView;


public class OrdersActivity extends ActionBarActivity
							implements ActivityWithLoading, NavigationDrawerFragment.NavigationDrawerCallbacks{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    
    private CharSequence mTitle;
    private ProgressDialog dialog;
    private ArrayList<Order> orders;
    private int orderTypeCode;
    private int actor;
	//private Order orderSelected;

        
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        this.dialog = new ProgressDialog(this);
        this.dialog.setCancelable(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //////////////////////////////NO ESBORRAR///////////////////////////////////////////////
        // Set up the drawer.
           mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        ///////////////////////////////////////////////////////////////////////////////////////
        
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        this.orders = intent.getParcelableArrayListExtra("orders");
        //I need to know if the type of order
        this.actor = extra.getInt("actor");
        this.orderTypeCode = extra.getInt("orderTypeCode");
        
        switch(orderTypeCode){
	        case 1:
	        	mTitle=mTitle + " ("+getStringResourceByName("unpaid")+")";
	        	break;
	        case 2:
	        	mTitle=mTitle + " ("+getStringResourceByName("paid")+")";
	        	break;
	        case 4:
	        	mTitle=mTitle + " ("+getStringResourceByName("shipped")+")";
	        	break;
	        case 8:
	        	mTitle=mTitle + " ("+getStringResourceByName("closed")+")";
	        	break;
	        case 32:
	        	mTitle=mTitle + " ("+getStringResourceByName("cancelled")+")";
	        	break;
	        case 128:
	        	mTitle=mTitle + " ("+getStringResourceByName("lost")+")";
	        	break;
        }
        
        setTitle(mTitle);
        
        OrdersFragment fragment = (OrdersFragment)
                getSupportFragmentManager().findFragmentById(R.layout.fragment_orders);



        if (fragment != null) {
            // If article frag is available, we're in two-pane layout...
            // Call a method in the ArticleFragment to update its content
            //productFrag.setUrlFinal(arguments.getString("url"));
        } else {
            // Otherwise, we're in the one-pane layout and must swap frags...
            // Create fragment and give it an argument for the selected article
        	OrdersFragment orderDetailsFragment = new OrdersFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.container, orderDetailsFragment, "orders");
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }

   public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {

	   /*MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.articles, menu);
       
       if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.articles, menu);
            restoreActionBar();
            return true;
        }

        return super.onCreateOptionsMenu(menu);*/

       /*MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.articles, menu);
       return super.onCreateOptionsMenu(menu);*/
	   
	   return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
    	/*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();*/
    	
    	if (mNavigationDrawerFragment.onOptionsItemSelected(item)){
         	return true;
         }
    	
    	switch (item.getItemId()) 
    	{

	    	/*case R.id.action_filter:
	            FilterFragment filterFragment = new FilterFragment();
	            transaction.replace(R.id.container, filterFragment, "filtre");
	            transaction.addToBackStack(null);
	            transaction.commit();
	            return true;
	            
	    	case R.id.action_legend:
	            LegendFragment legendFragment = new LegendFragment();
	            transaction.replace(R.id.container, legendFragment, "llegenda");
	            transaction.addToBackStack(null);
	            transaction.commit();

	            return true;*/
	        
	    	case android.R.id.home:
	    		onBackPressed();
	    		return true;
	    	
	    	default:
        		return super.onOptionsItemSelected(item);
    	}

    }

   /* @Override
    public void onClick(View view) {

        SettingsFragment settingsFragment = new SettingsFragment();
        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();

        switch(view.getId()) {
            case R.id.buttonFilter:
                break;


            case R.id.buttonResetFilter:
                break;
        }

    }*/

    @Override
    public void onBackPressed(){
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        }
        else {
            super.onBackPressed();
        }
    }

    /**
     * Get the visible Fragment
     * @return Fragment
     */
    public Fragment getVisibleFragment()
    {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for(Fragment fragment : fragments){
            if(fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    public void onResume()
    {
    	super.onResume();
        if (this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
    }
   
    public String getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
    	 FragmentManager fragmentManager = getSupportFragmentManager();
         fragmentManager.beginTransaction()
                 .replace(R.id.container, OrdersFragment.newInstance(position + 1))
                 .commit();
    }
    
    /*public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(getStringResourceByName(title));
    }*/
    
   /* private String generateUrlConsulta()
    {
    	String FILENAME = Constants.file_settings_name;
    	SharedPreferences settings = getSharedPreferences(FILENAME, 0);
    	String keyMKM = settings.getString("apiKey",null);
    	String userMKM = settings.getString("userName",null);
    	
    	return Constants.domain + userMKM +"/" + keyMKM + "/shoppingcart";
    }*/
    
    /*@Override
    public void onTabChanged(String tag) {
        newTab = (TabInfo) this.mapTabInfo.get(tag);

        //I need load the orders from webservice on changing tab
        if(tag.compareTo("unpaid")==0){
        	this.status = 1;		
        }else if (tag.compareTo("paid")==0){
        	this.status = 2;
        }else if (tag.compareTo("sent")==0){
	    	this.status = 4;
        }else if (tag.compareTo("received")==0){
	    	this.status = 8;
        }else if (tag.compareTo("lost")==0){
        	this.status = 32;
        }else if (tag.compareTo("cancelled")==0){
        	this.status = 128;
	    }

        
    	String url = getUrl() +"/orders/" + String.valueOf(this.actor) + "/" + String.valueOf(this.status);	
        OrdersUpdaterAsync updaterOrders = new OrdersUpdaterAsync(this, new ProgressDialog(this));
    	updaterOrders.execute(url);
  
    }*/


    
    public ProgressDialog getDialog(){
    	return this.dialog;
    }

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

	public int getOrderTypeCode() {
		return orderTypeCode;
	}

	public int getActor() {
		return this.actor;
	}
	
    
    public String getUrl(){
    	String FILENAME = Constants.file_settings_name;
     	SharedPreferences settings = this.getSharedPreferences(FILENAME, 0);
     	String keyMKM = settings.getString("apiKey",null);
     	String userMKM = settings.getString("userName",null);
     	
     	String url = Constants.domain + userMKM +"/" + keyMKM;
     	
     	return url;
    }

	@Override
	public void callBack(ArrayList args) {
		ArrayList<Order> newOrders = new ArrayList<Order>();
		
    	for(int x=0; x < args.size();x++){
    		newOrders.add((Order)args.get(x));
    	}
    	
    	this.orders = newOrders;
	}
	
	public void bannerSetup(View view){
    	BannerView mBanner = (BannerView) view.findViewById((R.id.banner_orders));
        mBanner.getAdSettings().setPublisherId(Constants.publisherId);
        mBanner.getAdSettings().setAdspaceId(Constants.adspaceId);
        mBanner.setAutoReloadFrequency(Constants.bannerRefreshTime);
        mBanner.getUserSettings().setKeywordList(Constants.keywordList); 
        mBanner.getUserSettings().setSearchQuery(Constants.searchQuery);
        mBanner.asyncLoadNewBanner();
    }
	
	public boolean checkInternetConnection() 
    {
        ConnectivityManager connec = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isConnected() || mobile.isConnected()){
        	return true;
        }else{
	        DialogMessage alertDialog = new DialogMessage(this, 5);
			alertDialog.show();
			
			return false;
        }
    }
	/*@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    outState.putCharSequence("title", mTitle);
	}*/
}
