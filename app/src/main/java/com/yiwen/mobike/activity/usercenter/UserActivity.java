package com.yiwen.mobike.activity.usercenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yiwen.mobike.MyApplication;
import com.yiwen.mobike.R;
import com.yiwen.mobike.activity.login.LoginActivity;
import com.yiwen.mobike.activity.pay.WalletActivity;
import com.yiwen.mobike.activity.riding.MyTripsActivity;
import com.yiwen.mobike.bean.Credit;
import com.yiwen.mobike.bean.MyCouponsData;
import com.yiwen.mobike.bean.MyUser;
import com.yiwen.mobike.bean.RideSummary;
import com.yiwen.mobike.utils.CommonUtils;
import com.yiwen.mobike.views.MySettingView;
import com.yiwen.mobike.views.MyToolBar;
import com.yiwen.mobike.views.NumberAnimTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserActivity extends AppCompatActivity {

    private static final String TAG ="UserActivity" ;
    @BindView(R.id.iv_avatar)
    CircleImageView         mIvAvatar;
    @BindView(R.id.tv_nick)
    TextView                mTvNickNmane;
    @BindView(R.id.tv_credit)
    TextView                mTvCredit;
    @BindView(R.id.tv_ride)
    NumberAnimTextView      mTvRide;
    @BindView(R.id.tv_save)
    NumberAnimTextView      mTvSave;
    @BindView(R.id.tv_kaluli)
    NumberAnimTextView      mTvKaluli;
    @BindView(R.id.tv_my_money)
    MySettingView           mTvMyMoney;
    @BindView(R.id.tv_my_youhui)
    MySettingView           mTvMyYouhui;
    @BindView(R.id.tv_my_distance)
    MySettingView           mTvMyDistance;
    @BindView(R.id.tv_my_mymassege)
    MySettingView           mTvMyMymassege;
    @BindView(R.id.tv_my_invent)
    MySettingView           mTvMyInvent;
    @BindView(R.id.tv_my_guide)
    MySettingView           mTvMyGuide;
    @BindView(R.id.tv_my_setting)
    MySettingView           mTvMysetting;
    @BindView(R.id.toolbar_use)
    MyToolBar               mToolbar_use;
    @BindView(R.id.collapsinglayout)
    CollapsingToolbarLayout mCollapsinglayout;
    @BindView(R.id.id_tv_netfail)
    TextView                mTvNetfail;
    @BindView(R.id.id_lo_ride_summary)
    LinearLayout            mLoRideSummary;
    MyUser myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        initView();
        initData();
        initEvent();
    }


    private void initEvent() {

        mToolbar_use.setOnLeftButtonClickListener(new MyToolBar.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                UserActivity.this.finish();
            }
        });
    }

    private void initData() {
    }

    private void initView() {
        initToolbar();
        if (!CommonUtils.isNetworkAvailable(getApplication())){
            mTvNetfail.setVisibility(View.VISIBLE);
            mLoRideSummary.setVisibility(View.GONE);
        }
        myUser = MyApplication.getInstance().getUser();
        if (myUser != null){
            if (myUser.getPicUser()!=null){
            Glide.with(this)
                    .load(myUser.getPicUser().getUrl())
                    .error(getResources().getDrawable(R.drawable.avatar_default_login))
                    .into(mIvAvatar);
            }
            mTvNickNmane.setText(myUser.getNickName());
            mTvMyMoney.setRigtTvText(myUser.getMoney()+"元");
        }
        BmobQuery<RideSummary> query = new BmobQuery<RideSummary>();
        query.addWhereEqualTo("mMyUser", myUser);
        query.setLimit(1);
        query.findObjects(new FindListener<RideSummary>() {
            @Override
            public void done(List<RideSummary> list, BmobException e) {
                if (e == null&&list!=null) {
                    RideSummary rideSummary=list.get(0);
                    mTvRide.setNumberString(rideSummary.getRide());
                    mTvSave.setNumberString(rideSummary.getSave());
                    mTvKaluli.setNumberString(rideSummary.getKaluli());
                } else {
                    Log.d(TAG, "done: RideSummary"+e);
                    mTvRide.setNumberString("0");
                    mTvSave.setNumberString("0");
                    mTvKaluli.setNumberString("0");
                }
            }
        });
        BmobQuery<Credit> credit = new BmobQuery<Credit>();
        credit.addWhereEqualTo("mMyUser", myUser);
        credit.setLimit(1);
        credit.findObjects(new FindListener<Credit>() {
            @Override
            public void done(List<Credit> list, BmobException e) {
                if (e == null&&list!=null) {
                    Credit cr=list.get(0);
                   mTvCredit.setText("信用积分"+cr.getCreditNub()+"");
                } else {
                    Log.d(TAG, "done: Credit"+e);
                    mTvCredit.setText("信用积0");
                }
            }
        });
        BmobQuery<MyCouponsData> count = new BmobQuery<MyCouponsData>();
        count.addWhereEqualTo("mMyUser", myUser);
        count.count(MyCouponsData.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e==null&&integer!=0){
                    mTvMyYouhui.setRigtTvText(integer+"张");
                }else {
                    mTvMyYouhui.setRigtTvText("");
                    Log.d(TAG, "done:MyCouponsData "+e);
                }
            }
        });


    }

    private void initToolbar() {
        setSupportActionBar(mToolbar_use);
        //        ActionBar actionBar = getSupportActionBar();
        //        if (actionBar != null) {
        //            actionBar.setDisplayHomeAsUpEnabled(true);
        //        }
        //        mToolbar_use.setNavigationIcon(R.drawable.up_arrow);
        //        mCollapsinglayout.setTitleEnabled(false);
        //        mCollapsinglayout.setCollapsedTitleGravity(Gravity.CENTER_HORIZONTAL);//收
        //        mCollapsinglayout.setExpandedTitleGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);
        //        mCollapsinglayout.setTitle("个人中心");
    }

    @OnClick({R.id.iv_avatar, R.id.tv_credit, R.id.tv_my_money, R.id.tv_my_youhui,
            R.id.tv_my_distance, R.id.tv_my_mymassege, R.id.tv_my_invent,
            R.id.tv_my_guide,R.id.tv_my_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_avatar:
                Go2Activity(UserDetailActivity.class);
                break;
            case R.id.tv_credit:
                Go2Activity(MyCreditActivity.class);
                break;
            case R.id.tv_my_money:
                Go2Activity(WalletActivity.class);
                break;
            case R.id.tv_my_youhui:
                Go2Activity(MyCouponsActivity.class);
                break;
            case R.id.tv_my_distance:
                Go2Activity(MyTripsActivity.class);
                break;
            case R.id.tv_my_mymassege:
                Go2Activity(MyMessagesActivity.class);
                break;
            case R.id.tv_my_invent:
                Go2Activity(InviteFriendActivity.class);
                break;
            case R.id.tv_my_guide:
                Go2Activity(UserManuelActivity.class);
                break;
            case R.id.tv_my_setting:
                Go2Activity(SettingsActivity.class);
                break;
        }
    }

    private void Go2Activity(Class c) {
        Intent intent=new Intent(UserActivity.this,c);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        myUser= MyApplication.getInstance().getUser();
        if (myUser==null){
            startActivity(new Intent(UserActivity.this, LoginActivity.class));
            finish();
        }
        initView();
    }
}
