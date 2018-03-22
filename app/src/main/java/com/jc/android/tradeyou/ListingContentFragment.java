package com.jc.android.tradeyou;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
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

    private String mCategoryNumber;

    @BindView(R.id.layout_noData_message)
    ConstraintLayout mEmptyListingView;

    @BindView(R.id.rv_itemsListing)
    RecyclerView mListingRecyclerView;

    @BindView(R.id.progressBar_listingPage)
    ProgressBar mProgressBar;

    private Unbinder unbinder;

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
        if (savedInstanceState != null)
            mCategoryNumber = savedInstanceState.getString(CATEGORY_NUMBER);

        loadTradeMeAPI();

        View rootView = inflater.inflate(R.layout.fragment_listing, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(CATEGORY_NUMBER, mCategoryNumber);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    private void loadTradeMeAPI() {

        String consumerKey = "A1AC63F0332A131A78FAC304D007E7D1";
        String consumerSecret = "EC7F18B17A062962C6930A8AE88B16C7";

        tradeMeApi = ServiceGenerator.createService(TradeMeApI.class,
                " OAuth oauth_consumer_key=\"" + consumerKey + "\"," + " oauth_signature_method=\"PLAINTEXT\", oauth_signature=\"" + consumerSecret + "&\"");

        //"List" query value make listing photos get more resolution, instead of being blurred
        tradeMeApi.getListing(mCategoryNumber, "List").enqueue(new Callback<Listing>() {

            @Override
            public void onResponse(Call<Listing> call, Response<Listing> response) {
                if (response.isSuccessful()) {

                    mItemDetailsList = response.body().getItemDetailsList();

                    Log.d("ListingContentFragment", "Listing loaded from API");

                    if (mItemDetailsList.size() > 0) {
                        displayRecyclerView();
                    } else {
                        displayNoListMessageView();
                    }

                    setUpProgressBarInvisible();

                } else {

                    APIError error = ErrorUtils.parseError(response);

                    int statusCode = response.code();

                    if (statusCode == 500)
                        Toast.makeText(getActivity(), "Ops :( Please try again later", Toast.LENGTH_SHORT).show();

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

    private void displayNoListMessageView() {
        mEmptyListingView.setVisibility(View.VISIBLE);
    }

    private void displayRecyclerView() {

        mItemListingAdapter = new ItemListingAdapter(getActivity(), mItemDetailsList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        mListingRecyclerView.setLayoutManager(layoutManager);

        mListingRecyclerView.setHasFixedSize(true);

        mListingRecyclerView.setAdapter(mItemListingAdapter);

        DividerItemDecoration mDividerItemDecoration =
                new DividerItemDecoration(mListingRecyclerView.getContext(),layoutManager.getOrientation());
        mDividerItemDecoration.setDrawable(getResources().getDrawable(R.color.divider_color));

        mListingRecyclerView.addItemDecoration(mDividerItemDecoration);

    }

    private void setUpProgressBarInvisible() {
        mProgressBar.setProgress(100);
        mProgressBar.setVisibility(View.GONE);
    }

    public void setCategoryNumber(String number) {
        mCategoryNumber = number;
    }

}
