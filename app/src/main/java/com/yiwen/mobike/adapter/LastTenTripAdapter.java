package com.yiwen.mobike.adapter;

import android.content.Context;

import com.yiwen.mobike.R;
import com.yiwen.mobike.bean.MyTripsData;

import java.util.List;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/20
 * Time: 21:27
 */

public class LastTenTripAdapter extends SimpleAdapter<MyTripsData> {
    public LastTenTripAdapter(Context context, List<MyTripsData> datas) {
        super(context, datas, R.layout.item_lastride);
    }

    @Override
    public void bindData(BaseViewHolder holder, MyTripsData mData) {
        holder.getTextView(R.id.id_iv_time).setText(mData.getTime());
        holder.getTextView(R.id.id_iv_card).setText("自行车编号："+mData.getCarNub());
        holder.getTextView(R.id.id_iv_minus).setText("骑行时间："+mData.getRideTime()+"分钟");
        holder.getTextView(R.id.id_iv_money).setText("骑行花费："+mData.getMoney()+"元");
    }
}
