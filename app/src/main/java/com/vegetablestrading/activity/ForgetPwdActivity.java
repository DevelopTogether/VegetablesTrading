package com.vegetablestrading.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vegetablestrading.R;
import com.vegetablestrading.interfaces.EditTestChangedListener;
import com.vegetablestrading.utils.Constant;
import com.vegetablestrading.utils.PublicUtils;
import com.vegetablestrading.utils.SharedPreferencesHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * created by 8级大的狂风
 * created date 2017/11/27 16:51.
 * application  忘记密码的类
 */
public class ForgetPwdActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    /**
     * 注册时登记的手机号
     */
    private EditText mRegistedMobileEt;
    /**
     * 4位数的验证码
     */
    private EditText mIdentifyingCodeEt;
    /**
     * 获取验证码
     */
    private TextView mGetIdentifyingCodeTv;
    /**
     * 请输入6-12位数字加字母组合的密码
     */
    private EditText mFindNewPwdEt;
    /**
     * 请重复输入密码，两次密码保持一致
     */
    private EditText mFindReNewPwdEt;
    /**
     * 立即重设
     */
    private TextView mConfirmResetTv;
    private String randomData;
    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    mGetIdentifyingCodeTv.setText("重新发送(" + msg.arg1 + ")");
                    break;
                case 0x02:
                    mGetIdentifyingCodeTv.setText("获取验证码");
                    mGetIdentifyingCodeTv.setEnabled(true);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        initView();
    }

    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);
        mTopTitleTv.setText("密码重设");
        mRegistedMobileEt = (EditText) findViewById(R.id.registed_mobile_et);
        mIdentifyingCodeEt = (EditText) findViewById(R.id.identifying_code_et);
        mIdentifyingCodeEt.addTextChangedListener(new EditTestChangedListener(this, mIdentifyingCodeEt, 1));
        mGetIdentifyingCodeTv = (TextView) findViewById(R.id.get_identifying_code_tv);
        mGetIdentifyingCodeTv.setOnClickListener(this);
        mFindNewPwdEt = (EditText) findViewById(R.id.find_new_Pwd_et);
        mFindReNewPwdEt = (EditText) findViewById(R.id.find_re_new_Pwd_et);
        mFindNewPwdEt.addTextChangedListener(new EditTestChangedListener(this, mFindNewPwdEt, 2));
        mFindReNewPwdEt.addTextChangedListener(new EditTestChangedListener(this, mFindReNewPwdEt, mFindNewPwdEt, 3));
        mConfirmResetTv = (TextView) findViewById(R.id.confirm_reset_tv);
        mConfirmResetTv.setOnClickListener(this);
        mRegistedMobileEt.addTextChangedListener(new EditTestChangedListener(this, mRegistedMobileEt, mGetIdentifyingCodeTv, 14));

    }

    @Override
    public void onClick(View v) {
        String mobile = mRegistedMobileEt.getText().toString().trim();
        switch (v.getId()) {
            case R.id.top_left_image_iv:
                finish();
                break;
            case R.id.get_identifying_code_tv://获取验证码按钮点击事件
                if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(getApplicationContext(), "请先输入注册时登记的手机号", Toast.LENGTH_SHORT).show();
                } else {
                    if (!PublicUtils.isMobileNO(mobile)) {
                        Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        String savedMobile = getSavedUserMobile();
                        if (TextUtils.isEmpty(savedMobile)) {
                            Toast.makeText(getApplicationContext(), "您还没有注册账户，请先注册", Toast.LENGTH_SHORT).show();
                        } else {
                            if (!savedMobile.equals(mobile)) {
                                Toast.makeText(getApplicationContext(), "请输入注册时登记的手机号", Toast.LENGTH_SHORT).show();
                            } else {
                                mGetIdentifyingCodeTv.setBackgroundResource(R.drawable.bt_pressed_selecter);
                                //TODO 调用聚合的找回密码的短信接口，获取验证码
                                getTestCode();
                            }
                        }
                    }
                }
                break;
            case R.id.confirm_reset_tv:
                resetPwd(mobile);
                break;
        }
    }
    /**
     * 获取验证码
     */
    private void getTestCode() {

       String mobile = mRegistedMobileEt.getText().toString().trim();
        if (!PublicUtils.isConnected(this)) {
            Toast.makeText(getApplicationContext(), "无网络，稍后重试", Toast.LENGTH_LONG).show();
            return;
        }
        getTestCodeThroughJuHe(mobile);

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
                .addParams("tpl_id", Constant.sms_Test_mode_pwd)
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
     * 同步获取验证码按钮的状态
     */
    private void initGetTestCodeButtonStatus() {
        mGetIdentifyingCodeTv.setEnabled(false);
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
     * 获取随机4位数
     */
    private String getRandomData() {
        String strRand = "";
        for (int i = 0; i < 4; i++) {
            strRand += String.valueOf((int) (Math.random() * 10));
        }
        return strRand;
    }

    /**
     * 重设密码
     * @param mobile
     */
    private void resetPwd(String mobile) {
        String testCode = mIdentifyingCodeEt.getText().toString().trim();
        String pwd = mFindNewPwdEt.getText().toString().trim();
        String repwd = mFindReNewPwdEt.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(getApplicationContext(), "请填写手机号", Toast.LENGTH_LONG).show();
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
        Toast.makeText(getApplicationContext(), "密码更改成功", Toast.LENGTH_LONG).show();
        //TODO 调用重置密码的接口
    }

    /**
     * 获取保存的手机号
     *
     * @return
     */
    private String getSavedUserMobile() {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(this, "USERINFO");
        return sharedPreferencesHelper.getString("USER_MOBILE", "");
    }
}
