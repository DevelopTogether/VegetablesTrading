package com.vegetablestrading.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vegetablestrading.R;

public class CustomView extends RelativeLayout {
    private TextView titleBarLeftBtn;
    private TextView titleBarRightBtn;
    private TextView titleBarTitle;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_view, this, true);
        titleBarLeftBtn = (TextView) findViewById(R.id.title_bar_left);
        titleBarRightBtn = (TextView) findViewById(R.id.title_bar_right);
        titleBarTitle = (TextView) findViewById(R.id.title_bar_title);

        initViewStatus(context, attrs);
    }

    private void initViewStatus(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomView);
        if (attributes != null) {
            //处理titleBar背景色
            int titleBarBackGround = attributes.getResourceId(R.styleable.CustomView_title_background_color,R.color.navigition_press);
            setBackgroundResource(titleBarBackGround);
            //先处理左边按钮
            //获取是否要显示左边按钮
            boolean leftButtonVisible = attributes.getBoolean(R.styleable.CustomView_left_button_visible, true);
            if (leftButtonVisible) {
                titleBarLeftBtn.setVisibility(View.VISIBLE);
            } else {
                titleBarLeftBtn.setVisibility(View.INVISIBLE);
            }
            //设置左边按钮的文字
            String leftButtonText = attributes.getString(R.styleable.CustomView_left_button_text);
            if (!TextUtils.isEmpty(leftButtonText)) {
                titleBarLeftBtn.setText(leftButtonText);
//                //设置左边按钮文字颜色
                int leftButtonTextColor = attributes.getColor(R.styleable.CustomView_left_button_text_color, Color.RED);
                titleBarLeftBtn.setTextColor(leftButtonTextColor);
            } else {
                //设置左边图片icon 这里是二选一 要么只能是文字 要么只能是图片
                int leftButtonDrawable = attributes.getResourceId(R.styleable.CustomView_left_button_drawable, R.drawable.back);
                if (leftButtonDrawable != -1) {
                    titleBarLeftBtn.setBackgroundResource(leftButtonDrawable);
                }
            }

            //处理标题
            //先获取标题是否要显示图片icon
            int titleTextDrawable = attributes.getResourceId(R.styleable.CustomView_title_text_drawable, -1);
            if (titleTextDrawable != -1) {
                titleBarTitle.setBackgroundResource(titleTextDrawable);
            } else {
                //如果不是图片标题 则获取文字标题
                String titleText = attributes.getString(R.styleable.CustomView_title_text);
                if (!TextUtils.isEmpty(titleText)) {
                    titleBarTitle.setText(titleText);
                }
                //获取标题显示颜色
                int titleTextColor = attributes.getColor(R.styleable.CustomView_title_text_color, Color.WHITE);
                titleBarTitle.setTextColor(titleTextColor);
            }

            //先处理右边按钮
            //获取是否要显示右边按钮
            boolean rightButtonVisible = attributes.getBoolean(R.styleable.CustomView_right_button_visible, true);
            if (rightButtonVisible) {
                titleBarRightBtn.setVisibility(View.VISIBLE);
            } else {
                titleBarRightBtn.setVisibility(View.INVISIBLE);
            }
            //设置右边按钮的文字
            String rightButtonText = attributes.getString(R.styleable.CustomView_right_button_text);
            if (!TextUtils.isEmpty(rightButtonText)) {
                titleBarRightBtn.setText(rightButtonText);
                //设置右边按钮文字颜色
                int rightButtonTextColor = attributes.getColor(R.styleable.CustomView_right_button_text_color, Color.WHITE);
                titleBarRightBtn.setTextColor(rightButtonTextColor);
            } else {
                //设置右边图片icon 这里是二选一 要么只能是文字 要么只能是图片
                int rightButtonDrawable = attributes.getResourceId(R.styleable.CustomView_right_button_drawable, -1);
                if (rightButtonDrawable != -1) {
                    titleBarRightBtn.setBackgroundResource(rightButtonDrawable);
                }
            }
            attributes.recycle();
        }
    }


    /**
     * 获取左控件实例
     * @return
     */
    public TextView getTitleBarLeftBtn() {
        return titleBarLeftBtn;
    }
    /**
     * 获取右控件实例
     * @return
     */
    public TextView getTitleBarRightBtn() {
        return titleBarRightBtn;
    }
    /**
     * 获取标题实例
     * @return
     */
    public TextView getTitleBarTitle() {
        return titleBarTitle;
    }


    /**
     *设置左控件图标的大小
     */
    public void setTitleBarLeftBtnIconSize(int width,int height){
        ViewGroup.LayoutParams params =  titleBarLeftBtn.getLayoutParams();
        params.width= width;
        params.height=height;
        titleBarLeftBtn.setLayoutParams(params);
    }

    /**
     *设置左控件图标的大小
     */
    public void setTitleBarRightBtnIconSize(int width,int height){
        ViewGroup.LayoutParams params =  titleBarRightBtn.getLayoutParams();
        params.width= width;
        params.height=height;
        titleBarRightBtn.setLayoutParams(params);
    }
}