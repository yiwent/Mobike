package com.yiwen.mobike.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jungly.gridpasswordview.GridPasswordView;
import com.yiwen.mobike.R;
import com.yiwen.mobike.qrcode.zxing.camera.CameraManager;
import com.yiwen.mobike.views.TabTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QRCodeInputActivity extends AppCompatActivity {
    public static boolean unlockSuccess = false;
    @BindView(R.id.title_wallet)
    TabTitleView     mTitleWallet;
    @BindView(R.id.id_iv_flash)
    ImageView        mIvFlash;
    @BindView(R.id.id_bt_query)
    Button           mBtQuery;
    @BindView(R.id.pswView)
    GridPasswordView mPasswordView;
    private String carNub;
    private boolean flashLightOpen = false;
    private boolean isOnlyNub      = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_input);
        ButterKnife.bind(this);
        CameraManager.init(getApplication());
        Intent intent = getIntent();
        if (intent != null)
            isOnlyNub = intent.getBooleanExtra("isOnlyNub", false);
        initView();
    }

    private void initView() {
        mTitleWallet.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        mTitleWallet.setOnRightTextViewClickListener(new TabTitleView.OnRightButtonClickListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(QRCodeInputActivity.this, HelpCardActivity.class);
                startActivity(intent);
            }
        });
        mPasswordView.setPasswordVisibility(true);
        mPasswordView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
                mBtQuery.setBackgroundResource(R.color.smssdk_gray);
                mBtQuery.setClickable(false);
            }

            @Override
            public void onInputFinish(String psw) {
                carNub = psw;
                mBtQuery.setBackgroundResource(R.color.red);
                mBtQuery.setClickable(true);
            }
        });
    }


    /**
     * 切换散光灯状态
     */
    public void toggleFlashLight() {
        if (flashLightOpen) {
            setFlashLightOpen(false);
        } else {
            setFlashLightOpen(true);
        }
    }

    private void setFlashLightOpen(boolean open) {
        if (flashLightOpen == open)
            return;

        flashLightOpen = !flashLightOpen;
        CameraManager.get().setFlashLight(open);
    }


    @OnClick({R.id.id_iv_flash, R.id.id_bt_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_iv_flash:
                flash();
                break;
            case R.id.id_bt_query:
                queryUnlock();
                break;
        }
    }

    public static Intent getMyIntent(Context context, boolean isOnlyNub) {
        Intent intent = new Intent(context, QRCodeInputActivity.class);
        intent.putExtra("isOnlyNub", isOnlyNub);
        return intent;
    }

    private void queryUnlock() {

        if (isOnlyNub) {
            Intent intent = new Intent();
            intent.putExtra("result", mPasswordView.getPassWord().toString());
            setResult(RESULT_OK, intent);
            finish();
        } else {
            unlockSuccess = true;
            startActivity(new Intent(QRCodeInputActivity.this, MainActivity.class));
            finish();
        }

    }

    private void flash() {
        if (flashLightOpen) {
            mIvFlash.setImageResource(R.drawable.scan_qrcode_flash_light_off);
        } else {
            mIvFlash.setImageResource(R.drawable.scan_qrcode_flash_light_on);
        }
        toggleFlashLight();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CameraManager.get().closeDriver();
    }

    public void onDestroy() {
        super.onDestroy();
        unlockSuccess = false;
    }

}
