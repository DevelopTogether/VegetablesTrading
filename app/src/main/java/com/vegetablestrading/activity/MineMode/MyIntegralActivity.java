package com.vegetablestrading.activity.MineMode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vegetablestrading.R;
import com.vegetablestrading.adapter.DividerItemDecoration;
import com.vegetablestrading.adapter.MyIntegralAdapter;
import com.vegetablestrading.bean.MyApply;
import com.vegetablestrading.bean.TransportRecord;
import com.vegetablestrading.customViews.CustomView;
import com.vegetablestrading.utils.DaoUtils;
import com.vegetablestrading.utils.PublicUtils;

import java.util.ArrayList;

/**
 * 我的积分
 */
public class MyIntegralActivity extends AppCompatActivity implements View.OnClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_integral);
        daoUtils = new DaoUtils(this,"");
        putIntegralInfoToSqlite();
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
        mTransportDateTv = (TextView) findViewById(R.id.transport_date_tv);
        mIrregularDetailTv = (TextView) findViewById(R.id.irregular_detail_tv);
        mTransportNoTv = (TextView) findViewById(R.id.transport_no_tv);
        mMineIntegralRv = (RecyclerView) findViewById(R.id.mine_integral_rv);
        mMineIntegralRv.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.HORIZONTAL,R.drawable.horizontal_line_grey));
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mMineIntegralRv.setLayoutManager(manager);
        adapter = new MyIntegralAdapter();
        mMineIntegralRv.setAdapter(adapter);
        adapter.setData(getTransportRecordData());
        adapter.setMyIntegralItemClick(new MyIntegralAdapter.MyIntegralItemClick() {
            @Override
            public void itemClick(TransportRecord transportRecord) {
                PublicUtils.transportRecord = transportRecord;
                Toast.makeText(getApplicationContext(), transportRecord.getResidualIntegral(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(MyIntegralActivity.this,TransportInfoActivity.class));
            }
        });
    }
    /**
     * 测试数据
     * @return
     */
    private ArrayList<TransportRecord> getTransportRecordData(){
        ArrayList<TransportRecord> arrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TransportRecord bean = new TransportRecord();
            bean.setLogisticsNo("110121211000200"+i);
            bean.setTransportPeople("王彬");
            bean.setTransportPeopleMobile("15311810032");
            bean.setTransportTime("2017-11-20 14:21:30");
            bean.setTransportInfo("多送点香菜");
            bean.setPetName("王司令");
            bean.setMobile("18888888888");
            bean.setAddress("北京市海淀区增光路30号");
            bean.setResidualIntegral(i+"");
            bean.setRelayBoxNo("168712357164563");
            bean.setOperatingPeople("文员1");
            bean.setOperateTime("2017-11-20 14:22:46");
            bean.setNoteInfo(".dlkfj");
            arrayList.add(bean);
        }

        return arrayList;
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
     * 将我的积分信息保存本地
     */
    private void putIntegralInfoToSqlite(){
        daoUtils.deleteAllEntity(MyApply.class);
        daoUtils.insertMultEntity(getTransportRecordData());

    }
    /**
     * 初始化adapter数据
     */
    private void initDataForAdapter(){
        if (PublicUtils.isConnected(this)) {
            adapter.setData(daoUtils.listAllapplys());
            //TODO 从服务端请求申请记录
//            OkHttpUtils
//                    .post()
//                    .url(url)
//                    .addParams("username", "hyman")
//                    .addParams("password", "123")
//                    .build()
//                    .execute(new StringCallback() {
//                        @Override
//                        public void onError(Call call, Exception e, int id) {
//
//                        }
//
//                        @Override
//                        public void onResponse(String response, int id) {
//
//                        }
//                    });

        }else{//没有网络的情况下读取数据库里面的数据
            adapter.setData(daoUtils.listAllapplys());
        }
    }
}
