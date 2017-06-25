package com.yiwen.mobike.activity.redpacket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yiwen.mobike.MyApplication;
import com.yiwen.mobike.R;
import com.yiwen.mobike.activity.customer.CustomerServiceWebActivity;
import com.yiwen.mobike.bean.MyRedPocketData;
import com.yiwen.mobike.bean.MyUser;
import com.yiwen.mobike.utils.BuidUrl;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.views.TabTitleView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import dmax.dialog.SpotsDialog;

public class MyRedPocketActivity extends AppCompatActivity {

    private static final String TAG = "MyRedPocketActivity";
    @BindView(R.id.title_red)
    TabTitleView mTitleRed;
    @BindView(R.id.id_tv_pockey)
    TextView     mTvPockey;
    @BindView(R.id.id_tv_gonlv)
    TextView     mTvGonlv;
    @BindView(R.id.id_bt_takemoney)
    Button       mBtTakemoney;
    @BindView(R.id.id_tv_problem)
    TextView     mTvProblem;
    private SpotsDialog mDialog;
    MyUser myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_red_pocket);
        ButterKnife.bind(this);
        mDialog=new SpotsDialog(this);
        initView();
    }

    private void initView() {
        myUser = MyApplication.getInstance().getUser();
        mTitleRed.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        mTitleRed.setOnRightTextViewClickListener(new TabTitleView.OnRightButtonClickListener() {
            @Override
            public void onClick() {
                startActivity(new Intent(MyRedPocketActivity.this,RedPocketDetailActivity.class));
            }
        });
        mTvPockey.setText("0.0元");
        mDialog.show();
        if (myUser != null){
            BmobQuery<MyRedPocketData> query = new BmobQuery<MyRedPocketData>();
            query.addWhereEqualTo("mMyUser",MyApplication.getInstance().getUser());
            query.addWhereEqualTo("isvalid", true);
            query.findObjects(new FindListener<MyRedPocketData>() {
                @Override
                public void done(List<MyRedPocketData> list, BmobException e) {
                    if (e == null&&list!=null) {
                        Float total=0.00f;
                      for (int i=0;i<list.size();i++){
                          total+=Float.parseFloat(list.get(i).getMoney());
                          Logger.d(list.get(i));
                      }
                        mTvPockey.setText(total+"");
                    } else {
                        mTvPockey.setText("0.0");
                    }
                    if (Float.parseFloat(mTvPockey.getText().toString())>10.00f){
                        mBtTakemoney.setBackgroundResource(R.color.red);
                        mBtTakemoney.setClickable(true);
                    }else {
                        mBtTakemoney.setBackgroundResource(R.color.gray);
                        mBtTakemoney.setClickable(false);
                    }
                    if (mDialog!=null&&mDialog.isShowing())mDialog.dismiss();
                }
            });
        }

    }

    @OnClick({ R.id.id_tv_gonlv, R.id.id_bt_takemoney, R.id.id_tv_problem})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_tv_gonlv:
                startActivity(CustomerServiceWebActivity
                        .getCustomerServiceIntent(MyRedPocketActivity.this, "红包攻略",
                                BuidUrl.getRedPacketCapturing()));
                break;
            case R.id.id_bt_takemoney:
                ToastUtils.show(MyRedPocketActivity.this,"可以提现了");
                break;
            case R.id.id_tv_problem:
                startActivity(CustomerServiceWebActivity
                        .getCustomerServiceIntent(MyRedPocketActivity.this, "常见问题",
                                BuidUrl.getRedPacketProblem(1,30)));
                break;
        }
    }
}
