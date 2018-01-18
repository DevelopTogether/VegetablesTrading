package com.vegetablestrading;

import android.app.Activity;
import android.app.Application;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.uploadbugs.utils.CrashHandler;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by ${王sir} on 2017/11/8.
 * application
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    private ArrayMap arrayMap = new ArrayMap();
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
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
        String key = activity.getLocalClassName();
        if(!arrayMap.containsKey(key)){
            arrayMap.put(activity.getLocalClassName(),activity);
            Log.e("MyApplication",arrayMap.size()+"======add======"+key);
        }else{
            activity.finish();
        }

    }
    /**
     * 移除Activity
     * @param activity
     */
    public void removeActivity(Activity activity) {
        String key = activity.getLocalClassName();
        if(arrayMap.containsKey(key)){
            arrayMap.remove(key);
            activity.finish();
            Log.e("MyApplication",arrayMap.size()+"=======remove====="+key);
        }

    }

    /**
     * 遍历所有Activity并finish.
    */
    public void clearAllActivity() {
        Iterator it = arrayMap.keySet().iterator();
        while (it.hasNext()){
            String key = (String) it.next();
            Activity activity = (Activity) arrayMap.get(key);
            activity.finish();
            Log.e("MyApplication",arrayMap.size()+"=======finish====="+key);
        }
    }

}
