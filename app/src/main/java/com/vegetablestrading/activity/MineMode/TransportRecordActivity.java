package com.vegetablestrading.activity.MineMode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vegetablestrading.R;
import com.vegetablestrading.activity.BaseActivity;
import com.vegetablestrading.adapter.DividerItemDecoration;
import com.vegetablestrading.adapter.TransportRecordAdapter;
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
import java.util.List;

import okhttp3.Call;

import static com.vegetablestrading.R.id.top_left_image_iv;

/**
 * 配送记录
 */
public class TransportRecordActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private ImageView mTopRightImageIv;
    private RecyclerView mTransportRecordRv;
    private TransportRecordAdapter adapter;
    private DaoUtils daoUtil;
    private LinearLayout mNoRecordLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);
        daoUtil = new DaoUtils(this, "transport_record.sqlit");
        initView();
        initActionBar();

    }

    /**
     * 初始化actionBar
     */
    private void initActionBar() {
        mTopTitleTv.setText("配送记录");
    }

    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);
        mTopRightImageIv = (ImageView) findViewById(R.id.top_right_image_iv);
        mTransportRecordRv = (RecyclerView) findViewById(R.id.transport_record_rv);
        mTransportRecordRv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, R.drawable.horizontal_line_grey));
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mTransportRecordRv.setLayoutManager(manager);
        adapter = new TransportRecordAdapter();
        mTransportRecordRv.setAdapter(adapter);
        transportRecordsByDate(PublicUtils.userInfo.getRegistDate(), CalendarUtil.getCurrentTime());
        adapter.setTransportRecordItemClick(new TransportRecordAdapter.TransportRecordItemClick() {
            @Override
            public void itemClick(TransportRecord transportRecord) {//item点击事件
                PublicUtils.transportRecordClicked = transportRecord;
                startActivity(new Intent(TransportRecordActivity.this, TransportInfoActivity.class));
            }

        });
        mNoRecordLl = (LinearLayout) findViewById(R.id.no_record_ll);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_left_image_iv:
                finish();
                break;
            default:
                break;
        }
    }


    /**
     * 根据起始时间获取配送记录
     *
     * @param startTime
     * @param endTime
     */
    private void transportRecordsByDate(String startTime, String endTime) {
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
                        Toast.makeText(TransportRecordActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                        ArrayList<TransportRecord> arrayList = daoUtil.listAll(TransportRecord.class);
                       if (arrayList.size()==0) {
                           mNoRecordLl.setVisibility(View.VISIBLE);
                       }else{
                           mNoRecordLl.setVisibility(View.GONE);
                       }
                        adapter.setData(arrayList);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                String result = obj.getString("Result");
                                if ("Ok".equals(result)) {
                                    String message = obj.getString("Model");
                                    ArrayList<TransportRecord> arrays = GsonUtils.jsonToArrayList(message, TransportRecord.class);
                                    if (arrays.size() > 0) {
                                        mNoRecordLl.setVisibility(View.GONE);
                                        List<LogisticsInfo> arrays_list = new ArrayList<LogisticsInfo>();
                                        List<TransportVegetableInfo> arrays_transport = new ArrayList<TransportVegetableInfo>();
                                        for (TransportRecord array : arrays) {
                                            array.setLogisticsInfos(arrays_list);
                                            array.setTransportVegetableInfos(arrays_transport);
                                        }
                                        Collections.reverse(arrays);
                                        adapter.setData(arrays);
                                    } else {
                                        mNoRecordLl.setVisibility(View.VISIBLE);
                                    }
                                    putTransportRecordInfoToSqlite(arrays);
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
    private void putTransportRecordInfoToSqlite(ArrayList<TransportRecord> list_service) {
        ArrayList<TransportRecord> list_local = daoUtil.listAll(TransportRecord.class);
        if (list_service.size()==0) {
            daoUtil.deleteAllEntity(TransportRecord.class) ;
        }
        List<String> arrays_recordId = new ArrayList<>();
        for (TransportRecord transportRecord : list_local) {
            arrays_recordId.add(transportRecord.getTransportRecordId());
        }
        for (TransportRecord record : list_service) {
            if (!arrays_recordId.contains(record.getTransportRecordId())) {
                daoUtil.insertEntity(record);
            }
        }

    }
}
