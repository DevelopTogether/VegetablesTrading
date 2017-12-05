package com.vegetablestrading.activity.MineMode.myPick;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.vegetablestrading.R;
import com.vegetablestrading.utils.CalendarUtil;
import com.vegetablestrading.utils.PublicUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.vegetablestrading.utils.CalendarUtil.GetUploadTime;
import static com.vegetablestrading.utils.CalendarUtil.GetWeekFromDate;

/**
 * created by 8级大的狂风
 * created date 2017/12/1 9:56.
 * application
 */
public class MyPickActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mTopLeftImageIv;
    /**
     * 标题
     */
    private TextView mTopTitleTv;
    private ImageView mTopRightImageIv;
    private EditText mUserMobileEt;
    private EditText mPickPeopleNoEt;
    /**
     * 年月日
     */
    private TextView mPickDateTv;
    private DatePicker mPickDateDp;
    /**
     * 上午 10:00
     */
    private TextView mPickTimeTv;
    private TimePicker mPickTimeTp;
    /**
     * 至少输入5个字，最多50个字
     */
    private EditText mPickNoteInfoEt;
    /**
     * 提交申请
     */
    private TextView mPickConfirmApplyTv;
    private int yearPick;
    private int monthPick;
    private int dayPick;

    private String date_pick_str;//采摘日期
    private String date_pick_str_current;//当前日期
    private RelativeLayout mPickDateRl;
    private RelativeLayout mPickTimeRl;
    private View mPickTimeLine;
    private SimpleDateFormat form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pick);
        initView();
        initPickerData();
    }

    private void initView() {
        mTopLeftImageIv = (ImageView) findViewById(R.id.top_left_image_iv);
        mTopLeftImageIv.setOnClickListener(this);
        mTopTitleTv = (TextView) findViewById(R.id.top_title_tv);
        mTopTitleTv.setText("我的采摘");
        mTopRightImageIv = (ImageView) findViewById(R.id.top_right_image_iv);
        mTopRightImageIv.setVisibility(View.VISIBLE);
        mTopRightImageIv.setImageResource(R.drawable.pick_record_icon);
        mTopRightImageIv.setOnClickListener(this);
        mUserMobileEt = (EditText) findViewById(R.id.user_mobile_et);
        PublicUtils.getEdittextHint(mUserMobileEt,"请输入申请人电话");
        mPickPeopleNoEt = (EditText) findViewById(R.id.pick_people_No_et);
        PublicUtils.getEdittextHint(mPickPeopleNoEt,"请输入采摘人数");
        mPickDateTv = (TextView) findViewById(R.id.pick_date_tv);
        mPickDateDp = (DatePicker) findViewById(R.id.pick_date_dp);
        mPickTimeTv = (TextView) findViewById(R.id.pick_time_tv);
        mPickTimeTp = (TimePicker) findViewById(R.id.pick_time_tp);
        mPickTimeTp.setIs24HourView(true);
        mPickNoteInfoEt = (EditText) findViewById(R.id.pickNote_info_et);
        mPickConfirmApplyTv = (TextView) findViewById(R.id.pick_confirm_apply_tv);
        mPickConfirmApplyTv.setOnClickListener(this);
        mPickDateRl = (RelativeLayout) findViewById(R.id.pick_date_rl);
        mPickDateRl.setOnClickListener(this);
        mPickTimeRl = (RelativeLayout) findViewById(R.id.pick_time_rl);
        mPickTimeRl.setOnClickListener(this);
        mPickTimeLine = (View) findViewById(R.id.pick_time_line);
    }


    /**
     * 初始化日期
     */
    private void initCalender() {
        form = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Calendar calendar_Pick = Calendar.getInstance();
        calendar_Pick.add(Calendar.MONTH, 0);
        Date date_Pick = calendar_Pick.getTime();
        String ymd_Pick = sdf.format(date_Pick);
        date_pick_str_current = form.format(date_Pick);
        String week_Pick = GetWeekFromDate(ymd_Pick);
        mPickDateTv.setText(ymd_Pick + " " + week_Pick);
        yearPick = calendar_Pick.get(Calendar.YEAR);
        monthPick = calendar_Pick.get(Calendar.MONTH);
        dayPick = calendar_Pick.get(Calendar.DAY_OF_MONTH);
        date_pick_str = yearPick+"年"+(monthPick+1)+"月"+dayPick+"日";
        mPickTimeTv.setText("10:00");
    }

    /**
     * 获取当前时间
     */
    private String getCurrentTime() {
        Calendar calendar_Pick = Calendar.getInstance();
        calendar_Pick.add(Calendar.MONTH, 0);
        Date date_Pick = calendar_Pick.getTime();
        String ymd_Pick = form.format(date_Pick);
        return ymd_Pick;
    }
    /**
     * 获取当前时间
     */
    private String getCurrentTime2() {
        Calendar calendar_Pick = Calendar.getInstance();
        calendar_Pick.add(Calendar.MONTH, 0);
        int hour = calendar_Pick.get(Calendar.HOUR);
        int minute = calendar_Pick.get(Calendar.MINUTE);
        return hour+":"+minute;
    }

    private void initPickerData() {
        initCalender();
        mPickDateDp.init(yearPick, monthPick, dayPick, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String str_Pick = getStringTypeOfDate(year, monthOfYear + 1, dayOfMonth);
                mPickDateTv.setText(str_Pick);
                date_pick_str = new StringBuffer().append(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日").toString();
                String time = date_pick_str + " "+getCurrentTime2();
                initCustomConfirmBtStatus(time);
            }
        });
        mPickTimeTp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                if (i1 < 10) {
                    mPickTimeTv.setText(i + ":0" + i1);
                } else {
                    mPickTimeTv.setText(i + ":" + i1);
                }
                String time =  date_pick_str+" "+ mPickTimeTv.getText();
                initCustomConfirmBtStatus(time);
            }
        });
    }


    /**
     * 初始化自定义栏中确定按钮的状态
     */
    private void initCustomConfirmBtStatus(String selectedTime) {
        boolean sure = CalendarUtil.compareTimeOfAddApply(getCurrentTime(), selectedTime);
        if (!sure) {
            mPickConfirmApplyTv.setBackgroundResource(R.drawable.bt_unpress_selecter);
            mPickConfirmApplyTv.setClickable(false);
        } else {
            mPickConfirmApplyTv.setBackgroundResource(R.drawable.bt_pressed_selecter);
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_image_iv:
                finish();
                break;
            case R.id.top_right_image_iv:
                startActivity(new Intent(this, PickRecordActivity.class));
                break;
            case R.id.pick_confirm_apply_tv:
                String mobile = mUserMobileEt.getText().toString().trim();
                String peopleNum = mPickPeopleNoEt.getText().toString().trim();
                String pickDate = mPickDateTv.getText().toString().trim().substring(0,12);
                String pickTime = mPickTimeTv.getText().toString().trim();
                String uploadPickTime = GetUploadTime(pickDate)+" "+pickTime+":00";
                uploadPickTime.toString();
                String pickNote = mPickNoteInfoEt.getText().toString().trim();
                break;
            case R.id.pick_date_rl:
                mPickDateDp.setVisibility(View.VISIBLE);
                mPickTimeTp.setVisibility(View.GONE);
                mPickTimeLine.setVisibility(View.INVISIBLE);
                break;
            case R.id.pick_time_rl:
                mPickDateDp.setVisibility(View.GONE);
                mPickTimeTp.setVisibility(View.VISIBLE);
                mPickTimeLine.setVisibility(View.VISIBLE);
                break;
        }
    }
}
