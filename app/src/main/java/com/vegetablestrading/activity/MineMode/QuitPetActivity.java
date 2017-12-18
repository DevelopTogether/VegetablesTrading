package com.vegetablestrading.activity.MineMode;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vegetablestrading.R;
import com.vegetablestrading.activity.BaseActivity;
import com.vegetablestrading.bean.UserInfo;
import com.vegetablestrading.customViews.CustomView;
import com.vegetablestrading.utils.Constant;
import com.vegetablestrading.utils.PublicUtils;
import com.vegetablestrading.utils.SharedPreferencesHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * created by 8级大的狂风
 * created date 2017/11/24 10:21.
 * application
 */


public class QuitPetActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private CustomView mQuitPetUserName;
    private CustomView mQuitPetUserType;
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
    /**
     * 100元
     */
    private TextView mQuitPetDepositToReturnAmountTv;
    private SharedPreferencesHelper sp;
    private CustomView mQuitPetDepositCv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = new SharedPreferencesHelper(this, "QUIT_PET");
        setContentView(R.layout.activity_quit_pet);
        initView();
        messageToView(PublicUtils.userInfo);
        mQuitPetDepositToReturn.setText(getQuitPetDepositToReturnText());

    }

    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);
        mTopTitleTv.setText("退会");
        mQuitPetUserName = (CustomView) findViewById(R.id.quitPet_userName);
        mQuitPetUserType = (CustomView) findViewById(R.id.quitPet_userType);
        mQuitPetUserSum = (CustomView) findViewById(R.id.quitPet_userSum);
        mQuitPetIntegral = (CustomView) findViewById(R.id.quitPet_integral);
        mQuitPetUserSumToReturn = (CustomView) findViewById(R.id.quitPet_userSumToReturn);
        mConfirmQuitPetTv = (TextView) findViewById(R.id.confirm_quitPet_tv);
        mConfirmQuitPetTv.setOnClickListener(this);
        if (sp.getBoolean("QUIT_COMMIT", false)) {
            mConfirmQuitPetTv.setText("处理中");
            mConfirmQuitPetTv.setClickable(false);
            mConfirmQuitPetTv.setBackgroundResource(R.drawable.bt_unpress_selecter);
        } else {
            mConfirmQuitPetTv.setText("申请退会");
            mConfirmQuitPetTv.setClickable(true);
            mConfirmQuitPetTv.setBackgroundResource(R.drawable.bt_pressed_selecter);
        }
        mQuitPetDepositToReturn = (TextView) findViewById(R.id.quitPet_depositToReturn);
        mQuitPetMobile = (CustomView) findViewById(R.id.quitPet_mobile);
        mRefundTypeRg = (RadioGroup) findViewById(R.id.refund_type_rg);
        mRefundAccountNameTv = (TextView) findViewById(R.id.refund_account_name_tv);
        mRefundAccountEt = (EditText) findViewById(R.id.refund_account_et);
        mRefundTypeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.zhifubao_rb) {
                    mRefundAccountNameTv.setText("支付宝账号");
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.weixin_rb) {
                    mRefundAccountNameTv.setText("微信账号");
                }
            }
        });
        mQuitPetDepositToReturnAmountTv = (TextView) findViewById(R.id.quitPet_depositToReturnAmount_tv);
        mQuitPetDepositCv = (CustomView) findViewById(R.id.quitPet_deposit_cv);
    }

    /**
     * 将用户信息填入控件中
     */
    private void messageToView(UserInfo userInfo) {
        String residualIntegral = userInfo.getResidualIntegral();
        mQuitPetUserName.getTitleBarRightBtn().setText(userInfo.getUserName());
        mQuitPetUserSum.getTitleBarRightBtn().setText(userInfo.getDues() + " 元");
        mQuitPetIntegral.getTitleBarRightBtn().setText(residualIntegral);
        mQuitPetMobile.getTitleBarRightBtn().setText(userInfo.getUserPhone());
        mQuitPetDepositCv.getTitleBarRightBtn().setText(userInfo.getDeposit()+ " 元");
        mQuitPetDepositToReturn.setText(getQuitPetDepositToReturnText());
        switch (userInfo.getUserType()) {//1==N蓝卡，2==P银卡，3==VIP金卡
            case "3":
                mQuitPetUserType.getTitleBarRightBtn().setText("VIP金卡");
                break;
            case "2":
                mQuitPetUserType.getTitleBarRightBtn().setText("P银卡");
                break;
            case "1":
                mQuitPetUserType.getTitleBarRightBtn().setText("N蓝卡");
                break;
            default:
                break;
        }
        if (!TextUtils.isEmpty(residualIntegral)) {
            mQuitPetUserSumToReturn.getTitleBarRightBtn().setText((Integer.parseInt(residualIntegral)) * 100 + " 元");
        }

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
                String mRefundAccount = mRefundAccountEt.getText().toString().trim();
                if (TextUtils.isEmpty(mRefundAccount)) {
                    if (getAccountOfQuitPet().equals("1")) {
                        Toast.makeText(getApplicationContext(), "请填写微信账号", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "请填写支付宝账号", Toast.LENGTH_LONG).show();
                    }
                    return;
                }

                transportRecordsByDate(mRefundAccount);
                break;
        }
    }

    /**
     * 1微信 2支付宝
     * 获取退会收款方式
     */
    private String getAccountOfQuitPet() {
        switch (mRefundTypeRg.getCheckedRadioButtonId()) {
            case R.id.zhifubao_rb:
                return "2";
            case R.id.weixin_rb:
                return "1";
            default:
                return "2";
        }
    }

    /**
     * 根据起始时间获取配送记录
     */
    private void transportRecordsByDate(String mRefundAccount) {
        OkHttpUtils
                .post()
                .url(Constant.applyforRefund_url)
                .addParams("userId", PublicUtils.userInfo.getUserId())
                .addParams("refundAccountName", getAccountOfQuitPet())
                .addParams("refundAccount", mRefundAccount)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(QuitPetActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                String result = obj.getString("Result");
                                String message = obj.getString("Message");
                                if ("Ok".equals(result)) {
                                    Toast.makeText(getApplicationContext(), "已成功提交申请", Toast.LENGTH_LONG).show();
                                    sp.putBoolean("QUIT_COMMIT", true);
                                    mConfirmQuitPetTv.setText("处理中");
                                    mConfirmQuitPetTv.setClickable(false);
                                    mConfirmQuitPetTv.setBackgroundResource(R.drawable.bt_unpress_selecter);
                                } else {
                                    Toast.makeText(QuitPetActivity.this, message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                });
    }

}
