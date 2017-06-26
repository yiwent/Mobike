package com.yiwen.mobike.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yiwen.mobike.MyApplication;
import com.yiwen.mobike.R;
import com.yiwen.mobike.activity.MainActivity;
import com.yiwen.mobike.activity.customer.CustomerServiceWebActivity;
import com.yiwen.mobike.bean.MyUser;
import com.yiwen.mobike.utils.BuidUrl;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.views.ClearEditText;
import com.yiwen.mobike.views.TabTitleView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class IDCardVerifyActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_login)
    TabTitleView  mToolbarLogin;
    @BindView(R.id.et_idcard)
    ClearEditText mEtIdcard;
    @BindView(R.id.et_name)
    ClearEditText mEtName;
    @BindView(R.id.login_query)
    Button        mLoginQuery;
    @BindView(R.id.login_services)
    TextView      mLoginServices;

    private MyUser      mUser;
    private SpotsDialog mDialog;
    private int idcardLength = 0;
    private int nameLength  = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard_verify);
        ButterKnife.bind(this);
        intView();

    }

    private void intView() {
        mDialog = new SpotsDialog(IDCardVerifyActivity.this);
        mToolbarLogin.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                Go2Main();
            }
        });
        mEtIdcard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                idcardLength = s.length();
               checkButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameLength = s.length();
                checkButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void checkButton() {
        if (nameLength > 0 && idcardLength>0 ) {//== 18
            setButtonRed(mLoginQuery);
        } else {
            setButtonGray(mLoginQuery);
        }
    }
    /**
     * 改变bt颜色red设置可点击
     *
     * @param bt
     */
    private void setButtonRed(Button bt) {
        bt.setClickable(true);
        bt.setBackgroundResource(R.color.red);
    }

    /**
     * 改变bt颜色gray设置不可点击
     *
     * @param bt
     */
    private void setButtonGray(Button bt) {
        bt.setClickable(false);
        bt.setBackgroundResource(R.color.gray);
    }

    private void Go2Main() {
        Intent intent = new Intent(IDCardVerifyActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void DismissDialog() {
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }

    @OnClick({R.id.login_query, R.id.login_services})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_query:
                toChackId();
                break;
            case R.id.login_services:
                startActivity(CustomerServiceWebActivity
                        .getCustomerServiceIntent(IDCardVerifyActivity.this, "用车服务条款",
                                BuidUrl.getProtocol()));
                break;
        }
    }

    private void toChackId() {
        if (TextUtils.isEmpty(mEtName.getText().toString())) {
            mEtName.setError("姓名不能为空");
            ToastUtils.show(this, "请输姓名");
            return ;
        }
        String rule = "^\\d{15}|\\d{18}$";
        Pattern compile = Pattern.compile(rule);
        Matcher matcher = compile.matcher(mEtIdcard.getText().toString().trim());
        if (!matcher.matches()) {
            mEtIdcard.setError("您输入的身份证格式不正确");
            ToastUtils.show(this, "您输入的身份证码格式不正确");
            return;
        }
        MyUser newUse=new MyUser();
        newUse.setMyName(mEtName.getText().toString().trim());
        newUse.setIdCard(mEtIdcard.getText().toString().trim());
        newUse.setRealName(true);
        MyUser myUser= MyApplication.getInstance().getUser();
        myUser.setMyName(mEtName.getText().toString().trim());
        myUser.setIdCard(mEtIdcard.getText().toString().trim());
        myUser.setRealName(true);
        MyApplication.getInstance().upDataUser(myUser,newUse);
        ToastUtils.show(this, "实名认证成功");
        Go2Main();
    }
}
