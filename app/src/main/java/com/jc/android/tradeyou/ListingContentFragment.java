package com.jc.android.tradeyou;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jc.android.tradeyou.adapter.ItemListingAdapter;
import com.jc.android.tradeyou.api.ApiUtils;
import com.jc.android.tradeyou.api.TradeMeApI;
import com.jc.android.tradeyou.models.Category;
import com.jc.android.tradeyou.models.ItemDetails;
import com.jc.android.tradeyou.models.Listing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListingContentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListingContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListingContentFragment extends Fragment {

    private static final String NUMBER_TAG = ListingActivity.CLICKEDCATEGORYNUMBER_TAG;

    private String mSelectedSubcategoryBNumber;

    private TradeMeApI tradeMeApi;

    private OnFragmentInteractionListener mListener;

    private ItemListingAdapter mItemListingAdapter;

    private List<ItemDetails> mItemDetailsList = new ArrayList<>();

    public ListingContentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ListingContentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListingContentFragment newInstance(String param1) {
        ListingContentFragment fragment = new ListingContentFragment();
        Bundle args = new Bundle();
        args.putString(NUMBER_TAG, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            mSelectedSubcategoryBNumber = getArguments().getString(NUMBER_TAG);

            loadTradeMeAPI();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listing, container, false);

        RecyclerView listingRecyclerView = rootView.findViewById(R.id.rv_itemsListing);

        mItemListingAdapter = new ItemListingAdapter(getActivity(), mItemDetailsList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        listingRecyclerView.setLayoutManager(layoutManager);

        listingRecyclerView.setHasFixedSize(true);

        listingRecyclerView.setAdapter(mItemListingAdapter);

        return rootView;
    }

    private void loadTradeMeAPI() {

        tradeMeApi = ApiUtils.getTradeMeApi();//TODO: here need OAuth to access

        tradeMeApi.getListing(mSelectedSubcategoryBNumber).enqueue(new Callback<Listing>() {

            @Override
            public void onResponse(Call<Listing> call, Response<Listing> response) {
                if (response.isSuccessful()) {

                    mItemDetailsList = response.body().getItemDetailsList();

                    Log.d("ListingContentFragment", "posts loaded from API");

                } else {
                    int statusCode = response.code();

                    Log.d("ListingContentFragment", "Error code: " + statusCode + response.message());
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<Listing> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "Internet is disconnected :( Check internet connection", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                } else {
                    Toast.makeText(getActivity(), "Data fetched failed :( Please try again later", Toast.LENGTH_SHORT).show();
                    Log.d("MarketCategoryActivity", "Error: " + t.getMessage());

                }
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        void onFragmentInteraction(Uri uri);
    }
}