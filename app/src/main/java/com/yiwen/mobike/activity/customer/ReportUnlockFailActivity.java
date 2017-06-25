package com.yiwen.mobike.activity.customer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yiwen.mobike.MyApplication;
import com.yiwen.mobike.R;
import com.yiwen.mobike.bean.MyUser;
import com.yiwen.mobike.bean.ReportUnlockData;
import com.yiwen.mobike.qrcode.CaptureActivity;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.views.TabTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import dmax.dialog.SpotsDialog;

public class ReportUnlockFailActivity extends AppCompatActivity {

    private static final int REQEST_CARNUB = 22;
    @BindView(R.id.title_unlock_fail)
    TabTitleView mTitleUnlockFail;
    @BindView(R.id.lo_report_unlock)
    LinearLayout mLoReportUnlock;
    @BindView(R.id.et_report_unlock)
    EditText     mEtReportUnlock;
    @BindView(R.id.id_bt_query)//
    Button       mBtQuery;
    @BindView(R.id.id_tv_carnub)//id_tv_desc_nub
    TextView     mCarnub;
    @BindView(R.id.id_tv_desc_nub)//
    TextView     mDesc_nub;
    ReportUnlockData mData;
    MyUser           mMyUser;
    SpotsDialog      mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_unlock_fail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTitleUnlockFail.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        mMyUser = MyApplication.getInstance().getUser();
        if (mMyUser == null)
            finish();
        Intent intent=getIntent();
        if (!TextUtils.isEmpty(intent.getStringExtra("carNub"))){
            mCarnub.setText(intent.getStringExtra("carNub"));
        }
        mData = new ReportUnlockData();
        mDialog = new SpotsDialog(this);
        mEtReportUnlock.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length=140-s.length();
                mDesc_nub.setText(length+"/140");
                if (s.length()>0&&mCarnub.getText().toString()!="扫描二维码或者手动输入"){
                    mBtQuery.setBackgroundResource(R.color.red);
                    mBtQuery.setClickable(true);
                }else {
                    mBtQuery.setBackgroundResource(R.color.bg_color);
                    mBtQuery.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.lo_report_unlock, R.id.id_bt_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lo_report_unlock:
                startActivityForResult(CaptureActivity.
                        getMyIntent(ReportUnlockFailActivity.this, true), REQEST_CARNUB);
                break;
            case R.id.id_bt_query:
                toQuery();
                break;
        }
    }

    private void toQuery() {
        if (mCarnub.getText().toString()=="扫描二维码或者手动输入") {
            ToastUtils.show(ReportUnlockFailActivity.this, "请输入单车编号");
            return;
        }
        if (TextUtils.isEmpty(mEtReportUnlock.getText().toString())) {
            ToastUtils.show(ReportUnlockFailActivity.this, "请输入描述信息");
            return;
        }
        mDialog.show();
        mData.setMyUser(mMyUser);
        mData.setCarNub(mCarnub.getText().toString());
        mData.setDesc(mEtReportUnlock.getText().toString());
        mData.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    ToastUtils.show(ReportUnlockFailActivity.this, "提交成功");
                    finish();
                } else {
                    ToastUtils.show(ReportUnlockFailActivity.this, "提交失败");
                }
                if (mDialog.isShowing())
                    mDialog.dismiss();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQEST_CARNUB:
                getCarNub(resultCode, data);
                break;
        }
    }

    private void getCarNub(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            mCarnub.setText(data.getStringExtra("result"));
        }
    }

    public static Intent getMyIntent(Context context, String carNub){
        Intent intent=new Intent(context,ReportUnlockFailActivity.class);
        intent.putExtra("carNub",carNub);
        return intent;
    }

}
