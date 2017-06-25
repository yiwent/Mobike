package com.yiwen.mobike.activity.usercenter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.yiwen.mobike.MyApplication;
import com.yiwen.mobike.R;
import com.yiwen.mobike.activity.pay.WalletActivity;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.views.TabTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MobileNumActivity extends AppCompatActivity {

    @BindView(R.id.title_mobike_num)
    TabTitleView mTitleMobikeNum;
    @BindView(R.id.bt_change_phone)
    Button       mBtChangePhone;
    @BindView(R.id.tv_mobike_curent)
    TextView     mTv_mobike_curent;
    private boolean isCash = false;
    private String mMyphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_num);
        ButterKnife.bind(this);

        initView();

        initEvent();
    }

    private void initView() {
        mMyphone = getMyphone();
        if (mMyphone.length() > 0) {
            mTv_mobike_curent.setText("您当前手机号为：" + mMyphone);
        }

    }

    /**
     * @return 返回当前手机号
     */
    private String getMyphone() {
        return MyApplication.getInstance().getUser().getMobilePhoneNumber();

    }

    private void initEvent() {
        mTitleMobikeNum.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                MobileNumActivity.this.finish();
            }
        });
    }

    @OnClick(R.id.bt_change_phone)
    public void onViewClicked() {
        isCash = CheckCash();
        if (!isCash) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MobileNumActivity.this);
            builder.setMessage("更换手机号需将先退押金您未退押金，是否前去退押金？");
            builder.setCancelable(true);

            builder.setPositiveButton("确定",
                    new android.content.DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            Intent intent = new Intent(MobileNumActivity.this, WalletActivity.class);
                            startActivityForResult(intent, 1); // 设置完成后返回到原来的界面

                        }
                    });
            builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        } else {
            // TODO: 2017/5/24
            ToastUtils.show(MobileNumActivity.this, "去更换手机号");
        }
    }

    /**
     * 网络请求押金是否已经退了
     *
     * @return turn :已经退了
     * false:没有退
     */
    private boolean CheckCash() {
        return false;
        // TODO: 2017/5/24
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    isCash = CheckCash();
                    break;
            }
        }
    }
}
