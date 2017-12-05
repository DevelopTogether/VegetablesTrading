package com.vegetablestrading.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.zackratos.ultimatebar.UltimateBar;
import com.vegetablestrading.R;
import com.vegetablestrading.utils.SharedPreferencesHelper;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private CheckBox mRememberPwdCb;
    /**
     * 登 录
     */
    private TextView mLoginConfirmTv;
    /**
     * 忘记密码
     */
    private TextView mForgetPwdTv;
    /**
     * 注册
     */
    private TextView mRegistTv;
    /**
     * 请输入您的手机号
     */
    private EditText mUserMobileEt;
    /**
     * 密码
     */
    private EditText mUserPwdEt;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private SharedPreferences sp;
    private LinearLayout mSavePwdLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initUserNameAndPwd();
        UltimateBar.newImmersionBuilder()
                .applyNav(true)         // 是否应用到导航栏
                .build(this)
                .apply();
    }

    /**
     * 初始化账号密码状态
     */
    private void initUserNameAndPwd() {
        sharedPreferencesHelper = new SharedPreferencesHelper(this,"LOGIN");
        mUserMobileEt.setText(sharedPreferencesHelper.getString("LOGIN_NAME",""));
        mUserPwdEt.setText(sharedPreferencesHelper.getString("LOGIN_PWD",""));
        boolean pwdSaved = sharedPreferencesHelper.getBoolean("REMEMBERPWD",false);
        if (pwdSaved) {
            mRememberPwdCb.setChecked(true);
        } else {
            mRememberPwdCb.setChecked(false);
        }


    }

    private void initView() {
        mRememberPwdCb = (CheckBox) findViewById(R.id.remember_pwd_cb);
        mLoginConfirmTv = (TextView) findViewById(R.id.login_confirm_tv);
        mLoginConfirmTv.setOnClickListener(this);
        mForgetPwdTv = (TextView) findViewById(R.id.forget_pwd_tv);
        mForgetPwdTv.setOnClickListener(this);
        mRegistTv = (TextView) findViewById(R.id.regist_tv);
        mRegistTv.setOnClickListener(this);
        mUserMobileEt = (EditText) findViewById(R.id.user_mobile_et);
        mUserPwdEt = (EditText) findViewById(R.id.user_pwd_et);
        mSavePwdLl = (LinearLayout) findViewById(R.id.save_pwd_ll);
        mSavePwdLl.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    /**
     * 将用户信息和记住密码的状态保存到sp中
     */
    private void putUserInfoToSp() {
        if (mRememberPwdCb.isChecked()) {
            mRememberPwdCb.setChecked(true);
            sharedPreferencesHelper.putBoolean("REMEMBERPWD", true);
            sharedPreferencesHelper.putString("LOGIN_NAME", mUserMobileEt.getText().toString().trim());
            sharedPreferencesHelper.putString("LOGIN_PWD", mUserPwdEt.getText().toString().trim());
        } else {
            mRememberPwdCb.setChecked(false);
            sharedPreferencesHelper.putBoolean("REMEMBERPWD", false);
            sharedPreferencesHelper.putString("LOGIN_NAME", "");
            sharedPreferencesHelper.putString("LOGIN_PWD", "");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_confirm_tv://登录
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.forget_pwd_tv://忘记密码
                startActivity(new Intent(this, ForgetPwdActivity.class));
                break;
            case R.id.regist_tv://注册
                startActivity(new Intent(this, RegistActivity.class));
                break;
            case R.id.save_pwd_ll://记住密码
                if (mRememberPwdCb.isChecked()) {
                    mRememberPwdCb.setChecked(false);
                }else{
                    mRememberPwdCb.setChecked(true);
                }
                putUserInfoToSp();
                break;
        }
    }
}
