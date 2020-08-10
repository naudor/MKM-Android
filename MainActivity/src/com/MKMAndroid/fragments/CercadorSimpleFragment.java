package com.MKMAndroid.fragments;

/**
 * Created by Arnau on 9/02/14.
 */

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.MKMAndroid.activities.MainActivity;
import com.MKMAndroid.app.R;
import com.MKMAndroid.classes.AdaptadorJocLlista;
import com.MKMAndroid.classes.Constants;
import com.MKMAndroid.classes.Game;
import com.smaato.soma.BannerView;

/**
 * A placeholder fragment containing a simple view.
 */
public class CercadorSimpleFragment extends Fragment
{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "1";
    private ProgressDialog dialog;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CercadorSimpleFragment newInstance(int sectionNumber)
    {
        CercadorSimpleFragment fragment = new CercadorSimpleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public CercadorSimpleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        List<Game> items = new ArrayList<Game>(3);

        Game joc1 = new Game();
        joc1.setName("Magic");
        joc1.setIdGame(1);

        Game joc2 = new Game();
        joc2.setName("World of Warcraft TCG");
        joc2.setIdGame(2);

        Game joc3 = new Game();
        joc3.setName("Yugioh");
        joc3.setIdGame(3);

        items.add(joc1);
        items.add(joc2);
        items.add(joc3);


        // omplo el combo dels jocs
        SpinnerAdapter adapter = new AdaptadorJocLlista(items, this.getActivity());
        Spinner spin = (Spinner)  rootView.findViewById(R.id.llistaJocs);
        spin.setAdapter(adapter);

        //acció buto de cercar
        final Button button = (Button) rootView.findViewById(R.id.botoCercarCartes);
        button.setOnClickListener((View.OnClickListener) getActivity());
        
        TextView text = (TextView) rootView.findViewById(R.id.cardName);
        
        //to catch enter key when someone are searching a card
        TextView.OnEditorActionListener exampleListener = new TextView.OnEditorActionListener()
        {
        	public boolean onEditorAction(TextView text, int actionId, KeyEvent event) 
        	{
        		   if (actionId == EditorInfo.IME_ACTION_DONE
        		      /*&& event.getAction() == KeyEvent.KEYCODE_ENTER*/) 
        		   { 
        		      MainActivity activity = (MainActivity)getActivity();
        		      activity.onClick(rootView);
        		   }
        		   return true;
        	}
        };
        
        text.setOnEditorActionListener(exampleListener);

        bannerSetup(rootView);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public void bannerSetup(View view){
    	/*BannerView mBanner = (BannerView) view.findViewById((R.id.banner_main));
        mBanner.getAdSettings().setPublisherId(Constants.publisherId);
        mBanner.getAdSettings().setAdspaceId(Constants.adspaceId);
        mBanner.setAutoReloadFrequency(Constants.bannerRefreshTime);
        mBanner.getUserSettings().setKeywordList(Constants.keywordList); 
        mBanner.getUserSettings().setSearchQuery(Constants.searchQuery);
        mBanner.asyncLoadNewBanner();*/
    }
}
