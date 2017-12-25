package com.vegetablestrading.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vegetablestrading.R;
import com.vegetablestrading.bean.TransportVegetableInfo;
import com.vegetablestrading.utils.CalendarUtil;
import com.vegetablestrading.utils.Constant;

import java.util.List;

/**
 * Created by ${王sir} on 2017/11/21.
 * application 配送列表适配器
 */

public class TransportListAdapter extends RecyclerView.Adapter<TransportListAdapter.ViewHolder> {

    private List<TransportVegetableInfo> arrayList;
    private Context context;
    private OnTransportListItemInterface onTransportListItemInterface;

    public void setData(List<TransportVegetableInfo> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
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
        Glide.with(context)
                .load(Uri.parse(Constant.company_url+bean.getVegetableIcon()))
                .load(Uri.parse(Constant.company_url + bean.getVegetableIcon()))
                .placeholder(R.drawable.loading_icon) // can also be a drawable
                .error(R.drawable.unload_icon) // will be displayed if the image cannot be loaded
                .into(holder.mVegetableIconIv);
        holder.mVegetableNameTv.setText(bean.getVegetableName());
        holder.mVegetableWeightTv.setText("重量：" + bean.getWeight() + "g");
        if (TextUtils.isEmpty(bean.getVegetableInfo())) {
            holder.mVegetableDescripTv.setVisibility(View.GONE);
        } else {
            holder.mVegetableDescripTv.setVisibility(View.VISIBLE);
            holder.mVegetableDescripTv.setText(bean.getVegetableInfo());
        }
        holder.mStartTimeTransportTv.setText("开始有效期：" + CalendarUtil.getSpecialTypeTime(bean.getTransportStartTime()));
        holder.mEndTimeTransportTv.setText("结束有效期：" + CalendarUtil.getSpecialTypeTime(bean.getTransportEndTime()));
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
