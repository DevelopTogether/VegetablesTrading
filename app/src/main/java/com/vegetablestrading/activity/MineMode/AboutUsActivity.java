package com.vegetablestrading.activity.MineMode;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.checktoupdatedemo.utils.CheckUpdateUtil;
import com.vegetablestrading.R;
import com.vegetablestrading.activity.BaseActivity;
import com.vegetablestrading.customViews.CustomView;
import com.vegetablestrading.utils.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;


public class AboutUsActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTestTv;
    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private ImageView mTopRightImageIv;
    /**
     * 当前版本号:1.0.0
     */
    private TextView mVersionTv;
    private CustomView mCheckUpdateCv;
    private CustomView mWebUrlCv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initView();
    }

    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);
        mTopTitleTv.setText("关于我们");
        mTopRightImageIv = (ImageView) findViewById(R.id.top_right_image_iv);
        mVersionTv = (TextView) findViewById(R.id.version_tv);
        mVersionTv.setText("当前版本号:"+getAPPVersion());
        mCheckUpdateCv = (CustomView) findViewById(R.id.check_update_cv);
        mCheckUpdateCv.setTitleBarRightBtnIconSize(70, 70);
        mWebUrlCv = (CustomView) findViewById(R.id.web_url_cv);
        mWebUrlCv.getTitleBarRightBtn().setTextColor(getResources().getColor(R.color.app_green));
        mWebUrlCv.getTitleBarRightBtn().setText(Constant.company_url);
        mWebUrlCv.setOnClickListener(this);
        mCheckUpdateCv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_image_iv:
                finish();
                break;
            case R.id.web_url_cv://官方网址
                Uri uri = Uri.parse(Constant.company_url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.check_update_cv://检查更新
                getApkDownLoadUrl(this);
                break;
        }
    }
    /**
     * 获取软件版本号
     */
    private String getAPPVersion() {
        PackageManager pm =getPackageManager();//得到PackageManager对象
        String version_app = "";
        try {
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);//得到PackageInfo对象，封装了一些软件包的信息在里面
            version_app = pi.versionName;//获取清单文件中versionCode节点的值
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version_app;
    }
    /**
     * 获取apk的下载路径
     */
    private void getApkDownLoadUrl(final Context context) {

        OkHttpUtils
                .post()
                .addParams("softwareId", Constant.APP_MARK)
                .addParams("softwareType", "mb")
                .url(Constant.URL_Reg_Center)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call cll, Exception e, int id) {
                        Toast.makeText(context, "网络错误", Toast.LENGTH_LONG).show();
//                        adapter.setData(daoUtil.listAll(TransportVegetableInfo.class));
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        if (response != null && !TextUtils.isEmpty(response)) {

                            try {
                                JSONObject obj = new JSONObject(response);
                                JSONArray infos = obj.getJSONArray("Model");
                                if (infos.length() > 0) {
                                    JSONObject obj_ = (JSONObject) infos.get(0);
                                    String nearestVersion = obj_.getString("SoftwareVersion").trim();
                                    String down_url = obj_.getString("softDownloadUrl");
                                    String appDescription = obj_.getString("softDescription");
                                    CheckUpdateUtil checkUpdateUtil = new CheckUpdateUtil(context, nearestVersion, Constant.apkDownLoadServerUrl, down_url, appDescription, "取消");
                                    if (!checkUpdateUtil.CheckVersionToWarnUpdate()) {
                                        Toast.makeText(getApplicationContext(), "当前版本已是最新版本", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(context, "服务器上查不到该软件", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                });

    }
}
