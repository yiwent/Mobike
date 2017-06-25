package com.yiwen.mobike.adapter;

import android.content.Context;

import com.yiwen.mobike.R;
import com.yiwen.mobike.bean.MyCouponsData;

import java.util.List;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/20
 * Time: 17:00
 */

public class MyCouponsAdapter extends SimpleAdapter<MyCouponsData> {
    public MyCouponsAdapter(Context context, List<MyCouponsData> datas) {
        super(context, datas, R.layout.item_coupon);
    }

    @Override
    public void bindData(BaseViewHolder holder, MyCouponsData coupon) {
        holder.getTextView(R.id.id_iv_maney).setText("￥"+coupon.getMoney()+"元");
        holder.getTextView(R.id.id_iv_time).setText("有效期至"+coupon.getTime());
        if ( coupon.getType().equals("1"))
        holder.getTextView(R.id.id_iv_type).setText("不限制车型使用");
    }
}
