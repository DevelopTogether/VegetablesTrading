package com.vegetablestrading.activity.MineMode;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vegetablestrading.R;
import com.vegetablestrading.activity.BaseActivity;
import com.vegetablestrading.bean.UserInfo;
import com.vegetablestrading.customViews.CustomView;
import com.vegetablestrading.utils.DaoUtils;
import com.vegetablestrading.utils.PublicUtils;


public class MyInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private ImageView mTopRightImageIv;
    private CustomView mUserName;
    private CustomView mUserType;
    private CustomView mUserStatus;
    private CustomView mUserSum;
    private CustomView mDeposit;
    private CustomView mIntegral;
    private CustomView mPickAmount;
    private CustomView mBoxNo;
    private CustomView mRegistDate;
    private CustomView mMineInfoUserPhone;
    private CustomView mMineInfoUserEmail;
    private DaoUtils daoUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        daoUtils = new DaoUtils(this,"");
        initView();
        setTextOfViews(PublicUtils.userInfo);
        initActionBar();

    }

    /**
     * 初始化actionBar
     */
    private void initActionBar() {
        mTopTitleTv.setText("我的信息");
    }

    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);
        mTopRightImageIv = (ImageView) findViewById(R.id.top_right_image_iv);
        mUserName = (CustomView) findViewById(R.id.mineInfo_userName);
        mUserType = (CustomView) findViewById(R.id.mineInfo_userType);
        mUserStatus = (CustomView) findViewById(R.id.mineInfo_userStatus);
        mUserSum = (CustomView) findViewById(R.id.mineInfo_userSum);
        mDeposit = (CustomView) findViewById(R.id.mineInfo_deposit);
        mIntegral = (CustomView) findViewById(R.id.mineInfo_integral);
        mPickAmount = (CustomView) findViewById(R.id.mineInfo_PickAmount);
        mBoxNo = (CustomView) findViewById(R.id.mineInfo_boxNo);
        mRegistDate = (CustomView) findViewById(R.id.mineInfo_regist_date);
        mMineInfoUserPhone = (CustomView) findViewById(R.id.mineInfo_userPhone);
        mMineInfoUserEmail = (CustomView) findViewById(R.id.mineInfo_userEmail);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_image_iv:
                finish();
                break;
        }
    }

    /**
     * 将我的信息填入
     */
    private void setTextOfViews(UserInfo userInfo) {
        mUserName.getTitleBarRightBtn().setText(userInfo.getUserName());
        mMineInfoUserPhone.getTitleBarRightBtn().setText(userInfo.getUserPhone());
        mMineInfoUserEmail.getTitleBarRightBtn().setText(userInfo.getUserEmail());

        mUserSum.getTitleBarRightBtn().setText(userInfo.getDues()+" 元");
        mDeposit.getTitleBarRightBtn().setText(userInfo.getDeposit()+" 元");
        mIntegral.getTitleBarRightBtn().setText(userInfo.getResidualIntegral()+"分");
        mPickAmount.getTitleBarRightBtn().setText(userInfo.getResidualPickAmount()+"次");
        mBoxNo.getTitleBarRightBtn().setText(userInfo.getBoxNo());
        mRegistDate.getTitleBarRightBtn().setText(userInfo.getRegistDate());
        switch (userInfo.getUserStatus()) {
            case "0":
                mUserStatus.getTitleBarRightBtn().setText("未激活");
                break;
            case "1":
                mUserStatus.getTitleBarRightBtn().setText("已激活");
                break;
            default:
                break;
        }
        switch (userInfo.getUserType()) {//1==N蓝卡，2==P银卡，3==VIP金卡
            case "3":
                mUserType.getTitleBarRightBtn().setText("VIP金卡");
                break;
            case "2":
                mUserType.getTitleBarRightBtn().setText("P银卡");
                break;
            case "1":
                mUserType.getTitleBarRightBtn().setText("N蓝卡");
                break;
            default:
                break;
        }
    }

//    /**
//     * 将申请记录信息保存本地
//     */
//    private void putMyInfoToSqlite(UserInfo userInfo){
//        daoUtils.insertEntity(userInfo);
//
//    }
}
