package com.vegetablestrading.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vegetablestrading.R;
import com.vegetablestrading.customViews.IrregularTextView;
import com.vegetablestrading.interfaces.OnItemClick;

import java.util.ArrayList;

/**
 * Created by ${王sir} on 2017/11/15.
 * application
 */

public class VegetableTypeAdapter extends RecyclerView.Adapter<VegetableTypeAdapter.ViewHolder> {
    private ArrayList<String> arrays;
    private VegetableTypeItemClickInterface vegetableTypeItemClickCallBack;

    public void setData(ArrayList<String> arrays) {
        this.arrays = arrays;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vegetable_types, parent, false);
        VegetableTypeAdapter.ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String bean = arrays.get(position);
        String[] beans = bean.split("&");
        holder.textView.setText(beans[0]);
      final  String[] datas = beans[1].split(",");
        holder.childView.initViews(datas, new OnItemClick() {
            @Override
            public void onClick(int position) {
                if (vegetableTypeItemClickCallBack != null) {
                    vegetableTypeItemClickCallBack.OnVegetableTypeItemClick(datas[position]);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrays == null ? 0 : arrays.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final IrregularTextView childView;
        private final TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.master_type_tv);
            childView = (com.vegetablestrading.customViews.IrregularTextView) itemView.findViewById(R.id.master_type_sv);

        }

    }

    public void setOnVegetableTypeItemClick(VegetableTypeItemClickInterface vegetableTypeItemClickCallBack) {
        this.vegetableTypeItemClickCallBack = vegetableTypeItemClickCallBack;
    }

    public interface VegetableTypeItemClickInterface {

        void OnVegetableTypeItemClick(String vegetableType);
    }
}
