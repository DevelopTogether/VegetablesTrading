package com.vegetablestrading.activity.MineMode;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.vegetablestrading.R;
import com.vegetablestrading.bean.UserInfo;
import com.vegetablestrading.customViews.CustomView;

/**
 * created by 8级大的狂风
 * created date 2017/11/24 10:21.
 * application
 */


public class QuitPetActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private CustomView mQuitPetUserName;
    private CustomView mQuitPetUserType;
    private CustomView expirationTimeCv;
    private CustomView mQuitPetUserSum;
    private CustomView mQuitPetIntegral;
    private CustomView mQuitPetUserSumToReturn;
    /**
     * 提交投诉
     */
    private TextView mConfirmQuitPetTv;
    /**
     * 押金
     */
    private TextView mQuitPetDepositToReturn;
    private CustomView mQuitPetMobile;
    private RadioGroup mRefundTypeRg;
    /**
     * 支付宝账号
     */
    private TextView mRefundAccountNameTv;
    /**
     * 请输入退款账户
     */
    private EditText mRefundAccountEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quit_pet);
        initView();
        mQuitPetDepositToReturn.setText(getQuitPetDepositToReturnText());
    }

    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);
        mTopTitleTv.setText("退会");
        mQuitPetUserName = (CustomView) findViewById(R.id.quitPet_userName);
        mQuitPetUserType = (CustomView) findViewById(R.id.quitPet_userType);
        expirationTimeCv = (CustomView) findViewById(R.id.expirationTime_cv);
        mQuitPetUserSum = (CustomView) findViewById(R.id.quitPet_userSum);
        mQuitPetIntegral = (CustomView) findViewById(R.id.quitPet_integral);
        mQuitPetUserSumToReturn = (CustomView) findViewById(R.id.quitPet_userSumToReturn);
        mConfirmQuitPetTv = (TextView) findViewById(R.id.confirm_quitPet_tv);
        mConfirmQuitPetTv.setOnClickListener(this);
        mQuitPetDepositToReturn = (TextView) findViewById(R.id.quitPet_depositToReturn);
        mQuitPetMobile = (CustomView) findViewById(R.id.quitPet_mobile);
        mRefundTypeRg = (RadioGroup) findViewById(R.id.refund_type_rg);
        mRefundAccountNameTv = (TextView) findViewById(R.id.refund_account_name_tv);
        mRefundAccountEt = (EditText) findViewById(R.id.refund_account_et);
        mRefundTypeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (radioGroup.getCheckedRadioButtonId()==R.id.zhifubao_rb) {
                    mRefundAccountNameTv.setText("支付宝账号");
                }else if (radioGroup.getCheckedRadioButtonId()==R.id.weixin_rb) {
                    mRefundAccountNameTv.setText("微信账号");
                }
            }
        });
    }

    /**
     * 将用户信息填入控件中
     */
    private void messageToView(UserInfo userInfo) {

        mQuitPetUserName.getTitleBarRightBtn().setText(userInfo.getUserName());
        mQuitPetUserType.getTitleBarRightBtn().setText(userInfo.getPetType());
        expirationTimeCv.getTitleBarRightBtn().setText(userInfo.getExpirationTime());
        mQuitPetUserSum.getTitleBarRightBtn().setText(userInfo.getPetSum());
        mQuitPetIntegral.getTitleBarRightBtn().setText(userInfo.getResidualIntegral());
        mQuitPetMobile.getTitleBarRightBtn().setText(userInfo.getUserPhone());
        mQuitPetDepositToReturn.setText(getQuitPetDepositToReturnText());
    }

    /**
     * 获取应退押金的标题
     */
    private SpannableString getQuitPetDepositToReturnText() {
        SpannableString spanString = new SpannableString("押金（*押金需要确认中转箱没问题后才可退还*）");
        ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
        spanString.setSpan(span, 2, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        AbsoluteSizeSpan spanSize = new AbsoluteSizeSpan(12, true);
        spanString.setSpan(spanSize, 2, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanString;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_image_iv:
                finish();
                break;
            case R.id.confirm_quitPet_tv:
                break;
        }
    }
    /**
     * 获取退会收款方式
     */
    private String getAccountOfQuitPet() {
        switch (mRefundTypeRg.getCheckedRadioButtonId()) {
            case R.id.zhifubao_rb:
                return "支付宝";
            case R.id.weixin_rb:
                return "微信";
            default:
                return "VIP金卡";
        }
    }
}
