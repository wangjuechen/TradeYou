package com.jc.android.tradeyou;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jc.android.tradeyou.models.SubcategoryB;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.yhq.dialog.core.DialogBuilder;


public class ListingConditionFragment extends Fragment {

    private static final String NAME_ARG_TAG = ListingActivity.CLICKEDCATEGORYNAME_TAG;

    private static final String SUBCATEGORY_ARG_TAG = ListingActivity.CLICKEDCATEGORYSUBLIST_TAG;

    private ArrayList<SubcategoryB> mSubcategoryBList = new ArrayList<>();

    private ArrayList<String> mSubcategoryBNameList = new ArrayList<>();

    private String mSelectedSubcategoryBName;

    private Unbinder unbinder;

    private OnFragmentInteractionListener mListener;

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

            mSelectedSubcategoryBName = getArguments().getString(NAME_ARG_TAG);

            mSubcategoryBList = (ArrayList<SubcategoryB>) getArguments().getSerializable(SUBCATEGORY_ARG_TAG);

            fetchSecondCategory();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listing_condition, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        tv_first_condition.setText(mSelectedSubcategoryBName);

        return rootView;
    }

    @OnClick(R.id.tv_listing_category_first_condition)
    public void chooseSecondCategory(View view) {

        DialogBuilder.listDialog(getActivity()).setChoiceItems(mSubcategoryBNameList)
                .setChoiceType(DialogBuilder.TYPE_CHOICE_NORMAL)
                .setOnChoiceListener(new DialogBuilder.OnChoiceListener() {

                    @Override
                    public void onChoiceItem(int index, Object item) {
                        tv_second_condition.setText(mSubcategoryBNameList.get(index));
                        tv_second_condition.setVisibility(View.VISIBLE);
                    }
                }).setOnChoiceClickListener(new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    private void fetchSecondCategory() {
        for (int i = 0; i < mSubcategoryBList.size(); i++) {
            mSubcategoryBNameList.add(mSubcategoryBList.get(i).getName());
        }
    }


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

        void onFragmentInteraction(Uri uri);
    }
}
