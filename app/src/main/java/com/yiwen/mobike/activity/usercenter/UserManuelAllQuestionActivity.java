package com.yiwen.mobike.activity.usercenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yiwen.mobike.R;
import com.yiwen.mobike.activity.customer.CustomerServiceWebActivity;
import com.yiwen.mobike.utils.BuidUrl;
import com.yiwen.mobike.views.MySettingView;
import com.yiwen.mobike.views.TabTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserManuelAllQuestionActivity extends AppCompatActivity {

    @BindView(R.id.title_allproblem)
    TabTitleView  mTitleAllproblem;
    @BindView(R.id.tv_regest_login)
    MySettingView mTvRegestLogin;
    @BindView(R.id.tv_planunlock)
    MySettingView mTvPlanunlock;
    @BindView(R.id.tv_cash)
    MySettingView mTvCash;
    @BindView(R.id.tv_return_car)
    MySettingView mTvReturnCar;
    @BindView(R.id.tv_aboutcar)
    MySettingView mTvAboutcar;
    @BindView(R.id.tv_aboutmobike)
    MySettingView mTvAboutmobike;
    @BindView(R.id.tv_credit)
    MySettingView mTvCredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manuel_all_question);
        ButterKnife.bind(this);
        iniEvent();
    }

    private void iniEvent() {
        mTitleAllproblem.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    @OnClick({R.id.tv_regest_login, R.id.tv_planunlock, R.id.tv_cash, R.id.tv_return_car, R.id.tv_aboutcar, R.id.tv_aboutmobike, R.id.tv_credit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_regest_login:
                go2activity("注册与账户", BuidUrl.getAccountSign());
                break;
            case R.id.tv_planunlock:
                go2activity("预约与开关锁", BuidUrl.getUnlockReservation());
                break;
            case R.id.tv_cash:
                go2activity("车费与押金", BuidUrl.getFareDeposit());
                break;
            case R.id.tv_return_car:
                go2activity("还车相关", BuidUrl.getTheCarGuide());
                break;
            case R.id.tv_aboutcar:
                go2activity("关于单车", BuidUrl.getAboutCycling());
                break;
            case R.id.tv_aboutmobike:
                go2activity("关于膜拜", BuidUrl.getAboutMobike());
                break;
            case R.id.tv_credit:
                go2activity("膜拜信用积分", BuidUrl.getMobikeCredit());
                break;
        }
    }

    private void go2activity(String title, String url) {
        startActivity(CustomerServiceWebActivity
                .getCustomerServiceIntent(UserManuelAllQuestionActivity.this, title,
                        url));//BuidUrl.getDepositInstruction())
    }
}
