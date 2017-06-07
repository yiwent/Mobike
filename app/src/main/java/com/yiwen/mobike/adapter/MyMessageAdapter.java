package com.yiwen.mobike.adapter;

import android.content.Context;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yiwen.mobike.R;
import com.yiwen.mobike.bean.MyMessage;

import java.util.List;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/5
 * Time: 15:53
 */

public class MyMessageAdapter extends SimpleAdapter<MyMessage> {

    public MyMessageAdapter(Context context, List<MyMessage> datas) {
        super(context, datas, R.layout.item_my_message);
    }

    @Override
    public void bindData(BaseViewHolder holder, MyMessage myMessage) {
        holder.getTextView(R.id.tv_massage_time).setText(myMessage.getTime());
        holder.getTextView(R.id.tv_massage_content).setText(myMessage.getDecrdescription());
        SimpleDraweeView sdw = (SimpleDraweeView) holder.getView(R.id.iv_massage_photo);
        sdw.setImageURI(myMessage.getImgUrl());
    }
}
