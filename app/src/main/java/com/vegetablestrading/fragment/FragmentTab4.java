package com.vegetablestrading.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vegetablestrading.R;
import com.vegetablestrading.activity.MineMode.AboutUsActivity;
import com.vegetablestrading.activity.MineMode.ActivateUserActivity;
import com.vegetablestrading.activity.MineMode.ModifyPwdActivity;
import com.vegetablestrading.activity.MineMode.MyInfoActivity;
import com.vegetablestrading.activity.MineMode.MyIntegralActivity;
import com.vegetablestrading.activity.MineMode.QuitPetActivity;
import com.vegetablestrading.activity.MineMode.TransportRecordActivity;
import com.vegetablestrading.activity.MineMode.myApply.AddApplyActivity;
import com.vegetablestrading.adapter.MineModeAdapter;
import com.vegetablestrading.interfaces.MineModeItemClickedListener;
import com.vegetablestrading.utils.PublicUtils;

/**
 * Created by ${王sir} on 2017/10/18.
 * application
 */

public class FragmentTab4 extends Fragment {
    private View view;
    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private ImageView mTopRightImageIv;
    private RecyclerView mMineRv;
    private Context context;
    private MineModeAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab4, container, false);

        initView(view);
        initActionBar();
        return view;


    }



    /**
     * 初始化actionBar
     */
    private void initActionBar() {
        mTopTitleTv.setText("我的");
        mTopRightImageIv.setVisibility(View.INVISIBLE);
        mTopLeftImageIv.setVisibility(View.INVISIBLE);
    }
    //销毁Webview
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initView(View view) {
        mTopLeftImageIv = (ImageView) view.findViewById(R.id.top_left_image_iv);
        mTopTitleTv = (TextView) view.findViewById(R.id.top_title_tv);
        mTopRightImageIv = (ImageView) view.findViewById(R.id.top_right_image_iv);
        mMineRv = (RecyclerView) view.findViewById(R.id.mine_rv);
        GridLayoutManager manager = new GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false);
        mMineRv.setLayoutManager(manager);
        adapter = new MineModeAdapter();
        mMineRv.setAdapter(adapter);
        //item点击事件
        adapter.setOnItemClickListener(new MineModeItemClickedListener() {
            @Override
            public void itemClickedListener(TextView view) {
                String text = view.getText().toString().trim();
                if (!"会员激活".equals(text)) {
                    if (!PublicUtils.ACTIVATED) {
                         PublicUtils.warnActivateDialog(context);
                        return;
                    }
                }else{
                    if (PublicUtils.ACTIVATED) {
                        Toast.makeText(context.getApplicationContext(), "您的账号已经激活，无需重复激活", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                switch (text) {
                    case "会员激活":
                        startActivity(new Intent(context, ActivateUserActivity.class));
                        break;
                    case "配送记录":
                        startActivity(new Intent(context, TransportRecordActivity.class));
                        break;
                    case "我的申请":
                        startActivity(new Intent(context, AddApplyActivity.class));
                        break;
                    case "我的积分":
                        startActivity(new Intent(context, MyIntegralActivity.class));
                        break;
                    case "我的订单":
                        PublicUtils.warnUserDialog(context);
                        break;
                    case "我的采摘":
                        PublicUtils.warnUserDialog(context);
//                        startActivity(new Intent(context, MyPickActivity.class));

                        break;
                    case "我的信息":
                        startActivity(new Intent(context, MyInfoActivity.class));
                        break;
                    case "修改密码":
                        startActivity(new Intent(context, ModifyPwdActivity.class));
                        break;
                    case "关于我们":
                        startActivity(new Intent(context, AboutUsActivity.class));

                        break;
                    case "使用帮助":
                        break;
//                    case "客户投诉":
//                        PublicUtils.warnUserDialog(context);
//                        startActivity(new Intent(context, CustomerComplainActivity.class));
//                        break;
                    case "退会":
                        startActivity(new Intent(context, QuitPetActivity.class));

                        break;
                    case "安全退出":
                        PublicUtils.warnUserExitApp(context);
                        break;
                    default:
                        break;
                }
            }
        });
    }

}
