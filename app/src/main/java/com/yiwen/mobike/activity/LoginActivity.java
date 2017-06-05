package com.yiwen.mobike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yiwen.mobike.R;
import com.yiwen.mobike.utils.MyConstains;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.views.ClearEditText;
import com.yiwen.mobike.views.CountTimerView;
import com.yiwen.mobike.views.TabTitleView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;
import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {
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
    
    private boolean isNeedLogin = true;//手机登陆
    private boolean isNeedPaycash = true;//押金
    private boolean isSendCode;//是否已发送了验证码
    private SpotsDialog mDialog;
    
    private CountTimerView mCountTimeView;

    private              int    phoneLength        = 0;
    private              int    codeLength         = 0;
    // 默认使用中国区号
    private static final String DEFAULT_COUNTRY_ID = "42";

    private SmsEventHandler mEventHandler;
    
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
        mDialog=new SpotsDialog(LoginActivity.this);
    }
    private void DimissMyDialog(){
        if (mDialog!=null&&mDialog.isShowing())
            mDialog.dismiss();
    }

    private void initDate() {
        SMSSDK.initSDK(this, "1dfed2cdde843", "4266d445a7c298caecfb04ecb165fde7");
        mEventHandler = new SmsEventHandler();
        SMSSDK.registerEventHandler(mEventHandler);

        isNeedLogin = getSharedPreferences(MyConstains.SP_MOBIKE, MODE_PRIVATE).
                getBoolean(MyConstains.IS_NEED_LOGIN, true);
        isNeedPaycash = getSharedPreferences(MyConstains.SP_MOBIKE, MODE_PRIVATE).
                getBoolean(MyConstains.IS_NEED_PAYCASH, false);// TODO: 2017/5/26  
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

        private void RegOK() {
            DimissMyDialog();
            ToastUtils.show(LoginActivity.this, "登陆成功");
            isNeedLogin=false;
            getSharedPreferences(MyConstains.SP_MOBIKE, MODE_PRIVATE)
                    .edit()
                    .putBoolean(MyConstains.IS_NEED_LOGIN, isNeedLogin)
                    .apply();
            if (isNeedPaycash){
              // TODO: 2017/5/26  
            }else {
                Go2Main();
            }
           
        }

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
        DimissMyDialog();
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

    @OnClick({R.id.get_code, R.id.loin_voice, R.id.login_query, R.id.login_services})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_code:
                getCode();
                break;
            case R.id.loin_voice:
                ToastUtils.show(LoginActivity.this,"语音验证");
                break;
            case R.id.login_query:
                submitCode();
                break;
            case R.id.login_services:
                ToastUtils.show(LoginActivity.this,"服务点击");
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        String phone = mEtPhone.getText().toString().trim().replace("\\s*", "");
        //      String countryCode = mTvCountryCode.getText().toString().trim();
        String countryCode = "+86";
        // String countryCode = mTvCountryCode.getText().toString().trim();
        if (checkPhoneNum(phone, countryCode)) {
        /*请求获得验证码*/
            Log.d(TAG, "getCode: " + phone + "**" + countryCode);
            SMSSDK.getVerificationCode(countryCode, phone);
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
            //ToastUtils.show(this, "请输入手机号码");
            //            dissmissDialog();
            return false;
        }
        if (countryCode.equals("86")) {
            if (phone.length() != 11) {
                mEtPhone.setError("手机号长度不正确");
                // ToastUtils.show(this, "手机号长度不正确");
                //                dissmissDialog();
                return false;
            }
        }
        String rule = "^1(3|5|7|8|4)\\d{9}";
        Pattern compile = Pattern.compile(rule);
        Matcher matcher = compile.matcher(phone);
        if (!matcher.matches()) {
            mEtPhone.setError("您输入的手机号码格式不正确");
            // ToastUtils.show(this, "您输入的手机号码格式不正确");
            // dissmissDialog();
            return false;
        }
        return true;
    }

    private void submitCode() {
        mDialog.setMessage("正在验证...");
        String code = mEtCode.getText().toString().trim();
        String mPhone = mEtPhone.getText().toString().trim().replace("\\s*", "");
        if (TextUtils.isEmpty(code)) {
            mEtCode.setError("请输入验证码");
            //            ToastUtils.show(this, "请输入验证码");
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
