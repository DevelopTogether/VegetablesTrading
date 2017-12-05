package com.vegetablestrading.activity.MineMode;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vegetablestrading.R;
import com.vegetablestrading.activity.BaseActivity;
import com.vegetablestrading.interfaces.EditTestChangedListener;
import com.vegetablestrading.utils.PublicUtils;


public class ModifyPwdActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private ImageView mTopRightImageIv;
    private EditText mOldPwdEt;
    private EditText mNewPwdEt;
    private EditText mReNewPwdEt;
    private CheckBox mDisplayPwdCb;
    private LinearLayout mDisplayPwdLl;
    /**
     * 确认修改
     */
    private TextView mConfirmModifyTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        initView();
        initActionBar();
    }

    /**
     * 初始化actionBar
     */
    private void initActionBar() {
        mTopTitleTv.setText("修改密码");
    }

    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);
        mTopRightImageIv = (ImageView) findViewById(R.id.top_right_image_iv);
        mOldPwdEt = (EditText) findViewById(R.id.oldPwd_et);
        mNewPwdEt = (EditText) findViewById(R.id.new_Pwd_et);
        mReNewPwdEt = (EditText) findViewById(R.id.re_new_Pwd_et);
        mDisplayPwdCb = (CheckBox) findViewById(R.id.display_pwd_cb);
        mDisplayPwdLl = (LinearLayout) findViewById(R.id.display_pwd_ll);
        mDisplayPwdLl.setOnClickListener(this);
        mConfirmModifyTv = (TextView) findViewById(R.id.confirm_modify_tv);
        mConfirmModifyTv.setOnClickListener(this);
        mOldPwdEt.addTextChangedListener(new EditTestChangedListener(this, mOldPwdEt, 11));
        mNewPwdEt.addTextChangedListener(new EditTestChangedListener(this, mNewPwdEt, mOldPwdEt, 12));
        mReNewPwdEt.addTextChangedListener(new EditTestChangedListener(this, mReNewPwdEt, mNewPwdEt, 13));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_image_iv://返回
                finish();
                break;
            case R.id.display_pwd_ll://显示密码
                if (mDisplayPwdCb.isChecked()) {//密码不可见
                    mDisplayPwdCb.setChecked(false);
                    mOldPwdEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mNewPwdEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mReNewPwdEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }else{
                    mDisplayPwdCb.setChecked(true);//密码可见
                    mOldPwdEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mNewPwdEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mReNewPwdEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                break;
            case R.id.confirm_modify_tv://确认修改
                commitModify();
                break;
        }
    }

    /**
     * 提交修改的密码
     */
    private void commitModify() {
        String old_pwd = mOldPwdEt.getText().toString().trim();
        String pwd_new = mNewPwdEt.getText().toString().trim();
        String pwd_new_re = mReNewPwdEt.getText().toString().trim();
        if (old_pwd == null || TextUtils.isEmpty(old_pwd)) {
            Toast.makeText(this, "请先输入原密码", Toast.LENGTH_LONG).show();
            return;
        } else {
            //TODO 检测原密码输入是否正确
        }
        if (pwd_new == null || TextUtils.isEmpty(pwd_new)) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_LONG).show();
            return;
        } else {
            if (!PublicUtils.isContainAll(pwd_new)) {
                Toast.makeText(this, "请按规定格式填写密码以保证账号安全", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (pwd_new_re == null || TextUtils.isEmpty(pwd_new_re)) {
            Toast.makeText(this, "请重新输入更改的新密码", Toast.LENGTH_LONG).show();
            return;
        } else {
            if (!PublicUtils.isContainAll(pwd_new)) {
                Toast.makeText(this, "请按规定格式填写密码以保证账号安全", Toast.LENGTH_LONG).show();
                return;
            } else {
                if (!pwd_new_re.equals(pwd_new)) {
                    Toast.makeText(this, "两次输入的密码不相同，请重新输入", Toast.LENGTH_LONG).show();
                }
            }
        }
        //TODO 将新密码提交后台

    }
}
