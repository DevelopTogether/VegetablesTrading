package com.vegetablestrading.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.choseaddrdemo.selectAddr.ChooserActivity;
import com.vegetablestrading.R;
import com.vegetablestrading.activity.MineMode.TermOfServiceActivity;
import com.vegetablestrading.interfaces.EditTestChangedListener;
import com.vegetablestrading.utils.Constant;
import com.vegetablestrading.utils.PublicUtils;
import com.vegetablestrading.utils.SharedPreferencesHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;


public class RegistActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "RegistActivity";
    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private ImageView mTopRightImageIv;
    /**
     * 请输入手机号
     */
    private EditText mUserMobileEt;
    /**
     * 请输入验证码
     */
    private EditText mUserTestEt;
    /**
     * 获取验证码
     */
    private TextView mForTestContentTv;
    /**
     * 请输入6-12位数字加字母组合的密码
     */
    private EditText mUserPwdEt;
    /**
     * 请重复输入密码，两次密码保持一致
     */
    private EditText mUserRePwdEt;
    /**
     * 请输入邮箱
     */
    private EditText mUserEmailEt;
    /**
     * 请输入会员昵称
     */
    private EditText mUserPetNameEt;
    private RadioButton mVipBlueRb;
    private RadioGroup mVipRg;
    private CheckBox mAgreeProvisionCb;
    /**
     * 同意 《账号服务条款，隐私政策》
     */
    private TextView mProvisionInfoTv;
    /**
     * 立即注册
     */
    private TextView mRegistRightnowTv;
    /**
     * 取消注册
     */
    private TextView mCancelRegistTv;
    private String mobile;
    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    mForTestContentTv.setText("重新发送(" + msg.arg1 + ")");
                    break;
                case 0x02:
                    mForTestContentTv.setText("获取验证码");
                    mForTestContentTv.setClickable(true);
                    break;
            }
        }
    };
    private String randomData = "";
    /**
     * 请选择收货地址
     */
    private TextView mSelectAddrTv;
    private LinearLayout mSelectAddrLl;
    /**
     * 街道门牌信息
     */
    private EditText mDetailAddrEt;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private String cityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
        initActionBar();
        registBroadcast();
        sharedPreferencesHelper = new SharedPreferencesHelper(this, "USERINFO");


    }

    private BroadcastReceiver echoRegionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ChooserActivity.ACTION)) {
                String region = intent.getStringExtra(ChooserActivity.ART_ADDRESS);
                cityId = intent.getStringExtra(ChooserActivity.ART_CITYINFO);
                mSelectAddrTv.setText("北京市 " + region);
            }
        }
    };

    /**
     * 注册处理选择地址的广播
     */
    private void registBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ChooserActivity.ACTION);
        registerReceiver(echoRegionReceiver, intentFilter);
    }

    /**
     * 初始化actionBar
     */
    private void initActionBar() {
        mTopTitleTv.setText("注册");
    }

    /**
     * 通过聚合接口获取注册验证码
     */
    private void getTestCodeThroughJuHe(String mobile) {
        randomData = getRandomData();
        OkHttpUtils
                .get()
                .url(Constant.sms_Test_juhe)
                .addParams("mobile", mobile)
                .addParams("tpl_id", Constant.sms_Test_mode_regist)
                .addParams("tpl_value", "#code#=" + randomData)
                .addParams("key", Constant.sms_Test_AppKey)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "短信验证服务器异常，请联系管理员", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        initGetTestCodeButtonStatus();
                    }

                });
    }

    /**
     * 获取随机4位数
     */
    private String getRandomData() {
        String strRand = "";
        for (int i = 0; i < 4; i++) {
            strRand += String.valueOf((int) (Math.random() * 10));
        }
        return strRand;
    }


    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);
        mTopRightImageIv = (ImageView) findViewById(R.id.top_right_image_iv);
        mUserMobileEt = (EditText) findViewById(R.id.user_mobile_et);
        mUserTestEt = (EditText) findViewById(R.id.user_test_et);
        mForTestContentTv = (TextView) findViewById(R.id.forTestContent_tv);
        mForTestContentTv.setOnClickListener(this);
        mUserPwdEt = (EditText) findViewById(R.id.user_pwd_et);
        mUserRePwdEt = (EditText) findViewById(R.id.user_re_pwd_et);
        mUserEmailEt = (EditText) findViewById(R.id.user_email_et);
        mUserPetNameEt = (EditText) findViewById(R.id.user_pet_name_et);
        mVipRg = (RadioGroup) findViewById(R.id.vip_rg);
        mAgreeProvisionCb = (CheckBox) findViewById(R.id.agree_provision_cb);
        mProvisionInfoTv = (TextView) findViewById(R.id.provision_info_tv);
        mProvisionInfoTv.setOnClickListener(this);
        mRegistRightnowTv = (TextView) findViewById(R.id.regist_rightnow_tv);
        mRegistRightnowTv.setOnClickListener(this);
        mCancelRegistTv = (TextView) findViewById(R.id.cancel_regist_tv);
        mCancelRegistTv.setOnClickListener(this);
        mUserMobileEt.addTextChangedListener(new EditTestChangedListener(this, mUserMobileEt, mForTestContentTv, 0));
        mUserTestEt.addTextChangedListener(new EditTestChangedListener(this, mUserTestEt, 1));
        mUserPwdEt.addTextChangedListener(new EditTestChangedListener(this, mUserPwdEt, 2));
        mUserRePwdEt.addTextChangedListener(new EditTestChangedListener(this, mUserRePwdEt, mUserPwdEt, 3));
        mUserEmailEt.addTextChangedListener(new EditTestChangedListener(this, mUserEmailEt, 4));
        mUserPetNameEt.addTextChangedListener(new EditTestChangedListener(this, mUserPetNameEt, 5));

        mSelectAddrTv = (TextView) findViewById(R.id.select_addr_et);
        mSelectAddrLl = (LinearLayout) findViewById(R.id.select_addr_ll);
        mSelectAddrLl.setOnClickListener(this);
        mDetailAddrEt = (EditText) findViewById(R.id.detail_addr_et);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_image_iv://返回按钮
                finish();
                break;
            case R.id.forTestContent_tv://获取验证码
                getTestCode();
                break;
            case R.id.provision_info_tv://服务条款详情
                startActivity(new Intent(this, TermOfServiceActivity.class));
                break;
            case R.id.regist_rightnow_tv://立即注册

                registRightNow();

                break;
            case R.id.cancel_regist_tv://取消注册
                finish();
                break;
            case R.id.select_addr_ll://选择收货地址
                ChooserActivity.start(RegistActivity.this, null);
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getTestCode() {

        mobile = mUserMobileEt.getText().toString().trim();

        if (!PublicUtils.isMobileNO(mobile)) {
            Toast.makeText(getApplicationContext(), "请填写正确格式的手机号", Toast.LENGTH_LONG).show();
            return;
        }
        if (!PublicUtils.isConnected(this)) {
            Toast.makeText(getApplicationContext(), "无网络，稍后重试", Toast.LENGTH_LONG).show();
            return;
        }
        getTestCodeThroughJuHe(mobile);

    }

    /**
     * 同步获取验证码按钮的状态
     */
    private void initGetTestCodeButtonStatus() {
        mForTestContentTv.setClickable(false);
        //开启线程去更新button的text
        new Thread() {
            @Override
            public void run() {
                int totalTime = 600;
                for (int i = 0; i < totalTime; i++) {
                    Message message = myHandler.obtainMessage(0x01);
                    message.arg1 = totalTime - i;
                    myHandler.sendMessage(message);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                myHandler.sendEmptyMessage(0x02);
            }
        }.start();
    }

    /**
     * 注册会员
     */
    private void registRightNow() {
        mobile = mUserMobileEt.getText().toString().trim();
        String testCode = mUserTestEt.getText().toString().trim();
        String pwd = mUserPwdEt.getText().toString().trim();
        String repwd = mUserRePwdEt.getText().toString().trim();
        String email = mUserEmailEt.getText().toString().trim();
        String petName = mUserPetNameEt.getText().toString().trim();
        String addr = mSelectAddrTv.getText().toString().trim();
        String addr_detail = mDetailAddrEt.getText().toString().trim();


        if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(getApplicationContext(), "请填写手机号", Toast.LENGTH_LONG).show();
            return;
        } else {
            if (!PublicUtils.isMobileNO(mobile)) {
                Toast.makeText(getApplicationContext(), "请填写正规的手机号", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (testCode == null || TextUtils.isEmpty(testCode)) {
            Toast.makeText(getApplicationContext(), "请填写验证码", Toast.LENGTH_LONG).show();
            return;
        } else {
            if (!randomData.equals(testCode)) {
                Toast.makeText(getApplicationContext(), "验证码输入有误", Toast.LENGTH_LONG).show();
                return;
            }


        }
        if (pwd == null || TextUtils.isEmpty(pwd)) {
            Toast.makeText(getApplicationContext(), "请设置密码", Toast.LENGTH_LONG).show();
            return;
        } else {
            if (!PublicUtils.isContainAll(pwd)) {
                Toast.makeText(getApplicationContext(), "请按规定格式填写密码以保证账号安全", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (repwd == null || TextUtils.isEmpty(repwd)) {
            Toast.makeText(getApplicationContext(), "请重新输入密码", Toast.LENGTH_LONG).show();
            return;
        } else {
            if (!repwd.equals(pwd)) {
                Toast.makeText(getApplicationContext(), "两次输入密码不相同，请重新输入", Toast.LENGTH_LONG).show();
                return;
            }
        }


        if (email == null || TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "请输入邮箱地址", Toast.LENGTH_LONG).show();
            return;
        } else {
            if (!PublicUtils.isEmail(email)) {
                Toast.makeText(getApplicationContext(), "邮箱格式不正确，请重新输入", Toast.LENGTH_LONG).show();
                return;
            }
        }

        if (petName == null || TextUtils.isEmpty(petName)) {
            Toast.makeText(getApplicationContext(), "请输入会员昵称", Toast.LENGTH_LONG).show();
            return;
        }
        if (addr == null || TextUtils.isEmpty(addr)) {
            Toast.makeText(getApplicationContext(), "请选择收货地址", Toast.LENGTH_LONG).show();
            return;
        }
        if (addr_detail == null || TextUtils.isEmpty(addr_detail)) {
            Toast.makeText(getApplicationContext(), "请填写街道门牌信息", Toast.LENGTH_LONG).show();
            return;
        }
        if (!mAgreeProvisionCb.isChecked()) {
            Toast.makeText(this, "请选择是否同意《账号服务条款，隐私政策》", Toast.LENGTH_SHORT).show();
            return;
        }
        registToService(pwd, email, petName,addr_detail);

        //将手机号保存到sp中
        sharedPreferencesHelper.putString("USER_MOBILE", mobile);
        Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, LoginActivity.class));


    }

    /**
     * @param pwd
     * @param email
     * @param petName
     */
    private void registToService(String pwd, String email, String petName,String detailAddr) {
        OkHttpUtils
                .post()
                .url(Constant.regist_url)
                .addParams("mobile", mobile)
                .addParams("password", pwd)
                .addParams("email", email)
                .addParams("userName", petName)
                .addParams("userLevel", getVipType())
                .addParams("address", cityId+","+detailAddr)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                String result = obj.getString("Result");
                                String message = obj.getString("Message");
                                if ("Ok".equals(result)) {
                                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_LONG).show();
                                } else {
                                    if ("账号已存在".equals(message)) {
                                        Toast.makeText(getApplicationContext(), "账号已存在,无需重复注册", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Log.e("DEBUG", response);
                    }

                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(echoRegionReceiver);
    }

    /**
     * 获取vip类型
     */
    private String getVipType() {
        switch (mVipRg.getCheckedRadioButtonId()) {
            case R.id.vip_glod_rb:
                return "3";
            case R.id.vip_silver_rb:
                return "2";
            case R.id.vip_blue_rb:
                return "1";
            default:
                return "3";
        }
    }
}
