package com.yiwen.mobike.adapter;

import android.content.Context;

import com.yiwen.mobike.R;

import java.util.List;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/22
 * Time: 20:16
 */

public class ChargeAmountAdapter extends SimpleAdapter<String> {
    private Context mContext;
    public int selectPosition = 0;

    public ChargeAmountAdapter(Context context, List<String> datas) {
        super(context, datas, R.layout.item_charge_text);
        mContext = context;
    }

    @Override
    public void bindData(BaseViewHolder holder, String s) {
        holder.getTextView(R.id.id_tv_charge).setText(s);
        if (holder.getAdapterPosition() == selectPosition) {
            holder.getTextView(R.id.id_tv_charge).setTextColor(mContext.getResources().getColor(R.color.white));
            holder.getTextView(R.id.id_tv_charge).setBackgroundResource(R.color.red);
        } else {
            holder.getTextView(R.id.id_tv_charge).setTextColor(mContext.getResources().getColor(R.color.black));
            holder.getTextView(R.id.id_tv_charge).setBackgroundResource(R.color.bg_color);
        }
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

}
