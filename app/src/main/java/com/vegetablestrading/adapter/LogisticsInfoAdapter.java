package com.vegetablestrading.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vegetablestrading.R;
import com.vegetablestrading.bean.LogisticsInfo;

import java.util.List;

/**
 * Author:wang_sir
 * Time:2017/12/4 15:45
 * Description:This is LogisticsInfoAdapter 物流详情
 */
public class LogisticsInfoAdapter extends RecyclerView.Adapter<LogisticsInfoAdapter.ViewHolder> {


    private List<LogisticsInfo> arrays;

    public void setData(List<LogisticsInfo> arrays) {
        this.arrays = arrays;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.logistics_info, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LogisticsInfo logisticsInfo = arrays.get(position);
        if (position == 0) {
            holder.mLogisticsMarkeTv.setVisibility(View.VISIBLE);
        } else {
            holder.mLogisticsMarkeTv.setVisibility(View.INVISIBLE);
        }
        holder.mLogisticsTimeTv.setText(logisticsInfo.getDatetime());
        holder.mLogisticsDescriptionTv.setText(logisticsInfo.getRemark());
    }

    @Override
    public int getItemCount() {
        return arrays == null ? 0 : arrays.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mLogisticsMarkeTv;
        TextView mLogisticsTimeTv;
        TextView mLogisticsDescriptionTv;

        ViewHolder(View view) {
            super(view);
            this.mLogisticsMarkeTv = (TextView) view.findViewById(R.id.logistics_marke_tv);
            this.mLogisticsTimeTv = (TextView) view.findViewById(R.id.logistics_time_tv);
            this.mLogisticsDescriptionTv = (TextView) view.findViewById(R.id.logistics_description_tv);
        }
    }
}
