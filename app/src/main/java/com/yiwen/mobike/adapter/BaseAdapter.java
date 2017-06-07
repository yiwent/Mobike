package com.yiwen.mobike.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;



public abstract class BaseAdapter<T, H extends BaseViewHolder> extends RecyclerView.Adapter<H> {

    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected int mLayoutResId;
    protected OnItemClickListener mListener;
    protected H mViewHolder;

    public interface OnItemClickListener {
        void onClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public BaseAdapter(Context context, List<T> datas, int layoutResId) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
//        mDatas = Collections.synchronizedList(datas);
        mLayoutResId = layoutResId;
    }

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(mLayoutResId, parent, false);
        mViewHolder = (H) new BaseViewHolder(view, mListener);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T item = getItem(position);
        bindData(holder, item);
    }

    @Override
    public int getItemCount() {
        if (!isNull(mDatas))
            return mDatas.size();
        return 0;
    }

    public T getItem(int position) {
        if (!isNull(mDatas))
            return mDatas.get(position);
        return null;
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void clearAll() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public void clearItem(int position, T t) {
        int i = mDatas.indexOf(t);
        mDatas.remove(i);
        notifyItemRemoved(i);
    }

    public void refreshData(List<T> datas) {
        if (!isNull(datas)) {
            clearAll();
            addData(datas);
        }
    }

    public void loadMoreData(List<T> datas) {
        if (!isNull(datas)) {
            int begin = getDataSize();
            addData(mDatas.size(), datas);
            notifyItemRangeInserted(begin, getDataSize());
        }
    }

    public void addData(List<T> datas) {
        addData(0, datas);
    }

    public void addData(int position, List<T> datas) {
        if (!isNull(datas)) {
            mDatas.addAll(position, datas);
            notifyItemRangeInserted(position, datas.size());
        }
    }

    protected boolean isNull(List datas) {
        return datas == null || datas.size() < 0;
    }

    public int getDataSize() {
        return mDatas.size();
    }

    public abstract void bindData(BaseViewHolder holder, T t);
}
