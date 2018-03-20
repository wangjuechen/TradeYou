package com.jc.android.tradeyou;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jc.android.tradeyou.adapter.ItemListingAdapter;
import com.jc.android.tradeyou.api.APIError;
import com.jc.android.tradeyou.api.ErrorUtils;
import com.jc.android.tradeyou.api.ServiceGenerator;
import com.jc.android.tradeyou.api.TradeMeApI;
import com.jc.android.tradeyou.models.ItemDetailsFromListing;
import com.jc.android.tradeyou.models.Listing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListingContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListingContentFragment extends Fragment {

    private static final String NUMBER_TAG = ListingActivity.CLICKEDCATEGORYNUMBER_TAG;

    private static final String CATEGORY_NUMBER = "category_number";

    private TradeMeApI tradeMeApi;

    private ItemListingAdapter mItemListingAdapter;

    private List<ItemDetailsFromListing> mItemDetailsList = new ArrayList<>();

    private RecyclerView mListingRecyclerView;

    private String mCategoryNumber;

    public ListingContentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param param1 Parameter 1.
     * @return A new instance of fragment ListingContentFragment.
     */
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
            mCategoryNumber = getArguments().getString(NUMBER_TAG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState != null)
            mCategoryNumber = savedInstanceState.getString(CATEGORY_NUMBER);

        loadTradeMeAPI();

        View rootView = inflater.inflate(R.layout.fragment_listing, container, false);

        mListingRecyclerView = rootView.findViewById(R.id.rv_itemsListing);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(CATEGORY_NUMBER, mCategoryNumber);
    }

    private void loadTradeMeAPI() {

        String consumerKey = "A1AC63F0332A131A78FAC304D007E7D1";
        String consumerSecret = "EC7F18B17A062962C6930A8AE88B16C7";

        tradeMeApi = ServiceGenerator.createService(TradeMeApI.class,
                " OAuth oauth_consumer_key=\"A1AC63F0332A131A78FAC304D007E7D1\", oauth_signature_method=\"PLAINTEXT\", oauth_signature=\"EC7F18B17A062962C6930A8AE88B16C7&\"");

        tradeMeApi.getListing(mCategoryNumber).enqueue(new Callback<Listing>() {

            @Override
            public void onResponse(Call<Listing> call, Response<Listing> response) {
                if (response.isSuccessful()) {

                    mItemDetailsList = response.body().getItemDetailsList();

                    mItemListingAdapter = new ItemListingAdapter(getActivity(), mItemDetailsList);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

                    mListingRecyclerView.setLayoutManager(layoutManager);

                    mListingRecyclerView.setHasFixedSize(true);

                    mListingRecyclerView.setAdapter(mItemListingAdapter);

                    DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mListingRecyclerView.getContext(),
                            layoutManager.getOrientation());
                    mListingRecyclerView.addItemDecoration(mDividerItemDecoration);

                    Log.d("ListingContentFragment", "Listing loaded from API");

                } else {

                    int statusCode = response.code();

                    Toast.makeText(getActivity(), "Listing fetched failed :( Please try again later", Toast.LENGTH_SHORT).show();

                    APIError error = ErrorUtils.parseError(response);

                    Log.d("ListingContentFragment", "Error code: " + statusCode + response.message() + error.message());

                }
            }

            @Override
            public void onFailure(Call<Listing> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "Internet is disconnected :( Check internet connection", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getActivity(), "Listing fetched failed :( Please try again later", Toast.LENGTH_SHORT).show();
                    Log.d("MarketCategoryActivity", "Error: " + t.getMessage());

                }
            }
        });
    }

    public void setCategoryNumber(String number){
        mCategoryNumber = number;
    }


}
