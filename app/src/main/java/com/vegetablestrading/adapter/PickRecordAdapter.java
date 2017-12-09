package com.vegetablestrading.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vegetablestrading.R;
import com.vegetablestrading.bean.MyPickInfo;

import java.util.ArrayList;

/**
 * Author:wang_sir
 * Time:2017/11/23 13:16
 * Description:This is Adapter
 */

public class PickRecordAdapter extends RecyclerView.Adapter<PickRecordAdapter.ViewHolder> {
    private ArrayList<MyPickInfo> arrays;

    public void setData(ArrayList<MyPickInfo> arrays) {
        this.arrays = arrays;
        notifyDataSetChanged();
    }

    @Override
    public PickRecordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pick_record_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MyPickInfo myPickInfo = arrays.get(position);
        holder.mPickNoTv.setText("采摘人数："+myPickInfo.getPickPeopleNumber());
        holder.mResidualPickAmountTv.setText("剩余次数："+myPickInfo.getResidualPickAmount());
        holder.mPickTimeTv.setText("采摘时间："+myPickInfo.getPickTime());
        holder.mPickNoteTv.setText("备注信息："+myPickInfo.getNoteInfo());
    }

    @Override
    public int getItemCount() {
        return arrays == null ? 0 : arrays.size();
    }




    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView mPickNoTv;
        TextView mResidualPickAmountTv;
        TextView mPickTimeTv;
        TextView mPickNoteTv;
        LinearLayout mPickRecordLl;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            this.mPickNoTv = (TextView) view.findViewById(R.id.pickNo_tv);
            this.mResidualPickAmountTv = (TextView) view.findViewById(R.id.residualPickAmount_tv);
            this.mPickTimeTv = (TextView) view.findViewById(R.id.pick_time_tv);
            this.mPickNoteTv = (TextView) view.findViewById(R.id.pick_note_tv);
            this.mPickRecordLl = (LinearLayout) view.findViewById(R.id.pick_record_ll);
        }
    }
}

