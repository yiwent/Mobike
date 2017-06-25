package com.yiwen.mobike.adapter;

import android.content.Context;
import android.widget.TextView;

import com.yiwen.mobike.R;
import com.yiwen.mobike.bean.MyTripsData;

import java.util.List;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/20
 * Time: 20:12
 */

public class MyTripsAdapter extends SimpleAdapter<MyTripsData> {
    public MyTripsAdapter(Context context, List<MyTripsData> datas) {
        super(context, datas, R.layout.item_mytrips);
    }

    @Override
    public void bindData(BaseViewHolder holder, MyTripsData mData) {
        TextView textView=holder.getTextView(R.id.id_tv_time_vecto);
        if (holder.getAdapterPosition()==getItemCount()-1)
            textView.setBackgroundResource(R.color.white);
        else textView.setBackgroundResource(R.drawable.my_trip_item_time_line);
        holder.getTextView(R.id.id_iv_time).setText(mData.getTime());
        holder.getTextView(R.id.id_iv_card).setText("自行车编号："+mData.getCarNub());
        holder.getTextView(R.id.id_iv_minus).setText("骑行时间："+mData.getRideTime()+"分钟");
        holder.getTextView(R.id.id_iv_money).setText("骑行花费："+mData.getMoney()+"元");
    }

}
