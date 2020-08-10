package com.MKMAndroid.activities;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.MKMAndroid.app.R;
import com.MKMAndroid.classes.ActivityWithLoading;
import com.MKMAndroid.classes.Article;
import com.MKMAndroid.classes.ArticlesUpdaterASync;
import com.MKMAndroid.classes.DialogMessage;
import com.MKMAndroid.classes.Product;
import com.MKMAndroid.classes.ProductsUpdater;
import com.MKMAndroid.classes.ProductsUpdaterAsync;
import com.MKMAndroid.fragments.NavigationDrawerFragment;
import com.MKMAndroid.fragments.ProductsFragment;


public class ProductsActivity extends ActionBarActivity
        implements ActivityWithLoading, NavigationDrawerFragment.NavigationDrawerCallbacks {

    /*private int currentFirstVisibleItem;
    private int currentVisibleItemCount;
    private int currentScrollState;*/

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
    private ArrayList<Product> products;
    private Product productSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String urlConsulta = intent.getStringExtra("url");
        this.products = intent.getParcelableArrayListExtra("productes");
        this.urlConsulta = urlConsulta;
        this.dialog = new ProgressDialog(this);
        this.dialog.setCancelable(false);

        setContentView(R.layout.activity_products);
       mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        


        //////////////////////////////NO ESBORRAR///////////////////////////////////////////////
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        ///////////////////////////////////////////////////////////////////////////////////////

        ProductsFragment productFrag = (ProductsFragment)
                getSupportFragmentManager().findFragmentById(R.layout.fragment_products);



        if (productFrag != null) {
            // If article frag is available, we're in two-pane layout...
            // Call a method in the ArticleFragment to update its content
            //productFrag.setUrlFinal(arguments.getString("url"));
        } else {
            // Otherwise, we're in the one-pane layout and must swap frags...
            // Create fragment and give it an argument for the selected article
            ProductsFragment productsFragment = new ProductsFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.container, productsFragment);
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
                .replace(R.id.container, ProductsFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
       /* switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }*/
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
        /*// Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);*/
    	
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

    /**
     * Recupero els productes
     * @param url
     * @return ArrayList<Product> amb els productes
     */
    public ArrayList<Product> obtainProducts(String url, ProgressDialog dialog)
    {
        //Integer progress = new Integer(0);
        ArrayList<Product> products = new ArrayList<Product>();
        ProductsUpdater updater;

        if (dialog!=null) updater = new ProductsUpdater(dialog);
        else updater = new ProductsUpdater();

        try
        {
        	if(this.checkInternetConnection()){
        		products = updater.execute(url).get();
        	}
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        return products;
    }

    public void obtainProductsAsync(String url, ProgressDialog dialog,
                                                  ArrayList<Product> products, ListView list,
                                                  int scroll)
    {
        ProductsUpdaterAsync updater = new ProductsUpdaterAsync(this, dialog, products);
        
        if(this.checkInternetConnection()){
        	updater.execute(url);
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

    public ArrayList<Product> getProducts()
    {
        return this.products;
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
    
    public void setProductSelected(Product product){
    	this.productSelected = product;
    }
    
    public void obtainArticlesAsync(ProductsActivity activity, String url, ProgressDialog dialog,
            ArrayList<Article> articles)
	{
    	ArticlesUpdaterASync updater = new ArticlesUpdaterASync(activity, dialog, articles);
    	
    	if(this.checkInternetConnection()){
    		updater.execute(url);
    	}
	}
    
    public void callBack(ArrayList args){
    	
    	ArrayList<Article> articles = new ArrayList<Article>();
    	for(int x=0; x < args.size();x++){
    		articles.add((Article)args.get(x));
    	}
    	
        Intent intent;
        intent = new Intent(this, ArticlesActivity.class);
        intent.putExtra("productSelected", this.productSelected);
        intent.putExtra("articles", articles);
        startActivity(intent);
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