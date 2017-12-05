package com.vegetablestrading.activity.MineMode.myPick;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vegetablestrading.R;
import com.vegetablestrading.adapter.DividerItemDecoration;
import com.vegetablestrading.adapter.PickRecordAdapter;
import com.vegetablestrading.bean.MyPickInfo;
import com.vegetablestrading.utils.DaoUtils;
import com.vegetablestrading.utils.PublicUtils;

import java.util.ArrayList;

/**
 * 采摘记录
 */
public class PickRecordActivity extends AppCompatActivity implements View.OnClickListener {

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
        daoUtils = new DaoUtils(this,"");
        putMyPickInfoToSqlite();
        initView();
    }

    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);
        mTopTitleTv.setText("采摘记录");
        mTopRightImageIv = (ImageView) findViewById(R.id.top_right_image_iv);
        mPickRecordRv = (RecyclerView) findViewById(R.id.pick_record_rv);
        mPickRecordRv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL,R.drawable.horizontal_line_grey));
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mPickRecordRv.setLayoutManager(manager);
        adapter = new PickRecordAdapter();
        mPickRecordRv.setAdapter(adapter);
        initDataForAdapter();
    }

    /**
     * 测试数据
     * @return
     */
    private ArrayList<MyPickInfo> getPickRecordData(){
        ArrayList<MyPickInfo> arrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MyPickInfo bean = new MyPickInfo();
            bean.setPetName("王司令");
            bean.setPickPeopleNumber("6人");
            bean.setResidualPickAmount((4-i)+"次");
            bean.setPick_Time("2017-11-20 14:21:30");
            bean.setOperatingPeople("文员1");
            bean.setOperateTime("2017-11-20 14:22:46");
            bean.setOperateNote("这次采摘数量和种类，这次采摘数量和种类这");
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
     * 将申请记录信息保存本地
     */
    private void putMyPickInfoToSqlite(){
        daoUtils.deleteAllEntity(MyPickInfo.class);
        daoUtils.insertMultEntity(getPickRecordData());

    }
    /**
     * 初始化adapter数据
     */
    private void initDataForAdapter(){
        if (PublicUtils.isConnected(this)) {
            adapter.setData(daoUtils.listAllMyPickInfo());
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
            adapter.setData(daoUtils.listAllMyPickInfo());
        }
    }
}
