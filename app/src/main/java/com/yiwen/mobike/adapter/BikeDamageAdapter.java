package com.yiwen.mobike.adapter;

import android.content.Context;

import com.yiwen.mobike.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/6/24
 * Time: 14:53
 */

public class BikeDamageAdapter extends SimpleAdapter<String>{

    private List<Boolean> mList;

    public BikeDamageAdapter(Context context, List<String> datas ) {
        super(context, datas, R.layout.item_bike_damage);
        mList=new ArrayList<>(datas.size());
        for (int i=0;i<datas.size();i++){
            mList.add(false);
        }
    }

    @Override
    public void bindData(BaseViewHolder holder, String s) {
        holder.getTextView(R.id.id_tv_type).setText(s);
        int i=  holder.getCurrentPosition();
      if (mList.get(i)){
        holder.getImageView(R.id.id_iv_select).setImageResource(R.drawable.single_choice_radio_checked);
      }else {
          holder.getImageView(R.id.id_iv_select).setImageResource(R.drawable.single_choice_radio_unchecked);
      }
    }

    public void selectItem(int position) {
        boolean b=!mList.get(position);
        mList.set(position,b);
        notifyDataSetChanged();
    }
    public String getType(){
        StringBuilder type=new StringBuilder();
       for (int i=0;i<mList.size();i++){
         if (mList.get(i)) {
             type.append(getItem(i)).append("*") ;
         }
       }
        return type.toString();
    }
}
