package com.vegetablestrading.customViews;

/**
 * Created by ${王sir} on 2017/11/14.
 * application
 */


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vegetablestrading.R;

/**
 * Android自定义SearchView
 * Created by yuandl on 2016-11-17.
 */

public class SearchView extends LinearLayout implements TextWatcher, View.OnClickListener {
    /**
     * 输入框
     */
    private EditText et_search;
    /**
     * 输入框后面的那个清除按钮
     */
    private ImageView bt_clear;
    private Button search_button;
    private SearchViewOnClick searchViewOnClick;


    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /**加载布局文件*/
        LayoutInflater.from(context).inflate(R.layout.pub_searchview, this, true);
        /***找出控件*/
        et_search = (EditText) findViewById(R.id.et_search);
        bt_clear = (ImageView) findViewById(R.id.bt_clear);
        search_button = (Button) findViewById(R.id.search_button);
        bt_clear.setVisibility(GONE);
        et_search.addTextChangedListener(this);
        bt_clear.setOnClickListener(this);
        search_button.setOnClickListener(this);
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    /****
     * 对用户输入文字的监听
     *
     * @param editable
     */
    @Override
    public void afterTextChanged(Editable editable) {
        /**获取输入文字**/
        String input = et_search.getText().toString().trim();
        if (input.isEmpty()) {
            bt_clear.setVisibility(GONE);
        } else {
            bt_clear.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_clear:
                et_search.setText("");
                break;
            case R.id.search_button://查询的点击事件
                if (searchViewOnClick != null) {
                    searchViewOnClick.searchVegetables();
                }
                break;
            default:
                break;
        }

    }

    public void setSearchVegetableCallBack(SearchViewOnClick searchViewOnClick) {
        this.searchViewOnClick = searchViewOnClick;
    }

    public interface SearchViewOnClick {
        void searchVegetables();
    }
}