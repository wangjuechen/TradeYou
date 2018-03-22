package com.jc.android.tradeyou;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jc.android.tradeyou.api.ServiceGenerator;
import com.jc.android.tradeyou.api.TradeMeApI;
import com.jc.android.tradeyou.models.Category;
import com.jc.android.tradeyou.models.SubcategoryA;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.yhq.dialog.core.DialogBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListingConditionFragment extends Fragment {

    private static final String NAME_ARG_TAG = ListingActivity.CLICKEDCATEGORYNAME_TAG;

    private static final String NUMBER_ARG_TAG = ListingActivity.CLICKEDCATEGORYNUMBER_TAG;

    private ArrayList<SubcategoryA> mSubcategoryBList = new ArrayList<>();

    private ArrayList<SubcategoryA> mSubcategoryCList = new ArrayList<>();

    private ArrayList<String> mSubcategoryBNameList = new ArrayList<>();

    private ArrayList<String> mSubcategoryCNameList = new ArrayList<>();

    private String mSelectedSubcategoryName;

    private String mSelectedSubcategoryNumber;

    private Unbinder unbinder;

    private OnFragmentInteractionListener mListener;

    private TradeMeApI tradeMeApi;

    @BindView(R.id.tv_listing_category_first_condition)
    TextView tv_first_condition;
    @BindView(R.id.tv_listing_category_second_condition)
    TextView tv_second_condition;
    @BindView(R.id.tv_listing_category_third_condition)
    TextView tv_third_condition;
    @BindView(R.id.tv_listing_category_forth_condition)
    TextView tv_forth_condition;
    @BindView(R.id.tv_listing_category_fifth_condition)
    TextView tv_fifth_condition;


    public ListingConditionFragment() {

    }

    public static ListingConditionFragment newInstance(String param1) {
        ListingConditionFragment fragment = new ListingConditionFragment();
        Bundle args = new Bundle();
        args.putString(NAME_ARG_TAG, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            mSelectedSubcategoryName = getArguments().getString(NAME_ARG_TAG);

            mSelectedSubcategoryNumber = getArguments().getString(NUMBER_ARG_TAG);

            loadSecondSubCategoryAPI(mSelectedSubcategoryNumber);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listing_condition, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        tv_first_condition.setText(mSelectedSubcategoryName);

        return rootView;
    }

    @OnClick(R.id.tv_listing_category_first_condition)
    public void chooseSecondCategory(View view) {

        DialogBuilder.listDialog(getActivity()).setChoiceItems(mSubcategoryBNameList)
                .setChoiceType(DialogBuilder.TYPE_CHOICE_NORMAL)
                .setOnChoiceListener(new DialogBuilder.OnChoiceListener() {

                    @Override
                    public void onChoiceItem(int index, Object item) {

                        loadThirdSubcategoryAPI(mSubcategoryBList.get(index).getIdentifier_number(), index);

                    }
                }).setOnChoiceClickListener(new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                mListener.onFragmentInteraction(mSubcategoryBList.get(which).getIdentifier_number());

            }
        }).show();
    }


    @OnClick(R.id.tv_listing_category_second_condition)
    public void chooseThirdCategory(View view) {

        DialogBuilder.listDialog(getActivity()).setChoiceItems(mSubcategoryCNameList)
                .setChoiceType(DialogBuilder.TYPE_CHOICE_NORMAL)
                .setOnChoiceListener(new DialogBuilder.OnChoiceListener() {

                    @Override
                    public void onChoiceItem(int index, Object item) {
                        tv_third_condition.setText(mSubcategoryCNameList.get(index));
                        tv_third_condition.setVisibility(View.VISIBLE);
                    }
                }).setOnChoiceClickListener(new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onFragmentInteraction(mSubcategoryCList.get(which).getIdentifier_number());
            }
        }).show();
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
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(String categoryNumber);
    }


    private void loadSecondSubCategoryAPI(String queryCategory) {

        tradeMeApi = ServiceGenerator.createService(TradeMeApI.class, null);

        tradeMeApi.getCategory(queryCategory, 1).enqueue(new Callback<Category>() {

            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {

                    mSubcategoryBList = response.body().getSubcategories();

                    if (mSubcategoryBList != null && mSubcategoryBList.size() > 0) {
                        fetchSecondCategory();
                    }

                    Log.d("ListConditionFragment", "Loaded from SecondSubCategoryAPI is complete");

                } else {
                    int statusCode = response.code();

                    if (statusCode == 500)
                        Toast.makeText(getActivity(), "Our serves have some issues :( They will be back shortly", Toast.LENGTH_SHORT).show();

                    Log.d("ListConditionFragment", "Error code: " + statusCode + response.message());

                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                if (t instanceof IOException) {
                    // IOException is because Internet issue
                    Toast.makeText(getActivity(), "Internet is disconnected :( Check internet connection", Toast.LENGTH_SHORT).show();

                } else {
                    //Other cause which mean Object format wrong or API problem
                    Log.d("MarketCategoryActivity", "Error: " + t.getMessage());

                }
            }
        });
    }

    private void loadThirdSubcategoryAPI(String queryCategory, final int index) {

        tradeMeApi = ServiceGenerator.createService(TradeMeApI.class, null);

        tradeMeApi.getCategory(queryCategory, 1).enqueue(new Callback<Category>() {

            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {

                    mSubcategoryCList = response.body().getSubcategories();

                    if (mSubcategoryCList != null && mSubcategoryCList.size() > 0)
                        fetchThirdCategory();
                    tv_second_condition.setText(mSubcategoryBNameList.get(index));
                    tv_second_condition.setVisibility(View.VISIBLE);
                    tv_third_condition.setVisibility(View.GONE);


                    Log.d("ListConditionFragment", "Loaded from ThirdSubCategoryAPI is complete");

                } else {
                    int statusCode = response.code();

                    if (statusCode == 500)
                        Toast.makeText(getActivity(), "Our serves have some issues :( They will be back shortly", Toast.LENGTH_SHORT).show();

                    Log.d("ListConditionFragment", "Error code: " + statusCode + response.message());

                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                if (t instanceof IOException) {
                    // IOException is because Internet issue
                    Toast.makeText(getActivity(), "Internet is disconnected :( Check internet connection", Toast.LENGTH_SHORT).show();

                } else {
                    //Other cause which mean Object format wrong or API problem
                    Log.d("MarketCategoryActivity", "Error: " + t.getMessage());

                }
            }
        });
    }


    private void fetchSecondCategory() {
        mSubcategoryBNameList.clear();
        for (int i = 0; i < mSubcategoryBList.size(); i++) {
            mSubcategoryBNameList.add(mSubcategoryBList.get(i).getName());
        }
    }

    private void fetchThirdCategory() {
        mSubcategoryCNameList.clear();
        for (int i = 0; i < mSubcategoryCList.size(); i++) {
            mSubcategoryCNameList.add(mSubcategoryCList.get(i).getName());
        }
    }
}
