package com.vegetablestrading.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vegetablestrading.R;
import com.vegetablestrading.bean.MyApply;

import java.util.ArrayList;

/**
 * Created by ${王sir} on 2017/11/21.
 * application 我的积分的适配器
 */

public class MyApplyAdapter extends RecyclerView.Adapter<MyApplyAdapter.ViewHolder> {

    private ArrayList<MyApply> arrays;
    private MyApplyItemClick myIntegralItemClick;

    public void setData(ArrayList<MyApply> arrays) {
        this.arrays = arrays;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_apply_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyApplyAdapter.ViewHolder holder, int position) {
        final MyApply bean = arrays.get(position);
        holder.mApplyTypeTv.setText("申请类型："+bean.getApplyType());
        holder.mApplyTimeTv.setText(bean.getApply_Time());
        holder.mApplyInfoTv.setText(bean.getApplyInfo());
        holder.mApplyStatusTv.setText(bean.getOperateStatus());
        holder.my_apply_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myIntegralItemClick!=null) {
                    myIntegralItemClick.itemClick(bean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrays == null ? 0 : arrays.size();
    }


    public void setMyApplyItemClick(MyApplyItemClick myIntegralItemClick) {
        this.myIntegralItemClick = myIntegralItemClick;
    }

    public interface MyApplyItemClick {
        void itemClick(MyApply myApply);
    }

   class ViewHolder extends RecyclerView.ViewHolder{
        TextView mApplyTypeTv;
        TextView mApplyTimeTv;
        TextView mApplyInfoTv;
        TextView mApplyStatusTv;
       RelativeLayout my_apply_rl;

        ViewHolder(View view) {
            super(view);
            this.mApplyTypeTv = (TextView) view.findViewById(R.id.apply_type_tv);
            this.mApplyTimeTv = (TextView) view.findViewById(R.id.apply_time_tv);
            this.mApplyInfoTv = (TextView) view.findViewById(R.id.apply_info_tv);
            this.mApplyStatusTv = (TextView) view.findViewById(R.id.apply_status_tv);
            this.my_apply_rl = (RelativeLayout) view.findViewById(R.id.my_apply_rl);
        }
    }
}
