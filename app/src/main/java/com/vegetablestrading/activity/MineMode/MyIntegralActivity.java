package com.vegetablestrading.activity.MineMode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vegetablestrading.R;
import com.vegetablestrading.activity.BaseActivity;
import com.vegetablestrading.adapter.DividerItemDecoration;
import com.vegetablestrading.adapter.MyIntegralAdapter;
import com.vegetablestrading.bean.TransportRecord;
import com.vegetablestrading.customViews.CustomView;
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

import okhttp3.Call;

/**
 * 我的积分
 */
public class MyIntegralActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private ImageView mTopRightImageIv;
    private CustomView mIrregularDisplayCv;
    /**
     * 配送日期
     */
    private TextView mTransportDateTv;
    /**
     * 扣次明细
     */
    private TextView mIrregularDetailTv;
    /**
     * 配送单号
     */
    private TextView mTransportNoTv;
    private RecyclerView mMineIntegralRv;
    private MyIntegralAdapter adapter;
    private DaoUtils daoUtils;
    private LinearLayout mNoRecordLayoutLl;
    /**
     * 暂无配送记录
     */
    private TextView mNoRecordTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_integral);
        daoUtils = new DaoUtils(this, "shop_vagetable_myIntegral.sqlite");
        initView();
        initActionBar();
    }

    /**
     * 初始化actionBar
     */
    private void initActionBar() {
        mTopTitleTv.setText("我的积分");
    }

    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);
        mTopRightImageIv = (ImageView) findViewById(R.id.top_right_image_iv);
        mIrregularDisplayCv = (CustomView) findViewById(R.id.irregular_display_cv);
        mIrregularDisplayCv.getTitleBarRightBtn().setText(PublicUtils.userInfo.getResidualIntegral() + "分");
        mTransportDateTv = (TextView) findViewById(R.id.transport_date_tv);
        mIrregularDetailTv = (TextView) findViewById(R.id.irregular_detail_tv);
        mTransportNoTv = (TextView) findViewById(R.id.transport_no_tv);
        mMineIntegralRv = (RecyclerView) findViewById(R.id.mine_integral_rv);
        mMineIntegralRv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, R.drawable.horizontal_line_grey));
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mMineIntegralRv.setLayoutManager(manager);
        adapter = new MyIntegralAdapter();
        mMineIntegralRv.setAdapter(adapter);
        transportVegetablesByDate(PublicUtils.userInfo.getRegistDate(), CalendarUtil.getCurrentTime());
        adapter.setMyIntegralItemClick(new MyIntegralAdapter.MyIntegralItemClick() {
            @Override
            public void itemClick(TransportRecord transportRecord) {
                PublicUtils.transportRecordClicked = transportRecord;
                startActivity(new Intent(MyIntegralActivity.this, TransportInfoActivity.class));
            }
        });
        mNoRecordLayoutLl = (LinearLayout) findViewById(R.id.no_record_layout_ll);
        mNoRecordTv = (TextView) findViewById(R.id.no_record_tv);
        mNoRecordTv.setText("暂无消费记录");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_image_iv:
                finish();
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
                .url(Constant.transportRecord_url)
                .addParams("userId", PublicUtils.userInfo.getUserId())
                .addParams("startTime", startTime)
                .addParams("endTime", endTime)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(MyIntegralActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                        ArrayList<TransportRecord> arrays = daoUtils.listAll(TransportRecord.class);
                        if (arrays.size() == 0) {
                            mNoRecordLayoutLl.setVisibility(View.VISIBLE);
                        } else {
                            mNoRecordLayoutLl.setVisibility(View.GONE);
                            adapter.setData(arrays);
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                String result = obj.getString("Result");
                                String message = obj.getString("Model");
                                if ("Ok".equals(result)) {
                                    ArrayList<TransportRecord> arrays = GsonUtils.jsonToArrayList(message, TransportRecord.class);
                                    if (arrays.size() == 0) {
                                        mNoRecordLayoutLl.setVisibility(View.VISIBLE);
                                    } else {
                                        mNoRecordLayoutLl.setVisibility(View.GONE);
                                        adapter.setData(arrays);
                                        putIntegralInfoToSqlite(arrays);
                                    }

                                } else {
                                    Toast.makeText(MyIntegralActivity.this, message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                });
    }

    /**
     * 将我的积分信息保存本地
     */
    private void putIntegralInfoToSqlite(ArrayList<TransportRecord> arrayList) {
        daoUtils.deleteAllEntity(TransportRecord.class);
        daoUtils.insertMultEntity(arrayList);

    }
}
