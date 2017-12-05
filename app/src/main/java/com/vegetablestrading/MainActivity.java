package com.vegetablestrading;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vegetablestrading.fragment.FragmentTab1;
import com.vegetablestrading.fragment.FragmentTab2;
import com.vegetablestrading.fragment.FragmentTab3;
import com.vegetablestrading.fragment.FragmentTab4;
import com.vegetablestrading.interfaces.FinishActivityInterface;
import com.vegetablestrading.utils.PublicUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout mContentFl;
    private ImageView mNavigationImg1;
    private TextView mNavigationTv1;
    private LinearLayout mNavigationLine1;
    private ImageView mNavigationImg2;
    private TextView mNavigationTv2;
    private LinearLayout mNavigationLine2;
    private ImageView mNavigationImg3;
    private TextView mNavigationTv3;
    private LinearLayout mNavigationLine3;
    private ImageView mNavigationImg4;
    private TextView mNavigationTv4;
    private LinearLayout mNavigationLine4;
    private FragmentManager fragmentManager;
    private FragmentTab1 navitionFragment1 = new FragmentTab1();
    private FragmentTab4 navitionFragment4 = new FragmentTab4();
    private FragmentTab3 navitionFragment3 = new FragmentTab3();
    private FragmentTab2 navitionFragment2 = new FragmentTab2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        fragmentManager = getSupportFragmentManager();
        initBottomViewStatus(0);
        initFragmentSelected(0);
        fragmentCallBackForFinishActivity();
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        PublicUtils.app_width = wm.getDefaultDisplay().getWidth();
        PublicUtils.app_height = wm.getDefaultDisplay().getHeight();
//        startActivity(new Intent(this, ActivatedActivity.class));
    }

    /**
     * 关闭Activity的回调
     */
    private void fragmentCallBackForFinishActivity() {
        navitionFragment2.setFinishActivity(new FinishActivityInterface() {
            @Override
            public void finishActivity() {
                finish();
            }
        });
        navitionFragment3.setFinishActivity(new FinishActivityInterface() {
            @Override
            public void finishActivity() {
                finish();
            }
        });
    }

    private void initView() {
        mContentFl = (FrameLayout) findViewById(R.id.content_fl);
        mNavigationImg1 = (ImageView) findViewById(R.id.navigation_img1);
        mNavigationTv1 = (TextView) findViewById(R.id.navigation_tv1);
        mNavigationLine1 = (LinearLayout) findViewById(R.id.navigation_line1);
        mNavigationLine1.setOnClickListener(this);
        mNavigationImg2 = (ImageView) findViewById(R.id.navigation_img2);
        mNavigationTv2 = (TextView) findViewById(R.id.navigation_tv2);
        mNavigationLine2 = (LinearLayout) findViewById(R.id.navigation_line2);
        mNavigationLine2.setOnClickListener(this);
        mNavigationImg3 = (ImageView) findViewById(R.id.navigation_img3);
        mNavigationTv3 = (TextView) findViewById(R.id.navigation_tv3);
        mNavigationLine3 = (LinearLayout) findViewById(R.id.navigation_line3);
        mNavigationLine3.setOnClickListener(this);
        mNavigationImg4 = (ImageView) findViewById(R.id.navigation_img4);
        mNavigationTv4 = (TextView) findViewById(R.id.navigation_tv4);
        mNavigationLine4 = (LinearLayout) findViewById(R.id.navigation_line4);
        mNavigationLine4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navigation_line1:
                initBottomViewStatus(0);
                initFragmentSelected(0);
                break;
            case R.id.navigation_line2:
                initBottomViewStatus(1);
                initFragmentSelected(1);
                break;
            case R.id.navigation_line3:
                initBottomViewStatus(2);
                initFragmentSelected(2);
                break;
            case R.id.navigation_line4:
                initBottomViewStatus(3);
                initFragmentSelected(3);
                break;
        }
    }

    /**
     * 初始化底部控件的状态
     *
     * @param i
     */
    private void initBottomViewStatus(int i) {
        mNavigationImg1.setImageResource(R.mipmap.navigation_trade);
        mNavigationImg2.setImageResource(R.mipmap.navigation_shop);
        mNavigationImg3.setImageResource(R.mipmap.navigation_car);
        mNavigationImg4.setImageResource(R.mipmap.navigation_mine);
        mNavigationTv1.setTextColor(getResources().getColor(R.color.navigition_normal));
        mNavigationTv2.setTextColor(getResources().getColor(R.color.navigition_normal));
        mNavigationTv3.setTextColor(getResources().getColor(R.color.navigition_normal));
        mNavigationTv4.setTextColor(getResources().getColor(R.color.navigition_normal));

        switch (i) {
            case 0:
                mNavigationImg1.setImageResource(R.mipmap.navigation_trade_press);
                mNavigationTv1.setTextColor(getResources().getColor(R.color.navigition_press));
                break;
            case 1:
                mNavigationImg2.setImageResource(R.mipmap.navigation_shop_press);
                mNavigationTv2.setTextColor(getResources().getColor(R.color.navigition_press));
                break;
            case 2:
                mNavigationImg3.setImageResource(R.mipmap.navigation_car_press);
                mNavigationTv3.setTextColor(getResources().getColor(R.color.navigition_press));
                break;
            case 3:
                mNavigationImg4.setImageResource(R.mipmap.navigation_mine_press);
                mNavigationTv4.setTextColor(getResources().getColor(R.color.navigition_press));
                break;
            default:
                break;
        }
    }

    /**
     * 初始化fragment
     *
     * @param i
     */
    private void initFragmentSelected(int i) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hindFragments(fragmentTransaction);
        switch (i) {
            case 0:
                if (!navitionFragment1.isAdded()) {
                    fragmentTransaction.add(R.id.content_fl, navitionFragment1);
                }
                fragmentTransaction.show(navitionFragment1);
                break;
            case 1:
                if (!navitionFragment2.isAdded()) {
                    fragmentTransaction.add(R.id.content_fl, navitionFragment2);
                }
                fragmentTransaction.show(navitionFragment2);
                break;
            case 2:
                if (!navitionFragment3.isAdded()) {
                    fragmentTransaction.add(R.id.content_fl, navitionFragment3);
                }
                fragmentTransaction.show(navitionFragment3);
                break;
            case 3:
                if (!navitionFragment4.isAdded()) {
                    fragmentTransaction.add(R.id.content_fl, navitionFragment4);
                }
                fragmentTransaction.show(navitionFragment4);
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

    /**
     * 隐藏fragment
     *
     * @param fragmentTransaction
     */
    private void hindFragments(FragmentTransaction fragmentTransaction) {
        if (navitionFragment1 != null) {
            fragmentTransaction.hide(navitionFragment1);
        }
        if (navitionFragment2 != null) {
            fragmentTransaction.hide(navitionFragment2);
        }
        if (navitionFragment3 != null) {
            fragmentTransaction.hide(navitionFragment3);
        }
        if (navitionFragment4 != null) {
            fragmentTransaction.hide(navitionFragment4);
        }
    }

    /**
     * 获取当前显示的fragment
     *
     * @return
     */
    public Fragment getVisibleFragment() {
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (getVisibleFragment() instanceof FragmentTab2) {
//            navitionFragment2.onKeyDown(keyCode, event);
//            return true;
//        }
//        if (getVisibleFragment() instanceof FragmentTab3) {
//            navitionFragment3.onKeyDown(keyCode, event);
//            return true;
//        }
        return super.onKeyDown(keyCode, event);
    }

}
