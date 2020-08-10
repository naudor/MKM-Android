package com.MKMAndroid.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.MKMAndroid.app.R;
import com.MKMAndroid.classes.ActivityWithLoading;
import com.MKMAndroid.classes.Article;
import com.MKMAndroid.classes.ArticlesUpdaterASync;
import com.MKMAndroid.classes.ArticlesUpdaterSync;
import com.MKMAndroid.classes.Constants;
import com.MKMAndroid.classes.DialogMessage;
import com.MKMAndroid.classes.Filter;
import com.MKMAndroid.classes.Product;
import com.MKMAndroid.classes.PutShoppingCartAsync;
import com.MKMAndroid.classes.ShoppingCart;
import com.MKMAndroid.classes.ShoppingCartRequest;
import com.MKMAndroid.fragments.ArticleDetailsFragment;
import com.MKMAndroid.fragments.ArticlesFragment;
import com.MKMAndroid.fragments.FilterFragment;
import com.MKMAndroid.fragments.LegendFragment;
import com.MKMAndroid.fragments.NavigationDrawerFragment;
import com.MKMAndroid.fragments.PhotoFragment;
import com.MKMAndroid.fragments.ProductsFragment;
import com.MKMAndroid.fragments.SettingsFragment;

public class ArticlesActivity extends ActionBarActivity
        implements ActivityWithLoading, NavigationDrawerFragment.NavigationDrawerCallbacks, View.OnClickListener {

	/**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private Product productSelected = null;
    private Article articleSelected = null;
    private ArrayList<Article> articles = null;
    private Filter filter = null;
    private ProgressDialog dialog;
    private String urlConsulta = null;
    private Bitmap bitMapImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        //setContentView(R.layout.fragment_products);

        this.urlConsulta = generateUrlConsulta();
        
        //Bundle extra = this.getIntent().getExtras();
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        this.productSelected = extra.getParcelable("productSelected");
        this.articles = intent.getParcelableArrayListExtra("articles");
        
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
        
           ArticlesFragment articlesFrag = (ArticlesFragment)
                   getSupportFragmentManager().findFragmentById(R.layout.fragment_articles);

           if (articlesFrag != null) {
               // If article frag is available, we're in two-pane layout...

               // Call a method in the ArticleFragment to update its content
               //productFrag.setUrlFinal(arguments.getString("url"));
           } else {

               ArticlesFragment articlesFragment = new ArticlesFragment();

               FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
               //transaction.add(R.id.container, articlesFragment, "llistaArticles");
               transaction.replace(R.id.container, articlesFragment, "llistaArticles");
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
       inflater.inflate(R.menu.menu, menu);
       
       /*if (!mNavigationDrawerFragment.isDrawerOpen()) {
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

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	
    	 if (mNavigationDrawerFragment.onOptionsItemSelected(item)){
          	return true;
          }
    	 
    	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    	
    	switch (item.getItemId()) 
    	{

	    	case R.id.action_filter:
	            FilterFragment filterFragment = new FilterFragment();
	            //transaction.add(R.id.container, filterFragment);
	            transaction.replace(R.id.container, filterFragment, "filtre");
	            transaction.addToBackStack(null);
	            transaction.commit();
	            //return true;
	            //return super.onOptionsItemSelected(item);
	            return true;
	            
	    	case R.id.action_legend:
	            LegendFragment legendFragment = new LegendFragment();
	            //transaction.add(R.id.container, filterFragment);
	            transaction.replace(R.id.container, legendFragment, "llegenda");
	            transaction.addToBackStack(null);
	            transaction.commit();
	            //return true;
	            //return super.onOptionsItemSelected(item);
	            return true;
	        
	    	case android.R.id.home:
	    		onBackPressed();
	    		return true;
	    	
	    	default:
        		return super.onOptionsItemSelected(item);
    	}
    	
        
        //return true;
    }

    public Product getProductSelected() {
        return productSelected;
    }
    
    public Article getArticleSelected() {
    	return articleSelected;
    }

    public void setProductSelected(Product productSelected) {
        this.productSelected = productSelected;
    }
    
    public void setArticleSelected(Article articleSelected) {
    	this.articleSelected = articleSelected;
    }

    @Override
    public void onClick(View view) {

        ArticlesFragment articlesFragment = new ArticlesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        PhotoFragment photoFragment = new PhotoFragment();
        Bitmap jo=null;
        
        //if i will need pass a image to the zoom fragment
        if (view.getId() == R.id.imageCard || view.getId() == R.id.imageViewScanCard){
	    	//jo = ((BitmapDrawable)imageToZoom.getDrawable()).getBitmap();
        }
        
        switch(view.getId()) {
            case R.id.buttonFilter:
                //Spinner condition = (Spinner)view.getRootView().findViewById(R.id.spinnerCondition);
                //Spinner language = (Spinner)view.getRootView().findViewById(R.id.spinnerLanguage);
                CheckBox isFoil = (CheckBox)view.getRootView().findViewById(R.id.checkFilterFoil);
                CheckBox isAltered = (CheckBox)view.getRootView().findViewById(R.id.checkFilterAltered);
                CheckBox isSigned = (CheckBox)view.getRootView().findViewById(R.id.checkFilterSigned);
                CheckBox isPlayset = (CheckBox)view.getRootView().findViewById(R.id.checkFilterPlayset);
                CheckBox conditionMT = (CheckBox)view.getRootView().findViewById(R.id.checkFilterMT);
                CheckBox conditionNM = (CheckBox)view.getRootView().findViewById(R.id.checkFilterNM);
                CheckBox conditionEX = (CheckBox)view.getRootView().findViewById(R.id.checkFilterEX);
                CheckBox conditionGD = (CheckBox)view.getRootView().findViewById(R.id.checkFilterGD);
                CheckBox conditionLP = (CheckBox)view.getRootView().findViewById(R.id.checkFilterLP);
                CheckBox conditionPL = (CheckBox)view.getRootView().findViewById(R.id.checkFilterPL);
                CheckBox conditionPO = (CheckBox)view.getRootView().findViewById(R.id.checkFilterPO);
                CheckBox languageEN = (CheckBox)view.getRootView().findViewById(R.id.checkFilterEN);
                CheckBox languageFR = (CheckBox)view.getRootView().findViewById(R.id.checkFilterFR);
                CheckBox languageGR = (CheckBox)view.getRootView().findViewById(R.id.checkFilterGR);
                CheckBox languageSP = (CheckBox)view.getRootView().findViewById(R.id.checkFilterSP);
                CheckBox languageIT = (CheckBox)view.getRootView().findViewById(R.id.checkFilterIT);
                CheckBox languageCH = (CheckBox)view.getRootView().findViewById(R.id.checkFilterCH);
                CheckBox languageJP = (CheckBox)view.getRootView().findViewById(R.id.checkFilterJP);
                CheckBox languagePT = (CheckBox)view.getRootView().findViewById(R.id.checkFilterPT);
                CheckBox languageRU = (CheckBox)view.getRootView().findViewById(R.id.checkFilterRU);
                CheckBox languageKO = (CheckBox)view.getRootView().findViewById(R.id.checkFilterKO);
                CheckBox languageCHT = (CheckBox)view.getRootView().findViewById(R.id.checkFilterCHT);


                EditText amount = (EditText) view.getRootView().findViewById(R.id.textAmount);

                this.filter = new Filter();
                //this.filter.setCondition((Condition)condition.getSelectedItem());
                //this.filter.setLanguage((Language)language.getSelectedItem());
                this.filter.setFoil(isFoil.isChecked());
                this.filter.setAltered(isAltered.isChecked());
                this.filter.setSigned(isSigned.isChecked());
                this.filter.setPlayset(isPlayset.isChecked());
                this.filter.setConditionMT(conditionMT.isChecked());
                this.filter.setConditionNM(conditionNM.isChecked());
                this.filter.setConditionEX(conditionEX.isChecked());
                this.filter.setConditionGD(conditionGD.isChecked());
                this.filter.setConditionLP(conditionLP.isChecked());
                this.filter.setConditionPL(conditionPL.isChecked());
                this.filter.setConditionPO(conditionPO.isChecked());
                this.filter.setLanguageEN(languageEN.isChecked());
                this.filter.setLanguageFR(languageFR.isChecked());
                this.filter.setLanguageGR(languageGR.isChecked());
                this.filter.setLanguageSP(languageSP.isChecked());
                this.filter.setLanguageIT(languageIT.isChecked());
                this.filter.setLanguageCH(languageCH.isChecked());
                this.filter.setLanguageJP(languageJP.isChecked());
                this.filter.setLanguagePT(languagePT.isChecked());
                this.filter.setLanguageRU(languageRU.isChecked());
                this.filter.setLanguageKO(languageKO.isChecked());
                this.filter.setLanguageCHT(languageCHT.isChecked());


                if (amount.getText().toString().compareTo("")==0) this.filter.setAmount(0);
                else this.filter.setAmount(Integer.parseInt(amount.getText().toString()));

                transaction.replace(R.id.container, articlesFragment, "llistaArticles");
                transaction.addToBackStack(null);
                transaction.commit();
                break;


            case R.id.buttonResetFilter:
                this.filter = null;
                
                transaction.replace(R.id.container, articlesFragment, "llistaArticles");
                transaction.addToBackStack(null);
                transaction.commit();

                break;

            case R.id.imageCard:

            	args.putParcelable("bitmap", bitMapImage);
            	photoFragment.setArguments(args);
            	
            	transaction.replace(R.id.container, photoFragment, "zoom");
            	transaction.addToBackStack(null);
            	transaction.commit();
            	break;
            	
            case R.id.imageViewScanCard:
            	args.putParcelable("bitmap", bitMapImage);
                photoFragment.setArguments(args);
                
                transaction.replace(R.id.container, photoFragment, "zoom");
                transaction.addToBackStack(null);
                transaction.commit();
                break;
                
            case R.id.buttonBuyArticle:
            	String FILENAME = Constants.file_settings_name;
                SharedPreferences settings = getSharedPreferences(FILENAME, 0);
                String apiKey = settings.getString("apiKey",null);
                
                //if the user have specified his api-key on his preference, I read the id_article and the amount and
                //make and send the request to the next activity.
                if (apiKey != null){
                	Spinner amountToBuy = (Spinner) view.getRootView().findViewById(R.id.spinnerNumArticlesToBuy);
                	String amountString = (String)amountToBuy.getSelectedItem();
                	
                	ArrayList<ShoppingCartRequest> requests = new ArrayList<ShoppingCartRequest>();
                	ShoppingCartRequest request = new ShoppingCartRequest("add", this.articleSelected.getIdArticle(), Integer.parseInt(amountString));
                	requests.add(request);

                	 if (request!=null){
                		 String url = generateUrlConsultaShoppingCart();
                		 PutShoppingCartAsync updater = new PutShoppingCartAsync(this, dialog, requests);
                		 if(this.checkInternetConnection()){
                			 updater.execute(url);
                		 }
                     }
                	
                }else{
            	  SettingsFragment settingsFragment = new SettingsFragment();
                  
                  transaction.replace(R.id.container, settingsFragment, "settings");
                  transaction.addToBackStack(null);
                  transaction.commit();
                }
                
            	break;
        }

    }

    @Override
    public void onBackPressed(){

        Fragment myFragment = this.getVisibleFragment();
        if (myFragment instanceof ArticlesFragment) {
            this.finish();
        }
        else{
            super.onBackPressed();
        }

        /*if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        }
        else {
            super.onBackPressed();
        }*/
    }

    public ArrayList<Article> obtainArticles(String url, ProgressDialog dialog)
    {
        //Integer progress = new Integer(0);
        ArrayList<Article> articles = new ArrayList<Article>();

        //ArticlesUpdater updater = new ArticlesUpdater(context);
        ArticlesUpdaterSync updater = new ArticlesUpdaterSync(dialog);

        try
        {
        	if(this.checkInternetConnection()){
        		articles = updater.execute(url).get();
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


        return articles;
    }

    public void obtainArticlesAsync(String url, ProgressDialog dialog,
                                                  ArrayList<Article> articles, ListView list,
                                                  int scroll)
    {

        ArticlesUpdaterASync updater = new ArticlesUpdaterASync(this, dialog, articles, list,scroll);
        if(this.checkInternetConnection()){
        	updater.execute(url);
        }
    }


    public ArrayList<Article> filterArticles(ArrayList<Article> articles, Filter filter)
    {
        ArrayList<Article> good = new ArrayList<Article>();
        ArrayList<String> conditionsCard = this.filter.getConditionsCard();
        ArrayList<String> languajeCard = this.filter.getLanguagesCard();
        boolean passCondition, passLanguage, passOther;

        for (Article i : articles) {

            passCondition = false;
            passLanguage = false;
            passOther = true;

            //I put a "dummy" article to refresh the listview and now I must control this
            if (i.getSeller()!=null) {

                String condition = i.getCondition();
                String language = i.getLanguage().getLanguageName();

                if(conditionsCard.size()==0 || conditionsCard.contains(condition)) passCondition = true;
                if(languajeCard.size()==0 || languajeCard.contains(language)) passLanguage = true;

                if (i.getCount() < filter.getAmount()) passOther = false;
                else if (i.isFoil() != filter.isFoil()) passOther = false;
                else if (i.isAltered() != filter.isAltered()) passOther = false;
                else if (i.isSigned() != filter.isSigned()) passOther = false;
                else if (i.isPlayset() != filter.isPlayset()) passOther = false;


            }

            if (passCondition && passLanguage && passOther) {
                good.add(i);
            }
        }

        return good;
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

    public String getUrlConsulta()
    {
        return this.urlConsulta;
    }
    public ArrayList<Article> getArticles(){
        return this.articles;
    }

    public Filter getFilter(){
        return this.filter;
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

    @Override
    public void onNavigationDrawerItemSelected(int position) {
    	 // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, ArticlesFragment.newInstance(position + 1))
                .commit();
    }
    
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(getStringResourceByName(title));
    }
    
    private String generateUrlConsulta()
    {
    	String FILENAME = Constants.file_settings_name;
    	SharedPreferences settings = getSharedPreferences(FILENAME, 0);
    	String keyMKM = settings.getString("apiKey",Constants.keyMKM);
    	String userMKM = settings.getString("userName",Constants.userMKM);
    	
    	return Constants.domain + userMKM +"/" + keyMKM + "/articles/";
    }
    
    private String generateUrlConsultaShoppingCart()
    {
    	String FILENAME = Constants.file_settings_name;
    	SharedPreferences settings = getSharedPreferences(FILENAME, 0);
    	String keyMKM = settings.getString("apiKey",null);
    	String userMKM = settings.getString("userName",null);
    	
    	return Constants.domain + userMKM +"/" + keyMKM + "/shoppingcart";
    }
    
    public void callBack(ArrayList args){
    	
    	ArrayList<ShoppingCart> shoppingCarts = new ArrayList<ShoppingCart>();
    	for(int x=0; x < args.size();x++){
    		shoppingCarts.add((ShoppingCart)args.get(x));
    	}
    	
        Intent intent;
        intent = new Intent(this, ShoppingCartActivity.class);
        intent.putExtra("shoppingCarts", shoppingCarts);
        startActivity(intent);
   }
    
    public void setImageBitMap (Bitmap ima){
    	this.bitMapImage = ima;
    }
    
    public Bitmap getImageBitMap(){
    	return this.bitMapImage;
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
