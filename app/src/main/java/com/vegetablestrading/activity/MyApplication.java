package com.vegetablestrading.activity;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by ${王sir} on 2017/11/8.
 * application
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    private List<Activity> activityList = new LinkedList<Activity>();
    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 单例模式中获取唯一的MyApplication实例.
     * @return
     */
    public static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }
    /**
     * 添加Activity
     * @param activity
     */
    public void addActivity(Activity activity) {
        if(!activityList.contains(activity)){
            activityList.add(activity);
            Log.e("MyApplication",activityList.size()+"============");
        }

    }
    /**
     * 移除Activity
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if(activityList.contains(activity)){
            activityList.remove(activity);
            activity.finish();
            Log.e("MyApplication",activityList.size()+"============");
        }

    }

    /**
     * 遍历所有Activity并finish.
     */
    public void clearAllActivity() {
        for (Activity activity : activityList) {
            if(activity!=null){
                activity.finish();
            }
        }
    }

}
