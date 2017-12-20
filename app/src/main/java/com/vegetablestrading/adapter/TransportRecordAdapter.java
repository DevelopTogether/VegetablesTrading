package com.vegetablestrading.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vegetablestrading.R;
import com.vegetablestrading.bean.TransportRecord;
import com.vegetablestrading.utils.CalendarUtil;

import java.util.ArrayList;

/**
 * Created by ${王sir} on 2017/11/20.
 * application
 */

public class TransportRecordAdapter extends RecyclerView.Adapter<TransportRecordAdapter.ViewHolder> {
    private ArrayList<TransportRecord> arrays;
    private TransportRecordItemClick transportRecordItemClick;

    public void setData(ArrayList<TransportRecord> arrays) {
        this.arrays = arrays;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transport_record_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       final TransportRecord transportRecord = arrays.get(position);
        holder.mTransportNoTv.setText("配送单号："+transportRecord.getLogisticsNo());
        holder.mTransportPersonTv.setText("配送人："+ transportRecord.getTransportPeople());
        holder.mTransportTimeTv.setText( "配送时间："+transportRecord.getTransportTime());
        if (!CalendarUtil.checkDateBelongThisWeek(transportRecord.getTransportTime())) {
            holder.transport_status_tv.setText("已过期");
        }else{
            holder.transport_status_tv.setText("");
        }
        holder.mTransportPersonMobileTv.setText( "配送人手机："+transportRecord.getTransportPeopleMobile());
        holder.transport_record_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transportRecordItemClick != null) {
                    transportRecordItemClick.itemClick(transportRecord);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrays == null ? 0 : arrays.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTransportNoTv;
        TextView mTransportPersonTv;
        TextView mTransportTimeTv;
        TextView mTransportPersonMobileTv;
        TextView transport_status_tv;
        LinearLayout transport_record_ll;

        ViewHolder(View view) {
            super(view);
            this.mTransportNoTv = (TextView) view.findViewById(R.id.transportNo_tv);
            this.mTransportPersonTv = (TextView) view.findViewById(R.id.transport_person_tv);
            this.mTransportTimeTv = (TextView) view.findViewById(R.id.transportTime_tv);
            this.mTransportPersonMobileTv = (TextView) view.findViewById(R.id.transport_person_mobile_tv);
            this.transport_status_tv = (TextView) view.findViewById(R.id.transport_status_tv);
            this.transport_record_ll = (LinearLayout) view.findViewById(R.id.transport_record_ll);
        }
    }

    public void setTransportRecordItemClick(TransportRecordItemClick transportRecordItemClick) {
        this.transportRecordItemClick = transportRecordItemClick;
    }

    public interface TransportRecordItemClick {
        void itemClick(TransportRecord transportRecord);
    }


}
