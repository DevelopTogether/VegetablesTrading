package com.vegetablestrading.activity.MineMode.myApply;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vegetablestrading.R;
import com.vegetablestrading.customViews.CustomView;
import com.vegetablestrading.utils.PublicUtils;

public class ApplyDescriptActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private ImageView mTopRightImageIv;
    private CustomView mApplyTypeDescriptCv;
    /**
     * 申请开始时间
     */
    private TextView mApplyStartTimeTv;
    /**
     * 申请结束时间
     */
    private TextView mApplyEndTimeTv;
    /**
     * 申请内容
     */
    private TextView mApplyInfoTv;
    private CustomView mOperateStatusCv;
    private CustomView mOperatingPeopleCv;
    private CustomView mOperatingTimeCv;
    private CustomView mOperatingNoteCv;
    /**
     * 申请提交时间
     */
    private TextView mApplyUploadTimeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_descript);
        initView();
    }

    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);
        mTopTitleTv.setText("申请详情");
        mTopRightImageIv = (ImageView) findViewById(R.id.top_right_image_iv);
        mApplyTypeDescriptCv = (CustomView) findViewById(R.id.apply_type_descript_cv);
        mApplyTypeDescriptCv.getTitleBarRightBtn().setText(PublicUtils.myApply.getApplyType());
        mApplyTypeDescriptCv.getTitleBarLeftBtn().setTextSize(16);
        mApplyTypeDescriptCv.getTitleBarRightBtn().setTextSize(16);
        mApplyStartTimeTv = (TextView) findViewById(R.id.apply_startTime_tv);
        mApplyStartTimeTv.setText("申请开始时间："+PublicUtils.myApply.getApplyStartTime());
        mApplyEndTimeTv = (TextView) findViewById(R.id.apply_endTime_tv);
        mApplyEndTimeTv.setText("申请结束时间："+PublicUtils.myApply.getApplyEndTime());
        mApplyInfoTv = (TextView) findViewById(R.id.apply_info_tv);
        mApplyInfoTv.setText("申请描述："+PublicUtils.myApply.getApplyInfo());
        mOperateStatusCv = (CustomView) findViewById(R.id.operateStatus_cv);
        mOperateStatusCv.getTitleBarRightBtn().setText(PublicUtils.myApply.getOperateStatus());
        mOperatingPeopleCv = (CustomView) findViewById(R.id.operatingPeople_cv);
        mOperatingPeopleCv.getTitleBarRightBtn().setText(PublicUtils.myApply.getOperatingPeople());
        mOperatingTimeCv = (CustomView) findViewById(R.id.operatingTime_cv);
        mOperatingTimeCv.getTitleBarRightBtn().setText(PublicUtils.myApply.getOperateTime());
        mOperatingNoteCv = (CustomView) findViewById(R.id.operatingNote_cv);
        mOperatingNoteCv.getTitleBarRightBtn().setText(PublicUtils.myApply.getOperateNote());
        mApplyUploadTimeTv = (TextView) findViewById(R.id.apply_uploadTime_tv);
        mApplyUploadTimeTv.setText("申请提交时间："+PublicUtils.myApply.getApplyTime());
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
