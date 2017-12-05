package com.vegetablestrading.activity.MineMode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vegetablestrading.R;
import com.vegetablestrading.adapter.DividerItemDecoration;
import com.vegetablestrading.adapter.LogisticsInfoAdapter;
import com.vegetablestrading.adapter.TransportListAdapter;
import com.vegetablestrading.bean.TransportRecord;
import com.vegetablestrading.bean.TransportVegetableInfo;
import com.vegetablestrading.utils.PublicUtils;

import java.util.ArrayList;

/**
 * 配送详情
 */
public class TransportInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private ImageView mTopRightImageIv;
    /**
     * 配送单号：
     */
    private TextView mTransportNoTv;
    /**
     * 配送时间：
     */
    private TextView mTransportTimeTv;
    /**
     * 配送人：
     */
    private TextView mTransportPersionTv;
    /**
     * 配送人电话：
     */
    private TextView mTransportPersionMobileTv;
    /**
     * 收货人：
     */
    private TextView mAcceptPersionTv;
    /**
     * 收货人电话：
     */
    private TextView mAcceptPersionMobileTv;
    /**
     * 收货地址：
     */
    private TextView mAcceptAddrTv;
    private RecyclerView mTransportInfoDetailRv;
    private TransportRecord transportRecord = PublicUtils.transportRecord;
    private TransportListAdapter adapter;
    /**
     * 配送中
     */
    private TextView mLogisticsStatusTv;
    private ImageView mDirectionIconIv;
    private RelativeLayout mLogisticsDesRl;
    private RecyclerView mLogisticsInfoRv;
    private LogisticsInfoAdapter adapter_logistics;

    private boolean displayLogistics = false;//是否展示物流详情
    private TextView mLogisticsTypeTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_info);
        initView();
        initActionBar();
    }

    /**
     * 初始化actionBar
     */
    private void initActionBar() {
        mTopTitleTv.setText("配送详情");
    }

    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);
        mTopRightImageIv = (ImageView) findViewById(R.id.top_right_image_iv);
        mTransportNoTv = (TextView) findViewById(R.id.transport_no_tv);
        mTransportTimeTv = (TextView) findViewById(R.id.transport_time_tv);
        mTransportPersionTv = (TextView) findViewById(R.id.transport_persion_tv);
        mTransportPersionMobileTv = (TextView) findViewById(R.id.transport_persion_mobile_tv);
        mAcceptPersionTv = (TextView) findViewById(R.id.accept_persion_tv);
        mAcceptPersionMobileTv = (TextView) findViewById(R.id.accept_persion_mobile_tv);
        mAcceptAddrTv = (TextView) findViewById(R.id.accept_addr_tv);
        mTransportInfoDetailRv = (RecyclerView) findViewById(R.id.transport_info_detail_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mTransportInfoDetailRv.setLayoutManager(linearLayoutManager);
        adapter = new TransportListAdapter(this);
        mTransportInfoDetailRv.setAdapter(adapter);
        mTransportInfoDetailRv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, R.drawable.horizontal_line_grey));
        adapter.setData(getTransportVegetables());
        mTransportNoTv.setText(transportRecord.getLogisticsNo());
        mTransportTimeTv.setText(transportRecord.getTransportTime());
        mTransportPersionTv.setText(transportRecord.getTransportPeople());
        mTransportPersionMobileTv.setText(transportRecord.getTransportPeopleMobile());
        mAcceptPersionTv.setText(transportRecord.getPetName());
        mAcceptPersionMobileTv.setText(transportRecord.getMobile());
        mAcceptAddrTv.setText(transportRecord.getAddress());
        mLogisticsStatusTv = (TextView) findViewById(R.id.logistics_status_tv);
        mDirectionIconIv = (ImageView) findViewById(R.id.direction_icon_iv);
        mDirectionIconIv.setBackgroundResource(R.drawable.down);
        mLogisticsDesRl = (RelativeLayout) findViewById(R.id.logistics_des_rl);
        mLogisticsDesRl.setOnClickListener(this);
        mLogisticsInfoRv = (RecyclerView) findViewById(R.id.logistics_info_rv);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mLogisticsInfoRv.setLayoutManager(linearLayoutManager2);
        adapter_logistics = new LogisticsInfoAdapter();
        mLogisticsInfoRv.setAdapter(adapter_logistics);
        mLogisticsInfoRv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, R.drawable.horizontal_line_grey));
        adapter_logistics.setData(transportRecord.getLogisticsInfos());
        mLogisticsTypeTv = (TextView) findViewById(R.id.logistics_type_tv);
        mLogisticsTypeTv.setText(transportRecord.getLogisticsName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_image_iv:
                finish();
                break;
            case R.id.logistics_des_rl://物流详情
                if (!displayLogistics) {
                    mLogisticsInfoRv.setVisibility(View.VISIBLE);
                    mDirectionIconIv.setBackgroundResource(R.drawable.up_icon);
                    displayLogistics = true;
                } else {
                    mLogisticsInfoRv.setVisibility(View.GONE);
                    mDirectionIconIv.setBackgroundResource(R.drawable.down);
                    displayLogistics = false;
                }

                break;
        }
    }

    /**
     * 获取配送列表中的蔬菜信息
     *
     * @return
     */
    public ArrayList<TransportVegetableInfo> getTransportVegetables() {
        ArrayList<TransportVegetableInfo> arrays = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TransportVegetableInfo bean = new TransportVegetableInfo();
            bean.setVegetableName("白菜" + i);
            bean.setWeight("2kg");
            bean.setVegetableInfo("描述信息faslkdjfasdjfasdjfas;dfja;slkdjfa;sljdfaslkd;jf;askjdfljasdflajs;dfljasdf");
            bean.setTransportStartTime("2017-11-21 12:00:00");
            bean.setTransportEndTime("2017-11-21 12:00:00");
            arrays.add(bean);
        }
        return arrays;
    }


}
