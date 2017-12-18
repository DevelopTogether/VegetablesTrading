package com.vegetablestrading.activity.MineMode;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vegetablestrading.R;
import com.vegetablestrading.activity.BaseActivity;

/**
 * created by 8级大的狂风
 * created date 2017/11/28 10:29.
 * application 服务条款
 */
public class TermOfServiceActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private TextView mTermOfServiceContentTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_of_service);
        initView();
    }

    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);
        mTopTitleTv.setText("用户服务协议");
        mTermOfServiceContentTv = (TextView) findViewById(R.id.termOfService_content_tv);
        mTermOfServiceContentTv.setText(R.string.term_service);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_image_iv:
                finish();
                break;
        }
    }
}
