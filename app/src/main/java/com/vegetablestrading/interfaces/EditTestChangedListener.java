package com.vegetablestrading.interfaces;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vegetablestrading.utils.PublicUtils;
import com.vegetablestrading.utils.SharedPreferencesHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ${王sir} on 2017/11/8.
 * application
 */

public class EditTestChangedListener implements TextWatcher{
    private Context context;
    private EditText editText_pre;
    private EditText editText;
    private int mTag;//标识
    private String mString;
    private TextView mTextView;//获取验证码控件

    public EditTestChangedListener(Context context, EditText editText, int tag) {

        this.context = context;
        this.editText = editText;
        this.mTag = tag;
    }
    public EditTestChangedListener(Context context, EditText editText, EditText editText_pre, int tag) {

        this.context = context;
        this.editText = editText;
        this.editText_pre = editText_pre;
        this.mTag = tag;
    }
    public EditTestChangedListener(Context context, EditText editText, TextView textView, int tag) {

        this.context = context;
        this.editText = editText;
        this.mTextView = textView;
        this.mTag = tag;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        mString = editText.getText().toString().trim();
       switch (mTag) {
           case 0://手机号
               if (mString.length()>10) {
                   if (!PublicUtils.isMobileNO(mString)) {
                       Toast.makeText(context, "手机号格式不正确", Toast.LENGTH_LONG).show();
                       return;
                   }
//                   else{
//                       mTextView.setBackgroundResource(R.drawable.bt_pressed_selecter);
//                   }
               }
//               else{
//                   mTextView.setBackgroundResource(R.drawable.cancel_regist_shape);
//               }
               break;
           case 1://短信验证码
               if (mString.length()>4) {
                   Toast.makeText(context, "验证码输入错误", Toast.LENGTH_SHORT).show();
               }
               break;
           case 2://密码
               if (TextUtils.isEmpty(mString)) {
                   return;
               }
               if (mString.length()>11) {
                   if (!PublicUtils.isContainAll(mString)) {
                       Toast.makeText(context, "请按规定格式填写密码以保证账号安全", Toast.LENGTH_LONG).show();
                       return;
                   }
               }

               break;
           case 3://重复密码
               String pwd = editText_pre.getText().toString().trim();
               if (TextUtils.isEmpty(pwd)) {
                   return;
               }
               if (mString.length()>pwd.length()-1) {
                   if (!PublicUtils.isContainAll(pwd)) {
                       Toast.makeText(context, "密码设置不符合规范，需要字母和数字的组合", Toast.LENGTH_LONG).show();
                       return;
                   }
                   if (!mString.equals(pwd)) {
                       Toast.makeText(context, "两次输入的密码不相同", Toast.LENGTH_LONG).show();
                   }
               }
               break;
           case 4://邮箱
               break;
           case 5://会员昵称
               setEditTextInhibitInputSpeChat(editText);
               break;
           case 11://修改密码中输入原密码

               break;
           case 12://修改密码中输入新密码

               String old_pwd = editText_pre.getText().toString().trim();
               if (old_pwd==null||TextUtils.isEmpty(old_pwd)) {
                   Toast.makeText(context, "请先输入原密码", Toast.LENGTH_LONG).show();
                   return;
               }else{
                   //TODO 检测原密码输入是否正确
               }
               if (mString.length()>11) {
                   if (!PublicUtils.isContainAll(mString)) {
                       Toast.makeText(context, "请按规定格式填写密码以保证账号安全", Toast.LENGTH_LONG).show();
                       return;
                   }
               }

               break;
           case 13://修改密码中重复输入新密码
               String pwd_new = editText_pre.getText().toString().trim();
               if (pwd_new==null||TextUtils.isEmpty(pwd_new)) {
                   Toast.makeText(context, "请先输入新密码", Toast.LENGTH_LONG).show();
                   return;
               }
               //检查新密码的格式是否正确
               if (!PublicUtils.isContainAll(pwd_new)) {
                   Toast.makeText(context, "新密码格式有误，请按规定格式填写密码以保证账号安全", Toast.LENGTH_LONG).show();
                   return;
               }
               //重复新密码和新密码比较
               if (mString.length()>pwd_new.length()-1) {
                   if (!PublicUtils.isContainAll(pwd_new)) {
                       Toast.makeText(context, "密码设置不符合规范，需要字母和数字的组合", Toast.LENGTH_LONG).show();
                       return;
                   }
                   if (!mString.equals(pwd_new)) {
                       Toast.makeText(context, "两次输入的密码不相同，请重新输入", Toast.LENGTH_LONG).show();
                   }
               }
               break;
           case 14://忘记密码中输入手机号的监听
               if (mString.length()>10) {
                   if (!PublicUtils.isMobileNO(mString)) {
                       Toast.makeText(context, "手机号格式不正确", Toast.LENGTH_LONG).show();
                       return;
                   }else{
                       String savedMobile = getSavedUserMobile();
                       if (TextUtils.isEmpty(savedMobile)) {
                           Toast.makeText(context.getApplicationContext(), "您还没有注册账户，请先注册", Toast.LENGTH_SHORT).show();
                       }else{
                           if (!savedMobile.equals(mString)) {
                               Toast.makeText(context.getApplicationContext(), "请输入注册时登记的手机号", Toast.LENGTH_SHORT).show();
                           }
//                           else{
//                               mTextView.setBackgroundResource(R.drawable.bt_pressed_selecter);
//                           }
                       }
                   }

               }
//               else{
//                   mTextView.setBackgroundResource(R.drawable.cancel_regist_shape);
//               }
               break;
           case 15://地址管理中的收货人手机号监听
               if (mString.length()>10) {
                   if (!PublicUtils.isMobileNO(mString)) {
                       Toast.makeText(context, "手机号格式不正确", Toast.LENGTH_LONG).show();
                       return;
                   }

               }
               break;
           default:
               break;
       }
    }

    /**
     * 获取保存的手机号
     * @return
     */
    private String getSavedUserMobile() {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context, "USERINFO");
        return sharedPreferencesHelper.getString("USER_MOBILE", "");
    }

    /**
     * 禁止EditText输入特殊字符
     * @param editText
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText){

        InputFilter filter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if(matcher.find()) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }
}
