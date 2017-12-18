package com.vegetablestrading.activity.MineMode.myApply;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vegetablestrading.R;
import com.vegetablestrading.activity.BaseActivity;
import com.vegetablestrading.utils.CalendarUtil;
import com.vegetablestrading.utils.Constant;
import com.vegetablestrading.utils.PublicUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;

import static com.vegetablestrading.utils.CalendarUtil.GetUploadTime;
import static com.vegetablestrading.utils.CalendarUtil.GetWeekFromDate;
import static com.vegetablestrading.utils.CalendarUtil.compareTimeOfAddApply;

/**
 * 添加申请
 */
public class AddApplyActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private ImageView mTopRightImageIv;
    /**
     * 年月日
     */
    private TextView mStartTimeTv;
    private RelativeLayout mStartTimeRl;
    private DatePicker mStartDp;
    /**
     * 年月日
     */
    private TextView mEndTimeTv;
    private RelativeLayout mEndTimeRl;
    private DatePicker mEndDp;
    /**
     * 确    定
     */
    private TextView confirm_apply_tv;
    private LinearLayout mCustomContentLl;
    private int yearStart;
    private int monthStart;
    private int dayStart;
    private int year_end;
    private int month_end;
    private int day_end;
    private String date_start_str;
    private String date_start_str_upload;//上传参数：开始时间
    private String date_end_str;
    private String date_end_str_upload;//上传参数：结束时间
    private View mLineEndTime;
    private RadioGroup mApplyTypeRg;
    /**
     * 至少输入5个字，最多50个字
     */
    private EditText mNoteInfoEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apply);
        initView();
        initDatePicker();


    }


    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);
        mTopTitleTv.setText("我的申请");
        mTopRightImageIv = (ImageView) findViewById(R.id.top_right_image_iv);
        mTopRightImageIv.setVisibility(View.VISIBLE);
        mTopRightImageIv.setImageResource(R.drawable.apply_record_icon);
        mTopRightImageIv.setOnClickListener(this);
        mStartTimeTv = (TextView) findViewById(R.id.start_time_tv);
        mStartTimeRl = (RelativeLayout) findViewById(R.id.start_time_rl);
        mStartTimeRl.setOnClickListener(this);
        mStartDp = (DatePicker) findViewById(R.id.start_dp);
        mEndTimeTv = (TextView) findViewById(R.id.end_time_tv);
        mEndTimeRl = (RelativeLayout) findViewById(R.id.end_time_rl);
        mEndTimeRl.setOnClickListener(this);
        mEndDp = (DatePicker) findViewById(R.id.end_dp);
        confirm_apply_tv = (TextView) findViewById(R.id.confirm_apply_tv);
        confirm_apply_tv.setOnClickListener(this);
        mCustomContentLl = (LinearLayout) findViewById(R.id.custom_content_ll);
        mLineEndTime = (View) findViewById(R.id.line_end_time);
        mApplyTypeRg = (RadioGroup) findViewById(R.id.apply_type_rg);
        mNoteInfoEt = (EditText) findViewById(R.id.note_info_et);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_image_iv:
                finish();
                break;
            case R.id.top_right_image_iv:
                startActivity(new Intent(this,MyApplyRecordActivity.class));
                break;
            case R.id.start_time_rl:
                changeDatePickerStatus(mStartDp, mEndDp);
                mLineEndTime.setVisibility(View.INVISIBLE);
                break;
            case R.id.end_time_rl:
                changeDatePickerStatus(mEndDp, mStartDp);
                mLineEndTime.setVisibility(View.VISIBLE);
                break;
            case R.id.confirm_apply_tv:
                date_start_str_upload=  GetUploadTime(date_start_str,"start");
                date_end_str_upload =  GetUploadTime(date_end_str,"end");
                String noteInfo = mNoteInfoEt.getText().toString().trim();
                if (TextUtils.isEmpty(noteInfo)) {
                    Toast.makeText(getApplicationContext(), "备注信息不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                //向服务器上传申请信息
                applyToService(noteInfo);

                break;
        }
    }

    /**
     * 上传申请信息
     * @param noteInfo
     */
    private void applyToService( String noteInfo) {

        OkHttpUtils
                .post()
                .url(Constant.applyOfTransport_url)
                .addParams("userId", PublicUtils.userInfo.getUserId())
                .addParams("applyType", getApplyType())
                .addParams("applyInfo", noteInfo)
                .addParams("startTime", date_start_str_upload)
                .addParams("endTime", date_end_str_upload)
                .addParams("apply_time", CalendarUtil.getCurrentTime())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                String result = obj.getString("Result");
                                String message = obj.getString("Message");
                                if ("Ok".equals(result)) {
                                    Toast.makeText(getApplicationContext(), "您已成功提交申请", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
     * 初始化日期
     */
    private void initCalender() {
        Calendar calendar_end = Calendar.getInstance();
        calendar_end.add(Calendar.DAY_OF_YEAR, 7);
        Date date_end = calendar_end.getTime();
        SimpleDateFormat form = new SimpleDateFormat("yyyy年MM月dd日");
        String ymd = form.format(date_end);
        date_end_str = ymd;
        String week = GetWeekFromDate(ymd);
        mEndTimeTv.setText(ymd + " " + week);
        year_end = calendar_end.get(Calendar.YEAR);
        month_end = calendar_end.get(Calendar.MONTH);
        day_end = calendar_end.get(Calendar.DAY_OF_MONTH);

        Calendar calendar_start = Calendar.getInstance();
        calendar_start.add(Calendar.MONTH, 0);
        Date date_start = calendar_start.getTime();
        String ymd_start = form.format(date_start);
        date_start_str = ymd_start;
        String week_start = GetWeekFromDate(ymd_start);
        mStartTimeTv.setText(ymd_start + " " + week_start);
        yearStart = calendar_start.get(Calendar.YEAR);
        monthStart = calendar_start.get(Calendar.MONTH);
        dayStart = calendar_start.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 初始化DatePicker
     */
    private void initDatePicker() {
        initCalender();
        mStartDp.init(yearStart, monthStart, dayStart, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String str_start = getStringTypeOfDate(year, monthOfYear + 1, dayOfMonth);
                mStartTimeTv.setText(str_start);
                date_start_str = new StringBuffer().append(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日").toString();
                initCustomConfirmBtStatus();
            }
        });
        mEndDp.init(year_end, month_end, day_end, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String str_end = getStringTypeOfDate(year, monthOfYear + 1, dayOfMonth);
                date_end_str = new StringBuffer().append(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日").toString();
                mEndTimeTv.setText(str_end);
                initCustomConfirmBtStatus();
            }
        });
    }

    /**
     * 改变两个DatePicker的状态
     *
     * @param displayDP
     * @param unDisplayDp
     */
    private void changeDatePickerStatus(DatePicker displayDP, DatePicker unDisplayDp) {
        displayDP.setVisibility(View.VISIBLE);
        unDisplayDp.setVisibility(View.GONE);
    }

    /**
     * 将年月日改成特定格式的字符串
     */
    private String getStringTypeOfDate(int year, int month, int day) {
        StringBuffer sb = new StringBuffer();
        if (month < 10) {
            sb.append(year + "年" + "0" + month + "月" + day + "日");
        } else {
            sb.append(year + "年" + month + "月" + day + "日");
        }
        String week = GetWeekFromDate(sb.toString());
        sb.append(" " + week);
        return sb.toString();
    }

    /**
     * 初始化自定义栏中确定按钮的状态
     */
    private void initCustomConfirmBtStatus() {
        boolean sure = compareTimeOfAddApply(date_start_str, date_end_str);
        if (!sure) {
            confirm_apply_tv.setBackgroundResource(R.drawable.bt_unpress_selecter);
            confirm_apply_tv.setClickable(false);
        } else {
            confirm_apply_tv.setClickable(true);
            confirm_apply_tv.setBackgroundResource(R.drawable.bt_pressed_selecter);
        }
    }

    /**
     * 获取申请类型 1代表减少配送，2代表增加配送，3代表不配送
     */
    private String getApplyType() {
        switch (mApplyTypeRg.getCheckedRadioButtonId()) {
            case R.id.do_not_transport_rv:
                return "3";
            case R.id.more_transport_rv:
                return "2";
            case R.id.less_transport_rv:
                return "1";
            default:
                return "3";
        }
    }
}
