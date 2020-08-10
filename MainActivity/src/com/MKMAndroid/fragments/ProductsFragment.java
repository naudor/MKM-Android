package com.MKMAndroid.fragments;

/**
 * Created by Arnau on 9/02/14.
 */

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.MKMAndroid.activities.ProductsActivity;
import com.MKMAndroid.app.R;
import com.MKMAndroid.classes.AdapterProducts;
import com.MKMAndroid.classes.Article;
import com.MKMAndroid.classes.Constants;
import com.MKMAndroid.classes.Product;
import com.smaato.soma.BannerView;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductsFragment extends Fragment implements AdapterView.OnItemClickListener,
        AbsListView.OnScrollListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "1";
    private ArrayList<Product> products;
    private String urlFinal;
    private View rootView;
    private  ProductsActivity activity;
    private ProgressDialog dialog;
    private ListView listProducts;
    private int processes;
    private String urlConsultaArticles = null;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ProductsFragment newInstance(int sectionNumber) {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ProductsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_products, container, false);
        
        this.urlConsultaArticles = generateUrlConsulta();

        activity = (ProductsActivity)this.getActivity();
        urlFinal = activity.getUrlConsulta();
        this.dialog = activity.getDialog();

        //Llegeixo els productes que coincideixen amb els parametres
        products = activity.getProducts();

        //Mostro els resultat en la vista
        ListView llista = (ListView)  rootView.findViewById(R.id.listOrders);
        this.listProducts = llista;


        if (!products.isEmpty()) {
            AdapterProducts adaptadorProductes = new AdapterProducts(this.getActivity(), products);
            llista.setAdapter(adaptadorProductes);
        }

        llista.setOnItemClickListener(this);
        llista.setOnScrollListener(this);
        llista.setEmptyView(rootView.findViewById(R.id.textNoItemFoundProducts));

        this.processes = 0;

        this.bannerSetup(rootView);

        return rootView;
    }

    @Override
    /**
     * Quan piquem en una fila del llistat
     */
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    	Product productSelected = (Product)adapterView.getItemAtPosition(i);
    	ProductsActivity activity = (ProductsActivity)getActivity();
    	activity.setProductSelected(productSelected);
        
        String urlFinal = urlConsultaArticles + productSelected.getIdProduct();
        
        activity.obtainArticlesAsync(this.activity, urlFinal, dialog, new ArrayList<Article>());
        //this.activity.obtainProductsAsync(url, dialog, products, list, scroll);


    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {

        boolean loadMore;

        int ultimRegistreVist= firstVisibleItem + visibleItemCount;
        boolean loadMoreTmp = firstVisibleItem + visibleItemCount >= totalItemCount;

        if ((this.products.size() == 100) && (loadMoreTmp)){
            loadMore = true;
        }
        else{
            loadMore = false;
        }

        if(loadMore && this.processes==0) {
            this.processes = 1;
            activity.obtainProductsAsync(this.urlFinal + "/" +
                            String.valueOf(this.products.size() + 1), this.dialog, this.products,
                    this.listProducts, (ultimRegistreVist - visibleItemCount)
            );
        }

    }

    public void setUrlFinal(String url)
    {
        this.urlFinal = url;
    }

    public void bannerSetup(View view){
    	/*BannerView mBanner = (BannerView) view.findViewById((R.id.banner_products));
        mBanner.getAdSettings().setPublisherId(Constants.publisherId);
        mBanner.getAdSettings().setAdspaceId(Constants.adspaceId);
        mBanner.setAutoReloadFrequency(Constants.bannerRefreshTime);
        mBanner.getUserSettings().setKeywordList(Constants.keywordList); 
        mBanner.getUserSettings().setSearchQuery(Constants.searchQuery);
        mBanner.asyncLoadNewBanner();*/
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*((ProductsActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));*/
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
       /* this.currentScrollState = i;
        this.isScrollCompleted();*/
    }
    
    private String generateUrlConsulta()
    {
    	String FILENAME = Constants.file_settings_name;
    	SharedPreferences settings = getActivity().getSharedPreferences(FILENAME, 0);
    	String keyMKM = settings.getString("apiKey",Constants.keyMKM);
    	String userMKM = settings.getString("userName",Constants.userMKM);
    	
    	return Constants.domain + userMKM +"/" + keyMKM + "/articles/";
    }

}
