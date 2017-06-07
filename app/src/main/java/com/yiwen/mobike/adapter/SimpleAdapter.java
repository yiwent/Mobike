package com.yiwen.mobike.adapter;

import android.content.Context;

import java.util.List;


public abstract class SimpleAdapter<T> extends BaseAdapter<T, BaseViewHolder> {
    public SimpleAdapter(Context context, List<T> datas, int layoutResId) {
        super(context, datas, layoutResId);
    }
}
