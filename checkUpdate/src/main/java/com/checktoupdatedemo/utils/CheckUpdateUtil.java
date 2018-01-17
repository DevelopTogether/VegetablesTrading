package com.checktoupdatedemo.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.checktoupdatedemo.R;
import com.checktoupdatedemo.adapter.CheckUpdateDialogAdapter;
import com.checktoupdatedemo.customView.UpdateProgressDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * Author:wang_sir
 * Time:2017/11/24 16:23
 * Description:This is CheckUpdateUtil
 */
public class CheckUpdateUtil {
    private  Context context;
    private final SharedPreferences sharedPreferences;
    private UpdateProgressDialog mProgressDialog;//自定义progressDialog
    private String downloadApkUrl;//服务端apk包的路径
    private String nearestVersion;//最新的软件版本serverUrl
    private String appVersionDescription;//最新软件版本特征描述
    private  String serverUrl;//服务端地址
    private String dialogNotice = "";//对话框的提示语，两种，一种是稍后提示，一种是其他自定义词语

    public CheckUpdateUtil(Context context, String nearestVersion,String serverUrl, String downloadApkUrl, String appVersionDescription,String  dialogNotice) {
        this.context = context;
        this.nearestVersion = nearestVersion;
        this.serverUrl = serverUrl;
        this.downloadApkUrl=downloadApkUrl;
        this.appVersionDescription=appVersionDescription;
        this.dialogNotice = dialogNotice;
        sharedPreferences = context.getSharedPreferences("NEXTWARNTIME", MODE_PRIVATE);
    }

    public boolean CheckVersionToWarnUpdate() {
        if(this.compareTwoVersionSize(this.getAPPVersion(), this.nearestVersion)) {
            if("稍后提示".equals(this.dialogNotice)) {
                if(!this.IsTheTimeToWarnUpdate()) {
                    return false;
                }

                this.WarnUpgradeDialog(this.appVersionDescription);
            } else {
                this.WarnUpgradeDialog(this.appVersionDescription);
            }

            return true;
        } else {
            if("稍后提示".equals(this.dialogNotice)) {
                SharedPreferences.Editor et = this.sharedPreferences.edit();
                et.putString("nextTime", "");
                et.commit();
            }

            return false;
        }
    }

