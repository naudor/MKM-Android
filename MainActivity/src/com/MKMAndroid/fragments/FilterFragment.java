package com.MKMAndroid.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.MKMAndroid.activities.ArticlesActivity;
import com.MKMAndroid.app.R;
import com.MKMAndroid.classes.Constants;
import com.MKMAndroid.classes.Filter;
import com.smaato.soma.BannerView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.MKMAndroid.fragments.FilterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link com.MKMAndroid.fragments.FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class FilterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    View rootView;
    ArticlesActivity activity;
    String urlFinal;
    private ProgressDialog dialog;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArticlesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilterFragment newInstance(String param1) {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public FilterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activity = (ArticlesActivity) this.getActivity();
        Filter filter = activity.getFilter();

        //Recupero els objectes del layout
        rootView = inflater.inflate(R.layout.fragment_filter, container, false);
        this.dialog = activity.getDialog();
        
        ((ArticlesActivity) getActivity()).setActionBarTitle("filter_title");

        CheckBox isFoil = (CheckBox)rootView.getRootView().findViewById(R.id.checkFilterFoil);
        CheckBox isAltered = (CheckBox)rootView.getRootView().findViewById(R.id.checkFilterAltered);
        CheckBox isSigned = (CheckBox)rootView.getRootView().findViewById(R.id.checkFilterSigned);
        CheckBox isPlayset = (CheckBox)rootView.getRootView().findViewById(R.id.checkFilterPlayset);
        CheckBox conditionMT = (CheckBox)rootView.getRootView().findViewById(R.id.checkFilterMT);
        CheckBox conditionNM = (CheckBox)rootView.getRootView().findViewById(R.id.checkFilterNM);
        CheckBox conditionEX = (CheckBox)rootView.getRootView().findViewById(R.id.checkFilterEX);
        CheckBox conditionGD = (CheckBox)rootView.getRootView().findViewById(R.id.checkFilterGD);
        CheckBox conditionLP = (CheckBox)rootView.getRootView().findViewById(R.id.checkFilterLP);
        CheckBox conditionPL = (CheckBox)rootView.getRootView().findViewById(R.id.checkFilterPL);
        CheckBox conditionPO = (CheckBox)rootView.getRootView().findViewById(R.id.checkFilterPO);
        CheckBox languageEN = (CheckBox)rootView.getRootView().findViewById(R.id.checkFilterEN);
        CheckBox languageFR = (CheckBox)rootView.getRootView().findViewById(R.id.checkFilterFR);
        CheckBox languageGR = (CheckBox)rootView.getRootView().findViewById(R.id.checkFilterGR);
        CheckBox languageSP = (CheckBox)rootView.getRootView().findViewById(R.id.checkFilterSP);
        CheckBox languageIT = (CheckBox)rootView.getRootView().findViewById(R.id.checkFilterIT);
        CheckBox languageCH = (CheckBox)rootView.getRootView().findViewById(R.id.checkFilterCH);
        CheckBox languageJP = (CheckBox)rootView.getRootView().findViewById(R.id.checkFilterJP);
        CheckBox languagePT = (CheckBox)rootView.getRootView().findViewById(R.id.checkFilterPT);
        CheckBox languageRU = (CheckBox)rootView.getRootView().findViewById(R.id.checkFilterRU);
        CheckBox languageKO = (CheckBox)rootView.getRootView().findViewById(R.id.checkFilterKO);
        CheckBox languageCHT = (CheckBox)rootView.getRootView().findViewById(R.id.checkFilterCHT);
        EditText amount = (EditText) rootView.getRootView().findViewById(R.id.textAmount);
        Button filterButton = (Button) rootView.findViewById(R.id.buttonFilter);
        Button filterReset = (Button) rootView.findViewById(R.id.buttonResetFilter);

        filterButton.setOnClickListener((View.OnClickListener) getActivity());
        filterReset.setOnClickListener((View.OnClickListener) getActivity());

        //AdapterLanguages languageAdapter = new AdapterLanguages(getActivity());
       // AdapterConditions conditionAdapter = new AdapterConditions(getActivity());

        //Read the filter status
        if (filter != null)
        {
            isFoil.setChecked(filter.isFoil());
            isAltered.setChecked(filter.isAltered());
            isSigned.setChecked(filter.isSigned());
            isPlayset.setChecked(filter.isPlayset());
            conditionMT.setChecked(filter.isConditionMT());
            conditionNM.setChecked(filter.isConditionNM());
            conditionEX.setChecked(filter.isConditionEX());
            conditionGD.setChecked(filter.isConditionGD());
            conditionLP.setChecked(filter.isConditionLP());
            conditionPL.setChecked(filter.isConditionPL());
            conditionPO.setChecked(filter.isConditionPO());
            languageEN.setChecked(filter.isLanguageEN());
            languageFR.setChecked(filter.isLanguageFR());
            languageGR.setChecked(filter.isLanguageGR());
            languageSP.setChecked(filter.isLanguageSP());
            languageIT.setChecked(filter.isLanguageIT());
            languageCH.setChecked(filter.isLanguageCH());
            languageJP.setChecked(filter.isLanguageJP());
            languagePT.setChecked(filter.isLanguagePT());
            languageRU.setChecked(filter.isLanguageRU());
            languageKO.setChecked(filter.isLanguageKO());
            languageCHT.setChecked(filter.isLanguageCHT());

            amount.setText(String.valueOf(filter.getAmount()));
        }

        bannerSetup(rootView);

        return rootView;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void bannerSetup(View view){
    	/*BannerView mBanner = (BannerView) view.findViewById((R.id.banner_filter));
        mBanner.getAdSettings().setPublisherId(Constants.publisherId);
        mBanner.getAdSettings().setAdspaceId(Constants.adspaceId);
        mBanner.setAutoReloadFrequency(Constants.bannerRefreshTime);
        mBanner.getUserSettings().setKeywordList(Constants.keywordList); 
        mBanner.getUserSettings().setSearchQuery(Constants.searchQuery);
        mBanner.asyncLoadNewBanner();*/
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item= menu.findItem(R.id.action_filter);
        item.setVisible(false);
        item.setEnabled(false);
        super.onPrepareOptionsMenu(menu);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
