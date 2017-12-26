package com.vegetablestrading.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vegetablestrading.R;
import com.vegetablestrading.interfaces.MineModeItemClickedListener;

/**
 * Created by ${王sir} on 2017/11/9.
 * application
 */

public class MineModeAdapter extends RecyclerView.Adapter<MineModeAdapter.ViewHolder> {

    private String[] names = {"会员激活", "配送记录", "我的申请", "我的积分", "我的订单", "我的采摘", "我的信息", "修改密码", "退会", "关于我们", "安全退出","地址管理"};
    private Integer[] icons = {R.drawable.mine_activated_icon, R.drawable.mine_tradelist, R.drawable.mine_apply, R.drawable.integral_icon, R.drawable.mine_order, R.drawable.mine_pick_list, R.drawable.mine_info, R.drawable.mine_modify_pwd, R.drawable.quit_member, R.drawable.mine_about_us, R.drawable.mine_safe_exit, R.drawable.mine_addr_manager};
    private MineModeItemClickedListener listener;
    private int position;

    public void setOnItemClickListener(MineModeItemClickedListener listener) {
        this.listener = listener;
    }

    @Override
    public MineModeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_mine, parent, false);
        MineModeAdapter.ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MineModeAdapter.ViewHolder holder, int position) {
        this.position = position;
        holder.imageView.setImageResource(icons[position]);
        holder.textView.setText(names[position]);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.mine_iv);
            textView = (TextView) itemView.findViewById(R.id.mine_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.itemClickedListener(textView);
                    }
                }
            });
        }
    }
}
