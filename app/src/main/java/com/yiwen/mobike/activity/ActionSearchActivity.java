package com.yiwen.mobike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.yiwen.mobike.R;
import com.yiwen.mobike.views.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActionSearchActivity extends AppCompatActivity {
    @BindView(R.id.et_action_search)
    ClearEditText mEtActionSearch;
    @BindView(R.id.tv_search_cancel)
    TextView      mTvSearchCancel;
    @BindView(R.id.tv_actionsech_mylotion)
    TextView      mTvActionsechMylotion;
    @BindView(R.id.recycler_actionserch)
    RecyclerView  mRecyclerActionserch;
    @BindView(R.id.tv_dele)
    TextView      mTvDele;
    private BDLocation mBDLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_search);
        ButterKnife.bind(this);
        intiView();
        initData();
        initEvent();

    }

    private void initEvent() {

    }

    private void initData() {
    }

    private void intiView() {
        Intent intent = getIntent();
        mBDLocation = intent.getParcelableExtra("mylotion");
        if (mBDLocation.getProvince() != null) {
            StringBuilder currentPosion = new StringBuilder();
            currentPosion.append(mBDLocation.getProvince()).append("省：");
            currentPosion.append(mBDLocation.getCity()).append("市：");
            currentPosion.append(mBDLocation.getDirection()).append("区：");
            currentPosion.append(mBDLocation.getStreet()).append("街道：");
            mTvActionsechMylotion.setText(currentPosion);
        }

    }

    @OnClick({R.id.tv_search_cancel, R.id.tv_dele})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search_cancel:
                ActionSearchActivity.this.finish();
                break;
            case R.id.tv_dele:
                break;
        }
    }
}
