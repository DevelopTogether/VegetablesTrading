package com.vegetablestrading.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vegetablestrading.R;
import com.vegetablestrading.bean.TransportRecord;

import java.util.ArrayList;

/**
 * Created by ${王sir} on 2017/11/21.
 * application 我的积分的适配器
 */

public class MyIntegralAdapter extends RecyclerView.Adapter<MyIntegralAdapter.ViewHolder> {

    private ArrayList<TransportRecord> arrays;
    private MyIntegralItemClick myIntegralItemClick;

    public void setData(ArrayList<TransportRecord> arrays) {
        this.arrays = arrays;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_integral, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TransportRecord transportRecord = arrays.get(position);
        holder.mTransportDateTv.setText(transportRecord.getTransportTime());
        holder.mIrregularDetailTv.setText("-1");
        holder.mTransportNoTv.setText(transportRecord.getLogisticsNo());
        holder.my_integral_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myIntegralItemClick != null) {
                    myIntegralItemClick.itemClick(transportRecord);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrays == null ? 0 : arrays.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTransportDateTv;
        TextView mIrregularDetailTv;
        TextView mTransportNoTv;
        private final LinearLayout my_integral_ll;

        ViewHolder(View view) {
            super(view);
            this.mTransportDateTv = (TextView) view.findViewById(R.id.transport_date_tv);
            this.mIrregularDetailTv = (TextView) view.findViewById(R.id.irregular_detail_tv);
            this.mTransportNoTv = (TextView) view.findViewById(R.id.transport_no_tv);
            my_integral_ll = (LinearLayout) view.findViewById(R.id.my_integral_ll);
        }
    }

    public void setMyIntegralItemClick(MyIntegralItemClick myIntegralItemClick) {
        this.myIntegralItemClick = myIntegralItemClick;
    }

    public interface MyIntegralItemClick {
        void itemClick(TransportRecord transportRecord);
    }

}
