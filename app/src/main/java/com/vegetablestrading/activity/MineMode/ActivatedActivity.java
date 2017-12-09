package com.vegetablestrading.activity.MineMode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.vegetablestrading.R;
import com.vegetablestrading.bean.UserInfo;
import com.vegetablestrading.customViews.CustomView;

/**
 * created by 8级大的狂风
 * created date 2017/11/29 16:19.
 * application 已激活  当用户激活完账户后，即支付完成后展示的类
 */
public class ActivatedActivity extends AppCompatActivity implements View.OnClickListener {

    private CustomView mActivatedUserName;
    private CustomView mActivatedUserPhone;
    private CustomView mActivatedUserType;
    private CustomView mActivatedUserSum;
    private CustomView mActivatedDeposit;
    private CustomView mActivatedRegistDate;
    private CustomView mActivatedExpirationTime;
    /**
     * 我知道了
     */
    private TextView mStartUseTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activated);
        initView();
    }

    private void initView() {
        mActivatedUserName = (CustomView) findViewById(R.id.activated_userName);
        mActivatedUserPhone = (CustomView) findViewById(R.id.activated_userPhone);
        mActivatedUserType = (CustomView) findViewById(R.id.activated_userType);
        mActivatedUserSum = (CustomView) findViewById(R.id.activated_userSum);
        mActivatedDeposit = (CustomView) findViewById(R.id.activated_deposit);
        mActivatedRegistDate = (CustomView) findViewById(R.id.activated_regist_date);
        mActivatedExpirationTime = (CustomView) findViewById(R.id.activated_expirationTime);
        mStartUseTv = (TextView) findViewById(R.id.start_use_tv);
        mStartUseTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_use_tv:
                finish();
                break;
        }
    }

    /**
     * 将我的信息填入
     */
    private void setTextOfViews(UserInfo userInfo) {
        mActivatedUserName.getTitleBarRightBtn().setText(userInfo.getUserName());
        mActivatedUserPhone.getTitleBarRightBtn().setText(userInfo.getUserPhone());
        mActivatedUserType.getTitleBarRightBtn().setText(userInfo.getUserType());
        mActivatedUserSum.getTitleBarRightBtn().setText(userInfo.getDues());
        mActivatedDeposit.getTitleBarRightBtn().setText(userInfo.getDeposit());
        mActivatedRegistDate.getTitleBarRightBtn().setText(userInfo.getRegistDate());
        mActivatedExpirationTime.getTitleBarRightBtn().setText(userInfo.getExpirationTime());
    }
}
