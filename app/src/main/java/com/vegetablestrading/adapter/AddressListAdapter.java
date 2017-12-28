package com.vegetablestrading.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vegetablestrading.R;
import com.vegetablestrading.bean.AddressInfo;
import com.vegetablestrading.utils.Constant;
import com.vegetablestrading.utils.PublicUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;

/**
 * Author:wang_sir
 * Time:2017/12/26 11:21
 * Description:This is AddressListAdapter
 */
public class AddressListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AddressInfo> arrays;
    private Context context;
    private final int Address_View_One = 1;
    private final int Address_View_Two = 2;
    private final int Address_View_Three = 3;

    public void setData(List<AddressInfo> arrays, Context context) {
        this.arrays = arrays;
        this.context = context;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == Address_View_One) {
            return new AddressViewHolderOne(LayoutInflater.from(context).inflate(R.layout.no_record, parent, false));
        } else if (viewType == Address_View_Two) {
            return new AddressViewHolderTwo(LayoutInflater.from(context).inflate(R.layout.my_address, parent, false));
        } else {
            return new AddressViewHolderThree(LayoutInflater.from(context).inflate(R.layout.my_address_three, parent, false));
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AddressViewHolderOne) {
            AddressViewHolderOne holderOne = (AddressViewHolderOne) holder;
            holderOne.mNoRecordTv.setText("暂无收件地址信息");

        } else if (holder instanceof AddressViewHolderTwo) {
            AddressViewHolderTwo holderTwo = (AddressViewHolderTwo) holder;
            final AddressInfo bean = arrays.get(position);
            holderTwo.mAccepterNameTv.setText(bean.getAccepter());
            holderTwo.mAccepterPhoneTv.setText(bean.getAccepterPhone());
            holderTwo.mAddrInfoTv.setText(bean.getAddress());
            if (bean.getIsDefault() == null || TextUtils.isEmpty(bean.getIsDefault())) {
                holderTwo.mAgreeProvisionCb.setChecked(false);
            } else {
                if ("1".equals(bean.getIsDefault())) {
                    holderTwo.mAgreeProvisionCb.setChecked(true);
                } else {
                    holderTwo.mAgreeProvisionCb.setChecked(false);
                }
            }
            holderTwo.mAgreeProvisionCb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setDefaultAddr(bean.getAddressId());
                }
            });
            holderTwo.mDeleteAddressLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    warnUserForOperate(bean);

                }
            });

        } else {
            AddressViewHolderThree holderThree = (AddressViewHolderThree) holder;
            holderThree.addressCount.setText("共有"+(arrays.size()-1)+"条地址信息");
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (arrays.size() == 1) {
            return Address_View_One;

        } else if ("2".equals(arrays.get(position).getLayoutTag())) {
            return Address_View_Two;
        } else {
            return Address_View_Three;
        }
    }


    /**
     * 设置默认地址
     */
    private void setDefaultAddr(String addressId) {
        OkHttpUtils
                .post()
                .url(Constant.setDefaultAddress_url)
                .addParams("userId", PublicUtils.userInfo.getUserId())
                .addParams("addressId", addressId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(context, "网络错误", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                String result = obj.getString("Result");
                                String message = obj.getString("Message");
                                if ("Ok".equals(result)) {
                                    Toast.makeText(context, "..", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                });
    }

    /**
     * 用户操作提醒
     */
    private Dialog warnUserForOperate(final AddressInfo bean) {

        View v = LayoutInflater.from(context).inflate(R.layout.warn_user_exit_layout
                , null);
        final Dialog dialog_toWarn = new Dialog(context, R.style.DialogStyle);
        dialog_toWarn.setCanceledOnTouchOutside(false);
        dialog_toWarn.setCancelable(false);
        Window window = dialog_toWarn.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        window.setAttributes(lp);
//        lp.width = dip2px(this, 300); // 宽度
//        lp.height = dip2px(this, 160); // 高度
        // lp.alpha = 0.7f; // 透明度
        dialog_toWarn.show();
        window.setContentView(v);
        dialog_toWarn.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        dialog_toWarn.dismiss();
                    }
                }
                return false;
            }
        });
        final TextView confirm_exit = (TextView) v.findViewById(R.id.confirm_exit_tv);
        final TextView cancel_exit = (TextView) v.findViewById(R.id.cancel_exit_tv);
        final TextView warn_content_tv = (TextView) v.findViewById(R.id.warn_content_tv);
        warn_content_tv.setText("确认要删除该地址吗？");
        confirm_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_toWarn.dismiss();
                arrays.remove(bean);
                notifyDataSetChanged();
            }
        });
        cancel_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_toWarn.dismiss();
            }
        });
        return dialog_toWarn;
    }

    @Override
    public int getItemCount() {
        return arrays == null ? 0 : arrays.size();
    }


    class AddressViewHolderOne extends RecyclerView.ViewHolder {

        TextView mNoRecordTv;

        AddressViewHolderOne(View view) {
            super(view);
            this.mNoRecordTv = (TextView) view.findViewById(R.id.no_record_tv);
        }
    }

    class AddressViewHolderTwo extends RecyclerView.ViewHolder {

        TextView mAccepterNameTv;
        TextView mAccepterPhoneTv;
        TextView mAddrInfoTv;
        CheckBox mAgreeProvisionCb;
        LinearLayout mDeleteAddressLl;

        public AddressViewHolderTwo(View itemView) {
            super(itemView);
            this.mAccepterNameTv = (TextView) itemView.findViewById(R.id.accepter_name_tv);
            this.mAccepterPhoneTv = (TextView) itemView.findViewById(R.id.accepter_phone_tv);
            this.mAddrInfoTv = (TextView) itemView.findViewById(R.id.addr_info_tv);
            this.mAgreeProvisionCb = (CheckBox) itemView.findViewById(R.id.agree_provision_cb);
            this.mDeleteAddressLl = (LinearLayout) itemView.findViewById(R.id.delete_address_ll);
        }
    }

    class AddressViewHolderThree extends RecyclerView.ViewHolder {

        TextView addressCount;

        AddressViewHolderThree(View view) {
            super(view);
            this.addressCount = (TextView) view.findViewById(R.id.addressCount_tv);
        }
    }


}
