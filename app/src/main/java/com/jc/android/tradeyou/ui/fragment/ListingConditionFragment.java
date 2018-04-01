package com.jc.android.tradeyou.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jc.android.tradeyou.BuildConfig;
import com.jc.android.tradeyou.R;
import com.jc.android.tradeyou.data.api.ServiceGenerator;
import com.jc.android.tradeyou.data.api.TradeMeApi;
import com.jc.android.tradeyou.data.models.category.Category;
import com.jc.android.tradeyou.data.models.category.Subcategory;
import com.jc.android.tradeyou.ui.activity.ListingActivity;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.yhq.dialog.core.DialogBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This fragment for condition choosing view of action bar in listing screen
 */
public class ListingConditionFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = ListingConditionFragment.class.getSimpleName();

    private static final String ARGUMENT_NAME = ListingActivity.EXTRA_LISTING_NAME;

    private static final String ARGUMENT_NUMBER = ListingActivity.EXTRA_LISTING_NUMBER;

    private final String BUNDLE_SECONDTEXTVIEW_VISIBILITY = "BUNDLE_SECONDTEXTVIEW_VISIBILITY";
    private final String BUNDLE_SECONDTEXTVIEW_TEXT = "BUNDLE_SECONDTEXTVIEW_TEXT";
    private final String BUNDLE_SUBCATEGORYB_LIST = "BUNDLE_SUBCATEGORYB_LIST";

    private final String BUNDLE_THIRDTEXTVIEW_VISIBILITY = "BUNDLE_THIRDTEXTVIEW_VISIBILITY";
    private final String BUNDLE_THIRDTEXTVIEW_TEXT = "BUNDLE_THIRDTEXTVIEW_TEXT";
    private final String BUNDLE_SUBCATEGORYC_LIST = "BUNDLE_SUBCATEGORYC_LIST";

    private final String BUNDLE_FORTHTEXTVIEW_VISIBILITY = "BUNDLE_FORTHTEXTVIEW_VISIBILITY";
    private final String BUNDLE_FORTHTEXTVIEW_TEXT = "BUNDLE_FORTHTEXTVIEW_TEXT";
    private final String BUNDLE_SUBCATEGORYD_LIST = "BUNDLE_SUBCATEGORYD_LIST";

    private final String BUNDLE_FIFTHTEXTVIEW_VISIBILITY = "BUNDLE_FIFTHTEXTVIEW_VISIBILITY";
    private final String BUNDLE_FIFTHTEXTVIEW_TEXT = "BUNDLE_FIFTHTEXTVIEW_TEXT";
    private final String BUNDLE_SUBCATEGORYE_LIST = "BUNDLE_SUBCATEGORYE_LIST";


    private ArrayList<Subcategory> mSubcategoryBList = new ArrayList<>();

    private ArrayList<Subcategory> mSubcategoryCList = new ArrayList<>();

    private ArrayList<Subcategory> mSubcategoryDList = new ArrayList<>();

    private ArrayList<Subcategory> mSubcategoryEList = new ArrayList<>();

    private final ArrayList<String> mSubcategoryBNameList = new ArrayList<>();

    private final ArrayList<String> mSubcategoryCNameList = new ArrayList<>();

    private final ArrayList<String> mSubcategoryDNameList = new ArrayList<>();

    private final ArrayList<String> mSubcategoryENameList = new ArrayList<>();

    private String mCurrentSubcategoryName;

    private Unbinder unbinder;

    /**
     * This flag is to check how many layers of subcategory users has entered
     */
    private int currentLoadedSubcategoryLayer;

    private OnFragmentInteractionListener mListener;

    private TradeMeApi tradeMeApi;


    @BindView(R.id.text_listing_category_first_condition)
    TextView tv_first_condition;
    @BindView(R.id.text_listing_category_second_condition)
    TextView tv_second_condition;
    @BindView(R.id.text_listing_category_third_condition)
    TextView tv_third_condition;
    @BindView(R.id.text_listing_category_forth_condition)
    TextView tv_forth_condition;
    @BindView(R.id.text_listing_category_fifth_condition)
    TextView tv_fifth_condition;

    public ListingConditionFragment() {

    }

    public static ListingConditionFragment newInstance(String param1) {
        ListingConditionFragment fragment = new ListingConditionFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_NAME, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            mCurrentSubcategoryName = getArguments().getString(ARGUMENT_NAME);

            String mCurrentSubcategoryNumber = getArguments().getString(ARGUMENT_NUMBER);

            loadSecondSubCategoryApi(mCurrentSubcategoryNumber);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listing_condition, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        tv_first_condition.setText(mCurrentSubcategoryName);

        tv_first_condition.setOnClickListener(this);

        tv_second_condition.setOnClickListener(this);

        tv_third_condition.setOnClickListener(this);

        tv_forth_condition.setOnClickListener(this);

        tv_fifth_condition.setOnClickListener(this);

        return rootView;
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_listing_category_first_condition:
                currentLoadedSubcategoryLayer = 2;
                openCategoryChoosingDialog(mSubcategoryBNameList, mSubcategoryBList, mSubcategoryCNameList, mSubcategoryCList, currentLoadedSubcategoryLayer);

                break;
            case R.id.text_listing_category_second_condition:
                currentLoadedSubcategoryLayer = 3;
                openCategoryChoosingDialog(mSubcategoryCNameList, mSubcategoryCList, mSubcategoryDNameList, mSubcategoryDList, currentLoadedSubcategoryLayer);

                break;
            case R.id.text_listing_category_third_condition:
                currentLoadedSubcategoryLayer = 4;
                openCategoryChoosingDialog(mSubcategoryDNameList, mSubcategoryDList, mSubcategoryENameList, mSubcategoryEList, currentLoadedSubcategoryLayer);

                break;
            case R.id.text_listing_category_forth_condition:
                currentLoadedSubcategoryLayer = 5;
                openCategoryChoosingDialog(mSubcategoryENameList, mSubcategoryEList, null, null, currentLoadedSubcategoryLayer);

                break;
        }
    }

    private void openCategoryChoosingDialog(final ArrayList<String> nameList,
                                            final ArrayList<Subcategory> lastCategoryList,
                                            final ArrayList<String> nextLayerNameList,
                                            final ArrayList<Subcategory> categoryList,
                                            final int subcategoryLayer) {

        if (lastCategoryList != null && lastCategoryList.size() > 0) {
            DialogBuilder.listDialog(getActivity()).setChoiceItems(nameList)
                    .setChoiceType(DialogBuilder.TYPE_CHOICE_NORMAL)
                    .setOnChoiceListener(new DialogBuilder.OnChoiceListener() {

                        @Override
                        public void onChoiceItem(int index, Object item) {

                            loadSubcategoryApi(lastCategoryList.get(index).getIdentifier_number(), nameList, nextLayerNameList, categoryList, index, subcategoryLayer);

                        }
                    }).setOnChoiceClickListener(new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    mListener.onFragmentInteraction(lastCategoryList.get(which).getIdentifier_number());

                }
            }).show();
        }

    }

    /**
     * This subcategory happens in the beginning of the activity, differs from others
     *
     * @param queryCategory
     */
    private void loadSecondSubCategoryApi(String queryCategory) {

        tradeMeApi = ServiceGenerator.createService(TradeMeApi.class, null);

        tradeMeApi.getCategory(queryCategory, 1).enqueue(new Callback<Category>() {

            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {

                    if (mSubcategoryBList != null && mSubcategoryBList.size() > 0)
                        mSubcategoryBList.clear();

                    mSubcategoryBList = response.body().getSubcategories();

                    fetchCategoryName(mSubcategoryBNameList, mSubcategoryBList);

                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "Loaded from SecondSubCategoryAPI is complete");

                } else {
                    int statusCode = response.code();

                    if (statusCode == 500)
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_server_issue_toast), Toast.LENGTH_SHORT).show();

                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "Error code: " + statusCode + response.message());

                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                if (t instanceof IOException) {
                    // IOException is because Internet issue
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_internet_issue_toast), Toast.LENGTH_SHORT).show();

                } else {
                    //Other cause which mean Object format wrong or API problem
                    if (BuildConfig.DEBUG) Log.d(TAG, "Error: " + t.getMessage());

                }
            }
        });
    }


    private void loadSubcategoryApi(String queryCategory,
                                    final ArrayList<String> lastNameList,
                                    final ArrayList<String> nameList,
                                    final ArrayList<Subcategory> subcategoryList,
                                    final int index,
                                    final int subcategoryLayerIndex) {

        tradeMeApi = ServiceGenerator.createService(TradeMeApi.class, null);

        tradeMeApi.getCategory(queryCategory, 1).enqueue(new Callback<Category>() {

            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {

                    if (subcategoryList != null && subcategoryList.size() > 0)
                        subcategoryList.clear();

                    switch (subcategoryLayerIndex) {
                        case 2:
                            mSubcategoryCList = response.body().getSubcategories();
                            fetchCategoryName(nameList, mSubcategoryCList);
                            break;
                        case 3:
                            mSubcategoryDList = response.body().getSubcategories();
                            fetchCategoryName(nameList, mSubcategoryDList);
                            break;
                        case 4:
                            mSubcategoryEList = response.body().getSubcategories();
                            fetchCategoryName(nameList, mSubcategoryEList);
                            break;
                        case 5:
                            break;

                    }

                    dynamicallyAddNewTextViewForNewSubcategory(currentLoadedSubcategoryLayer, index, lastNameList);

                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "Loaded from SubCategoryAPI is complete");

                } else {
                    int statusCode = response.code();

                    if (statusCode == 500)
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_server_issue_toast), Toast.LENGTH_SHORT).show();

                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "Error code: " + statusCode + response.message());
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                if (t instanceof IOException) {
                    // IOException is because Internet issue
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_internet_issue_toast), Toast.LENGTH_SHORT).show();

                } else {
                    //Other cause which mean Object format wrong or API problem
                    if (BuildConfig.DEBUG) Log.d(TAG, "Error: " + t.getMessage());

                }
            }
        });

    }

    private void dynamicallyAddNewTextViewForNewSubcategory(int textViewIndex, int listIndex, ArrayList<String> nameList) {

        if (nameList != null && nameList.size() > 0) {
            switch (textViewIndex) {
                case 2:
                    tv_second_condition.setText(nameList.get(listIndex));
                    tv_second_condition.setVisibility(View.VISIBLE);
                    if (mSubcategoryCList != null && mSubcategoryCList.size() > 0) {
                        tv_second_condition.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_right_white_18px, 0);
                    } else {
                        tv_second_condition.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }

                    tv_third_condition.setVisibility(View.GONE);
                    tv_forth_condition.setVisibility(View.GONE);
                    tv_fifth_condition.setVisibility(View.GONE);
                    currentLoadedSubcategoryLayer = currentLoadedSubcategoryLayer + 1;
                    break;
                case 3:
                    tv_third_condition.setText(nameList.get(listIndex));
                    tv_third_condition.setVisibility(View.VISIBLE);
                    if (mSubcategoryDList != null && mSubcategoryDList.size() > 0) {
                        tv_third_condition.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_right_white_18px, 0);
                    } else {
                        tv_third_condition.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }

                    tv_forth_condition.setVisibility(View.GONE);
                    tv_fifth_condition.setVisibility(View.GONE);
                    currentLoadedSubcategoryLayer = currentLoadedSubcategoryLayer + 1;
                    break;
                case 4:
                    tv_forth_condition.setText(nameList.get(listIndex));
                    tv_forth_condition.setVisibility(View.VISIBLE);
                    if (mSubcategoryEList != null && mSubcategoryEList.size() > 0) {
                        tv_forth_condition.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_right_white_18px, 0);
                    } else {
                        tv_forth_condition.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }

                    tv_fifth_condition.setVisibility(View.GONE);
                    currentLoadedSubcategoryLayer = currentLoadedSubcategoryLayer + 1;
                    break;
            }

        }
    }


    private void fetchCategoryName(ArrayList<String> nameList, ArrayList<Subcategory> subcategoryList) {

        nameList.clear();

        if (subcategoryList != null && subcategoryList.size() > 0) {
            for (int i = 0; i < subcategoryList.size(); i++) {
                nameList.add(subcategoryList.get(i).getName());
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mSubcategoryBList != null && mSubcategoryBList.size() > 0)
            outState.putParcelableArrayList(BUNDLE_SUBCATEGORYB_LIST, mSubcategoryBList);

        if (tv_second_condition.getVisibility() == View.VISIBLE || mSubcategoryCList != null && mSubcategoryCList.size() > 0) {
            outState.putBoolean(BUNDLE_SECONDTEXTVIEW_VISIBILITY, true);
            outState.putString(BUNDLE_SECONDTEXTVIEW_TEXT, tv_second_condition.getText().toString());
            outState.putParcelableArrayList(BUNDLE_SUBCATEGORYC_LIST, mSubcategoryCList);
        }

        if (tv_third_condition.getVisibility() == View.VISIBLE || mSubcategoryDList != null && mSubcategoryDList.size() > 0) {
            outState.putBoolean(BUNDLE_THIRDTEXTVIEW_VISIBILITY, true);
            outState.putString(BUNDLE_THIRDTEXTVIEW_TEXT, tv_third_condition.getText().toString());
            outState.putParcelableArrayList(BUNDLE_SUBCATEGORYD_LIST, mSubcategoryDList);
        }

        if (tv_forth_condition.getVisibility() == View.VISIBLE || mSubcategoryEList != null && mSubcategoryEList.size() > 0) {
            outState.putBoolean(BUNDLE_FORTHTEXTVIEW_VISIBILITY, true);
            outState.putString(BUNDLE_FORTHTEXTVIEW_TEXT, tv_forth_condition.getText().toString());
            outState.putParcelableArrayList(BUNDLE_SUBCATEGORYE_LIST, mSubcategoryEList);
        }

        if (tv_fifth_condition.getVisibility() == View.VISIBLE) {
            outState.putBoolean(BUNDLE_FIFTHTEXTVIEW_VISIBILITY, true);
            outState.putString(BUNDLE_FIFTHTEXTVIEW_TEXT, tv_fifth_condition.getText().toString());
        }

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.getParcelableArrayList(BUNDLE_SUBCATEGORYB_LIST) != null &&
                    savedInstanceState.getParcelableArrayList(BUNDLE_SUBCATEGORYB_LIST).size() > 0) {

                mSubcategoryBList = savedInstanceState.getParcelableArrayList(BUNDLE_SUBCATEGORYB_LIST);
                fetchCategoryName(mSubcategoryBNameList, mSubcategoryBList);
            }

            if ((savedInstanceState.getParcelableArrayList(BUNDLE_SUBCATEGORYC_LIST) != null &&
                    savedInstanceState.getParcelableArrayList(BUNDLE_SUBCATEGORYC_LIST).size() > 0) ||
                    savedInstanceState.getBoolean(BUNDLE_SECONDTEXTVIEW_VISIBILITY)) {
                tv_second_condition.setVisibility(View.VISIBLE);
                tv_second_condition.setText(savedInstanceState.getString(BUNDLE_SECONDTEXTVIEW_TEXT));
                mSubcategoryCList = savedInstanceState.getParcelableArrayList(BUNDLE_SUBCATEGORYC_LIST);
                fetchCategoryName(mSubcategoryCNameList, mSubcategoryCList);
            }

            if ((savedInstanceState.getParcelableArrayList(BUNDLE_SUBCATEGORYD_LIST) != null &&
                    savedInstanceState.getParcelableArrayList(BUNDLE_SUBCATEGORYD_LIST).size() > 0) ||
                    savedInstanceState.getBoolean(BUNDLE_THIRDTEXTVIEW_VISIBILITY)) {
                tv_third_condition.setVisibility(View.VISIBLE);
                tv_third_condition.setText(savedInstanceState.getString(BUNDLE_THIRDTEXTVIEW_TEXT));
                mSubcategoryDList = savedInstanceState.getParcelableArrayList(BUNDLE_SUBCATEGORYD_LIST);
                fetchCategoryName(mSubcategoryDNameList, mSubcategoryDList);
            }

            if ((savedInstanceState.getParcelableArrayList(BUNDLE_SUBCATEGORYE_LIST) != null &&
                    savedInstanceState.getParcelableArrayList(BUNDLE_SUBCATEGORYE_LIST).size() > 0) ||
                    savedInstanceState.getBoolean(BUNDLE_FORTHTEXTVIEW_VISIBILITY)) {
                tv_forth_condition.setVisibility(View.VISIBLE);
                tv_forth_condition.setText(savedInstanceState.getString(BUNDLE_FORTHTEXTVIEW_TEXT));
                mSubcategoryEList = savedInstanceState.getParcelableArrayList(BUNDLE_SUBCATEGORYE_LIST);
                fetchCategoryName(mSubcategoryENameList, mSubcategoryEList);
            }

            if (savedInstanceState.getBoolean(BUNDLE_FIFTHTEXTVIEW_VISIBILITY)) {
                tv_fifth_condition.setVisibility(View.VISIBLE);
                tv_fifth_condition.setText(savedInstanceState.getString(BUNDLE_FIFTHTEXTVIEW_TEXT));
            }

            determineDrawableArrowVisibility();
        }
    }

    /**
     * Specific for configuration change, after restore textView status
     */
    private void determineDrawableArrowVisibility() {
        if (mSubcategoryBList != null && mSubcategoryBList.size() > 0) {
            tv_first_condition.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_right_white_18px, 0);
        } else {
            tv_first_condition.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

        if (mSubcategoryCList != null && mSubcategoryCList.size() > 0) {
            tv_second_condition.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_right_white_18px, 0);
        } else {
            tv_second_condition.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

        if (mSubcategoryDList != null && mSubcategoryDList.size() > 0) {
            tv_third_condition.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_right_white_18px, 0);
        } else {
            tv_third_condition.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

        if (mSubcategoryEList != null && mSubcategoryEList.size() > 0) {
            tv_forth_condition.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_right_white_18px, 0);
        } else {
            tv_forth_condition.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            Resources r = getActivity().getResources();
            int leftPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    left,
                    r.getDisplayMetrics()
            );
            int topPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    top,
                    r.getDisplayMetrics()
            );
            int rightPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    right,
                    r.getDisplayMetrics()
            );
            int bottomPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    bottom,
                    r.getDisplayMetrics()
            );
            p.setMargins(leftPx, topPx, rightPx, bottomPx);
            view.requestLayout();
        }
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(String categoryNumber);
    }

}
