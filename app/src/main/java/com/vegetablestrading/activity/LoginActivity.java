package com.vegetablestrading.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.zackratos.ultimatebar.UltimateBar;
import com.google.gson.Gson;
import com.vegetablestrading.R;
import com.vegetablestrading.bean.UserInfo;
import com.vegetablestrading.utils.Constant;
import com.vegetablestrading.utils.PublicUtils;
import com.vegetablestrading.utils.SharedPreferencesHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private CheckBox mRememberPwdCb;
    /**
     * 登 录
     */
    private TextView mLoginConfirmTv;
//    /**
//     * 忘记密码
//     */
//    private TextView mForgetPwdTv;
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
    private ProgressBar mLoginPb;
    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mGson = new Gson();
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
        sharedPreferencesHelper = new SharedPreferencesHelper(this, "LOGIN");
        mUserMobileEt.setText(sharedPreferencesHelper.getString("LOGIN_NAME", ""));
        mUserPwdEt.setText(sharedPreferencesHelper.getString("LOGIN_PWD", ""));
        boolean pwdSaved = sharedPreferencesHelper.getBoolean("REMEMBERPWD", false);
        if (pwdSaved) {
            mRememberPwdCb.setChecked(true);
             String mobile = mUserMobileEt.getText().toString().trim();
            String pwd = mUserPwdEt.getText().toString().trim();
            loginToService(mobile, pwd);
        } else {
            mRememberPwdCb.setChecked(false);
        }


    }

    private void initView() {
        mRememberPwdCb = (CheckBox) findViewById(R.id.remember_pwd_cb);
        mLoginConfirmTv = (TextView) findViewById(R.id.login_confirm_tv);
        mLoginConfirmTv.setOnClickListener(this);
//        mForgetPwdTv = (TextView) findViewById(R.id.forget_pwd_tv);
//        mForgetPwdTv.setOnClickListener(this);
        mRegistTv = (TextView) findViewById(R.id.regist_tv);
        mRegistTv.setOnClickListener(this);
        mUserMobileEt = (EditText) findViewById(R.id.user_mobile_et);
        mUserPwdEt = (EditText) findViewById(R.id.user_pwd_et);
        mSavePwdLl = (LinearLayout) findViewById(R.id.save_pwd_ll);
        mSavePwdLl.setOnClickListener(this);
        mLoginPb = (ProgressBar) findViewById(R.id.login_pb);
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
                String mobile = mUserMobileEt.getText().toString().trim();
                String pwd = mUserPwdEt.getText().toString().trim();

                if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(getApplicationContext(), "请填写手机号", Toast.LENGTH_LONG).show();
                } else {
                    if (!PublicUtils.isMobileNO(mobile)) {
                        Toast.makeText(getApplicationContext(), "请填写正规的手机号", Toast.LENGTH_LONG).show();
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
                putUserInfoToSp();
                loginToService(mobile, pwd);
                break;
//            case R.id.forget_pwd_tv://忘记密码
//                startActivity(new Intent(this, ForgetPwdActivity.class));
//                break;
            case R.id.regist_tv://注册
                startActivity(new Intent(this, RegistActivity.class));
                break;
            case R.id.save_pwd_ll://记住密码
                if (mRememberPwdCb.isChecked()) {
                    mRememberPwdCb.setChecked(false);
                } else {
                    mRememberPwdCb.setChecked(true);
                }
                putUserInfoToSp();
                break;
        }
    }

    private void loginToService(String userName, String pwd) {
        if (!PublicUtils.isConnected(this)) {
            Toast.makeText(getApplicationContext(), "无网络，请稍后再试", Toast.LENGTH_LONG).show();
            return;
        }
        initRefreshStatus(false);
        OkHttpUtils
                .post()
                .url(Constant.login_url)
                .addParams("mobile", userName)
                .addParams("password", pwd)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        initRefreshStatus(true);

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
                                    String object = obj.getString("Model");
                                    PublicUtils.userInfo = mGson.fromJson(object, UserInfo.class);
                                    String userStatus = PublicUtils.userInfo.getUserStatus();
                                    switch (userStatus) {
                                        case "0":
                                            PublicUtils.ACTIVATED = false;
                                            break;
                                        case "1":
                                            PublicUtils.ACTIVATED = true;
                                            break;
                                        default:
                                            break;
                                    }
                                    initRefreshStatus(true);
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                } else {
                                    initRefreshStatus(true);
                                    if ("账号不存在!".equals(message)) {
                                        Toast.makeText(getApplicationContext(), "账号不存在", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "密码错误,请重新输入", Toast.LENGTH_LONG).show();

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

    /**
     * 初始化更新时的状态
     * @param b
     */
    private void initRefreshStatus(boolean b) {
        if (b) {
            mLoginPb.setVisibility(View.GONE);
            mLoginConfirmTv.setClickable(b);
            mLoginConfirmTv.setBackgroundResource(R.drawable.bt_pressed_selecter);
        }else{
            mLoginPb.setVisibility(View.VISIBLE);
            mLoginConfirmTv.setClickable(b);
            mLoginConfirmTv.setBackgroundResource(R.drawable.bt_unpress_selecter);
        }


    }


    @Override
    public void onBackPressed() {

        if (mLoginPb.isShown()) {
            mLoginPb.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }
}
