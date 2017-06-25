package com.yiwen.mobike.adapter;

import android.content.Context;

import com.yiwen.mobike.R;
import com.yiwen.mobike.bean.MyRedPocketData;

import java.util.List;




/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/24
 * Time: 0:59
 */

public class MyRedPocketAdapter extends SimpleAdapter<MyRedPocketData> {
    public MyRedPocketAdapter(Context context, List<MyRedPocketData> datas) {
        super(context, datas, R.layout.item_myrepocket);
    }

    @Override
    public void bindData(BaseViewHolder holder, MyRedPocketData data) {
        holder.getTextView(R.id.id_iv_carnub).setText(data.getCarNub()+"的红包");
        holder.getTextView(R.id.id_iv_money).setText("+"+data.getMoney()+"元");
        holder.getTextView(R.id.id_iv_time).setText(data.getTime());
    }
}
