package com.vegetablestrading.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.vegetablestrading.R;
import com.vegetablestrading.interfaces.FinishActivityInterface;

/**
 * Created by ${王sir} on 2017/10/18.
 * application
 */

public class FragmentTab3 extends Fragment implements View.OnClickListener {
    private View view;
    private FinishActivityInterface finishActivityInterface;
    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private ImageView mTopRightImageIv;
    private RecyclerView mShoppingTrolleyRv;
    /**
     * 全选
     */
    private RadioButton mSelectAllRb;
    /**
     * 合计：100元
     */
    private TextView mTotalSumTv;
    /**
     * 结算
     */
    private TextView mComfirmToPay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3, container, false);
        initView(view);
        initActionBar();
        return view;
    }
    /**
     * 初始化actionBar
     */
    private void initActionBar() {
        mTopTitleTv.setText("购物车");
        mTopRightImageIv.setImageResource(R.drawable.manager_icon);
        ViewGroup.LayoutParams params =  mTopRightImageIv.getLayoutParams();
        params.width=100;
        params.height=100;
        mTopRightImageIv.setLayoutParams(params);
        mTopLeftImageIv.setVisibility(View.INVISIBLE);
    }
    public void setFinishActivity(FinishActivityInterface finishActivityInterface) {
        this.finishActivityInterface = finishActivityInterface;
    }

    private void initView(View view) {
        mTopLeftImageIv = (ImageView) view.findViewById(R.id.top_left_image_iv);
        mTopTitleTv = (TextView) view.findViewById(R.id.top_title_tv);
        mTopRightImageIv = (ImageView) view.findViewById(R.id.top_right_image_iv);
        mTopRightImageIv.setOnClickListener(this);
        mShoppingTrolleyRv = (RecyclerView) view.findViewById(R.id.shopping_trolley_rv);
        mSelectAllRb = (RadioButton) view.findViewById(R.id.select_all_rb);
        mTotalSumTv = (TextView) view.findViewById(R.id.total_sum_tv);
        mComfirmToPay = (TextView) view.findViewById(R.id.comfirmToPay);
        mComfirmToPay.setOnClickListener(this);
    }


    //销毁Webview
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_right_image_iv:
                break;
            case R.id.comfirmToPay:
                break;
        }
    }
}
