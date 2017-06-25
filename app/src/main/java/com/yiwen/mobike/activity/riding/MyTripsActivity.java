package com.yiwen.mobike.activity.riding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.orhanobut.logger.Logger;
import com.yiwen.mobike.MyApplication;
import com.yiwen.mobike.R;
import com.yiwen.mobike.activity.customer.LastTenTripHistoryActivity;
import com.yiwen.mobike.adapter.BaseAdapter;
import com.yiwen.mobike.adapter.MyTripsAdapter;
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

public class MyTripsActivity extends AppCompatActivity {

    @BindView(R.id.title_trips)
    TabTitleView       mTitleCredit;
    @BindView(R.id.recycler_trips)
    RecyclerView       mRecyclerTrips;
    @BindView(R.id.id_refresh)
    SwipyRefreshLayout mRefreshLayout;
    private MyTripsAdapter         mAdapter;
    private List<MyTripsData>      mData;
    private SpotsDialog            mDialog;
    private BmobQuery<MyTripsData> mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);
        ButterKnife.bind(this);
        initEvent();
        requestData();
    }

    private void requestData() {
        mDialog.show();
        if (mQuery == null) {
            mQuery = new BmobQuery<>("MyTripsData");
            mQuery.addWhereEqualTo("mMyUser", MyApplication.getInstance().getUser().getObjectId());
            mQuery.order("-updatedAt");
        }
        mQuery.findObjects(new FindListener<MyTripsData>() {
            @Override
            public void done(List<MyTripsData> list, BmobException e) {
                if (e == null) {
                    mData = list;
                    showMyTrips();
                } else {
                    ToastUtils.show(MyTripsActivity.this, "暂时没有骑行记录");
                    Logger.d(e);
                }
                dismissMyDialog();
            }
        });
    }

    private void showMyTrips() {
        if (mData == null) {
            ToastUtils.show(MyTripsActivity.this, "暂时没有骑行记录");
            return;
        }
        if (mAdapter == null) {
            mAdapter = new MyTripsAdapter(this, mData);
            mRecyclerTrips.setAdapter(mAdapter);
            mRecyclerTrips.setLayoutManager(new LinearLayoutManager(this));
            mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onClick(View v, int position) {
                  startActivity(TripDetailActivity.
                          getMyIntent(MyTripsActivity.this,mData.get(position)));
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
    private void initEvent() {
        mDialog = new SpotsDialog(this);
        mTitleCredit.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                MyTripsActivity.this.finish();
            }
        });
        mTitleCredit.setOnRightTextViewClickListener(new TabTitleView.OnRightButtonClickListener() {
            @Override
            public void onClick() {
                startActivity(new Intent(MyTripsActivity.this, LastTenTripHistoryActivity.class));
            }
        });
        mRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                requestData();
                if (mRefreshLayout.isRefreshing())
                    mRefreshLayout.setRefreshing(false);

            }
        });
    }
}
