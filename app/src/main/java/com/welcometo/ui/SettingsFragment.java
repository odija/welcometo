package com.welcometo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.welcometo.R;
import com.welcometo.helpers.Constants;
import com.welcometo.helpers.Country;
import com.welcometo.helpers.DataBaseHelper;
import com.welcometo.helpers.LogHelper;
import com.welcometo.helpers.SharedPreferencesHelper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnSettingsListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Country mCurrentCountry;
    private Country mHomeCountry;

    private OnSettingsListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCurrentCountry = getArguments().getParcelable(Constants.PARAM_COUNTRY);
            mHomeCountry = DataBaseHelper.getInstance(getActivity()).getCountryByCode(SharedPreferencesHelper.getInstance(getActivity()).getString(Constants.OWN_COUNTRY_CODE));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View localView = inflater.inflate(R.layout.settings, container, false);

        CountryAdapterItem adapterItemFrom = new CountryAdapterItem(getActivity(), R.layout.country_item, getCountryList(R.string.where_are_you_from));
        adapterItemFrom.setDropDownViewResource(R.layout.country_list_item);

        Spinner spinnerFrom = (Spinner)localView.findViewById(R.id.countryListFrom);
        spinnerFrom.setAdapter(adapterItemFrom);
        spinnerFrom.setSelection(adapterItemFrom.getPosition(mHomeCountry));
        spinnerFrom.setOnItemSelectedListener(new fromSpinnerListener());

        CountryAdapterItem adapterItemTo = new CountryAdapterItem(getActivity(), R.layout.country_item, getCountryList(R.string.what_is_your_dest));
        adapterItemFrom.setDropDownViewResource(R.layout.country_list_item);

        Spinner spinnerTo = (Spinner)localView.findViewById(R.id.countryListTo);
        spinnerTo.setAdapter(adapterItemTo);

        spinnerTo.setSelection(adapterItemTo.getPosition(mCurrentCountry));

        spinnerTo.setOnItemSelectedListener(new toSpinnerListener());

        return localView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSettingsListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private ArrayList<Country> getCountryList(int resID) {
        // init country list
        Country localCountry = new Country();
        localCountry.setName(getResources().getString(resID));
        ArrayList<Country> countries = new ArrayList<Country>();
        countries.add(localCountry);
        countries.addAll(DataBaseHelper.getInstance(getActivity()).getCountries());
        return countries;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnSettingsListener {
        void onHomeLand(String countryCode);
        void onCurrentCountry(String countryCode);
    }

    class fromSpinnerListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView paramAdapterView, View paramView, int paramInt, long paramLong) {
            if (paramInt > 0) {
                Country country = (Country)paramAdapterView.getAdapter().getItem(paramInt);
                String countryCode = country.getCode();

                LogHelper.d("New HOME country code: " + countryCode);

                if (SettingsFragment.this.mListener != null) {
                    SettingsFragment.this.mListener.onHomeLand(countryCode);
                }
            }
        }

        public void onNothingSelected(AdapterView paramAdapterView) {}
    }

    class toSpinnerListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView paramAdapterView, View paramView, int paramInt, long paramLong) {
            if (paramInt > 0) {

                Country country = (Country)paramAdapterView.getAdapter().getItem(paramInt);
                String countryCode = country.getCode();

                LogHelper.d("New CURRENT country code: " + countryCode);

                if (SettingsFragment.this.mListener != null) {
                    SettingsFragment.this.mListener.onCurrentCountry(countryCode);
                }
            }
        }

        public void onNothingSelected(AdapterView paramAdapterView) {}
    }

}
