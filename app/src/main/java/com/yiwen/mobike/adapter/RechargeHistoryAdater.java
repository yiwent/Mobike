package com.yiwen.mobike.adapter;

import android.content.Context;

import com.yiwen.mobike.R;
import com.yiwen.mobike.bean.RechargeHistoryData;

import java.util.List;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/19
 * Time: 20:02
 */

public class RechargeHistoryAdater extends SimpleAdapter<RechargeHistoryData> {
    public RechargeHistoryAdater(Context context, List<RechargeHistoryData> datas) {
        super(context, datas, R.layout.item_recharge_histtory);
    }

    @Override
    public void bindData(BaseViewHolder holder, RechargeHistoryData data) {
        holder.getTextView(R.id.id_iv_ispay).setText(data.getStatusCode());
        holder.getTextView(R.id.id_iv_money).setText(data.getCash()+"元");
        holder.getTextView(R.id.id_iv_payapproach).setText(data.getPayApproach());
        holder.getTextView(R.id.id_iv_time).setText(data.getTimestamp());
    }
}
