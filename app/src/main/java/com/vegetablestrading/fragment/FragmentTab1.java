package com.vegetablestrading.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.vegetablestrading.R;
import com.vegetablestrading.activity.VegetableDescriptActivity;
import com.vegetablestrading.adapter.DividerItemDecoration;
import com.vegetablestrading.adapter.TransportListAdapter;
import com.vegetablestrading.bean.TransportVegetableInfo;
import com.vegetablestrading.interfaces.FinishActivityInterface;
import com.vegetablestrading.utils.CalendarUtil;
import com.vegetablestrading.utils.Constant;
import com.vegetablestrading.utils.DaoUtils;
import com.vegetablestrading.utils.GsonUtils;
import com.vegetablestrading.utils.PublicUtils;
import com.vegetablestrading.utils.SharedPreferencesHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;

import static com.vegetablestrading.utils.CalendarUtil.GetWeekFromDate;
import static com.vegetablestrading.utils.CalendarUtil.compareTime;
import static com.zhy.http.okhttp.OkHttpUtils.post;

/**
 * Created by ${王sir} on 2017/10/18.
 * application
 */

public class FragmentTab1 extends Fragment implements View.OnClickListener {


    private View view;
    private WebView mWebviewTab1;
    private FinishActivityInterface finishActivityInterface;
    private WebView mVegetableInfoWv;
    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private ImageView mTopRightImageIv;
    /**
     * 本周
     */
    private TextView mTradeDateTv;
    private LinearLayout mSelectedDateLl;
    /**
     * 本周不配送
     */
    private TextView mUnTradeThisWeekTv;
    private LinearLayout mDefaultTab1Ll;
    private Context mContext;
    private RecyclerView mVegetablesRv;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private TransportListAdapter adapter;
    private DaoUtils daoUtil;
    private SwipeRefreshLayout mSwipeRefreshSl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        sharedPreferencesHelper = new SharedPreferencesHelper(mContext, "TRADEABLE");
        daoUtil = new DaoUtils(mContext, "");

    }

    /**
     * 初始化按钮状态
     */
    private void initUntradeThisWeekButtonStatus() {
        if (conditionOfUnTradeThisWeek()) {
            if (sharedPreferencesHelper.getBoolean("UN_TRADE", false)) {
                mUnTradeThisWeekTv.setEnabled(false);
                mUnTradeThisWeekTv.setBackgroundResource(R.drawable.cancel_regist_shape);
                if (compareTime(CalendarUtil.getCurrentTime(), sharedPreferencesHelper.getString("THIS_DAY_WEEK", ""))) {
                    mUnTradeThisWeekTv.setEnabled(true);
                    mUnTradeThisWeekTv.setBackgroundResource(R.drawable.app_exit_shape);
                    sharedPreferencesHelper.putBoolean("UN_TRADE", false);
                }
            } else {
                mUnTradeThisWeekTv.setEnabled(true);
                mUnTradeThisWeekTv.setBackgroundResource(R.drawable.app_exit_shape);
                sharedPreferencesHelper.putBoolean("UN_TRADE", false);
            }
        } else {
            mUnTradeThisWeekTv.setBackgroundResource(R.drawable.cancel_regist_shape);
            mUnTradeThisWeekTv.setEnabled(false);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1, container, false);
        initView(view);
        initUntradeThisWeekButtonStatus();
        initActionBar();
        return view;
    }


    /**
     * 初始化actionBar
     */
    private void initActionBar() {
        mTopTitleTv.setText("配送菜品");
        mTopRightImageIv.setVisibility(View.INVISIBLE);
        mTopLeftImageIv.setVisibility(View.INVISIBLE);
    }

    //销毁Webview
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initView(View view) {
        mVegetableInfoWv = (WebView) view.findViewById(R.id.vegetable_info_wv);
        mTopLeftImageIv = (ImageView) view.findViewById(R.id.top_left_image_iv);
        mTopTitleTv = (TextView) view.findViewById(R.id.top_title_tv);
        mTopRightImageIv = (ImageView) view.findViewById(R.id.top_right_image_iv);
        mTradeDateTv = (TextView) view.findViewById(R.id.trade_date_tv);
        mSelectedDateLl = (LinearLayout) view.findViewById(R.id.selected_date_ll);
        mSelectedDateLl.setOnClickListener(this);
        mUnTradeThisWeekTv = (TextView) view.findViewById(R.id.unTradeThisWeek_tv);
        mUnTradeThisWeekTv.setOnClickListener(this);

        mDefaultTab1Ll = (LinearLayout) view.findViewById(R.id.default_tab1_ll);
        mVegetablesRv = (RecyclerView) view.findViewById(R.id.vegetables_rv);
        initDataForAdapter();
        mSwipeRefreshSl = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_sl);
        mSwipeRefreshSl.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshSl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 请求数据 更新adapter
                String tradeDate = mTradeDateTv.getText().toString().trim();
                if (tradeDate.equals("本周")) {
                    // 请求本周配送菜品信息
                    transportVegetablesByDate(CalendarUtil.getTimeOfWeekStart(), CalendarUtil.getTimeOfWeekEnd());
                } else {
                    // 请求上周配送菜品信息
                    transportVegetablesByDate(CalendarUtil.getTimeOfLastWeekStart(), CalendarUtil.getTimeOfWeekStart());
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!PublicUtils.ACTIVATED) {
            PublicUtils.warnActivateDialog(mContext);
            return;
        }
        switch (v.getId()) {
            case R.id.selected_date_ll://选择日期
                selectTradeDate().showAsDropDown(mTradeDateTv, -40, 10);
                break;
            case R.id.unTradeThisWeek_tv://本周不配送按钮点击事件
                unTradeThisWeekDialog();
                break;
        }
    }

    /**
     * 本周不配送的条件
     *
     * @return
     */
    private boolean conditionOfUnTradeThisWeek() {
        boolean clickable = false;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat form = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = calendar.getTime();
        String date_str = form.format(date);
        String week = GetWeekFromDate(date_str);
        if ("星期四".equals(week)) {
            if (CalendarUtil.compareTime(CalendarUtil.getCurrentTime(), CalendarUtil.getTimeMidOfDay())) {
                clickable = false;
            } else {
                clickable = true;
            }
        } else if ("星期五".equals(week) || "星期六".equals(week) || "星期日".equals(week)) {
            clickable = false;
        } else {
            clickable = true;
        }

        return clickable;
    }

