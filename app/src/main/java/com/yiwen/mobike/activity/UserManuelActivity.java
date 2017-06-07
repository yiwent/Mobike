package com.yiwen.mobike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yiwen.mobike.R;
import com.yiwen.mobike.views.MySettingView;
import com.yiwen.mobike.views.TabTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserManuelActivity extends AppCompatActivity {

    @BindView(R.id.title_user_manuel)
    TabTitleView  mTitleuser_manuel;
    @BindView(R.id.tv_reportunlock)
    MySettingView mTvReportunlock;
    @BindView(R.id.tv_bike_damage)
    MySettingView mTvBikeDamage;
    @BindView(R.id.tv_yajin)
    MySettingView mTvYajin;
    @BindView(R.id.tv_chongzhi)
    MySettingView mTvChongzhi;
    @BindView(R.id.tv_ReportViolations)
    MySettingView mTvReportViolations;
    @BindView(R.id.tv_notfindcar)
    MySettingView mTvNotfindcar;
    @BindView(R.id.tv_allproblem)
    MySettingView mTvAllproblem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manuel);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {
        mTitleuser_manuel.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                UserManuelActivity.this.finish();
            }
        });

    }

    @OnClick({R.id.tv_reportunlock, R.id.tv_bike_damage, R.id.tv_yajin, R.id.tv_chongzhi,
            R.id.tv_ReportViolations, R.id.tv_notfindcar, R.id.tv_allproblem})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_reportunlock:
               Go2Activity(ReportUnlockFailActivity.class);
                break;
            case R.id.tv_bike_damage:
                Go2Activity(BikeDamageReportActivity.class);
                break;
            case R.id.tv_ReportViolations:
                Go2Activity(ReportViolationsActivity.class);
                break;

            case R.id.tv_allproblem:
                Go2Activity(UserManuelAllQuestionActivity.class);
                break;
            case R.id.tv_yajin:
                break;
            case R.id.tv_chongzhi:
                break;
            case R.id.tv_notfindcar:
                break;
        }
    }

    private void Go2Activity(Class c) {
        Intent intent=new Intent(UserManuelActivity.this,c);
        startActivity(intent);
    }

}
