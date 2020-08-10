package com.MKMAndroid.activities;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.MKMAndroid.app.R;
import com.MKMAndroid.classes.ActivityWithLoading;
import com.MKMAndroid.classes.Article;
import com.MKMAndroid.classes.Constants;
import com.MKMAndroid.classes.DialogMessage;
import com.MKMAndroid.classes.Order;
import com.MKMAndroid.classes.OrderRequest;
import com.MKMAndroid.classes.Product;
import com.MKMAndroid.classes.ProductsUpdater;
import com.MKMAndroid.classes.PutOrderAsync;
import com.MKMAndroid.fragments.NavigationDrawerFragment;
import com.MKMAndroid.fragments.OrderDetailsFragment;
import com.MKMAndroid.fragments.ProductsFragment;


public class OrderActivity extends ActionBarActivity
        implements ActivityWithLoading, NavigationDrawerFragment.NavigationDrawerCallbacks {
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ProgressDialog dialog;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private String urlConsulta;
    private ArrayList<Article> articles;
    private Order orderSelected;
    private int actor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        this.orderSelected = intent.getParcelableExtra("order");
        this.actor = intent.getIntExtra("actor", 0);
        this.dialog = new ProgressDialog(this);
        this.dialog.setCancelable(false);

        setContentView(R.layout.activity_order);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        
        //Set the title
        mTitle = getTitle();
        setTitle(mTitle + " ("+ String.valueOf(orderSelected.getIdOrder())+")");
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //////////////////////////////NO ESBORRAR///////////////////////////////////////////////
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        ///////////////////////////////////////////////////////////////////////////////////////

        OrderDetailsFragment fragment = (OrderDetailsFragment)
                getSupportFragmentManager().findFragmentById(R.layout.fragment_order_details);



        if (fragment != null) {
            // If article frag is available, we're in two-pane layout...
            // Call a method in the ArticleFragment to update its content
            //productFrag.setUrlFinal(arguments.getString("url"));
        } else {
            // Otherwise, we're in the one-pane layout and must swap frags...
            // Create fragment and give it an argument for the selected article
        	OrderDetailsFragment orderDetailsFragment = new OrderDetailsFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.container, orderDetailsFragment, "order_detail");
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, OrderDetailsFragment.newInstance(position + 1))
                .commit();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.products, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);*/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	if (mNavigationDrawerFragment.onOptionsItemSelected(item)){
         	return true;
         }
    	
    	switch (item.getItemId()) 
    	{
        	case android.R.id.home:
        		onBackPressed();
        		return true;
        	default:
        		return super.onOptionsItemSelected(item);
        }
    	
        //return true;
    }

    
    @Override
    public void onBackPressed(){
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        }
        else {
            super.onBackPressed();
        }
    }


    public void setUrlConsulta(String url)
    {
        this.urlConsulta = url;
    }

    public String getUrlConsulta()
    {
        return this.urlConsulta;
    }

    public Order getOrderSelected()
    {
        return this.orderSelected;
    }
    
    public int getActor(){
    	return this.actor;
    }

    public void onResume()
    {
        super.onResume();
        if (this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
    }

    public ProgressDialog getDialog(){

        return this.dialog;
    }

    public String getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }
    
    
    public void callBack(ArrayList args){
    	
    	ArrayList<Order> ordres = new ArrayList<Order>();
    	for(int x=0; x < args.size();x++){
    		ordres.add((Order)args.get(x));
    	}
    	
    	this.orderSelected = ordres.get(0);
    	
    	OrderDetailsFragment fragment = (OrderDetailsFragment)
                getSupportFragmentManager().findFragmentByTag("order_detail");
    	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    	
    	//I need refresh the fragment
    	if(fragment==null){
    		fragment = new OrderDetailsFragment();
    	}else{
    		transaction.detach(fragment);
    		transaction.attach(fragment);
    		transaction.commit();
    	}
   }
    
    public String generateUrlConsulta(){
    	String FILENAME = Constants.file_settings_name;
    	SharedPreferences settings = getSharedPreferences(FILENAME, 0);
    	String keyMKM = settings.getString("apiKey",null);
    	String userMKM = settings.getString("userName",null);
    	
    	return Constants.domain + userMKM +"/" + keyMKM + "/order";
    }
    
    public void confirmReception(){
		OrderRequest request = new OrderRequest("confirmReception","",0); 
			PutOrderAsync updater = new PutOrderAsync(this, this.getDialog(), request);
			String url = (this.generateUrlConsulta()+"/"+
							String.valueOf(this.orderSelected.getIdOrder()));
			
			if(this.checkInternetConnection()){
				updater.execute(url);
			}
	}
    
    public void confirmSend(){
    	OrderRequest request = new OrderRequest("send","",0); 
		PutOrderAsync updater = new PutOrderAsync(this, this.dialog, request);
		String url = this.generateUrlConsulta()+"/"+
						String.valueOf(this.orderSelected.getIdOrder());
		
		if(this.checkInternetConnection()){
			updater.execute(url);
		}
    }
    
    public void cancelOrder(String reason){
    	OrderRequest request = new OrderRequest("requestCancellation",reason,0); 
		PutOrderAsync updater = new PutOrderAsync(this, this.dialog, request);
		String url = this.generateUrlConsulta()+"/"+
						String.valueOf(this.orderSelected.getIdOrder());
		
		if(this.checkInternetConnection()){
			updater.execute(url);
		}
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
}