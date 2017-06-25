package com.yiwen.mobike.activity.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.yiwen.mobike.MyApplication;
import com.yiwen.mobike.R;
import com.yiwen.mobike.adapter.BaseAdapter;
import com.yiwen.mobike.adapter.LastTenTripAdapter;
import com.yiwen.mobike.adapter.decoration.DividerItemDecoration;
import com.yiwen.mobike.bean.MyTripsData;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.views.TabTitleView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import dmax.dialog.SpotsDialog;

public class LastTenTripHistoryActivity extends AppCompatActivity {

    @BindView(R.id.title_lasttrip)
    TabTitleView mTitleLasttrip;
    @BindView(R.id.recycler_lasttrip)
    RecyclerView mRecyclerTrips;
    private LastTenTripAdapter     mAdapter;
    private List<MyTripsData>      mData;
    private SpotsDialog            mDialog;
    private BmobQuery<MyTripsData> mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_ten_trip_history);
        ButterKnife.bind(this);
        initEvent();
        requestData();
    }

    private void initEvent() {
        mDialog = new SpotsDialog(this);
        mTitleLasttrip.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                LastTenTripHistoryActivity.this.finish();
            }
        });
    }

    private void requestData() {
        mDialog.show();
        if (mQuery == null) {
            mQuery = new BmobQuery<>("MyTripsData");
            mQuery.setLimit(10);
            mQuery.addWhereEqualTo("mMyUser", MyApplication.
                    getInstance().getUser().getObjectId());
            mQuery.order("-updatedAt");
        }
        mQuery.findObjects(new FindListener<MyTripsData>() {
            @Override
            public void done(List<MyTripsData> list, BmobException e) {
                if (e == null) {
                    mData = list;
                    showMyTrips();
                } else {
                    ToastUtils.show(LastTenTripHistoryActivity.this, "暂时没有骑行记录");
                    Logger.d(e);
                }
                dismissMyDialog();
            }
        });
    }

    private void showMyTrips() {
        if (mData == null) {
            ToastUtils.show(LastTenTripHistoryActivity.this, "暂时没有骑行记录");
            return;
        }
        if (mAdapter == null) {
            mAdapter = new LastTenTripAdapter(this, mData);
            mRecyclerTrips.setAdapter(mAdapter);
            mRecyclerTrips.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerTrips.addItemDecoration(new DividerItemDecoration(
                    LastTenTripHistoryActivity.this,LinearLayoutManager.VERTICAL));
            mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onClick(View v, int position) {
                    MyTripsData myTripsData=mAdapter.getItem(position);
                 Intent intent= NormalLastRideDescActivity.getMyIntrnt(
                            LastTenTripHistoryActivity.this,myTripsData);
                   startActivity(intent);
                }
            });
        } else {
            mAdapter.refreshData(mData);
        }
    }
    private void dismissMyDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
