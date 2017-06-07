package com.yiwen.mobike.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yiwen.mobike.R;
import com.yiwen.mobike.views.TabTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InviteFriendActivity extends AppCompatActivity {

    @BindView(R.id.title_invent)
    TabTitleView mTitleInvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {
        mTitleInvent.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                InviteFriendActivity.this.finish();
            }
        });

    }
}
