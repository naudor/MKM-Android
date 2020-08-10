package com.MKMAndroid.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.MKMAndroid.app.R;
import com.MKMAndroid.classes.ActivityWithLoading;
import com.MKMAndroid.classes.Article;
import com.MKMAndroid.classes.Constants;
import com.MKMAndroid.classes.DeleteShoppingCartAsync;
import com.MKMAndroid.classes.DialogDeleteShoppingCart;
import com.MKMAndroid.classes.DialogGeneral;
import com.MKMAndroid.classes.DialogMessage;
import com.MKMAndroid.classes.PutShoppingCartAsync;
import com.MKMAndroid.classes.ShoppingCart;
import com.MKMAndroid.classes.ShoppingCartRequest;
import com.MKMAndroid.fragments.NavigationDrawerFragment;
import com.MKMAndroid.fragments.OrderDetailsFragment;
import com.MKMAndroid.fragments.ProductsFragment;
import com.MKMAndroid.fragments.ShoppingCartFragment;

public class ShoppingCartActivity extends ActionBarActivity
        implements ActivityWithLoading, NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private ProgressDialog dialog;
    private ArrayList<ShoppingCart> shoppingCarts;
    private ShoppingCart shoppingCartSelected;
    
    //private ShoppingCartRequest request;
    /*private String urlConsulta = Constants.domain + Constants.userMKM +"/" +
                                    Constants.keyMKM + "/articles/";*/

   

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //setContentView(R.layout.fragment_products);

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

        ShoppingCartFragment shoppingCartFragment = (ShoppingCartFragment)
                getSupportFragmentManager().findFragmentById(R.layout.fragment_shoppingcarts);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        this.shoppingCarts = intent.getParcelableArrayListExtra("shoppingCarts");

        if (shoppingCartFragment != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            //productFrag.setUrlFinal(arguments.getString("url"));
        } else {

        	shoppingCartFragment = new ShoppingCartFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //transaction.add(R.id.container, articlesFragment, "llistaArticles");
            transaction.replace(R.id.container, shoppingCartFragment, "shoppingCart_details");
            transaction.addToBackStack(null);
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

	   MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.shoppingcarts, menu);
       
      /* if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.articles, menu);
            restoreActionBar();
            return true;
        }*/

        return super.onCreateOptionsMenu(menu);

       /*MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.articles, menu);
       return super.onCreateOptionsMenu(menu);*/
	   
	   //return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
    	/*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();*/
    	if (mNavigationDrawerFragment.onOptionsItemSelected(item)){
         	return true;
         }
    	 
    	switch (item.getItemId()) 
    	{
	    	case R.id.action_discard:
	    		ShoppingCartFragment shoppingCartFragment = (ShoppingCartFragment)
	    			getSupportFragmentManager().findFragmentByTag("shoppingCart_details");

	    		//If I pressed the button from list ShoppingCart Fragment, I'm going to remove all ShoppingCarts
	 			if(shoppingCartFragment.isVisible()){
	 				/*DialogDeleteShoppingCart dialogDelete = new DialogDeleteShoppingCart(this, true);
	 				dialogDelete.show();*/
	 				DialogGeneral dialogGeneral = new DialogGeneral(this, 3); 
	 				dialogGeneral.show();
	 			}else{
	 				/*DialogDeleteShoppingCart dialogDelete = new DialogDeleteShoppingCart(this, false);
	 				dialogDelete.show();*/
	 				DialogGeneral dialogGeneral = new DialogGeneral(this, 4); 
	 				dialogGeneral.show();
	 			}
	 		return true;
	        
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
                 .replace(R.id.container, ShoppingCartFragment.newInstance(position + 1))
                 .commit();
    }
    
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(getStringResourceByName(title));
    }
    
    public String generateUrlConsulta()
    {
    	String FILENAME = Constants.file_settings_name;
    	SharedPreferences settings = getSharedPreferences(FILENAME, 0);
    	String keyMKM = settings.getString("apiKey",null);
    	String userMKM = settings.getString("userName",null);
    	
    	return Constants.domain + userMKM +"/" + keyMKM + "/shoppingcart";
    }
    
    public String getUrlCheckout(){
		String FILENAME = Constants.file_settings_name;
    	SharedPreferences settings = getSharedPreferences(FILENAME, 0);
    	String keyMKM = settings.getString("apiKey",null);
    	String userMKM = settings.getString("userName",null);
    	
    	return Constants.domain + userMKM +"/" + keyMKM + "/shoppingcart/checkout";
	}
    
    public void setShoppingCarts(ArrayList<ShoppingCart> shoppingCarts){
    	this.shoppingCarts = shoppingCarts;
    }
    
    public ArrayList<ShoppingCart> getShoppingCarts(){
    	return this.shoppingCarts;
    }
    
    public ProgressDialog getDialog(){
    	return this.dialog;
    }

	@Override
	public void callBack(ArrayList args) {
		ArrayList<ShoppingCart> shoppingCarts = new ArrayList<ShoppingCart>();
    	for(int x=0; x < args.size();x++){
    		shoppingCarts.add((ShoppingCart)args.get(x));
    	}
    	
    	this.shoppingCarts = shoppingCarts;
    	
    	ShoppingCartFragment fragment = (ShoppingCartFragment)
                getSupportFragmentManager().findFragmentByTag("shoppingCart_details");
    	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    	
    	//I need refresh the fragment
    	if(!fragment.isVisible()){
    		onBackPressed();
    	}else{
    		transaction.detach(fragment);
    		transaction.attach(fragment);
    		transaction.commit();
    	}
		
	}
	
	public ShoppingCart getShoppingCartSelected() {
		return shoppingCartSelected;
	}
	
	public void setShoppingCartSelected(ShoppingCart shoppingCartSelected) {
			this.shoppingCartSelected = shoppingCartSelected;
	}
	
	public void deleteAllShoppingCarts(){
		DeleteShoppingCartAsync updater = new DeleteShoppingCartAsync(this, this.dialog);
		
		if(this.checkInternetConnection()){
			updater.execute(this.generateUrlConsulta());
		}
	}
	
	public void deleteShoppingCartSelected(){
		//But if I pressed the button from detail ShoppingCart Fragment, I'm going to remove only
		//the selected shoppingCart, removing it articles
		ArrayList<Article> articles = this.shoppingCartSelected.getArticles();
		ArrayList<ShoppingCartRequest> requests = new ArrayList<ShoppingCartRequest>();
		
		for(int x=0; x < articles.size();x++){
			requests.add(new ShoppingCartRequest("remove", articles.get(x).getIdArticle(), 
					articles.get(x).getCount()));
		}
		
		PutShoppingCartAsync updater = new PutShoppingCartAsync(this, this.dialog, requests);
		
		if(this.checkInternetConnection()){
			updater.execute(generateUrlConsulta());
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
