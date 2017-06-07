package com.yiwen.mobike;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yiwen.mobike.bean.MyMessage;
import com.yiwen.mobike.bean.RideSummary;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        BmobUser user=BmobUser.getCurrentUser();
    }

    @OnClick(R.id.bt_test)
    public void onViewClicked() {
        insert2();
    }
    private void insert1() {
        MyMessage r = new MyMessage();
        r.setClickUrl("https://activity.huaruntong.cn/mobike0518/?uid=1909139792283648233927&from=singlemessage");
        r.setDecrdescription("骑客大赛，等你来战");
        r.setImgUrl("http://icon.qiantucdn.com/revision/images/resource.jpg");
        r.setTime("2017-05-30 00:00");
        r.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                Logger.d(s,e);
            }
        });
    }
    private void insert2() {
        MyMessage r = new MyMessage();
        r.setClickUrl("https://prg.lp-game.com/cdnweb/sunplay050201/?uid=1909139792283648233927&from=singlemessage");
        r.setDecrdescription("一起和阳光玩游戏");
        r.setImgUrl("http://pic.qiantucdn.com/58pic/18/42/77/55825cab52849_1024.jpg");
        r.setTime("2017-05-09 00:00");
        r.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                Logger.d(s,e);
            }
        });
    }
    private void insert() {
        RideSummary r = new RideSummary();
        r.setPhone("18290022002");
        r.setKaluli("123.5");
        r.setRide("123.5");
        r.setSave("345");
        r.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                Logger.d(s,e);
            }
        });
    }
    private void query() {
        BmobQuery<RideSummary> bmobQuery = new BmobQuery();
        bmobQuery.addWhereEqualTo("phone","18290022002");
        bmobQuery.findObjects(new FindListener<RideSummary>() {
            @Override
            public void done(List<RideSummary> list, BmobException e) {
                Logger.d(e);
                if (e==null){
                    Log.d(TAG, "done: ");
                }
            }
        });
    }
}