//    /**
//     * 获取配送列表中的蔬菜信息
//     *
//     * @return
//     */
//    public ArrayList<TransportVegetableInfo> getTransportVegetables() {
//        ArrayList<TransportVegetableInfo> arrays = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            TransportVegetableInfo bean = new TransportVegetableInfo();
//            bean.setType(TransportVegetableInfo.TRANSPORT_MODE);
//            bean.setVegetableName("白菜" + i);
//            bean.setWeight("2kg");
//            bean.setVegetableInfo("描述信息faslkdjfasdjfasdjfas;dfja;slkdjfa;sljdfaslkd;jf;askjdfljasdflajs;dfljasdf");
//            bean.setTransportStartTime("2017-11-21 12:00:00");
//            bean.setTransportEndTime("2017-11-21 12:00:00");
//            arrays.add(bean);
//        }
//        return arrays;
//    }

    /**
     * 选择查看配送菜品的日期
     *
     * @return
     */
    private PopupWindow selectTradeDate() {
        String tradeDate = mTradeDateTv.getText().toString().trim();
        LayoutInflater inflater = LayoutInflater.from(mContext); // 引入窗口配置文件
        View view = inflater.inflate(R.layout.weeks, null); //
// 创建PopupWindow对象
        final TextView thisWeek = (TextView) view.findViewById(R.id.this_week_tv);
        final TextView lastWeek = (TextView) view.findViewById(R.id.last_week_tv);
        if (tradeDate.equals("本周")) {
            thisWeek.setTextColor(getResources().getColor(R.color.app_green));
            lastWeek.setTextColor(getResources().getColor(R.color.app_black));
        } else {
            lastWeek.setTextColor(getResources().getColor(R.color.app_green));
            thisWeek.setTextColor(getResources().getColor(R.color.app_black));
        }
        final PopupWindow pop = new PopupWindow(view, PublicUtils.dip2px(mContext, 80),
                PublicUtils.dip2px(mContext, 80), false);
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        thisWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thisWeek.setTextColor(getResources().getColor(R.color.app_green));
                lastWeek.setTextColor(getResources().getColor(R.color.app_black));
                mTradeDateTv.setText("本周");
                pop.dismiss();
                // 请求本周配送菜品信息
                transportVegetablesByDate(CalendarUtil.getTimeOfWeekStart(), CalendarUtil.getTimeOfWeekEnd());
            }
        });
        lastWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastWeek.setTextColor(getResources().getColor(R.color.app_green));
                thisWeek.setTextColor(getResources().getColor(R.color.app_black));
                mTradeDateTv.setText("上周");
                pop.dismiss();
                // 请求上周配送菜品信息
                transportVegetablesByDate(CalendarUtil.getTimeOfLastWeekStart(), CalendarUtil.getTimeOfWeekStart());
            }
        });
        return pop;
    }

    /**
     * 根据起始时间获取配送清单
     *
     * @param startTime
     * @param endTime
     */
    private void transportVegetablesByDate(String startTime, String endTime) {
        post()
                .url(Constant.transportVegetablesByDate_url)
                .addParams("startTime", startTime)
                .addParams("endTime", endTime)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(mContext, "网络错误", Toast.LENGTH_LONG).show();
                        mSwipeRefreshSl.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        mSwipeRefreshSl.setRefreshing(false);
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
                                    Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
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
     * 不配送提示窗口
     */
    private void unTradeThisWeekDialog() {
        View v = LayoutInflater.from(mContext).inflate(R.layout.warn_untradethisweek, null);
        final TextView comfirmUnTrade = (TextView) v.findViewById(R.id.comfirmUnTrade);
        final TextView cancelUnTrade = (TextView) v.findViewById(R.id.cancelUnTrade);
        final Dialog dialog_c = new Dialog(mContext, R.style.DialogStyle);
        dialog_c.setCanceledOnTouchOutside(false);
        dialog_c.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        dialog_c.show();
        Window window = dialog_c.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        lp.width = PublicUtils.dip2px(mContext, 300); // 宽度
        lp.height = PublicUtils.dip2px(mContext, 230); // 高度
//lp.dimAmount = 0f;//去掉对话框自带背景色
        window.setAttributes(lp);
        window.setContentView(v);
        comfirmUnTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 将不配送的信息上传到平台
                applyToService("不配送");
                //更改本周不配送按钮的状态
                mUnTradeThisWeekTv.setBackgroundResource(R.drawable.cancel_regist_shape);
                mUnTradeThisWeekTv.setEnabled(false);
                sharedPreferencesHelper.putBoolean("UN_TRADE", true);
                sharedPreferencesHelper.putString("THIS_DAY_WEEK", CalendarUtil.getTimeOfWeekEnd());
                dialog_c.dismiss();
            }
        });
        cancelUnTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_c.dismiss();
            }
        });
    }
    /**
     * 上传申请信息
     * @param noteInfo
     */
    private void applyToService( String noteInfo) {
        //1代表减少配送，2代表增加配送，3代表不配送
        OkHttpUtils
                .post()
                .url(Constant.applyOfTransport_url)
                .addParams("userId", PublicUtils.userInfo.getUserId())
                .addParams("applyType", "3")
                .addParams("applyInfo", noteInfo)
                .addParams("startTime", CalendarUtil.getTimeOfWeekStart())
                .addParams("endTime", CalendarUtil.getTimeOfWeekEnd())
                .addParams("apply_time", CalendarUtil.getCurrentTime())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(mContext, "", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                String result = obj.getString("Result");
                                String message = obj.getString("Message");
                                if ("Ok".equals(result)) {
                                    Toast.makeText(mContext, "您已成功提交申请", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
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
        daoUtil.deleteAllEntity(TransportVegetableInfo.class);
        daoUtil.insertMultEntity(arrayList);

    }

    /**
     * 初始化adapter数据
     */
    private void initDataForAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mVegetablesRv.setLayoutManager(linearLayoutManager);
        adapter = new TransportListAdapter(mContext);
        mVegetablesRv.setAdapter(adapter);
        mVegetablesRv.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.HORIZONTAL, R.drawable.horizontal_line_grey));

        adapter.setOnTransportListItemClickListener(new TransportListAdapter.OnTransportListItemInterface() {
            @Override
            public void OnTransportListItemClick(TransportVegetableInfo transportVegetableInfo) {
                if (!PublicUtils.ACTIVATED) {
                    PublicUtils.warnActivateDialog(mContext);
                    return;
                }
                PublicUtils.transportVegetableInfo = transportVegetableInfo;
                startActivity(new Intent(mContext, VegetableDescriptActivity.class));
            }
        });
        if (PublicUtils.isConnected(mContext)) {
            // 请求本周配送菜品信息
            transportVegetablesByDate(CalendarUtil.getTimeOfWeekStart(), CalendarUtil.getTimeOfWeekEnd());
        } else {//没有网络的情况下读取数据库里面的数据
            adapter.setData(daoUtil.listAll(TransportVegetableInfo.class));
        }
    }

}
