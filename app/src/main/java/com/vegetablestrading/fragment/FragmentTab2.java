package com.vegetablestrading.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.vegetablestrading.R;
import com.vegetablestrading.adapter.DividerItemDecoration;
import com.vegetablestrading.adapter.ShopListAdapter;
import com.vegetablestrading.adapter.VegetableTypeAdapter;
import com.vegetablestrading.bean.TransportVegetableInfo;
import com.vegetablestrading.customViews.SearchView;
import com.vegetablestrading.interfaces.FinishActivityInterface;
import com.vegetablestrading.utils.DaoUtils;
import com.vegetablestrading.utils.PublicUtils;

import java.util.ArrayList;

/**
 * Created by ${王sir} on 2017/10/18.
 * application
 */

public class FragmentTab2 extends Fragment implements View.OnClickListener {
    private View view;
    private FinishActivityInterface finishActivityInterface;
    private WebView mShopVegetableInfoWv;
    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private ImageView mTopRightImageIv;
    /**
     * 价格最低
     */
    private TextView mPricePriorityTv;
    private LinearLayout mPricePriorityLl;
    /**
     * 销量优先
     */
    private TextView mSalesPriorityTv;
    private RecyclerView mShopVegetablesRv;
    private LinearLayout mDefaultTab1Ll;
    private Context mContext;
    private boolean salesClicked = false;//销量优先被点击了
    private PopupWindow popupWindow;
    private PopupWindow popupWindow_types;
    private SearchView mShopSearchSv;
    private ShopListAdapter adapter;
    private DaoUtils daoUtil;
    private SwipeRefreshLayout mSwipeRefreshSl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        daoUtil = new DaoUtils(mContext, "shop_vagetable_info.sqlite");
        putTransportVegetableInfoToSqlite();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2, container, false);
        initView(view);
        initActionBar();
        return view;
    }

    /**
     * 初始化actionBar
     */
    private void initActionBar() {
        mTopTitleTv.setText("蔬菜商城");
        mTopRightImageIv.setVisibility(View.INVISIBLE);
        mTopLeftImageIv.setImageResource(R.drawable.menu_icon);
    }

    public void setFinishActivity(FinishActivityInterface finishActivityInterface) {
        this.finishActivityInterface = finishActivityInterface;
    }
//    private void initView(View view) {
//        mWebviewTab2 = (WebView) view.findViewById(R.id.webview_tab2);
//        mWebviewTab2.loadUrl("http://byu2605480001.my3w.com/index.php/Mobile/Goods/categoryList.html");
//
//
//        //设置不用系统浏览器打开,直接显示在当前Webview
//        mWebviewTab2.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
//    }

