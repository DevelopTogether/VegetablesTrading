package com.vegetablestrading.activity.MineMode.myPick;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vegetablestrading.R;
import com.vegetablestrading.activity.BaseActivity;
import com.vegetablestrading.adapter.DividerItemDecoration;
import com.vegetablestrading.adapter.PickRecordAdapter;
import com.vegetablestrading.bean.MyPickInfo;
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
 * 采摘记录
 */
public class PickRecordActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private ImageView mTopRightImageIv;
    private RecyclerView mPickRecordRv;
    private PickRecordAdapter adapter;
    private DaoUtils daoUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_record);
        daoUtils = new DaoUtils(this, "");
        initView();
    }

    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);
        mTopTitleTv.setText("采摘记录");
        mTopRightImageIv = (ImageView) findViewById(R.id.top_right_image_iv);
        mPickRecordRv = (RecyclerView) findViewById(R.id.pick_record_rv);
        mPickRecordRv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, R.drawable.horizontal_line_grey));
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mPickRecordRv.setLayoutManager(manager);
        adapter = new PickRecordAdapter();
        mPickRecordRv.setAdapter(adapter);
        initDataForAdapter();
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
     * 初始化adapter数据
     */
    private void initDataForAdapter() {
        if (PublicUtils.isConnected(this)) {
            pickRecordFromService("2017-11-05 10:30:00", CalendarUtil.getCurrentTime());

        } else {//没有网络的情况下读取数据库里面的数据
            adapter.setData(daoUtils.listAll(MyPickInfo.class));
        }
    }

    /**
     * 根据起始时间获取采摘记录
     *
     * @param startTime
     * @param endTime
     */
    private void pickRecordFromService(String startTime, String endTime) {
        OkHttpUtils
                .post()
                .url(Constant.pickRecord_url)
                .addParams("userId", PublicUtils.userInfo.getUserId())
                .addParams("startTime", startTime)
                .addParams("endTime", endTime)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(PickRecordActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                String result = obj.getString("Result");
                                String message = obj.getString("Model");
                                if ("Ok".equals(result)) {
                                    ArrayList<MyPickInfo> arrays = GsonUtils.jsonToArrayList(message, MyPickInfo.class);
                                    Collections.reverse(arrays);
                                    adapter.setData(arrays);
                                    putPickRecordInfoToSqlite(arrays);
                                } else {
                                    Toast.makeText(PickRecordActivity.this, message, Toast.LENGTH_LONG).show();
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
    private void putPickRecordInfoToSqlite(ArrayList<MyPickInfo> arrayList) {
        daoUtils.deleteAllEntity(TransportVegetableInfo.class);
        daoUtils.insertMultEntity(arrayList);

    }
}
