package com.MKMAndroid.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.MKMAndroid.app.R;
import com.MKMAndroid.classes.ActivityWithLoading;
import com.MKMAndroid.classes.DialogMessage;
import com.MKMAndroid.fragments.ArticlesFragment;
import com.MKMAndroid.fragments.NavigationDrawerFragment;
import com.MKMAndroid.fragments.SettingsFragment;

public class SettingsActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, ActivityWithLoading {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private ProgressDialog dialog;
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

        SettingsFragment settingsFrag = (SettingsFragment)
                getSupportFragmentManager().findFragmentById(R.layout.fragment_settings);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();


        if (settingsFrag != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            //productFrag.setUrlFinal(arguments.getString("url"));
        } else {

            SettingsFragment settingsFragment = new SettingsFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //transaction.add(R.id.container, articlesFragment, "llistaArticles");
            transaction.replace(R.id.container, settingsFragment, "settings");
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
        
    	/*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    	
    	switch (item.getItemId()) 
    	{

	    	case R.id.action_filter:
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

	            return true;
	        
	    	case android.R.id.home:
	    		onBackPressed();
	    		return true;
	    	
	    	default:
        		return super.onOptionsItemSelected(item);
    	}*/
    	
    	return super.onOptionsItemSelected(item);

    }
    
    @Override
    public void onNavigationDrawerItemSelected(int position) {
    	 // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, SettingsFragment.newInstance(position + 1))
                .commit();
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
    
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(getStringResourceByName(title));
    }

	@Override
	public void callBack(ArrayList args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProgressDialog getDialog() {
		return this.dialog;
	}
	public void openDrawer(){
		View mDrawer = (View) findViewById(R.id.navigation_drawer); 
		DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mNavigationDrawerFragment.refresh();
		mDrawerLayout.openDrawer(mDrawer);
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
