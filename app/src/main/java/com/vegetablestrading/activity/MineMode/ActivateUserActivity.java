package com.vegetablestrading.activity.MineMode;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.vegetablestrading.R;
import com.vegetablestrading.aLiPay.PayResult;
import com.vegetablestrading.activity.BaseActivity;
import com.vegetablestrading.bean.UserInfo;
import com.vegetablestrading.customViews.CustomView;
import com.vegetablestrading.utils.Constant;
import com.vegetablestrading.utils.PublicUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.Call;


/**
 * 会员激活类
 */
public class ActivateUserActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private ImageView mTopRightImageIv;
    private CustomView mUserName;
    private CustomView mMobile;
    private CustomView mUserType;
    private CustomView mUserSum;
    private CustomView mDeposit;
    private RadioGroup mYearLimitRg;
    private CustomView mPayAmount;
    private static final int SDK_PAY_FLAG = 1;
    /**
     * 立即激活
     */
    private TextView mActivateRightnowTv;
    private CheckBox mSelectedAliPayCb;
    private RelativeLayout mAliPayRl;
    private CheckBox mSelectedWeixinCb;
    private RelativeLayout mWeixinPayRl;
    /**
     * 确定支付
     */
    private TextView mConfirmPayTv;
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(ActivateUserActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        if (popWindow != null && popWindow.isShowing()) {
                            popWindow.dismiss();
                            popWindow = null;
                        }
                        mActivateRightnowTv.setText("已激活");
                        mActivateRightnowTv.setClickable(false);
                        mActivateRightnowTv.setBackgroundResource(R.drawable.bt_unpress_selecter);

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(ActivateUserActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };
    private PopupWindow popWindow;
    private TextView mPetTypeDescriptionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_user);
        initView();
        setViewValue(PublicUtils.userInfo);
        initActionBar();

    }

    /**
     * 初始化actionBar
     */
    private void initActionBar() {
        mTopTitleTv.setText("会员激活");
    }

    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);
        mTopRightImageIv = (ImageView) findViewById(R.id.top_right_image_iv);
        mUserName = (CustomView) findViewById(R.id.activate_userName);
        mMobile = (CustomView) findViewById(R.id.activate_mobile);
        mUserType = (CustomView) findViewById(R.id.activate_userType);
        mUserSum = (CustomView) findViewById(R.id.activate_userSum);
        mDeposit = (CustomView) findViewById(R.id.activate_deposit);
        mYearLimitRg = (RadioGroup) findViewById(R.id.yearLimit_rg);
        mPayAmount = (CustomView) findViewById(R.id.activate_payAmount);
        mActivateRightnowTv = (TextView) findViewById(R.id.activate_rightnow_tv);
        mActivateRightnowTv.setOnClickListener(this);
        mYearLimitRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                String userSum = mUserSum.getTitleBarRightBtn().getText().toString().trim();
                if (TextUtils.isEmpty(userSum)) {
                    return;
                }
                int payAmount = Integer.parseInt(userSum.split("/")[0]);
                switch (mYearLimitRg.getCheckedRadioButtonId()) {
                    case R.id.limit_one_rb:
                        payAmount = payAmount + 100;
                        mPayAmount.getTitleBarRightBtn().setText(String.valueOf(payAmount));
                        break;
                    case R.id.limit_two_rb:
                        payAmount = (payAmount * 2) + 100;
                        mPayAmount.getTitleBarRightBtn().setText(String.valueOf(payAmount));
                        break;
                    case R.id.limit_three_rb:
                        payAmount = (payAmount * 3) + 100;
                        mPayAmount.getTitleBarRightBtn().setText(String.valueOf(payAmount));
                        break;
                    default:

                        break;
                }
            }
        });

        mPetTypeDescriptionTv = (TextView) findViewById(R.id.petType_description_tv);
    }

    /**
     * 设置会员信息
     */
    private void setViewValue(UserInfo userInfo) {
        mUserName.getTitleBarRightBtn().setText(userInfo.getUserName());
        mMobile.getTitleBarRightBtn().setText(userInfo.getUserPhone());
        mUserType.getTitleBarRightBtn().setText(userInfo.getUserType());
        mUserSum.getTitleBarRightBtn().setText(userInfo.getDues());
        mDeposit.getTitleBarRightBtn().setText(userInfo.getDeposit());
        switch (userInfo.getUserType()) {//1==N蓝卡，2==P银卡，3==VIP金卡
            case "3":
                mPetTypeDescriptionTv.setText(getResources().getText(R.string.glodCard_description));
                break;
            case "2":
                mPetTypeDescriptionTv.setText(getResources().getText(R.string.silverCard_description));
                break;
            case "1":
                mPetTypeDescriptionTv.setText(getResources().getText(R.string.blueCard_description));
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_image_iv:
                finish();
                break;
            case R.id.activate_rightnow_tv:
                selectPayType();
                break;
            case R.id.ali_pay_rl://选择支付宝支付
                changeCheckBoxStatus(true);

                break;
            case R.id.weixin_pay_rl://选择微信支付
                changeCheckBoxStatus(false);
                break;
            case R.id.confirm_pay_tv://开始支付
                if (mSelectedAliPayCb.isChecked()) {//通过支付宝支付
                    getOrderInfoByService(getYearLimit(), "100");
                } else {
                    getMsgForWeixinPay(getYearLimit(), "100");
                }

                break;
        }
    }

    /**
     *获取微信支付需要的数据
     */
    private void getMsgForWeixinPay(String yearLimit, String payAmount) {

        OkHttpUtils
                .post()
                .url(Constant.userActivateThroughWeiXinPay_url)
                .addParams("userId", PublicUtils.userInfo.getUserId())
                .addParams("yearLimit", yearLimit)
                .addParams("userType", PublicUtils.userInfo.getUserType())
                .addParams("payAmount", payAmount)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(ActivateUserActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                String result = obj.getString("Result");
                                String orderInfo = obj.getString("OrderInfo");
                                if ("Ok".equals(result) && !TextUtils.isEmpty(orderInfo)) {
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.e("DEBUG", response);

                    }

                });

    }

    /**
     * 从服务端获取orderInfo(支付宝)
     *
     * @param yearLimit 年限
     * @param payAmount 支付金额
     */
    private void getOrderInfoByService(String yearLimit, String payAmount) {
        OkHttpUtils
                .post()
                .url(Constant.userActivateThroughALiPay_url)
                .addParams("userId", PublicUtils.userInfo.getUserId())
                .addParams("yearLimit", yearLimit)
                .addParams("userType", PublicUtils.userInfo.getUserType())
                .addParams("payAmount", payAmount)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(ActivateUserActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                String result = obj.getString("Result");
                                String orderInfo = obj.getString("OrderInfo");
                                if ("Ok".equals(result) && !TextUtils.isEmpty(orderInfo)) {
                                    startPayForActivateUserThoughAli(orderInfo);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.e("DEBUG", response);

                    }

                });

    }

    /**
     * 更改CheckBox的状态
     */
    private void changeCheckBoxStatus(boolean isAli) {
        if (mSelectedAliPayCb.isChecked()) {
            mSelectedAliPayCb.setChecked(false);
        }
        if (mSelectedWeixinCb.isChecked()) {
            mSelectedWeixinCb.setChecked(false);
        }
        if (isAli) {
            mSelectedAliPayCb.setChecked(true);
        } else {
            mSelectedWeixinCb.setChecked(true);
        }

    }

    /**
     * 选择支付类型
     */
    private void selectPayType() {
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        View popView = View.inflate(this, R.layout.select_pay_type, null);
        mSelectedAliPayCb = (CheckBox) popView.findViewById(R.id.selected_ali_pay_cb);
        mAliPayRl = (RelativeLayout) popView.findViewById(R.id.ali_pay_rl);
        mAliPayRl.setOnClickListener(this);
        mSelectedWeixinCb = (CheckBox) popView.findViewById(R.id.selected_weixin_cb);
        mWeixinPayRl = (RelativeLayout) popView.findViewById(R.id.weixin_pay_rl);
        mWeixinPayRl.setOnClickListener(this);
        mConfirmPayTv = (TextView) popView.findViewById(R.id.confirm_pay_tv);
        mConfirmPayTv.setOnClickListener(this);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        popWindow = new PopupWindow(popView, width, height);
        popWindow.setAnimationStyle(R.style.AnimBottom);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(false);// 设置允许在外点击消失
        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 开始激活，支付宝支付
     */
    private void startPayForActivateUserThoughAli(final String orderInfo) {

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(ActivateUserActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    /**
     * 开始激活，微信支付
     */
    private void startPayForActivateUserThoughWeixin(final String orderInfo) {

    }

    /**
     * 获取会员期限
     */
    private String getYearLimit() {
        switch (mYearLimitRg.getCheckedRadioButtonId()) {
            case R.id.limit_one_rb:
                return "1";
            case R.id.limit_two_rb:
                return "2";
            case R.id.limit_three_rb:
                return "3";
            default:
                return "1";
        }
    }
}
