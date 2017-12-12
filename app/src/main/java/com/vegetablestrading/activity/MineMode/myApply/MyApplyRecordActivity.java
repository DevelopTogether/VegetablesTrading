package com.vegetablestrading.activity.MineMode.myApply;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vegetablestrading.R;
import com.vegetablestrading.adapter.DividerItemDecoration;
import com.vegetablestrading.adapter.MyApplyAdapter;
import com.vegetablestrading.bean.MyApply;
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
 * 我的申请
 */
public class MyApplyRecordActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private ImageView mTopRightImageIv;
    private RecyclerView mMyApplyRv;
    private MyApplyAdapter adapter;
    private DaoUtils daoUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_apply);
        daoUtils = new DaoUtils(this, "");

        initView();


    }


    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);
        mTopTitleTv.setText("申请记录");
        mMyApplyRv = (RecyclerView) findViewById(R.id.my_apply_rv);
        mMyApplyRv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, R.drawable.horizontal_line_grey));
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mMyApplyRv.setLayoutManager(manager);
        adapter = new MyApplyAdapter();
        mMyApplyRv.setAdapter(adapter);
        initDataForAdapter();
        adapter.setMyApplyItemClick(new MyApplyAdapter.MyApplyItemClick() {
            @Override
            public void itemClick(MyApply myApply) {
                PublicUtils.myApply = myApply;
                startActivity(new Intent(MyApplyRecordActivity.this, ApplyDescriptActivity.class));
            }
        });
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
     * 将申请记录信息保存本地
     */
    private void putApplyRecordInfoToSqlite( ArrayList arrayList) {
        daoUtils.deleteAllEntity(MyApply.class);
        daoUtils.insertMultEntity(arrayList);

    }

    /**
     * 初始化adapter数据
     */
    private void initDataForAdapter() {
        //TODO 从服务端请求申请记录
        OkHttpUtils
                .post()
                .url(Constant.applyRecordOfMin_url)
                .addParams("userId", PublicUtils.userInfo.getUserId())
                .addParams("startTime", "2017-10-2 10:52:22")
                .addParams("endTime", CalendarUtil.getCurrentTime())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_LONG).show();
                        adapter.setData(daoUtils.listAll(MyApply.class));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                String message = obj.getString("Model");
                                ArrayList arrayList = GsonUtils.jsonToArrayList(message, MyApply.class);
                                Collections.reverse(arrayList);
                                adapter.setData(arrayList);
                                putApplyRecordInfoToSqlite(arrayList);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                        Log.e("DEBUG", response);


                    }
                });

    }
}
