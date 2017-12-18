package com.vegetablestrading.activity.MineMode;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vegetablestrading.R;
import com.vegetablestrading.activity.BaseActivity;
import com.vegetablestrading.utils.Constant;

/**
 * Author:wang_sir
 * Time:${DATE} ${TIME}
 * Description:This is 客户投诉
 */

public class CustomerComplainActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    /**
     * 官方投诉电话:      400-888-888
     */
    private TextView mComplainMobileTv;
    /**
     * 投诉标题
     */
    private TextView mComplainTitleTv;
    /**
     * 输入投诉的标题
     */
    private EditText mComplainTitleEt;
    /**
     * 质量问题
     */
    private RadioButton mQualityProblemRv;
    /**
     * 售后问题
     */
    private RadioButton mAfterSaleProblemRv;
    /**
     * 配送问题
     */
    private RadioButton mTransportProblemRv;
    private RadioGroup mComplainTypeRg;
    /**
     * 至少输入5个字，最多50个字
     */
    private EditText mComplainInfoEt;
    /**
     * 提交投诉
     */
    private TextView mConfirmComplainTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_complain);
        initView();
    }

    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(R.id.top_left_image_iv);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);
        mTopTitleTv.setText("我的投诉");
        mComplainMobileTv = (TextView) findViewById(R.id.complain_mobile_tv);
        mComplainMobileTv.setText(Constant.complain_No);
        mComplainTitleTv = (TextView) findViewById(R.id.complain_title_tv);
        mComplainTitleEt = (EditText) findViewById(R.id.complain_title_et);
        mQualityProblemRv = (RadioButton) findViewById(R.id.quality_problem_rv);
        mAfterSaleProblemRv = (RadioButton) findViewById(R.id.after_sale_problem_rv);
        mTransportProblemRv = (RadioButton) findViewById(R.id.transport_problem_rv);
        mComplainTypeRg = (RadioGroup) findViewById(R.id.complain_type_rg);
        mComplainInfoEt = (EditText) findViewById(R.id.complain_info_et);
        mConfirmComplainTv = (TextView) findViewById(R.id.confirm_complain_tv);
        mConfirmComplainTv.setOnClickListener(this);
        mTopLeftImageIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_image_iv:
                finish();
                break;
            case R.id.confirm_complain_tv:
               submitComplainInfo();
                break;
        }
    }

    /**
     * 提交投诉内容
     */
    private void submitComplainInfo() {
        String complainTitle = mComplainTitleEt.getText().toString().trim();
        String problemType = getComplainType();
        String complainDescription = mComplainInfoEt.getText().toString().trim();
        if (TextUtils.isEmpty(complainTitle)) {
            Toast.makeText(getApplicationContext(), "请输入投诉标题", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(complainDescription)) {
            Toast.makeText(getApplicationContext(), "请输入投诉详情", Toast.LENGTH_LONG).show();
            return;
        }
        //TOdo 提交投诉的接口

    }

    /**
     * 获取投诉类型
     */
    private String getComplainType() {
        switch (mComplainTypeRg.getCheckedRadioButtonId()) {
            case R.id.quality_problem_rv:
                return "质量问题";
            case R.id.after_sale_problem_rv:
                return "售后问题";
            case R.id.transport_problem_rv:
                return "配送问题";
            default:
                return "质量问题";
        }
    }
}
