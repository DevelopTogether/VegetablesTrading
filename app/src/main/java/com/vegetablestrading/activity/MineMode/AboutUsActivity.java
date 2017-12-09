package com.vegetablestrading.activity.MineMode;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.checktoupdatedemo.utils.CheckUpdateUtil;
import com.vegetablestrading.R;
import com.vegetablestrading.customViews.CustomView;
import com.vegetablestrading.utils.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;


public class AboutUsActivity extends AppCompatActivity implements View.OnClickListener {

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
                getApkDownLoadUrl();
                break;
        }
    }

    /**
     * 获取apk的下载路径
     */
    private void getApkDownLoadUrl() {
        OkHttpUtils
                .post()
                .url(Constant.checkToUpdate_url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call cll, Exception e, int id) {
                        Toast.makeText(AboutUsActivity.this, "网络错误", Toast.LENGTH_LONG).show();
//                        adapter.setData(daoUtil.listAll(TransportVegetableInfo.class));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                String result = obj.getString("Result");
                                String softVersion = obj.getString("softwareVersion");
                                String downloadUrl = obj.getString("downloadUrl");
                                if ("Ok".equals(result)) {
                                    CheckUpdateUtil checkUpdateUtil = new CheckUpdateUtil(AboutUsActivity.this, "2.0", Constant.apkDownLoadServerUrl, Constant.downloadApkPath, Constant.appVersionDescription, Constant.unUpdateNotice);
                                    if (!checkUpdateUtil.CheckVersionToWarnUpdate()) {
                                        Toast.makeText(getApplicationContext(), "当前版本已是最新版本", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(AboutUsActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.e("DEBUG", response);

                    }

                });
    }
}
