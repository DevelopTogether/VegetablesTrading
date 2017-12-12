package com.vegetablestrading.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.vegetablestrading.R;
import com.vegetablestrading.adapter.ShopListAdapter;
import com.vegetablestrading.customViews.SearchView;
import com.vegetablestrading.interfaces.FinishActivityInterface;
import com.vegetablestrading.utils.DaoUtils;

/**
 * Created by ${王sir} on 2017/10/18.
 * application
 */

public class FragmentTab33 extends Fragment {
    private View view;
    private WebView mShopVegetableInfoWv;
    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private ImageView mTopRightImageIv;
    /**
     * 价格最低
     */
    private TextView mPricePriorityTv;
    private LinearLayout mPricePriorityLl;
    /**
     * 销量优先
     */
    private TextView mSalesPriorityTv;
    private RecyclerView mShopVegetablesRv;
    private LinearLayout mDefaultTab1Ll;
    private Context mContext;
    private boolean salesClicked = false;//销量优先被点击了
    private PopupWindow popupWindow;
    private PopupWindow popupWindow_types;
    private SearchView mShopSearchSv;
    private ShopListAdapter adapter;
    private DaoUtils daoUtil;
    private SwipeRefreshLayout mSwipeRefreshSl;
    private FinishActivityInterface finishActivityInterface;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab22, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTopLeftImageIv = (ImageView) view.findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setVisibility(View.INVISIBLE);
        mTopTitleTv = (TextView) view.findViewById(R.id.top_title_tv);
        mTopTitleTv.setText("购物车");
        mTopRightImageIv = (ImageView) view.findViewById(R.id.top_right_image_iv);
    }
    public void setFinishActivity(FinishActivityInterface finishActivityInterface) {
        this.finishActivityInterface = finishActivityInterface;
    }
}
