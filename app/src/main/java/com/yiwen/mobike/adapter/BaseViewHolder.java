package com.yiwen.mobike.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private BaseAdapter.OnItemClickListener mListener;
    private SparseArray<View>               mViews;

    public BaseViewHolder(View itemView, BaseAdapter.OnItemClickListener listener) {
        super(itemView);
        mViews = new SparseArray<>();
        mListener = listener;
        itemView.setOnClickListener(this);
    }

    public TextView getTextView(int id) {
        return findView(id);
    }

    public ImageView getImageView(int id) {
        return findView(id);
    }

    public Button getButton(int id) {
        return findView(id);
    }

    public View getView(int id) {
        return findView(id);
    }

    public CheckBox getCheckBox(int id) {
        return findView(id);
    }

    private <T extends View> T findView(int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            mViews.put(id, view);
        }
        return (T) view;
    }

    public int getCurrentPosition() {
        return getLayoutPosition();
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onClick(v, getCurrentPosition());
        }
    }
}
