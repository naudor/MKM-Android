package com.MKMAndroid.activities;

//import static android.os.StrictMode.setThreadPolicy;

import java.util.ArrayList;








import android.app.Activity;
//import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
/*import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;*/
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.MKMAndroid.app.R;
import com.MKMAndroid.classes.ActivityWithLoading;
import com.MKMAndroid.classes.Constants;
import com.MKMAndroid.classes.DialogMessage;
import com.MKMAndroid.classes.Game;
import com.MKMAndroid.classes.Order;
import com.MKMAndroid.classes.Product;
import com.MKMAndroid.classes.ProductsUpdaterAsync;
import com.MKMAndroid.fragments.CercadorSimpleFragment;
import com.MKMAndroid.fragments.NavigationDrawerFragment;

public class MainActivity extends ActionBarActivity
        implements ActivityWithLoading, NavigationDrawerFragment.NavigationDrawerCallbacks, View.OnClickListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private ProgressDialog dialog;
    private String final_url;
    private ArrayList<Order> orders;
    private int processes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        this.dialog = new ProgressDialog(this);
        this.dialog.setCancelable(false);


        //////////////////////////////NO ESBORRAR///////////////////////////////////////////////
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        ///////////////////////////////////////////////////////////////////////////////////////

        //Per poder executar conexions a Internet desde el MainThread
        /*StrictMode.ThreadPolicy policy = new Builder().permitAll().build();
        setThreadPolicy(policy);*/
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch(position){
        	case 0:
		        fragmentManager.beginTransaction()
		        .replace(R.id.container, CercadorSimpleFragment.newInstance(position + 1))
		        .commit();
		        break;
        	/*case 1:
        		fragmentManager.beginTransaction()
		        .replace(R.id.container,mNavigationDrawerFragment)
		        .commit();
        		break;*/
        	case 1:
        		fragmentManager.beginTransaction()
        		.replace(R.id.container,NavigationDrawerFragment.newInstance(position+1))
        		.commit();
        		break;
        }
        
    }

    public void onSectionAttached(int number) {
        /*switch (number) {
            case 1:
                mTitle = getString(R.string.settings);
                break;
            case 2:
                mTitle = getString(R.string.my_wants);
                break;
            case 3:
                mTitle = getString(R.string.my_purchases);
                break;
            case 4:
            	mTitle = getString(R.string.my_sales);
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
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
        	//getMenuInflater().inflate(R.menu.main, menu);
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);

        /*MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);*/

        int id = item.getItemId();
        
        if (mNavigationDrawerFragment.onOptionsItemSelected(item)){
        	return true;
        }
        
        if (id == R.id.action_info) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title
            alertDialogBuilder.setTitle(getStringResourceByName("about"));

            // set dialog message
            alertDialogBuilder
                    .setMessage(getStringResourceByName("created_by")+": Arnau Dunjó"+"\n"+
                            "naudor.code@gmail.com"+"\n"+"\n"+getStringResourceByName("version")+" "+
                            getStringResourceByName("app_version")+"\n"+"\n"+"\n"+getStringResourceByName("special_thanks"))
                    .setCancelable(false)
                    .setNegativeButton(getStringResourceByName("accept"),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        }

        return true;
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

    @Override
    public void onClick(View v)
    {
        Button button = (Button) v.findViewById(R.id.botoCercarCartes);
        EditText editNomCarta = (EditText) v.getRootView().findViewById(R.id.cardName);
        Spinner idGame = (Spinner) v.getRootView().findViewById(R.id.llistaJocs);
        Game gameSelected = (Game)idGame.getSelectedItem();

        String nomCarta = editNomCarta.getText().toString();

        //If the name is blank
        if (nomCarta.trim().compareTo("")!=0)
        {
        	//Load the api_key of user, if it's null or don't exist, I use my own api_key
        	String FILENAME = Constants.file_settings_name;
        	SharedPreferences settings = getSharedPreferences(FILENAME, 0);
        	String keyMKM = settings.getString("apiKey",Constants.keyMKM);
        	String userMKM = settings.getString("userName",Constants.userMKM);
        	
            this.final_url= new String(Constants.domain+ userMKM +"/"+
            		keyMKM + "/products/" + Uri.encode(nomCarta)+"/"+gameSelected.getIdGame()+"/1/false");
            
           obtainProductsAsync(this, final_url, dialog, new ArrayList<Product>());
        }
        else
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title
            alertDialogBuilder.setTitle(getStringResourceByName("error_no_name_card_title"));

            // set dialog message
            alertDialogBuilder
                    .setMessage(getStringResourceByName("error_no_name_card"))
                    .setCancelable(false)
                    .setNegativeButton(getStringResourceByName("accept"),
                            new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
    }

    public ProgressDialog getDialog(){
        return this.dialog;
    }

    public void onResume()
    {
        super.onResume();
        
        //I update the left menu
        this.mNavigationDrawerFragment.refresh();
        
        if (this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
    }

    public String getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }
    
    public void obtainProductsAsync(MainActivity activity, String url, ProgressDialog dialog,
            ArrayList<Product> products)
	{
    	if (checkInternetConnection()){
    		ProductsUpdaterAsync updater = new ProductsUpdaterAsync(activity, dialog, products);
			updater.execute(url);
    	}	
	}
    
    public void callBack(ArrayList args){
    	
    	ArrayList<Product> products = new ArrayList<Product>();
    	for(int x=0; x < args.size();x++){
    		products.add((Product)args.get(x));
    	}
    	
	   Intent intent;
	   intent = new Intent(this, ProductsActivity.class);
	   
	   intent.putExtra("url", this.final_url);
	   intent.putExtra("productes", products);
	   startActivity(intent);
		   
   }
    
    /*public void callBackOrders(ArrayList<Order> orders, int actor){
    	
    	if(this.processes==0 || this.orders==null)this.orders = new ArrayList<Order>();
    	
    	for(int x=0; x < orders.size();x++){
    		this.orders.add(orders.get(x));
    	}
    	
    	this.processes++;
    	
    	if(this.processes==Constants.orderTypeCodes.length){
	    	Intent intent = new Intent(this, OrdersActivity.class);
			intent.putExtra("actor", actor);
			intent.putExtra("orders", this.orders);
			this.processes = 0;
			this.dialog.dismiss();
			this.startActivity(intent);
    	}
    }*/
    
    public boolean checkInternetConnection() 
    {
        ConnectivityManager connec = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isConnected() || mobile.isConnected()){
        	hideVirturalKeyboard();
        	return true;
        }else{
	        DialogMessage alertDialog = new DialogMessage(this, 5);
			alertDialog.show();
			
			return false;
        }
    }
    
    private void hideVirturalKeyboard(){
    	View v = getCurrentFocus();
    	if (v != null && v instanceof EditText) {
	    	InputMethodManager mgr = (InputMethodManager)(v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
	    	mgr.hideSoftInputFromWindow(v.getWindowToken(), 0);
    	}
    } 

}
