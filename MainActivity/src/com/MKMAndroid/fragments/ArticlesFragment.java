package com.MKMAndroid.fragments;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.MKMAndroid.activities.ArticlesActivity;
import com.MKMAndroid.app.R;
import com.MKMAndroid.classes.AdapterArticles;
import com.MKMAndroid.classes.Article;
import com.MKMAndroid.classes.BitmapInternetLoader;
import com.MKMAndroid.classes.Constants;
import com.MKMAndroid.classes.DownloadImageBackground;
import com.MKMAndroid.classes.Filter;
import com.MKMAndroid.classes.Product;
import com.smaato.soma.BannerView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ArticlesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ArticlesFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ArticlesFragment extends Fragment implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_SECTION_NUMBER = "1";
    //private OnFragmentInteractionListener mListener;
    private ArrayList<Article> articles;
    private View rootView;
    private ArticlesActivity activity;
    private String urlFinal;
    private ListView listArticles = null;
    private ProgressDialog dialog;
    private int processes = 0;
    private int nInstance = 0;

    public ArticlesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_articles, null);
        
        this.listArticles = (ListView) rootView.findViewById(R.id.listArticles);
        if (getArguments() != null) {
        	nInstance = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        
        activity = (ArticlesActivity) this.getActivity();
        this.dialog = activity.getDialog();
        Product productSelected = activity.getProductSelected();

        //Llegeixo els articles relacionats
        this.articles = activity.getArticles();
        Filter filtre = activity.getFilter();
        ArrayList<Article> articlesFiltrats = new ArrayList<Article>();

        //Recupero els objectes del layout
        this.urlFinal = activity.getUrlConsulta() + productSelected.getIdProduct();

        TextView cardNameENG = (TextView) rootView.findViewById(R.id.textCardNameENG);
        TextView expansionName = (TextView) rootView.findViewById(R.id.textExpansionNameArticles);
        ImageView image = (ImageView) rootView.findViewById(R.id.imageCard);
        image.setOnClickListener((View.OnClickListener) getActivity());

        TextView cardSellPrice = (TextView) rootView.findViewById(R.id.textSellPrice);
        TextView cardLowPrice = (TextView) rootView.findViewById(R.id.textLowPrice);
        TextView cardAvgPrice = (TextView) rootView.findViewById(R.id.textAvgPrice);

        //Poso els valors als objectes del layout
        cardNameENG.setText(productSelected.getName());
        cardSellPrice.setText(String.valueOf(productSelected.getPriceGuide().getSell()));
        cardLowPrice.setText(String.valueOf(productSelected.getPriceGuide().getLow()));
        cardAvgPrice.setText(String.valueOf(productSelected.getPriceGuide().getAvg()));

        expansionName.setText(productSelected.getExpansion());
        
        if(nInstance == 1 || activity.getImageBitMap()==null){
	        BitmapInternetLoader imageThread = new BitmapInternetLoader(image, this.activity);
	        try {
	        	if(this.activity.checkInternetConnection()){
	        		imageThread.execute(productSelected.getImatge()).get();
	        	}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else
        {
        	image.setImageBitmap(activity.getImageBitMap());
        }
 
        //image.setOnClickListener((View.OnClickListener) this.getActivity());
        
       
        if(filtre!=null) {

            ArrayList<Article> articlesTotal = this.articles;
            ArrayList<Article> moreArticles = null;

            //The WS only send me 206 rows max, then I need only take the second page 
            //if I realize the max number of rows in first page
            if (this.articles.size()==100){
            	moreArticles = activity.obtainArticles(this.urlFinal + "/" +
                    String.valueOf(this.articles.size() + 1), this.dialog);
            }
            
            if(moreArticles!=null && moreArticles.size()>0) {
                articlesTotal.addAll(moreArticles);
            }

            articlesFiltrats = activity.filterArticles(articlesTotal, filtre);

        }else{
            articlesFiltrats = this.articles;
        }

        if (this.articles.get(0).getIdArticle()==0){
            this.articles.clear();
        }

        AdapterArticles adapterArticles = new AdapterArticles(activity, articlesFiltrats);
        //listArticles.addFooterView(inflater.inflate(R.layout.loading, null));
        this.listArticles.setAdapter(adapterArticles);
        adapterArticles.notifyDataSetChanged();
        this.listArticles.setClickable(true);
        this.listArticles.setOnScrollListener(this);
        this.listArticles.setOnItemClickListener(this);
        this.listArticles.setEmptyView(rootView.findViewById(R.id.textNoItemFoundArticles));
        this.listArticles.setSelection(0);
       
        this.bannerSetup(rootView);

        this.processes = 0;
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
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

        if ((this.articles.size() == 100) && (loadMoreTmp)){
        //if ((this.articles.size() == 100) && (ultimRegistreVist >= totalItemCount)){
            loadMore = true;
        }
        else{
            loadMore = false;
        }

        if(loadMore && this.processes==0) {
           this.processes = 1;
           activity.obtainArticlesAsync(this.urlFinal + "/" +
                    String.valueOf(this.articles.size() + 1),this.dialog, this.articles,
                   this.listArticles,(ultimRegistreVist - visibleItemCount));

        }
    }

    public ListView getListArticles(){
        return this.listArticles;
    }

    public void bannerSetup(View view){
    	/*BannerView mBanner = (BannerView) view.findViewById((R.id.banner_shoppingcart_articles));
        mBanner.getAdSettings().setPublisherId(Constants.publisherId);
        mBanner.getAdSettings().setAdspaceId(Constants.adspaceId);
        mBanner.setAutoReloadFrequency(Constants.bannerRefreshTime);
        mBanner.getUserSettings().setKeywordList(Constants.keywordList); 
        mBanner.getUserSettings().setSearchQuery(Constants.searchQuery);
        mBanner.asyncLoadNewBanner();*/
    }
    
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		Article articleSelected = (Article)parent.getItemAtPosition(position);
		this.activity.setArticleSelected(articleSelected);
		
		String urlScanImage = Constants.domainImages
				+ this.activity.getProductSelected().getExpansion()
				+ "/img/specimens/"
				+ articleSelected.getIdArticle()
				+ ".jpg";
    	
		DownloadImageBackground downloader = new DownloadImageBackground(this.activity, this ,this.dialog);
		if(this.activity.checkInternetConnection()){
			downloader.execute(urlScanImage);
		}
	}
	
	public void showArticleDetail(Bitmap bitmap)
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
        transaction.replace(R.id.container, detailsFragment, "articleDetail");
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
		
	}
	
	 /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ArticlesFragment newInstance(int sectionNumber) {
    	ArticlesFragment fragment = new ArticlesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

}
