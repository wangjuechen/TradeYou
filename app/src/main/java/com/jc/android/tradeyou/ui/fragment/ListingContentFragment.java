package com.jc.android.tradeyou.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jc.android.tradeyou.BuildConfig;
import com.jc.android.tradeyou.R;
import com.jc.android.tradeyou.data.api.ServiceGenerator;
import com.jc.android.tradeyou.data.api.TradeMeApi;
import com.jc.android.tradeyou.data.api.util.APIError;
import com.jc.android.tradeyou.data.api.util.ErrorUtils;
import com.jc.android.tradeyou.data.models.listing.Listing;
import com.jc.android.tradeyou.data.models.listing.ListingDetails;
import com.jc.android.tradeyou.ui.activity.ListingActivity;
import com.jc.android.tradeyou.ui.adapter.ListingAdapter;

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
 * This fragment for listing content view under action bar
 */
public class ListingContentFragment extends Fragment {

    private static final String TAG = ListingConditionFragment.class.getSimpleName();

    private static final String ARGUMENT_NAME = ListingActivity.EXTRA_LISTING_NUMBER;

    private final String BUNDLE_RECYCLE_LAYOUT = "BUNDLE_RECYCLE_LAYOUT";

    private static final String BUNDLE_CATEGORY_NUMBER = "BUNDLE_CATEGORY_NUMBER";

    private List<ListingDetails> mItemDetailsList = new ArrayList<>();

    private String mCategoryNumber;

    private LinearLayoutManager layoutManager;

    @BindView(R.id.layout_empty_message)
    ConstraintLayout mEmptyListingView;

    @BindView(R.id.rv_itemsListing)
    RecyclerView mListingRecyclerView;

    @BindView(R.id.progressBar_listingPage)
    ProgressBar mProgressBar;

    @BindView(R.id.swiperefresh_listing)
    SwipeRefreshLayout swipeContainer;

    private Unbinder unbinder;

    public ListingContentFragment() {
        // Required empty public constructor
    }


    public static ListingContentFragment newInstance(String param1) {
        ListingContentFragment fragment = new ListingContentFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_NAME, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mCategoryNumber = getArguments().getString(ARGUMENT_NAME);

        if (savedInstanceState != null)
            mCategoryNumber = savedInstanceState.getString(BUNDLE_CATEGORY_NUMBER);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        loadListingApi(savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_listing_content, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        layoutManager = new LinearLayoutManager(getActivity());

        DividerItemDecoration mDividerItemDecoration =
                new DividerItemDecoration(mListingRecyclerView.getContext(), layoutManager.getOrientation());
        mDividerItemDecoration.setDrawable(getResources().getDrawable(R.color.divider_color));

        mListingRecyclerView.addItemDecoration(mDividerItemDecoration);

        swipeContainer.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                loadListingApi(null);
            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mListingRecyclerView.getLayoutManager() != null)
            outState.putParcelable(BUNDLE_RECYCLE_LAYOUT, mListingRecyclerView.getLayoutManager().onSaveInstanceState());
        outState.putString(BUNDLE_CATEGORY_NUMBER, mCategoryNumber);
    }

    private void loadListingApi(final Bundle savedInstanceState) {

        String key = BuildConfig.TRADE_ME_API_CONSUMER_KEY;
        String secret = BuildConfig.TRADE_ME_API_CONSUMER_SECRET;

        TradeMeApi tradeMeApi = ServiceGenerator.createService(TradeMeApi.class,
                " OAuth oauth_consumer_key=\""
                        + key + "\","
                        + " oauth_signature_method=\"PLAINTEXT\", oauth_signature=\""
                        + secret + "&\"");

        //"List" query value make listing photos get more resolution, instead of being blurred
        tradeMeApi.getListing(mCategoryNumber, "List").enqueue(new Callback<Listing>() {

            @Override
            public void onResponse(Call<Listing> call, Response<Listing> response) {
                if (response.isSuccessful()) {

                    if (mItemDetailsList != null && mItemDetailsList.size() > 0)
                        mItemDetailsList.clear();

                    mItemDetailsList = response.body().getItemDetailsList();

                    if (BuildConfig.DEBUG) Log.d(TAG, "Listing loaded from API");

                    if (mItemDetailsList.size() > 0) {
                        displayRecyclerView(savedInstanceState);
                    } else {
                        displayNoListMessageView();
                    }

                    setUpProgressBarInvisible();
                    swipeContainer.setRefreshing(false);

                } else {

                    APIError error = ErrorUtils.parseError(response);

                    int statusCode = response.code();

                    if (statusCode == 500)
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_server_issue_toast), Toast.LENGTH_SHORT).show();

                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "Error code: " + statusCode + response.message() + error.message());

                }
            }

            @Override
            public void onFailure(Call<Listing> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_internet_issue_toast), Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getActivity(), getResources().getString(R.string.error_other_issue_toast), Toast.LENGTH_SHORT).show();
                    if (BuildConfig.DEBUG) Log.d(TAG, "Error: " + t.getMessage());

                }
            }
        });
    }

    private void displayNoListMessageView() {
        mEmptyListingView.setVisibility(View.VISIBLE);
    }

    private void displayRecyclerView(Bundle savedInstanceState) {

        ListingAdapter mListingAdapter = new ListingAdapter(getActivity(), mItemDetailsList);

        layoutManager = new LinearLayoutManager(getActivity());

        mListingRecyclerView.setLayoutManager(layoutManager);

        mListingRecyclerView.setHasFixedSize(true);

        mListingRecyclerView.setAdapter(mListingAdapter);

        if (savedInstanceState != null) {
            mCategoryNumber = savedInstanceState.getString(BUNDLE_CATEGORY_NUMBER);
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLE_LAYOUT);
            mListingRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }

    }

    private void setUpProgressBarInvisible() {
        mProgressBar.setProgress(100);
        mProgressBar.setVisibility(View.GONE);
    }

}
