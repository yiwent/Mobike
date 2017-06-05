package com.yiwen.mobike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.orhanobut.logger.Logger;
import com.yiwen.mobike.R;
import com.yiwen.mobike.adapter.BaseAdapter;
import com.yiwen.mobike.adapter.MyMessageAdapter;
import com.yiwen.mobike.bean.MyMessage;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.views.MyToolBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import dmax.dialog.SpotsDialog;

public class MyMessagesActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_massage)
    MyToolBar             mToolbarMassage;
    @BindView(R.id.recycler_massage)
    RecyclerView          mRecyclerMassage;
    @BindView(R.id.id_refresh)
    MaterialRefreshLayout mRefreshLayout;

    private List<MyMessage>  mMyMessages;
    private MyMessageAdapter mAdapter;
    private SpotsDialog      mDialog;
    private Boolean isFirstin = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);
        ButterKnife.bind(this);
        initToolbar();
        requestMassage();
        initRefreshLayout();
    }

    private void initRefreshLayout() {
        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                requestMassage();
                mRecyclerMassage.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                requestMassage();//实际活动较少，上下拉刷新不需要分页
                mRefreshLayout.finishRefreshLoadMore();
            }

        });
    }

    private void requestMassage() {
        BmobQuery<MyMessage> query = new BmobQuery<>("MyMessage");
        //   query.setLimit(4);
        if (isFirstin) {
            mDialog = new SpotsDialog(MyMessagesActivity.this, "正在加载...");
            mDialog.show();
        }
        query.findObjects(new FindListener<MyMessage>() {
            @Override
            public void done(List<MyMessage> list, BmobException e) {
                if (e == null) {
                    mMyMessages = list;
                    showMessage();
                } else {
                    ToastUtils.show(MyMessagesActivity.this, "暂时没有消息");
                    Logger.d(e);
                }
                dismissMyDialog();
                isFirstin=false;
            }
        });

    }

    private void dismissMyDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    private void showMessage() {
        if (mMyMessages == null) {
            ToastUtils.show(MyMessagesActivity.this, "暂时没有消息");
            return;
        }
        if (mAdapter == null) {
            mAdapter = new MyMessageAdapter(this, mMyMessages);
            mRecyclerMassage.setAdapter(mAdapter);
            mRecyclerMassage.setLayoutManager(new LinearLayoutManager(this));

            mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onClick(View v, int position) {
                    String url = mMyMessages.get(position).getClickUrl();
                    String title = mMyMessages.get(position).getDecrdescription();
                    Intent intent = new Intent(MyMessagesActivity.this, MessageDetailActivity.class);
                    intent.putExtra(MessageDetailActivity.URL, url);
                    intent.putExtra(MessageDetailActivity.TITLE, title);
                    startActivity(intent);
                }
            });
        } else {
            mAdapter.refreshData(mMyMessages);
        }


    }

    private void initToolbar() {
        mToolbarMassage.setOnLeftButtonClickListener(new MyToolBar.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                onBackPressed();
            }
        });
    }
}
