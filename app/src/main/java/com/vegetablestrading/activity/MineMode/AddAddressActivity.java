package com.vegetablestrading.activity.MineMode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vegetablestrading.R;

/**
 * created by 8级大的狂风
 * created date 2017/12/26 16:33.
 * application 添加新地址
 */
public class AddAddressActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private ImageView mTopRightImageIv;
    /**
     * 收货人地址，不能使用特殊字符
     */
    private EditText mAccepterNameEt;
    /**
     * 请输入手机号
     */
    private EditText mAccepterMobileEt;
    /**
     * 请选择地区
     */
    private EditText mAccepterAddrEt;
    private LinearLayout mSelectAddrLl;
    /**
     * 街道门牌信息
     */
    private EditText mAccepterDetailAddrEt;
    private CheckBox mSetDefaultAddrCb;
    private LinearLayout mSetDefaultAddrLl;
    /**
     * 保 存
     */
    private TextView mSaveAddrInfoTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initView();
    }

    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);
        mTopRightImageIv = (ImageView) findViewById(R.id.top_right_image_iv);
        mAccepterNameEt = (EditText) findViewById(R.id.accepter_name_et);
        mAccepterMobileEt = (EditText) findViewById(R.id.accepter_mobile_et);
        mAccepterAddrEt = (EditText) findViewById(R.id.accepter_addr_et);
        mSelectAddrLl = (LinearLayout) findViewById(R.id.select_addr_ll);
        mSelectAddrLl.setOnClickListener(this);
        mAccepterDetailAddrEt = (EditText) findViewById(R.id.accepter_detail_addr_et);
        mSetDefaultAddrCb = (CheckBox) findViewById(R.id.set_default_addr_cb);
        mSetDefaultAddrLl = (LinearLayout) findViewById(R.id.set_default_addr_ll);
        mSetDefaultAddrLl.setOnClickListener(this);
        mSaveAddrInfoTv = (TextView) findViewById(R.id.save_addrInfo_tv);
        mSaveAddrInfoTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_image_iv:
                break;
            case R.id.select_addr_ll:
                break;
            case R.id.set_default_addr_ll:
                break;
            case R.id.save_addrInfo_tv:
                break;
        }
    }
}
