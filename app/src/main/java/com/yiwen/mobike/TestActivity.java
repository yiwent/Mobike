package com.yiwen.mobike;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yiwen.mobike.bean.MyTripsData;
import com.yiwen.mobike.bean.Credit;
import com.yiwen.mobike.bean.MyMessage;
import com.yiwen.mobike.bean.MyUser;
import com.yiwen.mobike.bean.RechargeHistoryData;
import com.yiwen.mobike.bean.RideSummary;
import com.yiwen.mobike.views.LoadingPageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";
    @BindView(R.id.bt_test)
    Button   mBtTest;
    @BindView(R.id.tv_test)
    TextView mTvTest;

    @BindView(R.id.loading)
    LoadingPageView mLoadingPageView;
    MyUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        mUser = BmobUser.getCurrentUser(MyUser.class);
        Log.d(TAG, "onCreate: " + mUser.toString());
        //  mLoadingPageView.show();
    }

    @OnClick(R.id.bt_test)
    public void onViewClicked() {
        //  mLoadingPageView.hide();
        //cheackUser();
        insert6();
    }

    private void updata() {

    }

    private void cheackUser() {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereEqualTo("username", "18290022002");
        query.setLimit(1);
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                if (e == null) {
                    if (list.size() > 0 && list != null) {
                        Log.d(TAG, "cheackUser: ok");
                    } else {
                        Log.d(TAG, "cheackUser: not");
                    }
                } else {
                    Log.d(TAG, "done: " + e);
                }
            }
        });
    }

    private void insertMessage1() {
        MyMessage r = new MyMessage();
        r.setClickUrl("https://activity.huaruntong.cn/mobike0518/?uid=1909139792283648233927&from=singlemessage");
        r.setDecrdescription("骑客大赛，等你来战");
        r.setImgUrl("http://icon.qiantucdn.com/revision/images/resource.jpg");
        r.setTime("2017-05-30 00:00");
        r.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                Logger.d(s, e);
            }
        });
    }

    private void insertMessage2() {
        MyMessage r = new MyMessage();
        r.setClickUrl("https://prg.lp-game.com/cdnweb/sunplay050201/?uid=1909139792283648233927&from=singlemessage");
        r.setDecrdescription("一起和阳光玩游戏");
        r.setImgUrl("http://pic.qiantucdn.com/58pic/18/42/77/55825cab52849_1024.jpg");
        r.setTime("2017-05-09 00:00");
        r.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                Logger.d(s, e);
            }
        });
    }

    private void insert() {
        RideSummary r = new RideSummary();
        //  r.setPhone("18290022002");
        r.setKaluli("123.5");
        r.setRide("123.5");
        r.setSave("345");
        r.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                Logger.d(s, e);
            }
        });
    }

    private void insert3() {
        RideSummary r = new RideSummary();
        r.setMyUser(mUser);
        r.setKaluli("123.5");
        r.setRide("123.5");
        r.setSave("345");
        r.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                Logger.d(s, e);
            }
        });
    }

    private void insert4() {
        Credit r = new Credit();
        r.setMyUser(mUser);
        r.setCreditNub(150);
        r.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                Logger.d(s, e);
            }
        });
    }
    private void insert5() {
        RechargeHistoryData r = new RechargeHistoryData();
        r.setMyUser(mUser);
        r.setCash("10元");
        r.setPayApproach("微信支付");
        r.setStatusCode("付款成功");
        r.setTimestamp("2017-01-01 09:20");
        r.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                Logger.d(s, e);
            }
        });
    }

    private void insert6() {
        MyTripsData r = new MyTripsData();
        r.setMyUser(mUser);
        r.setMoney("1");
        r.setCarNub("1234509");
        r.setTime("2019-10-10 19:30");
        r.setRideTime("5");
        r.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                Logger.d(s, e);
            }
        });
    }
    private void query() {
        BmobQuery<RideSummary> bmobQuery = new BmobQuery();
        bmobQuery.addWhereEqualTo("phone", "18290022002");
        bmobQuery.findObjects(new FindListener<RideSummary>() {
            @Override
            public void done(List<RideSummary> list, BmobException e) {
                Logger.d(e);
                if (e == null) {
                    Log.d(TAG, "done: ");
                }
            }
        });
    }
}
