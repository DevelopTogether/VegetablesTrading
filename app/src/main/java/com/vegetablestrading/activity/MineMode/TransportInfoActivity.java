package com.vegetablestrading.activity.MineMode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vegetablestrading.R;
import com.vegetablestrading.adapter.DividerItemDecoration;
import com.vegetablestrading.adapter.LogisticsInfoAdapter;
import com.vegetablestrading.adapter.TransportListAdapter;
import com.vegetablestrading.bean.LogisticsInfo;
import com.vegetablestrading.bean.TransportRecord;
import com.vegetablestrading.bean.TransportVegetableInfo;
import com.vegetablestrading.utils.CalendarUtil;
import com.vegetablestrading.utils.Constant;
import com.vegetablestrading.utils.DaoUtils;
import com.vegetablestrading.utils.GsonUtils;
import com.vegetablestrading.utils.PublicUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import okhttp3.Call;

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
    private TransportRecord transportRecord = PublicUtils.transportRecordClicked;
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
    private DaoUtils daoUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_info);
        initView();
        initActionBar();
        daoUtil = new DaoUtils(this, "transport_record.sqlit");
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
        String[] times = CalendarUtil.getWeekStartAndWeekEndBaseTime(CalendarUtil.getZeroTime(transportRecord.getTransportTime()));
       if (times.length>1) {
           transportVegetablesByDate(times[0], times[1]);
       }

        mTransportNoTv.setText("配送单号："+transportRecord.getLogisticsNo());
        mTransportTimeTv.setText("配送时间："+transportRecord.getTransportTime());
        mTransportPersionTv.setText("配送人："+transportRecord.getTransportPeople());
        mTransportPersionMobileTv.setText("配送人电话："+transportRecord.getTransportPeopleMobile());
        mAcceptPersionTv.setText("收货人："+transportRecord.getUserName());
        mAcceptPersionMobileTv.setText("收货人电话："+transportRecord.getMobile());
        mAcceptAddrTv.setText("收货地址："+transportRecord.getAddress());
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
//        adapter_logistics.setData(transportRecord.getLogisticsInfos());
        LogisticsInfoFromService();
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
     * 根据起始时间获取配送清单
     *
     * @param startTime
     * @param endTime
     */
    private void transportVegetablesByDate(String startTime, String endTime) {
        OkHttpUtils
                .post()
                .url(Constant.transportVegetablesByDate_url)
                .addParams("startTime", startTime)
                .addParams("endTime", endTime)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call cll, Exception e, int id) {
                        Toast.makeText(TransportInfoActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                        adapter.setData(transportRecord.getTransportVegetableInfos());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                String result = obj.getString("Result");
                                String message = obj.getString("Model");
                                if ("Ok".equals(result)) {
                                    ArrayList<TransportVegetableInfo> arrays = GsonUtils.jsonToArrayList(message, TransportVegetableInfo.class);
                                    adapter.setData(arrays);
                                    putTransportVegetableInfoToSqlite(arrays);
                                } else {
                                    Toast.makeText(TransportInfoActivity.this, message, Toast.LENGTH_LONG).show();
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
     * 将配送蔬菜信息保存本地
     */
    private void putTransportVegetableInfoToSqlite(ArrayList<TransportVegetableInfo> arrayList) {
        transportRecord.setTransportVegetableInfos(arrayList);
        daoUtil.updateStudent(transportRecord);
    }

    /**
     * 根据起始时间获取配送清单
     */
    private void LogisticsInfoFromService() {
        OkHttpUtils
                .post()
                .url(Constant.logisticsInfo_url)
                .addParams("userId", PublicUtils.userInfo.getUserId())
                .addParams("transportRecordId", transportRecord.getTransportRecordId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call cll, Exception e, int id) {
                        Toast.makeText(TransportInfoActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                        adapter_logistics.setData(transportRecord.getLogisticsInfos());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                String result = obj.getString("Result");

                                if ("Ok".equals(result)) {
                                    String message = obj.getString("Model");
                                    ArrayList<LogisticsInfo> arrays = GsonUtils.jsonToArrayList(message, LogisticsInfo.class);

                                    Collections.reverse(arrays);
                                    adapter_logistics.setData(arrays);
                                    putLogisticsInfoToSqlite(arrays);
                                } else {
                                    mLogisticsStatusTv.setText("暂无物流信息");
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
     * 将物流信息保存本地
     *
     * @param arrays
     */
    private void putLogisticsInfoToSqlite(ArrayList<LogisticsInfo> arrays) {
        transportRecord.setLogisticsInfos(arrays);
        daoUtil.updateStudent(transportRecord);
    }
}
