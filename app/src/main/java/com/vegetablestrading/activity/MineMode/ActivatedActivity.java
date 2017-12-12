package com.vegetablestrading.activity.MineMode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.vegetablestrading.R;
import com.vegetablestrading.bean.UserInfo;
import com.vegetablestrading.customViews.CustomView;
import com.vegetablestrading.utils.PublicUtils;

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
        mStartUseTv = (TextView) findViewById(R.id.start_use_tv);
        mStartUseTv.setOnClickListener(this);
        setTextOfViews(PublicUtils.userInfo);
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
        switch (userInfo.getUserType()) {//1==N蓝卡，2==P银卡，3==VIP金卡
            case "3":
                mActivatedUserType.getTitleBarRightBtn().setText("VIP金卡");
                break;
            case "2":
                mActivatedUserType.getTitleBarRightBtn().setText("P银卡");
                break;
            case "1":
                mActivatedUserType.getTitleBarRightBtn().setText("N蓝卡");
                break;
            default:
                break;
        }
        mActivatedUserSum.getTitleBarRightBtn().setText(userInfo.getDues()+"/年");
        mActivatedDeposit.getTitleBarRightBtn().setText(userInfo.getDeposit());
        mActivatedRegistDate.getTitleBarRightBtn().setText(userInfo.getRegistDate());
    }
}
