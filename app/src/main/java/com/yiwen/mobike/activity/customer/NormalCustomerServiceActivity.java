package com.yiwen.mobike.activity.customer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yiwen.mobike.MyApplication;
import com.yiwen.mobike.R;
import com.yiwen.mobike.bean.MyTripsData;
import com.yiwen.mobike.utils.BuidUrl;
import com.yiwen.mobike.utils.CommonUtils;
import com.yiwen.mobike.views.MySettingView;
import com.yiwen.mobike.views.TabTitleView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import dmax.dialog.SpotsDialog;

public class NormalCustomerServiceActivity extends AppCompatActivity {

    @BindView(R.id.title_other)
    TabTitleView  mTitleOther;
    @BindView(R.id.id_tv_needhelp)
    TextView      mTvNeedhelp;
    @BindView(R.id.iv_card_nub)
    TextView      mCardNub;
    @BindView(R.id.iv_time_start)
    TextView      mTimeStart;
    @BindView(R.id.iv_teme_end)
    TextView      mIvTimeEnd;
    @BindView(R.id.tv_history)
    MySettingView mTvHistory;
    @BindView(R.id.tv_find_car_problem)
    MySettingView mTvFindCarProblem;
    @BindView(R.id.tv_unlock_fail)
    MySettingView mTvUnlockFail;
    @BindView(R.id.tv_register_login)
    MySettingView mTvRegisterLogin;
    @BindView(R.id.tv_cash)
    MySettingView mTvCash;
    @BindView(R.id.tv_credit)
    MySettingView mTvCredit;
    @BindView(R.id.tv_return_car)
    MySettingView mTvReturnCar;
    @BindView(R.id.tv_services)
    MySettingView mTvServices;
    @BindView(R.id.lo_car_nub)
    LinearLayout  mLoCar;
    @BindView(R.id.id_tv_netfail)
    TextView      mNetfail;
    private MyTripsData            mData;
    private SpotsDialog            mDialog;
    private BmobQuery<MyTripsData> mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_customer_service);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTitleOther.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        mData = new MyTripsData();
        mDialog = new SpotsDialog(this);
        mDialog.show();
        mLoCar.setVisibility(View.GONE);
        mTvHistory.setVisibility(View.GONE);
        if (!CommonUtils.isNetworkAvailable(this))
            fail();
        if (mQuery == null) {
            mQuery = new BmobQuery<>("MyTripsData");
            mQuery.addWhereEqualTo("mMyUser", MyApplication.getInstance().getUser().getObjectId());
            mQuery.order("-updatedAt");
            mQuery.setLimit(1);
        }
        mQuery.findObjects(new FindListener<MyTripsData>() {
            @Override
            public void done(List<MyTripsData> list, BmobException e) {
                if (e == null&&!list.isEmpty()) {
                    showData(list.get(0));
                } else {
                    mLoCar.setVisibility(View.GONE);
                    mTvHistory.setVisibility(View.GONE);
                    mNetfail.setVisibility(View.GONE);
                    Logger.d(e);
                }
                dismissMyDialog();
            }
        });

    }

    private void showData(MyTripsData tripsData) {
        mData=tripsData;
        mLoCar.setVisibility(View.VISIBLE);
        mTvHistory.setVisibility(View.VISIBLE);
        mNetfail.setVisibility(View.GONE);
        mCardNub.setText(tripsData.getCarNub());
        mTimeStart.setText(tripsData.getTime());
        mIvTimeEnd.setText(tripsData.getRideTime());
    }

    private void dismissMyDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    private void fail() {
        mLoCar.setVisibility(View.GONE);
        mTvHistory.setVisibility(View.GONE);
        mNetfail.setVisibility(View.VISIBLE);
    }

    public static Intent getMyIntent(Context context, String carNub) {
        Intent intent = new Intent(context, RidingOtherIssueActivity.class);
        intent.putExtra("carNub", carNub);
        return intent;
    }

    @OnClick({R.id.id_tv_needhelp, R.id.tv_history, R.id.tv_find_car_problem,
            R.id.tv_unlock_fail, R.id.tv_register_login, R.id.tv_cash,
            R.id.tv_credit, R.id.tv_return_car, R.id.tv_services})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_tv_needhelp:
                toNeedhelp();
                break;
            case R.id.tv_history:
                starIntent(LastTenTripHistoryActivity.class);
                break;
            case R.id.tv_find_car_problem:
                starIntent(BikeDamageReportActivity.class);
                break;
            case R.id.tv_unlock_fail:
                starIntent(ReportUnlockFailActivity.class);
                break;
            case R.id.tv_register_login:
                startActivity(CustomerServiceWebActivity
                        .getCustomerServiceIntent(NormalCustomerServiceActivity.this, "注册与登陆",
                                BuidUrl.getAccountSign()));
                break;
            case R.id.tv_cash:
                startActivity(CustomerServiceWebActivity
                        .getCustomerServiceIntent(NormalCustomerServiceActivity.this, "车费与押金",
                                BuidUrl.getFareDeposit()));
                break;
            case R.id.tv_credit:
                startActivity(CustomerServiceWebActivity
                        .getCustomerServiceIntent(NormalCustomerServiceActivity.this, "我的膜拜信用问题",
                                BuidUrl.getMobikeCredit()));
                break;
            case R.id.tv_return_car:
                startActivity(CustomerServiceWebActivity
                        .getCustomerServiceIntent(NormalCustomerServiceActivity.this, "还车相关",
                                BuidUrl.getTheCarGuide()));
                break;
            case R.id.tv_services:
                startActivity(CustomerServiceWebActivity
                        .getCustomerServiceIntent(NormalCustomerServiceActivity.this, "客户服务进度查询",
                                BuidUrl.getProgrestatus()));
                break;
        }
    }

    private void toNeedhelp() {
        if (mData!=null)
        startActivity(NormalLastRideDescActivity.
                getMyIntrnt(NormalCustomerServiceActivity.this, mData));
    }

    private void starIntent(Class c) {
        Intent intent = new Intent(NormalCustomerServiceActivity.this, c);
        startActivity(intent);
    }
}
