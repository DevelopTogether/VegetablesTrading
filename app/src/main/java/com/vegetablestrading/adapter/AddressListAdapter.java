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
public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder> {
    private List<AddressInfo> arrays;
    private Context context;

    public void setData(List<AddressInfo> arrays, Context context) {
        this.arrays = arrays;
        this.context = context;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_address, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final AddressInfo bean = arrays.get(position);
        holder.mAccepterNameTv.setText(bean.getAccepter());
        holder.mAccepterPhoneTv.setText(bean.getAccepterPhone());
        holder.mAddrInfoTv.setText(bean.getAddress());
        if (bean.getIsDefault() == null || TextUtils.isEmpty(bean.getIsDefault())) {
            holder.mAgreeProvisionCb.setChecked(false);
        } else {
            if ("1".equals(bean.getIsDefault())) {
                holder.mAgreeProvisionCb.setChecked(true);
            } else {
                holder.mAgreeProvisionCb.setChecked(false);
            }
        }
        holder.mAgreeProvisionCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDefaultAddr(bean.getAddressId());
            }
        });
        holder.mDeleteAddressLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                warnUserForOperate(bean);

            }
        });

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
     *
     */
    private Dialog warnUserForOperate( final AddressInfo bean) {

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

    static

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mAccepterNameTv;
        TextView mAccepterPhoneTv;
        TextView mAddrInfoTv;
        CheckBox mAgreeProvisionCb;
        LinearLayout mDeleteAddressLl;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mAccepterNameTv = (TextView) itemView.findViewById(R.id.accepter_name_tv);
            this.mAccepterPhoneTv = (TextView) itemView.findViewById(R.id.accepter_phone_tv);
            this.mAddrInfoTv = (TextView) itemView.findViewById(R.id.addr_info_tv);
            this.mAgreeProvisionCb = (CheckBox) itemView.findViewById(R.id.agree_provision_cb);
            this.mDeleteAddressLl = (LinearLayout) itemView.findViewById(R.id.delete_address_ll);
        }
    }


}
