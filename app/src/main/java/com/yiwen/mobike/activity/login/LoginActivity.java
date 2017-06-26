package com.yiwen.mobike.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yiwen.mobike.MyApplication;
import com.yiwen.mobike.R;
import com.yiwen.mobike.activity.MainActivity;
import com.yiwen.mobike.activity.customer.CustomerServiceWebActivity;
import com.yiwen.mobike.base.BaseActivity;
import com.yiwen.mobike.bean.Credit;
import com.yiwen.mobike.bean.MyRedPocketData;
import com.yiwen.mobike.bean.MyUser;
import com.yiwen.mobike.bean.RideSummary;
import com.yiwen.mobike.utils.BuidUrl;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.views.ClearEditText;
import com.yiwen.mobike.views.CountTimerView;
import com.yiwen.mobike.views.TabTitleView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;
import dmax.dialog.SpotsDialog;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.toolbar_login)
    TabTitleView  mToolbarLogin;
    @BindView(R.id.et_phone)
    ClearEditText mEtPhone;
    @BindView(R.id.et_code)
    EditText      mEtCode;
    @BindView(R.id.get_code)
    Button        mGetCode;
    @BindView(R.id.loin_voice)
    TextView      mLoinVoice;
    @BindView(R.id.login_query)
    Button        mLoginQuery;
    @BindView(R.id.login_services)
    TextView      mLoginServices;

    private boolean isRegister;//是否注册
    private boolean isNeedPaycash, isRealName;//押金
    private boolean     isSendCode;//是否已发送了验证码
    private SpotsDialog mDialog;

    private CountTimerView mCountTimeView;

    private              int    phoneLength        = 0;
    private              int    codeLength         = 0;
    // 默认使用中国区号
    private static final String DEFAULT_COUNTRY_ID = "42";
    private String mPhone;

    private SmsEventHandler mEventHandler;
    private MyUser          mUser;
    private MyApplication mApplication = MyApplication.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        intView();
        initDate();
        initEvent();
    }

    private void intView() {
        mDialog = new SpotsDialog(LoginActivity.this);
    }

    private void DismissMyDialog() {
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }

    private void initDate() {
        SMSSDK.initSDK(this, "1dfed2cdde843", "4266d445a7c298caecfb04ecb165fde7");
        mEventHandler = new SmsEventHandler();

        SMSSDK.registerEventHandler(mEventHandler);
        SMSSDK.getSupportedCountries();
    }

    private void initEvent() {
        mToolbarLogin.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                Go2Main();
            }
        });


        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phoneLength = s.length();
                if (phoneLength > 0) {
                    setButtonRed(mGetCode);
                } else {
                    setButtonGray(mGetCode);
                }
                if (phoneLength > 0 && codeLength > 0) {
                    setButtonRed(mLoginQuery);
                } else {
                    setButtonGray(mLoginQuery);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                codeLength = s.length();
                if (phoneLength > 0 && codeLength > 0) {
                    setButtonRed(mLoginQuery);
                } else {
                    setButtonGray(mLoginQuery);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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

    class SmsEventHandler extends EventHandler {
        @Override
        public void afterEvent(final int event, final int result, final Object data) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //回调完成
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        //返回支持发送验证码的国家列表
                        if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                            //                            SMSSDK.getSupportedCountries();
                            onCountryListGot((ArrayList<HashMap<String, Object>>) data);
                            //获取验证码成功
                        } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            // 请求验证码后，跳转到验证码填写页面
                            afterVerificationCodeRequested((Boolean) data);
                            //提交验证码成功
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {

                            mEtCode.setText("");

                            RegOK();
                        }

                    } else {

                        // 根据服务器返回的网络错误，给toast提示
                        try {
                            ((Throwable) data).printStackTrace();
                            Throwable throwable = (Throwable) data;
                            JSONObject object = new JSONObject(
                                    throwable.getMessage());
                            String des = object.optString("detail");
                            if (!TextUtils.isEmpty(des)) {
                                ToastUtils.show(LoginActivity.this, des);
                                return;
                            }
                        } catch (Exception e) {
                            SMSLog.getInstance().w(e);
                        }
                    }
                }
            });
        }
    }

    private void RegOK() {
        mDialog.show();
        mPhone = mEtPhone.getText().toString().trim().replace("\\s*", "");// TODO: 2017/6/21  测试后删除
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereEqualTo("username", mPhone);
        query.setLimit(1);
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                if (e == null) {
                    if (list.size() > 0 && list != null) {
                        Log.d(TAG, "cheackUser: ok");
                        toLogin();
                    } else {
                        Log.d(TAG, "cheackUser: notRegister");
                        toRegister();
                    }
                } else {
                    Log.d(TAG, "done: " + e);
                    DismissMyDialog();
                }
            }
        });
    }

    private void toLogin() {
        mUser = new MyUser();
        mUser.setUsername(mPhone);
        mUser.setPassword(mPhone);
        mUser.login(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e == null) {
                    if (myUser != null)
                        Logger.d(myUser);
                    mApplication.clearUser();
                    mApplication.putUser(myUser);
                    Log.d(TAG, "done: login ok");
                    Go2MainOrPay();
                } else {
                    Log.d("bmob", "isRegister：" + e.getMessage() + "," + e.getErrorCode());
                    ToastUtils.show(LoginActivity.this, "未知错误");
                }
                DismissMyDialog();
            }
        });
    }

    private void toRegister() {
        mUser = new MyUser();
        mUser.setUsername(mPhone);
        mUser.setPassword(mPhone);
        mUser.setMobilePhoneNumber(mPhone);
        mUser.setMoney(0.0f);
        mUser.setPay(false);
        mUser.setRealName(false);
        mUser.setMyName("");
        mUser.setNickName(mPhone.substring(0, 3) + "****" + mPhone.substring(7, 11));
        mUser.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e == null) {
                    Logger.d(myUser);
                    RideSummary summary = new RideSummary();
                    summary.setMyUser(myUser);
                    summary.setKaluli("11");
                    summary.setRide("123");
                    summary.setSave("1222");
                    summary.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {

                            } else {
                                Logger.d(e);
                            }
                        }
                    });
                    Credit cr = new Credit();
                    cr.setMyUser(myUser);
                    cr.setCreditNub(100);
                    cr.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {

                            } else {
                                Logger.d(e);
                            }
                        }
                    });
                    initPocketData(myUser);
                    mApplication.putUser(myUser);
                    Log.d(TAG, "done: Register ok");
                    Go2MainOrPay();
                } else {
                    Log.d("bmob", "Register：" + e.getMessage() + "," + e.getErrorCode());
                    ToastUtils.show(LoginActivity.this, "未知错误");
                }
                DismissMyDialog();
            }
        });
    }

    private void initPocketData(MyUser myUser) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        MyRedPocketData pocketData=new MyRedPocketData(myUser,"8888",
                formatter.format(curDate),"5",true);
        pocketData.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });
        pocketData=new MyRedPocketData(myUser,"123456",
                formatter.format(curDate),"5",true);
        pocketData.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });
    }

    /**
     * 获得支持的国家列表
     *
     * @param data
     */
    private void onCountryListGot(ArrayList<HashMap<String, Object>> data) {
        for (HashMap<String, Object> country : data) {
            String code = (String) country.get("zone");
            String rule = (String) country.get("rule");
            if (TextUtils.isEmpty(code) || TextUtils.isEmpty(rule)) {
                continue;
            }
            Log.d(TAG, "onCountryListGot: " + code + ":" + rule);
        }
    }

    /**
     * 请求验证码成功后跳转
     *
     * @param data
     */
    private void afterVerificationCodeRequested(Boolean data) {
        DismissMyDialog();
        ToastUtils.show(LoginActivity.this, "获取验证码成功");
        String phone = mEtPhone.getText().toString().trim().replace("\\s*", "");
        //        String countryCode = mTvCountryCode.getText().toString().trim();
        String countryCode = "86";

        if (countryCode.startsWith("+")) {
            countryCode = countryCode.substring(1);
        }
        isSendCode = false;
    }

    private void Go2Main() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void Go2MainOrPay() {
        if (MyApplication.getInstance().getUser().getPay()
                && MyApplication.getInstance().getUser().getRealName()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (!MyApplication.getInstance().getUser().getPay()) {
            Intent intent = new Intent(LoginActivity.this, RegisterRechargeActivity.class);
            startActivity(intent);
            finish();
        } else if (!MyApplication.getInstance().getUser().getRealName()) {
            Intent intent = new Intent(LoginActivity.this, IDCardVerifyActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @OnClick({R.id.get_code, R.id.loin_voice, R.id.login_query, R.id.login_services})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_code:
                getCode();
                break;
            case R.id.loin_voice:
                ToastUtils.show(LoginActivity.this, "click:语音验证");
                getCode();
                break;
            case R.id.login_query:
                // submitCode();
                RegOK();//// TODO: 2017/6/21 测试后改为submit 
                break;
            case R.id.login_services:
                startActivity(CustomerServiceWebActivity
                        .getCustomerServiceIntent(LoginActivity.this, "用车服务条款",
                                BuidUrl.getProtocol()));
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        mPhone = mEtPhone.getText().toString().trim().replace("\\s*", "");
        //      String countryCode = mTvCountryCode.getText().toString().trim();
        String countryCode = "+86";
        // String countryCode = mTvCountryCode.getText().toString().trim();
        if (checkPhoneNum(mPhone, countryCode)) {
        /*请求获得验证码*/
            Log.d(TAG, "getCode: " + mPhone + "**" + countryCode);
            SMSSDK.getVerificationCode(countryCode, mPhone);
            mCountTimeView = new CountTimerView(mGetCode);
            mCountTimeView.start();
            mDialog.show();
        }
    }

    /**
     * 检查手机号格式
     *
     * @param phone
     * @param countryCode
     */
    private boolean checkPhoneNum(String phone, String countryCode) {
        if (countryCode.startsWith("+")) {
            countryCode = countryCode.substring(1);
        }
        if (TextUtils.isEmpty(phone)) {
            mEtPhone.setError("手机号格式有误");
            ToastUtils.show(this, "请输入手机号码");
            DismissMyDialog();
            return false;
        }
        if (countryCode.equals("86")) {
            if (phone.length() != 11) {
                mEtPhone.setError("手机号长度不正确");
                ToastUtils.show(this, "手机号长度不正确");
                DismissMyDialog();
                return false;
            }
        }
        String rule = "^1(3|5|7|8|4)\\d{9}";
        Pattern compile = Pattern.compile(rule);
        Matcher matcher = compile.matcher(phone);
        if (!matcher.matches()) {
            mEtPhone.setError("您输入的手机号码格式不正确");
            ToastUtils.show(this, "您输入的手机号码格式不正确");
            DismissMyDialog();
            return false;
        }
        return true;
    }

    private void submitCode() {
        mDialog.setMessage("正在验证...");
        mDialog.show();
        String code = mEtCode.getText().toString().trim();
        mPhone = mEtPhone.getText().toString().trim().replace("\\s*", "");
        if (TextUtils.isEmpty(code)) {
            mEtCode.setError("请输入验证码");
            ToastUtils.show(this, "请输入验证码");
            return;
        }
        Log.d(TAG, "submitCode: " + mPhone + code);
        SMSSDK.submitVerificationCode("86", mPhone, code);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(mEventHandler);
    }
}
