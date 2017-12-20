package com.vegetablestrading.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vegetablestrading.R;
import com.vegetablestrading.bean.TransportVegetableInfo;
import com.vegetablestrading.utils.CalendarUtil;
import com.vegetablestrading.utils.Constant;
import com.vegetablestrading.utils.PublicUtils;

/**
 * created by 8级大的狂风
 * created date 2017/12/18 15:02.
 * application  配送蔬菜详情描述
 */
public class VegetableDescriptActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private ImageView mVegetableDescriptionIconIv;
    /**
     * 油菜
     */
    private TextView mVegetableDescriptionNameTv;
    /**
     * 重量：5KG
     */
    private TextView mVegetableDescriptionWeightTv;
    /**
     * 描述信息
     */
    private TextView mVegetableDescriptionDescripTv;
    /**
     * 开始配送时间：5016-16-16 16:00:00
     */
    private TextView mStartTimeTransportDescriptionTv;
    /**
     * 结束配送时间：5016-16-16 16:00:00
     */
    private TextView mEndTimeTransportDescriptionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetable_descript);
        initView();
    }

    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);

        mVegetableDescriptionIconIv = (ImageView) findViewById(R.id.vegetable_description_icon_iv);

        mVegetableDescriptionNameTv = (TextView) findViewById(R.id.vegetable_description_name_tv);

        mVegetableDescriptionWeightTv = (TextView) findViewById(R.id.vegetable_description_weight_tv);
        mVegetableDescriptionDescripTv = (TextView) findViewById(R.id.vegetable_description_descrip_tv);
        mStartTimeTransportDescriptionTv = (TextView) findViewById(R.id.startTime_transport_description_tv);
        mEndTimeTransportDescriptionTv = (TextView) findViewById(R.id.endTime_transport_description_tv);
        initDataToView(PublicUtils.transportVegetableInfo);

    }

    private void initDataToView(TransportVegetableInfo bean) {
        mTopTitleTv.setText(bean.getVegetableName() + "详情");
        Glide.with(this).load(Uri.parse(Constant.company_url + bean.getVegetableIcon()))
                .placeholder(R.drawable.placeholder_icon) // can also be a drawable
                .error(R.drawable.placeholder_icon) // will be displayed if the image cannot be loaded
                .into(mVegetableDescriptionIconIv);
        mVegetableDescriptionNameTv.setText(bean.getVegetableName());
        mVegetableDescriptionWeightTv.setText("重量："+bean.getWeight());
        if (TextUtils.isEmpty(bean.getVegetableInfo())) {
            mVegetableDescriptionDescripTv.setVisibility(View.GONE);
        }else{
            mVegetableDescriptionDescripTv.setVisibility(View.VISIBLE);
            mVegetableDescriptionDescripTv.setText(bean.getVegetableInfo());
        }

        mStartTimeTransportDescriptionTv.setText("开始有效期："+ CalendarUtil.getSpecialTypeTime(bean.getTransportStartTime()));
        mEndTimeTransportDescriptionTv.setText("结束有效期："+CalendarUtil.getSpecialTypeTime(bean.getTransportEndTime()));
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
