package com.MKMAndroid.fragments;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.MKMAndroid.activities.ArticlesActivity;
import com.MKMAndroid.app.R;
import com.MKMAndroid.classes.Article;
import com.MKMAndroid.classes.Product;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.MKMAndroid.fragments.LegendFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link com.MKMAndroid.fragments.ArticleDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ArticleDetailsFragment extends Fragment implements OnItemSelectedListener {
	
	private static final String ARG_SECTION_NUMBER = "1";
	
	private ArticlesActivity activity;
	private Article articleSelected;
	private Product productSelected;
	private ProgressDialog dialog;
	private View rootView;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
    }

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    	rootView = inflater.inflate(R.layout.fragment_article_detail, container, false);
    	this.activity = (ArticlesActivity) getActivity();
    	this.articleSelected = this.getArguments().getParcelable("articleSelected");
    	this.productSelected = this.getArguments().getParcelable("productSelected");
    	
    	this.dialog = this.activity.getDialog();
    	Bitmap bmp = (Bitmap) this.getArguments().getParcelable("image");
    	
    	((ArticlesActivity) getActivity()).setActionBarTitle("article_detail_title");
    	
    	 TextView titleCard = (TextView) rootView.findViewById(R.id.textCardNameDetail);
    	 TextView expansionCard = (TextView) rootView.findViewById(R.id.textViewExpansionName);
    	 TextView priceCard = (TextView) rootView.findViewById(R.id.textViewDetailPriceValue);
    	 TextView conditionCard = (TextView) rootView.findViewById(R.id.textViewDetailConditionValue);
    	 TextView languageCard = (TextView) rootView.findViewById(R.id.textViewDetailLanguageValue);
    	 TextView commentsCard = (TextView) rootView.findViewById(R.id.textViewDetailCommentsValue);
    	 TextView cardPriceSpinner = (TextView) rootView.findViewById(R.id.textViewPriceCardOnSpinner);
    	 ImageView imageFoil = (ImageView) rootView.findViewById(R.id.imageViewDetailFoil);
    	 ImageView imageAltered = (ImageView) rootView.findViewById(R.id.imageViewDetailAltered);
    	 ImageView imagePlayset = (ImageView) rootView.findViewById(R.id.imageViewDetailPlayset);
    	 ImageView imageSigned = (ImageView) rootView.findViewById(R.id.imageViewDetailSigned);
    	 //ImageView imageCommercial = (ImageView) rootView.findViewById(R.id.imageViewDetailSellerComercial);
    	 TextView userNameSeller = (TextView) rootView.findViewById(R.id.textViewDetailUserNameValue);
    	 ImageView countrySeller = (ImageView) rootView.findViewById(R.id.imageCountrySeller);
    	 ImageView reputationSeller = (ImageView) rootView.findViewById(R.id.imageReputationSeller);
    	 ImageView professionalSeller = (ImageView) rootView.findViewById(R.id.imageProfesionalUser);
    	 ImageView scanImage = (ImageView) rootView.findViewById(R.id.imageViewScanCard);
    	 Spinner howMany = (Spinner) rootView.findViewById(R.id.spinnerNumArticlesToBuy);
    	 Button buttonBuy = (Button) rootView.findViewById(R.id.buttonBuyArticle);
    	 TextView totalValue = (TextView) rootView.findViewById(R.id.textTotalValue);

    	 
    	 scanImage.setOnClickListener((View.OnClickListener) getActivity());
    	 titleCard.setText(this.productSelected.getName());
    	 expansionCard.setText(this.productSelected.getExpansion());
    	 priceCard.setText(String.valueOf(this.articleSelected.getPrice())+"€");
    	
    	 //commentsCard.setText(this.articleSelected.getComments());
    	 
    	 if(this.articleSelected.getComments()!=null && this.articleSelected.getComments().compareTo("")!=0){
    		 commentsCard.setText(this.articleSelected.getComments());
    	 }else{
    		 commentsCard.setText(this.activity.getStringResourceByName("no_comments"));
    	 }
    	 
    	 userNameSeller.setText(this.articleSelected.getSeller().getUsername());
    	 
    	/* if (this.articleSelected.getSeller().getCountry().compareTo("ES")==0){
    		 countrySeller.setText(this.activity.getStringResourceByName("country_spain"));
    	 }else if (this.articleSelected.getSeller().getCountry().compareTo("EN")==0){
    		 countrySeller.setText(this.activity.getStringResourceByName("country_uk"));
    	 }else if (this.articleSelected.getSeller().getCountry().compareTo("PT")==0){
    		 countrySeller.setText(this.activity.getStringResourceByName("country_portugal"));
    	 }else if (this.articleSelected.getSeller().getCountry().compareTo("FR")==0){
    		 countrySeller.setText(this.activity.getStringResourceByName("country_france"));
    	 }else if (this.articleSelected.getSeller().getCountry().compareTo("D")==0){
    		 countrySeller.setText(this.activity.getStringResourceByName("country_germany"));
    	 }else if (this.articleSelected.getSeller().getCountry().compareTo("AT")==0){
    		 countrySeller.setText(this.activity.getStringResourceByName("country_austria"));
    	 }else if (this.articleSelected.getSeller().getCountry().compareTo("CH")==0){
    		 countrySeller.setText(this.activity.getStringResourceByName("country_switzerland"));
    	 }else if (this.articleSelected.getSeller().getCountry().compareTo("SI")==0){
    		 countrySeller.setText(this.activity.getStringResourceByName("country_slovenia"));
    	 }else if (this.articleSelected.getSeller().getCountry().compareTo("BE")==0){
    		 countrySeller.setText(this.activity.getStringResourceByName("country_belgium"));
    	 }else if (this.articleSelected.getSeller().getCountry().compareTo("FI")==0){
    		 countrySeller.setText(this.activity.getStringResourceByName("country_finlandia"));
    	 }else if (this.articleSelected.getSeller().getCountry().compareTo("NL")==0){
    		 countrySeller.setText(this.activity.getStringResourceByName("country_holland"));
    	 }else if (this.articleSelected.getSeller().getCountry().compareTo("PL")==0){
    		 countrySeller.setText(this.activity.getStringResourceByName("country_poland"));
    	 }else if (this.articleSelected.getSeller().getCountry().compareTo("CZ")==0){
    		 countrySeller.setText(this.activity.getStringResourceByName("country_txequia"));
    	 }else if (this.articleSelected.getSeller().getCountry().compareTo("GR")==0){
    		 countrySeller.setText(this.activity.getStringResourceByName("country_greece"));
    	 }else if (this.articleSelected.getSeller().getCountry().compareTo("IT")==0){
    		 countrySeller.setText(this.activity.getStringResourceByName("country_italy"));
    	 }*/
    	 
    	 
    	 if (this.articleSelected.getSeller().getCountry().equals("PT")) {
    		 countrySeller.setImageResource(R.drawable.portugal);
         } else if (this.articleSelected.getSeller().getCountry().equals("ES")) {
        	 countrySeller.setImageResource(R.drawable.espanya);
         } else if (this.articleSelected.getSeller().getCountry().equals("GB")) {
        	 countrySeller.setImageResource(R.drawable.anglaterra);
         } else if (this.articleSelected.getSeller().getCountry().equals("FR")) {
        	 countrySeller.setImageResource(R.drawable.francia);
         } else if (this.articleSelected.getSeller().getCountry().equals("D")) {
        	 countrySeller.setImageResource(R.drawable.alemania);
         } else if (this.articleSelected.getSeller().getCountry().equals("DK")) {
        	 countrySeller.setImageResource(R.drawable.dinamarca);
         } else if (this.articleSelected.getSeller().getCountry().equals("AT")) {
        	 countrySeller.setImageResource(R.drawable.austria);
         } else if (this.articleSelected.getSeller().getCountry().equals("CH")) {
        	 countrySeller.setImageResource(R.drawable.suisa);
         } else if (this.articleSelected.getSeller().getCountry().equals("SI")) {
        	 countrySeller.setImageResource(R.drawable.eslovenia);
         } else if (this.articleSelected.getSeller().getCountry().equals("BE")) {
        	 countrySeller.setImageResource(R.drawable.belgica);
         } else if (this.articleSelected.getSeller().getCountry().equals("FI")) {
        	 countrySeller.setImageResource(R.drawable.finlandia);
         } else if (this.articleSelected.getSeller().getCountry().equals("NL")) {
        	 countrySeller.setImageResource(R.drawable.holanda);
         } else if (this.articleSelected.getSeller().getCountry().equals("PL")) {
        	 countrySeller.setImageResource(R.drawable.polonia);
         } else if (this.articleSelected.getSeller().getCountry().equals("CZ")) {
        	 countrySeller.setImageResource(R.drawable.republicatxeca);
         } else if (this.articleSelected.getSeller().getCountry().equals("GR")) {
        	 countrySeller.setImageResource(R.drawable.grecia);
         }else if (this.articleSelected.getSeller().getCountry().equals("IT")) {
        	 countrySeller.setImageResource(R.drawable.italia);
         }
    	 
    	 buttonBuy.setOnClickListener((ArticlesActivity)getActivity());
    	 
    	 if(!this.articleSelected.isFoil()) imageFoil.setImageResource(R.drawable.delete_icon);
    	 if(!this.articleSelected.isAltered()) imageAltered.setImageResource(R.drawable.delete_icon);
    	 if(!this.articleSelected.isPlayset()) imagePlayset.setImageResource(R.drawable.delete_icon);
    	 if(!this.articleSelected.isSigned()) imageSigned.setImageResource(R.drawable.delete_icon);
    	 if(this.articleSelected.getSeller().isCommercial()){
    		 professionalSeller.setImageResource(R.drawable.user_professional);
    	 }else{
    		 professionalSeller.destroyDrawingCache();
    	 }
    	 
    	 if (this.articleSelected.getCondition()!=null){
            if (this.articleSelected.getCondition().equals("MT")) {
                conditionCard.setText(this.activity.getStringResourceByName("condition_mint"));
            } else if (this.articleSelected.getCondition().equals("NM")) {
                conditionCard.setText(this.activity.getStringResourceByName("condition_near_mint"));
            } else if (this.articleSelected.getCondition().equals("EX")) {
            	conditionCard.setText(this.activity.getStringResourceByName("condition_excellet"));
            } else if (this.articleSelected.getCondition().equals("GD")) {
            	conditionCard.setText(this.activity.getStringResourceByName("condition_good"));
            }else if (this.articleSelected.getCondition().equals("LP")) {
            	conditionCard.setText(this.activity.getStringResourceByName("condition_light_played"));
            }else if (this.articleSelected.getCondition().equals("PL")) {
            	conditionCard.setText(this.activity.getStringResourceByName("condition_played"));
            }else if (this.articleSelected.getCondition().equals("PO")) {
            	conditionCard.setText(this.activity.getStringResourceByName("condition_poor"));
            }
         }
    	 
    	 
    	 switch (this.articleSelected.getLanguage().getIdLanguage()) {
         case 1:
        	 languageCard.setText(this.activity.getStringResourceByName("language_english"));
             break;
         case 2:
        	 languageCard.setText(this.activity.getStringResourceByName("language_french"));
             break;
         case 3:
        	 languageCard.setText(this.activity.getStringResourceByName("language_german"));
             break;
         case 4:
        	 languageCard.setText(this.activity.getStringResourceByName("language_spanish"));
             break;
         case 5:
        	 languageCard.setText(this.activity.getStringResourceByName("language_italian"));
             break;
         case 6:
        	 languageCard.setText(this.activity.getStringResourceByName("language_chinese"));
             break;   
         case 7:
        	 languageCard.setText(this.activity.getStringResourceByName("language_japanese"));
             break;
         case 8:
        	 languageCard.setText(this.activity.getStringResourceByName("language_portuguese"));
             break;
         case 10:
        	 languageCard.setText(this.activity.getStringResourceByName("language_korean"));
             break;
         case 11:
        	 languageCard.setText(this.activity.getStringResourceByName("language_chinese_t"));
             break;
             
    	 }

    	 
    	 /*if(this.articleSelected.getSeller().getReputation()==1){
    		 reputationSeller.setText(this.activity.getStringResourceByName("outstanding_seller"));
         }else if(this.articleSelected.getSeller().getReputation()==2){
        	 reputationSeller.setText(this.activity.getStringResourceByName("very_good_seller"));
         }else if(this.articleSelected.getSeller().getReputation()==3) {
        	 reputationSeller.setText(this.activity.getStringResourceByName("good_seller"));
         }else if(this.articleSelected.getSeller().getReputation()==4) {
        	 reputationSeller.setText(this.activity.getStringResourceByName("average_seller"));
         }else if(this.articleSelected.getSeller().getReputation()==0) {
         	if(this.articleSelected.getSeller().getRiskGroup()==1){
         		reputationSeller.setText(this.activity.getStringResourceByName("new_seller"));
         	}else{
         		reputationSeller.setText(this.activity.getStringResourceByName("average_seller"));
         	}
         }*/
    	 
    	 if(this.articleSelected.getSeller().getReputation()==1){
    		 reputationSeller.setImageResource(R.drawable.reputation1);
         }else if(this.articleSelected.getSeller().getReputation()==2){
        	 reputationSeller.setImageResource(R.drawable.reputation2);
         }else if(this.articleSelected.getSeller().getReputation()==3) {
        	 reputationSeller.setImageResource(R.drawable.reputation3);
         }else if(this.articleSelected.getSeller().getReputation()==4) {
        	 reputationSeller.setImageResource(R.drawable.reputation0);
         }else if(this.articleSelected.getSeller().getReputation()==0) {
         	if(this.articleSelected.getSeller().getRiskGroup()==1){
         		reputationSeller.setImageResource(R.drawable.no_reputation);
         	}else{
         		reputationSeller.setImageResource(R.drawable.reputation0);
         	}
         }
    	 
    	 
    	 List<String> numsArticles = new ArrayList<String>(); 
    	 
    	 for(int x = articleSelected.getCount(); x > 0;x--){
    		 numsArticles.add(Integer.toString(x));
    	 }
    	 
    	 ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, numsArticles);
    	 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	 howMany.setAdapter(adapter);
    	 howMany.setOnItemSelectedListener(this);

    	 cardPriceSpinner.setText("x "+String.valueOf(this.articleSelected.getPrice())+"€");
    	 
    	 //I'm going to calc the total value
    	 String amountString = (String)howMany.getSelectedItem();
    	 int amount = Integer.valueOf(amountString);
    	 Float price = Float.valueOf(this.articleSelected.getPrice());
    	 Float totalPrice = price * amount;
    	 
    	 DecimalFormat df = new DecimalFormat();
         df.setMinimumFractionDigits(2);
    	 
    	 totalValue.setText(String.valueOf(df.format(totalPrice))+"€");
    	 
    	 //I load the scan of the article
    	 if (bmp!=null){
    		 scanImage.setImageBitmap(bmp);
    	 }

    	 activity.setImageBitMap(bmp);
		 setHasOptionsMenu(true);
        
        return rootView;
    }
    
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item;
        
        item= menu.findItem(R.id.action_filter);
        item.setVisible(false);
        item.setEnabled(false);
        
        /*item= menu.findItem(R.id.action_legend);
        item.setVisible(false);
        item.setEnabled(false);*/
        super.onPrepareOptionsMenu(menu);
    }
    
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ArticleDetailsFragment newInstance(int sectionNumber) {
    	ArticleDetailsFragment fragment = new ArticleDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


	@Override
	public void onItemSelected(AdapterView<?> view, View item, int arg2,
			long arg3) {
		
		Spinner howMany = (Spinner) rootView.findViewById(R.id.spinnerNumArticlesToBuy);
		TextView totalValue = (TextView) rootView.findViewById(R.id.textTotalValue);
   	 
		switch(view.getId()){
			case R.id.spinnerNumArticlesToBuy:
				String amountString = (String)howMany.getSelectedItem();
			   	int amount = Integer.valueOf(amountString);
			   	Float price = Float.valueOf(this.articleSelected.getPrice());
			   	Float totalPrice = price * amount;
			   	
			    DecimalFormat df = new DecimalFormat();
		        df.setMinimumFractionDigits(2);
		         
			   	totalValue.setText(String.valueOf(df.format(totalPrice))+"€");
				break;
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
