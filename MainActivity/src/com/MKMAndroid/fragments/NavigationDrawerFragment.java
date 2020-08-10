package com.MKMAndroid.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.MKMAndroid.activities.MainActivity;
import com.MKMAndroid.activities.SettingsActivity;
import com.MKMAndroid.app.R;
import com.MKMAndroid.classes.ActivityWithLoading;
import com.MKMAndroid.classes.AdapterNavigationDrawer;
import com.MKMAndroid.classes.Constants;
import com.MKMAndroid.classes.DialogPassword;
import com.MKMAndroid.classes.OrdersUpdaterAsync;
import com.MKMAndroid.classes.ShoppingCartRequest;
import com.MKMAndroid.classes.ShoppingCartUpdaterAsync;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment implements OnChildClickListener, OnGroupClickListener{

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ExpandableListView mDrawerListView;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    private static final String ARG_SECTION_NUMBER = "1";
    private ActivityWithLoading activity;
   
    private ArrayList<String> listDataHeader = new ArrayList<String>();
    private ArrayList<Integer> listIcons = new ArrayList<Integer>();
    private  HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();
    
    private Dialog dialogAlert;

    public NavigationDrawerFragment() {
    }

    public static NavigationDrawerFragment newInstance(int sectionNumber)
    {
    	NavigationDrawerFragment fragment = new NavigationDrawerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	prepareListData();
    	
    	this.activity = (ActivityWithLoading)this.getActivity();
		mFragmentContainerView = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
		mDrawerListView = (ExpandableListView) mFragmentContainerView.findViewById(R.id.left_drawer);
		mDrawerListView.setAdapter(new AdapterNavigationDrawer(this.getActivity(), listDataHeader, listDataChild, listIcons));
		mDrawerListView.setOnChildClickListener(this);
		mDrawerListView.setOnGroupClickListener(this);
		/*mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                selectItem(position);
	            }
	        });*/

        //mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
        return mFragmentContainerView;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        
        // set up the drawer's list view with items and click listener
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).commit();
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
        	mDrawerListView.setItemChecked(position, true);
        	
        	String FILENAME = Constants.file_settings_name;
	    	SharedPreferences settings = this.getActivity().getSharedPreferences(FILENAME, 0);
	    	String keyMKM = settings.getString("apiKey",null);
	    	String userMKM = settings.getString("userName",null);
	    	
	    	String url;
        	Intent intent;

        	switch(position){
        		case 0:
        			intent = new Intent(this.getActivity(), SettingsActivity.class);
        	     	 //intent.putExtra("position", position);
        	     	startActivity(intent);
        			break;
        		case 1:
                	//ShoppingCartRequest request = new ShoppingCartRequest();
                	url = Constants.domain + userMKM +"/" + keyMKM + "/shoppingcart";
                	ShoppingCartUpdaterAsync updater = new ShoppingCartUpdaterAsync(this.activity, this.activity.getDialog());
                	if(this.activity.checkInternetConnection()){
           		 		updater.execute(url);
                	}
        			break;
        			
        		case 2:
        			for(int x=0; x < Constants.orderTypeCodes.length;x++){
        				url = Constants.domain + userMKM +"/" + keyMKM + "/orders" + "/" + Constants.actorAsBuyer + "/"+ Constants.orderTypeCodes[x];
        				OrdersUpdaterAsync updaterOrdersAsBuyer = new OrdersUpdaterAsync(this.activity, this.activity.getDialog(), Constants.actorAsBuyer, Integer.parseInt(Constants.orderTypeCodes[x]));
        				if(this.activity.checkInternetConnection()){
        					updaterOrdersAsBuyer.execute(url);
        				}
        			}

        		break;
        		
        		case 3:
        			for(int x=0; x < Constants.orderTypeCodes.length;x++){
        				url = Constants.domain + userMKM +"/" + keyMKM + "/orders" + "/" + Constants.actorAsSeller + "/"+ Constants.orderTypeCodes[x];
	        	        OrdersUpdaterAsync updaterOrdersAsSeller = new OrdersUpdaterAsync(this.activity, this.activity.getDialog(), Constants.actorAsSeller, Integer.parseInt(Constants.orderTypeCodes[x]));
	        	        if(this.activity.checkInternetConnection()){
	        	        	updaterOrdersAsSeller.execute(url);
	        	        }
        			}
        			break;
        	}
     	   
           
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle!=null && mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.action_example) {
            Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        //I prefer that the navigation drawer don't change the title
        //actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }
    
    public void refresh(){
    	prepareListData();
    	AdapterNavigationDrawer adapter = new AdapterNavigationDrawer(this.getActivity(), listDataHeader, listDataChild, listIcons);
		mDrawerListView.setAdapter(adapter);
		mDrawerListView.setOnChildClickListener(this);
    }
    

	/*
	 * Preparing the list data
	 */
	private void prepareListData() {
	    listDataHeader = new ArrayList<String>();
	    listDataChild = new HashMap<String, List<String>>();
	    listIcons = new ArrayList<Integer>();

		String FILENAME = Constants.file_settings_name;
        SharedPreferences settings = this.getActivity().getSharedPreferences(FILENAME, 0);
        String apiKey = settings.getString("apiKey",null);
        
	    // Adding child data
        if (apiKey!=null){
        	
        	listDataHeader.add(getString(R.string.settings));
        	listDataHeader.add(getString(R.string.search_card));
        	listDataHeader.add(getString(R.string.my_cart));
        	listDataHeader.add(getString(R.string.my_purchases));
        	listDataHeader.add(getString(R.string.my_sales));
        	
        	listIcons.add(R.drawable.ic_action_settings);
        	listIcons.add(R.drawable.ic_action_search);
        	listIcons.add(R.drawable.ic_action_cart);
        	listIcons.add(R.drawable.ic_action_download);
        	listIcons.add(R.drawable.ic_action_upload);
        	
        	List<String> purchases = new ArrayList<String>();
        	purchases.add(getString(R.string.unpaid));
        	purchases.add(getString(R.string.paid));
        	purchases.add(getString(R.string.shipped));
        	purchases.add(getString(R.string.closed));
        	purchases.add(getString(R.string.cancel));
        	purchases.add(getString(R.string.lost));
        	
        	
        	List<String> sales = new ArrayList<String>();
        	sales.add(getString(R.string.unpaid));
        	sales.add(getString(R.string.paid));
        	sales.add(getString(R.string.shipped));
        	sales.add(getString(R.string.closed));
        	sales.add(getString(R.string.cancel));
        	sales.add(getString(R.string.lost));
        	
        	listDataChild.put(listDataHeader.get(0), null); // Header, Child data
        	listDataChild.put(listDataHeader.get(1), null); // Header, Child data
        	listDataChild.put(listDataHeader.get(2), null); // Header, Child data
        	listDataChild.put(listDataHeader.get(3), purchases); // Header, Child data
     	    listDataChild.put(listDataHeader.get(4), sales);
        }
        else
        {
        	listDataHeader.add(getString(R.string.settings));
        	listDataChild.put(listDataHeader.get(0), null); // Header, Child data
        	
        	listDataHeader.add(getString(R.string.search_card));
        	listDataChild.put(listDataHeader.get(1), null); // Header, Child data
        	
        	listIcons.add(R.drawable.ic_action_settings);
        	listIcons.add(R.drawable.ic_action_search);
        }
	}
	
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		
		String FILENAME = Constants.file_settings_name;
    	SharedPreferences settings = this.getActivity().getSharedPreferences(FILENAME, 0);
    	String keyMKM = settings.getString("apiKey",null);
    	String userMKM = settings.getString("userName",null);
    	
    	String url;

		switch(groupPosition){
			case 3:
				mDrawerLayout.closeDrawer(mFragmentContainerView);
				url = Constants.domain + userMKM +"/" + keyMKM + "/orders" + "/" + Constants.actorAsBuyer + "/"+ Constants.orderTypeCodes[childPosition];
				OrdersUpdaterAsync updaterOrdersAsBuyer = new OrdersUpdaterAsync((ActivityWithLoading)this.getActivity(), ((ActivityWithLoading) this.getActivity()).getDialog(), Constants.actorAsBuyer, Integer.parseInt(Constants.orderTypeCodes[childPosition]));
				if(this.activity.checkInternetConnection()){
					updaterOrdersAsBuyer.execute(url);
				}
				break;
			case 4:
				mDrawerLayout.closeDrawer(mFragmentContainerView);
				url = Constants.domain + userMKM +"/" + keyMKM + "/orders" + "/" + Constants.actorAsSeller + "/"+ Constants.orderTypeCodes[childPosition];
    	        OrdersUpdaterAsync updaterOrdersAsSeller = new OrdersUpdaterAsync((ActivityWithLoading)this.getActivity(), ((ActivityWithLoading) this.getActivity()).getDialog(), Constants.actorAsSeller, Integer.parseInt(Constants.orderTypeCodes[childPosition]));
    	        if(this.activity.checkInternetConnection()){
    	        	updaterOrdersAsSeller.execute(url);
    	        }
				break;
		}
		return true;
	}

	@Override
	public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition,
			long id) {

		String FILENAME = Constants.file_settings_name;
    	SharedPreferences settings = this.getActivity().getSharedPreferences(FILENAME, 0);
    	String keyMKM = settings.getString("apiKey",null);
    	String userMKM = settings.getString("userName",null);
    	
		String url;
    	Intent intent;
    	boolean entro = false;

		switch(groupPosition){
			case 0:
				entro = true;
				if(keyMKM!=null){
	    			dialogAlert = new DialogPassword((ActivityWithLoading) this.getActivity(), 2);
	    			dialogAlert.show();
				}else{
					intent = new Intent(this.getActivity(), SettingsActivity.class);
			     	startActivity(intent);
				}
				break;
			case 1:
				entro = true;
				intent = new Intent(this.getActivity(), MainActivity.class);
		     	startActivity(intent);
				break;
			case 2:
				entro = true;
	        	//ShoppingCartRequest request = new ShoppingCartRequest();
	        	url = Constants.domain + userMKM +"/" + keyMKM + "/shoppingcart";
	        	ShoppingCartUpdaterAsync updater = new ShoppingCartUpdaterAsync((ActivityWithLoading)this.getActivity(), ((ActivityWithLoading)this.getActivity()).getDialog());
	        	if(this.activity.checkInternetConnection()){
	   		 		updater.execute(url);
	        	}
				break;
		}
		
		if(entro == true){
			mDrawerLayout.closeDrawer(mFragmentContainerView);
		}
		
		return entro;
	}
}
