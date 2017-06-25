package com.yiwen.mobike.activity.pay;

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
import com.yiwen.mobike.activity.customer.CustomerServiceWebActivity;
import com.yiwen.mobike.adapter.RechargeHistoryAdater;
import com.yiwen.mobike.adapter.decoration.DividerItemDecoration;
import com.yiwen.mobike.bean.RechargeHistoryData;
import com.yiwen.mobike.utils.BuidUrl;
import com.yiwen.mobike.utils.CommonUtils;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.views.NetworkUnavailableView;
import com.yiwen.mobike.views.TabTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import dmax.dialog.SpotsDialog;

public class RechargeHistoryActivity extends AppCompatActivity {

    @BindView(R.id.title_wallet)
    TabTitleView           mTitleWallet;
    @BindView(R.id.recycler_charge_history)
    RecyclerView           mRecyclerChargeHistory;
    @BindView(R.id.swipeRefresg_charge_history)
    SwipyRefreshLayout     mRefreshLayout;
    @BindView(R.id.lo_networt)
    NetworkUnavailableView unavailableView;

    private List<RechargeHistoryData> mDatas = new ArrayList<>();
    private RechargeHistoryAdater mAdapter;
    private SpotsDialog           mDialog;
    private Boolean isFirstin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_history);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initEvent() {
        mTitleWallet.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                RechargeHistoryActivity.this.finish();
            }
        });
        mTitleWallet.setOnRightTextViewClickListener(new TabTitleView.OnRightButtonClickListener() {
            @Override
            public void onClick() {
                Intent intent = CustomerServiceWebActivity.getCustomerServiceIntent(
                        RechargeHistoryActivity.this, "退押金说明", BuidUrl.getPayBackExplanation());
                startActivity(intent);
            }
        });

    }

    private void initView() {
        mDialog = new SpotsDialog(RechargeHistoryActivity.this, "正在加载...");
        if (!CommonUtils.isNetworkAvailable(this)) {
            unavailableView.setVisibility(View.VISIBLE);
        } else {
            unavailableView.setVisibility(View.GONE);
            requestData();
        }
        initRefreshLayout();
    }

    private void initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (!CommonUtils.isNetworkAvailable(RechargeHistoryActivity.this)) {
                    unavailableView.setVisibility(View.VISIBLE);
                    mRefreshLayout.setRefreshing(false);
                    return;
                }
                requestData();
                if (mRefreshLayout.isRefreshing())
                    mRefreshLayout.setRefreshing(false);

            }
        });
    }

    private void requestData() {
        BmobQuery<RechargeHistoryData> query = new BmobQuery<>("RechargeHistoryData");
        query.order("-updatedAt");
        query.addWhereEqualTo("mMyUser", MyApplication.getInstance().getUser().getObjectId());
        //query.setLimit(4);//最新四条活动信息
        if (isFirstin) {
            mDialog.show();
            isFirstin = false;
        }
        query.findObjects(new FindListener<RechargeHistoryData>() {
            @Override
            public void done(List<RechargeHistoryData> list, BmobException e) {
                if (e == null) {
                    mDatas = list;
                    showHistory();
                } else {
                    ToastUtils.show(RechargeHistoryActivity.this, "暂时没有消息");
                    Logger.d(e);
                }
                dismissMyDialog();
            }
        });

    }

    private void showHistory() {
        if (mDatas == null) {
            ToastUtils.show(RechargeHistoryActivity.this, "没有充值记录");
            return;
        }
        if (mAdapter == null) {
            mAdapter = new RechargeHistoryAdater(this, mDatas);
            mRecyclerChargeHistory.setAdapter(mAdapter);
            mRecyclerChargeHistory.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerChargeHistory.addItemDecoration(new DividerItemDecoration(
                    RechargeHistoryActivity.this, LinearLayoutManager.VERTICAL));
        } else {
            mAdapter.refreshData(mDatas);
        }
    }

    private void dismissMyDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @OnClick(R.id.lo_networt)
    public void onViewClicked() {
        if (CommonUtils.isNetworkAvailable(this)) {
            this.mDialog.show();
            unavailableView.setVisibility(View.GONE);
            requestData();
        } else {
            return;
        }

    }
}