//    //点击返回上一页面而不是退出浏览器
//
//
//    public void onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && mWebviewTab2.canGoBack()) {
//            mWebviewTab2.goBack();
//        }else{
//            if (finishActivityInterface!=null) {
//                finishActivityInterface.finishActivity();
//            }
//        }
//
//    }

    //销毁Webview
    @Override
    public void onDestroy() {
//        if (mWebviewTab2 != null) {
//            mWebviewTab2.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
//            mWebviewTab2.clearHistory();
//
//            ((ViewGroup) mWebviewTab2.getParent()).removeView(mWebviewTab2);
//            mWebviewTab2.destroy();
//            mWebviewTab2 = null;
//        }
        super.onDestroy();
    }

    private void initView(View view) {
        mShopVegetableInfoWv = (WebView) view.findViewById(R.id.shop_vegetable_info_wv);
        mTopLeftImageIv = (ImageView) view.findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) view.findViewById(R.id.top_title_tv);
        mTopRightImageIv = (ImageView) view.findViewById(R.id.top_right_image_iv);
        mPricePriorityTv = (TextView) view.findViewById(R.id.price_priority_tv);
        mPricePriorityTv.setText("价格最低");
        if (!salesClicked) {
            mPricePriorityTv.setTextColor(getResources().getColor(R.color.app_green));
        }
        mPricePriorityLl = (LinearLayout) view.findViewById(R.id.price_priority_ll);
        mPricePriorityLl.setOnClickListener(this);
        mSalesPriorityTv = (TextView) view.findViewById(R.id.sales_priority_tv);
        mSalesPriorityTv.setOnClickListener(this);
        mShopVegetablesRv = (RecyclerView) view.findViewById(R.id.shop_vegetables_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mShopVegetablesRv.setLayoutManager(linearLayoutManager);
        adapter = new ShopListAdapter(mContext);
        adapter.setShopListItemClick(new ShopListAdapter.ShopListItemClick() {

            @Override
            public void itemClick(TransportVegetableInfo transportVegetableInfo) {
                if (!PublicUtils.ACTIVATED) {
                    PublicUtils.warnActivateDialog(mContext);
                    return;
                }
            }

            @Override
            public void buyRightnow() {
                if (!PublicUtils.ACTIVATED) {
                    PublicUtils.warnActivateDialog(mContext);
                    return;
                }
            }
        });
        mShopVegetablesRv.setAdapter(adapter);
        mShopVegetablesRv.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.HORIZONTAL, R.drawable.horizontal_line_grey));
        initDataForAdapter();
        mDefaultTab1Ll = (LinearLayout) view.findViewById(R.id.default_tab1_ll);
        mShopSearchSv = (SearchView) view.findViewById(R.id.shop_search_sv);
        mShopSearchSv.setSearchVegetableCallBack(new SearchView.SearchViewOnClick() {
            @Override
            public void searchVegetables() {//查询相关蔬菜
                if (!PublicUtils.ACTIVATED) {
                    PublicUtils.warnActivateDialog(mContext);
                    return;
                }
                //TODO 根据搜索内容请求相关蔬菜信息
                Toast.makeText(mContext, "开始搜寻...", Toast.LENGTH_LONG).show();
            }
        });
        mSwipeRefreshSl = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_sl);
        mSwipeRefreshSl.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshSl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO 请求数据 更新adapter
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mSwipeRefreshSl.setRefreshing(false);

            }
        });
    }

    /**
     * 获取配送列表中的蔬菜信息
     *
     * @return
     */
    public ArrayList<TransportVegetableInfo> getTransportVegetables() {
        ArrayList<TransportVegetableInfo> arrays = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TransportVegetableInfo bean = new TransportVegetableInfo();
            bean.setType(TransportVegetableInfo.TRANSPORT_MODE);
            bean.setVegetableName("白菜" + i);
            bean.setVegetablePrice("12.5/kg");
            bean.setVegetableInfo("描述信息faslkdjfasdjfasdjfas;dfja;slkdjfa;sljdfaslkd;jf;askjdfljasdflajs;dfljasdf");
            bean.setTransportStartTime("2017-11-21 12:00:00");
            bean.setTransportEndTime("2017-11-21 12:00:00");
            arrays.add(bean);
        }
        return arrays;
    }

    @Override
    public void onClick(View v) {
        if (!PublicUtils.ACTIVATED) {
            PublicUtils.warnActivateDialog(mContext);
            return;
        }
        switch (v.getId()) {
            case R.id.top_left_image_iv:
                if (popupWindow_types == null) {
                    popupWindow_types = getVegetableTypesDialog();
                }

                popupWindow_types.showAsDropDown(v, -30, 40);
                break;
            case R.id.price_priority_ll:
                popupWindow = selectFilterCondition();
                popupWindow.showAsDropDown(v, -50, 60);

                break;
            case R.id.sales_priority_tv:
                salesClicked = true;
                mSalesPriorityTv.setTextColor(getResources().getColor(R.color.app_green));
                mPricePriorityTv.setTextColor(getResources().getColor(R.color.app_black));
                //TODO 根据销量优先的条件请求蔬菜信息
                break;
        }
    }

    /**
     * 选择筛选的条件（价格高低）
     *
     * @return
     */
    private PopupWindow selectFilterCondition() {
        String tradeDate = mPricePriorityTv.getText().toString().trim();
        LayoutInflater inflater = LayoutInflater.from(mContext); // 引入窗口配置文件
        View view = inflater.inflate(R.layout.weeks, null); //
// 创建PopupWindow对象
        final TextView thisWeek = (TextView) view.findViewById(R.id.this_week_tv);
        thisWeek.setText("价格最低");

        final TextView lastWeek = (TextView) view.findViewById(R.id.last_week_tv);
        lastWeek.setText("价格最高");
        if (!salesClicked) {
            if (tradeDate.equals("价格最低")) {
                thisWeek.setTextColor(getResources().getColor(R.color.app_green));
                lastWeek.setTextColor(getResources().getColor(R.color.app_black));
            } else {
                lastWeek.setTextColor(getResources().getColor(R.color.app_green));
                thisWeek.setTextColor(getResources().getColor(R.color.app_black));
            }
        } else {
            thisWeek.setTextColor(getResources().getColor(R.color.app_black));
            lastWeek.setTextColor(getResources().getColor(R.color.app_black));
        }

        final PopupWindow pop = new PopupWindow(view, PublicUtils.dip2px(mContext, 100),
                PublicUtils.dip2px(mContext, 80), false);
        pop.setOutsideTouchable(true);
        thisWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salesClicked = false;
                thisWeek.setTextColor(getResources().getColor(R.color.app_green));
                lastWeek.setTextColor(getResources().getColor(R.color.app_black));
                mPricePriorityTv.setText("价格最低");
                mSalesPriorityTv.setTextColor(getResources().getColor(R.color.app_black));
                mPricePriorityTv.setTextColor(getResources().getColor(R.color.app_green));
                pop.dismiss();
                //TODO 按价格从低到高的条件请求菜品信息
            }
        });
        lastWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salesClicked = false;

                lastWeek.setTextColor(getResources().getColor(R.color.app_green));
                thisWeek.setTextColor(getResources().getColor(R.color.app_black));
                mPricePriorityTv.setText("价格最高");
                mSalesPriorityTv.setTextColor(getResources().getColor(R.color.app_black));
                mPricePriorityTv.setTextColor(getResources().getColor(R.color.app_green));

                pop.dismiss();
                //TODO 按价格从高到低的条件请求菜品信息
            }
        });
        return pop;
    }

    /**
     * 展示蔬菜类型列表
     *
     * @return
     */
    private PopupWindow getVegetableTypesDialog() {
        LayoutInflater inflater = LayoutInflater.from(mContext); // 引入窗口配置文件
        View view = inflater.inflate(R.layout.vegetable_types_layout, null); //
        RecyclerView master_type_rv = (RecyclerView) view.findViewById(R.id.master_type_rv);
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        master_type_rv.setLayoutManager(manager);
        VegetableTypeAdapter adapter = new VegetableTypeAdapter();
        adapter.setData(getDataOfVegetableTypes());
        master_type_rv.setAdapter(adapter);
        adapter.setOnVegetableTypeItemClick(new VegetableTypeAdapter.VegetableTypeItemClickInterface() {
            @Override
            public void OnVegetableTypeItemClick(String vegetableType) {
                //TODO 通过关键字（蔬菜类型）请求对应蔬菜
                Toast.makeText(mContext, "您点击了======" + vegetableType, Toast.LENGTH_LONG).show();
            }
        });
        master_type_rv.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.HORIZONTAL, R.drawable.horizontal_line));
        // 创建PopupWindow对象
        final PopupWindow pop = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
                PublicUtils.app_height * 5 / 7, false);

        pop.setOutsideTouchable(false);
        pop.setFocusable(true);
        return pop;
    }

    /**
     * 获取蔬菜种类列表
     *
     * @return
     */
    private ArrayList<String> getDataOfVegetableTypes() {
        ArrayList<String> arrays = new ArrayList<>();
        String a = "叶菜类&白菜类,绿叶菜类,韭类,青萝卜类";

        String c = "根茎类&根类,茎类";
        String d = "瓜类与茄果类3&瓜类,茄果类";
        String e = "县豆类&大豆类,菜豆类,韭类,青萝卜类,红萝卜类";
        String h = "县豆类&大豆类,菜豆类,韭类,青萝卜类,红萝卜类";
        String i = "县豆类&大豆类,菜豆类,韭类,青萝卜类,红萝卜类";
        String f = "菌类&蘑菇类";
        arrays.add(a);
        arrays.add(c);
        arrays.add(d);
        arrays.add(e);
        arrays.add(f);
        arrays.add(h);
        arrays.add(i);
        return arrays;
    }

    /**
     * 将配送蔬菜信息保存本地
     */
    private void putTransportVegetableInfoToSqlite() {
        daoUtil.deleteAllEntity(TransportVegetableInfo.class);
        daoUtil.insertMultEntity(getTransportVegetables());

    }

    /**
     * 初始化adapter数据
     */
    private void initDataForAdapter() {
        if (PublicUtils.isConnected(mContext)) {
            adapter.setData( daoUtil.listAll(TransportVegetableInfo.class));
            //TODO 从服务端请求配送蔬菜列表信息
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

        } else {//没有网络的情况下读取数据库里面的数据
            adapter.setData( daoUtil.listAll(TransportVegetableInfo.class));
        }
    }
}
