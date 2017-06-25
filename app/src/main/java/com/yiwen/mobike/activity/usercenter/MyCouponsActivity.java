package com.yiwen.mobike.activity.usercenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.orhanobut.logger.Logger;
import com.yiwen.mobike.MyApplication;
import com.yiwen.mobike.R;
import com.yiwen.mobike.activity.customer.CustomerServiceWebActivity;
import com.yiwen.mobike.adapter.MyCouponsAdapter;
import com.yiwen.mobike.bean.MyCouponsData;
import com.yiwen.mobike.utils.BuidUrl;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.views.TabTitleView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import dmax.dialog.SpotsDialog;

public class MyCouponsActivity extends AppCompatActivity {

    @BindView(R.id.title_coupons)
    TabTitleView mTitleCoupons;
    @BindView(R.id.et_coupons)
    EditText     mEtCoupons;
    @BindView(R.id.bt_coupons)
    Button       mBtCoupons;
    @BindView(R.id.recycler_coupons)
    RecyclerView mRecyclerCoupons;

    private MyCouponsAdapter         mAdapter;
    private List<MyCouponsData>      mData;
    private SpotsDialog              mDialog;
    private boolean                  isOpen;
    private BmobQuery<MyCouponsData> mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coupons);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initView() {
        mDialog = new SpotsDialog(this);
        requesData();
    }

    private void requesData() {
        mDialog.show();
        if (mQuery == null) {
            mQuery = new BmobQuery<>("MyCouponsData");
            mQuery.setLimit(10);
            mQuery.addWhereEqualTo("mMyUser", MyApplication.getInstance().getUser().getObjectId());
            mQuery.order("-updatedAt");
        }
        mQuery.findObjects(new FindListener<MyCouponsData>() {
            @Override
            public void done(List<MyCouponsData> list, BmobException e) {
                if (e == null) {
                    mData = list;
                    showCoupons();
                } else {
                    ToastUtils.show(MyCouponsActivity.this, "暂时没有优惠信息");
                    Logger.d(e);
                }
                dismissMyDialog();
            }
        });
    }

    private void showCoupons() {
        if (mData == null) {
            ToastUtils.show(MyCouponsActivity.this, "暂时没有优惠");
            return;
        }
        if (mAdapter == null) {
            mAdapter = new MyCouponsAdapter(this, mData);
            mRecyclerCoupons.setAdapter(mAdapter);
            mRecyclerCoupons.setLayoutManager(new LinearLayoutManager(this));
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
        mTitleCoupons.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                MyCouponsActivity.this.finish();
            }
        });
        mTitleCoupons.setOnRightTextViewClickListener(new TabTitleView.OnRightButtonClickListener() {
            @Override
            public void onClick() {
                Intent intent = CustomerServiceWebActivity.
                        getCustomerServiceIntent(MyCouponsActivity.this, "使用说明", BuidUrl.getCoupon());
                startActivity(intent);
            }
        });
        mEtCoupons.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mBtCoupons.setBackgroundResource(R.color.red);
                } else {
                    mBtCoupons.setBackgroundResource(R.color.gray);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.bt_coupons)
    public void onViewClicked() {
        if (!isOpen) {
            isOpen=true;
            mEtCoupons.setVisibility(View.VISIBLE);
            mBtCoupons.setText("兑换");
            mBtCoupons.setBackgroundResource(R.color.gray);
        } else {
            if (TextUtils.isEmpty(mEtCoupons.getText().toString())){
                ToastUtils.show(MyCouponsActivity.this,"请输入兑换码");
            }else {
                mDialog.show();
                String money=mEtCoupons.getText().toString();
                MyCouponsData couponsData=new MyCouponsData();
                couponsData.setMoney(money);
                couponsData.setMyUser(MyApplication.getInstance().getUser());
                SimpleDateFormat formatter   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date curDate =  new Date(System.currentTimeMillis());
                couponsData.setTime( formatter.format(curDate));
                couponsData.setType("1");
                couponsData.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e==null){
                            mEtCoupons.setText("");
                            ToastUtils.show(MyCouponsActivity.this,"兑换成功");
                            requesData();
                        }else {
                            ToastUtils.show(MyCouponsActivity.this,"兑换失败");
                        }
                    }
                });
            }
        }
    }
}
