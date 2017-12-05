package com.vegetablestrading.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vegetablestrading.R;
import com.vegetablestrading.bean.TransportVegetableInfo;

import java.util.ArrayList;

/**
 * Created by ${王sir} on 2017/11/21.
 * application 配送列表适配器
 */

public class TransportListAdapter extends RecyclerView.Adapter<TransportListAdapter.ViewHolder> {

    private ArrayList<TransportVegetableInfo> arrayList;
    private Context context;
    private OnTransportListItemInterface onTransportListItemInterface;

    public void setData(ArrayList<TransportVegetableInfo> arrayList) {
        this.arrayList = arrayList;
    }

    public TransportListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transport_list_item, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TransportVegetableInfo bean = arrayList.get(position);
        Glide.with(context).load(R.drawable.app_logo).into(holder.mVegetableIconIv);
        holder.mVegetableNameTv.setText(bean.getVegetableName());
        holder.mVegetableWeightTv.setText(bean.getWeight());
        holder.mVegetableDescripTv.setText(bean.getVegetableInfo());
        holder.mStartTimeTransportTv.setText(bean.getTransportStartTime());
        holder.mEndTimeTransportTv.setText(bean.getTransportEndTime());
        holder.trade_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onTransportListItemInterface != null) {
                    onTransportListItemInterface.OnTransportListItemClick(bean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList == null ? 0 : arrayList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mVegetableIconIv;
        TextView mVegetableNameTv;
        TextView mVegetableWeightTv;
        TextView mVegetableDescripTv;
        TextView mStartTimeTransportTv;
        TextView mEndTimeTransportTv;
        LinearLayout trade_ll;


        ViewHolder(View view) {
            super(view);
            this.mVegetableIconIv = (ImageView) view.findViewById(R.id.vegetable_icon_iv);
            this.mVegetableNameTv = (TextView) view.findViewById(R.id.vegetable_name_tv);
            this.mVegetableWeightTv = (TextView) view.findViewById(R.id.vegetable_weight_tv);
            this.mVegetableDescripTv = (TextView) view.findViewById(R.id.vegetable_descrip_tv);
            this.mStartTimeTransportTv = (TextView) view.findViewById(R.id.startTime_transport_tv);
            this.mEndTimeTransportTv = (TextView) view.findViewById(R.id.endTime_transport_tv);
            this.trade_ll = (LinearLayout) view.findViewById(R.id.trade_ll);
        }
    }

    public void setOnTransportListItemClickListener(OnTransportListItemInterface onTransportListItemInterface) {
        this.onTransportListItemInterface = onTransportListItemInterface;
    }

    public interface OnTransportListItemInterface {
        void OnTransportListItemClick(TransportVegetableInfo transportVegetableInfo);
    }

}
