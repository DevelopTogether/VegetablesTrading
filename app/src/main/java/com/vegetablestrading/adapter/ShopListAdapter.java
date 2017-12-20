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
 * application 商城列表适配器
 */

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {

    private ArrayList<TransportVegetableInfo> arrayList;
    private Context  context;
    private ShopListItemClick ShopListItemClick;

    public void setData(ArrayList<TransportVegetableInfo> arrayList) {
        this.arrayList = arrayList;
    }
    public ShopListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_list_item, parent, false));

        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       final TransportVegetableInfo bean = arrayList.get(position);
        Glide.with(context).load(R.drawable.app_logo)
                .placeholder(R.drawable.loading_icon) // can also be a drawable
                .error(R.drawable.unload_icon) // will be displayed if the image cannot be loaded
                .into(holder.mVegetableIconIv);
        holder.mShopVegetableNameTv.setText(bean.getVegetableName());
        holder.mShopVegetableDescripTv.setText(bean.getVegetableInfo());
        holder.mShopVegetablePriceTv.setText(bean.getVegetablePrice());
        holder.mBuyRightnowTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ShopListItemClick != null) {
                    ShopListItemClick.buyRightnow();
                }
            }
        });
        holder.shop_list_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ShopListItemClick != null) {
                    ShopListItemClick.itemClick(bean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList == null ? 0 : arrayList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mVegetableIconIv;
        TextView mShopVegetableNameTv;
        TextView mShopVegetableDescripTv;
        TextView mShopVegetablePriceTv;
        TextView mBuyRightnowTv;
        LinearLayout shop_list_ll;

        ViewHolder(View view) {
            super(view);
            this.mVegetableIconIv = (ImageView) view.findViewById(R.id.vegetable_icon_iv);
            this.mShopVegetableNameTv = (TextView) view.findViewById(R.id.shop_vegetable_name_tv);
            this.mShopVegetableDescripTv = (TextView) view.findViewById(R.id.shop_vegetable_descrip_tv);
            this.mShopVegetablePriceTv = (TextView) view.findViewById(R.id.shop_vegetable_price_tv);
            this.mBuyRightnowTv = (TextView) view.findViewById(R.id.buy_rightnow_tv);
            this.shop_list_ll = (LinearLayout) view.findViewById(R.id.shop_list_ll);
        }
    }
    public void setShopListItemClick(ShopListItemClick ShopListItemClick) {
        this.ShopListItemClick = ShopListItemClick;
    }

    public interface ShopListItemClick {
        void itemClick(TransportVegetableInfo transportVegetableInfo);
        void buyRightnow();
    }
}
