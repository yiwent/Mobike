package com.yiwen.mobike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yiwen.mobike.R;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.views.ClearEditText;
import com.yiwen.mobike.views.TabTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ModifyNickNameActivity extends AppCompatActivity {

    @BindView(R.id.title_modify_nick_name)
    TabTitleView  mTitleModifyNickName;
    @BindView(R.id.et_modify_nick_name)
    ClearEditText mEtModifyNickName;
    private String mNick_name;
    private boolean isUpdateOk = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_nick_name);
        ButterKnife.bind(this);
        initData();
        initEvent();
    }

    private void initEvent() {
        mTitleModifyNickName.setOnLeftButtonClickListener(
                new TabTitleView.OnLeftButtonClickListener() {
                    @Override
                    public void onClick() {
                        ModifyNickNameActivity.this.finish();
                    }
                });
        mTitleModifyNickName.setOnRightTextViewClickListener(
                new TabTitleView.OnRightButtonClickListener() {
                    @Override
                    public void onClick() {
                        mNick_name = mEtModifyNickName.getText().toString();
                        if (mNick_name.length() < 1) {
                            ToastUtils.show(ModifyNickNameActivity.this, "昵称不能为空");
                            return;
                        } else {
                            updateNickname();
                        }
                    }
                });
    }

    private void updateNickname() {
        // TODO: 2017/5/24 updateInternet
        if (isUpdateOk) {
            Intent intent = new Intent();
            intent.putExtra("nick_name", mNick_name);
            setResult(RESULT_OK, intent);
            ModifyNickNameActivity.this.finish();
        }
    }

    private void initData() {
        Intent intent = getIntent();
        mNick_name = intent.getStringExtra("nick_name");
        if (mNick_name.length() > 0) {
            mEtModifyNickName.setText(mNick_name);
            mEtModifyNickName.setSelection(mNick_name.length());
            mEtModifyNickName.setFocusable(true);
        }

    }
}
