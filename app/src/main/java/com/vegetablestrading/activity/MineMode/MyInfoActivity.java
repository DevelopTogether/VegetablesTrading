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
    private CustomView mMineInfoExpirationTime;
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
        mMineInfoExpirationTime = (CustomView) findViewById(R.id.mineInfo_expirationTime);
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
        mUserType.getTitleBarRightBtn().setText(userInfo.getUserType());
        mUserStatus.getTitleBarRightBtn().setText(userInfo.getUserStatus());
        mUserSum.getTitleBarRightBtn().setText(userInfo.getDues());
        mDeposit.getTitleBarRightBtn().setText(userInfo.getDeposit());
        mIntegral.getTitleBarRightBtn().setText(userInfo.getResidualIntegral());
        mPickAmount.getTitleBarRightBtn().setText(userInfo.getResidualPickAmount());
        mBoxNo.getTitleBarRightBtn().setText(userInfo.getBoxNo());
        mRegistDate.getTitleBarRightBtn().setText(userInfo.getRegistDate());
        mMineInfoExpirationTime.getTitleBarRightBtn().setText(userInfo.getExpirationTime());
    }

//    /**
//     * 将申请记录信息保存本地
//     */
//    private void putMyInfoToSqlite(UserInfo userInfo){
//        daoUtils.insertEntity(userInfo);
//
//    }
}