    /**
     * 获取软件版本号
     */
   private String getAPPVersion() {
        PackageManager pm = context.getPackageManager();//得到PackageManager对象
        String version_app = "";
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);//得到PackageInfo对象，封装了一些软件包的信息在里面
            version_app = pi.versionName;//获取清单文件中versionCode节点的值
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version_app;
    }

    /**
     * 通过软件的版本名称判定是否升级
     *
     * @param localVersionName  本地软件的版本名称
     * @param serverVersionName 服务端软件的版本名称
     * @return
     */
    private boolean compareTwoVersionSize(String localVersionName, String serverVersionName) {
        if (TextUtils.isEmpty(localVersionName) || TextUtils.isEmpty(serverVersionName)) {
            return false;
        }
        if (!serverVersionName.contains(".")) {
            return false;
        }
        String local3 = "0";
        String server3 = "0";
        String[] localVersion = localVersionName.split("\\.");
        String[] serverVersion = serverVersionName.split("\\.");
        String local1 = localVersion[0];
        String local2 = localVersion[1];
        if (localVersion.length == 3) {
            local3 = localVersion[2];
        }
        String server1 = serverVersion[0];
        String server2 = serverVersion[1];
        if (serverVersion.length == 3) {
            server3 = serverVersion[2];
        }
        if (Integer.parseInt(server1) > Integer.parseInt(local1)) {
            return true;
        }
        if (Integer.parseInt(server2) > Integer.parseInt(local2)) {
            return true;
        }
        if (Integer.parseInt(server3) > Integer.parseInt(local3)) {
            return true;
        }
        return false;
    }

    /**
     * 判定是否提醒升级
     */
    private boolean IsTheTimeToWarnUpdate() {

        final String time = sharedPreferences.getString("nextTime", "");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String time2 = new SimpleDateFormat("yyyy-MM-dd").format(date);
        if (TextUtils.isEmpty(time)) {
            return true;
        } else {
            if (compareTwoTimeSize(time2, time)) {
                return true;
            } else {
                return false;
            }
        }


    }

    /**
     * 比较两个时间串的大小
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    private boolean compareTwoTimeSize(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Long a = sdf.parse(startTime).getTime();
            Long b = sdf.parse(endTime).getTime();
            if (a > b || a.equals(b)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 提醒升级的对话框
     * @param description 对话框的描述信息
     */
    private void WarnUpgradeDialog( String description) {

        View v = LayoutInflater.from(context).inflate(R.layout.warn_update_dialog, null);
        final Dialog dialog_warnUpdate = new Dialog(context, R.style.DialogStyle);
        dialog_warnUpdate.setCanceledOnTouchOutside(false);
        dialog_warnUpdate.setCancelable(false);
        dialog_warnUpdate.show();
        Window window = dialog_warnUpdate.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        lp.width = dip2px(context, 300); // 宽度
        lp.height = dip2px(context, 260); // 高度
        // lp.alpha = 0.7f; // 透明度
        window.setAttributes(lp);
        window.setContentView(v);
        ListView feature_lv = (ListView) v.findViewById(R.id.version_description_lv);
        feature_lv.setDivider(null);
        feature_lv.setAdapter(new CheckUpdateDialogAdapter(context, description));
        dialog_warnUpdate.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        dialog_warnUpdate.dismiss();
                    }
                }
                return false;
            }
        });
        final TextView update_rightnow_tv = (TextView) v.findViewById(R.id.update_rightnow_tv);
        final TextView update_later_tv = (TextView) v.findViewById(R.id.update_later_tv);
        update_later_tv.setText(dialogNotice);
        update_rightnow_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_warnUpdate.dismiss();
                downAPKfromService();
            }
        });
        update_later_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_warnUpdate.dismiss();
                if ("稍后提示".equals(dialogNotice)) {
                    String nextTime = GetNextWarnTime(7);
                    SharedPreferences.Editor et = sharedPreferences.edit();
                    et.putString("nextTime", nextTime);
                    et.commit();
                }

            }
        });
    }

    /**
     * 从服务器下载apk
     */
    private void downAPKfromService() {

        if (isNetworkConnected(context)) {
            mProgressDialog = new UpdateProgressDialog(context);
            mProgressDialog.setMessage("正在下载");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
            final DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute(downloadApkUrl);
            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    downloadTask.cancel(true);
                }
            });
            //downFile(url);
        }

    }
    // 判断网络是否正常

    public static boolean isNetworkConnected(Context context) {
        boolean isOk = true;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobNetInfo = connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetInfo = connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiNetInfo != null && !wifiNetInfo.isConnectedOrConnecting()) {
                if (mobNetInfo != null && !mobNetInfo.isConnectedOrConnecting()) {
                    NetworkInfo info = connectivityManager
                            .getActiveNetworkInfo();
                    if (info == null) {
                        isOk = false;
                    }
                }
            }
            mobNetInfo = null;
            wifiNetInfo = null;
            connectivityManager = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOk;
    }


    /**
     * 获取下次提醒的时间,day天后
     */

    private String GetNextWarnTime(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        Date date = calendar.getTime();
        String time = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return time;
    }


    /**
     * 下载的异步任务类
     */
    private class DownloadTask extends AsyncTask<String, Integer, String> {


        //执行异步任务（doInBackground）之前执行，并且在ui线程中执行
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //开始下载 对话框进度条显示
            mProgressDialog.show();
            mProgressDialog.setProgress(0);
        }

        @Override
        protected String doInBackground(String... params) {
            int i = 0;
            String uri = serverUrl + params[0];
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(uri);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file


                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }
                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();
                mProgressDialog.setMax(fileLength);
                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(GetAPKPath());
                byte data[] = new byte[4096];
                int total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
//					if (fileLength > 0) // only if total length is known
//					{
//						i = (int) (total * 100 / fileLength);
//					}
                    mProgressDialog.setProgress(total);
//					publishProgress(i);
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null) {
                        output.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException ignored) {
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return "right";
        }

        //在ui线程中执行 可以操作ui
        @Override
        protected void onPostExecute(String string) {
            // TODO Auto-generated method stub
            super.onPostExecute(string);
            //下载完成 对话框进度条隐藏
            if (string.equals("right")) {
                mProgressDialog.cancel();
                Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
                installApk();
            } else {
                mProgressDialog.cancel();
                Toast.makeText(context, "下载失败", Toast.LENGTH_LONG).show();
            }


        }

        /*
         * 在doInBackground方法中已经调用publishProgress方法 更新任务的执行进度后
         * 调用这个方法 实现进度条的更新
         * */
        @Override
        protected void onProgressUpdate(Integer... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);

        }
    }

    /**
     * 安装APK
     */
    private void installApk() {
        File file = new File(GetAPKPath());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 定义下载包存储路径
     */
    private String GetAPKPath() {
        File file = new File("/mnt/sdcard/.toInstallPG");
        if (!file.exists()) {
            file.mkdir();
        }
        String path = "/mnt/sdcard/.toInstallPG" + "/" + getAPPName() + nearestVersion + ".apk";
        return path;
    }

    /**
     * 获取软件名称
     */
    public String getAPPName() {
        String appName = "";
        PackageManager pm = context.getPackageManager();//得到PackageManager对象
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo(context.getPackageName(), 0);
            appName = (String) pm.getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appName;
    }

    /**
     * 将dp转换为px
     * @param context
     * @param dpValue
     * @return
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
