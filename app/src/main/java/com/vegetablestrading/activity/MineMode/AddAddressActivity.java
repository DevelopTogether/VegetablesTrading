package com.vegetablestrading.activity.MineMode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.choseaddrdemo.selectAddr.ChooserActivity;
import com.vegetablestrading.R;
import com.vegetablestrading.activity.BaseActivity;
import com.vegetablestrading.interfaces.EditTestChangedListener;
import com.vegetablestrading.utils.Constant;
import com.vegetablestrading.utils.PublicUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * created by 8级大的狂风
 * created date 2017/12/26 16:33.
 * application 添加新地址
 */
public class AddAddressActivity extends BaseActivity implements View.OnClickListener {

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
    private TextView mAccepterAddrTv;
    private LinearLayout mSelectAddrLl;
    /**
     * 街道门牌信息
     */
    private EditText mAccepterDetailAddrEt;
    private CheckBox mSetDefaultAddrCb;
    private LinearLayout mSetDefaultAddrLl;
    private String cityId;
    /**
     * 保 存
     */
    private TextView mSaveAddrInfoTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initView();
        registBroadcast();
    }
    /**
     * 注册处理选择地址的广播
     */
    private void registBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ChooserActivity.ACTION);
        registerReceiver(echoRegionReceiver, intentFilter);
    }
    private BroadcastReceiver echoRegionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ChooserActivity.ACTION)) {
                String region = intent.getStringExtra(ChooserActivity.ART_ADDRESS);
                cityId = intent.getStringExtra(ChooserActivity.ART_CITYINFO);
                mAccepterAddrTv.setText("北京市 " + region);
            }
        }
    };
    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);
        mTopTitleTv.setText("添加新地址");
        mTopRightImageIv = (ImageView) findViewById(R.id.top_right_image_iv);
        mAccepterNameEt = (EditText) findViewById(R.id.accepter_name_et);
        mAccepterNameEt.addTextChangedListener(new EditTestChangedListener(this, mAccepterNameEt, 5));
        mAccepterMobileEt = (EditText) findViewById(R.id.accepter_mobile_et);
        mAccepterMobileEt.addTextChangedListener(new EditTestChangedListener(this, mAccepterMobileEt, 15));
        mAccepterAddrTv = (TextView) findViewById(R.id.accepter_addr_tv);
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
            case R.id.top_left_image_iv://返回按钮
                finish();
                break;
            case R.id.select_addr_ll://选择地区
                ChooserActivity.start(AddAddressActivity.this, null);
                break;
            case R.id.set_default_addr_ll://设为默认收货地址
                if (mSetDefaultAddrCb.isChecked()) {
                    mSetDefaultAddrCb.setChecked(false);
                }else{
                    mSetDefaultAddrCb.setChecked(true);
                }
                break;
            case R.id.save_addrInfo_tv://保存地址信息
                saveAddressInfo();
                break;
        }
    }

    /**
     * 保存地址信息
     */
    private void saveAddressInfo() {
       String name = mAccepterNameEt.getText().toString().trim();
       String mobile = mAccepterMobileEt.getText().toString().trim();
        String addr = mAccepterAddrTv.getText().toString().trim();
        String addr_detail = mAccepterDetailAddrEt.getText().toString().trim();
        String isDefault = getDefaultStatus();
        if (name == null || TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "请输入会员昵称", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(getApplicationContext(), "请填写手机号", Toast.LENGTH_LONG).show();
            return;
        } else {
            if (!PublicUtils.isMobileNO(mobile)) {
                Toast.makeText(getApplicationContext(), "请填写正规的手机号", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (addr == null || TextUtils.isEmpty(addr)) {
            Toast.makeText(getApplicationContext(), "请选择收货地址", Toast.LENGTH_LONG).show();
            return;
        }
        if (addr_detail == null || TextUtils.isEmpty(addr_detail)) {
            Toast.makeText(getApplicationContext(), "请填写详细信息", Toast.LENGTH_LONG).show();
            return;
        }
        //TODO 调用添加地址的接口
        addAddressToService(name,mobile,cityId,isDefault);
    }

    /**
     * 将地址信息添加上
     * @param name
     * @param mobile
     * @param cityId
     * @param isDefault
     */
    private void addAddressToService(String name, String mobile, String cityId, String isDefault) {

        OkHttpUtils
                .post()
                .url(Constant.addAddress_url)
                .addParams("userId", PublicUtils.userInfo.getUserId())
                .addParams("accepterName", name)
                .addParams("accepterPhone", mobile)
                .addParams("address", cityId)
                .addParams("isDefault", isDefault)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(AddAddressActivity.this, "网络错误", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                String result = obj.getString("Result");
                                String message = obj.getString("Message");
                                if ("Ok".equals(result)) {
                                    Toast.makeText(AddAddressActivity.this, "..", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(AddAddressActivity.this, message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                });
    }

    /**
     * 获取默认状态
     * @return
     */
    private String getDefaultStatus() {
        if (mSetDefaultAddrCb.isChecked()) {
           return "1";//设为默认地址
        }else{
            return "2";//不设为默认地址
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(echoRegionReceiver);
    }
}
