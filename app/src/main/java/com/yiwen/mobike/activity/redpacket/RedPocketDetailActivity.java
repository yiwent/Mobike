package com.yiwen.mobike.activity.redpacket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.orhanobut.logger.Logger;
import com.yiwen.mobike.MyApplication;
import com.yiwen.mobike.R;
import com.yiwen.mobike.adapter.MyRedPocketAdapter;
import com.yiwen.mobike.adapter.decoration.DividerItemDecoration;
import com.yiwen.mobike.bean.MyRedPocketData;
import com.yiwen.mobike.utils.CommonUtils;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.views.TabTitleView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import dmax.dialog.SpotsDialog;

public class RedPocketDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_redpockey)
    TabTitleView       mToolbar;
    @BindView(R.id.recycler_redpockey)
    RecyclerView       mRecycler;
    @BindView(R.id.id_redpockey)
    SwipyRefreshLayout mRefreshLayout;

    private List<MyRedPocketData> mData;
    private MyRedPocketAdapter    mAdapter;
    private SpotsDialog           mDialog;
    private Boolean isFirstin = true;
    private BmobQuery<MyRedPocketData> query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_pocket_detail);
        ButterKnife.bind(this);
        initToolbar();
        initRefreshLayout();
        requestPoketData();
    }

    private void requestPoketData() {
        if (!CommonUtils.isNetworkAvailable(this)){
            ToastUtils.show(this,"暂时没有红包记录");
            return;
        }
        if (query == null) {
            query = new BmobQuery<>("MyRedPocketData");
            query.order("-updatedAt");
            query.addWhereEqualTo("mMyUser", MyApplication.getInstance().getUser());
            query.addWhereEqualTo("isvalid", true);
        }
        if (isFirstin) {
            mDialog = new SpotsDialog(RedPocketDetailActivity.this, "正在加载...");
            mDialog.show();
            isFirstin = false;
        }
        query.findObjects(new FindListener<MyRedPocketData>() {
            @Override
            public void done(List<MyRedPocketData> list, BmobException e) {
                if (e == null) {
                    mData = list;
                    showPoketData();
                } else {
                    ToastUtils.show(RedPocketDetailActivity.this, "暂时没有红包记录");
                    Logger.d(e);
                }
                dismissMyDialog();
            }
        });
    }

    private void showPoketData() {
        if (mData == null) {
            ToastUtils.show(RedPocketDetailActivity.this, "暂时没有红包记录");
            return;
        }
        if (mAdapter == null) {
            mAdapter = new MyRedPocketAdapter(this, mData);
            mRecycler.setAdapter(mAdapter);
            mRecycler.setLayoutManager(new LinearLayoutManager(this));
            mRecycler.addItemDecoration(new DividerItemDecoration(RedPocketDetailActivity.this,LinearLayoutManager.VERTICAL));
        } else {
            mAdapter.refreshData(mData);
        }


    }

    private void dismissMyDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
    private void initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                requestPoketData();
                if (mRefreshLayout.isRefreshing())
                    mRefreshLayout.setRefreshing(false);

            }
        });
    }

    private void initToolbar() {
        mToolbar.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }
}
