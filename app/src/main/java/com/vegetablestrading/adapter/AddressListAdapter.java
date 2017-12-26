package com.vegetablestrading.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vegetablestrading.R;
import com.vegetablestrading.bean.AddressInfo;

import java.util.List;

/**
 * Author:wang_sir
 * Time:2017/12/26 11:21
 * Description:This is AddressListAdapter
 */
public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder> {
    private List<AddressInfo> arrays;

    public void setData(List<AddressInfo> arrays) {
        this.arrays = arrays;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_address, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AddressInfo bean = arrays.get(position);
        holder.mAccepterNameTv.setText(bean.getAccepter());
        holder.mAccepterPhoneTv.setText(bean.getAccepterPhone());
        holder.mAddrInfoTv.setText(bean.getAddress());
        if (bean.getIsDefault()==null|| TextUtils.isEmpty(bean.getIsDefault())) {
            holder.mAgreeProvisionCb.setChecked(false);
        }else{
            if ("1".equals(bean.getIsDefault())) {
                holder.mAgreeProvisionCb.setChecked(true);
            }else{
                holder.mAgreeProvisionCb.setChecked(false);
            }
        }


    }

    @Override
    public int getItemCount() {
        return arrays==null?0:arrays.size();
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
