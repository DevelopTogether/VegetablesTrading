package com.vegetablestrading.activity.MineMode;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vegetablestrading.R;
import com.vegetablestrading.activity.BaseActivity;
import com.vegetablestrading.adapter.AddressListAdapter;
import com.vegetablestrading.adapter.DividerItemDecoration;
import com.vegetablestrading.bean.AddressInfo;
import com.vegetablestrading.utils.Constant;
import com.vegetablestrading.utils.PublicUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;

/**
 * created by 8级大的狂风
 * created date 2017/12/26 13:18.
 * application  地址管理类
 */
public class AddrManagerActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private RecyclerView mAddrManagerRv;
    private FloatingActionButton mAddAddressFab;
    private AddressListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addr_manager);
        initView();
    }

    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);
        mTopTitleTv.setText("地址管理");
        mAddrManagerRv = (RecyclerView) findViewById(R.id.addr_manager_rv);
        mAddAddressFab = (FloatingActionButton) findViewById(R.id.add_address_fab);
        mAddAddressFab.setOnClickListener(this);

        mAddrManagerRv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL,25,getResources().getColor(R.color.navigition_normal)));
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mAddrManagerRv.setLayoutManager(manager);
        adapter = new AddressListAdapter();
        mAddrManagerRv.setAdapter(adapter);
        adapter.setData(initData(),this);
    }

    private List<AddressInfo> initData() {
        List<AddressInfo> arrays = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            AddressInfo bean = new AddressInfo();
            bean.setAccepter(i+"");
            bean.setAccepterPhone("15311810000");
            bean.setAddressId("21312"+i);
            bean.setAddress("北京市海淀区增光路30号 对接发时代峰峻发动机ad将开发 ".substring(0,"北京市海淀区增光路30号 对接发时代峰峻发动机ad将开发 ".length()-i*3));
            if (i==4) {
                bean.setIsDefault("1");
            }else{
                bean.setIsDefault("2");
            }
            arrays.add(bean);
            Collections.sort(arrays);
        }
        return arrays;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_image_iv:
                finish();
                break;
            case R.id.add_address_fab:
                startActivity(new Intent(this,AddAddressActivity.class));
                break;
        }
    }
}
